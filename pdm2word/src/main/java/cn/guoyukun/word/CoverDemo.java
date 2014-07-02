package cn.guoyukun.word;

import com.itextpdf.text.pdf.codec.Base64.OutputStream;

public class CoverDemo {

	
	
	public static void main(String[] args) {
		
	}
	
	public static void createPDF(OutputStream os, Object rsHtwjxx,String path) {
//		  try {
//		   // 设置pdf文件页面边距顺序依次为左右上下
//		   Document document = new Document(PageSize.A4, 50, 50, 120, 80);
//		   PdfWriter writer = PdfWriter.getInstance(document, os);
//		   // 页眉
//		   Paragraph header = new PDFParagragh("", 1, 25);
//		   Paragraph headerh = new PDFParagragh("X X X X 公 司", 1, 25);
//		   header.setFont(FontFactory.getFont(FontFactory.HELVETICA, 12,
//		     Font.BOLD, new Color(255, 0, 0)));
//		   header.add(headerh);
//		   header.add("\n");
//		   header.add("\n");
//		   Paragraph header1 = new PDFParagragh("XXXX SOFT", 1, 10);
//		   header.add(header1);
//		   header.setLeading(10);
//		   header.add("\n");
//		   header.add(new PDFParagragh(rsHtwjxx.getWjbh(), 1, 10));
//		   HeaderFooter headerhead = new HeaderFooter(header, false);
//		   // headerhead.setBorder(Rectangle.NO_BORDER); //横线
//		   headerhead.setBorder(Rectangle.BOTTOM);
//		   headerhead.setBorderWidth(3);
//		   headerhead.setAlignment(1);
//		   headerhead.setBorderColor(new Color(50, 163, 163));
//		   document.setHeader(headerhead);
//		   // 页脚
//
//		   Paragraph headerfoot = new PDFParagragh("", 3, 10);
//		   Paragraph bao = new PDFParagragh("报：" + rsHtwjxx.getBaomc(), 3, 10);
//		   headerfoot.add(bao);
//		   Paragraph headerf = new PDFParagragh("发：" + rsHtwjxx.getFmc(), 3,
//		     10);
//		   headerfoot.add(headerf);
//		   Paragraph bei = new PDFParagragh("备：" + rsHtwjxx.getBeimc(), 3, 10);
//		   headerfoot.add(bei);
//		   HeaderFooter foot = new HeaderFooter(headerfoot, false);
//		   foot.setBorder(Rectangle.NO_BORDER); // 是否显示横线
//		   // foot.setBorder(Rectangle.BOTTOM);
//		   // 0是靠左 /1是居中/ 2是居右
//		   foot.setAlignment(0);
//		   foot.setBorderColor(Color.red);
//		   document.setFooter(foot);
//		   // 打开pdf
//		   document.open();
//		   String imlogopath = path;
//		   String imglinedownpath = path;
//		   if (ResourceBundleUtil.getResourceString("system_type").equals("0")) {
//		    // windows路径
//		    imlogopath += "\\images\\PDFimages\\PDF_logo.png";
//		    imglinedownpath += "\\images\\PDFimages\\PDF_lineBar.jpg";
//		   } else {
//		    // linux路径
//		    imlogopath += "/images/PDFimages/PDF_logo.png";
//		    imglinedownpath += "/images/PDFimages/PDF_lineBar.jpg";
//		   }
//		   // pdf文件上的logo图标
//		   Image imlogo = Image
//		     .getInstance(imlogopath);
//		   imlogo.setAlignment(3);
//		   imlogo.setAbsolutePosition(60, 700);
//		   imlogo.scaleAbsoluteHeight(35);
//		   imlogo.scaleAbsoluteWidth(40);
//		   // 正文
//		   // 设置段落对齐方式为:1-居中,2-居右,默认3-居左
//		   Paragraph headerwjbt = new PDFParagragh(rsHtwjxx.getWjbt(), 1, 20);
//		   Paragraph headerchm = new PDFParagragh(rsHtwjxx.getChm() + ":", 3,
//		     10);
//		   headerchm.setLeading(40);
//		   String wjnr = rsHtwjxx.getWjnr().replaceAll("<([^<>]+)>|&nbsp;", "");
//		   Paragraph headerwjnr = new PDFParagragh(wjnr, 3, 10);
//		   headerwjnr.setIndentationLeft(30);
//		   Image imglinedown = Image
//		     .getInstance(imglinedownpath);
//		   imglinedown.scaleAbsoluteHeight(3);
//		   imglinedown.scaleAbsoluteWidth(500);
//		   imglinedown.setAbsolutePosition(50, 140);
//		   //段落绝对位置
//		   PdfContentByte cb = writer.getDirectContent();
//		   BaseFont bf1 = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
//		     BaseFont.NOT_EMBEDDED);
//		   cb.beginText();
//		   cb.setFontAndSize(bf1, 10);
//		   cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "XXXX公司："
//		     + rsHtwjxx.getCm(), 350, 170, 0);
//		   cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "签发日期："
//		     + rsHtwjxx.getQfrq(), 350, 150, 0);
//		   cb.endText();
//		   document.add(imlogo);
//		   document.add(headerwjbt);
//		   document.add(headerchm);
//		   document.add(headerwjnr);
//		   document.add(imglinedown);
//		   document.close();
//		   os.close();
//		   return true;
//		  } catch (DocumentException e) {
//		   e.printStackTrace();
//		   return false;
//		  } catch (MalformedURLException e) {
//		   e.printStackTrace();
//		   return false;
//		  } catch (IOException e) {
//		   e.printStackTrace();
//		   return false;
//		  }
		 }
}
