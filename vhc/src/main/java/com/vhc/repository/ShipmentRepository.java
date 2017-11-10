package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Shipment;

public interface ShipmentRepository extends CrudRepository<Shipment, Long> {

	public Shipment findByShipmentid(long shipmentid);
	public List<Shipment> findAll();
}
