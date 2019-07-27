package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Cardtype;
import com.vhc.core.repository.CardtypeRepository;


@Service
public class CardtypeService {

	@Autowired
	private CardtypeRepository cardtypeRepository;


	@Transactional(readOnly=true)
	public Cardtype getById(long cardtypeid) {
		return cardtypeRepository.findByCardtypeid(cardtypeid);
	}

	@Transactional(readOnly=true)
	public Cardtype getByName(String name) {
		return cardtypeRepository.findByName(name);
	}

	@Transactional(readOnly=true)
	public List<Cardtype> getAll() {
		return cardtypeRepository.findAll();
	}
}
