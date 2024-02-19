package com.fin.portfolio.mf;

import com.fin.portfolio.utils.MutualFundTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MFRepository extends JpaRepository<MutualFundTransactions, Long> {
    public List<MutualFundTransactions> findByFundName(String name);

}