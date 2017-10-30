package com.vhc.repository;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Product;


public interface ProductRepository extends CrudRepository<Product, Long> {

}
