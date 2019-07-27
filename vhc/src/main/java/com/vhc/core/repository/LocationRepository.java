package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Location;
import com.vhc.core.model.Store;

public interface LocationRepository extends CrudRepository<Location, Long> {

	List<Location> findAll();

	List<Location> findByNameIgnoreCaseLike(String name);

	List<Location> findByStore(Store store);

	Location findByLocationid(long locationid);

}
