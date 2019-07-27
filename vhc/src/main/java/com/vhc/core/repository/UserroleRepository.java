package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Userrole;


public interface UserroleRepository extends CrudRepository<Userrole, Long> {

	public Userrole findByUserroleid(long userroleid);
	public List<Userrole> findAll();

}
