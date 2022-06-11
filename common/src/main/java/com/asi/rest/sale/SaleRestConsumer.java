package com.asi.rest.sale;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import com.asi.rest.sale.ISaleRest;
import com.asi.dto.SaleDto;
import com.asi.dto.SaleTransactionDto;

public class SaleRestConsumer implements ISaleRest {
    
	private final static Logger LOG = LoggerFactory.getLogger(SaleRestConsumer.class);

	private RestTemplate restTemplate = new RestTemplate();
	
	@Override
	public List<SaleDto> getAll() {
		return (List<SaleDto>) restTemplate.getForEntity(GET_ALL, SaleDto[].class);
	}

	@Override
	public void buyCard(SaleTransactionDto saleDto) {
		restTemplate.postForEntity(BUY, saleDto, null);		
	}

	@Override
	public void sellCard(SaleTransactionDto saleDto) {
		restTemplate.postForEntity(SELL, saleDto, null);
	}

}
