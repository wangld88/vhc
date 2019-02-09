package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Page;
import com.vhc.model.Pageimage;

public interface PageimageRepository extends CrudRepository<Pageimage, Long> {

	Pageimage findByPageimageid(long pageimageid);

	List<Pageimage> findByPageOrderBySeqnum(Page page);

	List<Pageimage> findAll();

}
