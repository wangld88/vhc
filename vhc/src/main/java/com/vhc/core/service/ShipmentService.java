package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Shipment;
import com.vhc.core.repository.ShipmentRepository;


@Service
public class ShipmentService {

	@Autowired
	private ShipmentRepository shipmentRepository;


	public Shipment getById(long shipmentid) {
		return shipmentRepository.findByShipmentid(shipmentid);
	}

	public List<Shipment> getAll() {
		return shipmentRepository.findAll();
	}

	public List<Shipment> getByName(String name) {
		return shipmentRepository.findByCodeIgnoreCaseLikeOrSupplier_nameIgnoreCaseLike(name, name);
	}

	@Transactional(rollbackFor=Exception.class)
	public Shipment save(Shipment shipment) {
		return shipmentRepository.save(shipment);
	}

}
