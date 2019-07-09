package com.berzenin.app.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.berzenin.app.model.HostWithPdf;
import com.berzenin.app.repo.HostWithPdfRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HostWithPdfService extends GenericServiceImpl<HostWithPdf, HostWithPdfRepo> {

	public HostWithPdfService(HostWithPdfRepo repository) {
		super(repository);
	}

	public Path addPdfFile(MultipartFile file) {
		if (!Files.exists(Paths.get(PdfParserService.getPathToResource() + "\\localfiles\\"))) {
			try {
				Files.createDirectory(Paths.get(PdfParserService.getPathToResource() + "\\localfiles\\"));
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		Path path = Paths.get(PdfParserService.getPathToResource() + "\\localfiles\\" + file.getOriginalFilename());
		try (InputStream in = file.getInputStream()) {
			Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
			return path;
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return Paths.get("");
	}
}
