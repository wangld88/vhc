package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Status;

public interface StatusRepository extends CrudRepository<Status, Long> {

	public Status findByStatusid(long statusid);

	public Status findByName(String name);

	public Status findByNameAndReftbl(String name, String reftbl);

	public List<Status> findAll();

	public List<Status> findByReftbl(String reftbl);

	public List<Status> findByNameNotAndReftbl(String name, String reftbl);

}
