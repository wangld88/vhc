package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Status;
import com.vhc.core.repository.StatusRepository;


@Service
public class StatusService {

	@Autowired
	private StatusRepository statusRepository;

	@Transactional(readOnly=true)
	public Status getById(long statusid) {
		return statusRepository.findByStatusid(statusid);
	}

	@Transactional(readOnly=true)
	public Status getByName(String name) {
		return statusRepository.findByName(name);
	}

	@Transactional(readOnly=true)
	public Status getByNameAndReftbl(String name, String reftbl) {
		return statusRepository.findByNameAndReftbl(name, reftbl);
	}

	@Transactional(readOnly=true)
	public List<Status> getAll() {
		return statusRepository.findAll();
	}

	@Transactional(readOnly=true)
	public List<Status> getByReftbl(String reftbl) {
		return statusRepository.findByReftbl(reftbl);
	}

	@Transactional(readOnly=true)
	public List<Status> getByReftblExclude(String name, String reftbl) {
		return statusRepository.findByNameNotAndReftbl(name, reftbl);
	}

	@Transactional(rollbackFor=Exception.class)
	public Status save(Status status) {
		return statusRepository.save(status);
	}
}
