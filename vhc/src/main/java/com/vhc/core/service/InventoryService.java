package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Inventory;
import com.vhc.core.model.Item;
import com.vhc.core.model.Product;
import com.vhc.core.model.Status;
import com.vhc.core.repository.InventoryRepository;


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

	public Inventory getByItemid(long itemid) {
		return inventoryRepository.findByItem_itemid(itemid);
	}

	public List<Inventory> getByProduct(Product product) {
		return inventoryRepository.findByItem_Product(product);
	}

	public List<Inventory> getByStoreid(long storeid) {
		return inventoryRepository.findByStore_storeid(storeid);
	}

	public List<Inventory> getByStoreAvaiable(long storeid, Status status) {
		return inventoryRepository.findByStore_storeidAndStatus(storeid, status);
	}

	public List<Inventory> getByStoreAvaiableUPC(String upc, long storeid, Status status) {
		return inventoryRepository.findByItem_skuAndStore_storeidAndStatus(upc, storeid, status);
	}

	public List<Inventory> getByProductidSizeidAvailable(long productid, long sizeid, long storeid, Status status) {
		return inventoryRepository.findByItem_Product_productidAndItem_Size_sizeidAndStore_storeidAndStatusOrderByItem_Size(productid, sizeid, storeid, status);
	}

	public List<Inventory> getAll() {
		return inventoryRepository.findAll();
	}

	@Transactional(rollbackFor=Exception.class)
	public Inventory save(Inventory inventory) {
		return inventoryRepository.save(inventory);
	}

	@Transactional(rollbackFor=Exception.class)
	public void delete(long inventoryid) {
		inventoryRepository.delete(inventoryid);
	}
}
