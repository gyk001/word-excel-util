package cn.guoyukun.pdm2pdf.pdm;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.Filter;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import cn.com.sinosoft.ie.data.reverse.vo.ColInfo;
import cn.guoyukun.pdm2pdf.model.TableInfo;

public class PdmReader {
	// 日志对象
	private static final Logger LOG = LoggerFactory.getLogger(PdmReader.class);

	public static final String ATTR_URI = "attribute";
	public static final String ATTR_PREFIX = "a";
	public static final String COLL_URI = "collection";
	public static final String COLL_PREFIX = "c";
	public static final String OBJ_URI = "object";
	public static final String OBJ_PREFIX = "o";

	private static final Namespace A_NS = Namespace.getNamespace(ATTR_PREFIX,
			ATTR_URI);
	private static final Namespace C_NS = Namespace.getNamespace(COLL_PREFIX,
			COLL_URI);
	private static final Namespace O_NS = Namespace.getNamespace(OBJ_PREFIX,
			OBJ_URI);

	private static final String DOMAINS_XPATH = "/Model/o:RootObject/c:Children/o:Model/c:Packages/o:Package";

	private static final XPathFactory factory = XPathFactory.instance();
	
	// 包
	private static final XPathExpression<Element> EXPR_DOMAIN = getXpath(DOMAINS_XPATH, Filters.element(),O_NS, C_NS);
	// 表
	private static final XPathExpression<Element> EXPR_TABLE = getXpath("c:Tables/o:Table", Filters.element(), C_NS, O_NS);
	// 列
	private static final XPathExpression<Element> EXPR_COLUMN = getXpath("c:Columns/o:Column", Filters.element(), C_NS, O_NS);

	private Document doc;

	private Map<String, TableInfo> tables = new HashMap<String, TableInfo>();

	public void parse(InputStream input) throws JDOMException {
		Document doc = getDoc(input);
		Element root = doc.getRootElement();
		parseDomains(root);
	}

	private static <T> XPathExpression<T> getXpath(String expression, Filter<T> filter,
			Namespace... ns)  {
		return factory.compile(expression, filter, null, ns);
	}

	public Document getDoc(InputStream is) {
		try {
			if (doc == null)
				doc = new SAXBuilder().build(is);
			else
				return doc;
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	private void parseDomains(Element root) throws JDOMException {
		List<Element> domainPackages = EXPR_DOMAIN.evaluate(root);
		for (Element ele : domainPackages) {
			// 域
			String domain = ele.getChildText("Code", A_NS);
			LOG.info("解析业务域:{}", domain);
			parseTables(ele);
		}
	}

	private void parseTables(Element ele) throws JDOMException {
		List<Element> tableElements = EXPR_TABLE.evaluate(ele);
		for (Element table : tableElements) {
			// 解析表
			TableInfo tableInfo = parseTable(table);
			tables.put(tableInfo.getCode(), tableInfo);
		}
	}

	private TableInfo parseTable(Element table) throws JDOMException {
		String tableCode = table.getChildText("Code", A_NS);
		String tableName = table.getChildText("Name", A_NS);
		LOG.info("解析表[{}]", tableCode);

		TableInfo t = new TableInfo();
		t.setCode(tableCode);
		t.setName(tableName);
		// 全部字段
		Map<String, ColInfo> allColumns = parseColumns(table);
		Map<String, ColInfo> headerColumns = Maps.newLinkedHashMap();
		Map<String, ColInfo> footerColumns = Maps.newLinkedHashMap();

		final String[] head = new String[]{"ID","UPLOAD_ORG_CODE"};
		final String[] foot = new String[]{"STATE","FLAG","SEND_ORG_CODE","SEND_TIME","RELATION_PK","PERSON_ID","SEND_SYSTEM","CREATETIME","LASTUPTIME","ID_FK"};
		
		moveColumns(allColumns, headerColumns, head);
		moveColumns(allColumns, footerColumns, foot);
		
		t.setColumns(allColumns);
		t.setHeaderColumns(headerColumns);
		t.setFooterColumns(footerColumns);
		return t;
	}
	
	/**
	 * 移动列
	 * @param from
	 * @param to
	 * @param colCodes
	 */
	private void moveColumns(Map<String, ColInfo> from, Map<String, ColInfo> to, String[] colCodes){
		for(String colCode : colCodes){
			ColInfo colInfo = from.remove(colCode);
			if(colInfo ==null){
				continue;
			}
			to.put( colCode, colInfo);
		}
	}
	

	/**
	 * 解析表元素的所有列
	 * 
	 * @param table
	 * @return
	 * @throws JDOMException
	 */
	private Map<String, ColInfo> parseColumns(Element table)
			throws JDOMException {
		List<Element> allColumns = EXPR_COLUMN.evaluate(table);
		Map<String, ColInfo> colInfos = Maps.newLinkedHashMap();
		for (Element col : allColumns) {
			ColInfo colInfo = parseColumn(col);
			colInfos.put(colInfo.getCode(), colInfo);
		}
		return colInfos;
	}

	/**
	 * 解析列元素
	 * 
	 * @param col
	 * @return
	 */
	private ColInfo parseColumn(Element col) {
		if (col == null) {
			return null;
		}
		String code = getColumnAttr(col, "Code");
		LOG.info("解析列[{}]",code);
		String name = getColumnAttr(col, "Name");
		String desc = getColumnAttr(col, "Comment");
		String type = getColumnAttr(col, "DataType");
		String nullable = getColumnAttr(col, "Column.Mandatory") ;
		
		ColInfo colInfo = new ColInfo();
		colInfo.setCode(code);
		colInfo.setName(name);
		colInfo.setDesc(desc);
		colInfo.setType(type);
		 
		if(nullable==null && "1".equals( nullable)){
			colInfo.setNullable(true);	
		}else{
			colInfo.setNullable(false);
		}
		
		return colInfo;
	}

	public static String getColumnAttr(Element columnEle, String eleName) {
		Element child = columnEle.getChild(eleName, A_NS);
		if (child == null) {
			return null;
		}
		String text = child.getText();
		return text.isEmpty() ? null : text;
	}
	

	public TableInfo getTableInfo(String tableCode){
		return  tables.get(tableCode);
	}
	
	public Map<String, TableInfo> getTables() {
		return tables;
	}

	public void setTables(Map<String, TableInfo> tables) {
		this.tables = tables;
	}

	public static void main(String[] args) throws JDOMException {
		PdmReader r = new PdmReader();
		r.parse(PdmReader.class.getResourceAsStream("/client-3.2.xml"));
		LOG.info("P:{}",r.getTableInfo("PERSON_INFO"));
	}
}
