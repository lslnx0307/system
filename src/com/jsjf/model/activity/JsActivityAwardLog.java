package com.jsjf.model.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class JsActivityAwardLog implements Serializable{

	private Integer id;
	private Integer atid;
	private Integer uid;
	private Integer pid;
	private Integer type;
	private Integer awardRule;
	private BigDecimal amount;
	private Date addTime;
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAtid() {
		return atid;
	}
	public void setAtid(Integer atid) {
		this.atid = atid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getAwardRule() {
		return awardRule;
	}
	public void setAwardRule(Integer awardRule) {
		this.awardRule = awardRule;
	}
	public JsActivityAwardLog(Integer id, Integer atid, Integer uid,
			Integer pid, Integer type,BigDecimal amount, Integer awardRule, Date addTime) {
		super();
		this.id = id;
		this.atid = atid;
		this.uid = uid;
		this.pid = pid;
		this.type = type;
		this.amount = amount;
		this.awardRule = awardRule;
		this.addTime = addTime;
	}
	public JsActivityAwardLog() {
		super();
	}
	@Override
	public String toString() {
		return "JsActivityAwardLog [id=" + id + ", atid=" + atid + ", uid=" + uid + ", pid=" + pid + ", type=" + type
				+ ", awardRule=" + awardRule + ", amount=" + amount + ", addTime=" + addTime + "]";
	}
	
}
