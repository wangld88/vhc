package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Item;


public interface ItemRepository extends CrudRepository<Item, Long> {

	public Item findByItemid(long itemid);

	public List<Item> findAllByOrderByItemidDesc();

	public List<Item> findAll();

	public List<Item> findByProduct_nameIgnoreCaseLikeOrderByItemidDesc(String name);

	public List<Item> findByShipment_shipmentid(long shipmentid);

	public List<Item> findByPurchaseorder_poid(long poid);

}
