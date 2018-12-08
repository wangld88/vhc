package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.City;
import com.vhc.repository.CityRepository;


@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;


	public City getById(long cityid) {
		return cityRepository.findByCityid(cityid);
	}

	public List<City> getAll() {
		return cityRepository.findAll();
	}

	public List<City> getByName(String name) {
		return cityRepository.findByNameIgnoreCaseLike(name);
	}

	public City save(City city) {
		return cityRepository.save(city);
	}
}
