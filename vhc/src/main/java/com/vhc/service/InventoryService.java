package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Inventory;
import com.vhc.model.Item;
import com.vhc.repository.InventoryRepository;


@Service
public class InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;
	
	public Inventory getById(long inventoryid) {
		return inventoryRepository.findByInventoryid(inventoryid);
	}
	
	public List<Inventory> getByItem(Item item) {
		return inventoryRepository.findByItem(item);
	}

	public List<Inventory> getByStoreid(long storeid) {
		return inventoryRepository.findByStore_storeid(storeid);
	}

	public List<Inventory> getAll() {
		return inventoryRepository.findAll();
	}
	
	public Inventory save(Inventory inventory) {
		return inventoryRepository.save(inventory);
	}
}
