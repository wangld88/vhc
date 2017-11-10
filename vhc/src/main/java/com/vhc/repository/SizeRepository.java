package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Size;

public interface SizeRepository extends CrudRepository<Size, Long> {

	List<Size> findAll();
	
	List<Size> findByType_typeidAndRegion_regionid(long typeid, long regionid);
	
	Size findBySizeid(long sizeid);
	
}
