package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.core.model.Inventory;
import com.vhc.core.model.Inventoryhistory;
import com.vhc.core.repository.InventoryHistoryRepository;

@Service
public class InventoryHistoryService {

	@Autowired
	private InventoryHistoryRepository inventoryHistoryRepository;


	public Inventoryhistory getUnreceivedTransfer(Inventory inventory, String status) {

		return this.inventoryHistoryRepository.findByInventory_inventoryidAndStatus_nameAndReceivedateIsNull(inventory.getInventoryid(), status);

	}

	public List<Inventoryhistory> getByInventory(Inventory inventory) {

		return this.inventoryHistoryRepository.findAllByInventory(inventory);

	}

	public Inventoryhistory save(Inventoryhistory inventoryHistory) {

		return this.inventoryHistoryRepository.save(inventoryHistory);

	}

}
