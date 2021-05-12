package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Eventproduct;
import com.vhc.core.repository.EventproductRepository;


@Service
public class EventproductService {

	@Autowired
	private EventproductRepository eventproductRepository;

	@Transactional(readOnly=true)
	public Eventproduct getById(long id) {
		return eventproductRepository.findByEventprodid(id);
	}

	@Transactional(readOnly=true)
	public Eventproduct getByEventidProductid(long eventid, long productid) {
		return eventproductRepository.findByEvent_eventidAndProduct_productid(eventid, productid);
	}

	@Transactional(readOnly=true)
	public List<Eventproduct> getAll() {
		return eventproductRepository.findAll();
	}

	@Transactional(readOnly=true)
	public List<Eventproduct> getByEventid(long eventid) {
		return eventproductRepository.findByEvent_eventid(eventid);
	}

	@Transactional(rollbackFor=Exception.class)
	public Eventproduct save(Eventproduct eventproduct) {
		return eventproductRepository.save(eventproduct);
	}

	@Transactional(rollbackFor=Exception.class)
	public void delete(Eventproduct eventproduct) {
		eventproductRepository.delete(eventproduct);
	}
}
