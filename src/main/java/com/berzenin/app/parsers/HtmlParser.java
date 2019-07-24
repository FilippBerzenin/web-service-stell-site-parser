package com.berzenin.app.parsers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.berzenin.app.model.ResultLine;

@Service
public class HtmlParser extends MainParser {

	public Set<ResultLine> parseHtmlTable(String[] args, String url) {
		Set<ResultLine> lines = new HashSet<>();
//		Document doc = getDocumentFormUrl(url);
//		Elements e = doc.select("table");
//		e.forEach(t -> lines.addAll(this.parseTable(args, url, t, metalType)));
		return lines;
	}

	public Set<ResultLine> parseTable(String[] args, String url, Element table, String metalType) {
		Elements rows = table.select("tr");
//		String host = this.getHostName(url);
		Set<ResultLine> lines = new HashSet<>();
//		int row = 0;
//		for (Element e : rows) {
//			Set<String> keys = new HashSet<>();
//			row++;
//			for (String arument : args) {
//				int count = 0;
//				if (e.text().toLowerCase().contains(metalType.toLowerCase())) {
//					count++;
//					keys.add(metalType);
//					if (e.text().toLowerCase().contains(arument.toLowerCase())) {
//						for (String a : args) {
//							if (e.text().contains(a)) {
//								count++;
//								keys.add(a);
//							}
//						}
//					}
//				}
//				if (count >= 1) {
//					lines.add(new ResultLine(host, count, e.text(), row, metalType, keys, url));
//				}
//			}
//		}
		return lines;
	}

	public Document getDocumentFormUrl(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	public List<Element> findKeywordFromHtml(String tag, String keyword, Document doc) {
		Elements metaElements = null;
		metaElements = doc.select(tag);
		List<Element> res = metaElements.stream().filter(e -> e.text().toLowerCase().contains(keyword.toLowerCase()))
				.collect(Collectors.toList());
		return res;
	}
}
