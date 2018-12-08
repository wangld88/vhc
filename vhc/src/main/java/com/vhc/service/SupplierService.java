package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Supplier;
import com.vhc.repository.SupplierRepository;


@Service
public class SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;

	public Supplier getById(long supplierid) {
		return supplierRepository.findBySupplierid(supplierid);
	}

	public List<Supplier> getAll() {
		return supplierRepository.findAllByOrderByName();
	}

	public List<Supplier> getByName(String name) {
		return supplierRepository.findByNameIgnoreCaseLikeOrderByName(name);
	}

	public Supplier save(Supplier supplier) {
		return supplierRepository.save(supplier);
	}

}
