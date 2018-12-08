package com.vhc.repository;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Creditcard;

public interface CreditcardRepository extends CrudRepository<Creditcard, Long> {

	Creditcard findByCreditcardid(long creditcardid);

	Creditcard findByCardnum(String cardnum);

}
