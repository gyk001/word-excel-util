package cn.guoyukun.word.util;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;

/**
 * Word文件(docx)操作工具
 * 
 * @author GuoYukun 
 * @version 2014-06-22
 */
public class DocUtil {

	/**
	 * 读取并打印word文件内的所有表格
	 * @throws IOException
	 */
	static void docTables() throws IOException {
		FileInputStream in = new FileInputStream("/Volumes/File/x.doc");
		HWPFDocument hwpf = new HWPFDocument(in);
		Range range = hwpf.getRange();// 得到文档的读取范围
		TableIterator it = new TableIterator(range);
		// 迭代文档中的表格
		while (it.hasNext()) {
			Table tb = (Table) it.next();
			// 迭代行，默认从0开始
			for (int i = 0; i < tb.numRows(); i++) {
				TableRow tr = tb.getRow(i);
				// 迭代列，默认从0开始
				for (int j = 0; j < tr.numCells(); j++) {
					TableCell td = tr.getCell(j);// 取得单元格
					// 取得单元格的内容
					for (int k = 0; k < td.numParagraphs(); k++) {
						Paragraph para = td.getParagraph(k);
						String s = para.text();
						System.out.print(s);
					}
					System.out.print("\t");
				}
				System.out.print("\n");
			}
			System.out.print("====================TABLE====================\n");
		}
	}

}
