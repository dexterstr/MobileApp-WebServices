package com.apps.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apps.ws.entity.AddressEntity;
import com.apps.ws.entity.UserEntity;
import com.apps.ws.repository.AddressRepository;
import com.apps.ws.repository.UserRepository;
import com.apps.ws.service.AddressService;
import com.apps.ws.shared.AddressDto;

@Service
public class AddressServiceImpl implements AddressService {

	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Override
	public List<AddressDto> getAddresses(String userId) {
		
		ModelMapper modelMapper = new ModelMapper();
		List<AddressDto> returnValue= new ArrayList<>();
		
		UserEntity userEntity=userRepository.findByUserId(userId);
		
		if(userEntity==null) return returnValue;
		
		Iterable<AddressEntity> addresses= addressRepository.findAllByUserDetails(userEntity);
		for( AddressEntity addressEntity : addresses) {
			
			returnValue.add(modelMapper.map(addressEntity,AddressDto.class));
		}
			
		
		
		return returnValue;
	}

	@Override
	public AddressDto getAddress(String addressId) {
		AddressDto returnValue=null;
		AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
		
		if(addressEntity!=null) {
			
			returnValue= new ModelMapper().map(addressEntity, AddressDto.class);
		}
		
		return returnValue;
		
		
		
		
	}

}
