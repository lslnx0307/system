package com.jsjf.dao.member;

import java.util.List;
import java.util.Map;

import com.jsjf.model.member.JsTaskDetails;

public interface JsTaskDetailsDAO {
	
	public JsTaskDetails selectObjectById(Integer id);
	/**
	 * 添加
	 * @param jsTaskDetails
	 */
	void insert(JsTaskDetails jsTaskDetails);
	
	
	/**
	 * 根据map查询会员任务详情
	 * @param map
	 * @return
	 */
	List<JsTaskDetails> getJstaskDetailByMap(Map<String,Object> map);
}
