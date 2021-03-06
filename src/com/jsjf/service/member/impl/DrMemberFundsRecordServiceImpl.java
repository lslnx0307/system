package com.jsjf.service.member.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.member.DrMemberFundsRecordDAO;
import com.jsjf.model.member.DrMemberFundsRecord;
import com.jsjf.service.member.DrMemberFundsRecordService;

@Service
public class DrMemberFundsRecordServiceImpl implements
		DrMemberFundsRecordService {
	@Autowired
	private DrMemberFundsRecordDAO drMemberFundsRecordDAO;

	@Override
	public BaseResult getDrMemberFundsRecordList(Map<String, Object> map,PageInfo pi) {
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<DrMemberFundsRecord> list = drMemberFundsRecordDAO.getDrMemberFundsRecordList(map);
		Integer total = drMemberFundsRecordDAO.getDrMemberFundsRecordTotal(map);
		map.clear();
		pi.setTotal(total);
		pi.setRows(list);
		map.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(map);
		return br;
	}

	@Override
	public List<Map<String, Object>> getFundsRecord(Map<String, Object> map) {
		return drMemberFundsRecordDAO.getFundsRecord(map);
	}

	@Override
	public int getFundsRecordCount(Map<String, Object> map) {
		return drMemberFundsRecordDAO.getFundsRecordCount(map);
	}

	@Override
	public BigDecimal getFundsRecordSRSum(Map<String, Object> map) {
		return drMemberFundsRecordDAO.getFundsRecordSRSum(map);
	}

	@Override
	public BigDecimal getFundsRecordZCSum(Map<String, Object> map) {
		return drMemberFundsRecordDAO.getFundsRecordZCSum(map);
	}

}
