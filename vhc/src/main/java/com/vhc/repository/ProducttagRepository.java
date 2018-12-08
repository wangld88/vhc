package com.vhc.repository;

import com.vhc.model.Product;
import com.vhc.model.Producttag;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface ProducttagRepository extends CrudRepository<Producttag, Long> {

	public Producttag findByProdtagid(long prodtagid);
	public List<Producttag> findAll();
	public List<Producttag> findByProduct(Product product);

}
