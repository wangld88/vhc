package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Brand;
import com.vhc.repository.BrandRepositroy;

@Service
public class BrandService {

	@Autowired
	BrandRepositroy brandRepository;
	
	
	public Brand getById(long brandid) {
		return brandRepository.findByBrandid(brandid);
	}
	
	public List<Brand> getAll() {
		return brandRepository.findAll();
	}
	
	public Brand save(Brand brand) {
		return this.brandRepository.save(brand);
	}
	
}
