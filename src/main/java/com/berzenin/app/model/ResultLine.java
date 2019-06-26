package com.berzenin.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResultLine {
	
	public ResultLine(int countEquals, String line) {
		this.countEquals = countEquals;
		this.line = line;
	}
	private int countEquals;
	private String line;

}
