package com.jsjf.dao.member;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jsjf.model.member.JsCompanyAccountLog;

public interface JsCompanyAccountLogDAO {

	public void insertCompanyAccountLog(JsCompanyAccountLog accountLog);
	
	/**
	 * 批量添加
	 * @param accountLog
	 */
	public void insertBatch(List<JsCompanyAccountLog> list);
	
	public List<Map<String, Object>> getCompanyAccountLog(Map<String, Object> map);
	
	public int getCompanyAccountLogCount(Map<String, Object> map);
	
	/**
	 * 统计收入
	 * @param map
	 * @return
	 */
	public BigDecimal getCompanyAccountSRSum(Map<String, Object> map);
	
	/**
	 * 统计支出
	 * @param map
	 * @return
	 */
	public BigDecimal getCompanyAccountZCSum(Map<String, Object> map);
	
	/**
	 * 修改
	 */
	public void updateCompanyAccountLog(JsCompanyAccountLog accountLog);
	
	/**
	 * 查询存管最新余额
	 * @return
	 */
	public BigDecimal getBlanceByFuiou();
	
	/**
	 * 查询金运通最新余额
	 * @return
	 */
	public BigDecimal getBlanceByJYT();

}
