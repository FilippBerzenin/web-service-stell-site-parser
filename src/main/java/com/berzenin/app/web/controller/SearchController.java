package com.berzenin.app.web.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.berzenin.app.model.LinkForMetalResources;
import com.berzenin.app.model.Links;
import com.berzenin.app.model.ResultLine;
import com.berzenin.app.service.LinkForMetalResourcesService;
import com.berzenin.app.service.SearchService;

@Controller
@RequestMapping(value = "/linksForSearch")
public class SearchController extends GenericViewControllerImpl<LinkForMetalResources, LinkForMetalResourcesService> {

	@Autowired
	private SearchService searchService;

	@Autowired
	private LinkForMetalResourcesService linkSearcher;

	public SearchController() {
		namePage = "linksForSearch";
	}

	@ModelAttribute("links")
	public Links getListofLinks() {
		return new Links();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getAllLinks(Model model) {
		model.addAttribute("message", "Get all links");
		model.addAttribute("page", namePage);
		model.addAttribute("listOfEntites", linkSearcher.findAll());
		return namePage;
	}

	@RequestMapping(value = {"/multiSerching"}, method = RequestMethod.POST)
	public String searchForManyLinks(@ModelAttribute(name = "links") @Valid Links links, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("message", "Error");
			model.addAttribute("page", namePage);
			model.addAttribute("listOfEntites", linkSearcher.findAll());
			return namePage;
		}
		links.setKey(links.getKeywords().split(" "));
		model.addAttribute("message", "Get result");
		model.addAttribute("page", namePage);
		List<ResultLine> result = null;
		try {
			result = searchService.distributorForLineSearch(links);
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("result", result);
		model.addAttribute("listOfEntites", linkSearcher.findAll());
		return namePage;
	}
}
