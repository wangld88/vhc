package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Brand;


public interface BrandRepositroy extends CrudRepository<Brand, Long> {

	public Brand findByBrandidOrderByName(long brandid);

	public List<Brand> findAll();

	public List<Brand> findAllByOrderByName();

	public List<Brand> findByNameIgnoreCaseLikeOrderByName(String name);
}
