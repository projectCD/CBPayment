package com.CBpayments.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CBPayment_Customer_List")
public class CbCustomer {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sequence;
	
	@Column(name = "mch_id", length = 12)
	private String mchId;
	
	@Column(name = "mch_name" )
	private String mchName;
	
	@Column(name = "current_status", length = 3)
	private String currentStatus;
	
	@Column(name = "mch_key" , length = 32)
	private String mchKey;
	
	@Column(name = "reg_date")
	private Calendar regDate;
	
	@Column(name = "create_date")
	private Calendar createDate;
	
	@Column(name = "edit_date")
	private Calendar editDate;

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getMchId() {
		return mchId;
	}

	public String getMchName() {
		return mchName;
	}

	public void setMchName(String mchName) {
		this.mchName = mchName;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Calendar getRegDate() {
		return regDate;
	}

	public void setRegDate(Calendar regDate) {
		this.regDate = regDate;
	}

	public String getMchKey() {
		return mchKey;
	}

	public void setMchKey(String mchKey) {
		this.mchKey = mchKey;
	}

	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	public Calendar getEditDate() {
		return editDate;
	}

	public void setEditDate(Calendar editDate) {
		this.editDate = editDate;
	}
}
