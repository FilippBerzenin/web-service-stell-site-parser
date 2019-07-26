package com.berzenin.app.web.controller;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.berzenin.app.service.GenericService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public abstract class GenericViewControllerImpl<E, S extends GenericService<E>> implements GenericViewController<E> {

	@Autowired
	protected S service;
	
	public static String message = "Something wrong";
	public Set<E> entites;
	protected String page = "hosts";
	
	@Override
	public String findAll(Model model) {
		message = "All entity";
		entites = service.findAll();
		setModelAttribute(model);
		return page;
	}	
	
	@Override
	public String findById(Long id, Model model) {
		try {
			entites = Arrays.asList(service.findById(id)).stream().collect(Collectors.toSet());
			setModelAttribute(model);
			return page;	
		} catch (RuntimeException e) {
			this.setModelAttributeWhenthrowException(e, model);
			return page;
		}
	}
	
	@Override
	public String add(
			@ModelAttribute("entity") @Valid E entity,
			BindingResult result, 
			Model model) {
		 {if (result.hasErrors()) {
				message = "Something wrong with parameters";
				setModelAttribute(model);
				return page;
			}
			try {
				service.add(entity);
				message = "Entity was successful save";
				entites = service.findAll();
				setModelAttribute(model);
				return page;
			} catch (RuntimeException e) {
				this.setModelAttributeWhenthrowException(e, model);
				return page;
			}
		 }
	}

	public String deleteEntity(@PathVariable("id") Long id, Model model) {
		try {
			service.removeById(id);
			entites = service.findAll();
			message = id+ " Successfully deleted.";
			return page;
		} catch (RuntimeException e) {
			log.info("Delete failed" + e);
			message = id+ " delete failed.";
			return page;
		} finally {
			setModelAttribute(model);
		}
	}

	public String update(
			@ModelAttribute("entity") @Valid E entity,
			BindingResult result, 
			Model model) {
		if (result.hasErrors()) {
			message = "Something wrong with attributes";
			setModelAttribute(model);
			return page;
		}
		try {
			service.update(entity);
			message = "Entity was successful update";
			entites = service.findAll();
			setModelAttribute(model);
			return page;
		} catch (RuntimeException e) {
			this.setModelAttributeWhenthrowException(e, model);
			return page;
		}
	}
	
	protected void setModelAttribute(Model model) {
		model.addAttribute("page", page);
		model.addAttribute("message", message);
		model.addAttribute("listOfEntites", entites);
	}
	
	protected void setModelAttributeWhenthrowException (Exception e, Model model) {
		log.error("Error "+e);
		message = "Error "+e;
		entites = service.findAll();
		setModelAttribute(model);
	}
}