package com.asi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.asi.model.CardInstance;

public interface CardInstanceRepository extends CrudRepository<CardInstance, Integer>{
	
	Optional<List<CardInstance>> findByIdUser(int idUser);

}
