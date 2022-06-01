package com.asi.rest.sale;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.asi.dto.SaleTransactionDto;
import com.asi.rest.IRest;

public interface ISaleRest extends IRest {
	public final String ROOT_PATH = "/api/sales";

	public final String BUY = ROOT_PATH + "/buy";
	public final String SELL = ROOT_PATH + "/sell";

	@RequestMapping(method = RequestMethod.POST, value = BUY)
	public int buyCard(@RequestBody SaleTransactionDto saleDto);

	@RequestMapping(method = RequestMethod.POST, value = SELL)
	public int sellCard(@RequestBody SaleTransactionDto saleDto);

	@GetMapping(ROOT_PATH)
	public List<SaleTransactionDto> get();
}
