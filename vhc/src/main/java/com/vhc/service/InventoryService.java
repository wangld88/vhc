package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Inventory;
import com.vhc.repository.InventoryRepository;


@Service
public class InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;
	
	public Inventory findByInventoryid(long inventoryid) {
		return inventoryRepository.findByInventoryid(inventoryid);
	}
	
	public List<Inventory> findAll() {
		return inventoryRepository.findAll();
	}
	
	public Inventory save(Inventory inventory) {
		return inventoryRepository.save(inventory);
	}
}
