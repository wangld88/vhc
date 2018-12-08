package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Location;
import com.vhc.model.Store;

public interface LocationRepository extends CrudRepository<Location, Long> {

	List<Location> findAll();

	List<Location> findByNameIgnoreCaseLike(String name);

	List<Location> findByStore(Store store);

	Location findByLocationid(long locationid);

}
