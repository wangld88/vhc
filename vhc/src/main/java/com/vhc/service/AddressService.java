package com.vhc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Address;
import com.vhc.repository.AddressRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	
	public Address save(Address address) {
		return this.addressRepository.save(address);
	}
}
