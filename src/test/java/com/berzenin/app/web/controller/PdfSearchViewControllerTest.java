package com.berzenin.app.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import com.berzenin.app.WebsitestillparserApplicationTests;
import com.berzenin.app.web.dto.RequestFoPdfArguments;

@AutoConfigureMockMvc
@SpringBootTest(classes = WebsitestillparserApplicationTests.class)
@RunWith(SpringRunner.class)
class PdfSearchViewControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	void PdfSearchViewControllerGetTest() throws Exception {
		// Given
		String[] args = { "", "" };
		RequestFoPdfArguments arguments = new RequestFoPdfArguments();
		arguments.setPathForLink(
				"https://www.remystahl.de/fileadmin/user_upload/downloads/Lieferprogramm_Remystahl.pdf");
		arguments.setArgs(args);
		// Then
		mvc.perform(get("/searchFromPdf")).andDo(print()).andExpect(status().isOk())
				.andExpect(forwardedUrl("/WEB-INF/jsp/searchFromPdf.jsp")).andExpect(view().name("searchFromPdf"));
	}

//	@Test
//	void PdfSearchViewControllerPostTest() throws Exception {
//		// Given
//		String[] args = { "", "" };
//		RequestFoPdfArguments arguments = new RequestFoPdfArguments();
//		arguments.setPathForLink(
//				"https://www.remystahl.de/fileadmin/user_upload/downloads/Lieferprogramm_Remystahl.pdf");
//		arguments.setArgs(args);
//		// Then
//		mvc.perform(post("/searchFromPdf")
//			.param("filter", arguments))
//			.andDo(print())
//			.andExpect(status().isOk())
//			.andExpect(forwardedUrl("/WEB-INF/jsp/searchFromPdf.jsp"))
//			.andExpect(view().name("searchFromPdf"));
//	}

}
