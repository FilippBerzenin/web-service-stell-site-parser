package com.berzenin.app.web.dto;

import com.berzenin.app.model.LinkForMetalResources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestFoPdfArguments {
	
	
	public RequestFoPdfArguments(LinkForMetalResources link, String[] args, String metalType) {
		this.link = link;
		this.args = args;
		this.metalType= metalType;
	}
	
	private LinkForMetalResources link;

	private String[] args;
	
	private String argument;
	
	private String metalType;

}
