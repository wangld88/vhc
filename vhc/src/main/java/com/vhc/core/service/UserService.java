package com.vhc.core.service;

import com.vhc.core.model.User;
import com.vhc.core.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;


	@Transactional(readOnly=true)
	public User getByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}

	@Transactional(readOnly=true)
	public User getByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}

	@Transactional(readOnly=true)
	public User getById(long userid) {
		return userRepository.findByUserid(userid);
	}

	@Transactional(readOnly=true)
	public List<User> getByRolename(String rolename) {
		return userRepository.findByUserroles_role_name(rolename);
	}

	@Transactional(readOnly=true)
	public List<User> getByRolenames(List<String> rolenames) {
		return userRepository.findByUserroles_role_nameIn(rolenames);
	}

	@Transactional(readOnly=true)
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Transactional(readOnly=true)
	public List<User> getByName(String name) {
		return userRepository.findByFirstnameIgnoreCaseLikeOrLastnameIgnoreCaseLike(name, name);
	}

	@Transactional(readOnly=true)
	public User authenticate(String username, String password) {
		return this.userRepository.findByUsernameAndPassword(username, password);
	}

	@Transactional(rollbackFor=Exception.class)
	public User save(User user) {
		return (User)this.userRepository.save(user);
	}

    public User updatePassword(long userid, String password) {
    	User user = userRepository.findByUserid(userid);

    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	user.setPassword(passwordEncoder.encode(password));

    	return save(user);
    }

}
