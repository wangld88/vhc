package com.vhc.core.service;

import com.vhc.core.model.Customer;
import com.vhc.core.model.User;
import com.vhc.core.repository.CustomerRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;


	@Transactional(readOnly=true)
	public Customer getById(long customerid) {
		return customerRepository.findByCustomerid(customerid);
	}

	@Transactional(readOnly=true)
	public List<Customer> getAll() {
		return customerRepository.findAll();
	}

	@Transactional(readOnly=true)
	public Customer getByUser(User user) {
		return customerRepository.findByUser(user);
	}

	@Transactional(readOnly=true)
	public List<Customer> getByLastnamePhone(User user) {
		return customerRepository.findByUser_lastnameOrUser_phone(user.getLastname(), user.getPhone());
	}

	@Transactional(rollbackFor=Exception.class)
	public Customer save(Customer customer) {
		return (Customer) this.customerRepository.save(customer);
	}
}
