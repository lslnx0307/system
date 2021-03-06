package com.jsjf.dao.cpa;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.cpa.DrChannelInfo;

public interface DrChannelInfoDAO {
	/**
	 * 得到渠道信息列表
	 * 
	 * @param Map
	 * @return List<DrChannelInfo>
	 */
	public List<DrChannelInfo> getDrChannelInfoList(Map<String, Object> map);

	/**
	 * 得到渠道信息总数
	 * 
	 * @param Map
	 * @return Integer
	 */
	public Integer getDrChannelInfoCounts(Map<String, Object> map);

	/**
	 * 添加渠道信息
	 * 
	 * @param DrChannelInfo
	 * @return void
	 * @throws SQLException
	 *             ;
	 */
	public void insertDrChannelInfo(DrChannelInfo drChannelInfo)
			throws SQLException;

	/**
	 * 修改渠道信息
	 * 
	 * @param DrProductInfo
	 * @return void
	 * @throws SQLException
	 *             ;
	 */
	public void updateDrChannelInfo(DrChannelInfo drChannelInfo)
			throws SQLException;

	/**
	 * 根据id得到渠道信息
	 * 
	 * @param id
	 * @return DrChannelInfo
	 */
	public DrChannelInfo getDrChannelInfoByid(Integer id);

	/**
	 * 根据Map得到渠道信息
	 * 
	 * @param Map
	 * @return List<DrChannelInfo>
	 */
	public List<DrChannelInfo> getDrChannelInfoListForMap(
			Map<String, Object> map);

	/**
	 * 得到渠道用户列表
	 * 
	 * @param Map
	 * @return List<DrChannelInfo>
	 */
	public List<DrChannelInfo> getDrChannelInfoUserList(Map<String, Object> map);

	/**
	 * 得到渠道用户总数
	 * 
	 * @param Map
	 * @return Integer
	 */
	public Integer getDrChannelInfoUserCounts(Map<String, Object> map);

	/**
	 * 得到渠道订单列表
	 * 
	 * @param Map
	 * @return List<DrChannelInfo>
	 */
	public List<DrChannelInfo> getDrChannelInfoOrderList(Map<String, Object> map);

	/**
	 * 得到渠道订单总数
	 * 
	 * @param Map
	 * @return Integer
	 */
	public Integer getDrChannelInfoOrderCounts(Map<String, Object> map);
	/**
	 * 得到首投渠道订单列表
	 * 
	 * @param Map
	 * @return List<DrChannelInfo>
	 */
	public List<DrChannelInfo> getFirstDrChannelInfoOrderList(
			Map<String, Object> map);
	/**
	 * 得到首投渠道订单总数
	 * 
	 * @param Map
	 * @return Integer
	 */
	public Integer getFirstDrChannelInfoOrderCounts(Map<String, Object> map);
	
	/**
	 * 用户渠道订单列表（首投）
	 * @param map
	 * @return
	 */
	public List<DrChannelInfo> getDrChannelInfoOrderListFirst(Map<String, Object> map);
	/**
	 * 用户渠道订单总数（首投）
	 * @param map
	 * @return
	 */
	public Integer getDrChannelInfoOrderCountsFirst(Map<String, Object> map);
	
	/**
	 * 得到渠道订单姓名手机号不隐藏的
	 * 
	 * @param Map
	 * @return List<DrChannelInfo>
	 */
	public List<DrChannelInfo> getDrChannelInfoOrderListNew(Map<String, Object> map);

	/**
	 * 得到渠道订单总数姓名手机号不隐藏的
	 * 
	 * @param Map
	 * @return Integer
	 */
	public Integer getDrChannelInfoOrderCountsNew(Map<String, Object> map);
	
	/**
	 * 获取首投数据 不隐藏
	 * @param map
	 * @return
	 */
	public List<DrChannelInfo> getFirstDrChannelInfoOrderListNew(Map<String, Object> map);
	
	/**
	 * 获取大类的名字
	 * @return
	 */
	public List<Map<String,Object>> selectChannelType();
	

}
