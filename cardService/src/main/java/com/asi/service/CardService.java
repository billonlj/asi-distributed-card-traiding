package com.asi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asi.model.Card;
import com.asi.repository.CardRepository;

@Service
public class CardService {
	
	@Autowired
	CardRepository cardRepository;
	
	public Card getCard(int idCard) {
		Optional<Card> c = cardRepository.findById(idCard);
		if (c.isPresent())
			return c.get();
		return null;
	}
}
