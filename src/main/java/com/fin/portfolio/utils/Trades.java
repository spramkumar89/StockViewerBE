package com.fin.portfolio.utils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TBL_TRADES")
public class Trades {
	@Id
	@Column(name = "trade_ref_id")
	private String tradeRefId;
	@Column(name = "stock_symbol")
	private String stockSymbol;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date date;
	private String type;
	private Long quantity;
	private Float price;

}
