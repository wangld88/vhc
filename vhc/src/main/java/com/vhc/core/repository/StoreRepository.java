package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Store;

public interface StoreRepository extends CrudRepository<Store, Long> {

	public Store findByStoreid(long storeid);

	public Store findByName(String name);

	public List<Store> findAll();

}
