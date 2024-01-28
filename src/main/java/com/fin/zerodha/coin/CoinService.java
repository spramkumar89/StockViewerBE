package com.fin.zerodha.coin;

import com.fin.zerodha.utils.Coin;
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
public class CoinService {

	@Autowired
	private CoinRepository coinRepoObj;

	public List<Coin> saveTransactions(MultipartFile file){
		List<Coin> coinList = new ArrayList<>();
		try {
			Reader reader = new InputStreamReader(file.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).build();
			List<String[]> trades = csvReader.readAll();
			System.out.println("CSV Row Size : " + trades.size());

			Coin coin = null;
			trades.remove(0);
			for(String[] trade:trades){
				coin = new Coin();
				coin.setSymbol(trade[0]);
				coin.setIsin(trade[1]);
				coin.setTradeDate(trade[2]);
				coin.setExchange(trade[3]);
				coin.setSegment(trade[4]);
				coin.setSeries(trade[5]);
				coin.setTradeType(trade[6]);
				coin.setAuction(trade[7]);
				coin.setQuantity(Float.parseFloat(trade[8]));
				coin.setPrice(Float.parseFloat(trade[9]));
				coin.setTradeId(Long.parseLong(trade[10]));
				coin.setOrderId(Long.parseLong(trade[11]));
				coin.setOrderExecutionTime(trade[12]);
				coinList.add(coin);
			};
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (CsvException e) {
			throw new RuntimeException(e);
		}

		System.out.println("Transaction List Size : " + coinList.size());
		return coinRepoObj.saveAllAndFlush(coinList);
	}

	public List<Coin> getAllTransactions(){
		List<Coin> trades = coinRepoObj.findAll();
		System.out.println("Transactions Size : " + trades.size());
		HashMap<String, Float> buyMap = new HashMap<>();
		HashMap<String, Float> sellMap = new HashMap<>();
		for(Coin trade : trades) {
			String strDate = trade.getTradeDate().substring(3);
			String type = trade.getTradeType();
			Float price = trade.getQuantity() * trade.getPrice();

			if(type.equals("buy")){
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

		return coinRepoObj.findAll();
	}
}
