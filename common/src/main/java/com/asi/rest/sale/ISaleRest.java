package com.asi.rest.sale;

import java.util.List;

import com.asi.dto.SaleDto;
import com.asi.dto.SaleTransactionDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.asi.dto.SaleTransactionDto;
import com.asi.rest.IRest;

public interface ISaleRest extends IRest {
	public final String ROOT_PATH = "/api/sales";

	public final String GET = ROOT_PATH + "/{id}";
	public final String GET_ALL = ROOT_PATH + "/";

	public final String BUY = ROOT_PATH + "/buy";
	public final String SELL = ROOT_PATH + "/sell";

	@GetMapping(GET_ALL)
	public List<SaleDto> getAll();

	@PostMapping(BUY)
	public void buyCard(@RequestBody SaleTransactionDto saleDto);

	@PostMapping(SELL)
	public void sellCard(@RequestBody SaleTransactionDto saleDto);

}
