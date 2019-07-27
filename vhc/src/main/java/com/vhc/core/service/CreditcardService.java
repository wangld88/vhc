package com.vhc.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Creditcard;
import com.vhc.core.repository.CreditcardRepository;


@Service
public class CreditcardService {

	@Autowired
	private CreditcardRepository creditcardRepository;


	@Transactional(readOnly=true)
	public Creditcard getById(long cardid) {
		return creditcardRepository.findByCreditcardid(cardid);
	}

	@Transactional(readOnly=true)
	public Creditcard getByCardnum(String cardnum) {
		return creditcardRepository.findByCardnum(cardnum);
	}

	@Transactional(rollbackFor=Exception.class)
	public Creditcard save(Creditcard card) {
		return creditcardRepository.save(card);
	}
}
