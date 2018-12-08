package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Status;

public interface StatusRepository extends CrudRepository<Status, Long> {

	public Status findByStatusid(long statusid);
	public Status findByName(String name);
	public List<Status> findAll();
	public List<Status> findByReftbl(String reftbl);

}
