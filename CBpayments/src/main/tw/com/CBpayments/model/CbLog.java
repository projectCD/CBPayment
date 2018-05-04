package com.CBpayments.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CBPayment_Log")
public class CbLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer logSeq;

	@Column(name = "log_Merchants", length = 30)
	private String logMerchants;

	@Column(name = "log_contents")
	private String logContents;

	@Column(name = "log_time")
	private Calendar logTime;

	public Integer getLogSeq() {
		return logSeq;
	}

	public void setLogSeq(Integer logSeq) {
		this.logSeq = logSeq;
	}

	public String getLogMerchants() {
		return logMerchants;
	}

	public void setLogMerchants(String logMerchants) {
		this.logMerchants = logMerchants;
	}

	public String getLogContents() {
		return logContents;
	}

	public void setLogContents(String logContents) {
		this.logContents = logContents;
	}

	public Calendar getLogTime() {
		return logTime;
	}

	public void setLogTime(Calendar logTime) {
		this.logTime = logTime;
	}

}
