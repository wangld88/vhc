package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Brand;
import com.vhc.core.repository.BrandRepositroy;

@Service
public class BrandService {

	@Autowired
	BrandRepositroy brandRepository;


	@Transactional(readOnly=true)
	public Brand getById(long brandid) {
		return brandRepository.findByBrandidOrderByName(brandid);
	}

	@Transactional(readOnly=true)
	public List<Brand> getByIds(List<Long> brandids) {
		return brandRepository.findByBrandidIn(brandids);
	}

	@Transactional(readOnly=true)
	public List<Brand> getAll() {
		return brandRepository.findAllByOrderByName();
	}

	@Transactional(readOnly=true)
	public List<Brand> getByName(String name) {
		return brandRepository.findByNameIgnoreCaseLikeOrderByName(name);
	}

	@Transactional(rollbackFor=Exception.class)
	public Brand save(Brand brand) {
		return this.brandRepository.save(brand);
	}

}
