package com.jsjf.dao.member;

import java.util.List;
import java.util.Map;

import com.jsjf.model.member.JsRewardsDetails;

public interface JsRewardsDetailsDAO {

	/**
	 * 根据map查询
	 * @param id
	 * @return
	 */
	List<Map<String,Object>> selectJsRewardsDetailsByMap(Map<String,Object> map);
	/**
	 * 更新
	 * @param map
	 */
	void update(JsRewardsDetails details);
	/**
	 * 添加权益领取记录
	 * @param jsRewardsDetails
	 */
	void insert(JsRewardsDetails jsRewardsDetails);
}
