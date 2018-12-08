package com.vhc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Debitcard;
import com.vhc.repository.DebitcardRepository;


@Service
public class DebitcardService {

	@Autowired
	private DebitcardRepository debitcardRepository;

	public Debitcard getById(long cardid) {
		return debitcardRepository.findByDebitcardid(cardid);
	}

	public Debitcard getByCardnum(String cardnum) {
		return debitcardRepository.findByCardnum(cardnum);
	}

	public Debitcard save(Debitcard card) {
		return debitcardRepository.save(card);
	}
}
