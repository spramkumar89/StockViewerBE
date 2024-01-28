package com.fin.icicidirect.utils;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TBL_ICICIDIRECT_TRADES")
public class ICICITrades {
	private String tradeDate;
	private String stock;
	private String tradeType;
	private int quantity;
	private float price;
	private float tradedValue;
	@Id
	private String orderRefNum;
	private String settlement;
	private String segment;
	private String dpclientid;
	private String exchange;
	private float stt;
	private float transactionCharges;
	private float stampDuty;
	private float brokerageServiceTax;
	private float brokerageInclTax;

}