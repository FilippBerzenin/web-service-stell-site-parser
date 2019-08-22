package com.berzenin.app.parsers;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berzenin.app.model.ResultLine;
import com.berzenin.app.service.utils.FilesController;
import com.berzenin.app.web.dto.RequestFoPdfArguments;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public abstract class MainParser {
	
	@Autowired
	private FilesController filesController;


	public List<ResultLine> setListWithSearchWords(RequestFoPdfArguments argument) {
		List<String> lines = null;
		List<ResultLine> result = new ArrayList<>();
		boolean flagForMetalType = false;
			int numberOfLines = 0;
			lines = filesController.readAllLines(argument.getLink().getLocalPathForTxtFile()).get();
			for (String line : lines) {
				Set<String> keywords = new HashSet<>();
				int amountEquals = 0;
				String strRegEx = "<[^>]*>";
				line = line.replaceAll(strRegEx, "");
				if (line.length()>50) {
					line.substring(0, 50);
					line = "need find in page..."+line+"...";
				}
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
				}
			numberOfLines++;
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
}
