package com.jsjf.service.member;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBaseInfo;
import com.jsjf.model.system.JsMerchantMarketing;

public interface DrWinCallLogService {
	
	public List<Map<String, Object>> selWincallLog(Map<String, Object> param);
    
    public int selWincallLogCount(Map<String, Object> param);
    
    public void insert(Map<String, Object> param);
    
    public void update(Map<String, Object> param);
    
    /**
     * 电销业绩核算
     * @param map
     * @return
     */
    public PageInfo salesCallsAchievement(Map<String,Object> map);
    /**
     * 电销业绩核算导出
     * @param map
     * @return
     */
    public List<Map<String,Object>> exportSalesAchievement(Map<String,Object> map);
 
    /**
     * 条件查询首投明细
     * @param pi
     * @param param
     * @return
     */
    public BaseResult getFirShotInfo(PageInfo pi,Map<String,Object> param);
    /**
     * 条件查询复投明细
     * @param pi
     * @param param
     * @return
     */
    public BaseResult getDoubleShotInfo(PageInfo pi,Map<String,Object> param);
    /**
     * 条件查询绑卡明细
     * @param pi
     * @param param
     * @return
     */
    public BaseResult getTiedCard(PageInfo pi,Map<String,Object> param);
   
    /**
     * 查询首投明细
     * @param param
     * @return
     */
    public List<Map<String,Object>> exportFirShotInfoList(Map<String, Object> param);
    /**
     * 查询复投明细
     * @param param
     * @return
     */
    public List<Map<String,Object>> exportDoubleShotInfoList(Map<String, Object> param);
    /**
     * 查询绑卡明细
     * @param pi
     * @param param
     * @return
     */
    public List<Map<String,Object>> exportTiedCard(Map<String,Object> param);
    
    
    public void testInsert(JsMerchantMarketing obj);
    
    public void testInsert1(List<JsMerchantMarketing> list);
    
}
