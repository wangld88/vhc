package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Categoryproduct;


public interface CategoryproductRepository extends CrudRepository<Categoryproduct, Long> {

	public Categoryproduct findByCateprodid(long cateprodid);
	public List<Categoryproduct> findByCategory_categoryid(long categoryid);
	public Categoryproduct findByCategory_categoryidAndProduct_productid(long categoryid, long productid);
	public List<Categoryproduct> findAll();

}
