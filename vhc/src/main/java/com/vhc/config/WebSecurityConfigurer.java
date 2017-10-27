package com.vhc.config;

import com.vhc.security.CustomSuccessHandler;
import com.vhc.security.UserAdminAuthenticationProvider;
import com.vhc.security.UserLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
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
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserLoginService userLoginService;
	
	@Autowired
	CustomSuccessHandler customSuccessHandler;
	
	
    @Override
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
	    	.passwordParameter("password")
	    	.permitAll()
	    .and()
	    	.logout()
	    	.logoutUrl("/admin/logout")
	    	.deleteCookies(new String[] {"remember-me"})
	    	.logoutSuccessUrl("/admin/login")
	    	.permitAll()
	    .and()
	    	.rememberMe();
	}
	  
    @Override
	public void configure(AuthenticationManagerBuilder auth)
	    throws Exception {
    	//auth.inMemoryAuthentication()
        //.withUser("vhc").password("vhc").roles("SUPERADMIN"); 

	    auth.userDetailsService(userLoginService);
	    auth.authenticationProvider(getAuthProvider());
	}
	  
    @Override
	public void configure(WebSecurity web)
	    throws Exception {
	    web.ignoring()
	    	.antMatchers(new String[] { "/i18n/**" })
	    	.antMatchers(new String[] { "/static/**" });
	}
	
	@Bean
	public DaoAuthenticationProvider getAuthProvider() {
	    final UserAdminAuthenticationProvider authProvider = new UserAdminAuthenticationProvider();
	    authProvider.setUserDetailsService(userLoginService);
	    authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
	    
	    return authProvider;
	}
	  
	/*@Bean
	public SessionRegistry sessionRegistry() {
	    return new SessionRegistryImpl();
	}*/
}
