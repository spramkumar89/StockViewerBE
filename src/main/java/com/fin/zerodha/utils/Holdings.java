package com.fin.zerodha.utils;

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
@Table(name="TBL_ZERODHA_HOLDINGS")
public class Holdings {
	@Id
	private String stockName;
	private int quantity;
	private float avgCost;
	private String lastTradedPrice;
	private float currentValue;
	private float PandL;
	private float NetChg;
	private float DayChg;
}
