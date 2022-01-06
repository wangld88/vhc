package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Inventory;
import com.vhc.core.model.Inventoryhistory;


public interface InventoryHistoryRepository extends CrudRepository<Inventoryhistory, Long> {

	Inventoryhistory findByInventory_inventoryidAndStatus_nameAndReceivedateIsNull(long inventoryid, String status);

	List<Inventoryhistory> findAllByInventory(Inventory inventory);

}
