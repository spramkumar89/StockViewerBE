package com.fin.icicidirect.trades;

import com.fin.icicidirect.utils.MonthlyTrades;
import com.fin.icicidirect.utils.ICICITradesTmp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/icicidirect/trades")
public class ICICITradesController {

	@Autowired
	private ICICITradesService tradesServiceObj;

	@PostMapping("/save")
	public HashMap<String,String> saveTrades(@RequestParam MultipartFile file) {
		tradesServiceObj.saveTrades(file);
		return new HashMap<>();
	}
	@GetMapping("/monthlydetails")
	public List<MonthlyTrades> getMonthlyTrades(){
		List<ICICITradesTmp> trades = tradesServiceObj.getAllTrades();
		return null;
	}
}
