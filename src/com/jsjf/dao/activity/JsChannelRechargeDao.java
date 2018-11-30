package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface JsChannelRechargeDao {

	/**
	 * 添加
	 * @param param
	 */
	public void insert(Map<String,Object> param);
	
	/**
	 * 渠道充值列表
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> selectList(Map<String,Object> param);
	/**
	 * 渠道充值列表总数
	 * @param param
	 * @return
	 */
	public int selectListCount(Map<String,Object> param);
	/**
	 * 渠道充值总和
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> selectListAmountSum(Map<String,Object> param);
	
	/**
	 * 获取每个大类下最新一条充值记录
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> selectChannelRechargeByChannelName(@Param("channel_id") Integer channel_id);
}
