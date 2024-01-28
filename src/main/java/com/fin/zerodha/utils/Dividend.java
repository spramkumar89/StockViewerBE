package com.fin.zerodha.utils;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_ZERODHA_DIVIDEND")
public class Dividend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String symbol;
    private String isin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private int quantity;
    private float dividendPerShare;
    private float netDividendAmount;
}
