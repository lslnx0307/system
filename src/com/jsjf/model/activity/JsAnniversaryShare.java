package com.jsjf.model.activity;

import java.math.BigDecimal;
import java.util.Date;

public class JsAnniversaryShare {
	
	private Integer  id;
	private Integer  uid;
	private BigDecimal  amount;
	private Date  addtime;
	private Integer  investId;
	private BigDecimal  investAmount;
	private String  mobile;
	private Integer status;
	
	public JsAnniversaryShare(Integer uid, BigDecimal amount, Date addtime,
			Integer investId, BigDecimal investAmount, String mobile,
			Integer status) {
		super();
		this.uid = uid;
		this.amount = amount;
		this.addtime = addtime;
		this.investId = investId;
		this.investAmount = investAmount;
		this.mobile = mobile;
		this.status = status;
	}
	public JsAnniversaryShare() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Integer getInvestId() {
		return investId;
	}
	public void setInvestId(Integer investId) {
		this.investId = investId;
	}
	public BigDecimal getInvestAmount() {
		return investAmount;
	}
	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
