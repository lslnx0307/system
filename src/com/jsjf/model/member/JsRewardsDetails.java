package com.jsjf.model.member;

import java.util.Date;

public class JsRewardsDetails {
	private Integer id;// Id自增长
	private Integer mrid;// 奖励ID
	private Date addDate;// 领取时间
	private Integer mid;// 用户ID
	public JsRewardsDetails() {
		super();
	}
	
	public JsRewardsDetails(Integer id, Integer mrid, Date addDate, Integer mid) {
		super();
		this.id = id;
		this.mrid = mrid;
		this.addDate = addDate;
		this.mid = mid;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMrid() {
		return mrid;
	}
	public void setMrid(Integer mrid) {
		this.mrid = mrid;
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
	
}
