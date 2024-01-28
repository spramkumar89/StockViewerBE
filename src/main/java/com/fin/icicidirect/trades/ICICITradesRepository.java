package com.fin.icicidirect.trades;

import com.fin.icicidirect.utils.ICICITradesTmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICICITradesRepository extends JpaRepository<ICICITradesTmp, String> {


}