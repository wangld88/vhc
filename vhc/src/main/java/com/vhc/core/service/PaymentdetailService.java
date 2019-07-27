package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Payment;
import com.vhc.core.model.Paymentdetail;
import com.vhc.core.repository.PaymentdetailRepository;


@Service
public class PaymentdetailService {

	@Autowired
	private PaymentdetailRepository paymentdetailRepository;


	@Transactional(readOnly=true)
	public Paymentdetail getById(long detailid) {
		return paymentdetailRepository.findByDetailid(detailid);
	}

	@Transactional(readOnly=true)
	public List<Paymentdetail> getByPayment(Payment payment) {
		return paymentdetailRepository.findByPayment(payment);
	}

}
