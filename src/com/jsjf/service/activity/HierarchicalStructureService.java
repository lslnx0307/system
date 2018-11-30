package com.jsjf.service.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;

public interface HierarchicalStructureService {
	
	public List<Map<String, Object>> selectHierarchicalStructure(Map<String, Object> map);
	
	/**
	 * 优惠券明细列表
	 * @param map
	 * @return
	 */
	public BaseResult selectCouponDetail(Map<String,Object> map,PageInfo page);
	
	public List<Map<String,Object>> exportCouponDetail(Map<String,Object> map);
	
	
	/**
	 * 活动预算
	 * @param map
	 * @return
	 */
	public BaseResult getActivityBudget(Map<String, Object> map,PageInfo info);

	/**
	 * 邀请奖励明细查询
	 * @param map
	 * @param pi
	 * @return
	 */
	public BaseResult getInviteAwarDetailList(Map<String, Object> map, PageInfo pi);

	/**
	 * 邀请奖励明细导出查询
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> exportInviteAwarDetail(Map<String, Object> map);

	/**
	 * 查询第三层分类
	 * @return
	 */
	public List<Map<String, Object>> getTreeFlag();
	
	/**
	 * 获取渠道结算列表
	 * @param map
	 * @return
	 */
	public BaseResult getChannelClearingList(Map<String, Object> map,PageInfo info);
	

	/**
	 * 查询优惠卷层次详情
	 * @param pi
	 * @param param
	 * @return
	 */
	public BaseResult selectCouponHierarchyDetail(PageInfo pi, Map<String, Object> param);

	/**
	 * 新增优惠卷
	 * @param param
	 */
	public void insertCouponHierarchy(Map<String, Object> param);

	/**
	 * 修改优惠卷
	 * @param param
	 */
	public void updateCouponHierarchy(Map<String, Object> param);

	/**
	 * 根据id查询第三层
	 * @param map
	 * @return
	 */
	public Map<String, Object> selectCouponHierarchyById(Map<String, Object> map);

}
