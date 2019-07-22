package com.berzenin.app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.berzenin.app.model.LinkForMetalResources;
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

	public LinkForMetalResources getHostWithPdfByLinkForPdfFile(String linkForPdfFile) {
		return repo.findByLocalPathForPdfFile(linkForPdfFile);
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
		Path path = Paths.get(entity.getLocalPathForPdfFile());
		try {
			if (Files.exists(path)) {
				Files.delete(path);
				if (Files.exists(Paths.get(entity.getLocalPathForTxtFile()))) {
					Files.delete(Paths.get(entity.getLocalPathForTxtFile()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		repository.delete(entity);
	}
}
