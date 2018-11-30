package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.JsAnniversaryShare;

public interface DrActivityDAO {
	/**
	 * 周年庆活动投资记录
	 * @return
	 */
	public List<JsAnniversaryShare> selectJsAnniversaryShares(Map<String,Object> map);
	
	/**
	 * 修改
	 * @param obj
	 */
	public void updateJsAnniversaryShares(JsAnniversaryShare obj);
	/**
	 * 双11获取往期排行榜
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> anniversaryInvestTop (Map<String,Object> map);

	/**
	 * 根据name查询活动表
	 * @param name
	 * @return
	 */
	public Map<String, Object> selectByName(@Param("activityName")String activityName);

}
