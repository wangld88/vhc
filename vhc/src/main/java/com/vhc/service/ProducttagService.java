package com.vhc.service;

import com.vhc.model.Product;
import com.vhc.model.Producttag;
import com.vhc.repository.ProducttagRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducttagService {

	@Autowired
	private ProducttagRepository producttagRepository;


	public Producttag getById(long prodtagid) {
		return producttagRepository.findByProdtagid(prodtagid);
	}

	public List<Producttag> getAll() {
		return producttagRepository.findAll();
	}

	public List<Producttag> getByProduct(Product product) {
		return producttagRepository.findByProduct(product);
	}

	public Producttag save(Producttag producttag) {
		return (Producttag)this.producttagRepository.save(producttag);
	}
}
