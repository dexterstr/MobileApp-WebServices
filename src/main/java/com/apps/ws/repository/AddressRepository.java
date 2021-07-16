package com.apps.ws.repository;



import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apps.ws.entity.AddressEntity;
import com.apps.ws.entity.UserEntity;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity,Long>
{

	
	List<AddressEntity> findAllByUserDetails(UserEntity user);
	AddressEntity findByAddressId(String addressId);
	
}

