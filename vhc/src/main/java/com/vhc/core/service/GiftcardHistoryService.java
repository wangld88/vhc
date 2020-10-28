package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Giftcard;
import com.vhc.core.model.GiftcardHistory;
import com.vhc.core.repository.GiftcardHistoryRepository;


@Service
public class GiftcardHistoryService {

	@Autowired
	GiftcardHistoryRepository giftcardHistoryRepository;


	@Transactional(readOnly=true)
	public GiftcardHistory getById(long gfhistoryid) {
		return giftcardHistoryRepository.findByGfhistoryid(gfhistoryid);
	}

	@Transactional(readOnly=true)
	public List<GiftcardHistory> getAll() {
		return giftcardHistoryRepository.findAllByOrderByGiftcard();
	}

	@Transactional(readOnly=true)
	public List<GiftcardHistory> getByGiftcard(Giftcard giftcard) {
		return giftcardHistoryRepository.findByGiftcard(giftcard);
	}

	@Transactional(rollbackFor=Exception.class)
	public GiftcardHistory save(GiftcardHistory giftcardHistory) {
		return giftcardHistoryRepository.save(giftcardHistory);
	}

	@Transactional(rollbackFor=Exception.class)
	public void delete(long gfhistoryid) {
		giftcardHistoryRepository.delete(gfhistoryid);
	}

}
