package cn.guoyukun.pdm2pdf.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

public class Fonts {
	public static BaseFont BF_SimSun = SimSunBaseFont.SimSun;
	// 正文字体,12号、正常
	public static Font FONT_MAIN_TEXT = new Font(BF_SimSun, 12, Font.NORMAL);
	// 正文字体,12号、正常、红色
	public static Font FONT_MAIN_TEXT_RED = new Font(BF_SimSun, 12, Font.NORMAL, BaseColor.RED);
	// 一级标题字体,16号、加粗
	public static Font FONT_TITILE1 = new Font(BF_SimSun, 16, Font.BOLD);
	// 二级标题字体,14号、加粗
	public static Font FONT_TITILE2 = new Font(BF_SimSun, 14, Font.BOLD);
	// 三级标题字体,12号、加粗
	public static Font FONT_TITILE3 = new Font(BF_SimSun, 12, Font.BOLD);
	// 封面标题字体,22号、加粗
	public static Font FONT_COVER_TITLE = new Font(BF_SimSun, 35, Font.BOLD);
	
	public static Font FONT_COVER_SUBTITLE = new Font(BF_SimSun, 20, Font.NORMAL);

	// 表格表头行字体,12号、加粗
	public static Font FONT_TABLE_HEADER_ROW = FONT_TITILE3;
	// 页脚字体,10号、正常
	public static Font FONT_FOOTER = new Font(BF_SimSun, 10, Font.NORMAL);
	
	final static class SimSunBaseFont {
		static BaseFont SimSun;
		static {
			try {
				SimSun = BaseFont.createFont("/font/SimSun.ttf",
						BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
				;
			} catch (Exception e) {

			}
		}
	}
}
