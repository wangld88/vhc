package com.vhc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Manufacture;
import com.vhc.repository.ManufactureRepository;

@Service
public class ManufactureService {

	@Autowired
	ManufactureRepository manufactureRepository;
	
	
	public Manufacture save(Manufacture manufacture) {
		return this.manufactureRepository.save(manufacture);
	}
	
}
