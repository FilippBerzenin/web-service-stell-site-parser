package com.berzenin.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.berzenin.app.type.ResourcesType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode (exclude= "id")
@AllArgsConstructor
@Builder
public class LinkForMetalResources {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="host", nullable=false)
	private String host;
	
	@Column(name="resourcesType", nullable=false)
	private ResourcesType resourcesType;
	
	@Column(name="urlForResource", nullable=false)
	private String urlForResource;
	
	@Column(name="localPathForPdfFile", nullable=false)
	private String localPathForPdfFile;
	
	@Column(name="localPathForTxtFile", nullable=false)
	private String localPathForTxtFile;	
	
//    @ManyToOne
//    @JoinColumn(name="id", nullable=false)
//    private Host hostFor;
}
