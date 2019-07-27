package com.vhc.core.service;

import com.vhc.core.model.Invoice;
import com.vhc.core.repository.InvoiceRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;


	@Transactional(readOnly=true)
	public Invoice getById(long invoiceid) {
		return invoiceRepository.findByInvoiceid(invoiceid);
	}

	@Transactional(readOnly=true)
	public Invoice getByBarcode(String barcode) {

		return invoiceRepository.findByBarcode(barcode);

	}

	@Transactional(readOnly=true)
	public Invoice getByInvoicenum(String invoicenum) {

		return invoiceRepository.findByInvoicenum(invoicenum);

	}

	@Transactional(readOnly=true)
	public List<Invoice> getAll() {
		return invoiceRepository.findAll();
	}

	@Transactional(rollbackFor=Exception.class)
	public Invoice save(Invoice invoice) {
		return (Invoice)this.invoiceRepository.save(invoice);
	}

}
