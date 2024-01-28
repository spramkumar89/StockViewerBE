package com.fin.zerodha.trades;

import com.fin.zerodha.utils.Holdings;
import com.fin.zerodha.utils.TradesTmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradesRepository extends JpaRepository<TradesTmp, Long> {


}