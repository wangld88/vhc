package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Region;
import com.vhc.core.repository.RegionRepository;

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

	@Transactional(rollbackFor=Exception.class)
	public Region save(Region region) {
		return this.regionRepository.save(region);
	}

}
