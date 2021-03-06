package com.jsjf.service.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBaseInfo;
import com.jsjf.model.system.SysFuiouNoticeLog;

public interface DrMemberService {
	
	/**
	 * 查询会员
	 * @param member
	 * @param PageInfo pi
	 * @param flag true=会员管理    false=客服页面查询
	 * @return PageInfo
	 */
    public PageInfo selectDrMemberList(DrMember member,PageInfo pi,boolean flag);
	/**
	 * 根据uid查询
	 * @param uid 会员id
	 * @return DrMember
	 */
    public DrMember queryDrMemberByUid(Integer uid);
    
    /**
     * 根据uid获取推荐人信息
     * @param uid 用户ID
     * @return
     */
    public DrMember queryReferrerMemberByUid(Integer uid);  
    
    
	/**
	 * 投资订单合计信息查询
	 * @param map 查询参数
	 * @param pi
	 * @return
	 */
	public Map<String,Object> selectMemberInfoDataSum(Map<String,Object> para);
    
    public PageInfo selectRegMemberInfoByParam(Map<String, Object> map, PageInfo pi);
    
    public DrMemberBaseInfo selectByParam(Map<String,Object> param);
    
    public List<DrMemberBaseInfo> selectByCard(Map<String,Object> map);
    
  	public List<Map<String, Object>> selectPersonRegBatchUpload();
    
    public List<Map<String, Object>> queryMember(Map<String, Object> param);
    
    public void updateFileStatus(List<DrMember> list);
    
    public int queryMemberCount(Map<String, Object> param);
    
	public List<Map<String, Object>> selCustomerManagement(Map<String, Object> param);
    
    public int selCustomerManagementCount(Map<String, Object> param);
    
    /**
	 * 通过用户UID获取用户信息
	 * @param uid
	 * @return
	 */
    public  DrMember selectByPrimaryKey(Integer uid);
    /**
     * 查询选择发放的用户列表
     * @param map
     * @param pi
     * @return
     */
    public PageInfo selectGiveRegMemberInfoByParam(Map<String, Object> map, PageInfo pi);
    
    /**
     * 注册第2天 -未绑卡
     */
    public void SMSMarketNoTCertified(String key);
    
    /**
     * 给电销部门分配客户
     */
    public void allotCustomer();
    
    /**
     * 根据手机号获取推荐人信息
     * @param mobilePhone
     * @return
     */
    public DrMember selectReferrerMemberByMobilePhone(Map<String, Object> map);
    /**
	 * 根据手机号查询被邀请人信息
	 * @param mobilePhone
	 * @return
	 */
	public PageInfo selectdrMemberByMobilePhone(DrMember drMember,PageInfo pi);
	/**
	 * 给客户分配部门
	 * @param map
	 */
	public void updateMemberAllot(Map<String,Object> map);
	/**
	 * 营销防流失提醒
	 */
	public void customerLossPrevention();	
	/**
	 * 营销防流失提醒2
	 */
	public void customerLossPrevention2();	
	/**
	 * 修改用户
	 * @param drMember
	 */
	public void updateDrmember(DrMember drMember);
	/**
	 * 根据手机号查用户
	 * @return
	 */
	public DrMember selectDrMemberByMobilePhone(String mobilePhone);
	
	/**
	 * 个人开户
	 * @param message
	 * @throws Exception
	 */
	public void openAccountRes(JSONObject message) throws Exception;
	
	
	public BaseResult selectFuiouByPrimaryKey(DrMember drMember) throws Exception;
	
	public void queryChangeCard(SysFuiouNoticeLog fuiouNoticeLog);
	
	public DrMember selectByMobilephone(DrMember drMember);
	
	/**
	 * 年末红包领取期限到期前7天发送手机短信提醒
	 */
	public void sendYearPresentSMS();
	
	/**
	 * 生日红包领取期限到期前7天发送手机短信提醒
	 */
	public void sendBirthdayPresentSMS();
	/**
	 *  上线注册未使用体验金和未绑卡
	 */
	public void sendNoBbinNoCard(String noBbinNoCard);
	/**
	 * 上线注册已使用已绑卡为投资
	 * @param string
	 */
	public void sendNoInvest(String string);
	/**
	 * 查询获得奖励资格的用户
	 * @throws SQLException 
	 */
	public void selectQualificationMember() throws SQLException;
    
}
