package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Size;
import com.vhc.repository.SizeRepository;

@Service
public class SizeService {

	@Autowired
	SizeRepository sizeRepository;
	
	
	public List<Size> getAll() {
		return sizeRepository.findAll();
	}
	
	public List<Size> getByTypeAndRegion(long typeid, long regionid) {
		return sizeRepository.findByType_typeidAndRegion_regionid(typeid, regionid);
	}
	
	public Size getById(long sizeid) {
		return sizeRepository.findBySizeid(sizeid);
	}
	
	public Size save(Size size) {
		return this.sizeRepository.save(size);
	}
	
}
