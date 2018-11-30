package com.jsjf.service.activity;

import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.activity.JsChannelType;

public interface JsChannelTypeService {

	public BaseResult getChannelTypeList(Map<String, Object> map,PageInfo info);
	
	public void insertChannelType(JsChannelType channelType);
	
	public void updateChannelType(JsChannelType channelType);
}
