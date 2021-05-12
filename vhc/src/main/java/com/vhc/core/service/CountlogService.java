package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Countlog;
import com.vhc.core.model.Countupload;
import com.vhc.core.model.Inventory;
import com.vhc.core.model.Inventorycount;
import com.vhc.core.repository.CountlogRepository;


@Service
public class CountlogService {

	@Autowired
	private CountlogRepository countlogRepository;


	@Transactional(readOnly=true)
	public Countlog getById(long id) {

		return countlogRepository.findByCountlogid(id);

	}


	@Transactional(readOnly=true)
	public List<Countlog> getByUpload(Countupload upload) {

		return countlogRepository.findByUploadOrderByCountlogid(upload);
	}


	@Transactional(readOnly=true)
	public List<Countlog> getOverageByCount(Inventorycount count) {

		return countlogRepository.findByUpload_countAndInventoryIsNullOrderByCountlogid(count);
	}


	@Transactional(readOnly=true)
	public List<Countlog> getBySku(String sku) {

		return countlogRepository.findBySku(sku);
	}


	@Transactional(readOnly=true)
	public List<Countlog> getByInventory(Inventory inventory) {

		return countlogRepository.findByInventory(inventory);
	}


	@Transactional(readOnly=true)
	public List<Countlog> getAll() {

		return countlogRepository.findAll();
	}


	@Transactional(rollbackFor=Exception.class)
	public Countlog save(Countlog countlog) {

		return countlogRepository.save(countlog);

	}
}
