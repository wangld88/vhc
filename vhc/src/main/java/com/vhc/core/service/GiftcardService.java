package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Giftcard;
import com.vhc.core.repository.GiftcardRepository;


@Service
public class GiftcardService {

	@Autowired
	GiftcardRepository giftcardRepository;


	@Transactional(readOnly=true)
	public Giftcard getById(long giftcardid) {
		return giftcardRepository.findByGiftcardid(giftcardid);
	}

	@Transactional(readOnly=true)
	public List<Giftcard> getAll() {
		return giftcardRepository.findAll();
	}

	@Transactional(readOnly=true)
	public Giftcard getByCode(String code) {
		return giftcardRepository.findByCode(code);
	}

	@Transactional(readOnly=true)
	public Giftcard getByCodePin(String code, String pin) {
		return giftcardRepository.findByCodeAndPin(code, pin);
	}

	@Transactional(rollbackFor=Exception.class)
	public Giftcard save(Giftcard giftcard) {
		return giftcardRepository.save(giftcard);
	}

	@Transactional(rollbackFor=Exception.class)
	public void delete(long giftcardid) {
		giftcardRepository.delete(giftcardid);
	}

}
