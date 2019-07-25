package com.berzenin.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.berzenin.app.model.LinkForMetalResources;
import com.berzenin.app.repo.LinkForMetalResourcesRepo;
import com.berzenin.app.type.ResourcesType;

@RunWith(SpringRunner.class)
class ServiceTest {
	
	@Autowired
	LinkForMetalResourcesRepo repo;

	@Autowired
	LinkForMetalResourcesService service;
	
	@BeforeEach
	public void setUp() {
		service = new LinkForMetalResourcesService(repo);
	}
	
	@Test
	void getHostNameFromUrlTest() {
		String url = "https://www.remystahl.de/fileadmin/user_upload/downloads/Lieferprogramm_Remystahl.pdf";		
		assertEquals("www.remystahl.de", service.getHostNameFromUrl(url));		
	}

	@Test
	public void getLocalPathForPdfTest() throws IOException {
	    File file = new File("C:\\Users\\Fylyp\\Desktop\\resume\\FilippBerzeninENG.pdf");
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
		assertEquals("..\\web-metal-searcher\\src\\main\\resources\\localfiles\\FilippBerzeninENG.pdf", service.getLocalPathForPdf(multipartFile).toString());
	}
	
	@Test
	public void setPathForFileTest() {
		assertEquals("..\\web-metal-searcher\\src\\main\\resources\\www.remystahl.de\\", service.setPathForFile("https://www.remystahl.de/fileadmin/user_upload/downloads/Lieferprogramm_Remystahl.pdf"));
	}
	
	@Test
	public void setPdfFileNameTest() {
		assertEquals("Lieferprogramm_Remystahl.pdf", service.setPdfFileName("https://www.remystahl.de/fileadmin/user_upload/downloads/Lieferprogramm_Remystahl.pdf"));
	}
	
	@Test
	public void downloadFileFRomUrlTest() throws IOException {
		String url = "https://www.remystahl.de/fileadmin/user_upload/downloads/Lieferprogramm_Remystahl.pdf";
		LinkForMetalResources entity = LinkForMetalResources.builder()
				.host(service.getHostNameFromUrl(url))
				.resourcesType(ResourcesType.REMOTE_PDF)
				.urlForResource(url)
				.localPathForPdfFile("..\\web-metal-searcher\\src\\main\\resources\\www.remystahl.de\\Lieferprogramm_Remystahl.pdf")
				.localPathForTxtFile("..\\web-metal-searcher\\src\\main\\resources\\www.remystahl.de\\Lieferprogramm_Remystahl.txt")
				.build();		
		boolean flag = false;
		Path path = Paths.get("..\\web-metal-searcher\\src\\main\\resources\\www.remystahl.de\\Lieferprogramm_Remystahl.pdf");
		if (Files.exists(path)) {
			flag = true;
		}
		assertTrue(service.downloadResorcesFromUrl(entity));
		if (flag==false) {
			Files.delete(path);
		}
	}
}
