package com.jsjf.dao.activity;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.DrActivityParameter;

public interface DrActivityParameterDAO {
	
	/**
	 * 新建活动
	 * @param drActivityParameter
	 * @throws SQLException
	 */
	public void insertActivityParameter(DrActivityParameter drActivityParameter)throws SQLException ;
	
	
	/**
	 * 更新活动
	 * @param drActivityParameter
	 * @throws SQLException
	 */
	public void updateActivityParameter(DrActivityParameter drActivityParameter) throws SQLException ;
	
	/**
	 * 查询活动列表
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DrActivityParameter> getDrActivityParameterList(Map<String,Object> map) throws SQLException;
	/**
	 * 查询活动列表总条数
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int getDrActivityParameterTotal(Map<String,Object> map) throws SQLException;
	/**
	 * 查询活动列表统计
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>> getDrActivityParameterListCensus(Map<String,Object> map)throws SQLException;
	
	/**
	 * 根据ID获取活动详情
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public DrActivityParameter getActivityParameterById(Integer id)throws SQLException;
	/**
	 * xi
	 * @param drActivityParameter
	 * @throws SQLException
	 */
	public void toModifyActivity(DrActivityParameter drActivityParameter)throws SQLException;
	/**
	 * 关闭券发放规则中所选的券
	 * @param coupons
	 * @throws SQLException
	 */
	public void updateStatusByRules(String coupons);

	/**
	 * 批量发放加息劵
	 * @param dap
	 */
	public void updateBatchActivityParameter(DrActivityParameter dap);
	/**
	 * 插入好友首投返现到活动账户
	 */
	public void insertFriendMemberActivityAmount();


	/**
	 * 插入好友复投180天投资返现到活动账户
	 */
	public void insertFriendMemberActivityAmountFor180();

	/**
	 * 插入返现到活动账户站内信
	 */
	public void sendJsActivityMemberAccountMsg();
	/**
	 * 修改返现到活动账户为已发送站内信
	 */
	public void updateJsActivityMemberAccount();
	
	public List<DrActivityParameter> selectDrActivityParameterByParam(Map<String,Object> map);
	
	public void insertCouponByFirstInvestLaterdays(@Param("activityId")Integer activityId,@Param("day")Integer day);
	/**
	 * 按activiytId,uids发券
	 * @param map
	 */
	public void insertCouponByParam(Map<String,Object> map);
	
	/**
	 * 插入三重好礼第一次投资(或插入 "好友双重礼"礼 第一重礼,三重礼与双重礼不共存)
	 * 每邀请一位好友首投定期标≥1000元，可获得10元返现，返现无上限！
	 */
	public void insertThreePresentFirst();
	/**
	 * 插入好友三重好礼第二次投资
	 */
	public void insertThreePresentSecond();
	/**
	 * 插入好友三重好礼第三次投资
	 */
	public void insertThreePresentThird();
	/**
	 * 插入 "好友双重礼"礼 第二重礼
	 * 活动期间，您邀请的好友在聚胜平台进行首投后的成功投资，即从好友的第二笔成功投资开始，您就可获得好友投资的佣金返现
	 */
	public void insertTwinPresentSecond();
	
	/**
	 * 三重好礼排行榜
	 * @throws Exception
	 */
	public List<Map<String,Object>> selectThreePresentTop() throws Exception;
	
	/**
	 * 发券--获取券列表
	 * @param map
	 * @return
	 */
	public List<DrActivityParameter> getGiveOutAPList(Map<String,Object> map);
	
	/**
	 * 查询优惠券列表
	 * @param map
	 * @return
	 */
	public List<DrActivityParameter> getActivityParameter(Map<String, Object> map);


	/**
	 * 获取回款方式
	 * @return
	 */
	public List<Map<String, Object>> getRepayType();
	
}
