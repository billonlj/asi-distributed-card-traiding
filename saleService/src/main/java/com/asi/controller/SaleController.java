package com.asi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// import com.asi.dto.SaleDto;
import com.asi.dto.SaleTransactionDto;
import com.asi.model.Sale;
import com.asi.rest.sale.ISaleRest;
import com.asi.service.SaleService;

@RestController
public class SaleController implements ISaleRest {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	SaleService saleService;

	private SaleTransactionDto convertToTransationDto(Sale sale) {
		SaleTransactionDto saleDto = modelMapper.map(sale, SaleTransactionDto.class);
		saleDto.setIdCard(sale.getCardInstanceIdSale());
	    return saleDto;
	}
	
	//TODO enlever user le param user dans les routes quand fct getcurrentUser dispo
	// error code html
	
	@Override
	public int sellCard(@RequestBody SaleTransactionDto saleDto) {
		System.out.println(saleDto.getIdSale() + " " + saleDto.getIdUser() + " " + saleDto.getPriceSale());
		int code = saleService.sell(saleDto.getIdUser(), saleDto.getIdCard(), saleDto.getPriceSale());
		return code;
	}
	
	@Override
	public int buyCard(@RequestBody SaleTransactionDto saleDto) {
		int code = saleService.buy(saleDto.getIdSale(), saleDto.getIdUser());
		System.out.println(saleDto.getIdSale() + " " + saleDto.getIdUser());
		return code;
	}
	
	@Override
	public List<SaleTransactionDto> get() {
		return saleService.getAllSales()
				.stream()
				.map(s -> convertToTransationDto(s))
				.collect(Collectors.toList());
	}
}
