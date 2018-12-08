package com.vhc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class VhcApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(VhcApplication.class, args);

	}
}