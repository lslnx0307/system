package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

public interface HierarchicalStructureDAO {

	public List<Map<String, Object>> selectHierarchicalStructure(Map<String, Object> map);
	
	/**
	 * 优惠券明细列表
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectCouponDetail(Map<String,Object> map);
	/**
	 *  优惠券总数
	 * @param map
	 * @return
	 */
	public int selectCouponDetailCount(Map<String,Object> map);
	/**
	 * 优惠券明细总额
	 * @param map
	 * @return
	 */
	public Map<String,Object> selectCouponDetailSum(Map<String,Object> map); 
	/**
	 * 优惠券明细分页总额
	 * @param map
	 * @return
	 */
	public  List<Map<String,Object>> selectCouponDetailPageSum(Map<String,Object> map); 
	
	/**
	 * 活动预算
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getActivityBudget(Map<String, Object> map);
	
	/**
	 * 活动预算统计
	 * @param map
	 * @return
	 */
	public Integer getActivityBudgetCount(Map<String, Object> map);
	
	/**
	 * 活动合计
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getActivityBudgetSum(Map<String, Object> map);
	/**
	 * 邀请奖励明细查询
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getInviteAwarDetailList(Map<String, Object> map);

	/**
	 * 邀请奖励明细总数查询
	 * @param map
	 * @return
	 */
	public int getInviteAwarDetailListCount(Map<String, Object> map);

	/**
	 * 邀请奖励明细总计
	 * @param map
	 * @return
	 */
	public Map<String, Object> selectInviteAwarDetailSum(Map<String, Object> map);

	/**
	 * 邀请奖励当页总计
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> selectInviteAwarDetailPageSum(Map<String, Object> map);

	/**
	 * 获取第三级层级分类
	 * @return
	 */
	public List<Map<String, Object>> getTreeFlag();

	/**
	 * 优惠卷层次查询
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> selectCouponHierarchyDetail(Map<String, Object> map);

	/**
	 * 优惠卷层次总数查询
	 * @param map
	 * @return
	 */
	public Integer selectCouponHierarchyDetailCount(Map<String, Object> map);

	/**
	 * 新增优惠卷
	 * @param map
	 */
	public void insertCouponHierarchy(Map<String, Object> map);

	
	/**
	 * 修改优惠卷
	 * @param map
	 */
	public void updateCouponHierarchy(Map<String, Object> map);

	/**
	 * 根据id查询第三层成级
	 * @param map
	 * @return
	 */
	public Map<String, Object> selectCouponHierarchyById(Map<String, Object> map);
}
