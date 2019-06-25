package com.berzenin.app.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class IndexViewController {
	
	@RequestMapping(value = {"/", "/index" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String index(Model model) {
		String message = "Abandon hope all ye who enter here";
		model.addAttribute("message", message);
		return "index";
	}
}
