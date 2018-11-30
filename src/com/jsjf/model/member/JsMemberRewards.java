package com.jsjf.model.member;

import java.math.BigDecimal;

public class JsMemberRewards {
	private Integer id;// Id自增长
	private String name;// 奖励名称
	private String apid;// 优惠券ID
	private Integer valid;// 是否禁用 1-可用 0-不可用
	private Integer mgid;// 会员等级ID
	private Integer type;// 1-升级奖励，2-免费提现次数，3-生日专享，4-年末大礼，5-V标特权，6-优先赔付,7-活动标定制，8-专属投资顾问
	private Integer quantity;// 次数
	private Integer grade;//等级
	private BigDecimal amount;
	private Integer way;
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getWay() {
		return way;
	}
	public void setWay(Integer way) {
		this.way = way;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public JsMemberRewards() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JsMemberRewards(String name, String apid, Integer valid,
			Integer mgid, Integer type, Integer quantity) {
		super();
		this.name = name;
		this.apid = apid;
		this.valid = valid;
		this.mgid = mgid;
		this.type = type;
		this.quantity = quantity;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getApid() {
		return apid;
	}
	public void setApid(String apid) {
		this.apid = apid;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public Integer getMgid() {
		return mgid;
	}
	public void setMgid(Integer mgid) {
		this.mgid = mgid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
