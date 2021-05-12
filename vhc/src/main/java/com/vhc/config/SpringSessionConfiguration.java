package com.vhc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;


@Configuration
public class SpringSessionConfiguration {

	@Bean
	public CookieSerializer cookieSerializer() {
	    DefaultCookieSerializer serializer = new DefaultCookieSerializer();
	    serializer.setSameSite("Lax");
	    serializer.setUseHttpOnlyCookie(true);
	    serializer.setUseSecureCookie(true);

	    return serializer;
	}

}
