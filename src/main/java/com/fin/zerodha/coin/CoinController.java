package com.fin.zerodha.coin;

import com.fin.zerodha.utils.Coin;
import com.fin.zerodha.utils.MonthlyTrades;
import com.fin.zerodha.utils.TradesTmp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/zerodha/coin")
public class CoinController {

	@Autowired
	private CoinService coinServiceObj;

	@PostMapping("/save")
	public HashMap<String,String> saveTrades(@RequestParam MultipartFile file) {
		coinServiceObj.saveTransactions(file);
		return new HashMap<>();
	}
	@GetMapping("/monthlydetails")
	public List<MonthlyTrades> getMonthlyTrades(){
		List<Coin> trades = coinServiceObj.getAllTransactions();
		return null;
	}
}
