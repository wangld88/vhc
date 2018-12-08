package com.vhc.repository;

import com.vhc.model.Page;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface PageRepository extends CrudRepository<Page, Long> {

	public Page findByPageid(long pageid);

	public List<Page> findAll();

}
