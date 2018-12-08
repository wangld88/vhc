package com.vhc.service;

import com.vhc.model.Transaction;
import com.vhc.repository.TransactionRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;


	public Transaction getById(long transactionid) {
		return transactionRepository.findByTransactionid(transactionid);
	}

	public List<Transaction> getAll() {
		return transactionRepository.findAll();
	}

	public Transaction save(Transaction transaction) {
		return (Transaction)this.transactionRepository.save(transaction);
	}
}
