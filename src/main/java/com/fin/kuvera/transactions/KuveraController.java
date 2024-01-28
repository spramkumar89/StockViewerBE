package com.fin.kuvera.transactions;

import com.fin.kuvera.utils.KuveraTransactions;
import com.fin.zerodha.utils.Coin;
import com.fin.zerodha.utils.MonthlyTrades;
import com.fin.zerodha.utils.TradesTmp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/kuvera/transactions")
public class KuveraController {

	@Autowired
	private KuveraService tradesServiceObj;

	@PostMapping("/save")
	public HashMap<String,String> saveTrades(@RequestParam MultipartFile file) {
		tradesServiceObj.saveTransactions(file);
		return new HashMap<>();
	}
	@GetMapping("/monthlydetails")
	public List<MonthlyTrades> getMonthlyTrades(){
		List<KuveraTransactions> trades = tradesServiceObj.getAllTransactions();
		return null;
	}
}
