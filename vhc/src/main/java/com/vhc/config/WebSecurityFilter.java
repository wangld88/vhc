package com.vhc.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.google.common.net.HttpHeaders;


@Component
public class WebSecurityFilter extends GenericFilterBean {

	private static final List<String> PATHS_TO_IGNORE_SETTING = Arrays.asList("resources");
	private static final String SESSION_COOKIE_NAME = "JSESSIONID";
	private static final String SAME_SITE_ATTRIBUTE_VALUES = ";HttpOnly;Secure;SameSite=Strict";
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		var requestURL = req.getRequestURL().toString();
		
		boolean isResourceRequest = requestURL != null? StringUtils.isNoneBlank(PATHS_TO_IGNORE_SETTING.stream().filter(s -> requestURL.contains(s)).findFirst().orElse(null)) : null;
		
		if(isResourceRequest) {
			Cookie[] cookies = req.getCookies();
			if(cookies != null && cookies.length > 0) {
				List<Cookie> cookieList = Arrays.asList(cookies);
				
				for(Cookie c: cookieList) {
					System.out.println("++++++++++++++ Cookie: " + c.getName());
					if(SESSION_COOKIE_NAME.equals(c.getName())) {
						resp.setHeader(HttpHeaders.SET_COOKIE, c.getName() + "=" + c.getValue() + SAME_SITE_ATTRIBUTE_VALUES);
					} else {
						c.setMaxAge(0);
					}
				}
//				var sessionCookie = cookieList.stream().filter(cookie -> SESSION_COOKIE_NAME.equals(cookie.getName())).findFirst().orElse(null);
//				if(sessionCookie != null) {
//					resp.setHeader(HttpHeaders.SET_COOKIE, sessionCookie.getName() + "=" + sessionCookie.getValue() + SAME_SITE_ATTRIBUTE_VALUES);
//				}
			}
		}
		
		chain.doFilter(request, response);
	}

}
