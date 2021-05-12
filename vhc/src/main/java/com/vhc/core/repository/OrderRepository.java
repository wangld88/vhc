package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Customer;
import com.vhc.core.model.Order;
import com.vhc.core.model.Status;


public interface OrderRepository extends CrudRepository<Order, Long> {

	public Order findByOrderid(long orderid);

	public List<Order> findAll();

	public List<Order> findByCustomer_customerid(long customerid);

	public List<Order> findByCustomerOrderByCreationdate(Customer customer);

	public List<Order> findByStoreIsNull();

	public List<Order> findByStoreIsNullAndStatusIsNull();

	public List<Order> findByStoreIsNullAndStatus(Status status);

	public List<Order> findByStoreIsNullAndStatusIsNot(Status status);

	public List<Order> findByStoreIsNullAndStatusIn(List<Status> status);

}
