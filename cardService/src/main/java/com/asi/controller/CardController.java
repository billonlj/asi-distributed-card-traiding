package com.asi.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asi.model.Card;
import com.asi.service.CardService;

@RestController
public class CardController {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	CardService cardService;
	
	@GetMapping("/")
	public String index() {
		System.out.println("Hello world");
		return "Hellow world";
	}
	
	@GetMapping("/cards/{id}")
	public Card get(@PathVariable int id) {
		Card c = cardService.getCard(id);
		if (c == null)
			throw new RuntimeException("Ressource not found");
		return c;
	}
	@GetMapping("/cards/")
	public List<Card> getAll() {
		List<Card>
	}
}
