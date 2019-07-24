package com.berzenin.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.berzenin.app.model.LinkForMetalResources;

@Repository
public interface LinkForMetalResourcesRepo extends JpaRepository <LinkForMetalResources, Long> {
	
	public LinkForMetalResources findByHost(String host);
	
	public LinkForMetalResources findByLocalPathForPdfFile (String linkForPdfFile);
	
	public LinkForMetalResources findByLocalPathForTxtFile (String localPathForTxtFile);

}
