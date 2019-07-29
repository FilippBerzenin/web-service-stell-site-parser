package com.berzenin.app.web.controller;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@Override
	public String findAll(HttpServletRequest request, Model model) {
		message = "All entity";
		entites = service.findAll();
		PagedListHolder pagedListHolder = new PagedListHolder(entites.stream().collect(Collectors.toList()));
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		pagedListHolder.setPage(page);
		pagedListHolder.setPageSize(3);
		model.addAttribute("pagedListHolder", pagedListHolder);
		setModelAttribute(model);
		return namePage;
	}	
	
	@Override
	public String findById(Long id, Model model) {
		try {
			entites = Arrays.asList(service.findById(id)).stream().collect(Collectors.toSet());
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
				entites = service.findAll();
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
			entites = service.findAll();
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