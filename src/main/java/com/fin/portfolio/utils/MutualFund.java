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
@Table(name="TBL_MUTUALFUND")
public class MutualFund {
	@Id
	@Column(name="mf_ref_id")
	private String mfRefId;
	@Column(name = "fund_name")
	private String fundName;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date date;
	private String type;
	private Float units;
	private Float nav;
}
