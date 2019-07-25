package com.berzenin.app.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berzenin.app.model.ResultLine;
import com.berzenin.app.parsers.PdfParser;
import com.berzenin.app.web.dto.RequestFoPdfArguments;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class PdfParserService  {

	private String path;
	@Getter
	private int count;

	@Autowired
	private PdfParser pdfParser;

	public List<ResultLine> getResult(RequestFoPdfArguments argument) throws IOException {
		this.count = argument.getArgs().length;
		List<ResultLine> result = pdfParser.setListWithSearchWords(argument);
		result = result.stream().filter(s -> s.getCountEquals() > count).collect(Collectors.toList());
		result.stream().forEach(s -> s.setLink(argument.getLink().getUrlForResource()));
		return result;
	}
}
