package com.vhc.repository;

import com.vhc.model.Style;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface StyleRepository extends CrudRepository<Style, Long> {

	public Style findByStyleid(long styleid);

	public List<Style> findAll();

	public List<Style> findByNameIgnoreCaseLike(String name);
}
