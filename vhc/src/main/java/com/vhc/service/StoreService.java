package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Store;
import com.vhc.repository.StoreRepository;

@Service
public class StoreService {

	@Autowired StoreRepository storeRespository;
	
	
	public Store getById(long storeid) {
		return storeRespository.findByStoreid(storeid);
	}
	
	public List<Store> getAll() {
		return storeRespository.findAll();
	}

	public Store save(Store store) {
		return storeRespository.save(store);
	}
}
