package com.fin.zerodha.trades;

import com.fin.zerodha.utils.Trades;
import com.fin.zerodha.utils.TradesTmp;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class TradesService {

	@Autowired
	private TradesRepository tradesRepoObj;

	public List<TradesTmp> saveTrades(MultipartFile file){
		List<TradesTmp> tradesTmpList = new ArrayList<>();
		try {
			Reader reader = new InputStreamReader(file.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).build();
			List<String[]> trades = csvReader.readAll();
			System.out.println("CSV Row Size : " + trades.size());

			TradesTmp tradesTmp = null;
			trades.remove(0);
			for(String[] trade:trades){
				tradesTmp = new TradesTmp();
				tradesTmp.setSymbol(trade[0]);
				tradesTmp.setIsin(trade[1]);
				tradesTmp.setTradeDate(trade[2]);
				tradesTmp.setExchange(trade[3]);
				tradesTmp.setSegment(trade[4]);
				tradesTmp.setSeries(trade[5]);
				tradesTmp.setTradeType(trade[6]);
				tradesTmp.setAuction(trade[7]);
				tradesTmp.setQuantity(Integer.parseInt(trade[8]));
				tradesTmp.setPrice(Float.parseFloat(trade[9]));
				tradesTmp.setTradeId(Long.parseLong(trade[10]));
				tradesTmp.setOrderId(Long.parseLong(trade[11]));
				tradesTmp.setOrderExecutionTime(trade[12]);
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

	public List<TradesTmp> getAllTrades(){
		List<TradesTmp> trades = tradesRepoObj.findAll();
		System.out.println("Trades Size : " + trades.size());
		HashMap<String, Float> buyMap = new HashMap<>();
		HashMap<String, Float> sellMap = new HashMap<>();
		for(TradesTmp trade : trades) {
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
		System.out.println("Buy Trades : " + buyMap);
		System.out.println("Sell Trades : " + sellMap);

		return tradesRepoObj.findAll();
	}
}
