package com.vhc.repository;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Debitcard;

public interface DebitcardRepository extends CrudRepository<Debitcard, Long> {

	Debitcard findByDebitcardid(long Debitcardid);

	Debitcard findByCardnum(String cardnum);

}
