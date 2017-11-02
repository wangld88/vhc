package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Manufacture;

public interface ManufactureRepository extends CrudRepository<Manufacture, Long> {

	List<Manufacture> findAll();
	
	Manufacture findByManufactureid(long manufactureid);
}
