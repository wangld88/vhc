package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Inventory;
import com.vhc.model.Item;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {

	public Inventory findByInventoryid(long inventoryid);
	public List<Inventory> findAll();
	public List<Inventory> findByItem(Item item);
	public List<Inventory> findByStore_storeid(long storeid);
	//@Query("select u from User u where u.emailAddress = ?1")
	//public int findSumByItem(Item item);
}
