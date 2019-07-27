package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	public Category findByCategoryid(long categoryid);
	public Category findByName(String name);
	public Category findByTitle(String title);
	public List<Category> findAll();

}
