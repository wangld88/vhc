package com.vhc.core.service;

import com.vhc.core.model.Orderitem;
import com.vhc.core.repository.OrderitemRepository;

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

	@Transactional(rollbackFor=Exception.class)
	public Orderitem save(Orderitem orderitem) {
		return this.orderitemRepository.save(orderitem);
	}
}
