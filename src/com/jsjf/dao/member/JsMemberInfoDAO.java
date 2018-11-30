package com.jsjf.dao.member;

import java.util.List;
import java.util.Map;

import com.jsjf.model.member.JsMemberInfo;

public interface JsMemberInfoDAO {
	
	/**
	 * 查询列表
	 * @param param
	 * @return
	 */
	public List<JsMemberInfo> getJsMemberInfoList(Map<String,Object> param);
	
	/**
	 * 查询总数
	 * @param param
	 * @return
	 */
	public int getJsMemberInfoListCount(Map<String,Object> param);
	
	/**
	 * 导出
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getJsMemberInfoListForExl(Map<String,Object> param);

}
