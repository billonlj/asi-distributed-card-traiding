package com.asi.rest.card;

import java.util.List;

import com.asi.dto.CardDto;
import com.asi.dto.CardInstanceDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ICardRest {
    public final String ROOT_PATH = "/api/cards";

	public final String GET = ROOT_PATH + "/template/{id}";
	public final String GET_ALL = ROOT_PATH + "/template/";
	
	public final String GET_CARD_FROM_INSTANCE = ROOT_PATH + "/{ids}";
	
	public final String ADD = ROOT_PATH + "/users/";
	public final String REGISTER = ROOT_PATH + "/users/register/{idUser}";
	


    @GetMapping(GET)
	public ResponseEntity<CardDto> get(@PathVariable int id);

    @GetMapping(GET_ALL)
	public List<CardDto> getAll();
    
    @GetMapping(GET_CARD_FROM_INSTANCE)
    public ResponseEntity<CardInstanceDto[]> getCardInstanceList(@PathVariable Integer[] ids);

    @PostMapping(ADD)
	public void add(CardInstanceDto cardInstanceDto);

    @PostMapping(REGISTER)
	public ResponseEntity<CardInstanceDto[]> generateCardsForNewUser(@PathVariable int idUser);
}
