package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Shippingmethod;
import com.vhc.core.repository.ShippingmethodRepository;


@Service
public class ShippingmethodService {

	@Autowired
	private ShippingmethodRepository shippingmethodRepository;

	public Shippingmethod getById(long shipmethodid) {

		return shippingmethodRepository.findByShipmethodid(shipmethodid);

	}


	public List<Shippingmethod> getAll() {

		return shippingmethodRepository.findAll();

	}


	@Transactional(rollbackFor=Exception.class)
	public Shippingmethod save(Shippingmethod shippingmethod) {

		return shippingmethodRepository.save(shippingmethod);

	}
}
