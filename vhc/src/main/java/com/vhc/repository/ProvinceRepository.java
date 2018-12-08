package com.vhc.repository;

import com.vhc.model.Province;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface ProvinceRepository extends CrudRepository<Province, Long> {

	public Province findByProvinceid(long provinceid);

	public List<Province> findAll();

	public List<Province> findByNameIgnoreCaseLike(String name);
}
