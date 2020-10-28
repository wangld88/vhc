package com.vhc.core.service;

import com.vhc.core.model.Orderitem;
import com.vhc.core.repository.OrderitemRepository;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderitemService {

	@Autowired
	private OrderitemRepository orderitemRepository;


	@Transactional(readOnly=true)
	public Orderitem getById(long orderitemid) {
		return orderitemRepository.findByOrderitemid(orderitemid);
	}

	@Transactional(readOnly=true)
	public List<Orderitem> getAll() {
		return orderitemRepository.findAll();
	}

	@Transactional(readOnly=true)
	public List<Orderitem> getAllInOrder() {
		return orderitemRepository.findAllByOrderByOrder_creationdateDesc();
	}

	@Transactional(readOnly=true)
	public List<Orderitem> getByStoreDate(long storeid, Calendar from, Calendar to) {
		if(storeid == 0) {
			return orderitemRepository.findByOrder_creationdateGreaterThanEqualAndOrder_creationdateLessThanEqual(from, to);
		} else {
			return orderitemRepository.findByOrder_store_storeidAndOrder_creationdateGreaterThanEqualAndOrder_creationdateLessThanEqual(storeid, from, to);
		}
	}

	@Transactional(readOnly=true)
	public List<Orderitem> getBySearchInOrder(String name) {
		return orderitemRepository.findByItem_product_nameLikeOrItem_product_modelnumLikeOrItem_skuLikeOrderByOrder_creationdateDesc(name, name, name);
	}

	@Transactional(rollbackFor=Exception.class)
	public Orderitem save(Orderitem orderitem) {
		return this.orderitemRepository.save(orderitem);
	}
}
