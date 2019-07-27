package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Account;
import com.vhc.core.model.Customer;


public interface AccountRepository extends CrudRepository<Account, Long> {

	Account findByAccountid(long accountid);

	Account findByCustomer(Customer customer);

	List<Account> findAll();

}
