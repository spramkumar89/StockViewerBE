package com.fin.portfolio.trades;

import com.fin.portfolio.utils.TradesHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/trades")
public class TradesController {

	@Autowired
	private TradesService tradesServiceObj;

	@PostMapping("/save")
	public HashMap<String,String> saveTrades(@RequestParam MultipartFile file) {
		tradesServiceObj.saveTrades(file);
		return new HashMap<>();
	}
	@GetMapping("/monthlydetails")
	public HashMap<String, Object> getMonthlyWiseTrades(){
		HashMap<String, Object> monthlyWiseTrades = tradesServiceObj.getMonthlyWiseTrades();
		return monthlyWiseTrades;
	}

	@GetMapping("/stock/{name}")
	public List<TradesHistory> getStockWiseTrades(@PathVariable String name){
		List<TradesHistory> stockWiseTrades = tradesServiceObj.getStockWiseTrades(name);
		return stockWiseTrades;
	}
}
