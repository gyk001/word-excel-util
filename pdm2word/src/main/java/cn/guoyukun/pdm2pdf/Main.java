package cn.guoyukun.pdm2pdf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.guoyukun.pdm2pdf.model.Biz;
import cn.guoyukun.pdm2pdf.model.Domain;
import cn.guoyukun.pdm2pdf.model.TableTree;

public class Main {
	//日志对象
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		//加载配置
		Properties p = new Properties();
		p.load(Main.class.getResourceAsStream("/config.properties"));
		
		
		final String d = p.getProperty("domains");
		if(d==null || d.isEmpty()){
			throw new Exception("必须指定要生成的域");
		}
		String[] domains = d.split(",");
		final String pdm = p.getProperty("pdm");
		final String pdf= p.getProperty("pdf");
		
		LOG.info("加载PDM:{}",pdm);
		LOG.info("目标PDF:{}",pdf);
		LOG.info("目标业务域：{}",Arrays.asList(domains));
		// 生成文档
		Helper.gen(pdm, pdf, domains);
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "serial", "unchecked", "unused" })
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
