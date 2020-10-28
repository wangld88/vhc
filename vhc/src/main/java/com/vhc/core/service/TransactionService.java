package com.vhc.core.service;

import com.vhc.core.model.Invoice;
import com.vhc.core.model.Store;
import com.vhc.core.model.Transaction;
import com.vhc.core.repository.TransactionRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public List<Transaction> getAllByDate() {
		return transactionRepository.findAllByOrderByRecorddateAsc();
	}

	public List<Transaction> getAllBySore(Store store) {
		return transactionRepository.findAllByInvoice_order_storeOrderByRecorddateAsc(store);
	}

	public List<Transaction> getByInvoice(Invoice invoice) {
		return transactionRepository.findByInvoice(invoice);
	}

	@Transactional(rollbackFor=Exception.class)
	public Transaction save(Transaction transaction) {
		return (Transaction)this.transactionRepository.save(transaction);
	}
}
