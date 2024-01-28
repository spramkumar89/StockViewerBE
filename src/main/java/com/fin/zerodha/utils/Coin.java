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
@Table(name="TBL_COIN_TRANSACTIONS")
public class Coin {
    private String symbol;
    private String isin;
    private String tradeDate;
    private String exchange;
    private String segment;
    private String series;
    private String tradeType;
    private String auction;
    private float quantity;
    private float price;
    private Long tradeId;
    @Id
    private Long orderId;
    private String orderExecutionTime;
}
