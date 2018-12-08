package com.vhc.service;

import com.vhc.model.User;
import com.vhc.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;


	public User findByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}

	public User getById(long userid) {
		return userRepository.findOne(userid);
	}

	public List<User> getByRolename(String rolename) {
		return userRepository.findByUserroles_role_name(rolename);
	}

	public List<User> getByRolenames(List<String> rolenames) {
		return userRepository.findByUserroles_role_nameIn(rolenames);
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public List<User> getByName(String name) {
		return userRepository.findByFirstnameIgnoreCaseLikeOrLastnameIgnoreCaseLike(name, name);
	}

	public User authenticate(String username, String password) {
		return this.userRepository.findByUsernameAndPassword(username, password);
	}

	public User save(User user) {
		return (User)this.userRepository.save(user);
	}
}
