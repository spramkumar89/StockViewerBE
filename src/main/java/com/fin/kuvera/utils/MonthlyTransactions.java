package com.fin.kuvera.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyTransactions {
	@DateTimeFormat(pattern = "MM-yyyy")
	private Date date;
	private float buyAmount = 0;
	private float sellAmount = 0;
	private float dividendAmount = 0;
}
