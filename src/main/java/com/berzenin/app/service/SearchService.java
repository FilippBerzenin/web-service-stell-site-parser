package com.berzenin.app.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berzenin.app.model.Links;
import com.berzenin.app.model.ResultLine;
import com.berzenin.app.web.dto.RequestFoPdfArguments;

@Service
public class SearchService {
	
	@Autowired
	private PdfParserService pdfParserService;
	
	@Autowired
	private HtmlParserService htmlParserService;
	
	public List<ResultLine> distributorForLineSearch (Links links) {
		List<ResultLine> result = new LinkedList<>();
		for (String link: links.getLinksFor()) {
			if (link.endsWith("pdf")) {
				result.addAll(pdfParserService.getResult(new RequestFoPdfArguments(link, links.getKey())));		
			}
			else {
				result.addAll(htmlParserService.getResult(new RequestFoPdfArguments(link, links.getKey())));		
			}
		}
		return result;		
	}

}
