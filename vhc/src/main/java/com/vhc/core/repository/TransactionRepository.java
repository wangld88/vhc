package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Invoice;
import com.vhc.core.model.Transaction;


public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	public Transaction findByTransactionid(long transactionid);

	public List<Transaction> findAll();

	public List<Transaction> findByInvoice(Invoice invoice);

}
