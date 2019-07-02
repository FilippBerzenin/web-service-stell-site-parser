package com.berzenin.app.web.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.berzenin.app.model.HostWithPdf;
import com.berzenin.app.model.Links;
import com.berzenin.app.model.ResultLine;
import com.berzenin.app.service.HostWithPdfService;
import com.berzenin.app.service.PdfParserService;
import com.berzenin.app.web.dto.RequestFoPdfArguments;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/linksForSearch")
public class LinksController extends GenericViewControllerImpl<HostWithPdf, HostWithPdfService> {
	
	@Autowired
	private HostWithPdfService hostService;
	
	@Autowired
	private PdfParserService pdfParserService;

	public LinksController() {
		page = "linksForSearch";
	}

	@ModelAttribute("links")
	public Links getListOfHostWithPdf() {
		return new Links();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getAllLinks(Model model) {
		model.addAttribute("message", "Get all links");
		model.addAttribute("page", page);
		model.addAttribute("listOfEntites", hostService.findAll());
		return page;
	}

	@RequestMapping(value = "/multiSerching", method = RequestMethod.POST)
	public String searchForManyLinks(
			@ModelAttribute(name = "links") @Valid Links links,
			BindingResult bindingResult, 
			Model model) {
		if (bindingResult.hasErrors() || links.getKeywords().length()==0) {
			model.addAttribute("message", "Error");
			model.addAttribute("page", page);
			model.addAttribute("listOfEntites", hostService.findAll());
			return page;
		}
		links.setKey(links.getKeywords().split(" "));
		model.addAttribute("message", "Get result");
		model.addAttribute("page", page);
		log.info(links.toString());
		List<ResultLine> result = new LinkedList<>();
		for (String link: links.getLinksFor()) {
			result.addAll(pdfParserService.getResult(new RequestFoPdfArguments(link, links.getKey())));
			result.forEach(System.out::println);
		}
		model.addAttribute("result", result);
		model.addAttribute("listOfEntites", hostService.findAll());
		return page;
	}

}
