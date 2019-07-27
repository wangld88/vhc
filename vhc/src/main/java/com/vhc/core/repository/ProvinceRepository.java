package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Province;


public interface ProvinceRepository extends CrudRepository<Province, Long> {

	public Province findByProvinceid(long provinceid);

	public List<Province> findAll();

	public List<Province> findByNameIgnoreCaseLike(String name);
}
