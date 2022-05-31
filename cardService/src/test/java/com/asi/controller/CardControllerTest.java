package com.asi.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.asi.model.Card;
import com.asi.service.CardService;

class CardControllerTest {
	
	@InjectMocks
	CardController cardController;
	
	@Mock
	CardService cardService;

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}


}
