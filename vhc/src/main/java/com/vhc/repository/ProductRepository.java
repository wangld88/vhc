package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Product;


public interface ProductRepository extends CrudRepository<Product, Long> {

	List<Product> findAll();
	
	Product findByProductid(long productid);
	
}
