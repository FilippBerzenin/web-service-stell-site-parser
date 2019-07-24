package com.berzenin.app.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.berzenin.app.web.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public abstract class GenericServiceImpl<E, R extends CrudRepository<E, Long>> implements GenericService<E> {
	
    protected final R repository;

    @Autowired
    public GenericServiceImpl(R repository) {
        this.repository = repository;
    }
	
	@Override
	public List<E> findAll() {
		List<E> list = (List<E>) repository.findAll();
		if (list==null) {
			return new ArrayList<>();
		}
		return list;
	}

	@Override
	public E findById(Long id) {
		return repository.findById(id)
				.orElseThrow(NotFoundException::new);
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
	
	public boolean addPdfFile(MultipartFile file) throws IOException {
		if (!Files.exists(Paths.get(PdfParserService.getPathToResource() + "\\localfiles\\"))) {
			try {
				Files.createDirectory(Paths.get(PdfParserService.getPathToResource() + "\\localfiles\\"));
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		if (pdfFileExist(file)) {
			return true;
		}
		return false;
	}
	
	public boolean pdfFileExist (MultipartFile file) throws IOException {
		Path path = Paths.get(PdfParserService.getPathToResource() + "\\localfiles\\" + file.getOriginalFilename());
		FileOutputStream fileOutputStream = null;
		FileChannel fileChannel = null;
		try {
			fileOutputStream = new FileOutputStream(path.toString());
			fileChannel = fileOutputStream.getChannel();
			ReadableByteChannel readableByteChannel = Channels.newChannel(file.getInputStream());
			fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
			return true;
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			fileOutputStream.close();
			fileChannel.close();
		}
		return false;
	}
}