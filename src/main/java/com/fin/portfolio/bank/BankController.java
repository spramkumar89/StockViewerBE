package com.fin.portfolio.bank;

import com.fin.portfolio.utils.BankTransactions;
import com.fin.portfolio.utils.DividendTransactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping("/bank")
public class BankController {

	@Autowired
	private BankService bankServiceObj;

	@PostMapping("/save")
	public HashMap<String,String> saveTransactions(@RequestParam MultipartFile file) {
		bankServiceObj.saveTransactions(file);
		return new HashMap<>();
	}
	@GetMapping("/monthwise")
	public TreeMap<String, Object> getMonthWiseTransactions(){
		TreeMap<String, Object> monthWiseTransactions = bankServiceObj.getMonthWiseTransactions();
		return monthWiseTransactions;
	}

	@GetMapping("/name")
	public List<BankTransactions> getBankTransactions(@RequestParam String name){
		List<BankTransactions> bankTransactions = bankServiceObj.getBankTransactions(name);
		return bankTransactions;
	}
}
