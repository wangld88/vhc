package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Brand;


public interface BrandRepositroy extends CrudRepository<Brand, Long> {

	public Brand findByBrandid(long brandid);
	public List<Brand> findAll();
	
}
