package com.jsjf.dao.member;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrMemberCarry;

public interface DrMemberCarryDAO {
	/**
	 * 得到提现记录列表数据
	 * @param Map
	 * @return List<YsCrush>
	 */
    public List<DrMemberCarry> getDrMemberCarryList(Map<String,Object> map); 
    
	/**
	 * 得到到提现记录列表总数
	 * @param Map
	 * @return Integer
	 */
    public Integer getDrMemberCarryCounts(Map<String,Object> map) ; 
    
	/**
	 * 显示提现金额合计
	 * @param Map
	 * @return Integer
	 */
    public Double getDrMemberCarrySum(Map<String,Object> map); 
    
	/**
	 * 修改状态
	 * @param DrMemberCarry
	 * @return void
	 * @throws SQLException
	 */
    public Integer updateStatusById(DrMemberCarry drMemberCarry) throws SQLException; 
    
	/**
	 * 根据ID查询
	 * @param id
	 * @return DrMemberCarry
	 * @throws SQLException
	 */
    public DrMemberCarry selectDrMemberCarryById(int id); 
    
	/**
	 * 根据商户订单号查询
	 * @param paymentNum
	 * @return DrMemberCarry
	 * @throws SQLException
	 */
    public DrMemberCarry selectDrMemberCarryByPaymentNum(String paymentNum); 
    
    
  	/**
  	 * 查询所有处理中的记录
  	 * @param DrMemberCrush
  	 * @return void
  	 * @throws SQLException
  	 */
      public List<DrMemberCarry> getAllDrMemberCarry(); 
      
      public DrMemberCarry selectDrMemberCarryByPaymentnum(Map<String, Object> map);
      
      /**
       * 查询存管未处理的提现订单
       * @return
       */
      public List<DrMemberCarry> selectFuiouByStatus(); 
      
}