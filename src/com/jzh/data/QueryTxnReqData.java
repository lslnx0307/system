package com.jzh.data;

public class QueryTxnReqData extends BaseReqdata{

	private String busi_tp;//交易类型 PWPC转账， PW13预授权， PWCF预授权撤销，PW03 划拨，PW14 转账冻结，PW15 划拨冻结，PWDJ 冻结，PWJD 解冻，PW19 冻结付款到冻结
	private String start_day;//起始时间
	private String end_day;//截止时间
	private String txn_ssn;//交易流水
	private String cust_no;//交易用户
	private String txn_st;//交易状态
	private String remark;//交易备注
	private String page_no;//交易页码
	private String page_size;//每页条数
	public String getBusi_tp() {
		return busi_tp;
	}
	public void setBusi_tp(String busi_tp) {
		this.busi_tp = busi_tp;
	}
	public String getStart_day() {
		return start_day;
	}
	public void setStart_day(String start_day) {
		this.start_day = start_day;
	}
	public String getEnd_day() {
		return end_day;
	}
	public void setEnd_day(String end_day) {
		this.end_day = end_day;
	}
	public String getTxn_ssn() {
		return txn_ssn;
	}
	public void setTxn_ssn(String txn_ssn) {
		this.txn_ssn = txn_ssn;
	}
	public String getCust_no() {
		return cust_no;
	}
	public void setCust_no(String cust_no) {
		this.cust_no = cust_no;
	}
	public String getTxn_st() {
		return txn_st;
	}
	public void setTxn_st(String txn_st) {
		this.txn_st = txn_st;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPage_no() {
		return page_no;
	}
	public void setPage_no(String page_no) {
		this.page_no = page_no;
	}
	public String getPage_size() {
		return page_size;
	}
	public void setPage_size(String page_size) {
		this.page_size = page_size;
	}
	
	
}
