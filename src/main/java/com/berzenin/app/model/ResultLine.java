package com.berzenin.app.model;

import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode (of = {"link", "host", "line"})
public class ResultLine {
	
	private String host;
	private int countEquals;
	private String line;
	private String link;
	private int numberOfLine;
	private Set<String> keywords;
	private String metalType;

		
	public ResultLine(String host, int countEquals, String line, int numberOfLine, String metalType, Set<String> keywords, String link) {
		this.host = host;
		this.countEquals = countEquals;
		this.line = line;
		this.numberOfLine=numberOfLine;
		this.keywords=keywords;
		this.link=link;
		this.metalType=metalType;
	}

}
