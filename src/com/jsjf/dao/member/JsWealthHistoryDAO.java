package com.jsjf.dao.member;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.member.JsWealthHistory;

public interface JsWealthHistoryDAO {
	/**
	 * 根据Id获取产数
	 * @param id
	 * @return
	 */
	public JsWealthHistory selectObjectById(Integer id);
	/**
	 * 根据tdid获取
	 * @param id
	 * @return
	 */
	public JsWealthHistory selectObjectByTdid(@Param(value="tdid")Integer tdid,@Param(value="type")Integer type);
	/**
	 * 根据Map获取
	 * @param id
	 * @return
	 */
	public int selectObjectByMap(Map<String,Object> map);
	/**
	 * 根据Map获取总数
	 * @param id
	 * @return
	 */
	public int selectObjectCountByMap(Map<String,Object> map);
	
	
	/**
	 * 添加
	 * @param obj
	 */
	public void insert(JsWealthHistory obj);
	/**
	 * 查找财富值总值
	 * @param obj
	 */
	public Integer selectWealthSum(@Param(value="uid")Integer uid);
}
