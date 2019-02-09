package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Payment;
import com.vhc.model.Paymentdetail;


public interface PaymentdetailRepository extends CrudRepository<Paymentdetail, Long> {

	Paymentdetail findByDetailid(long detailid);

	List<Paymentdetail> findByPayment(Payment payment);

}
