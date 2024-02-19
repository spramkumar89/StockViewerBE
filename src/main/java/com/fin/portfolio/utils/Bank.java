package com.fin.portfolio.utils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TBL_BANK")
public class Bank {
	@Id
	@Column(name="txn_ref_id")
	private String txnRefId;
	private String bank;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date date;
	private String description;
	private String ref_no;
	private Float debit;
	private Float credit;
	private Float balance;
}
