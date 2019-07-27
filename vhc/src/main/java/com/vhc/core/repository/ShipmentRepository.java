package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Shipment;

public interface ShipmentRepository extends CrudRepository<Shipment, Long> {

	public Shipment findByShipmentid(long shipmentid);

	public List<Shipment> findAll();

	public List<Shipment> findByCodeIgnoreCaseLikeOrSupplier_nameIgnoreCaseLike(String code, String name);
}
