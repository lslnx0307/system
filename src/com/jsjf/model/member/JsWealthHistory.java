package com.jsjf.model.member;

import java.util.Date;

public class JsWealthHistory {
	private Integer id;// Id自增长
	private String name;// 任务名称
	private Integer wealth;// 财富值
	private Date addDate;// 完成时间
	private Integer mid;// 用户ID
	private Integer tdid;//type= 0-任务id 1-投资id
	private Integer type;//0-任务 1-投资
	public JsWealthHistory() {
		super();
	}
	public JsWealthHistory(String name, Integer wealth, Date addDate,
			Integer mid, Integer tdid,Integer type) {
		super();
		this.name = name;
		this.wealth = wealth;
		this.addDate = addDate;
		this.mid = mid;
		this.tdid = tdid;
		this.type = type;
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
	public Integer getWealth() {
		return wealth;
	}
	public void setWealth(Integer wealth) {
		this.wealth = wealth;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public Integer getTdid() {
		return tdid;
	}
	public void setTdid(Integer tdid) {
		this.tdid = tdid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
