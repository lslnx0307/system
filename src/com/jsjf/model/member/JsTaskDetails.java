package com.jsjf.model.member;

import java.util.Date;

public class JsTaskDetails {
	private Integer  id;//Id自增长
	private Integer mtid;//任务ID
	private Date addDate;//任务完成时间
	private Integer mid;//用户ID
	private Integer type;
	public JsTaskDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JsTaskDetails(Integer mtid, Date addDate, Integer mid) {
		super();
		this.mtid = mtid;
		this.addDate = addDate;
		this.mid = mid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMtid() {
		return mtid;
	}
	public void setMtid(Integer mtid) {
		this.mtid = mtid;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
