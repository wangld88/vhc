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
	private Properties appProperties;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

		builder.sources(new Class[] { VhcApplication.class });

	    //loadAdditionalParams();

	    //builder.properties(this.additionalAppLvlProps);

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

	    String subenv = applicationprops.getProperty("application.profile");
System.out.println(subenv);
	    Properties subenvprops = new Properties();
	    if ((subenv != null) && (!subenv.isEmpty())) {
	      subenvprops = loadprops(String.format("/config/application-%s.properties", new Object[] { subenv }));
	    }
	    for (Object key : subenvprops.keySet()) {
	      this.additionalAppLvlProps.setProperty((String)key, subenvprops.getProperty((String)key));
	    }
	}

	private Properties loadprops(String propsUri) {
		InputStream inStream = getClass().getResourceAsStream(propsUri);
		Properties applicationprops = new Properties();

		if (inStream == null)
			throw new RuntimeException("Vault Exception: application.properties could not be loaded from resource directory " + propsUri);

		try {
			applicationprops.load( inStream );
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}
		finally {
		    try {
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return applicationprops;
		  //return appProperties;
	  }
}
