package com.berzenin.app.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.berzenin.app.web.exception.NotFoundException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public abstract class GenericServiceImpl<E, R extends CrudRepository<E, Long>> implements GenericService<E> {

	protected final R repository;

	@Getter
	protected static final String pathToResource = "..\\web-metal-searcher\\src\\main\\resources\\";

	@Autowired
	public GenericServiceImpl(R repository) {
		this.repository = repository;
	}

	public String getHostNameFromUrl(String url) {
		URL partOfurl = null;
		try {
			partOfurl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return partOfurl.getHost();
	}

	public String setPathForFile(String url) {
		String path = "";
		try {
			URL partOfurl = new URL(url);
			if (!Files.exists(Paths.get(pathToResource + partOfurl.getHost()))) {
				Files.createDirectories(Paths.get(pathToResource + partOfurl.getHost()));
			}
			path = pathToResource + partOfurl.getHost() + "\\";
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return path;
	}

	public String setPdfFileName(String url) {
		String pdfFileName = "";
		try {
			URL partOfurl = new URL(url.trim());
			pdfFileName = FilenameUtils.getName(partOfurl.getPath());
			if (pdfFileName==null || pdfFileName.length()==0) {
				if (url.endsWith("/")) {
					url = url.substring(0, url.length()-2);
				}
				pdfFileName = url.substring(url.lastIndexOf("/")+1);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
	}
		return pdfFileName;
	}
	
	@Override
	public Set<E> findAll() {
		List<E> list = (List<E>) repository.findAll();
		Set<E> set = list.stream().collect(Collectors.toSet());
		if (set == null) {
			return new HashSet<>();
		}		
		return set;
	}

	@Override
	public E findById(Long id) {
		return repository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Override
	public E add(E entity) {
		return repository.save(entity);
	}

	@Override
	public E update(E entity) {
		return repository.save(entity);
	}

	@Override
	public void removeById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void remove(E entity) {
		repository.delete(entity);
	}
}