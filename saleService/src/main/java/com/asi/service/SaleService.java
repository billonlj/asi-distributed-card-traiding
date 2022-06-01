package com.asi.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asi.model.Sale;
import com.asi.repository.SaleRepository;

import com.asi.dto.CardInstanceDto;
import com.asi.rest.card.CardRestConsumer;
import com.asi.rest.user.UserRestConsumer;

@Service
public class SaleService {
	
	@Autowired
	SaleRepository saleRepository;

	private static final CardRestConsumer cardRestConsumer = new CardRestConsumer();
	private static final UserRestConsumer userRestConsumer = new UserRestConsumer();
	
	private Map<Sale, CardInstanceDto> zipSaleCardDto(List<Sale> sales, List<CardInstanceDto> cards) {
		// On sort avant pour pouvoir matcher l'ordre d'index de la liste et l'ordre des id de cartes
		sales.sort(Comparator.comparing(Sale::getCardInstanceIdSale));
		cards.sort(Comparator.comparing(CardInstanceDto::getIdInstance));
		Map<Sale, CardInstanceDto> saleCardPairList = new HashMap<>();
		for(int i = 0; i < cards.size(); i++) {
			saleCardPairList.put(sales.get(i), cards.get(i));
		}
		return saleCardPairList;
	}
	
	public Map<Sale, CardInstanceDto> getSale(int id) {
		Optional<Sale> sale = saleRepository.findById(id);
		if (sale.isPresent()) {
			CardInstanceDto cardInstanceDto = null;//cardRestConsumer.get(sale.get().getCardInstanceIdSale()).body;
			Map<Sale, CardInstanceDto> saleCardPair = new HashMap<>(Map.of(sale.get(), cardInstanceDto));
			return saleCardPair;
		} else {
			return null;
		}
	}
	
	public Map<Sale, CardInstanceDto> getAllSales() {
		List<Sale> saleList = (List<Sale>) saleRepository.findAll();
		Integer[] cardInstanceIdList = saleList
			.stream()
			.mapToInt(Sale::getCardInstanceIdSale)
			.boxed()
			.toArray( Integer[]::new );
		List<CardInstanceDto> cardInstanceDtoList = cardRestConsumer.getCardInstanceList(cardInstanceIdList).getBody();
		Map<Sale, CardInstanceDto> zippedSaleCardDto = zipSaleCardDto(saleList, cardInstanceDtoList);
		return zippedSaleCardDto;
    }
	
	private void deduct(int userId, double amount){
        //user.setMoneyUser(user.getMoneyUser() - amount);
        //userRepository.save(user);
    }

    private void deposit(int userId, double amount){
    	//user.setMoneyUser(user.getMoneyUser() + amount);
        //userRepository.save(user);
    }	
	
    //TODO catch en dehors pour msg d'erreur ?
	private void buyTransaction(int buyerId, Sale sale) {
		int sellerId = sale.getUserIdSale();
		double price = sale.getPriceSale();
		//money transfer
	    deduct(buyerId, price);
	    deposit(sellerId, price);
	    //update card
	    int card = sale.getCardInstanceIdSale();
	    //card.setUserInstance(buyerId);
	    //cardInstanceRepository.save(card);
	    //delete sale
	    saleRepository.delete(sale);	    	
	}
	
	private void createOfferTransaction(int sellerId, int cardId, double price) {
		//Sale sale = new Sale(sellerId, cardId, price);		
		//saleRepository.save(sale);
		//cardId.setUserInstance(null);
		//cardInstanceRepository.save(cardId);
	}
	
	//TODO remove test sur User et use getCurrentUser() + utiliser des exceptions
	public int buy(int idSale, int idUser) {
		// Optional<BalancerUserDto> buyer = userRepository.findById(idUser);
		// Optional<SaleDto> sale = saleRepository.findById(idSale);
		//
		//if(!buyer.isPresent()) {
		//	return 500;
		//}
		//if(!sale.isPresent()) {
		//	return 500;
		//}
		////check si argent user suffisant
		//if(sale.get().getPriceSale() > buyer.get().getMoneyUser()) {
		//	return 500;
		//}
		//buyTransaction(buyer.get(), sale.get());
		return 200;
		
	}
	
	//TODO remove test sur User et use getCurrentUser() + utiliser des exceptions
	public int sell(int idUser, int idCardInstance, double price) {
		//checkout si user & sale existe
		//Optional<User> seller = userRepository.findById(idUser);
		//Optional<CardInstance> card = cardInstanceRepository.findById(idCardInstance);
		//if(!seller.isPresent()) {
		//	return 500;
		//}
		//if(!card.isPresent()) {
		//	return 500;
		//}
		////check user has the card
		//if(1 == 0) {
		//	return 500;
		//}
		//createOfferTransaction(seller.get(), card.get(), price);
		return 200;
			
	}
}
