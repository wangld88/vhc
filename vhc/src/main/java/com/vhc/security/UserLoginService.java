package com.vhc.security;

import com.vhc.model.User;
import com.vhc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserLoginService implements UserDetailsService {
  
	@Autowired
	UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	
	@Override
	public UserDetails loadUserByUsername(String username)
		throws UsernameNotFoundException {
    
		System.out.println("------------------UserLoginService----------------");
    
		logger.info(String.format("[UserLoginService] User Auth Begins. Locating User %s in DB", new Object[] { username }));
    
		User user = this.userService.findByUsername(username);
		
	    if (user == null) {
	    	logger.info("[UserLoginService] User is not found!!!!!!!!!!!!!!");
	    	throw new UsernameNotFoundException(String.format("User with username %s was not found", new Object[] { username }));
	    }
	    
	    logger.info("login user detail found: " + user.getPassword());
	    
	    return new LoginUser(user);
  }
}
