package com.berzenin.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.berzenin.app.service.utils.FilesController;
import com.berzenin.app.web.exception.NotFoundException;

@Service
public abstract class GenericServiceImpl<E, R extends CrudRepository<E, Long>> implements GenericService<E> {

	protected final R repository;
	protected final FilesController filesController;

	@Autowired
	public GenericServiceImpl(R repository, FilesController filesController) {
		this.repository = repository;
		this.filesController = filesController;
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