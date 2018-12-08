package com.vhc.repository;

import com.vhc.model.Orderitem;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface OrderitemRepository extends CrudRepository<Orderitem, Long> {

	public Orderitem findByOrderitemid(long orderitemid);
	public List<Orderitem> findAll();

}
