package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Customer;
import com.vhc.core.model.Order;


public interface OrderRepository extends CrudRepository<Order, Long> {

	public Order findByOrderid(long orderid);

	public List<Order> findAll();

	public List<Order> findByCustomer_customerid(long customerid);

	public List<Order> findByCustomerOrderByCreationdate(Customer customer);

}
