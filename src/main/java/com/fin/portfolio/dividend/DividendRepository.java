package com.fin.portfolio.dividend;

import com.fin.portfolio.utils.DividendTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DividendRepository extends JpaRepository<DividendTransactions, Long> {
    public List<DividendTransactions> findByStockSymbol(String name);

}