package com.vhc.service;

import com.vhc.model.User;
import com.vhc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
  @Autowired
  private UserRepository userRepository;
  
  public User findByUsername(String username)
  {
    return this.userRepository.findByUsername(username);
  }
  
  public User save(User user)
  {
    return (User)this.userRepository.save(user);
  }
}
