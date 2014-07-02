package cn.guoyukun.pdm2pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.guoyukun.pdm2pdf.model.Biz;
import cn.guoyukun.pdm2pdf.model.Domain;
import cn.guoyukun.pdm2pdf.model.TableTree;
import cn.guoyukun.pdm2pdf.model.json.JDomain;
import cn.guoyukun.pdm2pdf.pdm.PdmReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class Helper {
	// 日志对象
	private static final Logger LOG = LoggerFactory.getLogger(Helper.class);
	private static final Gson GSON = new Gson();

	public static String loadNotNullString(Properties props, String key){
		String value = props.getProperty(key);
		if(value==null){
			throw new NullPointerException(key+"键不能为空！");
		}
		return value;
	}
	
	public static String[] loadNotNullArray(Properties props, String key){
		String value = loadNotNullString(props, key);
		return value.split(",");
	}
	
	
	public static PdmReader parsePdm(String pdm) throws Exception{
		File pdmFile = new File(pdm);
		if (!pdmFile.exists()) {
			throw new FileNotFoundException(pdm + "不存在！");
		}
		FileInputStream is = new FileInputStream(pdmFile);
		// 解析pdm
		PdmReader r = new PdmReader();
		r.parse(is);
		is.close();
		return r;
	}

	public static Domain loadDomainConfig(String domainCode)
			throws FileNotFoundException {
		String config = "/domain/" + domainCode + ".json";
		LOG.info("加载业务域[{}]", config);
		InputStream is = Main.class.getResourceAsStream(config);
		if (is == null) {
			throw new FileNotFoundException(config + "文件不存在！");
		}
		JsonReader jr = new JsonReader(new InputStreamReader(is));
		JDomain jDomain = GSON.fromJson(jr, JDomain.class);
		Domain domain = new Domain();
		domain.setCode(domainCode);
		domain.setName(jDomain.getName());
		List<Biz> bizs = new ArrayList<Biz>(jDomain.getBizs().size());
		for (String bizCode : jDomain.getBizs()) {
			Biz biz = loadBizConfig(bizCode);
			bizs.add(biz);
		}
		domain.setBizs(bizs);
		return domain;
	}

	private static Biz loadBizConfig(String bizCode)
			throws FileNotFoundException {
		String config = "/domain/bizs/" + bizCode + ".json";
		LOG.info("加载业务[{}]", config);
		InputStream is = Main.class.getResourceAsStream(config);
		if (is == null) {
			throw new FileNotFoundException(config + "文件不存在！");
		}
		JsonReader jr = new JsonReader(new InputStreamReader(is));
		Biz biz = GSON.fromJson(jr, Biz.class);
		// List<TableTree> tableTrees = biz.getTableTrees();
		// 表关系直接写到rel里，不手动计算
		// calcTableRel(tableTrees, 0);
		return biz;
	}

	public static int calcTableCount(List<TableTree> trees) {
		if (trees == null) {
			return 0;
		}
		int i = trees.size();
		for (TableTree tree : trees) {
			i = i + calcTableCount(tree.getSubTables());
		}
		return i;
	}

	public static int calcTableCount(TableTree tree) {
		if (tree == null) {
			return 0;
		}
		return 1 + calcTableCount(tree.getSubTables());
	}

	@SuppressWarnings("unused")
	private static void calcTableRel(List<TableTree> tableTrees, int level) {
		final String[] TITLE = new String[] { "主表", "子表", "2层子表", "3层子表",
				"4层子表", "5层子表" };
		if (tableTrees == null) {
			return;
		}
		for (TableTree tree : tableTrees) {
			String rel = TITLE[level];
			if (tree.getRel() == null) {
				tree.setRel(rel);
			} else {
				tree.setRel(rel + " (" + tree.getRel() + ")");
			}
			calcTableRel(tree.getSubTables(), level + 1);
		}
	}
}
