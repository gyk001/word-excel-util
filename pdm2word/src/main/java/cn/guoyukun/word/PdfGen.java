package cn.guoyukun.word;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGen {

	public static void main(String[] args) throws DocumentException,
			FileNotFoundException {
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("C:\\ITextTest.pdf"));

		document.open();
		Anchor anchorTarget = new Anchor("First page of the document.");
		anchorTarget.setName("BackToTop");
		Paragraph paragraph1 = new Paragraph();

		paragraph1.setSpacingBefore(50);

		paragraph1.add(anchorTarget);
		document.add(paragraph1);

		document.add(new Paragraph(
				"Some more text on the first page with different color and font type.",
				FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD,
						new CMYKColor(0, 255, 0, 0))));
		
		//document.add(chapter1);

		document.close();
	}
}
