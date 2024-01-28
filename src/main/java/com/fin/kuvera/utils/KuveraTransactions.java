package com.fin.kuvera.utils;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TBL_KUVERA_TRANSACTIONS")
public class KuveraTransactions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String date;
	private Long folioNumber;
	private String fundName;
	private String tradeType;
	private float units;
	private float nav;
	private float currentNAV;
	private float amount;
}
