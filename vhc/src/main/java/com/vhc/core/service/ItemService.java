package com.vhc.core.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
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
import com.vhc.core.model.Store;
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
	public DataTablesOutput<Item> getAllByStore(DataTablesInput input, Store store) {
		return itemRepository.findAll(input, getByStoreidSpec(store));
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

	private static Specification<Item> getByStoreidSpec(Store store)  {
		return new Specification<Item>() {
			@Override
			public Predicate toPredicate(Root<Item> root,
					CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				Join<Item, Inventory> itemJoin = root.join("inventories");
				/*
				Predicate eqPredicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("store"), store),
						criteriaBuilder.notEqual(itemJoin.get("status").get("statusid"), 3));
				*/
				Predicate eqPredicate = criteriaBuilder.notEqual(itemJoin.get("status").get("statusid"), 3);

				return eqPredicate;
			}
		};
	}
}
