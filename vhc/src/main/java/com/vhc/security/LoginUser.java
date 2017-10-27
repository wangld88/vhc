package com.vhc.security;


import com.vhc.model.Userrole;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


public class LoginUser extends User {

	private static final long serialVersionUID = 1L;
	
	private com.vhc.model.User user;
	
	public LoginUser(com.vhc.model.User user) {
		super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles()));
		System.out.println("user.getUsername(): "+user.getUsername() + ", Password:" +user.getPassword()+", roles: "+user.getRoles());
		String[] roles = user.getRoles();
		for(String i: roles) {
			System.out.println("i :" + i);
		}
		this.user = user;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
	    List<GrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
	    for(Userrole role : user.getUserroles()) {
	        simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(role.getRole().getName()));
	    }
	    
	    return simpleGrantedAuthorityList;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	public com.vhc.model.User getUser() {
		return this.user;
	}
	
/*	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
*/	
}
