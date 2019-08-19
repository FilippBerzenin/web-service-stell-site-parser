package com.berzenin.app.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.berzenin.app.model.Host;
import com.berzenin.app.model.LinkForMetalResources;
import com.berzenin.app.service.LinkForMetalResourcesService;
import com.berzenin.app.service.utils.FilesController;
import com.berzenin.app.type.ResourcesType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/linksForResources")
public class LinkMetalResourcesController
		extends GenericViewControllerImpl<LinkForMetalResources, LinkForMetalResourcesService> {

	@Autowired
	private FilesController filesController;

	
	public LinkMetalResourcesController(LinkForMetalResourcesService service) {
		namePage = "linksForResources";
	}

	@ModelAttribute("entityFor")
	public LinkForMetalResources getEntityForForm() {
		return new LinkForMetalResources();
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public String deleteEntity(@PathVariable("id") Long id, Model model) {
		try {
			String path = service.findById(id).getUrlForResource();
			if (service.findById(id).getResourcesType().equals(ResourcesType.HOST_RESOURCE)) {
				service.deleteAllLinksFromHostResources(service.findById(id).getHost());
				filesController.deleteFile(filesController.setPathForFile(path));
				GenericViewControllerImpl.message = id + " Successfully deleted.";
			} else {
			service.deleteLink(service.findById(id));
			filesController.deleteFile(filesController.setPathForFile(path));
			message = id + " Successfully deleted.";
			return namePage;
			}
		} catch (RuntimeException e) {
			log.info("Delete failed" + e);
			message = id + " delete failed.";
			return namePage;
		} finally {
			setModelAttribute(model);
			showPageWithNumber(1, model);
		}
		return namePage;
	}

	@RequestMapping(value = "/addHost")
	public String addHost(@RequestParam("host") String host, Model model) {
		Host newHost = new Host();
		newHost.setUrl(host);
		newHost = service.getLinskFromHost(newHost);
		for (String s : newHost.getLinksInsideHost()) {
			if (s == null || s.isEmpty() || s.contains("mailto") || s.contains("maps.google.com")) {
				continue;
			}
			LinkForMetalResources link = new LinkForMetalResources();
			link.setResourcesType(ResourcesType.HOST_RESOURCE);
			link.setUrlForResource(s);
			try {
				service.add(link);
				message = "Host was successful save";
			} catch (RuntimeException e) {
				log.error(e.getLocalizedMessage());
			}
		}
		showPageWithNumber(1, model);
		setModelAttribute(model);
		return namePage;
	}

	@RequestMapping(value = "/addPdfFile")
	public String addPdfFile(@RequestParam("file") MultipartFile file, Model model) {
		LinkForMetalResources entity = LinkForMetalResources.builder().host("localhost")
				.resourcesType(ResourcesType.LOCAL_PDF)
				.urlForResource(filesController.getLocalPathForPdf(file).toString())
				.localPathForPdfFile(filesController.getLocalPathForPdf(file).toString())
				.localPathForTxtFile(filesController.getLocalPathForPdf(file).toString().replaceAll("pdf", "txt")).build();
		if (this.checkIfLinkInData(entity)) {
			message = "This link is already in the database.";
			showPageWithNumber(1, model);
			setModelAttribute(model);
			return namePage;
		}
		try {
			service.addPdf(entity, file);
			showPageWithNumber(1, model);
			return namePage;
		} catch (RuntimeException e) {
			log.info(e.getMessage());
			e.printStackTrace();
			this.setModelAttributeWhenthrowException(e, model);
			return namePage;
		}
	}

	@Override
	public String add(@ModelAttribute("entity") @Valid LinkForMetalResources entity, BindingResult result,
			Model model) {
		if (result.hasErrors() || entity.getUrlForResource().isEmpty() ) {
			message = "";
			message = "Something wrong with parameters";
			showPageWithNumber(1, model);
			setModelAttribute(model);
			return namePage;
		}
		service.add(entity);
		message = "Entuty add to collection" + entity.toString();
		showPageWithNumber(1, model);
		setModelAttribute(model);
		return namePage;
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
