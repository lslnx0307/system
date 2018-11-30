package com.jsjf.service.activity.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.HierarchicalStructureDAO;
import com.jsjf.dao.activity.JChannelClearingDAO;
import com.jsjf.service.activity.HierarchicalStructureService;

@Service
@Transactional
public class HierarchicalStructureServiceImpl implements HierarchicalStructureService{

	@Autowired
	private HierarchicalStructureDAO hierarchicalStructureDAO;
	@Autowired
	private JChannelClearingDAO jChannelClearingDAO;
	
	@Override
	public List<Map<String, Object>> selectHierarchicalStructure(Map<String, Object> map) {
		return hierarchicalStructureDAO.selectHierarchicalStructure(map);
	}
	@Override
	public BaseResult getInviteAwarDetailList(Map<String, Object> param, PageInfo pi) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
//		map.put("projectNo", param.get("projectNo"));
//		map.put("three_flag", param.get("three_flag").toString());
//		map.put("simpleName", param.get("simpleName").toString());
//		map.put("startDate",param.get("startDate"));
//		map.put("endDate", param.get("endDate"));
		param.put("limit",pi.getPageInfo().getLimit());
		param.put("offset",pi.getPageInfo().getOffset());
		List<Map<String,Object>> list =  hierarchicalStructureDAO.getInviteAwarDetailList(param);
		Integer total =  hierarchicalStructureDAO.getInviteAwarDetailListCount(param);
		Map<String,Object> allSum = hierarchicalStructureDAO.selectInviteAwarDetailSum(param);
		List<Map<String,Object>> footer = hierarchicalStructureDAO.selectInviteAwarDetailPageSum(param);
		pi.setTotal(total);
		pi.setRows(list);
		footer.add(allSum);
		pi.setFooter(footer);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
	
	@Override
	public BaseResult selectCouponDetail(Map<String, Object> map,PageInfo page) {
		BaseResult br = new BaseResult();
		Map<String,Object> result = new HashMap<>();
		try {
			map.put("offset", page.getPageInfo().getOffset());
			map.put("limit", page.getPageInfo().getLimit());
			List<Map<String,Object>> list = hierarchicalStructureDAO.selectCouponDetail(map);
			int total = hierarchicalStructureDAO.selectCouponDetailCount(map);
			Map<String,Object> allSum = hierarchicalStructureDAO.selectCouponDetailSum(map);
			List<Map<String,Object>> footer = hierarchicalStructureDAO.selectCouponDetailPageSum(map);
			page.setTotal(total);
			page.setRows(list);
			footer.add(allSum);
			page.setFooter(footer);
			result.put("page", page);
			br.setSuccess(true);
			br.setMap(result);
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorCode("9999");
			br.setErrorMsg("系统错误");
		}
		return br;
	}
	
	@Override
	public BaseResult getActivityBudget(Map<String, Object> map,PageInfo info) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		map.put("offset", info.getPageInfo().getOffset());
		map.put("limit",  info.getPageInfo().getLimit());
		List<Map<String, Object>> list=hierarchicalStructureDAO.getActivityBudget(map);
		Integer count=hierarchicalStructureDAO.getActivityBudgetCount(map);
		List<Map<String,Object>> footer = hierarchicalStructureDAO.getActivityBudgetSum(map);

		info.setTotal(count);
		info.setRows(list);
		info.setFooter(footer);
		resultMap.put("page", info);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public List<Map<String, Object>> exportCouponDetail(Map<String, Object> map) {
		return hierarchicalStructureDAO.selectCouponDetail(map);
		
	}
	@Override
	public List<Map<String, Object>> exportInviteAwarDetail(Map<String, Object> map) {
		
		return hierarchicalStructureDAO.getInviteAwarDetailList(map);
	}
	@Override
	public List<Map<String,Object>> getTreeFlag() {
		// TODO Auto-generated method stub
		return hierarchicalStructureDAO.getTreeFlag();
	}
	
	@Override
	public BaseResult getChannelClearingList(Map<String, Object> map,PageInfo info) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		map.put("offset", info.getPageInfo().getOffset());
		map.put("limit",  info.getPageInfo().getLimit());
		List<Map<String, Object>> list=jChannelClearingDAO.getChannelClearingList(map);
		Integer count=jChannelClearingDAO.getChannelClearingListCount(map);
		List<Map<String,Object>> footer = jChannelClearingDAO.getChannelClearingListSum(map);

		info.setTotal(count);
		info.setRows(list);
		info.setFooter(footer);
		resultMap.put("page", info);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
	@Override
	public BaseResult selectCouponHierarchyDetail(PageInfo pi, Map<String, Object> map) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit",  pi.getPageInfo().getLimit());
		List<Map<String, Object>> list=hierarchicalStructureDAO.selectCouponHierarchyDetail(map);
		Integer count=hierarchicalStructureDAO.selectCouponHierarchyDetailCount(map);
		pi.setTotal(count);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
	@Override
	public void insertCouponHierarchy(Map<String, Object> map) {
		hierarchicalStructureDAO.insertCouponHierarchy(map);
	}
	@Override
	public void updateCouponHierarchy(Map<String, Object> map) {
		
		hierarchicalStructureDAO.updateCouponHierarchy(map);
	}
	@Override
	public Map<String, Object> selectCouponHierarchyById(Map<String, Object> map) {
		return hierarchicalStructureDAO.selectCouponHierarchyById(map);
	}

}
	