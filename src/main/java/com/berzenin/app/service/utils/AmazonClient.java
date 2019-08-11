package com.berzenin.app.service.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.naming.NameNotFoundException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AmazonClient implements CrudFiles {

	private AmazonS3 s3Client;

	private TransferManager tm;

	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl = "https://ec2-35-177-156-144.eu-west-2.compute.amazonaws.com";
	@Value("${amazonProperties.bucketName}")
	private String bucketName = "web-stell-searcher";
	@Value("${amazonProperties.accessKey}")
	private String accessKey = "AKIAJB4PZDWISCLJS3ZA";;
	@Value("${amazonProperties.secretKey}")
	private String secretKey = "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";
	@Value("${amazonProperties.clientRegion}")
	private String clientRegion = Region.EU_London.toString();

	@PostConstruct
	public void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey,
				secretKey);
		s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(clientRegion).build();
	}

	public void createBucket(String bucketName) {
		if (s3Client.doesBucketExistV2(bucketName)) {
			log.info("Bucket name is not available." + " Try again with a different Bucket name.");
			return;
		}
		s3Client.createBucket(bucketName);
	}
	
	public Optional<List<Bucket>> getAllBucket() {
		return Optional.of(s3Client.listBuckets());
	}
	
	public boolean deleteBacket(String bucketName) {
		try {
			s3Client.deleteBucket(bucketName);
		    return true;
		} catch (AmazonServiceException e) {
		    log.error("Backet failed deleted "+bucketName);
		    return false;
		}
	}

	public boolean upload(File file, String pathToPutInStorage) {
		try {
			log.info("File upload started "+file.getName());
			s3Client.putObject(bucketName, pathToPutInStorage, file);
			log.info("File upload "+file.getName() + " successful");
			return true;
		} catch (AmazonClientException e) {
			log.error("File upload failed "+file.getName() + e.getLocalizedMessage());
			return false;
		}
	}
	
	public Optional<ObjectListing> getAllObject() {
		return Optional.of(s3Client.listObjects(bucketName));		
	}
	
	public S3Object getObjectFromStorage(String pathToGetFileInStorage) {
		return Optional.of(s3Client.getObject(bucketName, pathToGetFileInStorage)).orElseThrow(RuntimeException::new);
	}
//	return false;
//		try {
//			Upload upload = tm.upload(bucketName, "test", new File(filePath));
//			log.info("File upload started ");
//			upload.waitForCompletion();
//			log.info("File upload " + " successful");
//		} catch (AmazonClientException | InterruptedException e) {
//			log.info("File upload failed ");
//			e.printStackTrace();
//		}
//		return false;
//	}

	public boolean uploadFile() {
		initializeAmazon();
//		log.info("Files upload start "+multipartFile.getName());
		String fileUrl = "";
		try {
//			File file = convertMultiPartToFile(multipartFile);
//			String fileName = generateFileName(multipartFile);
			File file = new File("C:\\Users\\Fylyp\\Desktop\\resume\\FilippBerzeninENG.pdf");
			String fileName = file.getName();
			fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
			if (uploadFileTos3bucket(fileName, file)) {
				log.info("Files upload " + file.getName() + " successful");
			}
//			file.delete();
			return true;
		} catch (Exception e) {
			log.info("Files upload failed");
			e.printStackTrace();
			return false;
		}
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		log.info("Get files with size " + file.getSize());
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	private boolean uploadFileTos3bucket(String fileName, File file) {
		try {

			log.info("File " + fileName + " send to amazone basket " + bucketName);
			s3Client.putObject(
					new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
			return true;
		} catch (RuntimeException e) {
			log.info("File " + fileName + " failed upload" + e.getLocalizedMessage());
			return false;

		}
	}

	public boolean deleteFile(String fileUrl) {
		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
		log.info("File " + fileName + " start deleting");
		try {
			s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
			log.info("File " + fileName + " successful deleted");
			return true;
		} catch (RuntimeException e) {
			log.info("File " + fileName + " failed deleted" + e.getLocalizedMessage());
			return false;
		}
	}

	@Override
	public Optional<File> read(String filesname) {
		try {
			initializeAmazon();
			log.info("File " + filesname + " start reading");
			S3Object s3object = s3Client.getObject(bucketName, filesname);
			S3ObjectInputStream inputStream = s3object.getObjectContent();
			File file = new File(filesname);
			FileUtils.copyInputStreamToFile(inputStream, file);
			log.info("File " + filesname + " successful readed");
			return Optional.of(file);
		} catch (IOException e) {
			log.info("File " + filesname + " failed reding" + e.getLocalizedMessage());
			e.printStackTrace();
			return Optional.empty();

		}
	}

	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean uploadFile(MultipartFile file) {
		// TODO Auto-generated method stub
		return false;
	}

}
