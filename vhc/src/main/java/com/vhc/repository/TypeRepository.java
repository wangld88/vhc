package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Type;

public interface TypeRepository extends CrudRepository<Type, Long> {

	List<Type> findAll();
	
	Type findByTypeid(long typeid);
	
}