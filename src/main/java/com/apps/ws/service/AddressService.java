package com.apps.ws.service;

import java.util.List;

import com.apps.ws.shared.AddressDto;

public interface AddressService {

	List<AddressDto> getAddresses(String userId);
	AddressDto getAddress(String addressId);
	
	
}
