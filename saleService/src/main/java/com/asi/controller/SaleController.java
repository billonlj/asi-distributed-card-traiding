package com.asi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.asi.dto.SaleDto;
import com.asi.dto.SaleTransactionDto;
import com.asi.dto.CardInstanceDto;

import com.asi.model.Sale;
import com.asi.rest.sale.ISaleRest;
import com.asi.service.SaleService;

@RestController
public class SaleController implements ISaleRest {
	
	@Autowired
	SaleService saleService;

	@Autowired
	ModelMapper modelMapper;

	private SaleDto convertToSaleDto(Sale sale, CardInstanceDto card) {
		SaleDto saleDto = modelMapper.map(sale, SaleDto.class);
		saleDto.setCardInstance(card);
	    return saleDto;
	}
	
	@Override
	public List<SaleDto> getAll() {
		return saleService.getAllSales()
			.entrySet()
			.stream()
			.map(entry -> convertToSaleDto(entry.getKey(), entry.getValue()))
			.collect(Collectors.toList());
	}

	@Override
	public void sellCard(@RequestBody SaleTransactionDto saleDto) {
		System.out.println("___________________\n");
		System.out.println(saleDto.getIdCard());
		System.out.println("___________________\n");
		saleService.sell(saleDto.getIdUser(), saleDto.getIdCard(), saleDto.getPriceSale());
		System.out.println(saleDto.getIdSale() + " " + saleDto.getIdUser() + " " + saleDto.getPriceSale());
	}
	
	@Override
	public void buyCard(@RequestBody SaleTransactionDto saleDto) {
		System.out.println("___________________\n");
		System.out.println(saleDto.getIdCard());
		System.out.println("___________________\n");
		saleService.buy(saleDto.getIdSale(), saleDto.getIdUser());
		System.out.println(saleDto.getIdSale() + " " + saleDto.getIdUser());
	}

}
