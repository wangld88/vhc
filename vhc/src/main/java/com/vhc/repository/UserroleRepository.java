package com.vhc.repository;

import com.vhc.model.Userrole;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface UserroleRepository extends CrudRepository<Userrole, Long> {

	public Userrole findByUserroleid(long userroleid);
	public List<Userrole> findAll();

}
