package com.berzenin.app.service.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public abstract class AbstractFilesController {
	
	public String getHostNameFromUrl(String url) {
		URL partOfurl = null;
		try {
			partOfurl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return partOfurl.getHost();
	}

	public List<String> removeWhitespaces (List<String> list) {
		return list.stream()
				.map(s -> s.trim())
				.map(s -> s.replaceAll("\\s+", " "))
				.collect(Collectors.toList());
	}

}
