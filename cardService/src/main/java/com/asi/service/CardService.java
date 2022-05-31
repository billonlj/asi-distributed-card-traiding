package com.asi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asi.model.Card;
import com.asi.model.CardInstance;
import com.asi.repository.CardInstanceRepository;
import com.asi.repository.CardRepository;

@Service
public class CardService {
	
	@Autowired
	CardRepository cardRepository;
	
	@Autowired
	CardInstanceRepository cardinstanceRepository;
	
	private int numberOfCardThoGenerate = 5;
	
	public Card getCard(int idCard) {
		Optional<Card> c = cardRepository.findById(idCard);
		if (c.isPresent())
			return c.get();
		throw new RuntimeException("Ressource not found");
	}
	
	public List<Card> getAll() {
		List<Card> cards = (ArrayList<Card>) cardRepository.findAll();
		return cards;
	}
	
	private List<Card> getRandomCards(int nbToGenerate) {
		List<Card> cards = new ArrayList<Card>();
		Random r = new Random();
		
		for (int i = 0; i < nbToGenerate; i++) {
			cards.add(this.getCard(r.nextInt((int) cardRepository.count())));
		}
		
		if (cards.size() < nbToGenerate)
			throw new RuntimeException("Pas assez de cartes générées aléatoirement");
		
		return cards;
	}
	
	public List<CardInstance> registerNewUserCards(int idUser) {
		List<Card> cards = this.getRandomCards(this.numberOfCardThoGenerate);
		List<CardInstance> cardsInstances = new ArrayList<CardInstance>();
		for (Card card : cards) {
			CardInstance ci = convertCardToCardInstance(card);
			ci.setIdUser(idUser);
			cardsInstances.add(cardinstanceRepository.save(ci));
		}
		return cardsInstances;
	}
	
	private CardInstance convertCardToCardInstance(Card card) {
		CardInstance ci = new CardInstance(
				card,
				card.getEnergyCard(),
				card.getHpCard(),
				card.getAttackCard(),
				card.getDefenceCard());
		return ci;
	}
	
	
}
