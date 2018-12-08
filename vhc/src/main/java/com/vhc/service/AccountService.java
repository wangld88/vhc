package com.vhc.service;

import com.vhc.model.Account;
import com.vhc.repository.AccountRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;


	public Account getById(long accountid) {
		return accountRepository.findByAccountid(accountid);
	}

	public List<Account> getAll() {
		return accountRepository.findAll();
	}

	public Account save(Account account) {
		return (Account)this.accountRepository.save(account);
	}
}
