package com.berzenin.app.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berzenin.app.model.LinkForMetalResources;
import com.berzenin.app.model.Links;
import com.berzenin.app.model.ResultLine;
import com.berzenin.app.type.ResourcesType;
import com.berzenin.app.web.dto.RequestFoPdfArguments;

@Service
public class SearchService {
	
	@Autowired
	private PdfParserService pdfParserService;

	@Autowired
	private HtmlParserService htmlParserService;
	
	@Autowired
	private LinkForMetalResourcesService linkForMetalResourcesService;

	public List<ResultLine> distributorForLineSearch(Links links) throws IOException {
		List<ResultLine> result = new LinkedList<>();
		for (String link : links.getLinksFor()) {
			LinkForMetalResources res = linkForMetalResourcesService.findByLink(link);
			if (res.getResourcesType().equals(ResourcesType.LOCAL_PDF) || res.getResourcesType().equals(ResourcesType.REMOTE_PDF)) {
				result.addAll(pdfParserService
						.getResult(new RequestFoPdfArguments(res, links.getKey(), links.getMetalType())));
			} else {
				result.addAll(htmlParserService
						.getResult(new RequestFoPdfArguments(res, links.getKey(), links.getMetalType())));
			}
		}
		return result;
	}
}
