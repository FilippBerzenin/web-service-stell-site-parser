package com.berzenin.app.parsers;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class PdfParserTest {
	
	private PdfParser pars;
	private String localPath;
	private String path;
	private String pdfFileName;
	
	@BeforeEach
	public void setParametrs () {
		pars  = new PdfParser();
		localPath = "..\\websitestillparser\\src\\main\\resources\\www.remystahl.de\\";
		pdfFileName = "Lieferprogramm_Remystahl.pdf";
		path = "https://www.remystahl.de/fileadmin/user_upload/downloads/Lieferprogramm_Remystahl.pdf";
	}
	
	@Test
	public void downloadFileFRomUrlTest () {
		assertEquals(Paths.get(localPath+pdfFileName), pars.downloadFileFRomUrl(path, localPath, pdfFileName));
	}
	
	@Test
	public void convertPdfForExcelTest () {
		String pdf = localPath+pdfFileName;
		String html = localPath+"pdf.html";
		pars.generateHTMLFromPDF(html, pdf);
	}
	
	@Test
	public void generateTxtFromPDFTest () {
		String pdf = localPath+pdfFileName;
		String text = localPath+"text.txt";
		pars.generateTxtFromPDF(text, pdf);
	}
	
	@Test
	public void setListWithSearchWordsTest() {
		String text = localPath+"text.txt";
		String [] srs = {"1.3401", "rund"};
		pars.setListWithSearchWords("test", text, srs);
	}
	
	@Test
	public void checkRemoteFileForNewVersionTest () {
		assertFalse(pars.checkRemoteFileForNewVersion(path, localPath+pdfFileName));

	}
	
	@Disabled
	@Test
	public void getFileSizeTest () throws MalformedURLException {
		URL url = new URL (path);
		assertEquals(Paths.get(localPath).toFile().length(), pars.getFileSize(url));
	}
	

}
