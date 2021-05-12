package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.domain.Specification;

import com.vhc.core.model.Inventory;
import com.vhc.core.model.Item;
import com.vhc.core.model.Product;
import com.vhc.core.model.Shipment;
import com.vhc.core.model.Status;

public interface InventoryRepository extends DataTablesRepository<Inventory, Long> {

	public Inventory findByInventoryid(long inventoryid);
	public Inventory findByItem_itemid(long itemid);
	public List<Inventory> findAll();
	public List<Inventory> findAllByStatusIn(List<Status> status);
	public DataTablesOutput<Inventory> findAll(DataTablesInput input);
	public DataTablesOutput<Inventory> findAll(DataTablesInput input, Specification<Inventory> additionalSpecification);
	public List<Inventory> findByItem(Item item);
	public List<Inventory> findByItem_Product(Product product);
	public List<Inventory> findByItem_ProductAndStatus(Product product, Status status);
	public List<Inventory> findByStore_storeid(long storeid);
	public List<Inventory> findByStore_storeidOrDeststore_storeid(long storeid1, long storeid2);
	public List<Inventory> findByStore_storeidAndStatus(long storeid, Status status);
	public List<Inventory> findByStatus_statusidInAndStore_storeidOrDeststore_storeidOrderByStatus_nameDescInventoryidDesc(List<Long> status, long storeid1, long storeid2);
	public List<Inventory> findByItem_ProductAndStore_storeidAndStatus(Product product, long storeid, Status status);
	public List<Inventory> findByItem_skuAndStore_storeidAndStatus(String sku, long storeid, Status status);
	public List<Inventory> findByItem_skuAndStore_storeidAndStatusIn(String sku, long storeid, List<Status> status);
	public List<Inventory> findByItem_Product_productidAndItem_Size_sizeidAndStore_storeidAndStatusOrderByItem_Size(long productid, long sizeid, long storeid, Status status);
	public List<Inventory> findByItem_shipment(Shipment shipment);
	//@Query("select u from User u where u.emailAddress = ?1")
	//public int findSumByItem(Item item);
}
