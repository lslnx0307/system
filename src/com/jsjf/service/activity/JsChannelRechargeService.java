package com.jsjf.service.activity;

import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;

public interface JsChannelRechargeService {

	/***
	 * 渠道充值列表
	 * @param param
	 * @param pi
	 * @return
	 */
	public BaseResult getJsChannelRecharge(Map<String,Object> param,PageInfo pi);
	
	public BaseResult insert(Map<String,Object> param);
}
