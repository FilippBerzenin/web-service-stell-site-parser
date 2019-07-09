package com.berzenin.app.web.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	
	@RequestMapping(value = "/addPdfFile")
	public String addPdfFile(@RequestParam("file") MultipartFile file, Model model) {
		{
			try {
				HostWithPdf entity = new HostWithPdf();
				entity.setLinkForPdfFile(file.getOriginalFilename());
				Path path = service.addPdfFile(file);
				if (path.getFileName().toString().length()>=0) {
					entity.setLinkForPdfFile(path.toString());
					entity.setHost("localhost");
					service.add(entity);
					message = "File was successful save";
					entites = service.findAll();
					setModelAttribute(model);
					return page;
				}
				message = "Thomething wrong";
				entites = service.findAll();
				setModelAttribute(model);
				return page;
			} catch (RuntimeException e) {
				log.info(e.getMessage());
				e.printStackTrace();
				this.setModelAttributeWhenthrowException(e, model);
				return page;
			}
		}
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
				message = "Something wrong with parameters";
				entites = service.findAll();
				setModelAttribute(model);
				return page;
			} catch (RuntimeException e) {
				log.info(e.getMessage());
				e.printStackTrace();
				this.setModelAttributeWhenthrowException(e, model);
				return page;
			}
		 }
	}
}
