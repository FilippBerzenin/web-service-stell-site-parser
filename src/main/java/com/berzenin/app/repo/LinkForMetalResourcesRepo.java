package com.berzenin.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.berzenin.app.model.LinkForMetalResources;

@Repository
public interface LinkForMetalResourcesRepo extends JpaRepository <LinkForMetalResources, Long> {
	
	public LinkForMetalResources findByHost(String host);
	
	public List<LinkForMetalResources> findByLocalPathForPdfFile (String linkForPdfFile);
	
	public List<LinkForMetalResources> findByLocalPathForTxtFile (String localPathForTxtFile);

	public List<LinkForMetalResources> findAllByHost(String host);
}
