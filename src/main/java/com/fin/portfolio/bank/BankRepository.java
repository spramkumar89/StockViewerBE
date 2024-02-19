package com.fin.portfolio.bank;

import com.fin.portfolio.utils.BankTransactions;
import com.fin.portfolio.utils.DividendTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<BankTransactions, Long> {
    public List<BankTransactions> findByBank(String name);

}