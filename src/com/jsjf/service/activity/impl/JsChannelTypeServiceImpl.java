package com.jsjf.service.activity.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.activity.JsChannelTypeDAO;
import com.jsjf.model.activity.JsChannelType;
import com.jsjf.service.activity.JsChannelTypeService;

@Service
@Transactional
public class JsChannelTypeServiceImpl implements JsChannelTypeService{

	@Autowired
	private JsChannelTypeDAO jsChannelTypeDAO;
	@Override
	public BaseResult getChannelTypeList(Map<String, Object> map, PageInfo info) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		map.put("limit",info.getPageInfo().getLimit());
		map.put("offset",info.getPageInfo().getOffset());
		List<Map<String,Object>> list =  jsChannelTypeDAO.getChannelTypeList(map);
		Integer total =  jsChannelTypeDAO.getChannelTypeCount(map);
		info.setTotal(total);
		info.setRows(list);
		resultMap.put("page", info);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
	
	@Override
	public void insertChannelType(JsChannelType channelType) {
		jsChannelTypeDAO.insertChannelType(channelType);
	}
	
	@Override
	public void updateChannelType(JsChannelType channelType) {
		jsChannelTypeDAO.updateChannelType(channelType);
	}

}
