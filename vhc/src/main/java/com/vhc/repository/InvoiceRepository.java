package com.vhc.repository;

import com.vhc.model.Invoice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

	public Invoice findByInvoiceid(long invoiceid);
	public List<Invoice> findAll();

}
