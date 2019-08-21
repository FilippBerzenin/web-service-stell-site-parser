package com.berzenin.app.service.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.berzenin.app.model.LinkForMetalResources;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LocalFilesController extends AbstractFilesController {
	
	@Getter
	protected static final String pathToResource = "..\\web-metal-searcher\\src\\main\\resources\\";
	
	public boolean copyFileForlocalDirectory(LinkForMetalResources entity, MultipartFile file) {
		Path copied = Paths.get(entity.getLocalPathForPdfFile());
		try {
			Files.copy(file.getInputStream(), copied, StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteFile(String link) {
		try {
			Files.deleteIfExists(Paths.get(link));
			return true;
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
			return false;
		}
	}
	
	public Path getLocalPathForPdf(MultipartFile file) {
		createLocalDirectory();
		Path path = Paths.get(pathToResource + "\\localfiles\\" + file.getOriginalFilename());
		return path;
	}
	
	public void createLocalDirectory() {
		if (!Files.notExists(Paths.get(pathToResource + "\\localfiles\\"))) {
			try {
				Files.createDirectory(Paths.get(pathToResource + "\\localfiles\\"));
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}


	public boolean downloadPdfFileFromUrl (LinkForMetalResources res) {
		if (res.getUrlForResource() == null || res.getUrlForResource().length()==0) {
			return false;
		}
		URL url = null;
		Path filePdf = Paths.get(res.getLocalPathForPdfFile());
		try {
			url = new URL(res.getUrlForResource());
		} catch (MalformedURLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
		try (InputStream in = url.openStream()) {
			if (!Files.exists(filePdf)) {
				Files.createFile(filePdf);
			}
			Files.copy(in, filePdf, StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public String setPathForFile(String url) {
		String path = "";
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
		String pdfFileName = "";
		try {
			URL partOfurl = new URL(url.trim());
			pdfFileName = FilenameUtils.getName(partOfurl.getPath());
			if (pdfFileName==null || pdfFileName.length()==0) {
				if (url.endsWith("/")) {
					url = url.substring(0, url.length()-2);
				}
				pdfFileName = url.substring(url.lastIndexOf("/")+1);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
	}
		return pdfFileName;
	}

	public Optional<List<String>> readAllLines(String url) {
		try {
			return Optional.of(Files.readAllLines(Paths.get(url)));
		} catch (IOException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	public Optional<Path> generateTxtFromPDF(String txtFile, String pdfFile) {
		Path file = Paths.get(txtFile);
		if (!Files.exists(Paths.get(pdfFile))) {
			log.info("File don't find: " + pdfFile);
			return Optional.ofNullable(file);
		}
		PDFTextStripper tStripper;
		try (PDDocument document = PDDocument.load(new File(pdfFile))) {
			document.getClass();
			if (!document.isEncrypted()) {
				tStripper = new PDFTextStripper();
				String pdfFileInText = tStripper.getText(document);
				document.close();
				String lines[] = pdfFileInText.split("\\r?\\n");
				return this.writeBytesForTxtFile(txtFile, lines);
			}
		} catch (NoClassDefFoundError e) {
			log.info("txt "+txtFile+ " pdf "+pdfFile);
			log.error("NoClassDefFoundError "+e);
			e.printStackTrace();
			return Optional.ofNullable(file);
		} catch (InvalidPasswordException e) {
			log.error("invalid password"+e);
			e.printStackTrace();
			return Optional.ofNullable(file);
		} catch (IOException e) {
			e.printStackTrace();
			return Optional.ofNullable(file);
		} 
		return Optional.ofNullable(file);
	}

	public Optional<Path> writeBytesForTxtFile(String txtFile, String[] lines) {
		Path path = Paths.get(txtFile);
		List<String> lin = Arrays.asList(lines);
		lin = this.removeWhitespaces(lin);
		try {
			Files.write(path, lin, StandardOpenOption.CREATE);
			return Optional.ofNullable(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(path);
	}
}
