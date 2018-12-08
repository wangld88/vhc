package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Country;

public interface CountryRepository extends CrudRepository<Country, Long> {

	public Country findByCountryid(long countryid);

	public List<Country> findAll();

	public List<Country> findByNameIgnoreCaseLike(String name);
}
