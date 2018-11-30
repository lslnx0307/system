package com.jsjf.model.claims;

import java.math.BigDecimal;


public class DrClaimsGuarantee{

    private Integer id;//担保情况ID

    private Integer lid;//贷款项目基本信息ID
    
    private Integer isPawn;//是否有抵/质押物

    private Integer pawnType;//抵/质押物类别

    private BigDecimal assessAmount;//抵/质押物评估值
    
    private Integer isAcceptance;//是否有承兑保证

    private String guarantor;//保证人名称
    
    private Integer isGuarantee;//是否有个人保证

    private Integer guaranteeType;//担保类别

    private String guarantee;//个人保证名称
    
    private Integer isFinanc;//是否有融资性担保

    private Integer financType;//担保类别

    private BigDecimal guaranteeAmount;//担保公司保证金额
    
    private BigDecimal singleGuaranteeAmount;//个人保证金额
    
    private Integer isCompanyGuarantee;//是否有公司保证
    
    private String companyGuarantee;//公司保证名称
    
    private BigDecimal companyGuaranteeAmount;//公司保证金额
    
    private String finanCompanyguarantee;//融资性担保公司名称
    
    private Integer pawnWay;//担保方式
    
    private String pawnMatter;//抵/质押物
    
    

	public String getPawnMatter() {
		return pawnMatter;
	}

	public void setPawnMatter(String pawnMatter) {
		this.pawnMatter = pawnMatter;
	}

	public BigDecimal getSingleGuaranteeAmount() {
		return singleGuaranteeAmount;
	}

	public void setSingleGuaranteeAmount(BigDecimal singleGuaranteeAmount) {
		this.singleGuaranteeAmount = singleGuaranteeAmount;
	}

	public Integer getIsCompanyGuarantee() {
		return isCompanyGuarantee;
	}

	public void setIsCompanyGuarantee(Integer isCompanyGuarantee) {
		this.isCompanyGuarantee = isCompanyGuarantee;
	}

	public String getCompanyGuarantee() {
		return companyGuarantee;
	}

	public void setCompanyGuarantee(String companyGuarantee) {
		this.companyGuarantee = companyGuarantee;
	}

	public BigDecimal getCompanyGuaranteeAmount() {
		return companyGuaranteeAmount;
	}

	public void setCompanyGuaranteeAmount(BigDecimal companyGuaranteeAmount) {
		this.companyGuaranteeAmount = companyGuaranteeAmount;
	}

	public String getFinanCompanyguarantee() {
		return finanCompanyguarantee;
	}

	public void setFinanCompanyguarantee(String finanCompanyguarantee) {
		this.finanCompanyguarantee = finanCompanyguarantee;
	}

	public Integer getPawnWay() {
		return pawnWay;
	}

	public void setPawnWay(Integer pawnWay) {
		this.pawnWay = pawnWay;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLid() {
		return lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	public Integer getIsPawn() {
		return isPawn;
	}

	public void setIsPawn(Integer isPawn) {
		this.isPawn = isPawn;
	}

	public Integer getPawnType() {
		return pawnType;
	}

	public void setPawnType(Integer pawnType) {
		this.pawnType = pawnType;
	}

	public BigDecimal getAssessAmount() {
		return assessAmount;
	}

	public void setAssessAmount(BigDecimal assessAmount) {
		this.assessAmount = assessAmount;
	}

	public Integer getIsAcceptance() {
		return isAcceptance;
	}

	public void setIsAcceptance(Integer isAcceptance) {
		this.isAcceptance = isAcceptance;
	}

	public String getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}

	public Integer getIsGuarantee() {
		return isGuarantee;
	}

	public void setIsGuarantee(Integer isGuarantee) {
		this.isGuarantee = isGuarantee;
	}

	public Integer getGuaranteeType() {
		return guaranteeType;
	}

	public void setGuaranteeType(Integer guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	public String getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}

	public Integer getIsFinanc() {
		return isFinanc;
	}

	public void setIsFinanc(Integer isFinanc) {
		this.isFinanc = isFinanc;
	}

	public Integer getFinancType() {
		return financType;
	}

	public void setFinancType(Integer financType) {
		this.financType = financType;
	}

	public BigDecimal getGuaranteeAmount() {
		return guaranteeAmount;
	}

	public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	@Override
	public String toString() {
		return "DrClaimsGuarantee [id=" + id + ", lid=" + lid + ", isPawn=" + isPawn + ", pawnType=" + pawnType
				+ ", assessAmount=" + assessAmount + ", isAcceptance=" + isAcceptance + ", guarantor=" + guarantor
				+ ", isGuarantee=" + isGuarantee + ", guaranteeType=" + guaranteeType + ", guarantee=" + guarantee
				+ ", isFinanc=" + isFinanc + ", financType=" + financType + ", guaranteeAmount=" + guaranteeAmount
				+ ", singleGuaranteeAmount=" + singleGuaranteeAmount + ", isCompanyGuarantee=" + isCompanyGuarantee
				+ ", companyGuarantee=" + companyGuarantee + ", companyGuaranteeAmount=" + companyGuaranteeAmount
				+ ", finanCompanyguarantee=" + finanCompanyguarantee + ", pawnWay=" + pawnWay + ", pawnMatter="
				+ pawnMatter + "]";
	}

    
}