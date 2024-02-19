package com.fin.portfolio.trades;

import com.fin.portfolio.utils.TradesHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradesRepository extends JpaRepository<TradesHistory, Long> {
    public List<TradesHistory> findByStockSymbol(String name);

}