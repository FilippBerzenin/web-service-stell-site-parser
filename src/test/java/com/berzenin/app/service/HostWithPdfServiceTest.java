package com.berzenin.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.berzenin.app.WebsitestillparserApplication;
import com.berzenin.app.WebsitestillparserApplicationTests;
import com.berzenin.app.model.HostWithPdf;
import com.berzenin.app.repo.HostWithPdfRepo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebsitestillparserApplication.class)
class HostWithPdfServiceTest {
	

	@Autowired
	private HostWithPdfRepo repo;

	@Test
	void HostWithPdfRepoTest() {
		//Given
		HostWithPdf hostWithPdf  = new HostWithPdf("www.test.com", "test.link");
		//Then
		repo.save(hostWithPdf);
		HostWithPdf hostWithPdf2 = repo.findByHost("www.test.com");
		//When
        assertNotNull(hostWithPdf2);
        assertEquals(hostWithPdf2.getHost(), hostWithPdf.getHost());
        assertEquals(hostWithPdf2.getLinkForPdfFile(), hostWithPdf.getLinkForPdfFile());
		
	}

}
