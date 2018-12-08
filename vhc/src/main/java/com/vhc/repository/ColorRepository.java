package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Color;

public interface ColorRepository extends CrudRepository<Color, Long> {

	List<Color> findAll();

	List<Color> findByNameIgnoreCaseLike(String name);

	Color findByColorid(long colorid);

}
