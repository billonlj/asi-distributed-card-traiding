package com.asi.repository;

import org.springframework.data.repository.CrudRepository;

import com.asi.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>{
	
	public Optional<User> findByEmailUser(String emailUser);
	
}