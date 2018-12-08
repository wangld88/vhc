package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Giftcard;


public interface GiftcardRepository extends CrudRepository<Giftcard, Long> {

	List<Giftcard> findAll();

	Giftcard findByGiftcardid(long giftcardid);

	Giftcard findByCode(String code);

	Giftcard findByCodeAndPin(String code, String ping);

}
