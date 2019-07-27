package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Orderitem;


public interface OrderitemRepository extends CrudRepository<Orderitem, Long> {

	public Orderitem findByOrderitemid(long orderitemid);
	public List<Orderitem> findAll();

}
