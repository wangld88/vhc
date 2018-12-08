package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Supplier;


public interface SupplierRepository extends CrudRepository<Supplier, Long> {

	public Supplier findBySupplierid(long supplierid);

	public List<Supplier> findAll();

	public List<Supplier> findAllByOrderByName();

	public List<Supplier> findByNameIgnoreCaseLikeOrderByName(String name);
}
