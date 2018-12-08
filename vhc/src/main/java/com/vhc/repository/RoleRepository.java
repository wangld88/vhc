package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Role;


public interface RoleRepository extends CrudRepository<Role, Long> {

	List<Role> findAll();

	Role findByRoleid(long roleid);

	Role findByName(String name);

}
