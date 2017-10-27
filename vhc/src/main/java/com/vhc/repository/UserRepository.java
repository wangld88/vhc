package com.vhc.repository;

import com.vhc.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	public User findByUsername(String username);
	public User findByUsernameAndPassword(String username, String password);
}
