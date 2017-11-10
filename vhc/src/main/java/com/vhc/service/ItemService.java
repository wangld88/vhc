package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Item;
import com.vhc.repository.ItemRepository;


@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	public Item getById(long itemid) {
		return itemRepository.findByItemid(itemid);
	}
	
	public List<Item> getAll() {
		return itemRepository.findAll();
	}
	
	public List<Item> getByShipment(long shipmentid) {
		return itemRepository.findByShipment_shipmentid(shipmentid);
	}

	public Item save(Item item) {
		return itemRepository.save(item);
	}
}
