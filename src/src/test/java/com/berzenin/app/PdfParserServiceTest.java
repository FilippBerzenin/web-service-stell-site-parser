package com.berzenin.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.berzenin.app.parsers.PdfParser;
import com.berzenin.app.service.PdfParserService;
import com.berzenin.app.web.dto.RequestFoPdfArguments;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
class PdfParserServiceTest {
	
	private PdfParserService service;
	private String url;
	private RequestFoPdfArguments argument;
	
	@Mock
	private PdfParser parser;
	
	@BeforeEach
	public void setUp() {
		service = new PdfParserService();
		url = "https://www.remystahl.de/fileadmin/user_upload/downloads/Lieferprogramm_Remystahl.pdf";
		argument = new RequestFoPdfArguments();
		argument.setPathForLink(url);
		String[] args = {"rund", "1.3401"};
		argument.setArgs(args);
	}

	@Disabled
	@Test
	void setPathForFileTest() {
			assertEquals("..\\websitestillparser\\src\\main\\resources\\www.remystahl.de\\", service.setPathForFile(url));
	}
	
	@Disabled
	@Test
	public void setPdfFileNameTest () {
		assertEquals("Lieferprogramm_Remystahl.pdf", service.setPdfFileName(url));
	}
	
	@Disabled
	@Test
	public void setTxtFileNameTest () {
		assertEquals("Lieferprogramm_Remystahl.txt", service.setTxtFileName(url));
	}
	
	@Test
	public void getResultTest() throws IOException {
		service.getResult(argument);
	}
	
}
