package com.jsjf.service.member.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.member.DrMemberFundsDAO;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.service.member.DrMemberFundsService;

@Service
@Transactional
public class DrMemberFundsServiceImpl implements DrMemberFundsService {
	
	@Autowired
	private DrMemberFundsDAO drMemberFundsDAO;
	
	@Override
	public DrMemberFunds selectDrMemberFundsByUid(Integer uid) {
		return drMemberFundsDAO.queryDrMemberFundsByUid(uid);
	}

	@Override
	public void updateDrMemberFunds(DrMemberFunds drMemberFunds)throws SQLException {
		drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);
	}

}
