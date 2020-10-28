package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.core.model.Inventory;
import com.vhc.core.model.InventoryHistory;
import com.vhc.core.repository.InventoryHistoryRepository;

@Service
public class InventoryHistoryService {

	@Autowired
	private InventoryHistoryRepository inventoryHistoryRepository;


	public List<InventoryHistory> getByInventory(Inventory inventory) {

		return this.inventoryHistoryRepository.findAllByInventory(inventory);

	}

	public InventoryHistory save(InventoryHistory inventoryHistory) {

		return this.inventoryHistoryRepository.save(inventoryHistory);

	}

}
