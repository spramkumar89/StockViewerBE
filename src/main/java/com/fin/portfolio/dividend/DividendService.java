package com.fin.portfolio.dividend;

import com.fin.portfolio.utils.DividendTransactions;
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
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

@Service
public class DividendService {

	@Autowired
	private DividendRepository dividendRepositoryObj;

	public List<DividendTransactions> saveTrades(MultipartFile file){
		List<DividendTransactions> dividendTmpList = new ArrayList<>();
		try {
			Reader reader = new InputStreamReader(file.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).build();
			List<String[]> dividends = csvReader.readAll();
			System.out.println("CSV Row Size : " + dividends.size());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			DividendTransactions dividendTmp = null;
			dividends.remove(0);
			for(String[] dividend : dividends){
				dividendTmp = new DividendTransactions();
				dividendTmp.setDividendRefId(dividend[0]);
				dividendTmp.setStockSymbol(dividend[1]);
				dividendTmp.setDate(sdf.parse(dividend[2]));
				dividendTmp.setQuantity(Long.parseLong(dividend[3]));
				dividendTmp.setDividend_per_share(Float.parseFloat(dividend[4]));

				dividendTmpList.add(dividendTmp);
			};
			dividendRepositoryObj.saveAllAndFlush(dividendTmpList);
			System.out.println("Trade Records Saved");
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (CsvException e) {
			throw new RuntimeException(e);
		} catch (ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Trade List Size : " + dividendTmpList.size());
		return dividendTmpList;
	}

	public TreeMap<String, Float> getDividendList(){
		List<DividendTransactions> dividends = dividendRepositoryObj.findAll();
		System.out.println("Dividend Size : " + dividends.size());
		TreeMap<String, Float> stockDividend = new TreeMap<String, Float>();

		for(DividendTransactions dividend : dividends) {
			String stock = dividend.getStockSymbol();
			Float price = dividend.getQuantity() * dividend.getDividend_per_share();

			if(stockDividend.containsKey(stock)){
				Float tmpPrice = stockDividend.get(stock);
				stockDividend.put(stock,tmpPrice + price);
			} else {
				stockDividend.put(stock,price);
			}
		}
		System.out.println("stockDividend : " + stockDividend);
		return stockDividend;
	}

	public List<DividendTransactions> getStockDividend(String name){
		List<DividendTransactions> dividends = dividendRepositoryObj.findByStockSymbol(name);
		System.out.println("Dividend Size : " + dividends.size());
		System.out.println("Dividend : " + dividends);
		return dividends;
	}
}
