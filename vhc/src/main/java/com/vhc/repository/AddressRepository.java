package com.vhc.repository;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {

}
