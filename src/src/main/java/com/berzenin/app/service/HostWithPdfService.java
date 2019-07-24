package com.berzenin.app.service;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.berzenin.app.model.HostWithPdf;
import com.berzenin.app.repo.HostWithPdfRepo;

@Service
public class HostWithPdfService extends GenericServiceImpl<HostWithPdf, HostWithPdfRepo> {

	public HostWithPdfService(HostWithPdfRepo repository) {
		super(repository);
	}

	@Autowired
	private HostWithPdfRepo repo;

	public HostWithPdf getHostWithPdfByLinkForPdfFile(String linkForPdfFile) {
		return repo.findBylinkForPdfFile(linkForPdfFile);
	}

	@Override
	public HostWithPdf add(HostWithPdf entity) {
		String file = entity.getPathForPdf();
		String url = entity.getLinkForPdfFile();
		
//		try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
//				  FileOutputStream fileOutputStream = new FileOutputStream(file)) {
//				    byte dataBuffer[] = new byte[1024];
//				    int bytesRead;
//				    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
//				        fileOutputStream.write(dataBuffer, 0, bytesRead);
//				    }
//				} catch (IOException e) {
//				    // handle exception
//				}
		
		try (FileOutputStream fileOutputStream = new FileOutputStream(file);
				FileChannel fileChannel = fileOutputStream.getChannel();
				ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(url).openStream())) {
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return repository.save(entity);
	}

	@Override
	public void removeById(Long id) {
		HostWithPdf entity = repo.findById(id).get();
		repository.delete(entity);
		Path path = Paths.get(entity.getPathForPdf());
		try {
			if (Files.exists(path)) {
				Files.delete(path);
				if (Files.exists(Paths.get(entity.getPathForPdf().replace("pdf", "txt")))) {
					Files.delete(Paths.get(entity.getPathForPdf().replace("pdf", "txt")));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		repository.delete(entity);
	}
}
