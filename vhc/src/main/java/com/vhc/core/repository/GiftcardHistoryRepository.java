package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Giftcard;

import com.vhc.core.model.GiftcardHistory;


public interface GiftcardHistoryRepository extends CrudRepository<GiftcardHistory, Long> {

	GiftcardHistory findByGfhistoryid(long gfhistoryid);

	List<GiftcardHistory> findAllByOrderByGiftcard();

	List<GiftcardHistory> findByGiftcard(Giftcard giftcard);

}
