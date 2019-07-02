package com.berzenin.app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.berzenin.app.model.HostWithPdf;
import com.berzenin.app.service.HostWithPdfService;

@Controller
@RequestMapping(value = "/hosts")
public class HostWithPdfController extends GenericViewControllerImpl<HostWithPdf, HostWithPdfService> {
	
	public 	HostWithPdfController(HostWithPdfService service) {
		page = "hosts";
	}
	
	@ModelAttribute("entityFor")
	public HostWithPdf getEntityForForm () {
		return new HostWithPdf();
	}
}
