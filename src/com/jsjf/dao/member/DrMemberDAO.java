package com.jsjf.dao.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.member.DrMember;

public interface DrMemberDAO {
	
	/**
	 * 添加会员
	 * @param member
	 * @return
	 */
	Integer insert(DrMember member);
	
	/**
	 * 通过用户UID获取用户信息
	 * @param uid
	 * @return
	 */
	DrMember selectByPrimaryKey(Integer uid);
	
	/**
	 * 通过uid查询
	 * @param uid
	 * @return
	 */
	public DrMember selectByUid(Integer uid);
	
    /**
     * 查询会员信息
     * @param map
     * @return List<DrMember>
     */
    public List<DrMember> selectDrMemberList(Map<String,Object> map);
    
    /**
     * 会员总数
     * @param map
     * @return Integer
     */
    public Integer selectDrMemberListCount(Map<String,Object> map);
    
    /**
     * 修改会员信息
     * @param drMember
     */
    public int updateDrMemberByUid(DrMember drMember);
    
    /**
     * 通过用户UID获取推荐人用户信息
     * @param map
     * @return
     */
    public DrMember selectOnlyOneMember(Integer uid);
    
    /**
     * 查询注册用户信息
     * @param param
     * @return
     */
    public List<Map<String, Object>> selectRegMemberInfoListByParam(Map<String, Object> param);
    /**
     * #合计 ：**个渠道，***个投资用户，*** 个绑卡用户，****元投资 
     * @param map
     * @return
     */
    public Map<String,Object> selectMemberInfoDataSum(Map<String,Object> para);
    
    public Integer selectRegMemberInfoListCountByParam(Map<String, Object> param);
    
    public List<Map<String, Object>> queryMember(Map<String, Object> param);
    
    public int queryMemberCount(Map<String, Object> param);
    
	public List<Map<String, Object>> selCustomerManagement(Map<String, Object> param);
	
	public void updateFileStatus(List<DrMember> param);
    
    public int selCustomerManagementCount(Map<String, Object> param);
    
    /**
     * 查询选择发放用户
     * @param map
     * @return
     */
	public List<Map<String, Object>> selectGiveRegMemberInfoListByParam(Map<String, Object> map);
	/**
	 * 查询选择发放用户总数
	 * @param map
	 * @return
	 */
	public Integer selectGiveRegMemberInfoListCountByParam(Map<String, Object> map);
	
	/**
	 * 获取能领取压岁钱但未领取的用户
	 * @return
	 */
	public List<String> selectLuckyMoney();
	
	/**
	 * 发送体验金过期信息
	 * @return
	 */
	public List<Map<String,Object>> selectTiYanJinGuoQi();
	
	/**
	 * //注册第2天 -未绑卡
	 * @return
	 */
	public  String[] selectNotCertified();
	
	/**
	 * 按渠道查询待分配的客户,
	 * @return
	 */
	public List<Map<String,Object>> selectdrMemberGroupByChannle();
	
	/**
	 * 根据手机号或者推荐码查询
	 * @param mobilePhone
	 * @return
	 */
	public List<DrMember> selectdrMemberByMobile(Map<String, Object> map);
    
	/**
	 * 根据手机号查询count
	 * @param mobilePhone
	 * @return
	 */
	public Integer selectdrMemberByMobilePhoneCount(Map<String, Object> map);
	/**
	 * 分配部门
	 * @param map
	 */
	public void updateMemberAllot(Map<String,Object> map);
	/**
	 * 未登录4天
	 * @param map
	 * @return
	 */
	public String[] gt4DayNotlogged(@Param(value="sign") Integer sign);
	/**
	 * 未登录7天
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> gt7DayNotlogged(@Param(value="sign") Integer sign);
	/**
	 * 未登录7天
	 * @param map
	 * @return
	 */
	public String[] couponGived4NotUse(@Param(value="name") String name);
	/**
	 * 根据手机号或者推荐码查询推荐人信息
	 * @param mobilePhone
	 * @return
	 */
	public DrMember selectReferrerMemberByMobilePhone(Map<String, Object> map);
	/**
	 * 根据手机号查用户
	 * @param mobilePhone
	 * @return
	 */
	public DrMember selectDrMemberByMobilePhone(String mobilePhone);
	/**
	 * 根据恒丰user_id查询用户
	 * @param mobilePhone
	 * @return
	 */
	public DrMember selectDrMemberByUserId(String userId);
    
	/**
	 * 根据渠道号查询用户
	 * @return
	 */
	public List<DrMember> selectDrMemberByChannel(Map<String,Object> param);
	public Integer selectDrMemberByChannelCount(Map<String,Object> param);
	
	public void updatelastCallTime(Map<String,Object> param);
	
	/**
	 * 用户基础资料
	 * @param param
	 * @return
	 */
	public List<DrMember> queryDrMemberByMobilePhone(Map<String,Object> param);
	
	public DrMember selectByMobilephone(DrMember drMember);
	
	/**
	 * 年末红包领取期限到期前7天发送手机短信提醒
	 */
	public List<Map<String, Object>> sendYearPresentSMS();
	
	/**
	 * 生日礼包领取期限到期前7天发送手机短信提醒
	 */
	public List<Map<String, Object>> sendBirthdayPresentSMS();

	/**
	 * 注册两天未使用体验金未绑卡
	 * @return
	 */
	public String[] selectNoBbinNoCard();

	/**
	 * 绑卡为投资
	 * @return
	 */
	public String[] sendNoInvest();

	/**
	 * 查询最高投资额奖励资格用户
	 * @param drActivityMap 
	 * @return
	 */
	List<Map<String, Object>> selectQualificationMember(Map<String, Object> drActivityMap);

	/**
	 * 查询最晚投资资格用户
	 * @param drActivityMap 
	 * @return
	 */
	List<Map<String, Object>> selectLastInvestMember(Map<String, Object> drActivityMap);
}
