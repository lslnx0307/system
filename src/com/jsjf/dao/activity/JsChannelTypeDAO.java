package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.JsChannelType;

public interface JsChannelTypeDAO {
	
	public List<Map<String, Object>> getChannelTypeList(Map<String, Object> map);
	
	public int getChannelTypeCount(Map<String, Object> map);
	
	public void insertChannelType(JsChannelType channelType);
	
	public void updateChannelType(JsChannelType channelType);

}
