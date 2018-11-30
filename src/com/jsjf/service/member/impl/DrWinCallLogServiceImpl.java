package com.jsjf.service.member.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.member.DrWinCallLogDAO;
import com.jsjf.dao.system.JsMerchantMarketingDAO;
import com.jsjf.model.system.JsMerchantMarketing;
import com.jsjf.service.member.DrWinCallLogService;

@Service
@Transactional
public class DrWinCallLogServiceImpl implements DrWinCallLogService {
	@Autowired DrWinCallLogDAO drWinCallLogDAO;
	@Autowired DrMemberDAO drMemberDAO;
	@Autowired JsMerchantMarketingDAO jsMerchantMarketingDAO;
	
	@Override
	public List<Map<String, Object>> selWincallLog(Map<String, Object> param) {
		return drWinCallLogDAO.selWincallLog(param);
	}

	@Override
	public int selWincallLogCount(Map<String, Object> param) {
		return drWinCallLogDAO.selWincallLogCount(param);
	}

	@Override
    public void insert(Map<String, Object> param){
		drWinCallLogDAO.insert(param);
		drMemberDAO.updatelastCallTime(param);
	}

	@Override
	public void update(Map<String, Object> param) {
		drWinCallLogDAO.update(param);		
	}

	@Override
	public PageInfo salesCallsAchievement(Map<String, Object> map) {
		PageInfo info = new PageInfo();
		
		List<Map<String,Object>> rows = drWinCallLogDAO.salesCallsAchievement(map);
//		int total = drWinCallLogDAO.salesCallsAchievementCount(map);// 暂时不分页
		int total = rows.size();
		info.setRows(rows);
		info.setTotal(total);
		
		return info;
	}
	
	@Override
	public BaseResult getFirShotInfo(PageInfo info, Map<String, Object> map) {
		BaseResult br = new BaseResult();
		map.put("offset",info.getPageInfo().getOffset()); 
		map.put("limit",info.getPageInfo().getLimit()); 
		String startDate = map.get("startDate").toString();
		if (!Utils.strIsNull(startDate)) {
			try {
				if (Utils.format(Utils.parse(startDate, "yyyy-MM"), "yyyyMM").equals(Utils.format(new Date(), "yyyyMM"))) {
					map.put("nowDate", Utils.format(new Date(), "yyyyMMdd"));
				}
				map.put("startDate", Utils.format(Utils.parse(startDate, "yyyy-MM"), "yyyyMM"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			map.put("nowDate", Utils.format(new Date(), "yyyyMMdd"));
			map.put("startDate", Utils.format(new Date(), "yyyyMM"));
		}
		List<Map<String,Object>> list = drWinCallLogDAO.getFirShotInfoList(map);
		Integer total = drWinCallLogDAO.getFirShotInfoCount(map);
		info.setTotal(total);
		info.setRows(list);
		map.clear();
		map.put("page", info);
		br.setMap(map);
		return br;
	}

	@Override
	public BaseResult getDoubleShotInfo(PageInfo info, Map<String, Object> map) {
		BaseResult br = new BaseResult();
		map.put("offset",info.getPageInfo().getOffset()); 
		map.put("limit",info.getPageInfo().getLimit()); 
		String startDate = map.get("startDate").toString();
		if (!Utils.strIsNull(startDate)) {
			try {
				if (Utils.format(Utils.parse(startDate, "yyyy-MM"), "yyyyMM").equals(Utils.format(new Date(), "yyyyMM"))) {
					map.put("nowDate", Utils.format(new Date(), "yyyyMMdd"));
				}
				map.put("startDate", Utils.format(Utils.parse(startDate, "yyyy-MM"), "yyyyMM"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			map.put("nowDate", Utils.format(new Date(), "yyyyMMdd"));
			map.put("startDate", Utils.format(new Date(), "yyyyMM"));
		}
		List<Map<String,Object>> list = drWinCallLogDAO.getDoubleShotInfoList(map);
		Integer total = drWinCallLogDAO.getDoubleShotInfoCount(map);
		info.setTotal(total);
		info.setRows(list);
		map.clear();
		map.put("page", info);
		br.setMap(map);
		return br;
	}

	@Override
	public List<Map<String, Object>> exportSalesAchievement(
			Map<String, Object> map) {
		return drWinCallLogDAO.exportSalesAchievement(map);
	}
	@Override
	public BaseResult getTiedCard(PageInfo info, Map<String, Object> map) {
		BaseResult br = new BaseResult();
		map.put("offset",info.getPageInfo().getOffset()); 
		map.put("limit",info.getPageInfo().getLimit()); 
		String startDate = map.get("startDate").toString();
		if (!Utils.strIsNull(startDate)) {
			try {
				if (Utils.format(Utils.parse(startDate, "yyyy-MM"), "yyyyMM").equals(Utils.format(new Date(), "yyyyMM"))) {
					map.put("nowDate", Utils.format(new Date(), "yyyyMMdd"));
				}
				map.put("startDate", Utils.format(Utils.parse(startDate, "yyyy-MM"), "yyyyMM"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			map.put("nowDate", Utils.format(new Date(), "yyyyMMdd"));
			map.put("startDate", Utils.format(new Date(), "yyyyMM"));
		}
		List<Map<String,Object>> list = drWinCallLogDAO.getTiedCard(map);
		Integer total = drWinCallLogDAO.getTiedCardCount(map);
		info.setTotal(total);
		info.setRows(list);
		map.clear();
		map.put("page", info);
		br.setMap(map);
		return br;
	}

	@Override
	public List<Map<String, Object>> exportFirShotInfoList(
			Map<String, Object> param) {
		return drWinCallLogDAO.getFirShotInfoList(param);
	}

	@Override
	public List<Map<String, Object>> exportDoubleShotInfoList(
			Map<String, Object> param) {
		return drWinCallLogDAO.getDoubleShotInfoList(param);
	}

	@Override
	public List<Map<String, Object>> exportTiedCard(Map<String, Object> param) {
		return drWinCallLogDAO.getTiedCard(param);
	}

	@Override
	public void testInsert(JsMerchantMarketing obj) {
		jsMerchantMarketingDAO.insert(obj);
		
	}

	@Override
	public void testInsert1(List<JsMerchantMarketing> list) {
		jsMerchantMarketingDAO.insertBatch(list);
		
	}

	
}
