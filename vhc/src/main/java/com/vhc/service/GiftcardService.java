package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.model.Giftcard;
import com.vhc.repository.GiftcardRepository;


@Service
public class GiftcardService {

	@Autowired
	GiftcardRepository giftcardRepository;


	public Giftcard getById(long giftcardid) {
		return giftcardRepository.findByGiftcardid(giftcardid);
	}

	public List<Giftcard> getAll() {
		return giftcardRepository.findAll();
	}

	public Giftcard getByCode(String code) {
		return giftcardRepository.findByCode(code);
	}

	public Giftcard getByCodePin(String code, String pin) {
		return giftcardRepository.findByCodeAndPin(code, pin);
	}

	public Giftcard save(Giftcard giftcard) {
		return giftcardRepository.save(giftcard);
	}

	@Transactional
	public void delete(long giftcardid) {
		giftcardRepository.delete(giftcardid);
	}
}
