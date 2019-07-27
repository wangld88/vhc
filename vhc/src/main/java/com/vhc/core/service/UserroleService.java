package com.vhc.core.service;

import com.vhc.core.model.Userrole;
import com.vhc.core.repository.UserroleRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional(rollbackFor=Exception.class)
	public Userrole save(Userrole userrole) {
		return (Userrole)this.userroleRepository.save(userrole);
	}
}
