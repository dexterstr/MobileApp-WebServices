package com.apps.ws.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apps.ws.model.request.UserDetailsRequestModel;
import com.apps.ws.model.response.AddressesRest;
import com.apps.ws.model.response.OperationStatusModel;
import com.apps.ws.model.response.RequestOperationName;
import com.apps.ws.model.response.RequestOperationStatus;
import com.apps.ws.model.response.UserRest;
import com.apps.ws.service.AddressService;
import com.apps.ws.service.UserService;
import com.apps.ws.shared.AddressDto;
import com.apps.ws.shared.UserDto;


@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AddressService addressService;
	
	@Autowired 
	AddressService addreService;
	
	
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
	
	
	//localhost:8080/users-app/users/userId/addresses
	@GetMapping(path="/{id}/addresses",produces= {MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE})
	public List<AddressesRest> getUserAddresses(@PathVariable String id) {
		
		List<AddressesRest> returnValue= new ArrayList<>();
		
		List<AddressDto> addressDto=addressService.getAddresses(id);
		
		if(addressDto!=null & !addressDto.isEmpty()) 
		{
		
		java.lang.reflect.Type listType=new TypeToken<List<AddressesRest>>() {}.getType();
		
		returnValue=new ModelMapper().map(addressDto,listType);
		}	
		return returnValue;
		
		
	}
	
	@GetMapping(path="/{userId}/addresses/{addressId}",produces= {MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE})
	public EntityModel<AddressesRest> getUserAddress(@PathVariable String userId ,@PathVariable String addressId) {
		//public AddressesRest getUserAddress(@PathVariable String userId ,@PathVariable String addressId)
		
		
		AddressDto addressDto=addreService.getAddress(addressId);
		
		ModelMapper modelMapper=new ModelMapper();
		
		AddressesRest returnValue=modelMapper.map(addressDto, AddressesRest.class);
		
		//localhost:8080/users/<userId>
		//link for user
		Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(userId).withRel("user");
			
		
		//localhost:8080/users/<userId>/addresses
		//link for user with multiple addresses
		//the addresses is hardcoded so using methodOn method
//		Link userAddressesLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(userId).slash("addresses").withRel("addresses");
		Link userAddressesLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
				.getUserAddresses(userId))
				//.slash(userId)
				//.slash("addresses")
				.withRel("addresses");
		
		
		//link for this specific request and this address
		Link selfLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
				.getUserAddress(userId,addressId)).withSelfRel();
			
				//.slash(userId)
				//.slash("addresses")
				//.slash(addressId)
				//.withRel("self");
		
		
		//these work with representation model class
//		returnValue.add(userLink);
//		returnValue.add(userAddressesLink);
//		returnValue.add(selfLink);
		
		EntityModel.of(returnValue, Arrays.asList(userLink,userAddressesLink,selfLink));
		
		
		
		return EntityModel.of(returnValue, Arrays.asList(userLink,userAddressesLink,selfLink));
		
		
	}
	
	
	
	
	@GetMapping(produces= {MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE})
	public List<UserRest> getUsers(@RequestParam(value="page",defaultValue="0")int page,
			@RequestParam(value="limit",defaultValue="25")int limit)
	{
		
		List<UserRest> returnValue=new ArrayList<>();
		List<UserDto> users=userService.getUsers(page,limit);
		
		for(UserDto userDto: users) {
			UserRest userModel=new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
		returnValue.add(userModel);
			
		}
		
		return  returnValue;
	}
	
	
	
	
	//consumes means takes/providng data in json format i.e http request consumes our data and responses.
	// produces means sending a response //or the answer in simple terms. 
	@PostMapping(consumes= {MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE},
			produces= {MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE})
	//post request must contain a jsonbody which will contain details of user
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserRest returnValue = new UserRest();
		
		if(userDetails.getFirstName().isEmpty()) throw new NullPointerException("firstname is empty");
		
		UserDto userDto= new UserDto();
		
		//this api is best to copy properties from one source obj to another but if an object contains another
		//object inside its difficult for BeanUtils to figure data-type.
		//alternate to this is modelmapper
//		BeanUtils.copyProperties(userDetails,userDto);
		
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.map(userDetails, userDto);
		
		UserDto createdUser = userService.createUser(userDto);
//		BeanUtils.copyProperties(createdUser,returnValue);
		
		returnValue=modelMapper.map(createdUser, UserRest.class);
		
		return returnValue;
	}
	
	@PutMapping(path="/{id}",
			consumes= {MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE},
			produces= {MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE})
	public UserRest updateUser(@PathVariable String id,@RequestBody UserDetailsRequestModel userDetails) {
		
		UserRest returnValue= new UserRest();
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto updatedUser= userService.updateUser(id,userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);
		
		
		return returnValue;
	}
	
	
	@DeleteMapping(path="/{id}",produces= {MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE})
	//created a new class OperationStatusModel
	public OperationStatusModel deleteUser(@PathVariable String id)
	{
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		
		userService.deleteUser(id);
		
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		
		return returnValue;
	}

}
