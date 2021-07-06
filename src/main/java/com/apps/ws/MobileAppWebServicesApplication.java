package com.apps.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.apps.ws.security.AppProperties;

@SpringBootApplication
public class MobileAppWebServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileAppWebServicesApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder  bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SpringApplicationContext springApplicationContext(){
		
		return new SpringApplicationContext();
	}
	
	@Bean(name="AppProperties")
	public AppProperties getAppProperties() {
		
		return new AppProperties();
	}

}
 