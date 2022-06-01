package com.asi.rest.sale;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.asi.dto.SaleTransactionDto;

public class SaleRestConsumer implements ISaleRest {
    
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public int buyCard(SaleTransactionDto saleDto) {
		restTemplate.postForEntity(BASE_PATH + BUY, saleDto, null);
		return 0;
	}

	@Override
	public int sellCard(SaleTransactionDto saleDto) {
		restTemplate.postForEntity(BASE_PATH + SELL, saleDto, null);
		return 0;
	}

	@Override
	public List<SaleTransactionDto> get() {
		// TODO Auto-generated method stub
		return null;
	}
}
