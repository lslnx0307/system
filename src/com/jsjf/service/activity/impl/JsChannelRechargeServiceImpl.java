package com.jsjf.service.activity.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.JsChannelRechargeDao;
import com.jsjf.service.activity.JsChannelRechargeService;

@Service
@Transactional
public class JsChannelRechargeServiceImpl implements JsChannelRechargeService {
	
	@Autowired
	private JsChannelRechargeDao jsChannelRechargeDao; 

	@Override
	public BaseResult getJsChannelRecharge(Map<String, Object> param, PageInfo pi) {
		BaseResult br = new BaseResult();
		try {
			param.put("offset", pi.getPageInfo().getOffset());
			param.put("limit", pi.getPageInfo().getLimit());
			List<Map<String,Object>> dataList = jsChannelRechargeDao.selectList(param);
			int total = jsChannelRechargeDao.selectListCount(param);
			List<Map<String,Object>> footer = jsChannelRechargeDao.selectListAmountSum(param);
			pi.setTotal(total);
			pi.setRows(dataList);
			pi.setFooter(footer);
			param.clear();
			param.put("page",pi);
			br.setMap(param);
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorCode("9999");
			br.setErrorMsg("系统错误");
		}
		return br;
	}

	@Override
	public BaseResult insert(Map<String, Object> param) {
		BaseResult br = new BaseResult();
		try {
			Integer channel_id = Integer.parseInt(String.valueOf(param.get("channel_id")));
			List<Map<String,Object>> list = jsChannelRechargeDao.selectChannelRechargeByChannelName(channel_id);
			if(Utils.isEmptyList(list)){
				param.put("balance", param.get("recharge_amount"));
			}else{
				param.put("balance", Utils.nwdBcadd(param.get("recharge_amount"),list.get(0).get("balance")));
			}
			jsChannelRechargeDao.insert(param);
			br.setSuccess(true);
			br.setMsg("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("系统错误！");
			br.setErrorCode("9999");
		}
		return br;
		
	}

}
