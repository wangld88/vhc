package com.vhc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Brand;
import com.vhc.repository.BrandRepositroy;

@Service
public class BrandService {

	@Autowired
	BrandRepositroy brandRepository;
	
	
	public Brand save(Brand brand) {
		return this.brandRepository.save(brand);
	}
	
}
