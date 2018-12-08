package com.vhc.repository;

import com.vhc.model.Customer;
import com.vhc.model.User;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface CustomerRepository extends CrudRepository<Customer, Long> {

	public Customer findByCustomerid(long customerid);
	public Customer findByUser(User user);
	public List<Customer> findByUser_lastnameOrUser_phone(String lastname, String phone);
	public List<Customer> findAll();

}
