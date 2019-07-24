package com.berzenin.app.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

	private Set<URL> allLinks;

	public List<ResultLine> distributorForLineSearch(Links links) throws IOException {
		List<ResultLine> result = new LinkedList<>();
		for (String link : links.getLinksFor()) {
			if (link.endsWith("pdf")) {
				result.addAll(pdfParserService.getResult(new RequestFoPdfArguments(link, links.getKey(), links.getMetalType())));
			} else {
				result.addAll(htmlParserService.getResult(new RequestFoPdfArguments(link, links.getKey(), links.getMetalType())));
			}
		}
		return result;
	}

	// Not used
	public void getAllLinks (String url) {
		allLinks = new HashSet<>();
		this.getAllLinesFromHost(url);
		allLinks.forEach(System.out::println);
	}

	// Not used
	public Set<URL> getAllLinesFromHost(String url) {
		Document document;
		try {
			document = Jsoup.connect(url).get();	 
			Elements links = document.select("a[href]");	 
			for (Element link : links) {
				URL workLink = null;
				try  {
					workLink = new URL(link.attr("href"));	
					System.out.println("work: "+workLink);
				} catch (MalformedURLException e) {
				}
				if (workLink!=null && workLink.getHost().equals(new URL(url).getHost())) {
					allLinks.add(workLink);
				}
			}	 
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("finish");
		return null;	
	}
}
