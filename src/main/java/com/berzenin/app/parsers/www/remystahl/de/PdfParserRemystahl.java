package com.berzenin.app.parsers.www.remystahl.de;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.fit.pdfdom.PDFDomTree;

import com.berzenin.app.model.ResultLine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PdfParserRemystahl {

	public Path downloadFileFRomUrl (String path) {
		URL url = null;
		Path filePdf = null;
		try {
			url = new URL(path);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		try (InputStream in = url.openStream()) {
			filePdf = Paths.get("..\\websitestillparser\\src\\main\\resources\\pdf_files\\working.pdf");
		   Files.copy(in, filePdf, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
		   e.printStackTrace();
		}
		return filePdf;
	}

	public void generateHTMLFromPDF(String htmlFile, String pdfFile) {
		try  {
			PDDocument pdf = PDDocument.load(new File(pdfFile)); 
			Writer output = new PrintWriter(htmlFile);
			new PDFDomTree().writeText(pdf, output);
			output.close();
			pdf.close();
		} catch (InvalidPasswordException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException | ParserConfigurationException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void generateTxtFromPDF(String txtFile, String pdfFile) {
		try {
			File f = new File(pdfFile);
			String parsedText;
			PDFParser parser = new PDFParser(new RandomAccessFile(f, "r"));
			parser.parse();
			COSDocument cosDoc = parser.getDocument();
			PDFTextStripper pdfStripper = new PDFTextStripper();
			PDDocument pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
			PrintWriter pw = new PrintWriter(txtFile);
			pw.print(parsedText);
			pw.close();
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<ResultLine> setListWithSearchWords(String txtFile, String... args) {
		List<String> lines = null;
		List<ResultLine> result = new ArrayList<>();
		try {
			lines = Files.readAllLines(Paths.get(txtFile));
			for (String line: lines) {
				int amountEquals = 0;
				for (String arg: args) {
					if (line.contains(arg)) {
						amountEquals++;
					}
				}
				if (amountEquals>0) {
					result.add(new ResultLine("host", amountEquals, line, 0));
				}
			}
			result.forEach(System.out::println);
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}		
		return result;
		
	}

}
