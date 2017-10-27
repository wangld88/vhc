package com.vhc.security;

import com.vhc.model.User;
import com.vhc.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


public class UserAdminAuthenticationProvider
	extends DaoAuthenticationProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAdminAuthenticationProvider.class);
	
	@Autowired
	private UserService userService;
  
	@Override
	public Authentication authenticate(Authentication auth)
		throws AuthenticationException {
    
		System.out.println("++++++++++++++++UserAuthenticationProvider++++++++++++++++");

		if (auth == null) {
			System.out.println("auth is NULL");
	    } else {
	    	System.out.println("auth.getName(): " + auth.getName() + ", password: " + auth.getCredentials() + ", principal:" + auth.getPrincipal());
	    }
    
		//User user = this.userService.authenticate(auth.getName(), auth.getCredentials().toString());
		User user = this.userService.findByUsername(auth.getName());
    
		if (user == null) {
			logger.error("User " + auth.getName() + " can not be found!!!!");
			throw new BadCredentialsException("Invalid username or password");
		}
		Authentication result = null;
		try {
			result = super.authenticate(auth);
		//System.out.println("!!!!!!!!!!result.getCredentials(): " + result.getCredentials().toString());
		//logger.info("result.getCredentials(): " + result.getCredentials().toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return new UsernamePasswordAuthenticationToken(new LoginUser(user), result.getCredentials(), result.getAuthorities());
	}
  
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
