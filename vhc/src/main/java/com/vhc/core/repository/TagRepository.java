package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Tag;


public interface TagRepository extends CrudRepository<Tag, Long> {

	public Tag findByTagid(long tagid);
	public List<Tag> findAll();

}
