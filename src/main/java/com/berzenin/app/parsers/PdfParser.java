package com.berzenin.app.parsers;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PdfParser extends MainParser {

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
}
