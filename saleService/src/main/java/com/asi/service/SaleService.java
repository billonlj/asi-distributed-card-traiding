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
import com.asi.dto.user.BalanceUserDto;
import com.asi.dto.user.UserDto;
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
	
	public boolean buy(int idSale, int idUser) {
		
		Optional<Sale> saleOpt = saleRepository.findById(idSale);
		if(!saleOpt.isPresent()) {
			return false;
		}
		Sale sale = saleOpt.get();

		BalanceUserDto buyer = new BalanceUserDto();
		buyer.setBalanceMoney(-sale.getPriceSale());
		buyer.setIdUser(idUser);

		BalanceUserDto seller = new BalanceUserDto();
		seller.setBalanceMoney(sale.getPriceSale());
		seller.setIdUser(sale.getUserIdSale());

		try {
			System.out.println("Service_______________________________\n");
			System.out.println(buyer.getIdUser() + buyer.getBalanceMoney() + buyer.toString());
			System.out.println("Service_______________________________\n");

			Boolean buyHappened = userRestConsumer.balanceUserMoney(buyer).getBody();
			Boolean sellHappened = userRestConsumer.balanceUserMoney(seller).getBody();
			
			Boolean res = cardRestConsumer.buyCard(Integer.valueOf(sale.getCardInstanceIdSale()), Integer.valueOf(buyer.getIdUser())).getBody();
			saleRepository.delete(sale);
			return buyHappened && sellHappened && res;
		} catch (Exception e) {
			System.out.println("_______________________________\n");
			e.printStackTrace();
			System.out.println("_______________________________\n");
			return false;
		}
		
	}
	
	//TODO remove test sur User et use getCurrentUser() + utiliser des exceptions
	public boolean sell(int idUser, int idCardInstance, double price) {
		Boolean res = cardRestConsumer.sellCard(Integer.valueOf(idCardInstance)).getBody();
		Sale s = new Sale();
		s.setCardInstanceIdSale(idCardInstance);
		s.setUserIdSale(idUser);
		s.setPriceSale(price);
		saleRepository.save(s);
		return res;
			
	}
}
