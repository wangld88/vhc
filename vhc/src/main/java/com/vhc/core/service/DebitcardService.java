package com.vhc.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Debitcard;
import com.vhc.core.repository.DebitcardRepository;


@Service
public class DebitcardService {

	@Autowired
	private DebitcardRepository debitcardRepository;

	@Transactional(readOnly=true)
	public Debitcard getById(long cardid) {
		return debitcardRepository.findByDebitcardid(cardid);
	}

	@Transactional(readOnly=true)
	public Debitcard getByCardnum(String cardnum) {
		return debitcardRepository.findByCardnum(cardnum);
	}

	@Transactional(rollbackFor=Exception.class)
	public Debitcard save(Debitcard card) {
		return debitcardRepository.save(card);
	}

}
