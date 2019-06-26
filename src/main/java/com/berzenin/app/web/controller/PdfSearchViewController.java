package com.berzenin.app.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.berzenin.app.service.PdfParserService;
import com.berzenin.app.web.dto.RequestFoPdfArguments;

@Controller
@RequestMapping(value = "/searchFromPdf")
public class PdfSearchViewController {
	
	@Autowired
	private PdfParserService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("atributeForPdf", new RequestFoPdfArguments());
		return "searchFromPdf";
	}
	
	public String searchAdd (
			@Valid RequestFoPdfArguments requestFoPdfArguments,
			BindingResult bindingResult
			) {
		if (bindingResult.hasErrors()) {
			return "error";
		}
		service.getResult(requestFoPdfArguments); 
		requestFoPdfArguments.toString();
		return "searchFromPdf";		
	}

}
