package com.apps.ws.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	// they return json payload, 
	//by default the format is json, to change it to xml
	//use produces=....mentioned below
	//i have configure here to respond in either format based on order.-here xml first
	@GetMapping(path="/{id}",produces= {MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE})
	public UserRest getUser(@PathVariable String id) {
		
		UserRest returnValue= new UserRest();
		UserDto userDto=userService.getUserByUserId(id);
		
		BeanUtils.copyProperties(userDto, returnValue);
		
		return returnValue;
		
	}
	//consumes means takes/providng data in json format i.e http request consumes our data and responses.
	// produces means sending a response //or the answer in simple terms.
	@PostMapping(consumes= {MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE},
			produces= {MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE})
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
