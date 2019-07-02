package com.berzenin.app.service;

import org.springframework.stereotype.Service;

import com.berzenin.app.model.HostWithPdf;
import com.berzenin.app.repo.HostWithPdfRepo;

@Service
public class HostWithPdfService extends GenericServiceImpl <HostWithPdf, HostWithPdfRepo> {
	
	public HostWithPdfService(HostWithPdfRepo repository) {
		super(repository);
	}
}
