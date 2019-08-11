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

class AmazonClientTest {
	
	private static AmazonClient client;
	
	@BeforeAll
	public static void setUp() {
		client = new AmazonClient();
		client.initializeAmazon();
	}
	
	@Disabled
	@Test
	public void uploadObject() throws IOException {
		String path = "1.txt";
		String pathToStorage = "test/"+path;		
		client.upload(new File(path), pathToStorage);		
	}
	
	@Test
	public void getObject() {
		String path = "1.txt";
		String pathToStorage = "test/"+path;	
		System.out.println(client.getObjectFromStorage(pathToStorage)+"-------------------------------");
	}
	
	@Test
	public void getAllObject() {
		client.getAllObject().get().getObjectSummaries().forEach(System.out::println);
	}
	
	@Disabled
	@Test
	public void createAndDeleteBucketTest() {
		String bucketName = "baeldung";
		client.createBucket(bucketName);
		client.deleteBacket(bucketName);
	}
	
	@Disabled
	@Test
	public void getAllBucketsTest() {
		client.getAllBucket().get().forEach(System.out::println);
	}
	
}
