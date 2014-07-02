package cn.guoyukun.word;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordAnanly {
	private static final List<String> INDENT = new ArrayList<String>();
	static {
		String indent = "";
		for (int i = 0; i < 10000; i++) {
			INDENT.add(indent);
			indent = indent + "\t";
		}
	}
	// 日志对象
	private static final Logger LOG = LoggerFactory.getLogger(WordAnanly.class);

	public static void main(String[] args) {
		String tpl = "/Volumes/File/临时文档/word-test.docx";
		try {
			// 获得word的pack对象
			OPCPackage pack = POIXMLDocument.openPackage(tpl);
			// 获得XWPFDocument对象
			XWPFDocument doc = new XWPFDocument(pack);
			// 输出doc body中包含的元素个数
			LOG.info("doc.getBodyElements().size() = {}", doc.getBodyElements()
					.size());
			// 输出pack中包含的的part个数,这里就是word文档的页数
			LOG.info("pack.getParts().size() = {}", pack.getParts().size());
			//analyseBodyElements(doc.getBodyElements());
			analyseParagraphS(doc.getParagraphs(),0);
			pack.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	private static void analyseBodyElements(List<IBodyElement> elementList) {
		analyseBodyElements(elementList, 0);
	}

	private static void analyseBodyElements(List<IBodyElement> elementList,
			int level) {
		LOG.info("{}=================={}========================", INDENT.get(level));
		for (IBodyElement bodyElement : elementList) {
			analyseBodyElement(bodyElement, level + 1);
		}
		LOG.info("{}==========================================\n", INDENT.get(level));
	}

	private static void analyseBodyElement(IBodyElement be, int level) {
		LOG.info("{}<IBodyElement类型:{}>", INDENT.get(level), be.getElementType());
		LOG.info("{}<getPartType类型:{}>", INDENT.get(level),be.getPartType());
		IBody body = be.getBody();
		//analyseBodyElements(body.getBodyElements(), level + 1);
		List<XWPFParagraph> paragraphs = body.getParagraphs();
		analyseParagraphS(paragraphs, level+1);
	}
	
	private static void analyseParagraphS(List<XWPFParagraph> paragraphs, int level){
		LOG.info("{}------------------", INDENT.get(level));
		for(XWPFParagraph p : paragraphs){
			analyseParagraph(p, level+1);
		}
		LOG.info("{}------------------\n", INDENT.get(level));
	}
	
	private static void analyseParagraph(XWPFParagraph p, int level){
		analyseRuns(p.getRuns());
	}
	
	private static void analyseRuns(List<XWPFRun> runs){
		for(XWPFRun run:runs){
			analyseRun(run);
		}
	}
	
	private static void analyseRun(XWPFRun run){
		List<CTText> texts = run.getCTR().getTList();
		for(CTText text : texts){
			LOG.info("-> {}",text.getStringValue());
		}
	}
}
