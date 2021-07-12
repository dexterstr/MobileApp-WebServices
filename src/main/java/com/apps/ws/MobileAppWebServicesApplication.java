package com.apps.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.apps.ws.security.AppProperties;
import com.apps.ws.SpringApplicationContext;

@SpringBootApplication
public class MobileAppWebServicesApplication extends SpringBootServletInitializer {

	// For generating Web Application Archive(WAR) we comment this code and override
	// it
	// WAR to deploy in standalone apache tomcat
	
	public static void main(String[] args) {
		SpringApplication.run(MobileAppWebServicesApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MobileAppWebServicesApplication.class);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext() {

		return new SpringApplicationContext();
	}

	@Bean(name = "AppProperties")
	public AppProperties getAppProperties() {

		return new AppProperties();
	}

}
