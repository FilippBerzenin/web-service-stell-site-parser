package com.berzenin.app.service;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.berzenin.app.WebsitestillparserApplication;
import com.berzenin.app.WebsitestillparserApplicationTest;
import com.berzenin.app.service.utils.AmazonClient;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=WebsitestillparserApplicationTest.class)
class AmazonClientTest {
	
	private static AmazonClient client;
	
//	@Value("${amazonProperties.endpointUrl}")
//	private static String endpointUrl = "https://ec2-35-177-156-144.eu-west-2.compute.amazonaws.com";
//	@Value("${amazonProperties.bucketName}")
//	private static String bucketName = "web-stell-searcher";
//	@Value("${amazonProperties.accessKey}")
//	private static String accessKey = "AKIAJB4PZDWISCLJS3ZA";;
//	@Value("${amazonProperties.secretKey}")
//	private static String secretKey = "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";

//	private AmazonS3 s3client;
	
	@BeforeAll
	public static void setUp() {
		client = new AmazonClient();
		client.initializeAmazon();
	}
//
//	@SuppressWarnings("deprecation")
//	@PostConstruct
//	private void initializeAmazon() {
//		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
//		this.s3client = new AmazonS3Client(credentials);
//	}
	
	@Test
	public void createBucketTest() {
		client.createBucket();
	}
	
	@Disabled
	@Test
	public void uploadFile() throws IOException {
		File fi = new File("‪C:\\workspace\\Web-metal-searcher-new\\boing.txt");
//		List<String> lines = Files.readAllLines(fi.toPath(), StandardCharsets.UTF_8);
//		lines.forEach(System.out::println);
		client.upload("‪C:\\workspace\\Web-metal-searcher-new\\boing.txt");
//		client  = new AmazonClient();
//		Path path = Paths.get("C:\\Users\\Fylyp\\Desktop\\resume\\FilippBerzeninENG.pdf");
//		String name = "FilippBerzeninENG.pdf";
//		String originalFileName = "FilippBerzeninENG.pdf";
//		String contentType = "application/pdf";
//		byte[] content = null;
//		try {
//		    content = Files.readAllBytes(path);
//		} catch (final IOException e) {
//		}
//		MultipartFile result = new MockMultipartFile(name, Files.readAllBytes(path));
//		MultipartFile result = new MockMultipartFile(name,
//		                     originalFileName, contentType, content);
//		File fi = client.read("upwork.txt").get();
//		List<String> lines = Files.readAllLines(Paths.get(fi.getAbsolutePath()), StandardCharsets.UTF_8);
//		lines.forEach(System.out::println);
	}

}
