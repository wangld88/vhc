package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Cardtype;


public interface CardtypeRepository extends CrudRepository<Cardtype, Long> {

	Cardtype findByCardtypeid(long cardtypeid);

	Cardtype findByName(String name);

	List<Cardtype> findAll();

}
