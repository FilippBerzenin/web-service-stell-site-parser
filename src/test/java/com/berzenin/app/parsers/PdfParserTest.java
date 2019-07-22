package com.berzenin.app.parsers;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
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
	public void testGenerateTxtFromPDF () throws IOException {
		String pdfFile = "C:\\workspace\\web-metal-searcher\\src\\main\\resources\\localfiles\\FilippBerzeninENG.pdf";
		String txtFile = "C:\\workspace\\web-metal-searcher\\src\\main\\resources\\localfiles\\FilippBerzeninENG.txt";
		pars.generateTxtFromPDF(txtFile, pdfFile);
	}
	
	@Disabled
	@Test
	public void buildingRegExpressionTest () {
		String find = "X 120 Mn 12";
		System.out.println(pars.buildingRegExpression(find));
	}
	
	@Disabled
	@Test
	public void stringContainsAnotherStringTest () {
		String line = "X 120 Mn 12 1.3401 1,10–1,30 0,30–0,50 12,0–13,0 0,100 0,040 max. 1,50 – – 1,00 –";
		String word = "X120Mn12";
		System.out.println(pars.stringContainsAnotherString(line, word));
		String line2 = "X 120 Mn 12 1.3401 1,10–1,30 0,30–0,50 12,0–13,0 0,100 0,040 max. 1,50 – – 1,00 –";
		String word2 = "130";
		System.out.println(pars.stringContainsAnotherString(line2, word2));
	}
	
//	@Disabled
//	@Test
//	public void downloadFileFRomUrlTest () throws IOException {
//		assertEquals(Paths.get(localPath+pdfFileName), pars.downloadFileFRomUrl(path, localPath, pdfFileName));
//	}
	
	
	@Disabled
	@Test
	public void generateTxtFromPDFTest () throws FileNotFoundException, IOException {
		String pdf = localPath+pdfFileName;
		String text = localPath+"text.txt";
		pars.generateTxtFromPDF(text, pdf);
	}
	
	@Disabled
	@Test
	public void setListWithSearchWordsTest() {
		String text = localPath+"text.txt";
		String [] srs = {"1.3401", "rund"};
//		pars.setListWithSearchWords(path, "test", text, srs);
	}
	
	@Disabled
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
