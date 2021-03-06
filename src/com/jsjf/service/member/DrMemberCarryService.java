package com.jsjf.service.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberCarry;
import com.jsjf.model.system.SysUsersVo;

import net.sf.json.JSONObject;

public interface DrMemberCarryService {
	
	/**
	 * 得到 提现列表列表数据
	 * @param DrMemberCarry
	 * @param PageInfo
	 * @return BaseResult
	 * @throws SQLException
	 */
	public BaseResult getMemberCarryList(DrMemberCarry drMemberCarry,PageInfo pi);
    
	/**
	 * 拒绝
	 * @param DrMemberCarry
	 * @param SysUsersVo
	 * @return void
	 * @throws SQLException
	 */
	public void updateMemberCarryRefuse(int id,SysUsersVo usersVo) throws Exception;
	
	/**
	 * 审核
	 * @param drMemberCarry
	 * @param SysUsersVo
	 * @return DrMemberCarry
	 * @throws SQLException
	 */
	public int updateMemberCarryAudit(DrMemberCarry drMemberCarry,SysUsersVo usersVo) throws Exception;
	
	/**
	 * 发送金运通
	 * @param id
	 * @param SysUsersVo
	 * @return BaseResult
	 * @throws SQLException
	 */
	public BaseResult saveJYTpayment(int id,SysUsersVo usersVo) throws Exception;
	
	/**
	 * 金运通异步通知[成功 ，失败和异常 都通知]
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void
	 * @throws Exception
	 */
	public void receiveNotifyJYT(HttpServletRequest req, HttpServletResponse resp)throws Exception;
	
	/**
	 * 查询代付结果
	 * @throws Exception
	 */
	public void updatePaymentResult() throws Exception;
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return DrMemberCarry
	 * @throws SQLException
	 */
    public DrMemberCarry selectDrMemberCarryById(int id); 
    
    /**
	 * 得到 提现总金额/平台用户账户剩余总额/明日到期总额
	 * @param drMemberCarry
	 * @return
	 */
	public Map<String, Object> getDrMemberCarrySum(DrMemberCarry drMemberCarry,String startDate,String endDate);
	
	public void withdrawalsData(JSONObject message) throws Exception;//提现异步通知
	
	  /**
     * 查询存管未处理的提现订单
     * @return
     */
    public List<DrMemberCarry> selectFuiouByStatus(); 
    
    public void queryFuiouMemberCarryByStatus(DrMemberCarry drMemberCarry) throws SQLException;
    
    /**
     * 提现同步
     * @return
     */
    public BaseResult withdrawals(String str) throws SQLException; 
    
    public DrMemberCarry selectDrMemberCarryByPaymentnum(Map<String, Object> map);
    
    public void depositsMemberCarry(DrMemberCarry drMemberCarry, DrMember member) throws SQLException;
    
    /**
     * 扣除手续费失败重新跑扣除手续费
     */
	public void getSysFuiouNoticeLogByIcd();
}