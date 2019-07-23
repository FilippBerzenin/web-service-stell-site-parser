package com.berzenin.app.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.berzenin.app.model.LinkForMetalResources;
import com.berzenin.app.parsers.PdfParser;
import com.berzenin.app.repo.LinkForMetalResourcesRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LinkForMetalResourcesService extends GenericServiceImpl<LinkForMetalResources, LinkForMetalResourcesRepo> {

	public LinkForMetalResourcesService(LinkForMetalResourcesRepo repository) {
		super(repository);
	}

	@Autowired
	private LinkForMetalResourcesRepo repo;
	
	@Autowired
	private PdfParser pdfParser;

	public LinkForMetalResources getHostWithPdfByLinkForPdfFile(String linkForPdfFile) {
		return repo.findByLocalPathForPdfFile(linkForPdfFile);
	}
	
	public void parsePdf(LinkForMetalResources entity) {
		pdfParser.generateTxtFromPDF(entity.getLocalPathForTxtFile(), entity.getLocalPathForPdfFile());
	}

	public Path getLocalPathForPdf(MultipartFile file) {
		Path path = Paths.get(pathToResource + "\\localfiles\\" + file.getOriginalFilename());
		createLocalDirectory();
		return path;
	}

	public void createLocalDirectory() {
		if (!Files.exists(Paths.get(pathToResource + "\\localfiles\\"))) {
			try {
				Files.createDirectory(Paths.get(pathToResource + "\\localfiles\\"));
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public boolean copyFileForlocalDirectory(LinkForMetalResources entity, MultipartFile file) {
		Path copied = Paths.get(entity.getLocalPathForPdfFile());
		try {
			Files.copy(file.getInputStream(), copied, StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	@Override
	public void removeById(Long id) {
		LinkForMetalResources entity = repo.findById(id).get();
		repository.delete(entity);
		try {
			Files.deleteIfExists(Paths.get(entity.getLocalPathForPdfFile()));
			Files.deleteIfExists(Paths.get(entity.getLocalPathForTxtFile()));
			Files.deleteIfExists(Paths.get(entity.getLocalPathForPdfFile()
					.substring(0, entity.getLocalPathForPdfFile().lastIndexOf("\\"))));
			repository.delete(entity);
		} catch (DirectoryNotEmptyException e) {
			log.info("Directory: "+e+" not empty");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean downloadFileFRomUrl(LinkForMetalResources res) {
		if (res.getUrlForResource() == null || res.getUrlForResource().length()==0) {
			return false;
		}
		URL url = null;
		Path filePdf = Paths.get(res.getLocalPathForPdfFile());
		try {
			url = new URL(res.getUrlForResource());
		} catch (MalformedURLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		try (InputStream in = url.openStream()) {
			if (!Files.exists(filePdf)) {
				Files.createFile(filePdf);
			}
			Files.copy(in, filePdf, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
}
