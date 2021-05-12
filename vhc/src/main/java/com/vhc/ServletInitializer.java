package com.vhc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

import com.vhc.config.SessionListener;


@Configuration
public class ServletInitializer extends SpringBootServletInitializer {

	protected Properties additionalAppLvlProps = new Properties();

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

		builder.sources(new Class[] { VhcApplication.class });

	    return builder;

	}


	@Override
	protected WebApplicationContext run(SpringApplication application) {
		return super.run(application);
	}


//	@Override
//	public void onStartup(ServletContext servletContext)
//		throws ServletException {
//
//	    super.onStartup(servletContext);
//
//	    servletContext.addListener(new SessionListener(this.appProperties.getProperty("server.session.timeout")));
//
//	}

}
