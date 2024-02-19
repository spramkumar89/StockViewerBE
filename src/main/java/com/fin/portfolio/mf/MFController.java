package com.fin.portfolio.mf;

import com.fin.portfolio.utils.MutualFundTransactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping("/mf")
public class MFController {

	@Autowired
	private MFService mfServiceObj;

	@PostMapping("/save")
	public HashMap<String,String> saveTrades(@RequestParam MultipartFile file) {
		mfServiceObj.saveTrades(file);
		return new HashMap<>();
	}
	@GetMapping("/monthlydetails")
	public HashMap<String, Object> getMonthWiseTransactions(){
		HashMap<String, Object> monthWiseTransactions = mfServiceObj.getMonthWiseTransactions();
		return monthWiseTransactions;
	}
	@GetMapping("/list")
	public TreeMap<String, Float> getMFPortfolio(){
		TreeMap<String, Float> mfPortfolio = mfServiceObj.getMFPortfolio();
		return mfPortfolio;
	}

	@GetMapping("/fund")
	public List<MutualFundTransactions> getFundDetails(@RequestParam String fundName){
		List<MutualFundTransactions> fundDetails = mfServiceObj.getFundDetails(fundName);
		return fundDetails;
	}
}
