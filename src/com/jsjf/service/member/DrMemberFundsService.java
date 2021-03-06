package com.jsjf.service.member;


import java.sql.SQLException;

import com.jsjf.model.member.DrMemberFunds;


public interface DrMemberFundsService {

	/**
	 * 根据UID查询用户资金表
	 * @param uid
	 * @return DrMemberFunds
	 */
	public DrMemberFunds selectDrMemberFundsByUid(Integer uid);
	
	/**
	 * 修改
	 * @param DrMemberFunds
	 * @return void
	 * @throws SQLException
	 */
    public void updateDrMemberFunds(DrMemberFunds drMemberFunds) throws SQLException; 
}
