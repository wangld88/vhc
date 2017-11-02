package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Product;
import com.vhc.repository.ProductRepository;


@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	
	public Product getById(long productid) {
		return productRepository.findByProductid(productid);
	}
	
	public List<Product> getAll() {
		return productRepository.findAll();
	}
	
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
}
