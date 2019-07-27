package com.vhc.core.service;

import com.vhc.core.model.Customer;
import com.vhc.core.model.Order;
import com.vhc.core.repository.OrderRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;


	@Transactional(readOnly=true)
	public Order getById(long orderid) {
		return orderRepository.findByOrderid(orderid);
	}

	@Transactional(readOnly=true)
	public List<Order> getAll() {
		return orderRepository.findAll();
	}

	@Transactional(readOnly=true)
	public List<Order> getByCustomer(Customer customer) {
		return orderRepository.findByCustomerOrderByCreationdate(customer);
	}

	@Transactional(readOnly=true)
	public List<Order> getByCustomer(long customerid) {
		return orderRepository.findByCustomer_customerid(customerid);
	}

	@Transactional(rollbackFor=Exception.class)
	public Order save(Order order) {
		return (Order)this.orderRepository.save(order);
	}

}
