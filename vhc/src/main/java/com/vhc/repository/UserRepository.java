package com.vhc.repository;

import com.vhc.model.User;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	public User findByUsername(String username);

	public User findByUsernameAndPassword(String username, String password);

	public List<User> findByUserroles_role_name(String rolename);

	public List<User> findByUserroles_role_nameIn(List<String> rolenames);

	public List<User> findAll();

	public List<User> findByFirstnameIgnoreCaseLikeOrLastnameIgnoreCaseLike(String firstname, String lastname);
}
