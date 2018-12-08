package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Purchaseorder;
import com.vhc.repository.PurchaseorderRepository;


@Service
public class PurchaseorderService {

	@Autowired
	private PurchaseorderRepository purchaseorderRepository;


	public Purchaseorder getById(long poid) {
		return purchaseorderRepository.findByPoid(poid);
	}

	public List<Purchaseorder> getAll() {
		return purchaseorderRepository.findAll();
	}

	public List<Purchaseorder> getByName(String name) {
		return purchaseorderRepository.findByCodeIgnoreCaseLikeOrSupplier_nameIgnoreCaseLike(name, name);
	}

	public List<Purchaseorder> getUnusedOrdersBySupplierid(long supplierid) {
		return purchaseorderRepository.findUnusedOrders(supplierid);
	}

	public Purchaseorder save(Purchaseorder order) {
		return purchaseorderRepository.save(order);
	}

}
