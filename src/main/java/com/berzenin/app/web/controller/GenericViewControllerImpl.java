package com.berzenin.app.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
	protected String namePage = "hosts";
	protected Map <Integer, Set<E>> pageResult;
	protected List<Integer> counterPage;

	
	@Override
	public String showPageWithNumber(@PathVariable("page") int page, Model model) {
		setEntitesFromPage();
		setModelAttribute(model);
		entites = pageResult.get(page);
		message = message+ " All entity from "+page;
		return namePage;
	}
	
	@Override
	public String uploadById(Long id, Model model) {
		if (service.uploadById(id)) {
			setEntitesFromPage();
			message = message + " Entity with id "+id+" succeseful upload ";
		}
		setEntitesFromPage();
		message = message+  "Entity with id "+id+" failed upload ";
		setModelAttribute(model);
		return namePage;
	}
	
	private void setEntitesFromPage() {
		entites = service.findAll();
		counterPage = new ArrayList<>();
		int count = entites.size();
		pageResult = new HashMap<>();
		Iterator<E> iterator = entites.iterator();
		for (int i = 0, g = 1; i<count; i=i+10, g++) {
				Set<E> points = new HashSet<>(); 
				counterPage.add(g);
				for (int j = 0;iterator.hasNext() && j<10;j++) {
					points.add(iterator.next());
				}
				pageResult.put(g, points);
		}
	}
	
	@Override
	public String findAll(Model model) {
		message = "All entity";
		entites = service.findAll();
		if (entites.size()>10) {
			showPageWithNumber(1, model);
		}
		setModelAttribute(model);
		return namePage;
	}	
	
	@Override
	public String findById(Long id, Model model) {
		try {
			entites = Arrays.asList(service.findById(id)).stream().collect(Collectors.toSet());
			showPageWithNumber(1, model);
			setModelAttribute(model);
			return namePage;	
		} catch (RuntimeException e) {
			this.setModelAttributeWhenthrowException(e, model);
			return namePage;
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
				return namePage;
			}
			try {
				service.add(entity);
				message = "Entity was successful save";
				showPageWithNumber(1, model);
				findAll(model);
				setModelAttribute(model);
				return namePage;
			} catch (RuntimeException e) {
				this.setModelAttributeWhenthrowException(e, model);
				return namePage;
			}
		 }
	}

	public String deleteEntity(@PathVariable("id") Long id, Model model) {
		try {
			service.removeById(id);
			showPageWithNumber(1, model);
			findAll(model);
			message = id+ " Successfully deleted.";
			return namePage;
		} catch (RuntimeException e) {
			log.info("Delete failed" + e);
			message = id+ " delete failed.";
			return namePage;
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
			return namePage;
		}
		try {
			service.update(entity);
			message = "Entity was successful update";
			entites = service.findAll();
			setModelAttribute(model);
			return namePage;
		} catch (RuntimeException e) {
			this.setModelAttributeWhenthrowException(e, model);
			return namePage;
		}
	}
	
	protected void setModelAttribute(Model model) {
		model.addAttribute("countPage", counterPage);
		model.addAttribute("page", namePage);
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