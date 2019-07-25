package com.berzenin.app.service;

import java.io.IOException;
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
import com.berzenin.app.web.dto.RequestFoPdfArguments;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class HtmlService {

	@Autowired
	private HtmlParser htmlParser;

	public Set<ResultLine> getResult(RequestFoPdfArguments argument) {
		Set<ResultLine> result = htmlParser.parseHtmlTable(argument.getArgs(), argument.getMetalType());
		return result;
	}

	public Host getAllLinksFromHost(Host host) {
		Document document;
		try {
			// Get Document object after parsing the html from given url.
			document = Jsoup.connect(host.getUrl()).get();

			// Get links from document object.
			Elements links = document.select("a[href]");

			// Iterate links and print link attributes.
			for (Element link : links) {
				System.out.println("Link: " + link.attr("href"));
				System.out.println("Text: " + link.text());
				System.out.println("");
			}
			return host;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return host;
	}

	public boolean convertHtmlPageForTxt(LinkForMetalResources res) {
		Document doc;
		try {
			doc = Jsoup.connect(res.getUrlForResource()).get();
			Element body = doc.body();

			System.out.println(body.toString());
			String lines[] = body.toString().split("\\r?\\n");
			htmlParser.writeBytesForTxtFile(res.getLocalPathForTxtFile(), lines);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
