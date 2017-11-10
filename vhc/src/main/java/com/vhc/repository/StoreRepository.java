package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Store;

public interface StoreRepository extends CrudRepository<Store, Long> {

	public Store findByStoreid(long storeid);
	public List<Store> findAll();
	
}
