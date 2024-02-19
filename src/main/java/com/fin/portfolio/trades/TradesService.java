package com.fin.portfolio.trades;

import com.fin.portfolio.utils.TradesHistory;
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
import java.util.*;

@Service
public class TradesService {

	@Autowired
	private TradesRepository tradesRepoObj;

	public List<TradesHistory> saveTrades(MultipartFile file){
		List<TradesHistory> tradesTmpList = new ArrayList<>();
		try {
			Reader reader = new InputStreamReader(file.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).build();
			List<String[]> trades = csvReader.readAll();
			System.out.println("CSV Row Size : " + trades.size());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			TradesHistory tradesTmp = null;
			trades.remove(0);
			for(String[] trade:trades){
				tradesTmp = new TradesHistory();
				tradesTmp.setTradeRefId(trade[0]);
				tradesTmp.setStockSymbol(trade[1]);
				tradesTmp.setDate(sdf.parse(trade[2]));
				tradesTmp.setType(trade[3]);
				tradesTmp.setQuantity(Long.parseLong(trade[4]));
				tradesTmp.setPrice(Float.parseFloat(trade[5]));
				tradesTmpList.add(tradesTmp);
			};
			tradesRepoObj.saveAllAndFlush(tradesTmpList);
			System.out.println("Trade Records Saved");
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (CsvException e) {
			throw new RuntimeException(e);
		} catch (ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Trade List Size : " + tradesTmpList.size());
		return tradesTmpList;
	}

	public HashMap<String, Object> getMonthlyWiseTrades(){
		List<TradesHistory> trades = tradesRepoObj.findAll();
		System.out.println("Trades Size : " + trades.size());
		HashMap<String, Object> monthlyData = new HashMap<>();
		TreeMap<String, Float> buyMap = new TreeMap<String, Float>();
		TreeMap<String, Float> sellMap = new TreeMap<String, Float>();

		for(TradesHistory trade : trades) {
			String strDate = trade.getDate().toString().substring(0,7);
			String type = trade.getType();
			Float price = trade.getQuantity() * trade.getPrice();

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
		System.out.println("Buy Trades : " + buyMap);
		System.out.println("Sell Trades : " + sellMap);

		monthlyData.put("buy",buyMap);
		monthlyData.put("sell",sellMap);
		return monthlyData;
	}

	public List<TradesHistory> getStockWiseTrades(String name){
		List<TradesHistory> trades = tradesRepoObj.findByStockSymbol(name);
		System.out.println("Trades Size : " + trades.size());
		System.out.println("Trades : " + trades);
		return trades;
	}
}
