package com.apps.ws.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ws.shared.dto.UserDto;
import com.apps.ws.model.request.UserDetailsRequestModel;
import com.apps.ws.model.response.UserRest;
import com.apps.ws.service.UserService;


@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	//binding http boot requests for user
	// they return json payload, for now just my text. 
	@GetMapping
	public String getUser() {
		
		return "get user was called";
		
	}
	
	@PostMapping
	//post request must contain a jsonbody which will contain details of user
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnValue = new UserRest();
		
		UserDto userDto= new UserDto();
		
		BeanUtils.copyProperties(userDetails,userDto);
		
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser,returnValue);
		
		
		return returnValue;
	}
	
	@PutMapping
	public String updateUser() {
		return "update User was called";
	}
	
	
	@DeleteMapping
	public String deleteUser() {
		
		return "delete User was called";
	}

}
