package com.berzenin.app.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.berzenin.app.parsers.www.remystahl.de.PdfParserRemystahl;

class PdfParserTest {
	
	private PdfParserRemystahl pars;
	
	@BeforeEach
	public void setParametrs () {
		pars  = new PdfParserRemystahl();
	}
	
	@Disabled
	@Test
	public void downloadFileFRomUrlTest () {
		PdfParserRemystahl pars  = new PdfParserRemystahl();
		String path = "https://www.remystahl.de/fileadmin/user_upload/downloads/Lieferprogramm_Remystahl.pdf";
		
		assertEquals(Paths.get("..\\websitestillparser\\src\\main\\resources\\pdf_files\\working.pdf"), pars.downloadFileFRomUrl(path));
	}
	
	@Disabled
	@Test
	public void convertPdfForExcelTest () {
		String pdf = "..\\websitestillparser\\src\\main\\resources\\pdf_files\\working.pdf";
		String html = "..\\websitestillparser\\src\\main\\resources\\pdf_files\\pdf.html";
		pars.generateHTMLFromPDF(html, pdf);
	}
	
	@Disabled
	@Test
	public void generateTxtFromPDFTest () {
		String pdf = "..\\websitestillparser\\src\\main\\resources\\pdf_files\\working.pdf";
		String text = "..\\websitestillparser\\src\\main\\resources\\pdf_files\\text.txt";
		pars.generateTxtFromPDF(text, pdf);
	}
	
	@Test
	public void setListWithSearchWordsTest() {
		String text = "..\\websitestillparser\\src\\main\\resources\\pdf_files\\text.txt";
		String [] srs = {"1.3401", "rund"};
		pars.setListWithSearchWords(text, srs);
	}
	

}
