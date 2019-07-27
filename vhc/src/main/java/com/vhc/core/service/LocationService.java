package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Location;
import com.vhc.core.model.Store;
import com.vhc.core.repository.LocationRepository;

@Service
public class LocationService {

	@Autowired
	LocationRepository locationRepository;


	public List<Location> getAll() {
		return locationRepository.findAll();
	}

	public List<Location> getByName(String name) {
		return locationRepository.findByNameIgnoreCaseLike(name);
	}

	public List<Location> getByStore(Store store) {
		return locationRepository.findByStore(store);
	}

	public Location getById(long locationid) {
		return locationRepository.findByLocationid(locationid);
	}

	@Transactional(rollbackFor=Exception.class)
	public Location save(Location location) {
		return this.locationRepository.save(location);
	}

	@Transactional(rollbackFor=Exception.class)
	public void delete(long locationid) {
		locationRepository.delete(locationid);
	}
}
