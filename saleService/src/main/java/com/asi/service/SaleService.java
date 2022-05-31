package com.asi.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.asi.model.Sale;
import com.asi.repository.SaleRepository;


@Service
public class SaleService {
	
	@Autowired
	SaleRepository saleRepository;

	RestTemplate restTemplate;
	
	//@Autowired
	//UserRepository userRepository;

	//@Autowired
	//CardInstanceRepository cardInstanceRepository;
	
	public Sale getSale(int id) {
        Optional<Sale> s = saleRepository.findById(id);
        if (s.isPresent()) {
			return s.get();
		} else {
			return null;
		}
    }
	
	public List<Sale> getAllSales() {
		List<Sale> salesList = (List<Sale>) saleRepository.findAll();
		return salesList;
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
		//cardInstanceRepository.save(cardId);		<relativePath /> <!-- lookup parent from repository -->
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
