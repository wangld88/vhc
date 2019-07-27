package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.core.model.Paymentmethod;
import com.vhc.core.repository.PaymentmethodRepository;

@Service
public class PaymentmethodService {

	@Autowired
	private PaymentmethodRepository paymentmethodRepository;

	public Paymentmethod getById(long methodid) {
		return paymentmethodRepository.findByMethodid(methodid);
	}

	public Paymentmethod getByCode(String code) {
		return paymentmethodRepository.findByCode(code);
	}

	public List<Paymentmethod> getAll() {
		return paymentmethodRepository.findAll();
	}

	public List<Paymentmethod> getByType(String type) {
		return paymentmethodRepository.findByTypeLike(type);
	}

}
