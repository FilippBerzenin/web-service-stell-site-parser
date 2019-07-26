package com.berzenin.app.model;

import java.util.Set;

import javax.persistence.*;

import lombok.Data;

//@Entity
@Data
public class Host {
	
//	@Id
	private Long id;
	
//	@Column (nullable=false)
	private String url;
	
//	@Column
	private Set<String> linksInsideHost;

}
