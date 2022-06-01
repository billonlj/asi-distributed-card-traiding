package com.asi.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.asi.dto.CardDto;
import com.asi.dto.CardInstanceDto;
import com.asi.dto.FamilyDto;
import com.asi.model.Card;
import com.asi.model.CardInstance;
import com.asi.model.Family;
import com.asi.rest.card.ICardRest;
import com.asi.service.CardService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class CardController implements ICardRest {

	private final static Logger LOG = LoggerFactory.getLogger(CardController.class);
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	CardService cardService;
	

	// Renvoie un template d'une carte
	public ResponseEntity<CardDto> get(@PathVariable int id) {
		Card c = cardService.getCard(id);
		if (c == null)
			return ResponseEntity.noContent().build();
		return new ResponseEntity<CardDto>(convertToCardDto(c), HttpStatus.OK);
	}
	
	
	// Renvoie toutes les cartes existantes dans le jeu
	@Override
	public List<CardDto> getAll() {
		List<Card> cards = cardService.getAll();
		return cards.stream()
				.map(this::convertToCardDto)
				.collect(Collectors.toList());
	}
	
	// Retourne une CardDto à partir d'un id de CardInstance
	@Override
	public ResponseEntity<List<CardInstanceDto>> getCardInstanceList(@PathVariable Integer[] ids) {
		System.out.println(ids);
		List<CardInstance> cards = cardService.getAllInstanceByIds(ids);
		if (cards.isEmpty())
			return ResponseEntity.internalServerError().build();
		
		List<CardInstanceDto> cardInstanceDto = cards.stream()
				.map(this::convertToCardInstanceDto)
				.collect(Collectors.toList());
		
		for (CardInstanceDto card : cardInstanceDto) {
			card.setCard(convertToCardDto(cardService.getCard(card.getCardIdInstance())));
		}
		
		return new ResponseEntity<List<CardInstanceDto>>(cardInstanceDto , HttpStatus.OK);
	}
	
	
  	@Override
	public void add(CardInstanceDto cardInstanceDto) {
		CardInstance cardInstance = convertToCardInstanceModel(cardInstanceDto);
	}

	//
	@PutMapping("/api/cards/users/")
	public ResponseEntity<CardInstanceDto> buyCard(CardInstanceDto cardInstanceDto) {
		CardInstance cardInstance = convertToCardInstanceModel(cardInstanceDto);
		
		CardInstance boughtCard = cardService.buyCard(cardInstance);
		if (boughtCard == null)
			return ResponseEntity.internalServerError().build();
		
		return new ResponseEntity<CardInstanceDto>(convertToCardInstanceDto(boughtCard), HttpStatus.OK);
	}
	
  	// Génère 5 cartes aléatoires pour un utilisateur qui s'inscrit
	@Override
	public ResponseEntity<List<CardInstanceDto>> generateCardsForNewUser(@PathVariable int idUser) {
		LOG.info("[CardController] generateCardsForNewUser");
		// On ignore les propriétés sources qui peuvent matcher plusieurs propriétés des champs du DTO
		List<CardInstance> ci = cardService.registerNewUserCards(idUser);
		
		// Si la liste est vide on renvoie un code 500
		if (ci.isEmpty())
			return ResponseEntity.internalServerError().build();
		
		List<CardInstanceDto> cardInstanceDto = ci.stream()
				.map(this::convertToCardInstanceDto)
				.collect(Collectors.toList());
			
		return ResponseEntity.ok(cardInstanceDto);
	}
	
	// Retourne toutes les cartes d'un utilisateur
	@GetMapping("/api/cards/users/{idUser}")
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
		cardDto.setFamilyCardDto(convertToFamilyDto(card.getFamilyCard()));
		return cardDto;
	}
	
	// Convertie une CardInstance en CardInstanceDto
	private CardInstanceDto convertToCardInstanceDto(CardInstance cardInstance) {
		CardInstanceDto cardInstanceDto = modelMapper.map(cardInstance, CardInstanceDto.class);
		cardInstanceDto.setCardIdInstance(cardInstance.getCardInstance().getIdCard());
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
		cardInstance.setCardInstance(cardService.getCard(cardInstanceDto.getCardIdInstance()));
		return cardInstance;
	}
	
	// Convertie une Family en une FamilyDto
	private FamilyDto convertToFamilyDto(Family family) {
		FamilyDto familyDto = modelMapper.map(family, FamilyDto.class);
		return familyDto;
	}

	@PostConstruct
    private void postConstruct() {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }
}
