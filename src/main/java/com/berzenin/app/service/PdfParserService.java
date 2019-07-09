package com.berzenin.app.service;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berzenin.app.model.ResultLine;
import com.berzenin.app.parsers.PdfParser;
import com.berzenin.app.web.dto.RequestFoPdfArguments;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Service
public class PdfParserService {

	private String path;
	@Getter
	private static final String pathToResource = "..\\websitestillparser\\src\\main\\resources\\";
	private static String pdfFileName;
	private static String txtFileName;
	private static String host;
	private int count;

	@Autowired
	private PdfParser pdfParser;
	
	public List<ResultLine> getResult(RequestFoPdfArguments argument) {
		this.count = 1;
		this.setPathForFile(argument.getPathForLink());
		this.setPdfFileName(argument.getPathForLink());
		this.setTxtFileName(argument.getPathForLink());
		System.out.println(Files.exists(Paths.get(path + txtFileName)));
		if (!Files.exists(Paths.get(path + txtFileName))) {		
		if (host.equals("localhost")) {
			pdfParser.generateTxtFromPDF(path + txtFileName, path + pdfFileName);
		} 
		else if (pdfParser.downloadFileFRomUrl(argument.getPathForLink(), path, pdfFileName)) {
			pdfParser.generateTxtFromPDF(path + txtFileName, path + pdfFileName);
		}
		}
		List<ResultLine> result = pdfParser.setListWithSearchWords(argument.getPathForLink(), host, path + txtFileName, argument.getArgs());
		result = result.stream().filter(s -> s.getCountEquals() > count).collect(Collectors.toList());
		result.stream().forEach(s -> s.setLink(argument.getPathForLink()));
		result.forEach(System.out::println);
		return result;
	}

	public String setPathForFile(String url) {
		if (url.contains("localfiles")) {
			path = url.substring(0, url.lastIndexOf("\\")+1);
			return url;
		}
		try {
			URL partOfurl = new URL(url);
			if (!Files.exists(Paths.get(pathToResource + partOfurl.getHost()))) {
				Files.createDirectories(Paths.get(pathToResource + partOfurl.getHost()));
			}
			path = pathToResource + partOfurl.getHost() + "\\";
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return path;
	}

	public String setPdfFileName(String url) {
		if (url.contains("localfiles")) {
			pdfFileName = url.substring(url.lastIndexOf("\\")+1);
			host = "localhost";
			return url;
		}
		try {
			URL partOfurl = new URL(url);
			pdfFileName = FilenameUtils.getName(partOfurl.getPath());
			host = partOfurl.getHost();
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return pdfFileName;
	}

	public String setTxtFileName(String url) {
		if (url.contains("localfiles")) {
			txtFileName = url.replace("pdf", "txt").substring(url.lastIndexOf("\\")+1);
			return url;
		}
		try {
			URL partOfurl = new URL(url);
			txtFileName = FilenameUtils.getName(partOfurl.getPath());
			txtFileName = txtFileName.replace("pdf", "txt");
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return txtFileName;
	}

}
