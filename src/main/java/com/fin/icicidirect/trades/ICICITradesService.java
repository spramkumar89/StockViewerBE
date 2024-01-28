package com.fin.icicidirect.trades;

import com.fin.icicidirect.utils.ICICITradesTmp;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class ICICITradesService {

	@Autowired
	private ICICITradesRepository tradesRepoObj;

	public List<ICICITradesTmp> saveTrades(MultipartFile file){
		List<ICICITradesTmp> tradesTmpList = new ArrayList<>();
		try {
			Reader reader = new InputStreamReader(file.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).build();
			List<String[]> trades = csvReader.readAll();
			System.out.println("CSV Row Size : " + trades.size());

			ICICITradesTmp tradesTmp = null;
			trades.remove(0);
			for(String[] trade:trades){
				System.out.println("Trade data : " + Arrays.toString(trade));
				tradesTmp = new ICICITradesTmp();
				tradesTmp.setTradeDate(trade[0]);
				tradesTmp.setStock(trade[1]);
				tradesTmp.setTradeType(trade[2]);
				tradesTmp.setQuantity(Integer.parseInt(trade[3]));
				tradesTmp.setPrice(Float.parseFloat(trade[4]));
				tradesTmp.setTradedValue(Float.parseFloat(trade[5]));
				tradesTmp.setOrderRefNum(trade[6]);
				tradesTmp.setSettlement(trade[7]);
				tradesTmp.setSegment(trade[8]);
				tradesTmp.setDpclientid(trade[9]);
				tradesTmp.setExchange(trade[10]);
				tradesTmp.setStt(Float.parseFloat(trade[11]));
				tradesTmp.setTransactionCharges(Float.parseFloat(trade[12]));
				tradesTmp.setStampDuty(Float.parseFloat(trade[13]));
				tradesTmp.setBrokerageServiceTax(Float.parseFloat(trade[14]));
				tradesTmp.setBrokerageInclTax(Float.parseFloat(trade[15]));
				tradesTmpList.add(tradesTmp);
			};
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (CsvException e) {
			throw new RuntimeException(e);
		}

		System.out.println("Trade List Size : " + tradesTmpList.size());
		return tradesRepoObj.saveAllAndFlush(tradesTmpList);
	}

	public List<ICICITradesTmp> getAllTrades(){
		List<ICICITradesTmp> trades = tradesRepoObj.findAll();
		System.out.println("Trades Size : " + trades.size());
		HashMap<String, Float> buyMap = new HashMap<>();
		HashMap<String, Float> sellMap = new HashMap<>();
		for(ICICITradesTmp trade : trades) {
			String strDate = trade.getTradeDate().substring(3);
			String type = trade.getTradeType();
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

		return tradesRepoObj.findAll();
	}
}
