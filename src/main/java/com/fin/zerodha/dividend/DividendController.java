package com.fin.zerodha.dividend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zerodha/dividend")
public class DividendController {
	
	@Autowired
	private DividendService holdingService;

/*
	@GetMapping("/holdings/all")
	public List<String> getHoldings() {
		return holdingService.getHoldings();
	}
	
	@GetMapping("/holdings/{symbol}")
	public Holdings getHoldingDetail(@PathVariable String symbol) {
		return holdingService.getHoldingDetail(symbol);
	}
	
	@GetMapping("/holdings/details/all")
	public List<Holdings> getAllHoldings() {
		return holdingService.getAllHoldings();
	}
*/

}
