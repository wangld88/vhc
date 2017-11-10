package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Inventory;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {

	public Inventory findByInventoryid(long inventoryid);
	public List<Inventory> findAll();
	
}
