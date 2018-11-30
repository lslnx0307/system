package com.jsjf.dao.member;

import java.util.List;
import java.util.Map;

import com.jsjf.model.member.JsMemberTask;
import com.jsjf.model.member.JsTaskDetails;

public interface JsMemberTaskDAO {
	
	public JsMemberTask selectObjectById(Integer id);
	
	/**
	 * 获取单表list
	 * @param map
	 * @return
	 */
	public List<JsMemberTask> selectjsmembTasksList(Map<String,Object> map);
	
    /**
     * 根据uid查询充值记录
     * @param map
     * @return
     */
    public Integer getCrushCount(Integer uid);    
    
	/**
	 * 根据推荐人uid查询推荐人数
	 * @param rid
	 * @return
	 */
	public Integer getRecommendedCountByUid(Integer uid);
	
	/**
	 * 根据map查询除新手标、体验标以外标的数量
	 * @return
	 */
	public Map<String,Object> getInvestCount(Map<String,Object> map);
	
	/**
	 * 查询邀请好友投资了指定标的人
	 * @param uid
	 * @return
	 */
	public Map<String,Object> getRecommendedInvestCount(Map<String,Object> map);
	

}
