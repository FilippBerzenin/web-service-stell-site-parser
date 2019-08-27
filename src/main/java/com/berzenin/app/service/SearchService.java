package com.berzenin.app.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

	public List<ResultLine> distributorForLineSearch(Links links) {
		ExecutorService service = Executors.newFixedThreadPool(links.getLinksFor().length);
		List<ResultLine> result = new LinkedList<>();
		for (String link : links.getLinksFor()) {
			LinkForMetalResources res = linkForMetalResourcesService.findByLink(link).get(0);
			pdfParserService.setArgument(new RequestFoPdfArguments(res, links.getKey(), links.getMetalType()));
			if (res.getResourcesType().equals(ResourcesType.HOST_RESOURCE)) {
				linkSearcher.getAllByHost(res.getHost()).stream().forEach(r -> {						
						try {
							Future<Optional<List<ResultLine>>> task = service.submit(pdfParserService);
							while (!task.isDone()) {
								result.addAll(task.get().get());	
							}
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
					});
			} else {
				try {
					Future<Optional<List<ResultLine>>> task = service.submit(pdfParserService);
					while (!task.isDone()) {
						result.addAll(task.get().get());	
					}
					}  catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
			}
		}
		service.shutdown();
		return result;
	}
	
	
}
