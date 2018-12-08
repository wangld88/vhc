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
		return brandRepository.findByBrandidOrderByName(brandid);
	}

	public List<Brand> getAll() {
		return brandRepository.findAllByOrderByName();
	}

	public List<Brand> getByName(String name) {
		return brandRepository.findByNameIgnoreCaseLikeOrderByName(name);
	}

	public Brand save(Brand brand) {
		return this.brandRepository.save(brand);
	}

}
