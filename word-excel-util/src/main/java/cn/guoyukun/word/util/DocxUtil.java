package cn.guoyukun.word.util;

import java.util.Iterator;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;


/**
 * Word文件(docx)操作工具
 * 
 * @author GuoYukun 
 * @version 2014-06-22
 */
public class DocxUtil {

	/**
	 * 打印素有的Paragraph
	 * @param hwpf
	 */
	static void printParagraph(XWPFDocument hwpf) {
		Iterator<XWPFParagraph> it = hwpf.getParagraphsIterator();
		while (it.hasNext()) {
			XWPFParagraph paragraph = it.next();
			System.out.print("\n==========TABLE============\n");
			System.out.print(paragraph.getParagraphText());

		}
	}

	/** 
	 * 打印所有的表格
	 * @param tables
	 */
	static void printTables(List<XWPFTable> tables) {
		System.out.println("###########################################\n");
		for (XWPFTable table : tables) {
			System.out.print("==========TABLE============");
			List<XWPFTableRow> rows = table.getRows();
			for (XWPFTableRow row : rows) {
				List<XWPFTableCell> cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					System.out.print(cell.getText());
					System.out.print("\t");
				}
				System.out.print("\n");
			}
		}
	}

}
