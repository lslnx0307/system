package com.jsjf.model.claims;

import java.math.BigDecimal;
import java.util.Date;

public class JsBorrowingInformation {

	private Integer id;
	private String loanNo;//订单号
	private BigDecimal amount;//借款金额
	private String birthday;//出生日期
	private String borrowerName;//姓名
	private Date borrowingTime;//借款时间
	private String function;//资金周转
	private String idCardNo;//身份证
	private Integer period;//期限
	private String phoneNo;//手机号
	private String marriage;//婚姻状况
	private String sex;//性别
	private String educationalBg;//教育程度
	private String repaymentSource;//还款来源
	private String repaymentMode;//还款方式
	private Integer lid;//债权ID
	private String address;//现居住地
	private String accountName;//数据来源
	private Integer age;//年龄
	private String startDate;//借款时间（开始）
	private String endDate;//借款时间（结束）
	private BigDecimal startAmount;//借款金额（开始）
	private BigDecimal endAmount;//借款金额（结束）
	private String isRelevanceLoan;//是否关联债权
	private String no;//关联的债权编号
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public BigDecimal getStartAmount() {
		return startAmount;
	}
	public void setStartAmount(BigDecimal startAmount) {
		this.startAmount = startAmount;
	}
	public BigDecimal getEndAmount() {
		return endAmount;
	}
	public void setEndAmount(BigDecimal endAmount) {
		this.endAmount = endAmount;
	}
	public String getIsRelevanceLoan() {
		return isRelevanceLoan;
	}
	public void setIsRelevanceLoan(String isRelevanceLoan) {
		this.isRelevanceLoan = isRelevanceLoan;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getBorrowerName() {
		return borrowerName;
	}
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	public Date getBorrowingTime() {
		return borrowingTime;
	}
	public void setBorrowingTime(Date borrowingTime) {
		this.borrowingTime = borrowingTime;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEducationalBg() {
		return educationalBg;
	}
	public void setEducationalBg(String educationalBg) {
		this.educationalBg = educationalBg;
	}
	public String getRepaymentSource() {
		return repaymentSource;
	}
	public void setRepaymentSource(String repaymentSource) {
		this.repaymentSource = repaymentSource;
	}
	public String getRepaymentMode() {
		return repaymentMode;
	}
	public void setRepaymentMode(String repaymentMode) {
		this.repaymentMode = repaymentMode;
	}
	public Integer getLid() {
		return lid;
	}
	public void setLid(Integer lid) {
		this.lid = lid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "JsBorrowingInformation [id=" + id + ", loanNo=" + loanNo + ", amount=" + amount + ", birthday="
				+ birthday + ", borrowerName=" + borrowerName + ", borrowingTime=" + borrowingTime + ", function="
				+ function + ", idCardNo=" + idCardNo + ", period=" + period + ", phoneNo=" + phoneNo + ", marriage="
				+ marriage + ", sex=" + sex + ", educationalBg=" + educationalBg + ", repaymentSource="
				+ repaymentSource + ", repaymentMode=" + repaymentMode + ", lid=" + lid + ", address=" + address
				+ ", accountName=" + accountName + ", age=" + age + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", startAmount=" + startAmount + ", endAmount=" + endAmount + ", isRelevanceLoan=" + isRelevanceLoan
				+ ", no=" + no + "]";
	}
	
}
