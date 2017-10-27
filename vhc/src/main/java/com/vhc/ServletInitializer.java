package com.vhc;

import com.vhc.config.SessionListener;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;


@Configuration
public class ServletInitializer extends SpringBootServletInitializer {

	protected Properties additionalAppLvlProps = new Properties();
	private Properties appProperties;
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		
		builder.sources(new Class[] { VhcApplication.class });
	    
	    //loadAdditionalParams();
	    
	    builder.properties(this.additionalAppLvlProps);
	    
	    return builder;	
	    
	}

	
	@Override
	protected WebApplicationContext run(SpringApplication application) {
		return super.run(application);
	}
	
	
	@Override
	public void onStartup(ServletContext servletContext)
		throws ServletException {
		
	    super.onStartup(servletContext);
	    
	    //servletContext.addListener(new SessionListener(this.appProperties.getProperty("server.session.timeout")));
	    
	}
	  
	private void loadAdditionalParams() {
		
	    String propsUri = "/config/application.properties";
	    
	    Properties applicationprops = loadprops(propsUri);
	    this.appProperties = applicationprops;
	    
	    String subenv = applicationprops.getProperty("vault.subenv");
	    
	    Properties subenvprops = new Properties();
	    if ((subenv != null) && (!subenv.isEmpty())) {
	      subenvprops = loadprops(String.format("/config/%s.properties", new Object[] { subenv }));
	    }
	    for (Object key : subenvprops.keySet()) {
	      this.additionalAppLvlProps.setProperty((String)key, subenvprops.getProperty((String)key));
	    }
	  }

	  private Properties loadprops(String propsUri) {
		  return appProperties;
	  }
}
