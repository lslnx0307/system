package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

public interface JChannelClearingDAO {
	
	/**
	 * 获取渠道结算列表
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getChannelClearingList(Map<String, Object> map);
	
	/**
	 * 获取渠道结算列表总条数
	 * @param map
	 * @return
	 */
	public int getChannelClearingListCount(Map<String, Object> map);
	
	/**
	 * 获取渠道结算合计
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getChannelClearingListSum(Map<String, Object> map);

}
