package com.jsjf.dao.activity;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.model.activity.DrMemberFavourable;

public interface DrMemberFavourableDAO {
	/**
	 * 插入用户优惠券
	 * @param drMemberFavourable
	 * @throws SQLException
	 */
	public void insertIntoInfo(DrMemberFavourable drMemberFavourable) throws SQLException;
	/**
	 * 获取用户优惠券列表
	 * @param map
	 * @return
	 */
	public List<DrMemberFavourable> getMemberFavourable(Map<String,Object> map); 
	/**
	 * 获取用户优惠券统计
	 * @param map
	 * @return
	 */
	public Integer getMemberFavourableTotal(Map<String,Object> map); 
	
	/**
	 * 查询优惠券信息
	 * @param id 优惠券主键ID
	 * @return
	 */
	public DrMemberFavourable selectByPrimaryKey(Integer id);
	
	/**
	 * 修改优惠券状态
	 * @param drMemberFavourable
	 * @throws SQLException
	 */
	public void updateByPrimaryKey(DrMemberFavourable drMemberFavourable) throws SQLException;
	
	/**
	 * 获取体验金金额合计
	 * @param experience
	 * @return
	 */
	public BigDecimal getExperienceAmount(Map<String,Object> map);
	
	/**
	 * 
	 * @param map
	 */
	public void updateByExperience(Map<String,Object> map);
	/**
	 * 批量插入优惠券
	 * @param list
	 */
	public void batchInsert(List<DrMemberFavourable> list);
	/**
	 * 获取活动大礼包
	 * @param map
	 */
	public List<DrMemberFavourable> getMemberFavourableByValentine(Map<String,Object> map);
	/**
	 * 跟据map修改
	 * @param map
	 */
	public void updateFavourableStatusByMap(Map<String,Object> map);
	/**
	 * 获取用户优惠券列表
	 * @param map
	 * @return
	 */
	public List<DrMemberFavourable> getMemberFavourableByParam(Map<String,Object> map); 
	
	public void updateFavourableStatus(DrMemberFavourable drMemberFavourable);
	/**
	 * 根据uid获取用户优惠卷
	 * @param uid
	 * @return 
	 */
	public BigDecimal selectHasRedPacket(Integer uid);
	
	public Map<String, Object> getFavourableById(Map<String, Object> map);
}
