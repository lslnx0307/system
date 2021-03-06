package com.jsjf.service.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.product.DrProductInvestRepayInfo;
import com.jsjf.model.product.JsNoviceContinueRecord;

import net.sf.json.JSONObject;

public interface DrProductInvestService {

	/**
	 * 拿到投资列表数据
	 * 
	 * @param DrProductInvest
	 * @param PageInfo
	 * @return BaseResult
	 */
	public BaseResult getDrProductInvestList(DrProductInvest drProductInvest, PageInfo pi);
	
	/**
	 * 获取用户周期内投资金额合计
	 * @param period
	 * @return
	 */
	public BigDecimal getDrProductInvestByTime(Integer uid,Integer period);
	
	/**
	 * 投资成功列表
	 * @param map
	 * @param pi
	 * @return
	 */
	public BaseResult selectInvestLogByParam(Map<String, Object> map, PageInfo pi);
	
	/**
	 * 查询有回款记录失败的产品
	 * @return
	 */
	public List<DrProductInfo> selectfailExpireProductInfo(Map<String, Object> map);
	
	/**
	 * 查询回款失败需要再次回款的项目
	 * @return
	 */
	public List<DrProductInfo> selectExpireProductInfo();
	
	/**
	 * 执行产品回款
	 * @throws Exception
	 */
	public void saveInvestRepay(DrProductInfo info) throws Exception;
	/**
	 * 执行恒丰存管项目报备产品回款
	 * @throws Exception
	 */
	public void saveInvestRepayByFuiou(DrProductInfo info) throws Exception;
	
	
	/**
	 * 自动任务：计算好友首投奖励活动
	 * @throws Exception
	 */
	public void updateFriendsFirstInvestmentReward()throws Exception;
	
	/**
	 * 投资订单信息查询
	 * @param map 查询参数
	 * @param pi
	 * @return
	 */
	public PageInfo selectInvestMemberInfoListByParam(Map<String, Object> map, PageInfo pi);
	
	/**
	 * SEM投资订单信息查询
	 * @param map 查询参数
	 * @param pi
	 * @return
	 */
	public PageInfo selectSemInvestList(Map<String, Object> map, PageInfo pi);
	
	/**
	 * 债权匹配
	 */
	public void insertInvestTransfer();
	
	 /**
     * 查询渠道投资记录
     * @param map
     * @return
     */
    public List<Map<String,Object>> QueryChannelInvestList(Map<String,Object> map) throws Exception;
    /**
     * 查询易瑞特渠道投资记录
     * @param map
     * @return
     */
    public List<Map<String,Object>> QueryChannelYRT_InvestList(Map<String,Object> map) throws Exception;
    /**
     *	优惠投资订单查询
     * @param param
     * @param pi
     * @return
     */

	public PageInfo selectActivityInvestListByParam(Map<String, Object> map, PageInfo pi);
	
	/**
	 * 新手标到期续投
	 * @param pi
	 * @param jncr
	 * @throws Exception
	 */
	public void investContinueInvest(DrProductInfo pi,JsNoviceContinueRecord jncr)throws Exception;
	
	/**
	 * 
	 * @param pid
	 * @return
	 */
	public Integer selectInvestCountByPid(Integer pid);
	
	/**
	 * 注册第n天 未投资type = 2 and if(dp.atid or dp.prizeId,0,1)
	 */
	public void marketSMSNotInvest(Integer day,String key,boolean isbatch);
	
	/**
	 * 体验标投资订单信息查询
	 * @param map 查询参数
	 * @param pi
	 * @return
	 */
	public PageInfo selectExperienceInvestMemberInfoListByParam(Map<String, Object> map, PageInfo pi);
	
	/**
	 *复投列表
	 * @param drProductInvest
	 * @param pi
	 * @return
	 */
	public PageInfo getInvestListForFuTou(Map<String, Object> map, PageInfo pi);
	
	/**
	 *根据uid查询列表
	 * @param drProductInvest
	 * @param pi
	 * @return
	 */
	public PageInfo getProductInvestListByUid(Map<String, Object> map, PageInfo pi);
	/**
	 * 自动发标
	 */
	public void toAutoReleaseProduct(Map<String, Object> map);
	/**
	 * 投资完成后回调更新信息
	 * @param map
	 */
	public void updateInvestInfo (JSONObject map);
	
	/**
	 * 回款其他收益发放走营销存管账号
	 */
	public void productOtherInterestDistributeByFuiouMarketingAccount(Map<String,Object> map) throws Exception;
	
	/**
	 * 回款其他收益发放走营销存管账号
	 * @param map
	 * @throws Exception
	 */
	public void cashbackDistributeByFuiouMarketingAccount(Map<String,Object> map) throws Exception;
	
	
	public void updateFileStatus(List<DrProductInvest> param);
	
	public void updateFileStatusRepayInfo(List<DrProductInvestRepayInfo> param);
	
	/**
	 * 根据投资ID查询回款数据
	 * @param map
	 * @return
	 */
    public List<Map<String, Object>> getInvestRepayinfo(Map<String, Object> map);
    
    /**
     * 根据投资ID查询剩余回款合计
     * @param map
     * @return
     */
    public Map<String, Object> getInvestRepayinfoResidueSum(Map<String, Object> map); 
    
    /**
     * 根据投资ID查询已回款合计
     * @param map
     * @return
     */
    public Map<String, Object> getInvestRepayinfoAlreadySum(Map<String, Object> map);
    
    /**
	 * 执行恒丰存管项目报备产品回款
	 * @throws Exception
	 */
	public void saveFailInvestRepayByFuiou(DrProductInfo info) throws Exception;
    /**
     * 获取平台贴息明细
     * @param map
     * @return
     */
    public BaseResult selectActivityBudget(PageInfo info,Map<String,Object> map);
    
    public List<Map<String, Object>> selectActivityBudgetList(Map<String,Object> map);
    
    public void failproductRepay(DrProductInfo info) throws Exception;
	
}
