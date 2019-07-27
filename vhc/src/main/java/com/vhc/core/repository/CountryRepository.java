package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Country;

public interface CountryRepository extends CrudRepository<Country, Long> {

	public Country findByCountryid(long countryid);

	public List<Country> findAll();

	public List<Country> findByNameIgnoreCaseLike(String name);
}
