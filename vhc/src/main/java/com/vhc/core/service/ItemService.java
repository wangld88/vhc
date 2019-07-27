package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Item;
import com.vhc.core.repository.ItemRepository;


@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	public Item getById(long itemid) {
		return itemRepository.findByItemid(itemid);
	}

	public List<Item> getByName(String name) {
		return itemRepository.findByProduct_nameIgnoreCaseLikeOrderByItemidDesc(name);
	}

	public List<Item> getAll() {
		return itemRepository.findAllByOrderByItemidDesc();
		//return itemRepository.findAll();
	}

	public List<Item> getByShipment(long shipmentid) {
		return itemRepository.findByShipment_shipmentid(shipmentid);
	}

	public List<Item> getByPurchaseorder(long poid) {
		return itemRepository.findByPurchaseorder_poid(poid);
	}

	@Transactional(rollbackFor=Exception.class)
	public Item save(Item item) {
		return itemRepository.save(item);
	}

	@Transactional(rollbackFor=Exception.class)
	public void delete(long itemid) {
		itemRepository.delete(itemid);
	}
}
