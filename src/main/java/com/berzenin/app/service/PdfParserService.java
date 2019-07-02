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
	private static final String pathToResource = "..\\websitestillparser\\src\\main\\resources\\";
	private static String pdfFileName;
	private static String txtFileName;
	private static String host;

	@Autowired
	private PdfParser parser;

	public List<ResultLine> getResult(RequestFoPdfArguments argument) {
		this.setPathForFile(argument.getPathForLink());
		this.setPdfFileName(argument.getPathForLink());
		this.setTxtFileName(argument.getPathForLink());
		if (parser.downloadFileFRomUrl(argument.getPathForLink(), path, pdfFileName)) {
			parser.generateTxtFromPDF(path + txtFileName, path + pdfFileName);
		}
		List<ResultLine> result = parser.setListWithSearchWords(host, path + txtFileName, argument.getArgs());
		result = result.stream().filter(s -> s.getCountEquals() > 1).collect(Collectors.toList());
		result.stream().forEach(s -> s.setLink(argument.getPathForLink()));
		result.forEach(System.out::println);
		return result;
	}

	public String setPathForFile(String url) {
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
