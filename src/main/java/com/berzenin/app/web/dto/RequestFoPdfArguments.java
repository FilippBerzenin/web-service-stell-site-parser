package com.berzenin.app.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestFoPdfArguments {
	
	
	public RequestFoPdfArguments(String pathForLink, String[] args) {
		this.pathForLink = pathForLink;
		this.args = args;
	}

	private String pathForLink;
	
	private String[] args;
	
	private String argument;

}
