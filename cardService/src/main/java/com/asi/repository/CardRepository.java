package com.asi.repository;

import org.springframework.data.repository.CrudRepository;

import com.asi.model.Card;

public interface CardRepository extends CrudRepository<Card, Integer> {

}
