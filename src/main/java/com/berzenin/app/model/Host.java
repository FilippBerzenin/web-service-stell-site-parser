package com.berzenin.app.model;

import java.util.Set;

import lombok.Data;

@Data
public class Host {
	
	private Long id;
	
	private String url;
	
	private Set<String> linksInsideHost;

}
