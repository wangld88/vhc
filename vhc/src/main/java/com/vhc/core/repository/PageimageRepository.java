package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Page;
import com.vhc.core.model.Pageimage;

public interface PageimageRepository extends CrudRepository<Pageimage, Long> {

	Pageimage findByPageimageid(long pageimageid);

	List<Pageimage> findByPageOrderBySeqnum(Page page);

	List<Pageimage> findAll();

}
