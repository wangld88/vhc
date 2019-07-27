package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Paymentmethod;

public interface PaymentmethodRepository extends CrudRepository<Paymentmethod, Long> {

	Paymentmethod findByMethodid(long methodid);

	Paymentmethod findByCode(String code);

	List<Paymentmethod> findAll();

	List<Paymentmethod> findByTypeLike(String type);

}
