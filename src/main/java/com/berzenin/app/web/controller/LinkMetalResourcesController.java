package com.berzenin.app.web.controller;

import javax.validation.Valid;

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
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public String deleteEntity(@PathVariable("id") Long id, Model model) {
		if (service.findById(id).getResourcesType().equals(ResourcesType.HOST_RESOURCE)) {
			service.deleteAllLinksFromHostResources(service.findById(id).getHost());
			entites = service.findAll();
			GenericViewControllerImpl.message = id+ " Successfully deleted.";
		}
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

	@RequestMapping(value = "/addHost")
	public String addHost(@RequestParam("host") String host, Model model) {
		Host newHost = new Host();
		newHost.setUrl(host);
		newHost = service.getLinskFromHost(newHost);
		for (String s : newHost.getLinksInsideHost()) {
			if (s == null || s.isEmpty() || s.contains("mailto")) {
				continue;
			}
			LinkForMetalResources link = new LinkForMetalResources();
			link.setResourcesType(ResourcesType.HOST_RESOURCE);
			link.setUrlForResource(s);
			service.add(link);
			message = "Host was successful save";
			entites = service.findAll();
			setModelAttribute(model);
		}
		return page;
	}

	@RequestMapping(value = "/addPdfFile")
	public String addPdfFile(@RequestParam("file") MultipartFile file, Model model) {
		LinkForMetalResources entity = LinkForMetalResources.builder().host("localhost")
				.resourcesType(ResourcesType.LOCAL_PDF).urlForResource(service.getLocalPathForPdf(file).toString())
				.localPathForPdfFile(service.getLocalPathForPdf(file).toString())
				.localPathForTxtFile(service.getLocalPathForPdf(file).toString().replaceAll("pdf", "txt")).build();
		if (this.checkIfLinkInData(entity)) {
			message = "This link is already in the database.";
			entites = service.findAll();
			setModelAttribute(model);
			return page;
		}
		try {
			service.add(entity);
//			if (service.copyFileForlocalDirectory(entity, file) && service.addPdf(entity)) {
//				service.parsePdf(entity);
//				message = "File was successful save";
//				entites = service.findAll();
//				setModelAttribute(model);
//				return page;
//			}
//			message = "Thomething wrong";
//			entites = service.findAll();
//			setModelAttribute(model);
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
//		try {
			service.add(entity);
//		}
//			ResourcesType resources;
//			String name = service.setPdfFileName(entity.getUrlForResource());
//			String localPath;
//			String pathForTxtFile = "null";
//			String url = entity.getUrlForResource();
//			if (name.substring(name.length() - 3).equals("pdf")) {
//				resources = ResourcesType.REMOTE_PDF;
//				localPath = service.setPathForFile(entity.getUrlForResource()) + name;
//				pathForTxtFile = localPath.replace("pdf", "txt");
//
//			} else if (true) {
//				resources = ResourcesType.HTML_RESOURCES;
//				localPath = service.setPathForFile(entity.getUrlForResource()) + name + ".pdf";
//				pathForTxtFile = localPath.replace("pdf", "txt");
//			}
//			entity = LinkForMetalResources.builder().host(service.getHostNameFromUrl(entity.getUrlForResource()))
//					.resourcesType(resources).urlForResource(url).localPathForPdfFile(localPath)
//					.localPathForTxtFile(pathForTxtFile).build();
//			if (this.checkIfLinkInData(entity)) {
//				message = "This link is already in the database.";
//				entites = service.findAll();
//				setModelAttribute(model);
//				return page;
//			}
//			service.downloadResorcesFromUrl(entity);
//			service.parsePdf(entity);
//			service.add(entity);
//			message = "Entity was successful save";
//			entites = service.findAll();
//			setModelAttribute(model);
//			return page;
//		} catch (StringIndexOutOfBoundsException e) {
//			message = "input value incorrect";
//			e.getLocalizedMessage();
//		} catch (RuntimeException e) {
//			log.info(e.getMessage());
//			e.printStackTrace();
//			this.setModelAttributeWhenthrowException(e, model);
//		}
		return page;
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
