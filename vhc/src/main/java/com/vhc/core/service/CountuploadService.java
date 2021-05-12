package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Countupload;
import com.vhc.core.model.Inventorycount;
import com.vhc.core.repository.CountuploadRepository;

@Service
public class CountuploadService {

	@Autowired
	private CountuploadRepository countuploadRepository;


	@Transactional(readOnly=true)
	public Countupload getById(long id) {

		return countuploadRepository.findByCountuploadid(id);

	}

	@Transactional(readOnly=true)
	public List<Countupload> getAll() {

		return countuploadRepository.findAll();

	}

	@Transactional(readOnly=true)
	public List<Countupload> getByCount(Inventorycount count) {

		return countuploadRepository.findByCount(count);

	}

	@Transactional(rollbackFor=Exception.class)
	public Countupload save(Countupload countupload) {

		return countuploadRepository.save(countupload);

	}
}
