package com.jsjf.model.operate;

import java.math.BigDecimal;
import java.util.Date;

public class DrUserAnalysis {

	private Integer id;

	private Integer newUser;// 新增注册用户

	private Integer totalUser;// 累计注册用户

	private Integer newInvestUser;// 新增投资用户

	private Integer totalInvestUser;// 累计投资用户

	private BigDecimal newInvestAmount;// 新增投资额

	private BigDecimal totalInvestAmount;// 累计投资额

	private BigDecimal pendRepaymentAmount;// 待还款金额

	private BigDecimal userAvgInvestAmount;// 用户平均投资额

	private Date addDate;// 添加时间

	private Date startDate;// 开始时间
	private Date endDate;// 结束时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNewUser() {
		return newUser;
	}

	public void setNewUser(Integer newUser) {
		this.newUser = newUser;
	}

	public Integer getTotalUser() {
		return totalUser;
	}

	public void setTotalUser(Integer totalUser) {
		this.totalUser = totalUser;
	}

	public Integer getNewInvestUser() {
		return newInvestUser;
	}

	public void setNewInvestUser(Integer newInvestUser) {
		this.newInvestUser = newInvestUser;
	}

	public Integer getTotalInvestUser() {
		return totalInvestUser;
	}

	public void setTotalInvestUser(Integer totalInvestUser) {
		this.totalInvestUser = totalInvestUser;
	}

	public BigDecimal getNewInvestAmount() {
		return newInvestAmount;
	}

	public void setNewInvestAmount(BigDecimal newInvestAmount) {
		this.newInvestAmount = newInvestAmount;
	}

	public BigDecimal getTotalInvestAmount() {
		return totalInvestAmount;
	}

	public void setTotalInvestAmount(BigDecimal totalInvestAmount) {
		this.totalInvestAmount = totalInvestAmount;
	}

	public BigDecimal getPendRepaymentAmount() {
		return pendRepaymentAmount;
	}

	public void setPendRepaymentAmount(BigDecimal pendRepaymentAmount) {
		this.pendRepaymentAmount = pendRepaymentAmount;
	}

	public BigDecimal getUserAvgInvestAmount() {
		return userAvgInvestAmount;
	}

	public void setUserAvgInvestAmount(BigDecimal userAvgInvestAmount) {
		this.userAvgInvestAmount = userAvgInvestAmount;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
