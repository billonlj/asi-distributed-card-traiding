package com.asi.rest.card;

import java.util.List;

import com.asi.dto.CardDto;
import com.asi.dto.CardInstanceDto;
import com.asi.rest.IRest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface ICardRest extends IRest {
    public final String ROOT_PATH = "/api/cards";

	public final String GET = ROOT_PATH + "/{id}";
	public final String GET_ALL = ROOT_PATH + "/";
	public final String ADD = ROOT_PATH + "/users/";
	public final String REGISTER = ROOT_PATH + "/users/register/{idUser}";


    @GetMapping(GET)
	public ResponseEntity<CardDto> get(@PathVariable int id);

    @GetMapping(GET_ALL)
	public List<CardDto> getAll();

    @PostMapping(ADD)
	public void add(CardInstanceDto cardInstanceDto);

    @PostMapping(REGISTER)
	public ResponseEntity<List<CardInstanceDto>> generateCardsForNewUser(@PathVariable int idUser);
}
