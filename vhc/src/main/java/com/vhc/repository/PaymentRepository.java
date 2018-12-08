package com.vhc.repository;

import com.vhc.model.Payment;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface PaymentRepository extends CrudRepository<Payment, Long> {

	public Payment findByPaymentid(long paymentid);
	public List<Payment> findAll();

}
