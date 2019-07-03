package com.berzenin.app.parsers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HtmlParseTest {
	
	HtmlParser parser;
	
	@BeforeEach
	public void setIp () {
		parser = new HtmlParser();
	}

	@Test
	void parserHtmlTest() {
//		String arg = "S24 DIN 5904 12 120";
//		String[] args = arg.trim().split(" ");
//		parser.parserHtml(args);
		String url = "https://www.bemorail.com/portfolio-item/vignola-rail/";
		parser.parseTable(parser.getDocumentFormUrl(url));
	}

}
