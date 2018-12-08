package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Country;
import com.vhc.repository.CountryRepository;

@Service
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;

	public Country getById(long countryid) {
		return countryRepository.findByCountryid(countryid);
	}

	public List<Country> getAll() {
		return this.countryRepository.findAll();
	}

	public List<Country> getByName(String name) {
		return this.countryRepository.findByNameIgnoreCaseLike(name);
	}

	public Country save(Country country) {
		return countryRepository.save(country);
	}
}
