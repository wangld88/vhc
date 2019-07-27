package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.City;
import com.vhc.core.repository.CityRepository;


@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;


	@Transactional(readOnly=true)
	public City getById(long cityid) {
		return cityRepository.findByCityid(cityid);
	}

	@Transactional(readOnly=true)
	public List<City> getAll() {
		return cityRepository.findAll();
	}

	@Transactional(readOnly=true)
	public List<City> getByName(String name) {
		return cityRepository.findByNameIgnoreCaseLike(name);
	}

	@Transactional(rollbackFor=Exception.class)
	public City save(City city) {
		return cityRepository.save(city);
	}
}
