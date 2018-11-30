package com.jsjf.dao.member;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMember;

public interface DrWinCallLogDAO {
    
	public List<Map<String, Object>> selWincallLog(Map<String, Object> param);
    
    public int selWincallLogCount(Map<String, Object> param);
    
    public void insert(Map<String, Object> param);
    
    public void update(Map<String, Object> param);
    
    /**
     * 电销业绩核算
     * @param map
     * @return
     */
    public List<Map<String,Object>> salesCallsAchievement(Map<String,Object> map);
    
    /**
     * 电销业绩 人员数
     * @param map
     * @return
     */
    public int salesCallsAchievementCount(Map<String,Object> map);
    /**
     * 导出电销业绩核算
     * @param map
     * @return
     */
    public List<Map<String,Object>> exportSalesAchievement(Map<String,Object> map);
    
    /**
     * 查询首投明细
     * @param param
     * @return
     */
    public List<Map<String,Object>> getFirShotInfoList(Map<String, Object> param);
    /**
     * 查询首投count
     * @param param
     * @return
     */
    public Integer getFirShotInfoCount(Map<String, Object> param);
    /**
     * 查询复投明细
     * @param param
     * @return
     */
    public List<Map<String,Object>> getDoubleShotInfoList(Map<String, Object> param);
    /**
     * 查询复投count
     * @param param
     * @return
     */
    public Integer getDoubleShotInfoCount(Map<String, Object> param);
    /**
     * 查询绑卡明细
     * @param pi
     * @param param
     * @return
     */
    public List<Map<String,Object>> getTiedCard(Map<String,Object> param);
    /**
     * 查询绑卡明细count
     * @param param
     * @return
     */
    public Integer getTiedCardCount(Map<String,Object> param);
    
}
