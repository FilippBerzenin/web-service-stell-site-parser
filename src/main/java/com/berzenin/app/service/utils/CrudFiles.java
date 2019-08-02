package com.berzenin.app.service.utils;

import java.io.File;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface CrudFiles {
	
	boolean uploadFile(MultipartFile file);
	
	Optional<File> read(String filesname);
	
	boolean update();
	
	boolean deleteFile(String fileUrl);

}
