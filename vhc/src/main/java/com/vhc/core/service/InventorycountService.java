package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Inventorycount;
import com.vhc.core.repository.InventorycountRepository;


@Service
public class InventorycountService {

	@Autowired
	private InventorycountRepository inventorycountRepository;


	@Transactional(readOnly=true)
	public Inventorycount getById(long countid) {

		return inventorycountRepository.findByCountid(countid);

	}


	@Transactional(readOnly=true)
	public List<Inventorycount> getAll() {

		return inventorycountRepository.findAll();

	}


	@Transactional(rollbackFor=Exception.class)
	public Inventorycount save(Inventorycount inventorycount) {

		return inventorycountRepository.save(inventorycount);

	}
}
