package com.vhc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Product;
import com.vhc.repository.ProductRepository;


@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
}
