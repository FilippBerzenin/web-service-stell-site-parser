package com.berzenin.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResultLine {
	
	private String host;
	private int countEquals;
	private String line;
		
	public ResultLine(String host, int countEquals, String line) {
		this.host = host;
		this.countEquals = countEquals;
		this.line = line;
	}

}
