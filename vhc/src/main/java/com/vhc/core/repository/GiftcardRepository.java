package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Giftcard;


public interface GiftcardRepository extends CrudRepository<Giftcard, Long> {

	List<Giftcard> findAllByOrderByCode();

	Giftcard findByGiftcardid(long giftcardid);

	Giftcard findByCode(String code);

	Giftcard findByCodeAndPin(String code, String pin);

	List<Giftcard> findByCodeContainingOrderByCode(String name);

}
