package com.vhc.service;

import com.vhc.model.Invoice;
import com.vhc.repository.InvoiceRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;


	public Invoice getById(long invoiceid) {
		return invoiceRepository.findByInvoiceid(invoiceid);
	}

	public List<Invoice> getAll() {
		return invoiceRepository.findAll();
	}

	public Invoice save(Invoice invoice) {
		return (Invoice)this.invoiceRepository.save(invoice);
	}
}
