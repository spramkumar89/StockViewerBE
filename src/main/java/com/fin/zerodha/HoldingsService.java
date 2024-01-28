package com.fin.zerodha;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fin.zerodha.utils.Holdings;

@Service
public class HoldingsService {

	@Autowired
	private HoldingsRepository holdingObj;
	
	public List<String> getHoldings() {
		System.out.println("Holdings Service");
		return holdingObj.getHoldingNames();
	}
	
	public Holdings getHoldingDetail(String symbol) {
		return holdingObj.findBySymbol(symbol);
	}
	
	public List<Holdings> getAllHoldings() {
		return holdingObj.findAll();
	}

}
