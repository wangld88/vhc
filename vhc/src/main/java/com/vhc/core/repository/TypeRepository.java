package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Type;

public interface TypeRepository extends CrudRepository<Type, Long> {

	List<Type> findAll();

	Type findByTypeid(long typeid);

	Type findByNameIgnoreCaseAndReftblIgnoreCase(String name, String reftbl);

	List<Type> findByReftblIgnoreCase(String reftbl);

	List<Type> findByNameIgnoreCaseLike(String name);
}
