package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vhc.core.model.Countupload;
import com.vhc.core.model.Inventorycount;


@Repository
public interface CountuploadRepository extends CrudRepository<Countupload, Long> {

	Countupload findByCountuploadid(long id);

	List<Countupload> findAll();

	List<Countupload> findByCount(Inventorycount count);

}
