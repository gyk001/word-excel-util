package cn.guoyukun.pdm2pdf;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.DocumentException;

public class Main {
	public static void main(String[] args) throws MalformedURLException, DocumentException, IOException {
		String pdm = "";
		String pdf= "/Users/Guo/Desktop/ITextTest.pdf";
		Generator g = new Generator();
		g.generator(pdm, pdf);
	}
}
