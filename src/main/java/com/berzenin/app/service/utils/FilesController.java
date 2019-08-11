package com.berzenin.app.service.utils;

import org.springframework.web.multipart.MultipartFile;

import com.berzenin.app.model.LinkForMetalResources;

public interface FilesController {
	
	public boolean copyFileForlocalDirectory(LinkForMetalResources entity, MultipartFile file);

	public boolean deleteFile(String localPathForTxtFile);

}
