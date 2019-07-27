package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Brand;


public interface BrandRepositroy extends CrudRepository<Brand, Long> {

	public Brand findByBrandidOrderByName(long brandid);

	public List<Brand> findByBrandidIn(List<Long> brandids);

	public List<Brand> findAll();

	public List<Brand> findAllByOrderByName();

	public List<Brand> findByNameIgnoreCaseLikeOrderByName(String name);

}
