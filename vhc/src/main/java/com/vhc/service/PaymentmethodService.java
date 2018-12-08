package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Paymentmethod;
import com.vhc.repository.PaymentmethodRepository;

@Service
public class PaymentmethodService {

	@Autowired
	private PaymentmethodRepository paymentmethodRepository;

	public Paymentmethod getById(long methodid) {
		return paymentmethodRepository.findByMethodid(methodid);
	}

	public List<Paymentmethod> getAll() {
		return paymentmethodRepository.findAll();
	}
}
