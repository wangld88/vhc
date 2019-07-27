package com.vhc.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Address;
import com.vhc.core.repository.AddressRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;


	@Transactional(rollbackFor=Exception.class)
	public Address save(Address address) {
		return this.addressRepository.save(address);
	}
}
