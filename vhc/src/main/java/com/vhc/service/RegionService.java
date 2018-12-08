package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Region;
import com.vhc.repository.RegionRepository;

@Service
public class RegionService {

	@Autowired
	RegionRepository regionRepository;


	public List<Region> getAll() {
		return regionRepository.findAll();
	}

	public Region getById(long regionid) {
		return regionRepository.findByRegionid(regionid);
	}

	public Region save(Region region) {
		return this.regionRepository.save(region);
	}

}
