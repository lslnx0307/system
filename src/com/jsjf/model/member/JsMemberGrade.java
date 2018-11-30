package com.jsjf.model.member;

import java.math.BigDecimal;

public class JsMemberGrade {
	private Integer id;// Id自增长
	private String level;// 等级
	private Integer wealthStart;// 财富值区间（开始分值）
	private Integer wealthEnd;// 财富值区间（结束分值）
	private BigDecimal amount;// 需投资最少金额
	private Integer grade;//等级
	public JsMemberGrade(String level, Integer wealthStart, Integer wealthEnd,
			BigDecimal amount,Integer grade) {
		super();
		this.level = level;
		this.wealthStart = wealthStart;
		this.wealthEnd = wealthEnd;
		this.amount = amount;
		this.grade = grade;
	}
	public JsMemberGrade() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Integer getWealthStart() {
		return wealthStart;
	}
	public void setWealthStart(Integer wealthStart) {
		this.wealthStart = wealthStart;
	}
	public Integer getWealthEnd() {
		return wealthEnd;
	}
	public void setWealthEnd(Integer wealthEnd) {
		this.wealthEnd = wealthEnd;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
	
	
	
}
