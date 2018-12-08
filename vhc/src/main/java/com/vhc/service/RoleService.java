package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Role;
import com.vhc.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;


	public List<Role> getAll() {
		return roleRepository.findAll();
	}

	public Role getById(long roleid) {
		return roleRepository.findByRoleid(roleid);
	}

	public Role getByName(String name) {
		return roleRepository.findByName(name);
	}

	public Role save(Role role) {
		return this.roleRepository.save(role);
	}

}
