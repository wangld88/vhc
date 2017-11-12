package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Image;

public interface ImageRepository extends CrudRepository<Image, Long> {

	public Image findByImageid(long imageid);
	public List<Image> findByProduct_productid(long productid);
	public List<Image> findAll();
	
}
