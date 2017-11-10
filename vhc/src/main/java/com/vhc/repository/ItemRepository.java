package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Item;


public interface ItemRepository extends CrudRepository<Item, Long> {

	public Item findByItemid(long itemid);
	
	public List<Item> findAll();

	public List<Item> findByShipment_shipmentid(long shipmentid);
	
}
