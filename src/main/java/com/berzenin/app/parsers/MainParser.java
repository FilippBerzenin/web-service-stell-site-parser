package com.berzenin.app.parsers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import com.berzenin.app.model.ResultLine;
import com.berzenin.app.web.dto.RequestFoPdfArguments;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public abstract class MainParser {

	public List<ResultLine> setListWithSearchWords(RequestFoPdfArguments argument) {
		List<String> lines = null;
		List<ResultLine> result = new ArrayList<>();
		boolean flagForMetalType = false;
		try {
			int numberOfLines = 0;
			lines = Files.readAllLines(Paths.get(argument.getLink().getLocalPathForTxtFile()));
			for (String line : lines) {
				Set<String> keywords = new HashSet<>();
				int amountEquals = 0;
				if (this.stringContainsAnotherString(line, argument.getMetalType())) {
					amountEquals++;
					flagForMetalType = true;
					for (String arg : argument.getArgs()) {
						if (this.stringContainsAnotherString(line, arg)) {
							amountEquals++;
							keywords.add(arg);
						}
					}
					if (amountEquals > 0 && flagForMetalType == true) {
						result.add(new ResultLine(
								argument.getLink().getHost(),
								amountEquals,
								line,
								numberOfLines,
								argument.getMetalType(),
								keywords,
								argument.getLink().getUrlForResource()));
					}
					numberOfLines++;
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	public boolean stringContainsAnotherString(String line, String word) {
		Pattern pattern;
		if (word.matches("[\\d]+")) {
		    pattern = Pattern.compile(word);
		}
		else {
			pattern = buildingRegExpression(word);
		}
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			System.out.println(line.substring(matcher.start(), matcher.end()));
			return true;
		}
		return false;
	}

	public Pattern buildingRegExpression(String find) {
		find.replaceAll("\\s+","");
		StringBuffer regex = new StringBuffer();
		for (char ch : find.toCharArray()) {
			regex.append(ch);
			regex.append(".?");
		}
		return Pattern.compile(regex.toString());
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
		lin = this.removeWhitespaces(lin);
		try {
			Files.write(path, lin, StandardOpenOption.CREATE);
			return Optional.ofNullable(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(path);
	}
	
	public List<String> removeWhitespaces (List<String> list) {
		return list.stream()
				.map(s -> s.trim())
				.map(s -> s.replaceAll("\\s+", " "))
				.collect(Collectors.toList());
	}
}
