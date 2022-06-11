package com.asi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asi.model.Card;
import com.asi.model.CardInstance;
import com.asi.repository.CardInstanceRepository;
import com.asi.repository.CardRepository;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

@Service
public class CardService {
	private final static Logger LOG = LoggerFactory.getLogger(CardService.class);
	
	@Autowired
	CardRepository cardRepository;
	
	@Autowired
	CardInstanceRepository cardInstanceRepository;
	
	private int numberOfCardThoGenerate = 5;
	
	public Card getCard(int idCard) {
		LOG.info("[getCard] idCard: " + idCard);

		Optional<Card> c = cardRepository.findById(idCard);
		if (c.isPresent())
			return c.get();
		throw new RuntimeException("Ressource not found");
	}
	
	public List<CardInstance> getCardsByUser(int idUser) {
		Optional<List<CardInstance>> oCards = cardInstanceRepository.findByIdUser(idUser);
		if (oCards.isPresent())
			return oCards.get();
		return new ArrayList<CardInstance>();
	}
	
	public List<Card> getAll() {
		List<Card> cards = (ArrayList<Card>) cardRepository.findAll();
		return cards;
	}
	
	public List<CardInstance> getAllInstanceByIds(Integer[] ids) {
		List<CardInstance> cards = new ArrayList<CardInstance>(); 
		cardInstanceRepository.findAllById(Arrays.asList(ids)).forEach(cards::add);
		
		return cards;
	}
	
	private List<Card> getRandomCards(int nbToGenerate) {
		List<Card> allCards = this.getAll();
		List<Card> cards = new ArrayList<Card>();
		Random r = new Random();
		
		for (int i = 0; i < nbToGenerate; i++) {
			cards.add(allCards.get(r.nextInt(allCards.size())));
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
			cardsInstances.add(cardInstanceRepository.save(ci));
		}
		return cardsInstances;
	}

	public Boolean buyCard(Integer idCardInstance, Integer idUser) {
		try {
			CardInstance card = cardInstanceRepository.findById(idCardInstance).get();
			card.setIdUser(idUser);
			cardInstanceRepository.save(card);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public Boolean sellCard(Integer idCardInstance) {
		try {
			CardInstance card = cardInstanceRepository.findById(idCardInstance).get();
			card.setIdUser(-1);
			cardInstanceRepository.save(card);
			return true;
		} catch (Exception e) {
			return false;
		}
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
