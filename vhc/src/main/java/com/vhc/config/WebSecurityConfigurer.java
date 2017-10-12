package com.vhc.config;

import com.vhc.security.CustomSuccessHandler;
import com.vhc.security.UserAdminAuthenticationProvider;
import com.vhc.security.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan({"com.vhc.security"})
@Order(2147483640)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Autowired
	UserLoginService userLoginService;
	
	@Autowired
	CustomSuccessHandler customSuccessHandler;
	
	
	protected void configure(HttpSecurity http)
	    throws Exception {
	    http.authorizeRequests()
	    	.antMatchers(new String[] { "/" }).permitAll()
	    	.antMatchers(new String[] { "/error/**" }).permitAll()
	    	.antMatchers(new String[] { "/static/**" }).permitAll()
	    	.antMatchers(new String[] { "/layout/**" }).permitAll()
	    	.antMatchers(new String[] { "/admin/login/**" }).permitAll()
	    	.antMatchers(new String[] { "/admin/**" }).hasAuthority("SUPERADMIN")
	    .and()
	    	.formLogin().loginPage("/admin/login").successHandler(this.customSuccessHandler)
	    	.failureUrl("/admin/login?error")
	    	.usernameParameter("username")
	    	.permitAll()
	    .and()
	    	.logout()
	    	.logoutUrl("/admin/logout")
	    	.deleteCookies(new String[] { "remember-me" })
	    	.logoutSuccessUrl("/admin/login")
	    	.permitAll()
	    .and()
	    	.rememberMe();
	}
	  
	public void configure(AuthenticationManagerBuilder auth)
	    throws Exception {
	    auth.userDetailsService(this.userLoginService);
	    auth.authenticationProvider(getAuthProvider());
	}
	  
	public void configure(WebSecurity web)
	    throws Exception {
	    web.ignoring()
	    	.antMatchers(new String[] { "/i18n/**" })
	    	.antMatchers(new String[] { "/static/**" });
	}
	
	@Bean
	public DaoAuthenticationProvider getAuthProvider() {
	    System.out.println("***********getAuthProvider************");
	    UserAdminAuthenticationProvider authProvider = new UserAdminAuthenticationProvider();
	    authProvider.setUserDetailsService(this.userLoginService);
	    authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
	    
	    return authProvider;
	}
	  
	@Bean
	public SessionRegistry sessionRegistry() {
	    return new SessionRegistryImpl();
	}
}
