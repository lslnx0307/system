package com.jsjf.service.cpa.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.cpa.DrChannelInfoDAO;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.model.cpa.DrChannelInfo;
import com.jsjf.model.member.DrMember;
import com.jsjf.service.cpa.DrChannelInfoService;
import com.jytpay.utils.StringUtil;

@Service
@Transactional
public class DrChannelInfoServiceImpl implements DrChannelInfoService{
	@Autowired
	private DrChannelInfoDAO drChannelInfoDAO;
	@Autowired
	private DrMemberDAO drMemberDAO;
	
	@Override
	public BaseResult getDrChannelInfoList(DrChannelInfo drChannelInfo,PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", drChannelInfo.getName());
		map.put("code", drChannelInfo.getCode());
		map.put("channelTypeId", drChannelInfo.getChannelTypeId());
		map.put("status", drChannelInfo.getStatus());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<DrChannelInfo> list = drChannelInfoDAO.getDrChannelInfoList(map);
		Integer total = drChannelInfoDAO.getDrChannelInfoCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
	
	@Override
	public BaseResult getDrChannelInfoUserList(DrChannelInfo drChannelInfo,PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map = getCommonMap(drChannelInfo);
		/*map.put("mobilephone", drChannelInfo.getMobilephone());
		map.put("code", drChannelInfo.getCode());
		map.put("startDate", drChannelInfo.getStartDate());
		map.put("endDate", drChannelInfo.getEndDate());*/
		map.put("isbank", drChannelInfo.getIsbank());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<DrChannelInfo> list = drChannelInfoDAO.getDrChannelInfoUserList(map);
		Integer total = drChannelInfoDAO.getDrChannelInfoUserCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
	@Override
	public BaseResult getDrChannelInfoOrderList(DrChannelInfo drChannelInfo,
			PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map = getCommonMap(drChannelInfo);
		/*map.put("mobilephone", drChannelInfo.getMobilephone());
		map.put("code", drChannelInfo.getCode());
		map.put("startDate", drChannelInfo.getStartDate());
		map.put("endDate", drChannelInfo.getEndDate());*/
		map.put("startInvestDate", drChannelInfo.getStartInvestDate());
		map.put("endInvestDate", drChannelInfo.getEndInvestDate());
		map.put("isbank",drChannelInfo.getIsbank());
		map.put("deadline",drChannelInfo.getDeadline());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit());
		map.put("cids",drChannelInfo.getCids());
		map.put("investId",drChannelInfo.getInvestId());
		if(drChannelInfo.getRepayType()!=null){
			map.put("repayType",drChannelInfo.getRepayType());
		}
		List<DrChannelInfo> list = drChannelInfoDAO.getDrChannelInfoOrderList(map);
		Integer total = drChannelInfoDAO.getDrChannelInfoOrderCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
	@Override
	public void insertDrChannelInfo(DrChannelInfo drChannelInfo)
			throws SQLException {
		drChannelInfoDAO.insertDrChannelInfo(drChannelInfo);
	}

	@Override
	public void updateDrChannelInfo(DrChannelInfo drChannelInfo)
			throws SQLException {
		drChannelInfoDAO.updateDrChannelInfo(drChannelInfo);
	}

	@Override
	public DrChannelInfo getDrChannelInfoByid(Integer id) {
		return drChannelInfoDAO.getDrChannelInfoByid(id);
	}
	
	@Override
	public List<DrChannelInfo> getDrChannelInfoListForMap(Map<String,Object> map) {
		return drChannelInfoDAO.getDrChannelInfoListForMap(map);
	}

	@Override
	public Map<String, Object> exportChannelUser(DrChannelInfo drChannelInfo)
			throws Exception {
		Map<String,Object> map = getCommonMap(drChannelInfo);
		map.put("excelName", "channel_user_record"+System.currentTimeMillis()+".xls");
		/*map.put("mobilephone", drChannelInfo.getMobilephone());
		map.put("cids",cids);
		map.put("startDate", drChannelInfo.getStartDate());
		map.put("endDate", drChannelInfo.getEndDate());*/
		map.put("isbank", drChannelInfo.getIsbank());
		List<DrChannelInfo> list = drChannelInfoDAO.getDrChannelInfoUserList(map);
		String[] title = new String[]{"序号","客户姓名","手机号码","注册时间","渠道名称","绑卡信息","渠道编号"};
		Integer[] columnWidth = new Integer[]{5,20,20,20,20,20,20};
		
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		int i=1;
		for(DrChannelInfo info:list){
			lc = new ArrayList<Object>();
			lc.add(i++);
			lc.add(info.getRealName() == null ? "":info.getRealName());
			lc.add(info.getMobilephone());
			lc.add(Utils.getparseDate(info.getRegDate(), "yyyy-MM-dd HH:mm:ss"));
			lc.add(info.getName() == null ? "":info.getName());
			if(Utils.isObjectEmpty(info.getRealName())){
				lc.add("否");
			}else{
				lc.add("是");
			}
			lc.add(info.getCode() == null ?"":info.getCode());
			tableList.add(lc);
		}
		
		map.put("titles", title);
		map.put("columnWidth", columnWidth);
		map.put("list", tableList);
		return map;
	}

	@Override
	public Map<String, Object> exportChannelOrder(DrChannelInfo drChannelInfo)
			throws Exception {
		Map<String,Object> map = getCommonMap(drChannelInfo);
		map.put("excelName", "channel_order_record"+System.currentTimeMillis()+".xls");
		/*map.put("mobilephone", drChannelInfo.getMobilephone());
		map.put("cids", drChannelInfo.getCids());
		map.put("startDate", drChannelInfo.getStartDate());
		map.put("endDate", drChannelInfo.getEndDate());*/
		map.put("startInvestDate", drChannelInfo.getStartInvestDate());
		map.put("endInvestDate", drChannelInfo.getEndInvestDate());
		map.put("isbank", drChannelInfo.getIsbank());
		map.put("deadline",drChannelInfo.getDeadline());
		map.put("investId",drChannelInfo.getInvestId());
		if(drChannelInfo.getRepayType()!=null){
			map.put("repayType",drChannelInfo.getRepayType());
		}
		List<DrChannelInfo> list = drChannelInfoDAO.getDrChannelInfoOrderList(map);
		String[] title = new String[]{"序号","客户姓名","手机号码","产品名称","订单金额","项目周期","订单id","订单时间","注册时间","渠道名称","绑卡信息","渠道编号"};
		Integer[] columnWidth = new Integer[]{5,20,20,20,20,20,20,20,20,20,20,20};
		
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		int i=1;
		for(DrChannelInfo info:list){
			lc = new ArrayList<Object>();
			lc.add(i++);
			lc.add(info.getRealName() == null ? "":info.getRealName());
			lc.add(info.getMobilephone());
			lc.add(info.getFullName());
			lc.add(info.getAmount());
			lc.add(info.getDeadline());
			lc.add(info.getInvestId());
			lc.add(Utils.format(info.getInvestTime(), "yyyy-MM-dd HH:mm:ss"));
			lc.add(Utils.getparseDate(info.getRegDate(), "yyyy-MM-dd HH:mm:ss"));
			lc.add(info.getName() == null ? "":info.getName());
			if(Utils.isObjectEmpty(info.getRealName())){
				lc.add("否");
			}else{
				lc.add("是");
			}
			lc.add(info.getCode() == null ?"":info.getCode());
			tableList.add(lc);
		}
		
		map.put("titles", title);
		map.put("columnWidth", columnWidth);
		map.put("list", tableList);
		return map;
	}

	@Override
	public BaseResult getFirstDrChannelInfoOrderList(
			DrChannelInfo drChannelInfo, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map = getCommonMap(drChannelInfo);
		/*map.put("mobilephone", drChannelInfo.getMobilephone());
		map.put("cids", drChannelInfo.getCids());
		map.put("startDate", drChannelInfo.getStartDate());
		map.put("endDate", drChannelInfo.getEndDate());*/
		map.put("startInvestDate", drChannelInfo.getStartInvestDate());
		map.put("endInvestDate", drChannelInfo.getEndInvestDate());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		map.put("isbank", drChannelInfo.getIsbank());
		map.put("deadline",drChannelInfo.getDeadline());
		map.put("investId",drChannelInfo.getInvestId());
		if(drChannelInfo.getRepayType()!=null){
			map.put("repayType",drChannelInfo.getRepayType());
		}
		List<DrChannelInfo> list = drChannelInfoDAO.getFirstDrChannelInfoOrderList(map);
		Integer total = drChannelInfoDAO.getFirstDrChannelInfoOrderCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public Map<String, Object> exportFirstChannelOrder(
			DrChannelInfo drChannelInfo) {
		Map<String,Object> map = getCommonMap(drChannelInfo);
		map.put("excelName", "channel_order_record"+System.currentTimeMillis()+".xls");
	/*	map.put("mobilephone", drChannelInfo.getMobilephone());
		map.put("code", drChannelInfo.getCode());
		map.put("startDate", drChannelInfo.getStartDate());
		map.put("endDate", drChannelInfo.getEndDate());*/
		map.put("startInvestDate", drChannelInfo.getStartInvestDate());
		map.put("endInvestDate", drChannelInfo.getEndInvestDate());
		map.put("deadline",drChannelInfo.getDeadline());
		map.put("isbank", drChannelInfo.getIsbank());
		map.put("investId",drChannelInfo.getInvestId());
		if(drChannelInfo.getRepayType()!=null){
			map.put("repayType",drChannelInfo.getRepayType());
		}
		List<DrChannelInfo> list = drChannelInfoDAO.getFirstDrChannelInfoOrderList(map);
		String[] title = new String[]{"序号","客户姓名","手机号码","产品名称","订单金额","项目周期","订单id","订单时间","注册时间","渠道名称","绑卡信息","渠道编号"};
		Integer[] columnWidth = new Integer[]{5,20,20,20,20,20,20,20,20,20,20,20};
		
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		int i=1;
		for(DrChannelInfo info:list){
			lc = new ArrayList<Object>();
			lc.add(i++);
			lc.add(info.getRealName() == null ? "":info.getRealName());
			lc.add(info.getMobilephone());
			lc.add(info.getFullName());
			lc.add(info.getAmount());
			lc.add(info.getDeadline());
			lc.add(info.getInvestId());
			lc.add(Utils.format(info.getInvestTime(), "yyyy-MM-dd HH:mm:ss"));
			lc.add(Utils.getparseDate(info.getRegDate(), "yyyy-MM-dd HH:mm:ss"));
			lc.add(info.getName() == null ? "":info.getName());
			if(Utils.isObjectEmpty(info.getRealName())){
				lc.add("否");
			}else{
				lc.add("是");
			}
			lc.add(info.getCode() == null ?"":info.getCode());
			tableList.add(lc);
		}
		
		map.put("titles", title);
		map.put("columnWidth", columnWidth);
		map.put("list", tableList);
		return map;
	}

	@Override
	public BaseResult getDrChannelInfoOrderListFirst(DrChannelInfo drChannelInfo, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map= getCommonMap(drChannelInfo);
		/*map.put("mobilephone", drChannelInfo.getMobilephone());
		map.put("cids", drChannelInfo.getCids());
		map.put("startDate", drChannelInfo.getStartDate());
		map.put("endDate", drChannelInfo.getEndDate());*/
		map.put("startInvestDate", drChannelInfo.getStartInvestDate());
		map.put("endInvestDate", drChannelInfo.getEndInvestDate());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit());
		map.put("isbank", drChannelInfo.getIsbank());
		map.put("deadline",drChannelInfo.getDeadline());
		map.put("isUpdate",drChannelInfo.getIsUpdate());
		if(drChannelInfo.getRepayType()!=null){
			map.put("repayType",drChannelInfo.getRepayType());
		}
		List<DrChannelInfo> list = drChannelInfoDAO.getDrChannelInfoOrderListFirst(map);
		Integer total = drChannelInfoDAO.getDrChannelInfoOrderCountsFirst(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public Map<String, Object> exportChannelOrderFirst(DrChannelInfo drChannelInfo) {
		Map<String,Object> map = getCommonMap(drChannelInfo);
		map.put("excelName", "channel_order_record"+System.currentTimeMillis()+".xls");
		
		/*map.put("mobilephone", drChannelInfo.getMobilephone());
		map.put("cids", drChannelInfo.getCids());
		map.put("startDate", drChannelInfo.getStartDate());
		map.put("endDate", drChannelInfo.getEndDate());*/
		map.put("startInvestDate", drChannelInfo.getStartInvestDate());
		map.put("endInvestDate", drChannelInfo.getEndInvestDate());
		map.put("deadline",drChannelInfo.getDeadline());
		map.put("isbank", drChannelInfo.getIsbank());
		map.put("isUpdate",drChannelInfo.getIsUpdate());
		
		if(drChannelInfo.getRepayType()!=null){
			map.put("repayType",drChannelInfo.getRepayType());

		}
		List<DrChannelInfo> list = drChannelInfoDAO.getDrChannelInfoOrderListFirst(map);
		String[] title = new String[]{"序号","客户姓名","手机号码","产品名称","订单金额","投资产品期限","投资时间","注册时间","渠道编号","渠道名称","绑卡信息"};
		Integer[] columnWidth = new Integer[]{5,20,20,20,20,20,20,20,20,20,20};
		
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		int i=1;
		for(DrChannelInfo info:list){
			lc = new ArrayList<Object>();
			lc.add(i++);
			lc.add(info.getRealName() == null ? "":info.getRealName());
			lc.add(info.getMobilephone());
			lc.add(info.getFullName());
			lc.add(info.getAmount());
			lc.add(info.getDeadline());
			lc.add(Utils.format(info.getInvestTime(), "yyyy-MM-dd HH:mm:ss"));
			lc.add(Utils.getparseDate(info.getRegDate(), "yyyy-MM-dd HH:mm:ss"));
			lc.add(info.getCode());
			lc.add(info.getName() == null ? "":info.getName());
			if(StringUtil.isNotEmpty(info.getRealName())){
				info.setCardInfo("是");
			}else{
				info.setCardInfo("否");
			}
			lc.add(info.getCardInfo());
			tableList.add(lc);
		}
		map.put("titles", title);
		map.put("columnWidth", columnWidth);
		map.put("list", tableList);
		return map;
	}
	/**
	 * 抽取公共的查询部分给其它方法调用
	 * @param drChannelInfo
	 * @return
	 */
	public Map<String, Object> getCommonMap(DrChannelInfo drChannelInfo){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mobilephone", drChannelInfo.getMobilephone());
		map.put("cids", drChannelInfo.getCids());
		map.put("code", drChannelInfo.getCode());
		map.put("startDate", drChannelInfo.getStartDate());
		map.put("endDate", drChannelInfo.getEndDate());
		
		try {
			String code_7_newHand_byChannel = PropertyUtil.getProperties("code_7_newHand_byChannel");
			if(!Utils.strIsNull(code_7_newHand_byChannel) ){
				String[] codes_newHand = code_7_newHand_byChannel.split(",");
				if (drChannelInfo.getCode() != null && drChannelInfo.getCode() != "") {
					for (String s : codes_newHand) {
						if (drChannelInfo.getCode().equals(s)) {
							map.put("flag_7_newHand", 1);//是否包涵 存管 7天标
							break;
						}
					}
				}else{
					map.put("flag_7_newHand", 1);//是否包涵 存管 7天标
					/*if(drChannelInfo.getCids() != null && drChannelInfo.getCids().length>0){
						Map<String,Object> param = new HashMap<String, Object>();
						param.put("cids", drChannelInfo.getCids());
						List<DrChannelInfo> list = drChannelInfoDAO.getDrChannelInfoListForMap(map);
						for (String s : codes_newHand) {
							for (DrChannelInfo c : list) {
								if (c.getCode().equals(s)) {
									map.put("flag_7_newHand", 1);//是否包涵 存管 7天标
									break;
								}
							}
							
						}
						
					}*/
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}

	@Override
	public BaseResult getDrChannelInfoOrderListNew(DrChannelInfo drChannelInfo, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map = getCommonMap(drChannelInfo);
		map.put("startInvestDate", drChannelInfo.getStartInvestDate());
		map.put("endInvestDate", drChannelInfo.getEndInvestDate());
		map.put("isbank",drChannelInfo.getIsbank());
		map.put("deadline",drChannelInfo.getDeadline());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit());
		map.put("cids",drChannelInfo.getCids());
		map.put("investId",drChannelInfo.getInvestId());
		if(drChannelInfo.getRepayType()!=null){
			map.put("repayType",drChannelInfo.getRepayType());
		}
		List<DrChannelInfo> list = drChannelInfoDAO.getDrChannelInfoOrderListNew(map);
		Integer total = drChannelInfoDAO.getDrChannelInfoOrderCountsNew(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public BaseResult getFirstDrChannelInfoOrderListNew(DrChannelInfo drChannelInfo, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map = getCommonMap(drChannelInfo);
		map.put("startInvestDate", drChannelInfo.getStartInvestDate());
		map.put("endInvestDate", drChannelInfo.getEndInvestDate());
		map.put("isbank",drChannelInfo.getIsbank());
		map.put("deadline",drChannelInfo.getDeadline());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit());
		map.put("cids",drChannelInfo.getCids());
		map.put("investId",drChannelInfo.getInvestId());
		if(drChannelInfo.getRepayType()!=null){
			map.put("repayType",drChannelInfo.getRepayType());
		}
		List<DrChannelInfo> list = drChannelInfoDAO.getFirstDrChannelInfoOrderListNew(map);
		Integer total = drChannelInfoDAO.getFirstDrChannelInfoOrderCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public List<DrChannelInfo> getDrChannelInfoList(Map<String, Object> map) {
		return drChannelInfoDAO.getDrChannelInfoList(map);
	}

	@Override
	public BaseResult selectDrMemberByChannel(DrMember drMember, PageInfo pi) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("realName", drMember.getRealName());
		map.put("mobilephone", drMember.getMobilephone());
		map.put("startDate", drMember.getStartDate());
		map.put("endDate", drMember.getEndDate());
		map.put("toFrom",drMember.getToFrom());
		map.put("toFromName",drMember.getToFromName());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit());
		List<DrMember> list = drMemberDAO.selectDrMemberByChannel(map);
		Integer total = drMemberDAO.selectDrMemberByChannelCount(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public List<Map<String, Object>> selectChannelType() {
		return drChannelInfoDAO.selectChannelType();
	}
	
}
