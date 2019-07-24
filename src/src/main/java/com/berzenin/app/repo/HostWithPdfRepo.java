package com.berzenin.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.berzenin.app.model.HostWithPdf;

@Repository
public interface HostWithPdfRepo extends JpaRepository <HostWithPdf, Long> {
	
	public HostWithPdf findByHost(String host);
	
	public HostWithPdf findBylinkForPdfFile (String linkForPdfFile);

}
