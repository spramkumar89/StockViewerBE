package com.fin.kuvera.transactions;

import com.fin.kuvera.utils.KuveraTransactions;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class KuveraService {

	@Autowired
	private KuveraRepository kuveraRepoObj;

	public List<KuveraTransactions> saveTransactions(MultipartFile file){
		List<KuveraTransactions> transactionList = new ArrayList<>();
		try {
			Reader reader = new InputStreamReader(file.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).build();
			List<String[]> transactions = csvReader.readAll();
			System.out.println("CSV Row Size : " + transactions.size());

			KuveraTransactions transaction = null;
			transactions.remove(0);
			for(String[] trade:transactions){
				transaction = new KuveraTransactions();
				transaction.setDate(trade[0]);
				transaction.setFolioNumber(Long.parseLong(trade[1]));
				transaction.setFundName(trade[2]);
				transaction.setTradeType(trade[3]);
				transaction.setUnits(Float.parseFloat(trade[4]));
				transaction.setNav(Float.parseFloat(trade[5]));
				transaction.setCurrentNAV(Float.parseFloat(trade[6]));
				transaction.setAmount(Float.parseFloat(trade[7]));
				transactionList.add(transaction);
			};
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (CsvException e) {
			throw new RuntimeException(e);
		}

		System.out.println("Transaction List Size : " + transactionList.size());
		return kuveraRepoObj.saveAllAndFlush(transactionList);
	}

	public List<KuveraTransactions> getAllTransactions(){
		List<KuveraTransactions> transactions = kuveraRepoObj.findAll();
		System.out.println("Transactions Size : " + transactions.size());
		HashMap<String, Float> buyMap = new HashMap<>();
		HashMap<String, Float> sellMap = new HashMap<>();
		for(KuveraTransactions transaction : transactions) {
			System.out.println("Transaction Date : " + transaction.getDate());
			String strDate = transaction.getDate().trim().substring(0,7);
			System.out.println("Transaction Date : " + strDate);
			String type = transaction.getTradeType();

			if(type.equals("buy")){
				if(buyMap.containsKey(strDate)){
					Float tmpPrice = buyMap.get(strDate);
					buyMap.put(strDate,tmpPrice + transaction.getAmount());
				} else {
					buyMap.put(strDate,transaction.getAmount());
				}
			} else {
				if(sellMap.containsKey(strDate)){
					Float tmpPrice = sellMap.get(strDate);
					sellMap.put(strDate,tmpPrice + transaction.getAmount());
				} else {
					sellMap.put(strDate,transaction.getAmount());
				}
			}
		}
		System.out.println("Buy Transactions : " + buyMap);
		System.out.println("Sell Transactions : " + sellMap);

		return kuveraRepoObj.findAll();
	}
}
