package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Product;
import com.vhc.core.model.Producttag;


public interface ProducttagRepository extends CrudRepository<Producttag, Long> {

	public Producttag findByProdtagid(long prodtagid);
	public List<Producttag> findAll();
	public List<Producttag> findByProduct(Product product);

}
