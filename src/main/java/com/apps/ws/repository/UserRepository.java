package com.apps.ws.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apps.ws.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	//these methods are also called querymethods
	//you must use - find keyword - By - yourfiledname (method argument)
	UserEntity findByEmail(String email);
	//it is a public user is so it is String not long
	UserEntity findByUserId(String userId); 
	
	
//	UserEntity findUserByEmail(String email);
	
	
}
