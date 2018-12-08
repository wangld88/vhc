package com.vhc.service;

import com.vhc.model.Orderitem;
import com.vhc.repository.OrderitemRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderitemService {

	@Autowired
	private OrderitemRepository orderitemRepository;


	public Orderitem getById(long orderitemid) {
		return orderitemRepository.findByOrderitemid(orderitemid);
	}

	public List<Orderitem> getAll() {
		return orderitemRepository.findAll();
	}

	public Orderitem save(Orderitem orderitem) {
		return this.orderitemRepository.save(orderitem);
	}
}
