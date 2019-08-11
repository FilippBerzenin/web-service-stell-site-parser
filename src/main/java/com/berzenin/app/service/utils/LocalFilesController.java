package com.berzenin.app.service.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.berzenin.app.model.LinkForMetalResources;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LocalFilesController implements FilesController {
	
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
	
	public boolean deleteFile(String link) {
		try {
			Files.deleteIfExists(Paths.get(link));
			return true;
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
			return false;
		}
	}
	
}
