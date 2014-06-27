package cn.guoyukun.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class PdmGenerator {

	public static void main(String[] args) throws IOException {
		String tpl = "/Volumes/File/临时文档/word-test.docx";
		String target = "/Users/Guo/Desktop/test-out.docx";
		try {
			// 获得word的pack对象
			OPCPackage pack = POIXMLDocument.openPackage(tpl);
			// 获得XWPFDocument对象
			XWPFDocument doc = new XWPFDocument(pack);
			// 输出doc body中包含的元素个数
			System.out.println(doc.getBodyElements().size());
			// 输出pack中包含的的part个数,这里就是word文档的页数
			System.out.println(pack.getParts().size());
			// 获得第一个段落对象
			XWPFParagraph paragraph = doc.getParagraphs().get(0);
			// 段落的格式,下面及个设置,将使新添加的文字向左对其,无缩进.
			paragraph.setIndentationLeft(0);
			paragraph.setIndentationHanging(0);
			paragraph.setAlignment(ParagraphAlignment.LEFT);
			paragraph.setWordWrap(true);
			System.out.println( paragraph.getStyle());
			// 在段落中新插入一个run,这里的run我理解就是一个word文档需要显示的个体,里面可以放文字,参数0代表在段落的最前面插入
			XWPFRun run = paragraph.insertNewRun(0);
			// 设置run内容
			run.setText("finish");
			run.setFontFamily("宋体");
			run.setBold(true);
			// 因为不支持直接保存会原有对象,或者我不会,只能新保存一个文件,然后将原来的删除,将新的文件重命名回原文件名.
			File newFile = new File(target);
			FileOutputStream fos = new FileOutputStream(newFile);
			doc.write(fos);
			fos.flush();
			fos.close();
			pack.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
}
