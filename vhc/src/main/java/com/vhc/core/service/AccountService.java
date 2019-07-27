package com.vhc.core.service;

import com.vhc.core.model.Account;
import com.vhc.core.model.Customer;
import com.vhc.core.repository.AccountRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;


	@Transactional(readOnly=true)
	public Account getById(long accountid) {
		return accountRepository.findByAccountid(accountid);
	}

	@Transactional(readOnly=true)
	public Account getByCustomer(Customer customer) {
		return accountRepository.findByCustomer(customer);
	}

	@Transactional(readOnly=true)
	public List<Account> getAll() {
		return accountRepository.findAll();
	}

	@Transactional(rollbackFor=Exception.class)
	public Account save(Account account) {
		return (Account)this.accountRepository.save(account);
	}
}
