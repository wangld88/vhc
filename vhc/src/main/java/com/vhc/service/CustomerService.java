package com.vhc.service;

import com.vhc.model.Customer;
import com.vhc.model.User;
import com.vhc.repository.CustomerRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;


	public Customer getById(long customerid) {
		return customerRepository.findByCustomerid(customerid);
	}

	public List<Customer> getAll() {
		return customerRepository.findAll();
	}

	public Customer getByUser(User user) {
		return customerRepository.findByUser(user);
	}

	public List<Customer> getByLastnamePhone(User user) {
		return customerRepository.findByUser_lastnameOrUser_phone(user.getLastname(), user.getPhone());
	}

	public Customer save(Customer customer) {
		return (Customer)this.customerRepository.save(customer);
	}
}
