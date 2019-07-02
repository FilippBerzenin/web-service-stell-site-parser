package com.berzenin.app.web.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface GenericViewController<E> {

	@RequestMapping(value="/show/all", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	String findAll(Model model);
	
	@RequestMapping(value="/show/{id}", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	String findById(@PathVariable("id") Long id, Model model);
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	String deleteEntity(@PathVariable("id") Long id, Model model);
	
	@RequestMapping(value="/update/", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	String update(@ModelAttribute("entity") @Valid E entity,
			BindingResult result, 
			Model model);
	
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	String add(
			@ModelAttribute("entity") @Valid E entity,
			BindingResult result, 
			Model model);
}