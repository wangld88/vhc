package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Region;

public interface RegionRepository extends CrudRepository<Region, Long> {

	List<Region> findAll();
	
	Region findByRegionid(long regionid);
	
}
