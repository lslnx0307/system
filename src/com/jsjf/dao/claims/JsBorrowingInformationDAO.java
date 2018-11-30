package com.jsjf.dao.claims;

import java.util.List;
import java.util.Map;

import com.jsjf.model.claims.JsBorrowingInformation;

public interface JsBorrowingInformationDAO {
	
	public List<JsBorrowingInformation> selectBorrowingInformation(Map<String, Object> map);
	
	public void updateBorrowingInformationLid(Map<String, Object> map);

	
	/**
	 * 查询小贷信息
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getBorrowInformationList(Map<String, Object> map);

	
	/**
	 * 查询小贷信息总数
	 * @param map
	 * @return
	 */
	public Integer getBorrowInformationListCount(Map<String, Object> map);
	
	/**
	 * 添加借款信息
	 * @param map
	 */
	public void insert(Map<String,Object> map);
	/**
	 * 批量添加
	 * @param map
	 */
	public void batchInsert(List<Map<String,Object>> list);

	/**
	 * 借款金额总计
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getBorrowInformationListCountAll(Map<String, Object> map);
	
}