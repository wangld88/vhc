package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Inventory;
import com.vhc.core.model.InventoryHistory;


public interface InventoryHistoryRepository extends CrudRepository<InventoryHistory, Long> {

	InventoryHistory findByInventory_inventoryidAndStatus_nameAndReceivedateIsNull(long inventoryid, String status);

	List<InventoryHistory> findAllByInventory(Inventory inventory);

}
