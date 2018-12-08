package com.vhc.service;

import com.vhc.model.Payment;
import com.vhc.repository.PaymentRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;


	public Payment getById(long paymentid) {
		return paymentRepository.findByPaymentid(paymentid);
	}

	public List<Payment> getAll() {
		return paymentRepository.findAll();
	}

	public Payment save(Payment payment) {
		return (Payment)this.paymentRepository.save(payment);
	}
}
