package com.apps.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apps.ws.entity.UserEntity;
import com.apps.ws.exceptions.UserServiceException;
import com.apps.ws.model.response.ErrorMessages;
import com.apps.ws.repository.UserRepository;
import com.apps.ws.service.UserService;
import com.apps.ws.shared.AddressDto;
import com.apps.ws.shared.UserDto;
import com.apps.ws.shared.Utils;


@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	UserRepository userRepository;
	@Autowired
	Utils utils;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDto createUser(UserDto user) {
		// TODO Auto-generated method stub
		
		
		if(userRepository.findByEmail(user.getEmail()) !=null) throw new RuntimeException("Record already exists");
		for(int i=0;i<user.getAddresses().size();i++) {
			AddressDto address = user.getAddresses().get(i);
			address.setUserdetails(user);
			address.setAddressId(utils.generateAddressId(30));
			user.getAddresses().set(i, address);
			
			
		}
		
//		UserEntity userEntity = new UserEntity();
//		BeanUtils.copyProperties(user, userEntity);
		ModelMapper modelMapper= new ModelMapper();
		UserEntity userEntity=modelMapper.map(user, UserEntity.class);
		
		
		
		String publicUserId=utils.generateUserId(30);
		userEntity.setUserId(publicUserId);
		
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	
		UserEntity storedUserDetails=userRepository.save(userEntity);
		
//		UserDto returnValue= new UserDto();
//		BeanUtils.copyProperties(storedUserDetails, returnValue);
		UserDto returnValue=modelMapper.map(storedUserDetails,UserDto.class);
		
		return returnValue;
	}
	
	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity=userRepository.findByEmail(email);
		if(userEntity==null)throw new UsernameNotFoundException(email);
		 
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		
		return returnValue;
		
	}

	@Override
	// to load user details by username/param provided from database
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserEntity userEntity=userRepository.findByEmail(email);
		if(userEntity==null)throw new UsernameNotFoundException(email);
		
		
		
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUserByUserId(String userId) {

		UserDto returnValue = new UserDto();
		
		UserEntity userEntity=userRepository.findByUserId(userId);
		
		if(userEntity==null)throw new UsernameNotFoundException(userId);

		  BeanUtils.copyProperties(userEntity,returnValue );
		
		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto user) {
		
		UserDto returnValue = new UserDto();
		
		UserEntity userEntity= userRepository.findByUserId(userId);
		if(userEntity==null)throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		
		UserEntity updatedUserDetails=userRepository.save(userEntity);
		BeanUtils.copyProperties(updatedUserDetails,returnValue);
		return returnValue;
	}

	@Override
	public void deleteUser(String userId) {
		
		UserEntity userEntity= userRepository.findByUserId(userId);
		if(userEntity==null)throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		userRepository.delete(userEntity);
		
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		
		List<UserDto> returnValue=new ArrayList<>();
		
		
		 if(page>0)page-=1;
		
		Pageable pageablereq=PageRequest.of(page, limit);
		Page<UserEntity> usersPage=userRepository.findAll(pageablereq);
		
		List<UserEntity> users=usersPage.getContent();
		
		for(UserEntity userEntity: users) {
			
			UserDto userDto= new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}
		
		
		
		
		return returnValue;
	}

} 
