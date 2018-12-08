package com.vhc.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private static final Logger logger = LoggerFactory.getLogger(CustomSuccessHandler.class);

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
		throws IOException {
		logger.info("CustomSuccessHandler is called");
		String targetUrl = determineTargetUrl(authentication);

		logger.info("CustomSuccessHandler is called, targetUrl: " + targetUrl);
		if (response.isCommitted()) {
			logger.info("Can't redirect");
			return;
		}
		this.redirectStrategy.sendRedirect(request, response, targetUrl);
	}


	protected String determineTargetUrl(Authentication authentication) {
		String url = "";

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		List<String> roles = new ArrayList<String>();

		for (GrantedAuthority a : authorities) {
			roles.add(a.getAuthority());
		}
		logger.info("User login roles: "+roles.toString());

		if (isStaff(roles)) {
			url = "/store/staff/home";
		} else if (isAdmin(roles)) {
			url = "/store/admin/home";
		} else if (isCustomer(roles)) {
			url = "/customer/home";
		} else {
			logger.info("User is not admin/staff/customer with password failed login.");
			url = "/error/accessdenied";
		}

		return url;
	}

	private boolean isAdmin(List<String> roles) {
		if (roles.contains("ADMIN")) {
			return true;
		}
		return false;
	}

	private boolean isStaff(List<String> roles) {
		if (roles.contains("STAFF")) {
			return true;
		}
		return false;
	}

	private boolean isCustomer(List<String> roles) {
		if (roles.contains("CUSTOMER")) {
			return true;
		}
		return false;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return this.redirectStrategy;
	}
}
