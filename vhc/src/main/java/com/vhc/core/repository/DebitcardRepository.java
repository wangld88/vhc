package com.vhc.core.repository;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Debitcard;

public interface DebitcardRepository extends CrudRepository<Debitcard, Long> {

	Debitcard findByDebitcardid(long Debitcardid);

	Debitcard findByCardnum(String cardnum);

}
