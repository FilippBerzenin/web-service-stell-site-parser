package com.berzenin.app.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.berzenin.app.model.ResultLine;
import com.berzenin.app.service.PdfParserService;
import com.berzenin.app.web.dto.RequestFoPdfArguments;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PdfSearchViewController {
	
	@Autowired
	private PdfParserService service;
	
	private List<ResultLine> result;
	
	@RequestMapping(value = "/searchPdf", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("atributeForPdf", new RequestFoPdfArguments());
		return "searchPdf";
	}
	
	@RequestMapping(value = "/searchPdf", method = RequestMethod.POST)
	public String searchAdd (
			Model model,
			@Valid RequestFoPdfArguments requestFoPdfArguments,
			BindingResult bindingResult
			) {
		if (bindingResult.hasErrors()) {
			return "error";
		}
		requestFoPdfArguments.setArgs(new String[]{"X120Mn12", "1.3401"});
		service.getResult(requestFoPdfArguments); 
		result = service.getResult(requestFoPdfArguments);
		model.addAttribute("result", result);
		return "result";		
	}

}
