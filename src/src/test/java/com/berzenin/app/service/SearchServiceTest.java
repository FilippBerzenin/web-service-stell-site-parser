package com.berzenin.app.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SearchServiceTest {

	@Test
	void getAllLinesFromHostTest() {
		String link = "https://www.steelinox.nl/en/";
		SearchService service = new SearchService();
		service.getAllLinks(link);
	}

}
