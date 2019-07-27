package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Promocode;


public interface PromocodeRepository extends CrudRepository<Promocode, Long> {

	Promocode findByPromocodeid(long promocodeid);

	Promocode findByCode(String code);

	List<Promocode> findAll();

}
