package cn.guoyukun.pdm2pdf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.guoyukun.pdm2pdf.model.Biz;
import cn.guoyukun.pdm2pdf.model.Domain;
import cn.guoyukun.pdm2pdf.model.TableTree;
import cn.guoyukun.pdm2pdf.pdm.PdmReader;

public class Main {
	//日志对象
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		//加载配置
		Properties p = new Properties();
		p.load(Main.class.getResourceAsStream("/config.properties"));
		
		final String pdm = Helper.loadNotNullString(p, "pdm");
		LOG.info("加载PDM:{}",pdm);
		
		final String pdf= Helper.loadNotNullString(p, "pdf");
		LOG.info("目标PDF:{}",pdf);
		
		// 文档标题
		final String title=Helper.loadNotNullString(p, "title");
		LOG.info("文档标题:{}",title);
		
		// 生成域
		final String[] domains = Helper.loadNotNullArray(p,"domains");
		LOG.info("目标业务域：{}",Arrays.asList(domains));

		// 生成时间，不指定会使用当前日期
		String genDate = p.getProperty("date");
		if(genDate==null || genDate.isEmpty()){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			genDate = sdf.format(new Date());
		}
		
		// Pdm解析器
		PdmReader r = Helper.parsePdm(pdm);
		// Pdf生成器
		PdfGenerator g = new PdfGenerator();
		// 设置变量
		g.setTables(r.getTables()).setGenerateDate(genDate).setPdfTitle(title)
				.setVersion("V" + r.getVersion());
		
		//  打开文档，生成封面
		g.newPdf(pdf).openPdf()
				.addOutline().addCover();
		// 生成业务域内容
		for (String code : domains) {
			Domain domain = Helper.loadDomainConfig(code);
			g.addDomainContent(domain);
		}
		// 关闭文档
		g.closePdf();
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
