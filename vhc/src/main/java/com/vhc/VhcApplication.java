package com.vhc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication()
@EnableAutoConfiguration
@EnableTransactionManagement
//@EnableJpaRepositories({"com.vhc.core.repository"})
@EntityScan({"com.vhc.core.model"})
@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)
public class VhcApplication {

	public static void main(String[] args) {
		//System.setProperty("spring.profiles.active", "production");
		ApplicationContext ctx = SpringApplication.run(VhcApplication.class, args);

	}
}