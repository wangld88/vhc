package com.vhc.service;

import com.vhc.model.Order;
import com.vhc.repository.OrderRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;


	public Order getById(long orderid) {
		return orderRepository.findByOrderid(orderid);
	}

	public List<Order> getAll() {
		return orderRepository.findAll();
	}

	public Order save(Order order) {
		return (Order)this.orderRepository.save(order);
	}
}
