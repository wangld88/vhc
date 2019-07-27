package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Style;


public interface StyleRepository extends CrudRepository<Style, Long> {

	public Style findByStyleid(long styleid);

	public List<Style> findAll();

	public List<Style> findByNameIgnoreCaseLike(String name);
}
