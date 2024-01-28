package com.fin.zerodha.utils;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TBL_ZERODHA_TRADES")
public class Trades {
	@Id
	private Long orderId;
	private String symbol;
	private String isin;
	private String tradeDate;
	private String lastTradedPrice;
	private String exchange;
	private String segment;
	private String series;
	private String tradeType;
	private String auction		;
	private int quantity;
	private float price;
	private Long tradeId;
	private String orderExecutionTime;
}
