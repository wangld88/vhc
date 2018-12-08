package com.vhc.repository;

import com.vhc.model.Transaction;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	public Transaction findByTransactionid(long transactionid);
	public List<Transaction> findAll();

}
