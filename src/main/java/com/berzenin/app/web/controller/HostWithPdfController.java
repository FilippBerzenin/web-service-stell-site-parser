package com.berzenin.app.web.controller;

import java.net.MalformedURLException;
import java.net.URL;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.berzenin.app.model.HostWithPdf;
import com.berzenin.app.service.HostWithPdfService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	
	@Override
	public String add(
			@ModelAttribute("entity") @Valid HostWithPdf entity,
			BindingResult result, 
			Model model) {
		 {if (result.hasErrors()) {
				message = "Something wrong with parameters";
				setModelAttribute(model);
				return page;
			}
			try {
				entity.setHost(new URL(entity.getLinkForPdfFile()).getHost());
				service.add(entity);
				message = "Entity was successful save";
				entites = service.findAll();
				setModelAttribute(model);
				return page;
			}  catch (MalformedURLException e) {
				log.info(e.getMessage());
				e.printStackTrace();
				return page;
			}
			catch (RuntimeException e) {
				log.info(e.getMessage());
				e.printStackTrace();
				this.setModelAttributeWhenthrowException(e, model);
				return page;
			}
		 }
	}
}
