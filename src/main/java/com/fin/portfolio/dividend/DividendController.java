package com.fin.portfolio.dividend;

import com.fin.portfolio.utils.DividendTransactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping("/dividend")
public class DividendController {

	@Autowired
	private DividendService dividendServiceObj;

	@PostMapping("/save")
	public HashMap<String,String> saveTrades(@RequestParam MultipartFile file) {
		dividendServiceObj.saveTrades(file);
		return new HashMap<>();
	}
	@GetMapping("/list")
	public TreeMap<String, Float> getDividendList(){
		TreeMap<String, Float> monthlyWiseTrades = dividendServiceObj.getDividendList();
		return monthlyWiseTrades;
	}

	@GetMapping("/stock/{name}")
	public List<DividendTransactions> getStockDividend(@PathVariable String name){
		List<DividendTransactions> monthlyWiseTrades = dividendServiceObj.getStockDividend(name);
		return monthlyWiseTrades;
	}
}
