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
	private int chapterIndex = 1;
	
	public void generator(String pdm, String pdf) throws MalformedURLException,
			DocumentException, IOException {
		newPdf(pdf);
	}

	private void setHeaderFooter(PdfWriter writer) throws DocumentException, IOException {
		// 更改事件，瞬间变身 第几页/共几页 模式。
		PdfReportM1HeaderFooter headerFooter = new PdfReportM1HeaderFooter();// 就是上面那个类
		headerFooter.setHeader("中科软科技股份有限公司");
		// 跳过封皮
		headerFooter.setPageOffset(-1);
		headerFooter.setPresentFontSize(10);
		writer.setBoxSize("art", PageSize.A4);
		writer.setPageEvent(headerFooter);
	}
	
	public Document newPdf(String pdf) throws DocumentException,
			MalformedURLException, IOException {
		// 横版A4
		Document document = new Document(PageSize.A4.rotate(), 50, 50, 50, 50);
		// 
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

	
	public void buildContent(Document document) throws MalformedURLException, IOException, DocumentException{
		buildDomainContent1(document);
		// TODO: 测试
		buildDomainContent1(document);
		buildDomainContent1(document);
	}
	
	/**
	 * 构建一个业务的内容
	 * @param cParentChapter
	 * @throws DocumentException
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public void buildBiz(Section parentSection) throws DocumentException, MalformedURLException, IOException{
		
		// ============ 业务开始 ==============
		// 业务标题
		Paragraph pBiz = new Paragraph("住院业务",
						Fonts.FONT_TITILE2);
		// 业务章节
		Section sBiz = parentSection.addSection(pBiz);
		//sBiz.setTriggerNewPage(true);

		// 表关系图标题
		Paragraph pTableImageTitle = new Paragraph("住院业务关系图",
				Fonts.FONT_TITILE2);
		// 表关系图章节
		Section sTableImage = sBiz.addSection(pTableImageTitle);
		// 表关系图
		Image iTableImg = Image.getInstance(Generator.class
				.getResource("/table_rel/biz1.png"));
		iTableImg.setAlt("说明！！");
		iTableImg.scaleToFit(360f, 480f);
		//iTableImg.scaleAbsolute(360f, 360f);
		//iTableImg.setAlignment(Image.LEFT);
		sTableImage.add(iTableImg);
		
		// 表清单章节
		buildTableListSection(sBiz);
		
	}
	
	/**
	 * 表清单章节
	 * @param cParentChapter
	 * @throws DocumentException
	 */
	public void buildTableListSection(Section parentSection) throws DocumentException{
		// 表清单标题
		Paragraph pTableListTitle = new Paragraph("住院业务接口表清单",
				Fonts.FONT_TITILE2);
		// 表清单章节
		Section sTableList = parentSection.addSection(pTableListTitle);
		// 表清单说明
		Paragraph someSectionText = new Paragraph("门急诊业务共包括26张业务信息接口表",
				Fonts.FONT_MAIN_TEXT);
		someSectionText.setSpacingBefore(10);
		someSectionText.setFirstLineIndent(30);
		sTableList.add(someSectionText);
		// 表清单表格
		PdfPTable tTableList = buildTableList();
		sTableList.add(tTableList);		
	}
	
	/**
	 * 构建业务域内容
	 * @param document
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void buildDomainContent1(Document document) throws MalformedURLException,
			IOException, DocumentException {
		String domainTitle = "医疗服务域";
		// 域标题
		Paragraph pDomainTitle = new Paragraph(domainTitle,
				Fonts.FONT_TITILE1);
		pDomainTitle.setAlignment(Paragraph.ALIGN_CENTER);
		// 章节
		Chapter cDomainChapter = new Chapter(pDomainTitle, chapterIndex++);
		// 编号深度
		cDomainChapter.setNumberDepth(1);
		// 分割线
		LineSeparator line = new LineSeparator(2f, 100, BaseColor.BLACK,
				Element.ALIGN_CENTER, -5f);
		pDomainTitle.add(line);
	
		// 构建业务内容
		buildBiz(cDomainChapter);
		// TODO: 测试！！
		buildBiz(cDomainChapter);
		buildBiz(cDomainChapter);

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
