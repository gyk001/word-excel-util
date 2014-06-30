package cn.guoyukun.pdm2pdf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.guoyukun.pdm2pdf.model.Biz;
import cn.guoyukun.pdm2pdf.model.Domain;
import cn.guoyukun.pdm2pdf.model.TableInfo;
import cn.guoyukun.pdm2pdf.model.TableTree;
import cn.guoyukun.pdm2pdf.model.json.JDomain;
import cn.guoyukun.pdm2pdf.pdm.PdmReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.itextpdf.text.DocumentException;

public class Main {
	private static final Gson GSON = new Gson();
	//日志对象
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		String[] domains = new String[]{"MS"};
		
		final String pdm = "/client-3.3.3.pdm";
		String pdf= "/Users/Guo/Desktop/db.pdf";
		gen(pdm, pdf, domains);
	}
	
	public static void gen(String pdm , String pdf, String[] domainCodes) throws JDOMException, MalformedURLException, DocumentException, IOException{
		
		// 解析pdm
		PdmReader r = new PdmReader();
		r.parse(PdmReader.class.getResourceAsStream(pdm));
		//  
		Map<String,TableInfo> tables = r.getTables();
		
		Generator g = new Generator();
		g.setTables(tables).newPdf(pdf, "V"+r.getVersion()).addCover(r.getVersion());

		for(String code: domainCodes){
			Domain domain = loadDomainConfig(code);
			g.addDomainContent(domain);	
		}
		g.closePdf();
				
	}
	
	private static Domain loadDomainConfig(String domainCode) throws FileNotFoundException{
		String config = "/domain/"+domainCode+".json";
		LOG.info("加载业务域[{}]",config);
		InputStream is = Main.class.getResourceAsStream(config);
		if(is==null){
			throw new FileNotFoundException(config+"文件不存在！");
		}
		JsonReader jr = new JsonReader(new InputStreamReader(is));
		JDomain jDomain = GSON.fromJson(jr, JDomain.class);
		Domain domain = new Domain();
		domain.setCode(domainCode);
		domain.setName(jDomain.getName());
		List<Biz> bizs = new ArrayList<Biz>(jDomain.getBizs().size());
		for(String bizCode :jDomain.getBizs()){
			Biz biz = loadBizConfig(bizCode);
			bizs.add(biz);
		}
		domain.setBizs(bizs);
		return domain;
	}
	
	private static Biz loadBizConfig(String bizCode) throws FileNotFoundException{
		String config = "/domain/bizs/"+bizCode+".json";
		LOG.info("加载业务[{}]",config);
		InputStream is = Main.class.getResourceAsStream(config);
		if(is==null){
			throw new FileNotFoundException(config+"文件不存在！");
		}
		JsonReader jr = new JsonReader(new InputStreamReader(is));
		Biz biz =  GSON.fromJson(jr, Biz.class);
		List<TableTree> tableTrees = biz.getTableTrees();
		calcTableRel(tableTrees, 0);
		return biz;
	}
	
	
	private static void calcTableRel(List<TableTree> tableTrees, int level){
		final String[] TITLE=new String[]{"主表","子表","2层子表","3层子表","4层子表","5层子表"};
		if(tableTrees==null){
			return ;
		}
		for(TableTree tree: tableTrees){
			String rel = TITLE[level];
			tree.setRel(rel+" ("+tree.getRel()+")");
			calcTableRel(tree.getSubTables(), level+1);
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
	private  static Domain testDomain(){
		Domain domain = new Domain();
		domain.setCode("MS");
		domain.setName("医疗服务域");
		
		final Biz biz = new Biz();
		biz.setCode("b1");
		biz.setName("门诊业务");
		
		

		final TableTree x = new TableTree("T_MS_PATIE_INHS_INDIAG");
		final TableTree xx = new TableTree("T_MS_PATIE_INHS_TS_SAMP");
		
		final TableTree treeI = new TableTree("T_MS_PATIE_INHS");
		treeI.setSubTables(new ArrayList<TableTree>(){{
			add(x);
			add(xx);
		}});
		
		
		final TableTree treeO = new TableTree("T_MS_OUTPATEME");

		treeO.setSubTables(new ArrayList<TableTree>(){{
			add(treeI);
			
		}});
		
		biz.setTableTrees(new ArrayList<TableTree>(){{
			;add(treeO);
			;add(treeO);
			}});
		
		final Biz biz2 = new Biz();
		biz2.setCode("b2");
		biz2.setName("住院业务");
		
		domain.setBizs(new ArrayList(){{add(biz);add(biz);add(biz2);}});
		return domain;
	}

}
