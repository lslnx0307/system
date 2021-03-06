package com.jsjf.model.member;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PropertyUtil;

public class DrMemberFundsRecord {
	
	private Integer id;
	
	//投资ID
	private Integer investId;
	
	//关联的产品ID
	private Integer pid;
	
	//用户ID
	private Integer uid;
	
	//交易类型
	private Integer tradeType;
	
	private String tradeTypeName;
	
	//0-支出，1收入
	private Integer type;
	
	private String typeName;
	
	//变动金额	
	private BigDecimal amount;
	
	//账户余额
	private BigDecimal balance;
	
	//状态
	private Integer status;
	
	private String statusName;
	
	//备注
	private String remark;
	
	//发生时间
	private Date addTime;
	
	//订单编号
	private String orderNo;
	
	private String level;//会员等级
	
	public DrMemberFundsRecord(){}

	public DrMemberFundsRecord(Integer pid, Integer investId,Integer uid, Integer tradeType,
			Integer type, BigDecimal amount, BigDecimal balance,
			Integer status, String remark,String orderNo) {
		super();
		this.pid = pid;
		this.investId = investId;
		this.uid = uid;
		this.tradeType = tradeType;
		this.type = type;
		this.amount = amount;
		this.balance = balance;
		this.status = status;
		this.remark = remark;
		this.orderNo = orderNo;
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public Integer getInvestId() {
		return investId;
	}


	public void setInvestId(Integer investId) {
		this.investId = investId;
	}

	/**
	 * @return the tradeTypeName
	 */
	public String getTradeTypeName() {
		try {
			tradeTypeName = ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("tradeType")).get(tradeType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tradeTypeName;
	}

	/**
	 * @param tradeTypeName the tradeTypeName to set
	 */
	public void setTradeTypeName(String tradeTypeName) {
		this.tradeTypeName = tradeTypeName;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		try {
			typeName = ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("balanceType")).get(type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		try {
			statusName = ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("recordStatus")).get(status);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
