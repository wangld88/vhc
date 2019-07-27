package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Payment;


public interface PaymentRepository extends CrudRepository<Payment, Long> {

	public Payment findByPaymentid(long paymentid);
	public List<Payment> findAll();

}
