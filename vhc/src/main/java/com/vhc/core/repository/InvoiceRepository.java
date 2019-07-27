package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Invoice;


public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

	public Invoice findByInvoiceid(long invoiceid);

	public Invoice findByBarcode(String barcode);

	public Invoice findByInvoicenum(String invoicenum);

	public List<Invoice> findAll();

}
