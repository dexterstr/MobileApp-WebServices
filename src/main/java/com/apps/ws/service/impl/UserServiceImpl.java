package com.apps.ws.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.ws.shared.dto.UserDto;
import com.apps.ws.entity.UserEntity;
import com.apps.ws.repository.UserRepository;
import com.apps.ws.service.UserService;
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
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		
		
		String publicUserId=utils.generateUserId(30);
		userEntity.setUserId(publicUserId);
		
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	
		UserEntity storedUserDetails=userRepository.save(userEntity);
	
		
		
		
		
		UserDto returnValue= new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnValue);
		
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

}
