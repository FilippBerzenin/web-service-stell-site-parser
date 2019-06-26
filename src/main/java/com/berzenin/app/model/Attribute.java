package com.berzenin.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Attribute {
	
	private String brand;
	private String state;
	private String form;
	private int dimensionalRangesMin;
	private int dimensionalRangesMax;

}
