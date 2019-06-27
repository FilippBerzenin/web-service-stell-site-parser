package com.berzenin.app.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestFoPdfArguments {
	
	private String pathForLink;
	
	private String[] args;
	
	private String argument;

}
