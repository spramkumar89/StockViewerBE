package com.fin.zerodha.coin;

import com.fin.zerodha.utils.Coin;
import com.fin.zerodha.utils.TradesTmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {


}