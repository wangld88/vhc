package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Country;
import com.vhc.core.repository.CountryRepository;

@Service
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;

	@Transactional(readOnly=true)
	public Country getById(long countryid) {
		return countryRepository.findByCountryid(countryid);
	}

	@Transactional(readOnly=true)
	public List<Country> getAll() {
		return this.countryRepository.findAll();
	}

	@Transactional(readOnly=true)
	public List<Country> getByName(String name) {
		return this.countryRepository.findByNameIgnoreCaseLike(name);
	}

	@Transactional(rollbackFor=Exception.class)
	public Country save(Country country) {
		return countryRepository.save(country);
	}
}
