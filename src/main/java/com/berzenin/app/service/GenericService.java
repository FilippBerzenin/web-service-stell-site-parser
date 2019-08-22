package com.berzenin.app.service;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

public interface GenericService<E> {

	public Set<E> findAll();

	public E findById(Long id);

	public E add(E entity);

	public void remove(E entity);

	public void removeById(Long id);

	public E update(@Valid E entity);
	
	public List<E> findByLink(String link);

	public boolean uploadById(Long id);
}

