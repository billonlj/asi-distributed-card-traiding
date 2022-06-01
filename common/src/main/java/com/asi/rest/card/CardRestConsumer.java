package com.asi.rest.card;

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
        return restTemplate.getForEntity(GET, CardDto.class, id);
    }

    @Override
    public List<CardDto> getAll() {
        return (List<CardDto>) restTemplate.getForEntity(GET_ALL, CardDto[].class);
    }

    @Override
    public void add(CardInstanceDto cardInstanceDto) {
        restTemplate.postForEntity(ADD, cardInstanceDto, null);
    }

    @Override
    public ResponseEntity<CardInstanceDto[]> generateCardsForNewUser(int idUser) {

        LOG.info("[generateCardsForNewUser] idUser: " + idUser);
        try {
            Map<String, Integer> map = new HashMap();
            map.put("idUser", idUser);
            ResponseEntity<CardInstanceDto[]> data =  restTemplate.postForEntity("http://localhost" + REGISTER, null, CardInstanceDto[].class, map);
            LOG.info("[generateCardsForNewUser] Status: " + data.getStatusCode());
            return data;
        } catch (Exception e) {
            //TODO: handle exception
            LOG.error("[generateCardsForNewUser]", e);
            return null;
        }
    }
    
}
