package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Inventorycount;


public interface InventorycountRepository extends CrudRepository<Inventorycount, Long> {

	Inventorycount findByCountid(long countid);

	List<Inventorycount> findAll();

}
