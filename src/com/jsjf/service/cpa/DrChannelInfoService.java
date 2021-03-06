package com.jsjf.service.cpa;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.cpa.DrChannelInfo;
import com.jsjf.model.member.DrMember;

public interface DrChannelInfoService {
	/**
	 * 得到渠道信息列表
	 * 
	 * @param Map
	 * @return List<DrChannelInfo>
	 */
	public BaseResult getDrChannelInfoList(DrChannelInfo drChannelInfo,
			PageInfo pi);

	/**
	 * 得到渠道用户列表
	 * 
	 * @param Map
	 * @return List<DrChannelInfo>
	 */
	public BaseResult getDrChannelInfoUserList(DrChannelInfo drChannelInfo,
			PageInfo pi);

	/**
	 * 得到渠道订单列表
	 * 
	 * @param Map
	 * @return List<DrChannelInfo>
	 */
	public BaseResult getDrChannelInfoOrderList(DrChannelInfo drChannelInfo,
			PageInfo pi);
	
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
	 * 导出渠道用户信息
	 * 
	 * @param DrChannelInfo
	 * @return Map<String,Object>
	 */
	public Map<String, Object> exportChannelUser(DrChannelInfo drChannelInfo)
			throws Exception;
	
	/**
	 * 导出渠道订单信息
	 * 
	 * @param DrChannelInfo
	 * @return Map<String,Object>
	 */
	public Map<String, Object> exportChannelOrder(DrChannelInfo drChannelInfo)
			throws Exception;
	/**
	 * 得到首投渠道订单列表
	 * 
	 * @param Map
	 * @return List<DrChannelInfo>
	 */
	public BaseResult getFirstDrChannelInfoOrderList(
			DrChannelInfo drChannelInfo, PageInfo pi);
	/**
	 * 导出首投渠道用户信息
	 * 
	 * @param DrChannelInfo
	 * @return Map<String,Object>
	 */
	public Map<String, Object> exportFirstChannelOrder(
			DrChannelInfo drChannelInfo);
	/**
	 * 用户渠道订单（首投）列表
	 * @param drChannelInfo
	 * @param pi
	 * @return
	 */
	public BaseResult getDrChannelInfoOrderListFirst(DrChannelInfo drChannelInfo, PageInfo pi);
	/**
	 * 用户渠道订单导出（首投）
	 * @param drChannelInfo
	 * @param flag_7_newHand 1 包涵存管 7 天标 ,0 不包涵
	 * @return
	 */
	public Map<String, Object> exportChannelOrderFirst(DrChannelInfo drChannelInfo);
	
	/**
	 * 得到渠道订单列表姓名 手机号不隐藏的
	 * 
	 * @param Map
	 * @return List<DrChannelInfo>
	 * @param flag_newHand 是否显示 7天 新手标
	 */
	public BaseResult getDrChannelInfoOrderListNew(DrChannelInfo drChannelInfo,
			PageInfo pi);
	/**
	 * 获取首投数据 不隐藏
	 * @param map
	 * @return
	 */
	public BaseResult getFirstDrChannelInfoOrderListNew(DrChannelInfo drChannelInfo, PageInfo pi);
	/**
	 * 获取渠道列表
	 * @param map
	 * @return
	 */
	public List<DrChannelInfo> getDrChannelInfoList(Map<String, Object> map);
	/**
	 * 根据渠道号查询用户
	 * @return
	 */
	public BaseResult selectDrMemberByChannel(DrMember drMember, PageInfo pi);
	
	public List<Map<String,Object>> selectChannelType();

}
