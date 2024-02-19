package com.fin.portfolio.bank;

import com.fin.portfolio.utils.BankTransactions;
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
import java.util.List;
import java.util.TreeMap;

@Service
public class BankService {

	@Autowired
	private BankRepository bankRepositoryObj;

	public List<BankTransactions> saveTransactions(MultipartFile file){
		List<BankTransactions> bankTransactionsList = new ArrayList<>();
		try {
			Reader reader = new InputStreamReader(file.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).build();
			List<String[]> transactions = csvReader.readAll();
			System.out.println("CSV Row Size : " + transactions.size());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			BankTransactions bankTransactions = null;
			transactions.remove(0);
			for(String[] transaction : transactions){
				System.out.println("Debit : " + transaction[5] + " , Credit : " + transaction[6] + " , Balance : " + transaction[7] + " , Date : " + transaction[2]);

				bankTransactions = new BankTransactions();
				bankTransactions.setTxnRefId(transaction[0]);
				bankTransactions.setBank(transaction[1]);
				bankTransactions.setDate(sdf.parse(transaction[2]));
				bankTransactions.setDescription(transaction[3]);
				bankTransactions.setRef_no(transaction[4]);
				if(transaction[5].trim().isEmpty()){
					transaction[5] = "0";
				}
				bankTransactions.setDebit(Float.parseFloat(transaction[5]));
				if(transaction[6].trim().isEmpty()){
					transaction[6] = "0";
				}
				bankTransactions.setCredit(Float.parseFloat(transaction[6]));
				bankTransactions.setBalance(Float.parseFloat(transaction[7]));
				bankTransactionsList.add(bankTransactions);
			};
			bankRepositoryObj.saveAllAndFlush(bankTransactionsList);
			System.out.println("Trade Records Saved");
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (CsvException e) {
			throw new RuntimeException(e);
		} catch (ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Trade List Size : " + bankTransactionsList.size());
		return bankTransactionsList;
	}

	public TreeMap<String, Object> getMonthWiseTransactions(){
		List<BankTransactions> bankTransactions = bankRepositoryObj.findAll();
		System.out.println("Dividend Size : " + bankTransactions.size());
		TreeMap<String, Object> bankTransactionMap = new TreeMap<String, Object>();
		TreeMap<String, Float> debitMap = new TreeMap<String, Float>();
		TreeMap<String, Float> creditMap = new TreeMap<String, Float>();

		for(BankTransactions transaction : bankTransactions) {
			Float debit = transaction.getDebit();
			Float credit = transaction.getCredit();
			Float balance = transaction.getBalance();
			String strDate = transaction.getDate().toString().substring(0,7);
			System.out.println("Debit : " + debit + " , Credit : " + credit + " , Balance : " + balance + " , Date : " + strDate);

			if(debit>0){
				if(debitMap.containsKey(strDate)){
					Float tmpPrice = debitMap.get(strDate);
					debitMap.put(strDate,tmpPrice + balance);
				} else {
					debitMap.put(strDate,balance);
				}
			} else {
				if(creditMap.containsKey(strDate)){
					Float tmpPrice = creditMap.get(strDate);
					creditMap.put(strDate,tmpPrice + balance);
				} else {
					creditMap.put(strDate,balance);
				}
			}
		}

		System.out.println("bankTransactionMap : " + bankTransactionMap);
		bankTransactionMap.put("debit",debitMap);
		bankTransactionMap.put("credit",creditMap);
		return bankTransactionMap;
	}

	public List<BankTransactions> getBankTransactions(String bankName){
		List<BankTransactions> transactions = bankRepositoryObj.findByBank(bankName);
		System.out.println("Transaction Size : " + transactions.size());
		System.out.println("Transaction : " + transactions);
		return transactions;
	}
}
