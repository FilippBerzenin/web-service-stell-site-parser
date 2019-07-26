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
	private LinkForMetalResourcesService linkSearcher;
	
	@Autowired
	private LinkForMetalResourcesService linkForMetalResourcesService;

	public List<ResultLine> distributorForLineSearch(Links links) throws IOException {
		List<ResultLine> result = new LinkedList<>();
		for (String link : links.getLinksFor()) {
			LinkForMetalResources res = linkForMetalResourcesService.findByLink(link);
			if (res.getResourcesType().equals(ResourcesType.HOST_RESOURCE)) {
				linkSearcher.getAllByHost(res.getHost()).stream().forEach(r -> {
					try {
						result.addAll(pdfParserService
								.getResult(new RequestFoPdfArguments(r, links.getKey(), links.getMetalType())));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
				result.addAll(pdfParserService
						.getResult(new RequestFoPdfArguments(res, links.getKey(), links.getMetalType())));
		}
		return result;
	}
}
