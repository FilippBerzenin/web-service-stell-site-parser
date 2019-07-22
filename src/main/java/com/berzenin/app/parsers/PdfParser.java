package com.berzenin.app.parsers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PdfParser extends MainParser {

	public boolean downloadFileFRomUrl(String path, String localPath, String pdfFileName) {
		if (pdfFileName == null || pdfFileName.length()==0) {
			return false;
		}
		URL url = null;
		Path filePdf = Paths.get(localPath + pdfFileName);
		if (!this.checkRemoteFileForNewVersion(path, filePdf.toString())) {
			System.out.println("false");
			return false;
		}
		try {
			url = new URL(path);
		} catch (MalformedURLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		try (InputStream in = url.openStream()) {
			Files.createFile(filePdf);
			Files.copy(in, filePdf, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	public boolean checkRemoteFileForNewVersion(String remotePath, String localPath) {
		try {
			if (this.getFileSize(new URL(remotePath)) != Paths.get(localPath).toFile().length()) {
				return true;
			}
		} catch (MalformedURLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public long getFileSize(URL url) {
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("HEAD");
			System.out.println(conn.getContentLengthLong());
			return conn.getContentLengthLong();
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return conn.getContentLengthLong();
	}

	public Optional<Path> generateTxtFromPDF(String txtFile, String pdfFile) {
		Path file = Paths.get(txtFile);
		if (!Files.exists(Paths.get(pdfFile))) {
			log.info("File don't find: " + pdfFile);
			return Optional.ofNullable(file);
		}
		try (PDDocument document = PDDocument.load(new File(pdfFile))) {
			document.getClass();
			if (!document.isEncrypted()) {
				if (!document.isEncrypted()) {
					PDFTextStripper tStripper = new PDFTextStripper();
					String pdfFileInText = tStripper.getText(document);
					String lines[] = pdfFileInText.split("\\r?\\n");
					return this.writeBytesForTxtFile(txtFile, lines);
				}
			}
		} catch (InvalidPasswordException e) {
			log.error("invalid password"+e);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(file);
	}
	
	public Optional<Path> writeBytesForTxtFile(String txtFile, String[] lines) {
	    Path path = Paths.get(txtFile);
	    List<String> lin = Arrays.asList(lines);
		    try {
				Files.write(path, lin, StandardOpenOption.CREATE);
				return Optional.ofNullable(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		    return Optional.ofNullable(path);
	    }

}
