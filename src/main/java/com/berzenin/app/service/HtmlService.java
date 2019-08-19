package com.berzenin.app.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berzenin.app.model.Host;
import com.berzenin.app.model.LinkForMetalResources;
import com.berzenin.app.model.ResultLine;
import com.berzenin.app.parsers.HtmlParser;
import com.berzenin.app.service.utils.FilesController;
import com.berzenin.app.web.dto.RequestFoPdfArguments;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Service
public class HtmlService {

	@Autowired
	private HtmlParser htmlParser;
	
	@Autowired
	private FilesController filesController;
	
    public static Set<String> uniqueURL = new HashSet<String>();
    public static String my_site;

	public Set<ResultLine> getResult(RequestFoPdfArguments argument) {
		Set<ResultLine> result = htmlParser.parseHtmlTable(argument.getArgs(), argument.getMetalType());
		return result;
	}

	public Host getAllLinksFromHost(Host host) {
		my_site  = host.getUrl();
		get_links(host.getUrl());
		host.setLinksInsideHost(uniqueURL);
		return host;
	}

	private void get_links(String url) {
		String regex = "";
		try {
			Document doc = Jsoup.connect(url).userAgent("chrome").get();
			Elements links = doc.select("a");
			if (links.isEmpty()) {
				return;
			}
			links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((this_url) -> {
				boolean add = false;
				if (this_url.matches(regex) && this_url.contains(my_site)) {
					log.info("URL add: "+this_url);
					add = uniqueURL.add(this_url);	
				}
				if (add) {
					get_links(this_url);
				}
			});
		} catch (IOException e) {
			log.error("Error: "+e.getLocalizedMessage());
		}

	}

	public boolean convertHtmlPageForTxt(LinkForMetalResources res) {
		Document doc;
		try {
			doc = Jsoup.connect(res.getUrlForResource()).get();
			Element body = doc.body();
			String lines[] = body.toString().split("\\r?\\n");
			filesController.writeBytesForTxtFile(res.getLocalPathForTxtFile(), lines);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
