package com.vhc.repository;

import com.vhc.model.Tag;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface TagRepository extends CrudRepository<Tag, Long> {

	public Tag findByTagid(long tagid);
	public List<Tag> findAll();

}
