package com.vhc.core.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Inventory;
import com.vhc.core.model.Item;
import com.vhc.core.model.Product;
import com.vhc.core.model.Shipment;
import com.vhc.core.model.Status;
import com.vhc.core.model.Store;
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

	@Transactional(readOnly=true)
	public List<Inventory> getByProduct(Product product) {
		return inventoryRepository.findByItem_Product(product);
	}

	@Transactional(readOnly=true)
	public List<Inventory> getByStoreid(long storeid) {
		//return inventoryRepository.findByStore_storeid(storeid);
		return inventoryRepository.findByStore_storeidOrDeststore_storeid(storeid, storeid);
	}


	@Transactional(readOnly=true)
	public List<Inventory> getAllAvaiable(List<Status> status) {
		return inventoryRepository.findAllByStatusIn(status);
	}


	@Transactional(readOnly=true)
	public List<Inventory> getByStoreAvaiable(long storeid, List<Long> status) {
		return inventoryRepository.findByStatus_statusidInAndStore_storeidOrDeststore_storeidOrderByStatus_nameDescInventoryidDesc(status, storeid, storeid);
	}

	@Transactional(readOnly=true)
	public List<Inventory> getByStoreAvaiableUPC(String upc, long storeid, Status status) {
		return inventoryRepository.findByItem_skuAndStore_storeidAndStatus(upc, storeid, status);
	}

	@Transactional(readOnly=true)
	public List<Inventory> getByStoreAvaiableUPC(String upc, long storeid, List<Status> status) {
		return inventoryRepository.findByItem_skuAndStore_storeidAndStatusIn(upc, storeid, status);
	}

	@Transactional(readOnly=true)
	public List<Inventory> getByProductidSizeidAvailable(long productid, long sizeid, long storeid, Status status) {
		return inventoryRepository.findByItem_Product_productidAndItem_Size_sizeidAndStore_storeidAndStatusOrderByItem_Size(productid, sizeid, storeid, status);
	}

	@Transactional(readOnly=true)
	public List<Inventory> getByShipment(Shipment shipment) {
		return inventoryRepository.findByItem_shipment(shipment);
	}

	@Transactional(readOnly=true)
	public List<Inventory> getAll() {
		return inventoryRepository.findAll();
	}

	@Transactional(readOnly=true)
	public DataTablesOutput<Inventory> getAll(DataTablesInput input, Store store, List<Status> statuss, Status status) {

		return inventoryRepository.findAll(input, getByStoreidSpec(store, statuss, status));
	}

	@Transactional(rollbackFor=Exception.class)
	public Inventory save(Inventory inventory) {
		return inventoryRepository.save(inventory);
	}

	@Transactional(rollbackFor=Exception.class)
	public void delete(long inventoryid) {
		inventoryRepository.deleteById(inventoryid);
	}

	private static Specification<Inventory> getByStoreidSpec(Store store, List<Status> statuss, Status status)  {
		return new Specification<Inventory>() {
			@Override
			public Predicate toPredicate(Root<Inventory> root,
					CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				Predicate storePredicate = criteriaBuilder.and(criteriaBuilder.in(root.get("status")).value(statuss),
						criteriaBuilder.equal(root.get("store"), store));

				Predicate destPredicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), status),
						criteriaBuilder.equal(root.get("deststore"), store));

				Predicate orPredicate = criteriaBuilder.or(storePredicate, destPredicate);

				return orPredicate;
			}
		};
	}
}
