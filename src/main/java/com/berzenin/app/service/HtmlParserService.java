package com.berzenin.app.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berzenin.app.model.ResultLine;
import com.berzenin.app.parsers.HtmlParser;
import com.berzenin.app.web.dto.RequestFoPdfArguments;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class HtmlParserService {
	
	@Autowired
	private HtmlParser htmlParser;
	
	public Set<ResultLine> getResult(RequestFoPdfArguments argument) {
		Set<ResultLine> result = htmlParser.parseHtmlTable(argument.getArgs(), argument.getMetalType());
		return result;
	}

}
