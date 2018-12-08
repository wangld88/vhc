package com.vhc.service;

import com.vhc.model.Userrole;
import com.vhc.repository.UserroleRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserroleService {

	@Autowired
	private UserroleRepository userroleRepository;


	public Userrole getById(long userroleid) {
		return userroleRepository.findByUserroleid(userroleid);
	}

	public List<Userrole> getAll() {
		return userroleRepository.findAll();
	}

	public Userrole save(Userrole userrole) {
		return (Userrole)this.userroleRepository.save(userrole);
	}
}
