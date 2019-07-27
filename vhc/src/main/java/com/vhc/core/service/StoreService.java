package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Store;
import com.vhc.core.repository.StoreRepository;

@Service
public class StoreService {

	@Autowired StoreRepository storeRespository;


	@Transactional(readOnly=true)
	public Store getById(long storeid) {
		return storeRespository.findByStoreid(storeid);
	}

	@Transactional(readOnly=true)
	public Store getByName(String name) {
		return storeRespository.findByName(name);
	}

	@Transactional(readOnly=true)
	public List<Store> getAll() {
		return storeRespository.findAll();
	}

	@Transactional(rollbackFor=Exception.class)
	public Store save(Store store) {
		return storeRespository.save(store);
	}
}
