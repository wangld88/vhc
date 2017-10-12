package com.vhc.security;

import com.vhc.model.User;
import com.vhc.service.UserService;
import java.io.PrintStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserAdminAuthenticationProvider
  extends DaoAuthenticationProvider
{
  private static final Logger logger = LoggerFactory.getLogger(UserAdminAuthenticationProvider.class);
  @Autowired
  private UserService userService;
  
  public Authentication authenticate(Authentication auth)
    throws BadCredentialsException
  {
    System.out.println("++++++++++++++++UserAuthenticationProvider++++++++++++++++");
    if (auth == null) {
      System.out.println("auth is NULL");
    } else {
      System.out.println("auth.getName(): " + auth.getName() + ", " + auth.getCredentials());
    }
    User user = this.userService.findByUsername(auth.getName());
    if (user == null)
    {
      logger.error("User " + auth.getName() + "can not be found!!!!");
      throw new BadCredentialsException("Invalid username or password");
    }
    Authentication result = super.authenticate(auth);
    
    logger.info("result.getCredentials(): " + result.getCredentials().toString());
    
    return new UsernamePasswordAuthenticationToken(new LoginUser(user), result.getCredentials(), result.getAuthorities());
  }
  
  public boolean supports(Class<?> authentication)
  {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
