package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
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

	@Transactional(readOnly=true)
	public List<Item> getByName(String name) {
		return itemRepository.findByProduct_nameIgnoreCaseLikeOrderByItemidDesc(name);
	}

	@Transactional(readOnly=true)
	public List<Item> getAll() {
		return itemRepository.findAllByOrderByItemidDesc();
		//return itemRepository.findAll();
	}

	@Transactional(readOnly=true)
	public DataTablesOutput<Item> getAll(DataTablesInput input) {
		return itemRepository.findAll(input);
	}

	@Transactional(readOnly=true)
	public List<Item> getAllAvailables() {
		return itemRepository.findAllAvailables();
		//return itemRepository.findAll();
	}

	@Transactional(readOnly=true)
	public List<Item> getByShipment(long shipmentid) {
		return itemRepository.findByShipment_shipmentid(shipmentid);
	}

	@Transactional(readOnly=true)
	public List<Item> getByPurchaseorder(long poid) {
		return itemRepository.findByPurchaseorder_poid(poid);
	}

	@Transactional(rollbackFor=Exception.class)
	public Item save(Item item) {
		return itemRepository.save(item);
	}

	@Transactional(rollbackFor=Exception.class)
	public void delete(long itemid) {
		itemRepository.deleteById(itemid);
	}
}
