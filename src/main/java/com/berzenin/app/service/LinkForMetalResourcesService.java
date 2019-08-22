package com.berzenin.app.service;

//import java.nio.file.DirectoryNotEmptyException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.berzenin.app.model.Host;
import com.berzenin.app.model.LinkForMetalResources;
import com.berzenin.app.parsers.PdfParser;
import com.berzenin.app.repo.LinkForMetalResourcesRepo;
import com.berzenin.app.service.utils.FilesController;
import com.berzenin.app.type.ResourcesType;
import com.berzenin.app.web.controller.GenericViewControllerImpl;
import com.berzenin.app.web.controller.LinkMetalResourcesController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LinkForMetalResourcesService extends GenericServiceImpl<LinkForMetalResources, LinkForMetalResourcesRepo> {

	public LinkForMetalResourcesService(LinkForMetalResourcesRepo repository, FilesController filesController) {
		super(repository, filesController);
	}

	@Autowired
	private LinkForMetalResourcesRepo repo;
	
	@Autowired
	private PdfParser pdfParser;
	
	@Autowired
	private HtmlService htmlService;
	
	public boolean deleteAllLinksFromHostResources (String host) {
		try {
			repo.findAllByHost(host).forEach(s -> deleteLink(s));
			return true;
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean uploadById(Long id) {
		LinkForMetalResources entity = this.findById(id);
		if (entity.getResourcesType().equals(ResourcesType.LOCAL_PDF)) {
			GenericViewControllerImpl.message = "This is local file, please delete and upload pdf ones more";
			return false;
		}
		try {
			deleteLink(entity);
			add(entity);
			return true;
		} catch (RuntimeException e) {
			log.error(e.getLocalizedMessage());
		}
		return false;
	}
	
	public void deleteLink (LinkForMetalResources links) {
		for (LinkForMetalResources link : repo.findAllByHost(links.getHost())) {
				filesController.deleteFile(link.getLocalPathForTxtFile());
				filesController.deleteFile(link.getLocalPathForPdfFile());
				remove(link);
		}
	}
	
	public List<LinkForMetalResources> getAllByHost(String host) {
		return repo.findAllByHost(host);
	}
	
	@Override
	public Set<LinkForMetalResources> findAll() {
		GenericViewControllerImpl.message = GenericViewControllerImpl.message + "All entity";
		Set <LinkForMetalResources> entites = repo.findAll().stream().collect(Collectors.toSet());
		return entites.stream()
			.collect(Collectors.toCollection(
			() -> new TreeSet<LinkForMetalResources>((p1, p2) -> p1.getHost().compareTo(p2.getHost()))));
	}
	
	public Host getLinskFromHost(Host host) {
		htmlService.getAllLinksFromHost(host);
		return host;
	}
	
	public List<LinkForMetalResources> findByLink(String link) {
		return repository.findByLocalPathForTxtFile(link);
	}

	public List<LinkForMetalResources> getHostWithPdfByLinkForPdfFile(String linkForPdfFile) {
		return repo.findByLocalPathForPdfFile(linkForPdfFile);
	}
	
	public boolean addPdf (LinkForMetalResources entity, MultipartFile file) {
		try {
			if (this.filesController.copyFileForlocalDirectory(entity, file) && 
				this.filesController.generateTxtFromPDF(entity.getLocalPathForTxtFile(), entity.getLocalPathForPdfFile()) != null) {
				repo.save(entity);
				return true;				
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public LinkForMetalResources add(LinkForMetalResources entity) {
		try {
		String name = filesController.setPdfFileName(entity.getUrlForResource());
		String localPath = null;
		String pathForTxtFile = "null";
		String url = entity.getUrlForResource().trim();
		localPath = filesController.setPathForFile(entity.getUrlForResource())+name;
		if (entity.getResourcesType() != null && entity.getResourcesType().equals(ResourcesType.LOCAL_PDF)) {
			downloadResorcesFromUrl(entity);
			filesController.generateTxtFromPDF(entity.getLocalPathForTxtFile(), entity.getLocalPathForPdfFile());
			repository.save(entity);
			LinkMetalResourcesController.message = "Entity was successful save";
			return entity;
		} else if (name.substring(name.length()-3).equals("pdf")) {
			entity.setResourcesType(ResourcesType.REMOTE_PDF);
		} else if (entity.getResourcesType() != null && entity.getResourcesType().equals(ResourcesType.HOST_RESOURCE)) {
			localPath = filesController.setPathForFile(entity.getUrlForResource())+name+".pdf";
		} else if (entity.getResourcesType() == null) {
			entity.setResourcesType(ResourcesType.HTML_RESOURCES);
			localPath = filesController.setPathForFile(entity.getUrlForResource())+name+".pdf";
		}		
		pathForTxtFile = localPath.replace("pdf", "txt");
		entity = LinkForMetalResources.builder()
			.host(filesController.getHostNameFromUrl(entity.getUrlForResource()))
			.resourcesType(entity.getResourcesType())
			.urlForResource(url)
			.localPathForPdfFile(localPath)
			.localPathForTxtFile(pathForTxtFile)
			.build();
		log.info("Entity: "+entity.toString());
		if (this.checkIfLinkInData(entity)) {
			LinkMetalResourcesController.message = "This link is already in the database.";
			return entity;
		}
		if(!this.downloadResorcesFromUrl(entity)) {
			return entity;
		}
		if (entity.getResourcesType().equals(ResourcesType.REMOTE_PDF)) {
			filesController.generateTxtFromPDF(entity.getLocalPathForTxtFile(), entity.getLocalPathForPdfFile());	
		}
		repository.save(entity);
		LinkMetalResourcesController.message = "Entity was successful save";
		return entity;
		} catch (RuntimeException e) {
			log.debug(e.getLocalizedMessage());
			return entity;
		}
	}

	public boolean checkIfLinkInData(LinkForMetalResources entity) {
		List<LinkForMetalResources> find = this.getHostWithPdfByLinkForPdfFile(entity.getLocalPathForPdfFile());
		if (find == null || find.size()==0) {
			return false;
		}
		if (find.equals(entity)) {
			return true;
		}
		return false;
	}

	@Override
	public void removeById(Long id) {
		LinkForMetalResources entity = repo.findById(id).get();
		repository.delete(entity);
		filesController.deleteFile(entity.getLocalPathForPdfFile());
		filesController.deleteFile(entity.getLocalPathForTxtFile());
		//TODO
		filesController.deleteFile(entity.getLocalPathForPdfFile()
				.substring(0, entity.getLocalPathForPdfFile().lastIndexOf("\\")));
		repository.delete(entity);
	}

	public boolean downloadResorcesFromUrl(LinkForMetalResources res) {
		if (res.getResourcesType().equals(ResourcesType.HTML_RESOURCES)) {
			return htmlService.convertHtmlPageForTxt(res);
		} if (res.getResourcesType().equals(ResourcesType.REMOTE_PDF)) {
			return filesController.downloadPdfFileFromUrl(res);
		} if (res.getResourcesType().equals(ResourcesType.HOST_RESOURCE)) {
			return htmlService.convertHtmlPageForTxt(res);
		} if (res.getResourcesType().equals(ResourcesType.LOCAL_PDF)) {
			return true;
		} else {
			return false;
		}
		
	}
}
