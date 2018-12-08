package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Paymentmethod;

public interface PaymentmethodRepository extends CrudRepository<Paymentmethod, Long> {

	Paymentmethod findByMethodid(long methodid);

	List<Paymentmethod> findAll();

}
