 package com.apps.ws.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.apps.ws.service.UserService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	//UserDetailsService-interface given by spring security
	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	
	public WebSecurity( UserService userDetailsService,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
		this.userService=userDetailsService;
	}
	
	@Override
	public void configure(HttpSecurity http)throws Exception{
		
		http
		.csrf()
		.disable()
		.authorizeRequests() 
		.antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL)
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.addFilter(getAuthenticationFilter())
		.addFilter(new AuthorizationFilter(authenticationManager()))
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	
	@Override
	public void configure(AuthenticationManagerBuilder auth)throws Exception{
		
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder); 
	}
	
	public AuthenticationFilter getAuthenticationFilter()throws Exception{
		final AuthenticationFilter filter=new AuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl("/users/login");
		return filter;
	}
	
	
}
