package com.vhc.core.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Countinventory;
import com.vhc.core.model.Inventorycount;
import com.vhc.core.repository.CountinventoryRepository;


@Service
public class CountinventoryService {

	@Autowired
	private CountinventoryRepository countinventoryRepository;


	@Transactional(readOnly=true)
	public List<Countinventory> getByCount(Inventorycount count) {

		return countinventoryRepository.findByCount(count);
	}


	@Transactional(readOnly=true)
	public List<Countinventory> getShortageByCount(Inventorycount count) {

		return countinventoryRepository.findByCountAndCountlogIsNullOrderByCountinventoryid(count);
	}


	@Transactional(readOnly=true)
	public List<Countinventory> getByUPC(String sku) {

		return countinventoryRepository.findBySku(sku);
	}

	@Transactional(readOnly=true)
	public List<Countinventory> getAvailableByUPC(String sku) {

		return countinventoryRepository.findBySkuAndCountlogIsNull(sku);
	}


	@Transactional(rollbackFor=Exception.class)
	public Countinventory save(Countinventory countinventory) {

		return countinventoryRepository.save(countinventory);

	}
}
