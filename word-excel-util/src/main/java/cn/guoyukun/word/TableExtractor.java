package cn.guoyukun.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.IRunElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFSDT;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;

import cn.guoyukun.word.util.PropertiesUtil;

public class TableExtractor {

	// 从word文档里读取的所有表格
	static Map<String, XWPFTable> tables = new LinkedHashMap<String, XWPFTable>();
	// 读取到的标题（包含文本？）栈，帮助取到表格的名字（读到表格时的栈顶标题离表格最近，认为是表格的名字）
	static Stack<String> title = new Stack<String>();
	// 表名的中英文对照
	static Map<String,String> tableNames = new HashMap<String,String>();

	public static void main(String[] args) throws XmlException,
			OpenXML4JException, IOException {
		//从配置文件加载表名中英文对照
		tableNames.putAll(PropertiesUtil.loadToMap("/tables.properties"));
		// 文件或者文件夹路径
		String path = "/Volumes/File/wordtable/";
		File file = new File(path);
		if (file.isDirectory()) {
			docxFolderTable(path);
		} else {
			docxTable(path);
		}
		// 移除表清单
		tables.remove("表清单");
		// 写入excel
		XlsxWriter.writeSheetsWithTables("/Volumes/File/wordtable/outlining_collapsed.xlsx",tables);
	}

	/**
	 * 解析文件夹里的所有的docx文档里的表格，放入tables变量内
	 * 
	 * @param folder
	 * @throws IOException
	 */
	static void docxFolderTable(String folder) throws IOException {
		File file = new File(folder);
		if (file.isDirectory()) {
			String[] docxs = file.list(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return (name.endsWith(".docx"));
				}
			});
			for (String docx : docxs) {
				docxTable(file.getAbsolutePath() + File.separator + docx);
			}
		}
	}

	/**
	 * 解析docxFile文件里的表格，放入tables变量
	 * 
	 * @param docxFile
	 * @throws IOException
	 */
	static void docxTable(String docxFile) throws IOException {
		System.out.println("\n================================");
		System.out.println(docxFile);
		FileInputStream in = new FileInputStream(docxFile);
		XWPFDocument hwpf = new XWPFDocument(in);
		StringBuffer text = new StringBuffer();
		for (IBodyElement e : hwpf.getBodyElements()) {
			appendBodyElementText(text, e);
			text.append('\n');
		}
		// System.out.println(text);

	}

	 /**
	  * 解析word里所有内容
	  * @param text
	  * @param e
	  */
	static void appendBodyElementText(StringBuffer text, IBodyElement e) {
		if (e instanceof XWPFParagraph) {
			appendParagraphText(text, (XWPFParagraph) e);
		} else if (e instanceof XWPFTable) {
			appendTableText(text, (XWPFTable) e);
		} else if (e instanceof XWPFSDT) {
			text.append(((XWPFSDT) e).getContent().getText());
		}
	}

	/**
	 * 解析word里的table
	 * @param text
	 * @param table
	 */
	static void appendTableText(StringBuffer text, XWPFTable table) {
		// 取栈顶的Paragraph作为表名，去除章节标号( 型如:1.2.7)
		String tableName = title.pop().replaceFirst("\\d[\\\\.\\d]*", "");

		// 表格内容输入到text，（调试用）
		text.append("\n@@@@@@@@@@@@@@@@@@@@@@").append(tableName)
				.append("@@@@@@@@@@@@@@@@@@@@@@@@@@\n");
		for (XWPFTableRow row : table.getRows()) {
			List<XWPFTableCell> cells = row.getTableCells();
			for (int i = 0; i < cells.size(); i++) {
				XWPFTableCell cell = cells.get(i);
				text.append(cell.getTextRecursively());
				if (i < cells.size() - 1) {
					text.append("\t");
				}
			}
			text.append('\n');
		}
		text.append("\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n");

		// 如果表标题是表清单，从里面加载表名中英文对照
		if (tableName.equals("表清单")) {
			tableNames.putAll(loadTableNames(table));
		}
		// 取表的英文名
		String tableNameEn = (String) tableNames.get(tableName);
		if (tableNameEn == null) {
			tableNameEn = tableName;
		}
		System.out.println(">> " + tableName + " : " + tableNameEn);
		// 将表添加到结果map容器中
		tables.put(tableNameEn, table);

	}

	/**
	 * 解析所有的Paragraph（具体代表标题还是代表标题和内容没有测试）
	 * @param text
	 * @param paragraph
	 */
	static public void appendParagraphText(StringBuffer text,
			XWPFParagraph paragraph) {
		text.append("=========appendParagraphText=================");

		// try {
		//CTSectPr ctSectPr = null;
//		if (paragraph.getCTP().getPPr() != null) {
//			ctSectPr = paragraph.getCTP().getPPr().getSectPr();
//		}

		// XWPFHeaderFooterPolicy headerFooterPolicy = null;
		//
		// if (ctSectPr!=null) {
		// headerFooterPolicy = new XWPFHeaderFooterPolicy(document, ctSectPr);
		// extractHeaders(text, headerFooterPolicy);
		// }

		text.append("[").append(paragraph.getText()).append("]");
		
		// 添加到展里
		title.push(paragraph.getText());
		
		for (IRunElement run : paragraph.getRuns()) {
			text.append("<");

			text.append(run.toString());
			text.append(">");
			// if(run instanceof XWPFHyperlinkRun && fetchHyperlinks) {
			// XWPFHyperlink link =
			// ((XWPFHyperlinkRun)run).getHyperlink(document);
			// if(link != null)
			// text.append(" <" + link.getURL() + ">");
			// }
		}
		//
		// // Add comments
		// XWPFCommentsDecorator decorator = new
		// XWPFCommentsDecorator(paragraph, null);
		// String commentText = decorator.getCommentText();
		// if (commentText.length() > 0){
		// text.append(commentText).append('\n');
		// }
		//
		// // Do endnotes and footnotes
		// String footnameText = paragraph.getFootnoteText();
		// if(footnameText != null && footnameText.length() > 0) {
		// text.append(footnameText + '\n');
		// }

		// if (ctSectPr!=null) {
		// extractFooters(text, headerFooterPolicy);
		// }

		// } catch (IOException e) {
		// throw new POIXMLException(e);
		// } catch (XmlException e) {
		// throw new POIXMLException(e);
		// }
		text.append("#############==appendParagraphText=================########");
	}

	/**
	 * 从word文档里的表情单表格里加载表名和表的英文名对应关系
	 * @param table
	 * @return
	 */
	static Map<String,String> loadTableNames(XWPFTable table) {
		Map<String, String> names = new HashMap<String, String>();
		for (XWPFTableRow row : table.getRows()) {
			String n = row.getCell(0).getText();
			String v = row.getCell(2).getText();
			names.put(n, v);
		}
		return names;
	}
	

}
