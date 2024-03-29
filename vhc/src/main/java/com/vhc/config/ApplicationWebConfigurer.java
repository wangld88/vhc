package com.vhc.config;

import java.util.Locale;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;


@Configuration
@EnableTransactionManagement
public class ApplicationWebConfigurer extends WebMvcConfigurerAdapter {

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.driverClassName}")
	private String className;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;


	private static final String[] MESSAGESOURCE_BASENAME = { "classpath:/i18n/messages", "classpath:/i18n/validationmessages" };

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(new String[] { "/static/**" }).addResourceLocations(new String[] { "classpath:/static/" });
	}

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();

    Resource[] resources = { new ClassPathResource("application.properties") };

    pspc.setLocations(resources);
    pspc.setIgnoreResourceNotFound(true);
    pspc.setIgnoreUnresolvablePlaceholders(true);
    pspc.setLocalOverride(true);
    return pspc;
  }

  @Bean(name={"messageSource"})
  public MessageSource getMessageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

    messageSource.setBasenames(MESSAGESOURCE_BASENAME);

    messageSource.setUseCodeAsDefaultMessage(true);

    return messageSource;
  }

  @Bean
  public LocaleResolver localeResolver() {
    CookieLocaleResolver resolver = new CookieLocaleResolver();
    resolver.setDefaultLocale(new Locale("en"));
    resolver.setCookieName("vhcLocaleCookie");
    resolver.setCookieMaxAge(Integer.valueOf(4800));
    resolver.setCookieHttpOnly(true);
    resolver.setRejectInvalidCookies(true);
    resolver.setCookieSecure(true);
    
    return resolver;
  }

  public void addInterceptors(InterceptorRegistry registry)  {
    LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
    interceptor.setParamName("lang");
    registry.addInterceptor(interceptor);
  }

//  @Bean(name="entityManagerFactory")
//  public HibernateJpaSessionFactoryBean sessionFactory() {
//      return new HibernateJpaSessionFactoryBean();
//  }

  @Bean(name="entityManagerFactory")
  public LocalSessionFactoryBean sessionFactory() {
      LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
      sessionFactory.setDataSource(dataSource());
      sessionFactory.setPackagesToScan("com.vhc.core.model");
      sessionFactory.setHibernateProperties(hibernateProperties());

      return sessionFactory;
  }

  @Bean
  public DataSource dataSource() {
	  BasicDataSource dataSource = new BasicDataSource();
	  dataSource.setDriverClassName(className);
	  dataSource.setUrl(url);
	  dataSource.setUsername(username);
	  dataSource.setPassword(password);

	  return dataSource;
  }

  private final Properties hibernateProperties() {
	  Properties hibernateProperties = new Properties();
	  hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "none");
	  hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

	  return hibernateProperties;
  }
  /*@Bean(name={"validator"})
  public LocalValidatorFactoryBean validator() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(getMessageSource());
    return bean;
  }

  @Override
  public Validator getValidator() {
    return validator();
  }*/
}
