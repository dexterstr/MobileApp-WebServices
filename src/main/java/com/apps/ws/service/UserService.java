package com.apps.ws.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.apps.ws.shared.UserDto;

public interface UserService extends UserDetailsService {

	
	UserDto createUser(UserDto user);
	UserDto getUser(String email);
	UserDto getUserByUserId(String userId);
	UserDto updateUser(String id,UserDto user);
	void deleteUser(String userId);
	
	List<UserDto> getUsers(int page,int limit);
	
}
 