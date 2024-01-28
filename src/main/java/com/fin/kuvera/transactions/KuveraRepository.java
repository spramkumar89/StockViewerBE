package com.fin.kuvera.transactions;

import com.fin.kuvera.utils.KuveraTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KuveraRepository extends JpaRepository<KuveraTransactions, Long> {


}