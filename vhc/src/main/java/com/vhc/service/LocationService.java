package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Location;
import com.vhc.model.Store;
import com.vhc.repository.LocationRepository;

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

	public Location save(Location location) {
		return this.locationRepository.save(location);
	}

	public void delete(long locationid) {
		locationRepository.delete(locationid);
	}
}
