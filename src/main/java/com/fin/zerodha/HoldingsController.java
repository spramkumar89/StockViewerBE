package com.fin.zerodha;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fin.zerodha.utils.Holdings;

@RestController
@RequestMapping("/zerodha")
public class HoldingsController {
	
	@Autowired
	private HoldingsService holdingService;

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

}
