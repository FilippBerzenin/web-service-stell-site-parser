package com.berzenin.app.service.utils;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.berzenin.app.model.LinkForMetalResources;

public interface FilesController {
	
	public boolean copyFileForlocalDirectory(LinkForMetalResources entity, MultipartFile file);

	public boolean deleteFile(String localPathForTxtFile);

	public boolean downloadPdfFileFromUrl(LinkForMetalResources res);

	public Path getLocalPathForPdf(MultipartFile file);

	public String setPathForFile(String path);

	public String setPdfFileName(String urlForResource);

	public String getHostNameFromUrl(String urlForResource);

	public Optional<Path> generateTxtFromPDF(String localPathForTxtFile, String localPathForPdfFile);

	public Optional<List<String>> readAllLines(String localPathForTxtFile);

	public Optional<Path> writeBytesForTxtFile(String localPathForTxtFile, String[] lines);

}
