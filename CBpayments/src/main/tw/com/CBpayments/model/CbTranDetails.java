package com.CBpayments.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CBPayment_Tran_Details")
public class CbTranDetails {
	
	@Id 
	@Column(name = "sequence", length = 13)
	private String sequence;
	
	@Column(name = "order_key", length = 20)
	private String orderKey;
	
	@Column(name = "mch_id", length = 12)
	private String mchId;
	
	@Column(name = "out_trade_no", length = 19)
	private String outTradeNo;
	
	@Column(name = "pay_status", length = 10)
	private String payStatus;
	
	@Column(name = "fee_type", length = 16)
	private String feeType;
	
	@Column(name = "amount", length = 12)
	private double amount;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "pay_time")
	private Calendar payTime;
	
	@Column(name = "create_date")
	private Calendar createDate;
	
	@Column(name = "edit_date")
	private Calendar editDate;

	@Column(name = "body")
	private String body;
	
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Calendar getPayTime() {
		return payTime;
	}

	public void setPayTime(Calendar payTime) {
		this.payTime = payTime;
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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
