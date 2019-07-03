package com.berzenin.app.parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.berzenin.app.model.ResultLine;

import ch.qos.logback.core.net.SyslogOutputStream;

public class HtmlParser {
	
	public Document getDocumentFormUrl (String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	public void parserHtml(String[] keywords) {
		String url = "https://www.bemorail.com/portfolio-item/vignola-rail/";
		Document doc = getDocumentFormUrl(url);
		String search = "S24";
		String tag = "td";
		String r = doc.body().text();
		
		System.out.println(r);
		Set<Element> result = new HashSet<>();
		for (String keyword: keywords) {
			List<Element> resul = this.findKeywordFromHtml(tag, keyword, doc);
			if (!resul.equals(null) || resul.size()>0) {
				for (String word: keywords) {
				this.checkLineForContainsAnotherKeyWord(resul, word);
				}
			}
			result.addAll(findKeywordFromHtml(tag, keyword, doc));
		}
//		result.forEach(s -> System.out.println("Result: "+s.text()));
	}
	
	public List<Element> findKeywordFromHtml (String tag, String keyword, Document doc) {
		Elements metaElements = null;
		metaElements = doc.select(tag);
		List<Element> res = metaElements.stream()
				.filter(e -> e.text().toLowerCase()
				.contains(keyword.toLowerCase()))
				.collect(Collectors.toList());
		return res;
	}
	
	public boolean checkLineForContainsAnotherKeyWord (List<Element> result, String keyword) {
		List<String[]> elements = new ArrayList<>();
		
		int count = 0;
		for (Element e: result) {
			String words = e.getAllElements().text();

		}
		
		
		return false;
		
	}
	
	public void parseTable (Document doc) {
		ArrayList<String> downServers = new ArrayList<>();
		Element table = doc.select("table").get(0); //select the first table.
		Elements rows = table.select("tr");
		String arg = "S24 DIN 5904 12 120";
		String[] args = arg.trim().split(" ");
		Set<ResultLine> lines = new HashSet<>();
//		Set<Element> el = new HashSet<>();
		int row = 0;
		for (Element e: rows) {
			Set<String> keywords = new HashSet<>();
			row++;
			for (String arument: args) {
				int count = 0;
				if (e.text().contains(arument)) {
					for (String a: args) {
						if (e.text().contains(a)) {
							count++;
							keywords.add(a);
						}
					}
					if (count>=2) {
						lines.add(new ResultLine("host", count, e.text(), row, keywords));
					}
				}
			}
		}
		lines.forEach(s -> System.out.println("line "+s));

//		for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
//		    Element row = rows.get(i);
//		    Elements cols = row.select("td");
//
//		    if (cols.get(7).text().equals("down")) {
//		        downServers.add(cols.get(5).text());
//		    }
//		}
//		downServers.forEach(System.out::println);
	}
}
