package com.fin.portfolio.mf;

import com.fin.portfolio.utils.MutualFundTransactions;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@Service
public class MFService {

	@Autowired
	private MFRepository mfRepoObj;

	public List<MutualFundTransactions> saveTrades(MultipartFile file){
		List<MutualFundTransactions> mfTmpList = new ArrayList<>();
		try {
			Reader reader = new InputStreamReader(file.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).build();
			List<String[]> transactions = csvReader.readAll();
			System.out.println("CSV Row Size : " + transactions.size());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			MutualFundTransactions mfTmp = null;
			transactions.remove(0);
			for(String[] transaction : transactions){
				mfTmp = new MutualFundTransactions();
				mfTmp.setMfRefId(transaction[0]);
				mfTmp.setFundName(transaction[1]);
				mfTmp.setDate(sdf.parse(transaction[2]));
				mfTmp.setType(transaction[3]);
				mfTmp.setUnits(Float.parseFloat(transaction[4]));
				mfTmp.setNav(Float.parseFloat(transaction[5]));
				mfTmpList.add(mfTmp);
			};
			mfRepoObj.saveAllAndFlush(mfTmpList);
			System.out.println("Transactions Saved");
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (CsvException e) {
			throw new RuntimeException(e);
		} catch (ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Transaction List Size : " + mfTmpList.size());
		return mfTmpList;
	}

	public HashMap<String, Object> getMonthWiseTransactions(){
		List<MutualFundTransactions> transactions = mfRepoObj.findAll();
		System.out.println("Transactions Size : " + transactions.size());
		HashMap<String, Object> monthlyData = new HashMap<>();
		TreeMap<String, Float> buyMap = new TreeMap<String, Float>();
		TreeMap<String, Float> sellMap = new TreeMap<String, Float>();

		for(MutualFundTransactions transaction : transactions) {
			String strDate = transaction.getDate().toString().substring(0,7);
			String type = transaction.getType();
			Float price = transaction.getUnits() * transaction.getNav();

			if(type.equalsIgnoreCase("buy")){
				if(buyMap.containsKey(strDate)){
					Float tmpPrice = buyMap.get(strDate);
					buyMap.put(strDate,tmpPrice + price);
				} else {
					buyMap.put(strDate,price);
				}
			} else {
				if(sellMap.containsKey(strDate)){
					Float tmpPrice = sellMap.get(strDate);
					sellMap.put(strDate,tmpPrice + price);
				} else {
					sellMap.put(strDate,price);
				}
			}
		}
		System.out.println("Buy Transactions : " + buyMap);
		System.out.println("Sell Transactions : " + sellMap);

		monthlyData.put("buy",buyMap);
		monthlyData.put("sell",sellMap);
		return monthlyData;
	}

	public TreeMap<String, Float> getMFPortfolio(){
		List<MutualFundTransactions> transactions = mfRepoObj.findAll();
		System.out.println("Transactions Size : " + transactions.size());
		TreeMap<String, Float> fundMap = new TreeMap<String, Float>();
		TreeMap<String, Float> sellMap = new TreeMap<String, Float>();

		for(MutualFundTransactions transaction : transactions) {
			String fundName = transaction.getFundName();
			Float price = transaction.getUnits() * transaction.getNav();

			if(fundMap.containsKey(fundName)){
				Float tmpPrice = fundMap.get(fundName);
				fundMap.put(fundName,tmpPrice + price);
			} else {
				fundMap.put(fundName,price);
			}
		}
		System.out.println("Fund Details : " + fundMap);
		return fundMap;
	}

	public List<MutualFundTransactions> getFundDetails(String name){
		List<MutualFundTransactions> transactions = mfRepoObj.findByFundName(name);
		System.out.println("Transactions Size : " + transactions.size());
		System.out.println("Transactions : " + transactions);
		return transactions;
	}
}
