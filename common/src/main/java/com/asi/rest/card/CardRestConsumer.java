package com.asi.rest.card;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asi.dto.CardDto;
import com.asi.dto.CardInstanceDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CardRestConsumer implements ICardRest{

    private final static Logger LOG = LoggerFactory.getLogger(CardRestConsumer.class);

	private RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseEntity<CardDto> get(int id) {
        return restTemplate.getForEntity(BASE_PATH + GET, CardDto.class, id);
    }

    @Override
    public List<CardDto> getAll() {
        CardDto[] response = restTemplate.getForEntity(BASE_PATH + GET_ALL, CardDto[].class).getBody();
        return Arrays.asList(response);
    }

    @Override
    public void add(CardInstanceDto cardInstanceDto) {
        restTemplate.postForEntity(BASE_PATH + ADD, cardInstanceDto, null);
    }

    @Override
    public ResponseEntity<List<CardInstanceDto>> generateCardsForNewUser(int idUser) {
        try {
            Map<String, Integer> map = new HashMap();
            map.put("idUser", idUser);

            ResponseEntity<CardInstanceDto[]> data =  restTemplate.postForEntity(BASE_PATH + REGISTER, null, CardInstanceDto[].class, map);
            LOG.info("[generateCardsForNewUser] ", data.toString());

            return ResponseEntity.ok(Arrays.asList(data.getBody()));
        } catch (Exception e) {
            LOG.error(e.toString());
            return null;
        }
    }
    
}
