package com.vhc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.vhc.security.CustomSuccessHandler;
import com.vhc.security.SuperAdminSuccessHandler;
import com.vhc.security.UserAdminAuthenticationProvider;
import com.vhc.security.UserLoginService;


@Configuration
public class MultiHttpSecurityConfig {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
	UserLoginService userLoginService;

	/*@Autowired
	static CustomSuccessHandler customSuccessHandler;*/


	@Configuration
	public static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			//http.csrf().disable();

			http
			    .authorizeRequests()
		    	.antMatchers(new String[] { "/*" }).permitAll()
		    	.antMatchers(new String[] { "/error/**" }).permitAll()
		    	.antMatchers(new String[] { "/static/**" }).permitAll()
		    	.antMatchers(new String[] { "/layout/**" }).permitAll()
		    	.antMatchers(new String[] { "/admin/login/**" }).permitAll()
				//.antMatchers("/admin/products").permitAll()
				//.antMatchers("/admin/giftcardupload").permitAll()
		    	.antMatchers(new String[] { "/admin/**" }).hasAuthority("SUPERADMIN")
		    .and()
		    	.formLogin().loginPage("/admin/login").successHandler(new SuperAdminSuccessHandler())
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
		    /*.and()
		    	.rememberMe()
		    .and()
		    	.csrf().disable()*/;
			
			http.addFilterAfter(new WebSecurityFilter(), BasicAuthenticationFilter.class);
		}
	}


	@Configuration
	@Order(1)
	public static class StoreSecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {

			//http.csrf().disable();

			http
				.antMatcher("/store/**")
				.authorizeRequests()
		    	.antMatchers("/static/**").permitAll()
//				.antMatchers("/store/*").permitAll()
//				.antMatchers("/store/aboutus/*").permitAll()
//				.antMatchers("/store/brands/*").permitAll()
//				.antMatchers("/store/brand/*").permitAll()
//				.antMatchers("/store/mens/*").permitAll()
//				.antMatchers("/store/womens/*").permitAll()
//				.antMatchers("/store/womens/products").permitAll()
//				.antMatchers("/store/kids/*").permitAll()
//				.antMatchers("/store/accessories/*").permitAll()
//				.antMatchers("/store/shopping/**").permitAll()
//				.antMatchers("/store/products").permitAll()
//				.antMatchers("/store/cart/**").permitAll()
//				.antMatchers("/store/customer/cart/**").permitAll()
	         	.antMatchers("/vhc/store/admin/login/**").permitAll()
	        	.antMatchers("/store/admin/**").hasAuthority("ADMIN")
	                .anyRequest().authenticated()
	        .and()
		        .formLogin()
		            .loginPage("/store/admin/login")
		            .successHandler(new CustomSuccessHandler())
		            .usernameParameter("username")
		            //.defaultSuccessUrl("/admin/home")
		            .permitAll()
	        .and()
		        .logout()
		        	.logoutSuccessUrl("/store/admin/login")
		            .permitAll();
		    /*.and()
		    	.csrf().disable();*/
			http.addFilterAfter(new WebSecurityFilter(), BasicAuthenticationFilter.class);

		}
	}


//	@Configuration
//	@Order(2)
//	public static class CustomerSecurityConfiguration extends WebSecurityConfigurerAdapter {
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//
//			http
//				.antMatcher("/customer/**")
//				.authorizeRequests()
//	         	.antMatchers("/customer/login/**").permitAll()
//	        	.antMatchers("/customer/**").hasAuthority("CUSTOMER")
//	                .anyRequest().authenticated()
//	        .and()
//		        .formLogin()
//		            .loginPage("/customer/login")
//		            .successHandler(new CustomSuccessHandler())
//		            .usernameParameter("username")
//		            //.defaultSuccessUrl("/admin/home")
//		            .permitAll()
//	        .and()
//		        .logout()
//		        	.logoutSuccessUrl("/customer/login")
//		            .permitAll();
//		}
//	}


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
	    auth.userDetailsService(userLoginService);
	    auth.authenticationProvider(getAuthProvider());
	}


	/*@Autowired
	public void configureGlobal(WebSecurity web)
	    throws Exception {
	    web.ignoring()
	    	.antMatchers(new String[] { "/i18n/**" })
	    	.antMatchers(new String[] { "/static/**" });
	}*/


    @Bean
    public DaoAuthenticationProvider getAuthProvider() {
        final UserAdminAuthenticationProvider authProvider = new UserAdminAuthenticationProvider();
        authProvider.setUserDetailsService(userLoginService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;
    }
}
