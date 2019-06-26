package com.berzenin.app.parsers;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class MainParser {
	
	public void getResultFromWebSite () {
		try {
		       Document doc = Jsoup.connect("http://eclipse.org").get();  
		       String title = doc.title();  
		       System.out.println("Title : " + title);  
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
