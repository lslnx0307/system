package com.jsjf.dao.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.member.DrMemberFundsRecord;

import java.math.BigDecimal;
import java.sql.SQLException;


public interface DrMemberFundsRecordDAO {
	
	/**
	 * 查找交易记录
	 * @param investId 投资ID 为NULL 则根据orderNo查询
	 * @param orderNo 支付订单编号 为NULL 则根据investId查询
	 * @return 查询到的交易记录
	 */
	public DrMemberFundsRecord selectByParam(@Param("investId") Integer investId, @Param("orderNo") String orderNo);

	/**
	 * 查找交易记录
	 * @param investId 投资ID 为NULL 则根据orderNo查询
	 * @param status 支付订单编号 为NULL 则根据investId查询
	 * @return 查询到的交易记录
	 */
	public DrMemberFundsRecord selectByParamForProductInterest(@Param("investId") Integer investId, @Param("status") Integer status);
	
	/**
	 * 批量修改用户交易记录
	 * @param list
	 */
	public void batchUpdateMemberFundsRecord(List<DrMemberFundsRecord> list);
	
	
	public void insert(DrMemberFundsRecord record) throws SQLException;
	
	/**
	 * 根据订单号修改
	 * @param DrMemberFundsRecord
	 * @return void
	 */
	public void updateStatusByNo(DrMemberFundsRecord record) throws SQLException;
	/**
	 * 根据Id修改
	 * @param DrMemberFundsRecord
	 * @return void
	 */
	public void updateStatusById(DrMemberFundsRecord record) throws SQLException;
	
	/**
	 * 获取用户资金记录
	 * @param map 
	 * @return
	 */
	public List<DrMemberFundsRecord> getDrMemberFundsRecordList(Map<String, Object> map);
	
	/**
	 * 获取用户资金记录条数
	 * @param map
	 * @return
	 */
	public Integer getDrMemberFundsRecordTotal(Map<String,Object> map);
	
	public DrMemberFundsRecord getDrMemberFundsRecordByMap(Map<String,Object> map);
	
	public void updateDrMemberFundsRecord(DrMemberFundsRecord record);
	
	public DrMemberFundsRecord selectMemberFundsRecordByOrderNo(Map<String, Object> map);
	
	/**
	 * 投资客户账户查询
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getFundsRecord(Map<String, Object> map);
	
	public int getFundsRecordCount(Map<String, Object> map);
	
	/**
	 * 统计收入
	 * @param map
	 * @return
	 */
	public BigDecimal getFundsRecordSRSum(Map<String, Object> map);
	
	/**
	 * 统计支出
	 * @param map
	 * @return
	 */
	public BigDecimal getFundsRecordZCSum(Map<String, Object> map);

}
