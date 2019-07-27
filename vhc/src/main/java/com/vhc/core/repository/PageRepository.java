package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Page;


public interface PageRepository extends CrudRepository<Page, Long> {

	public Page findByPageid(long pageid);

	public List<Page> findAll();

}
