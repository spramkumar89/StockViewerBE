package com.fin.zerodha.trades;

import com.fin.zerodha.utils.MonthlyTrades;
import com.fin.zerodha.utils.TradesTmp;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/zerodha/trades")
public class TradesController {

	@Autowired
	private TradesService tradesServiceObj;

	@PostMapping("/save")
	public HashMap<String,String> saveTrades(@RequestParam MultipartFile file) {
		tradesServiceObj.saveTrades(file);
		return new HashMap<>();
	}
	@GetMapping("/monthlydetails")
	public List<MonthlyTrades> getMonthlyTrades(){
		List<TradesTmp> trades = tradesServiceObj.getAllTrades();
		return null;
	}
}
