package com.berzenin.app.parsers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PdfParser extends MainParser {

//	public boolean downloadFileFRomUrl(String path, String localPath, String pdfFileName) {
//		if (pdfFileName == null || pdfFileName.length()==0) {
//			return false;
//		}
//		URL url = null;
//		Path filePdf = Paths.get(localPath + pdfFileName);
//		if (!this.checkRemoteFileForNewVersion(path, filePdf.toString())) {
//			System.out.println("false");
//			return false;
//		}
//		try {
//			url = new URL(path);
//		} catch (MalformedURLException e) {
//			log.error(e.getMessage());
//			e.printStackTrace();
//		}
//		try (InputStream in = url.openStream()) {
//			Files.createFile(filePdf);
//			Files.copy(in, filePdf, StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			log.error(e.getMessage());
//			e.printStackTrace();
//		}
//		return true;
//	}

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

	
	public void generateTxtFromPDF(String txtFile, String pdfFile) throws IOException {
		File f = new File(pdfFile);
		String parsedText;
		PDFParser parser = new PDFParser(new RandomAccessFile(f, "r"));
		parser.parse();
		PDDocument document = null;
		try (PrintWriter pw = new PrintWriter(txtFile);
				COSDocument cosDoc = parser.getDocument()) {
			document = new PDDocument(cosDoc);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			parsedText = pdfStripper.getText(document);
			pw.print(parsedText);
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			document.close();
			document = null;
		}
	}

}
