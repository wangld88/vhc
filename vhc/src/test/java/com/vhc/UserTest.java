package com.vhc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.vhc.model.User;
import com.vhc.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

	@Autowired
	private UserService userService;
	//@Autowired
	//private BCryptPasswordEncoder encoder;
	
	@Test
	public void test() {
		User user = userService.findByUsername("vhc");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = encoder.encode("vhc1");
		//String password = "vhc1";
		user.setPassword(password);
		userService.save(user);
		
	}
}
