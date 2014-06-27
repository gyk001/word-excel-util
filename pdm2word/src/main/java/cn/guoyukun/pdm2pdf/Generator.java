package cn.guoyukun.pdm2pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPHeaderCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class Generator {

	public void generator(String pdm, String pdf) throws MalformedURLException,
			DocumentException, IOException {
		newPdf(pdf);
	}

	private void setHeaderFooter(PdfWriter writer) throws DocumentException, IOException {
		//HeaderFooter headerFooter = new HeaderFooter(this);
		//更改事件，瞬间变身 第几页/共几页 模式。
		PdfReportM1HeaderFooter headerFooter = new PdfReportM1HeaderFooter();//就是上面那个类
		headerFooter.setHeader("中科软科技股份有限公司");
		// 跳过封皮
		headerFooter.setPageOffset(-1);
		headerFooter.setPresentFontSize(10);
		writer.setBoxSize("art",PageSize.A4);
		writer.setPageEvent(headerFooter);
		}
	
	public Document newPdf(String pdf) throws DocumentException,
			MalformedURLException, IOException {
		// Listing 1. Instantiation of document object
		Document document = new Document(PageSize.A4.rotate(), 50, 50, 50, 50);
		// Listing 2. Creation of PdfWriter object
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(pdf));
		// 页眉页脚
		setHeaderFooter(writer);
		writer.setFullCompression();
		writer.setPdfVersion(PdfWriter.VERSION_1_4);
		
		//TODO: 设置行间距，没试出效果
		writer.setInitialLeading(-100f);
		document.open();
		
				
		buildCover(document);
		buildContent(document);
		// buildContent(document);
		document.close();
		writer.close();
		return document;
	}

	/**
	 * 封皮
	 * 
	 * @param document
	 * @throws DocumentException
	 */
	private void buildCover(Document document) throws DocumentException {
		// 封皮
		Anchor anchorTarget = new Anchor("中科软科技股份有限公司", Fonts.FONT_COVER);
		anchorTarget.setName("BackToTop");

		Paragraph paragraph1 = new Paragraph();

		paragraph1.setSpacingBefore(50);

		paragraph1.add(anchorTarget);
		paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(paragraph1);

		
		
		document.add(new Paragraph("封皮！！！", Fonts.FONT_TITILE1));
		document.add(new Paragraph("V 3.1", Fonts.FONT_FOOTER));
		
		

	}

	public void buildContent(Document document) throws MalformedURLException,
			IOException, DocumentException {
		// 域标题
		Paragraph pDomainTitle = new Paragraph("医疗服务域--住院业务接口表",
				Fonts.FONT_TITILE1);
		pDomainTitle.setAlignment(Paragraph.ALIGN_CENTER);
		Chapter cDomainChapter = new Chapter(pDomainTitle, 1);
		cDomainChapter.setNumberDepth(1);
		LineSeparator line = new LineSeparator(2f, 100, BaseColor.BLACK,
				Element.ALIGN_CENTER, -5f);
		pDomainTitle.add(line);

		// 表关系图
		Paragraph pTableImageTitle = new Paragraph("住院业务关系图",
				Fonts.FONT_TITILE2);
		Section spTableImage = cDomainChapter.addSection(pTableImageTitle);

		Image iTableImg = Image.getInstance(Generator.class
				.getResource("/table_rel/biz1.png"));
		iTableImg.scaleToFit(360f, 480f);
		spTableImage.add(iTableImg);

		// 表清单
		Paragraph pTableListTitle = new Paragraph("住院业务接口表清单",
				Fonts.FONT_TITILE2);
		Section sTableList = cDomainChapter.addSection(pTableListTitle);
		Paragraph someSectionText = new Paragraph("门急诊业务共包括26张业务信息接口表",
				Fonts.FONT_MAIN_TEXT);
		someSectionText.setSpacingBefore(10);
		someSectionText.setFirstLineIndent(30);
		sTableList.add(someSectionText);

		PdfPTable tTableList = buildTableList();
		
		sTableList.add(tTableList);

		//
		// // Listing 6. Creation of table object
		// PdfPTable t = new PdfPTable(3);
		//
		// t.setSpacingBefore(25);
		// t.setSpacingAfter(25);
		// PdfPCell c1 = new PdfPCell(new Phrase("Header1"));
		// t.addCell(c1);
		// PdfPCell c2 = new PdfPCell(new Phrase("Header2"));
		// t.addCell(c2);
		// PdfPCell c3 = new PdfPCell(new Phrase("Header3"));
		// t.addCell(c3);
		// t.addCell("1.1");
		// t.addCell("1.2");
		// t.addCell("1.3");
		// section1.add(t);

		// // Listing 7. Creation of list object
		// List l = new List(true, false, 10);
		// l.add(new ListItem("First item of list"));
		// l.add(new ListItem("Second item of list"));
		// section1.add(l);
		//
		// // Listing 8. Adding image to the main document
		//
		// Image image2 = Image.getInstance(ITextTest.class
		// .getResource("/IBMLogo.bmp"));
		// image2.scaleAbsolute(120f, 120f);
		// section1.add(image2);
		//
		// // Listing 9. Adding Anchor to the main document.
		// Paragraph title2 = new Paragraph("Using Anchor", FontFactory.getFont(
		// FontFactory.HELVETICA, 16, Font.BOLD, new CMYKColor(0, 255, 0,
		// 0)));
		// section1.add(title2);
		//
		// title2.setSpacingBefore(5000);
		// Anchor anchor2 = new Anchor("Back To Top");
		// anchor2.setReference("#BackToTop");
		//
		// section1.add(anchor2);

		// Listing 10. Addition of a chapter to the main document
		document.add(cDomainChapter);

	}
	
	
	private PdfPCell buildHeaderCell(String text){
		PdfPHeaderCell h1 = new PdfPHeaderCell();
		Phrase pHeader = new Phrase(text,Fonts.FONT_TITILE3);
		//TODO: 居中，没试出效果
		h1.setHorizontalAlignment(PdfPHeaderCell.ALIGN_CENTER);
		h1.setBackgroundColor(BaseColor.GRAY);
		h1.addElement(pHeader);
		return h1;
	}
	
	private PdfPCell buildCell(String text){
		PdfPCell cell = new PdfPCell();
		Phrase pText = new Phrase(text,Fonts.FONT_MAIN_TEXT);
		cell.setHorizontalAlignment(PdfPHeaderCell.ALIGN_LEFT);
		cell.addElement(pText);
		return cell;
	}
	
	public PdfPTable buildTableList() throws DocumentException{
		PdfPTable t = new PdfPTable(4);
		// 宽度百分比，相对于父容器
		t.setWidthPercentage(98);
		// 各列宽度百分比
		t.setWidths(new int[]{30,30,10,30});
		// 上下间隙
		t.setSpacingBefore(25);
		t.setSpacingAfter(25);
		// 设置标题行有几行，跨页时会再生成标题行
		t.setHeaderRows(1);
		/*如果出现某些行中的文本非常大， 
	    那么iText将按照“行优先”的方式对表格进行分页处理， 
	    所谓“行优先”是说：当遇到无法在当前页显示完整的一行时， 
	    该行将被放到下一页进行显示，而只有当一整业都无法显示完此行时， 
	    iText才会将此行拆开显示在两页中。如果不想使用“行优先”的方式， 
	    而是想采用“页优先”方式（保证填满当前页面的前提下，决定是否需要分拆行）显示， 
	    可使用方法setSplitLate(false)。 
	    */  
	    t.setSplitLate(true);  
	    t.setSplitRows(false); 
		// 表头
		t.addCell(buildHeaderCell("表中文名"));
		t.addCell(buildHeaderCell("表英文名"));
		t.addCell(buildHeaderCell("表关系"));
		t.addCell(buildHeaderCell("表描述"));
		// 表内容
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("主表"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("子表 1..n"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("2层子表 1..n"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("3层子表 1..n"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("主表"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("子表 1..n"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("2层子表 1..n"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("3层子表 1..n"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("主表"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("子表 1..n"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("2层子表 1..n"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("3层子表 1..n"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("主表"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("子表 1..n"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("2层子表 1..n"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("3层子表 1..n"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("主表"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("子表 1..n"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("2层子表 1..n"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		
		t.addCell(buildCell("住院诊疗实验室检验标本记录表"));
		t.addCell(buildCell("T_MS_PATIE_INHS_TS_SAMP"));
		t.addCell(buildCell("3层子表 1..n"));
		t.addCell(buildCell("记录住院诊疗实验室检验标本信息"));
		
		return t;
	}

	public void buildContent1(Document document) throws MalformedURLException,
			IOException, DocumentException {

		// // Listing 3. Creation of paragraph object
		// Anchor anchorTarget = new Anchor("First page of the document.");
		// anchorTarget.setName("BackToTop");
		// Paragraph paragraph1 = new Paragraph();
		//
		// paragraph1.setSpacingBefore(50);
		//
		// paragraph1.add(anchorTarget);
		// document.add(paragraph1);
		//
		// document.add(new Paragraph(
		// "Some more text on the first page with different color and font type.",
		// FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD,
		// new CMYKColor(0, 255, 0, 0))));

		// Section section1 = addParagraph(document);

		Font font = Fonts.FONT_TITILE2;
		// 创建段落
		// BaseFont bfChinese = BaseFont.createFont( "STSongStd-Light" ,
		// "UniGB-UCS2-H" , false );
		Paragraph domain = new Paragraph("第一章 医疗服务域——住院业务接口表", font);
		domain.setAlignment(Paragraph.ALIGN_CENTER);
		Chapter domainChapter = new Chapter(domain, 1);
		// 编号深度为0，即不显示编号
		domainChapter.setNumberDepth(0);

		// Listing 5. Creation of section object
		Paragraph title = new Paragraph("门急诊业务共包括26张业务信息接口表", font);

		Section section1 = domainChapter.addSection(title);
		Paragraph someSectionText = new Paragraph(
				"This text comes as part of section 1 of chapter 1.");
		section1.add(someSectionText);
		someSectionText = new Paragraph("Following is a 3 X 2 table.");
		section1.add(someSectionText);
		document.add(domain);
		document.add(domainChapter);

		// // Listing 6. Creation of table object
		// PdfPTable t = new PdfPTable(3);
		//
		// t.setSpacingBefore(25);
		// t.setSpacingAfter(25);
		// PdfPCell c1 = new PdfPCell(new Phrase("Header1"));
		// t.addCell(c1);
		// PdfPCell c2 = new PdfPCell(new Phrase("Header2"));
		// t.addCell(c2);
		// PdfPCell c3 = new PdfPCell(new Phrase("Header3"));
		// t.addCell(c3);
		// t.addCell("1.1");
		// t.addCell("1.2");
		// t.addCell("1.3");
		// section1.add(t);
		//
		// // Listing 7. Creation of list object
		// List l = new List(true, false, 10);
		// l.add(new ListItem("First item of list"));
		// l.add(new ListItem("Second item of list"));
		// section1.add(l);
		//
		// // Listing 8. Adding image to the main document
		//
		// Image image2 = Image.getInstance(Generator.class
		// .getResource("/IBMLogo.bmp"));
		// image2.scaleAbsolute(120f, 120f);
		// section1.add(image2);
		//
		// // Listing 9. Adding Anchor to the main document.
		// Paragraph title2 = new Paragraph("Using Anchor", FontFactory.getFont(
		// FontFactory.HELVETICA, 16, Font.BOLD, new CMYKColor(0, 255, 0,
		// 0)));
		// section1.add(title2);
		//
		// title2.setSpacingBefore(5000);
		// Anchor anchor2 = new Anchor("Back To Top");
		// anchor2.setReference("#BackToTop");
		//
		// section1.add(anchor2);

		// Listing 10. Addition of a chapter to the main document
		// document.add(chapter1);

	}

	public Section addParagraph(Document document) throws DocumentException {
		// 创建段落
		Paragraph domain = new Paragraph("第 1 章	医疗服务域----住院业务接口表",
				FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC,
						new CMYKColor(0, 255, 255, 17)));
		Chapter domainChapter = new Chapter(domain, 1);
		// 编号深度为0，即不显示编号
		domainChapter.setNumberDepth(0);

		// Listing 5. Creation of section object
		Paragraph title = new Paragraph("门急诊业务共包括26张业务信息接口表",
				FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD,
						new CMYKColor(0, 255, 255, 17)));
		Section section1 = domainChapter.addSection(title);
		Paragraph someSectionText = new Paragraph(
				"This text comes as part of section 1 of chapter 1.");
		section1.add(someSectionText);
		someSectionText = new Paragraph("Following is a 3 X 2 table.");
		section1.add(someSectionText);
		document.add(domain);
		return section1;
	}
}
