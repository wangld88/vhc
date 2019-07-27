package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.core.model.Status;
import com.vhc.core.repository.StatusRepository;


@Service
public class StatusService {

	@Autowired
	private StatusRepository statusRepository;

	public Status getById(long statusid) {
		return statusRepository.findByStatusid(statusid);
	}

	public Status getByName(String name) {
		return statusRepository.findByName(name);
	}

	public Status getByNameAndReftbl(String name, String reftbl) {
		return statusRepository.findByNameAndReftbl(name, reftbl);
	}

	public List<Status> getAll() {
		return statusRepository.findAll();
	}

	public List<Status> getByReftbl(String reftbl) {
		return statusRepository.findByReftbl(reftbl);
	}

	public Status save(Status status) {
		return statusRepository.save(status);
	}
}
