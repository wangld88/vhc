package com.vhc.core.service;

import com.vhc.core.model.Province;
import com.vhc.core.repository.ProvinceRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProvinceService {

	@Autowired
	private ProvinceRepository provinceRepository;


	public Province getById(long provinceid) {
		return provinceRepository.findByProvinceid(provinceid);
	}

	public List<Province> getAll() {
		return provinceRepository.findAll();
	}

	public List<Province> getByName(String name) {
		return provinceRepository.findByNameIgnoreCaseLike(name);
	}

	@Transactional(rollbackFor=Exception.class)
	public Province save(Province province) {
		return (Province)this.provinceRepository.save(province);
	}
}
