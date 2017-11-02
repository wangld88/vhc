package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Shipment;
import com.vhc.repository.ShipmentRepository;


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

	public Shipment save(Shipment shipment) {
		return shipmentRepository.save(shipment);
	}
	
}
