package com.jsjf.service.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface JsMemberGradeService {


	/**
	 * 获取会员等级数据
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getMemberGrade();

	/**
	 * 增加用户财富值
	 * 1.增加财富值  
	 * 		1.查询任务详情 2.获取财富值历史记录 3.是否增加财富值 4.记录财富值历史记录
	 * 2.升级
	 * 		1.会员表等级修改	
	 * 
	 * @param param
	 */
	public void incrWealth(Map<String,Object> param) throws Exception;

}
