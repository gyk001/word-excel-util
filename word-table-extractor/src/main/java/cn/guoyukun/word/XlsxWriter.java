package cn.guoyukun.word;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class XlsxWriter {

	/**
	 * 将所有表格分别作为一个sheet写入excel文件中，execel会被覆盖！！
	 * @param wbPath
	 * @param tables
	 * @throws IOException
	 */
	static void writeSheetsWithTables(String wbPath, Map<String,XWPFTable> tables) throws IOException{
		// TODO: 300 含义未明确，待查
		SXSSFWorkbook wb = new SXSSFWorkbook(300);
		// 写入表格数据
		writeSheetsWithTables(wb, tables);
		// 保存至excel文件
		FileOutputStream fileOut = new FileOutputStream(wbPath);
		wb.write(fileOut);
		fileOut.close();
		wb.dispose();
	}
	
	/**
	 * 将所有表格分别作为一个sheet写入工作簿中
	 * @param tables
	 * @throws IOException
	 */
	static void writeSheetsWithTables(SXSSFWorkbook wb, Map<String,XWPFTable> tables) throws IOException{
		for(Entry<String,XWPFTable> entry: tables.entrySet()){
			writeSheetWithTable(wb, entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * 用表格数据填充一个sheet
	 * @param wb excel工作簿对象
	 * @param name sheet名
	 * @param data 表格数据，此为从docx解析出来的表格对象
	 */
	static void writeSheetWithTable(SXSSFWorkbook wb, String name, XWPFTable data){
		SXSSFSheet sheet=null;
		try {
			sheet = (SXSSFSheet) wb.createSheet(name);
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			System.out.println("~~ "+name+"表Sheet页已存在!");
			e.printStackTrace();
			// 该sheet已经存在，忽略错误
			return ;
		}

		int rowIndex = 0;
		 for (XWPFTableRow dataRow : data.getRows()){
			 Row row = sheet.createRow(rowIndex++);
			 
	          List<XWPFTableCell> cells = dataRow.getTableCells();
	          for (int i = 0; i < cells.size(); i++){
	     		 Cell cell = row.createCell(i);
	              XWPFTableCell dataCell = cells.get(i);
	              String text = dataCell.getTextRecursively();
	              cell.setCellValue(text);
	          }
	      }		
	}
	
	
}
