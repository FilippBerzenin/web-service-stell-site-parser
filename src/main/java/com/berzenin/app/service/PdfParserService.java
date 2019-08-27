package com.berzenin.app.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
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
public class PdfParserService implements Callable<Optional<List<ResultLine>>> {

	private String path;
	private int count;
	private RequestFoPdfArguments argument;
	
	@Autowired
	private PdfParser pdfParser;

	public List<ResultLine> getResult() {
		this.count = this.argument.getArgs().length;
		List<ResultLine> result = pdfParser.setListWithSearchWords(this.argument);
		result = result.stream().filter(s -> s.getCountEquals() > count).collect(Collectors.toList());
		result.stream().forEach(s -> s.setLink(this.argument.getLink().getUrlForResource()));
		return result;
	}

	@Override
	public Optional<List<ResultLine>> call() throws Exception {
		return Optional.of(getResult());
	}
}
