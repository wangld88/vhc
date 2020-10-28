package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Type;
import com.vhc.core.repository.TypeRepository;

@Service
public class TypeService {

	@Autowired
	TypeRepository typeRepository;


	public List<Type> getAll() {
		return typeRepository.findAll();
	}

	public List<Type> getByName(String name) {
		return typeRepository.findByNameIgnoreCaseLike(name);
	}

	public Type getById(long typeid) {
		return typeRepository.findByTypeid(typeid);
	}

	public Type getByNameReftbl(String name, String reftbl) {
		return typeRepository.findByNameIgnoreCaseAndReftblIgnoreCase(name, reftbl);
	}

	public List<Type> getByReftbl(String reftbl) {
		return typeRepository.findByReftblIgnoreCase(reftbl);
	}

	@Transactional(rollbackFor=Exception.class)
	public Type save(Type type) {
		return this.typeRepository.save(type);
	}

}
