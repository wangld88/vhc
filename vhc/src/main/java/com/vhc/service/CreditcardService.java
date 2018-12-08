package com.vhc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.model.Creditcard;
import com.vhc.repository.CreditcardRepository;


@Service
public class CreditcardService {

	@Autowired
	private CreditcardRepository creditcardRepository;

	public Creditcard getById(long cardid) {
		return creditcardRepository.findByCreditcardid(cardid);
	}

	public Creditcard getByCardnum(String cardnum) {
		return creditcardRepository.findByCardnum(cardnum);
	}

	@Transactional
	public Creditcard save(Creditcard card) {
		return creditcardRepository.save(card);
	}
}
