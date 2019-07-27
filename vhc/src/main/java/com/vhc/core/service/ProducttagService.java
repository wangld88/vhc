package com.vhc.core.service;

import com.vhc.core.model.Product;
import com.vhc.core.model.Producttag;
import com.vhc.core.repository.ProducttagRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional(rollbackFor=Exception.class)
	public Producttag save(Producttag producttag) {
		return (Producttag)this.producttagRepository.save(producttag);
	}
}
