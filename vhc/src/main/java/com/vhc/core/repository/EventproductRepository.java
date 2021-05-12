package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Eventproduct;


public interface EventproductRepository extends CrudRepository<Eventproduct, Long> {

	Eventproduct findByEventprodid(long id);

	Eventproduct findByEvent_eventidAndProduct_productid(long eventid, long productid);

	List<Eventproduct> findAll();

	List<Eventproduct> findByEvent_eventid(long eventid);

}
