package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;

import com.vhc.core.model.Item;


public interface ItemRepository extends DataTablesRepository<Item, Long> {

	public Item findByItemid(Long itemid);

	public DataTablesOutput<Item> findAll(DataTablesInput input);

	public DataTablesOutput<Item> findAll(DataTablesInput input, Specification<Item> additionalSpecification);

	public List<Item> findAll();

	public List<Item> findAllByOrderByItemidDesc();

	@Query("SELECT i FROM Item i RIGHT OUTER JOIN i.inventories iv WHERE iv.status.statusid != 3 Order By i.itemid Desc")
	public List<Item> findAllAvailables();

	public List<Item> findByProduct_nameIgnoreCaseLikeOrderByItemidDesc(String name);

	public List<Item> findByShipment_shipmentid(long shipmentid);

	public List<Item> findByPurchaseorder_poid(long poid);

}
