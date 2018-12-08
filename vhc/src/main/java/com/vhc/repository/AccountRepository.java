package com.vhc.repository;

import com.vhc.model.Account;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface AccountRepository extends CrudRepository<Account, Long> {

	public Account findByAccountid(long accountid);
	public List<Account> findAll();

}
