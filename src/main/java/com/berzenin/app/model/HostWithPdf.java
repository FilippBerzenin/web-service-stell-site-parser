package com.berzenin.app.model;

import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class HostWithPdf {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="host", nullable=false)
	private String host;
	
	@Column(name="linkForPdfFile", nullable=false)
	private String linkForPdfFile;
		
	public HostWithPdf(String linkForPdfFile) {
		this.linkForPdfFile = linkForPdfFile;
	}
}
