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

	public Item save(Item item) {
		return itemRepository.save(item);
	}

	public void delete(long itemid) {
		itemRepository.delete(itemid);
	}
}
