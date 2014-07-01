package cn.guoyukun.pdm2pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.guoyukun.pdm2pdf.model.Biz;
import cn.guoyukun.pdm2pdf.model.ColInfo;
import cn.guoyukun.pdm2pdf.model.Domain;
import cn.guoyukun.pdm2pdf.model.TableInfo;
import cn.guoyukun.pdm2pdf.model.TableTree;
import cn.guoyukun.pdm2pdf.pdf.Fonts;
import cn.guoyukun.pdm2pdf.pdf.PdfReportM1HeaderFooter;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPHeaderCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class PdfGenerator {
	private static final String AUTHOR = "中科软科技股份有限公司";
	// 日志对象
	private static final Logger LOG = LoggerFactory
			.getLogger(PdfGenerator.class);
	private Document document;
	private PdfWriter writer;
	// 章节索引
	private int chapterIndex = 1;
	// 表数据
	private Map<String, TableInfo> tables;
	// 文档标题，封面上的
	private String pdfTitle;
	// 版本
	private String version;
	// 生成日期
	private String generateDate;
	
	public String getVersion() {
		return version;
	}

	public PdfGenerator setVersion(String version) {
		this.version = version;
		return this;
	}

	public String getPdfTitle() {
		return pdfTitle;
	}
	
	public PdfGenerator setPdfTitle(String pdfTitle) {
		this.pdfTitle = pdfTitle;
		return this;
	}
	
	public String getGenerateDate() {
		return generateDate;
	}

	public PdfGenerator setGenerateDate(String generateDate) {
		this.generateDate = generateDate;
		return this;
	}

	private void setHeaderFooter(PdfWriter writer, String left, String right)
			throws DocumentException, IOException {
		// 第几页/共几页 模式。
		PdfReportM1HeaderFooter headerFooter = new PdfReportM1HeaderFooter();// 就是上面那个类
		headerFooter.setHeader(left);
		headerFooter.setHeaderRight(right);
		// 跳过封皮
		headerFooter.setPageOffset(-1);
		headerFooter.setPresentFontSize(10);
		writer.setBoxSize("art", PageSize.A4);
		writer.setPageEvent(headerFooter);
	}

	public PdfGenerator newPdf(String pdf)
			throws DocumentException, MalformedURLException, IOException {
		// 横版A4
		document = new Document(PageSize.A4.rotate(), 50, 50, 50, 50);
		//
		writer = PdfWriter.getInstance(document, new FileOutputStream(pdf));
		// //1，设置此PDF文件的权限，只有写上的是答应的。这里只答应打印，读取和保存，不答应修改等。
		// int intPermissions =
		// PdfWriter.ALLOW_PRINTING|PdfWriter.ALLOW_SCREENREADERS;
		// //2，若要实现其他权限如修改等 则需要写进密码，这里是设置密码加密标准或加密类型。
		// int intEncryptionType = PdfEncryption.AES_128;
		// //3，要是用这个方法需要引进一个jar包（bcprov-jdk15-137.jar）。第一个参数：打开时需要的密码；第二个参数：实用其他其他权限时使用的密码；第三个参数：可使用的权限；第四个参数：密码类型
		// writer.setEncryption(null, "hello".getBytes(),
		// intPermissions,intEncryptionType);

		writer.setPdfVersion(PdfWriter.VERSION_1_7);
		writer.setFullCompression();
		// 保证图片不会漂移
		writer.setStrictImageSequence(true);
		// TOC
		writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);
		// 页眉页脚
		setHeaderFooter(writer, AUTHOR, version);
		// TODO: 设置行间距，没试出效果
		writer.setInitialLeading(-100f);

		document.addTitle(this.pdfTitle + " " + this.version);
		document.addAuthor(AUTHOR);
		document.addCreationDate();
		document.addSubject(this.pdfTitle);
		document.addCreator(AUTHOR);
		writer.setTagged();
		return this;
	}

	public PdfGenerator openPdf() {
		document.open();
		return this;
	}

	public PdfGenerator addOutline() {
//		PdfStructureTreeRoot root = writer.getStructureTreeRoot();
//		root.mapRole(new PdfName("chapter"), PdfName.SECT);
//		root.mapRole(new PdfName("title"), PdfName.H);
//		root.mapRole(new PdfName("para"), PdfName.P);
//		PdfStructureElement top = new PdfStructureElement(root, new PdfName(
//				"chapter"));
//		//SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
//		List<PdfStructureElement> elements = new ArrayList<PdfStructureElement>();
//		
//		elements.add(new PdfStructureElement(top, new PdfName("title")));
//		
		
//		
//		parser.parse(new InputSource(new FileInputStream(RESOURCE)),
//				new StructureParser(top, elements));
//		parser.parse(new InputSource(new FileInputStream(RESOURCE)),
//				new ContentParser(document, writer, elements));
//		// PdfOutline root = writer.getRootOutline();
		//
		// PdfOutline link;
		// String title;
		// for(int i=0;i<100;i++){
		// PdfOutline movieBookmark = new PdfOutline(root,
		// new PdfDestination(
		// PdfDestination.FITH, writer.getVerticalPosition(true)),
		// "title-"+i, true);
		// movieBookmark.setStyle(Font.BOLD);
		// }
		// // for (Movie movie : movies) {
		// // title = movie.getMovieTitle();
		// // if ("3-Iron".equals(title))
		// // title = "\ube48\uc9d1";
		// // movieBookmark = new PdfOutline(root,
		// // new PdfDestination(
		// // PdfDestination.FITH, writer.getVerticalPosition(true)),
		// // title, true);
		// // movieBookmark.setStyle(Font.BOLD);
		// // link = new PdfOutline(movieBookmark,
		// // new PdfAction(String.format(RESOURCE, movie.getImdb())),
		// // "link to IMDB");
		// // link.setColor(BaseColor.BLUE);
		// // new PdfOutline(movieBookmark,
		// // PdfAction.javaScript(
		// // String.format(INFO, movie.getYear(), movie.getDuration()),
		// writer),
		// // "instant info");
		// // document.add(new Paragraph(movie.getMovieTitle()));
		// // document.add(PojoToElementFactory.getDirectorList(movie));
		// // document.add(PojoToElementFactory.getCountryList(movie));
		// // }
		return this;
	}

	public void closePdf() {
		document.close();
		writer.close();
	}

	/**
	 * 封皮
	 * 
	 * @throws DocumentException
	 */
	public PdfGenerator addCover() throws DocumentException {

		Paragraph title = new Paragraph(this.pdfTitle, Fonts.FONT_COVER_TITLE);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		title.setSpacingBefore(100);
		document.add(title);

		
		Paragraph pVersion = new Paragraph("版本：" + version, Fonts.FONT_TITILE1);
		pVersion.setAlignment(Paragraph.ALIGN_CENTER);
		pVersion.setSpacingBefore(90f);
		pVersion.setSpacingAfter(10f);
		document.add(pVersion);

		Paragraph pDate = new Paragraph( generateDate, Fonts.FONT_TITILE1);
		pDate.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(pDate);
		
		
		// 封皮
		Anchor anchorTarget = new Anchor(AUTHOR, Fonts.FONT_COVER_SUBTITLE);
		anchorTarget.setName("BackToTop");

		Paragraph paragraph1 = new Paragraph();

		paragraph1.setSpacingBefore(220);

		paragraph1.add(anchorTarget);
		paragraph1.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph1);

		return this;
	}

	/**
	 * 构建一个业务的内容
	 * 
	 * @param cParentChapter
	 * @throws DocumentException
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public void addBiz(Section parentSection, Biz biz)
			throws DocumentException, MalformedURLException, IOException {

		String bizTitle = biz.getName();
		// 业务标题
		Paragraph pBiz = new Paragraph(bizTitle, Fonts.FONT_TITILE2);
		// 业务章节
		Section sBiz = parentSection.addSection(pBiz);
		if (biz.isHasImg()) {
			// 表关系章节
			buildTableRelationship(sBiz, biz);
		}
		// 表清单章节
		buildTableListSection(sBiz, biz);
		// 所有表结构
		buildAllTableDetail(sBiz, biz);
	}

	/**
	 * 所有表结构
	 * 
	 * @param parentSection
	 * @throws DocumentException
	 */
	public void buildAllTableDetail(Section parentSection, Biz biz)
			throws DocumentException {
		String title = biz.getName() + "接口表明细";
		// 明细标题
		Paragraph pTitle = new Paragraph(title, Fonts.FONT_TITILE2);

		// 表明细章节
		Section section = parentSection.addSection(pTitle);
		// 循环生成表树的结构
		List<TableTree> tableTrees = biz.getTableTrees();
		if (tableTrees == null) {
			return;
		}
		for (TableTree tableTree : tableTrees) {
			addTableTree(section, tableTree);
		}
	}

	// 生成一个表树得结构
	public void addTableTree(Section parentSection, TableTree tableTree)
			throws DocumentException {
		String code = tableTree.getCode();
		String title = tables.get(code).getName() + "表";
		// 表结构标题
		Paragraph pTitle = new Paragraph(title, Fonts.FONT_TITILE3);
		// 表结构章节
		Section section = parentSection.addSection(pTitle);
		// 生成表结构
		TableInfo tableInfo = tables.get(code);
		addTable(section, tableInfo);
		// 递归子表数
		List<TableTree> subs = tableTree.getSubTables();
		if (subs == null) {
			return;
		}
		for (TableTree sub : subs) {
			addTableTree(section, sub);
		}
	}

	public void addTable(Section section, TableInfo tableInfo)
			throws DocumentException {
		PdfPTable t = new PdfPTable(5);
		// 宽度百分比，相对于父容器
		t.setWidthPercentage(98);
		// 各列宽度百分比
		t.setWidths(new int[] { 30, 20, 12, 8, 30 });
		// 上下间隙
		t.setSpacingBefore(25);
		t.setSpacingAfter(25);
		// 设置标题行有几行，跨页时会再生成标题行
		t.setHeaderRows(1);
		/*
		 * 如果出现某些行中的文本非常大， 那么iText将按照“行优先”的方式对表格进行分页处理，
		 * 所谓“行优先”是说：当遇到无法在当前页显示完整的一行时， 该行将被放到下一页进行显示，而只有当一整业都无法显示完此行时，
		 * iText才会将此行拆开显示在两页中。如果不想使用“行优先”的方式，
		 * 而是想采用“页优先”方式（保证填满当前页面的前提下，决定是否需要分拆行）显示， 可使用方法setSplitLate(false)。
		 */
		t.setSplitLate(true);
		t.setSplitRows(false);
		// 表头
		t.addCell(buildHeaderCell("数据项"));
		t.addCell(buildHeaderCell("字段名"));
		t.addCell(buildHeaderCell("数据类型"));
		t.addCell(buildHeaderCell("填报要求"));
		t.addCell(buildHeaderCell("描述"));

		addColumns(t, tableInfo.getHeaderColumns());
		addColumns(t, tableInfo.getColumns());
		addColumns(t, tableInfo.getFooterColumns());
		section.add(t);
	}

	private void addCell(PdfPTable t, String text, boolean nullable) {
		if (text == null) {
			t.addCell("");
		} else {
			if(nullable){
				t.addCell(buildCell(text));	
			}else{
				t.addCell(buildCell(text, Fonts.FONT_MAIN_TEXT_RED));
			}
			
		}

	}

	public void addColumns(PdfPTable t, Map<String, ColInfo> cols) {
		if (cols != null) {
			for (Entry<String, ColInfo> entry : cols.entrySet()) {
				ColInfo colInfo = entry.getValue();
				if (colInfo == null) {
					LOG.warn("字段【{}】为空！！", entry.getKey());
					continue;
				}
				boolean nullable = colInfo.isNullable();
				
				addCell(t, colInfo.getName(), nullable);
				addCell(t, colInfo.getCode(), nullable);
				addCell(t, colInfo.getType(), nullable);
				addCell(t, nullable ? "可选" : "必填", nullable);
				addCell(t, colInfo.getDesc(), nullable);
			}
		}
	}

	/**
	 * 表关系章节
	 * 
	 * @param parentSection
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void buildTableRelationship(Section parentSection, Biz biz)
			throws MalformedURLException, IOException, DocumentException {
		String title = biz.getName() + "关系图";
		// 表关系图标题
		Paragraph pTableImageTitle = new Paragraph(title, Fonts.FONT_TITILE2);
		// 表关系图章节
		Section sTableImage = parentSection.addSection(pTableImageTitle);

		String img = "/table_rel/" + biz.getCode() + ".png";
		LOG.info("加载关系图[{}]", img);
		// 表关系图
		Image iTableImg = Image.getInstance(
				PdfGenerator.class.getResource(img), true);
		iTableImg.scaleToFit(360f, 480f);
		// iTableImg.setAlignment(Image.MIDDLE);
		iTableImg.setIndentationLeft(20f);
		// iTableImg.setBorder(10);
		// iTableImg.setBorderColor(BaseColor.GREEN);
		// iTableImg.setBorderWidth(4);
		iTableImg.setAlignment(Image.LEFT);
		// iTableImg.enableBorderSide(Rectangle.BOX);
		iTableImg.setSpacingBefore(10f);
		iTableImg.setSpacingAfter(10);

		// iTableImg.setAbsolutePosition(
		// (PageSize.POSTCARD.getWidth()
		// - iTableImg.getScaledWidth()) / 2,
		// (PageSize.POSTCARD.getHeight()
		// -iTableImg.getScaledHeight()) / 2);
		//
		// writer.getDirectContent().addImage(iTableImg);

		sTableImage.add(iTableImg);
		// Chunk c = new Chunk("xxxxx", Fonts.FONT_TITILE1);
		// c.setNewPage();
		// sTableImage.add(c);
		// sTableImage.setComplete(true);
		// Chunk c = new Chunk();
		// LineDrawer ld = new LineDrawer(iTableImg);
		// sTableImage.add(ld);
	}

	/**
	 * 表清单章节
	 * 
	 * @param cParentChapter
	 * @throws DocumentException
	 */
	public void buildTableListSection(Section parentSection, Biz biz)
			throws DocumentException {
		String title = biz.getName() + "表清单";
		// 表清单标题
		Paragraph pTableListTitle = new Paragraph(title, Fonts.FONT_TITILE2);
		pTableListTitle.setExtraParagraphSpace(10);
		// 表清单章节
		Section sTableList = parentSection.addSection(pTableListTitle);

		int count = Helper.calcTableCount(biz.getTableTrees());
		// 表清单说明
		Paragraph someSectionText = new Paragraph(biz.getName() + "业务共包括"
				+ count + "张业务信息接口表", Fonts.FONT_MAIN_TEXT);
		someSectionText.setSpacingBefore(10);
		someSectionText.setFirstLineIndent(30);
		sTableList.add(someSectionText);
		// 表清单表格
		PdfPTable tTableList = buildTableList(sTableList, biz);
		sTableList.add(tTableList);

		pTableListTitle.setAlignment(Paragraph.ALIGN_LEFT);
	}

	/**
	 * 构建业务域内容
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void addDomainContent(Domain domain) throws MalformedURLException,
			IOException, DocumentException {
		String domainTitle = domain.getName();
		// 域标题
		Paragraph pDomainTitle = new Paragraph(domainTitle, Fonts.FONT_TITILE1);
		pDomainTitle.setAlignment(Paragraph.ALIGN_CENTER);
		// 章节
		Chapter cDomainChapter = new Chapter(domainTitle, chapterIndex++);
		// 编号深度
		cDomainChapter.setNumberDepth(0);
		// 必须添加一个Section。Chapter不会产生目录！！(会产生目录，只有一章的时候阅读器默认隐藏了而已！)
		// Section section = cDomainChapter.addSection(pDomainTitle);
		// 分割线
		LineSeparator line = new LineSeparator(2f, 100, BaseColor.BLACK,
				Element.ALIGN_CENTER, -5f);
		pDomainTitle.add(line);

		List<Biz> bizs = domain.getBizs();

		// 构建该域下面的业务内容
		if (bizs != null && !bizs.isEmpty()) {
			for (Biz biz : bizs) {
				addBiz(cDomainChapter, biz);
			}
		}

		document.add(cDomainChapter);

	}

	private PdfPCell buildHeaderCell(String text) {
		PdfPHeaderCell h1 = new PdfPHeaderCell();
		Phrase pHeader = new Phrase(text, Fonts.FONT_TITILE3);
		// TODO: 居中，没试出效果
		h1.setHorizontalAlignment(PdfPHeaderCell.ALIGN_CENTER);
		h1.setBackgroundColor(BaseColor.GRAY);
		h1.addElement(pHeader);
		return h1;
	}

	private PdfPCell buildCell(String text) {
		return buildCell(text, Fonts.FONT_MAIN_TEXT);
	}
	
	private PdfPCell buildCell(String text, Font font) {
		PdfPCell cell = new PdfPCell();
		Phrase pText = new Phrase(text, font );
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.addElement(pText);
		return cell;
	}

	private void addTableTrees(PdfPTable t, List<TableTree> trees) {
		if (trees == null) {
			return;
		}
		for (TableTree tree : trees) {
			addTableTree(t, tree);
		}
	}

	private void addTableTree(PdfPTable t, TableTree tree) {
		if (tree == null) {
			return;
		}
		String code = tree.getCode();
		LOG.info("添加清单:[{}]", code);
		String rel = tree.getRel();
		TableInfo tableInfo = tables.get(code);
		String name = tableInfo.getName();
		//String desc = tableInfo.getDesc();
		// 表内容
		t.addCell(buildCell(name));
		t.addCell(buildCell(code));
		t.addCell(buildCell(rel));
		//t.addCell(buildCell(desc));
		List<TableTree> subTrees = tree.getSubTables();
		addTableTrees(t, subTrees);

	}

	public PdfPTable buildTableList(Section parentSection, Biz biz)
			throws DocumentException {
		PdfPTable t = new PdfPTable(3);
		t.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		// 宽度百分比，相对于父容器
		t.setWidthPercentage(80);
		// 各列宽度百分比
		t.setWidths(new int[] { 45, 35, 20 });
		// 上下间隙
		t.setSpacingBefore(25);
		t.setSpacingAfter(25);
		// 设置标题行有几行，跨页时会再生成标题行
		t.setHeaderRows(1);
		/*
		 * 如果出现某些行中的文本非常大， 那么iText将按照“行优先”的方式对表格进行分页处理，
		 * 所谓“行优先”是说：当遇到无法在当前页显示完整的一行时， 该行将被放到下一页进行显示，而只有当一整业都无法显示完此行时，
		 * iText才会将此行拆开显示在两页中。如果不想使用“行优先”的方式，
		 * 而是想采用“页优先”方式（保证填满当前页面的前提下，决定是否需要分拆行）显示， 可使用方法setSplitLate(false)。
		 */
		t.setSplitLate(true);
		t.setSplitRows(false);
		// 表头
		t.addCell(buildHeaderCell("表中文名"));
		t.addCell(buildHeaderCell("表英文名"));
		t.addCell(buildHeaderCell("表关系"));
		//t.addCell(buildHeaderCell("表描述"));

		// TODO: 第一行不显示，所以填充一行占位！！
		t.addCell(buildCell(""));
		t.addCell(buildCell(""));
		t.addCell(buildCell(""));
		//t.addCell(buildCell(""));

		List<TableTree> tableTrees = biz.getTableTrees();
		addTableTrees(t, tableTrees);

		return t;
	}

	public Map<String, TableInfo> getTables() {
		return tables;
	}

	public PdfGenerator setTables(Map<String, TableInfo> tables) {
		this.tables = tables;
		return this;
	}

}
