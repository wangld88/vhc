package com.vhc.repository;

import com.vhc.model.Order;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface OrderRepository extends CrudRepository<Order, Long> {

	public Order findByOrderid(long orderid);
	public List<Order> findAll();

}
