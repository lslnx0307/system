package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.JsActivityAwardLog;

public interface JsActivityAwardLogDAO {

	/**
	 * 插入奖励日志表
	 * @param jsActivityAwardLog 
	 */
	void insert(JsActivityAwardLog jsActivityAwardLog);

	/**
	 * 根据uid查询记录表
	 * @param uid
	 * @return
	 */
	List<JsActivityAwardLog> selectByUid(@Param("uid")int uid, @Param("awardRule")int awardRule,@Param("pid") int pid);

	/**
	 * 根据pid查询
	 * @param pid
	 * @return
	 */
	List<JsActivityAwardLog> selectByPid(@Param("pid")int pid);
	/**
	 * 根据map查询
	 * @param map
	 * @return
	 */
	List<JsActivityAwardLog> selectByMap(Map<String,Object> map);
	
}
