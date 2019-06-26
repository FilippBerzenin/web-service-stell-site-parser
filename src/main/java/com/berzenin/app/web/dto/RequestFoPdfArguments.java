package com.berzenin.app.web.dto;

import lombok.Data;

@Data
public class RequestFoPdfArguments {
	
	private String pathForLink;
	
	private String[] args;

}
