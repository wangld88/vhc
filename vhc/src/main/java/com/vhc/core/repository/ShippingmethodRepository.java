package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Shippingmethod;


public interface ShippingmethodRepository extends CrudRepository<Shippingmethod, Long> {

	Shippingmethod findByShipmethodid(long shipmethodid);

	List<Shippingmethod> findAll();

}
