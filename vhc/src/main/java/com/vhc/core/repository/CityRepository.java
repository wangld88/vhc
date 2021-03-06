package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.City;

public interface CityRepository extends CrudRepository<City, Long> {

	public City findByCityid(long cityid);

	public List<City> findAll();

	public List<City> findByNameIgnoreCaseLike(String name);
}
