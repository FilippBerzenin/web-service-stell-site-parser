package com.berzenin.app.web.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.berzenin.app.model.LinkForMetalResources;
import com.berzenin.app.service.LinkForMetalResourcesService;
import com.berzenin.app.type.ResourcesType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/linksForResources")
public class LinkMetalResourcesController
		extends GenericViewControllerImpl<LinkForMetalResources, LinkForMetalResourcesService> {

	public LinkMetalResourcesController(LinkForMetalResourcesService service) {
		page = "linksForResources";
	}

	@ModelAttribute("entityFor")
	public LinkForMetalResources getEntityForForm() {
		return new LinkForMetalResources();
	}

	@RequestMapping(value = "/addPdfFile")
	public String addPdfFile(@RequestParam("file") MultipartFile file, Model model) {
		LinkForMetalResources entity = LinkForMetalResources.builder()
				.host("localhost")
				.resourcesType(ResourcesType.LOCAL_PDF)
				.urlForResource("clients file")
				.localPathForPdfFile(service.getLocalPathForPdf(file).toString())
				.localPathForTxtFile(service.getLocalPathForPdf(file).toString().replaceAll("pdf", "txt"))
				.build();
		if (this.checkIfLinkInData(entity)) {
			message = "This link is already in the database.";
			entites = service.findAll();
			setModelAttribute(model);
			return page;
		}
		try {
			if (service.copyFileForlocalDirectory(entity, file)) {
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

	@Override
	public String add(@ModelAttribute("entity") @Valid LinkForMetalResources entity, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			message = "Something wrong with parameters";
			setModelAttribute(model);
			return page;
		}
		try {
			String name = service.setPdfFileName(entity.getUrlForResource());
			String localPath = service.setPathForFile(entity.getUrlForResource());
			String url = entity.getUrlForResource();
			entity = LinkForMetalResources.builder()
				.host(service.getHostNameFromUrl(entity.getUrlForResource()))
				.resourcesType(ResourcesType.REMOTE_PDF)
				.urlForResource(url)
				.localPathForPdfFile(localPath + name)
				.localPathForTxtFile(localPath + name.replace("pdf", "txt"))
				.build();
			if (this.checkIfLinkInData(entity)) {
				message = "This link is already in the database.";
				entites = service.findAll();
				setModelAttribute(model);
				return page;
			}
			service.add(entity);
			message = "Entity was successful save";
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

	public boolean checkIfLinkInData(LinkForMetalResources entity) {
		LinkForMetalResources find = service.getHostWithPdfByLinkForPdfFile(entity.getLocalPathForPdfFile());
		if (find == null) {
			return false;
		}
		if (find.equals(entity)) {
			return true;
		}
		return true;
	}
}
