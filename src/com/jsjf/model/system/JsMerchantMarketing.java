package com.jsjf.model.system;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户营销交易流水
 * @author DELL
 *
 */
public class JsMerchantMarketing {
	
	private Integer id;//主键
	private BigDecimal amount;//营销 支持 金额
	private Integer pid;//产品id
	private Integer investId;//投资id
	private Integer fid;//红包id
	private Integer uid;//用户id
	private Integer type;//类型:0.其他,1.投资红包返现,2.其他收益,3.邀请返现,4.提现手续费,5.提现退票手续退回,6.公司垫付
	private Date addtime;//添加时间
	private String mchnt_txn_ssn;//交易流水
	private String fuiouMessageNo;//报文流水号
	private Integer fileStatus;//报备状态：1-报文已上传，2-报备成功，3-报备失败
	private String failureCause;//报备备注
	private String remark;//备注
	private Integer status;//红包id
	
	public JsMerchantMarketing() {
	}

	public JsMerchantMarketing(BigDecimal amount, Integer pid,
			Integer investId, Integer fid, Integer uid, Integer type,
			Date addtime, String mchnt_txn_ssn, String fuiouMessageNo,
			Integer fileStatus, String failureCause, String remark,
			Integer status) {
		super();
		this.amount = amount;
		this.pid = pid;
		this.investId = investId;
		this.fid = fid;
		this.uid = uid;
		this.type = type;
		this.addtime = addtime;
		this.mchnt_txn_ssn = mchnt_txn_ssn;
		this.fuiouMessageNo = fuiouMessageNo;
		this.fileStatus = fileStatus;
		this.failureCause = failureCause;
		this.remark = remark;
		this.status = status;
	}




	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getInvestId() {
		return investId;
	}
	public void setInvestId(Integer investId) {
		this.investId = investId;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public String getMchnt_txn_ssn() {
		return mchnt_txn_ssn;
	}
	public void setMchnt_txn_ssn(String mchnt_txn_ssn) {
		this.mchnt_txn_ssn = mchnt_txn_ssn;
	}
	public String getFuiouMessageNo() {
		return fuiouMessageNo;
	}
	public void setFuiouMessageNo(String fuiouMessageNo) {
		this.fuiouMessageNo = fuiouMessageNo;
	}
	public Integer getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(Integer fileStatus) {
		this.fileStatus = fileStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}




	public Integer getPid() {
		return pid;
	}




	public void setPid(Integer pid) {
		this.pid = pid;
	}




	public String getFailureCause() {
		return failureCause;
	}




	public void setFailureCause(String failureCause) {
		this.failureCause = failureCause;
	}




	public Integer getStatus() {
		return status;
	}




	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
