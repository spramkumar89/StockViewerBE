package com.fin.zerodha.dividend;

import com.fin.zerodha.utils.Holdings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DividendRepository extends JpaRepository<Holdings, Long>{

	public static final String HOLDING_NAME = "SELECT h.symbol FROM zerodha_holdings h";
	public static final String HOLDING_BASIC_DETAIL = "SELECT h.holding_id, h.symbol, h.average_price, h.quantity_available, h.sector, h.unrealize_pl_percentage, h.unrealized_pl FROM zerodha_holdings h";

	/*@Query(value = HOLDING_NAME, nativeQuery = true)
	public List<String> getHoldingNames();
	
	public Holdings findBySymbol(String symbol);*/
}
