package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Status;
import com.vhc.repository.StatusRepository;


@Service
public class StatusService {

	@Autowired
	private StatusRepository statusRepository;
	
	public Status getById(long statusid) {
		return statusRepository.findByStatusid(statusid);
	}

	public List<Status> findAll() {
		return statusRepository.findAll();
	}
	
	public List<Status> findByReftbl(String reftbl) {
		return statusRepository.findByReftbl(reftbl);
	}
	
	public Status save(Status status) {
		return statusRepository.save(status);
	}
}
