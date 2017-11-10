package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Region;

public interface RegionRepository extends CrudRepository<Region, Long> {

	List<Region> findAll();
	
	Region findByRegionid(long regionid);
	
}
