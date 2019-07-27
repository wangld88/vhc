package com.vhc.core.repository;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {

}
