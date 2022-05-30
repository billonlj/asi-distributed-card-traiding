package com.asi.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asi.service.CardService;

public class CardController {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	CardService cardService;
	
	@GetMapping("/")
	public String index() {
		return "Hellow world";
	}
}
