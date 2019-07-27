package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Customer;
import com.vhc.core.model.User;


public interface CustomerRepository extends CrudRepository<Customer, Long> {

	public Customer findByCustomerid(long customerid);
	public Customer findByUser(User user);
	public List<Customer> findByUser_lastnameOrUser_phone(String lastname, String phone);
	public List<Customer> findAll();

}
