package com.asi.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asi.dto.CardDto;
import com.asi.dto.CardInstanceDto;
import com.asi.model.Card;
import com.asi.model.CardInstance;
import com.asi.service.CardService;

@RestController
@RequestMapping(path = "/api/cards")
public class CardController {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	CardService cardService;
	
	// Renvoie un template d'une carte
	@GetMapping("/{id}")
	public ResponseEntity<CardDto> get(@PathVariable int id) {
		Card c = cardService.getCard(id);
		if (c == null)
			return ResponseEntity.noContent().build();
		return new ResponseEntity<CardDto>(convertToCardDto(c), HttpStatus.OK);
	}
	
	// Renvoie toutes les cartes existantes dans le jeu
	@GetMapping("/")
	public List<CardDto> getAll() {
		List<Card> cards = cardService.getAll();
		return cards.stream()
				.map(this::convertToCardDto)
				.collect(Collectors.toList());
	}
	
	// TODO : ajout d'une carte à un utilisateur
	@PostMapping("/users/")
	public void add(CardInstanceDto cardInstanceDto) {
		CardInstance cardInstance = convertToCardInstanceModel(cardInstanceDto);
	}

	//
	@PutMapping("/users/")
	public ResponseEntity<CardInstanceDto> buyCard(CardInstanceDto cardInstanceDto) {
		CardInstance cardInstance = convertToCardInstanceModel(cardInstanceDto);
		
		CardInstance boughtCard = cardService.buyCard(cardInstance);
		if (boughtCard == null)
			return ResponseEntity.internalServerError().build();
		
		return new ResponseEntity<CardInstanceDto>(convertToCardInstanceDto(boughtCard), HttpStatus.OK);
	}
	
	// Génère 5 cartes aléatoires pour un utilisateur qui s'inscrit
	@PostMapping("/users/register/{idUser}")
	public ResponseEntity<List<CardInstanceDto>> generateCardsForNewUser(@PathVariable int idUser) {
		// On ignore les propriétés sources qui peuvent matcher plusieurs propriétés des champs du DTO
		List<CardInstance> ci = cardService.registerNewUserCards(idUser);
		
		// Si la liste est vide on renvoie un code 500
		if (ci.isEmpty())
			return ResponseEntity.internalServerError().build();
		
		List<CardInstanceDto> cardInstanceDto = ci.stream()
				.map(this::convertToCardInstanceDto)
				.collect(Collectors.toList());
		
		// Si la liste n'est pas vide on renvoie un code 200 et la liste des cartes insérées
		return new ResponseEntity<List<CardInstanceDto>>(cardInstanceDto, HttpStatus.OK);
	}
	
	// Retourne toutes les cartes d'un utilisateur
	@GetMapping("/users/{idUser}")
	public ResponseEntity<List<CardInstanceDto>> getAllCardForOneUser(@PathVariable int idUser) {
		// On ignore les propriétés sources qui peuvent matcher plusieurs propriétés des champs du DTO
		List<CardInstance> cardInstances = cardService.getCardsByUser(idUser);
		
		if (cardInstances.isEmpty())
			return ResponseEntity.noContent().build();
		
		List<CardInstanceDto> cardInstancesDto = cardInstances.stream()
				.map(this::convertToCardInstanceDto)
				.collect(Collectors.toList());
		
		return new ResponseEntity<List<CardInstanceDto>>(cardInstancesDto, HttpStatus.OK);
	}
	
	
	// Convertie une Card en une CardDto
	private CardDto convertToCardDto(Card card) {
		CardDto cardDto = modelMapper.map(card, CardDto.class);
		cardDto.setFamilyCard(card.getFamilyCard().getNameFamily());
		return cardDto;
	}
	
	// Convertie une CardInstance en CardInstanceDto
	private CardInstanceDto convertToCardInstanceDto(CardInstance cardInstance) {
		CardInstanceDto cardInstanceDto = modelMapper.map(cardInstance, CardInstanceDto.class);
		cardInstanceDto.setCardInstance(cardInstance.getCardInstance().getIdCard());
		return cardInstanceDto;
	}
	
	// Convertie une CardDto en une Card
	private Card convertToCardModel(CardDto cardDto) {
		Card card = modelMapper.map(cardDto, Card.class);
		return card;
	}
	
	// Convertie une CardInstanceDto en une CardInstance
	private CardInstance convertToCardInstanceModel(CardInstanceDto cardInstanceDto) {
		CardInstance cardInstance = modelMapper.map(cardInstanceDto, CardInstance.class);
		cardInstance.setCardInstance(cardService.getCard(cardInstanceDto.getCardInstance()));
		return cardInstance;
	}

	@PostConstruct
    private void postConstruct() {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }
}
