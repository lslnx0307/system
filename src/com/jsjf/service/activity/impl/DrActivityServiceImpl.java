package com.jsjf.service.activity.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.jsjf.common.BaseResult;
import com.jsjf.common.SerializeUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrActivityDAO;
import com.jsjf.dao.activity.JsActivityAwardLogDAO;
import com.jsjf.dao.member.DrCompanyFundsLogDAO;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.member.DrMemberFundsDAO;
import com.jsjf.dao.member.DrMemberFundsLogDAO;
import com.jsjf.dao.member.DrMemberFundsRecordDAO;
import com.jsjf.dao.member.JsCompanyAccountLogDAO;
import com.jsjf.dao.product.DrProductInvestDAO;
import com.jsjf.dao.system.JsMerchantMarketingDAO;
import com.jsjf.model.activity.JsActivityAwardLog;
import com.jsjf.model.activity.JsAnniversaryShare;
import com.jsjf.model.member.DrCompanyFundsLog;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.member.DrMemberFundsLog;
import com.jsjf.model.member.DrMemberFundsRecord;
import com.jsjf.model.member.JsCompanyAccountLog;
import com.jsjf.model.member.JsRewardsDetails;
import com.jsjf.model.system.JsMerchantMarketing;
import com.jsjf.service.activity.DrActivityService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jzh.FuiouConfig;

@Service
@Transactional
public class DrActivityServiceImpl implements DrActivityService {
	
	@Autowired
    private DrActivityDAO drActivityDAO;
	@Autowired
	private JsCompanyAccountLogDAO jsCompanyAccountLogDAO;
    @Autowired
    private JsMerchantMarketingDAO jsMerchantMarketingDAO;
    @Autowired
    private DrMemberDAO drMemberDAO;
    @Autowired
    private DrMemberFundsDAO drMemberFundsDAO;
    @Autowired
    private DrMemberFundsRecordDAO drMemberFundsRecordDAO;
    @Autowired
    private DrCompanyFundsLogDAO drCompanyFundsLogDAO;
    @Autowired
    private DrMemberFundsLogDAO drMemberFundsLogDAO;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private DrProductInvestDAO drProductInvestDAO;
    @Autowired
    private JsActivityAwardLogDAO jsActivityAwardLogDAO;
	
	@Override
	public void receiveAnniversaryAmount(Map<String, Object> param) {
		//TODO
		BaseResult result = new BaseResult();
		Integer uid  =  (Integer)param.get("uid");			
		Integer id = (Integer) param.get("id");//
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("uid", uid);
		map.put("id", id);
		map.put("status", 0);
		map.put("orders ", " amount desc");
		
		List<JsAnniversaryShare> list = drActivityDAO.selectJsAnniversaryShares(map);
		
		if (list !=null && list.size()>0 && list.get(0).getStatus() == 0) {
			DrMember member = drMemberDAO.selectByPrimaryKey(uid);
			
			BigDecimal amt = list.get(0).getAmount();
			Integer investId = list.get(0).getInvestId();
			
			String remitMchntTxnSsn = Utils.createOrderNo(6, member.getUid(), "");//流水号
			map.put("mchnt_txn_ssn", remitMchntTxnSsn);
			map.put("out_cust_no", FuiouConfig.LOGIN_ID);
			map.put("in_cust_no", member.getMobilephone());
			map.put("amt",amt.toString());
			map.put("rem", "周年庆投资分享返现金|investId:"+investId);
			map.put("contract_no", "");
			map.put("icd_name", "周年庆投资分享返现金");
			
			//
			try {
				list.get(0).setStatus(1);
				drActivityDAO.updateJsAnniversaryShares(list.get(0));
				
				DrMemberFunds funds = drMemberFundsDAO.queryDrMemberFundsByUid(uid);
				
				//用户资金记录 修改用户资金
				funds.setFuiou_balance(funds.getFuiou_balance().add(amt));
				funds.setFuiou_investProfit(funds.getFuiou_investProfit().add(amt));// 已收益加上红包收益
				drMemberFundsDAO.updateDrMemberFunds(funds);
				
				DrMemberFundsRecord record = new DrMemberFundsRecord(null, null, uid, 4, 1,
						amt,
						funds.getFuiou_balance(), 3, "周年庆投资分享返现金", null);
				drMemberFundsRecordDAO.insert(record);
				
				DrMemberFundsLog logs = new DrMemberFundsLog(uid, record.getId(), amt,
						20, 1, "周年庆投资分享返现金");
				drMemberFundsLogDAO.insertDrMemberFundsLog(logs);
				
				DrCompanyFundsLog cfundsLog = new DrCompanyFundsLog(11,uid, null,amt,
						0, "周年庆投资分享返现金", 0);
				drCompanyFundsLogDAO.insertDrCompanyFundsLog(cfundsLog);
				//....
				//记公司账户日志 回款营销收益
				JsCompanyAccountLog companyAccountLog=new JsCompanyAccountLog();
				companyAccountLog.setCompanyfunds(11);//资金类型
				companyAccountLog.setType(0);//支出
				companyAccountLog.setAmount(amt);//金额
				//companyAccountLog.setBalance(jsCompanyAccountLogDAO.getBlanceByFuiou().subtract(balanceProfit));//支出
				companyAccountLog.setStatus(3);//成功
				companyAccountLog.setPid(null);//				
				companyAccountLog.setRemark(member.getMobilephone()+"周年庆投资分享返现金");
				companyAccountLog.setAddTime(new Date());
				companyAccountLog.setChannelType(2);//存管
				companyAccountLog.setUid(member.getUid());//用户id
				jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);
				
				//商户营销流水
				JsMerchantMarketing jmm = new JsMerchantMarketing(amt, null, investId, null,
						member.getUid(), 0, new Date(), remitMchntTxnSsn, null, null, null, "周年庆投资分享返现金", 1);
				
				jsMerchantMarketingDAO.insert(jmm);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}			
				
			 //调用fuiou接口
			result = FuiouConfig.transferBmu(map);//转帐				
			if(!result.isSuccess()){// 失败回滚事务
				throw new RuntimeException("周年庆投资分享返现金失败:"+result.getErrorMsg()+",uid="+uid+",investId="+investId+",amt="+amt);//外面捕捉到异常 ,会保存到redis里
			}
		}
		
	}

	@Override
	public void getDouble11LastKing() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("startDate", "2017-11-03 00:00:00");
		map.put("endDate", "2017-11-09 23:59:59");
		List<Map<String, Object>> list1 = drActivityDAO.anniversaryInvestTop(map);
		map.put("startDate", "2017-11-10 00:00:00");
		map.put("endDate", "2017-11-16 23:59:59");
		List<Map<String, Object>> list2 = drActivityDAO.anniversaryInvestTop(map);
		map.put("startDate", "2017-11-17 00:00:00");
		map.put("endDate", "2017-11-23 23:59:59");
		List<Map<String, Object>> list3 = drActivityDAO.anniversaryInvestTop(map);
		
		Map<String,Object> param = new HashMap<String, Object>();
		Map<String,Object> anniversaryMap = new HashMap<String, Object>();
		if (!Utils.isEmptyList(list1)) {
			anniversaryMap = new HashMap<String, Object>();
			for (Map<String, Object> item : list1) {
				item.put("periods", 1);
				anniversaryMap.put(item.get("uid").toString(), item);// 每个推荐人的 排名,手机,uid,投资年化总额
			}
			anniversaryMap.put("anniversaryList",list1.size() > 10 ? new ArrayList<Map<String, Object>>(list1.subList(0, 10)): list1);// 排行榜
			param.put("king1", anniversaryMap);
		}
		if (!Utils.isEmptyList(list2)) {
			anniversaryMap = new HashMap<String, Object>();
			for (Map<String, Object> item : list2) {
				item.put("periods", 2);
				anniversaryMap.put(item.get("uid").toString(), item);// 每个推荐人的 排名,手机,uid,投资年化总额
			}
			anniversaryMap.put("anniversaryList",list2.size() > 10 ? new ArrayList<Map<String, Object>>(list2.subList(0, 10)): list2);// 排行榜
			param.put("king2", anniversaryMap);
		}
		if (!Utils.isEmptyList(list3)) {
			anniversaryMap = new HashMap<String, Object>();
			for (Map<String, Object> item : list3) {
				item.put("periods", 3);
				anniversaryMap.put(item.get("uid").toString(), item);// 每个推荐人的 排名,手机,uid,投资年化总额
			}
			anniversaryMap.put("anniversaryList",list3.size() > 10 ? new ArrayList<Map<String, Object>>(list3.subList(0, 10)): list3);// 排行榜
			param.put("king3", anniversaryMap);
		}
		redisClientTemplate.set("double11LastKing".getBytes(),SerializeUtil.serialize(param));
	}

	@Override
	public void insertDouble12CashBack() {
		Map<String,Object> map = new HashMap<String, Object>();
		//查询活动
		Map<String,Object> activity = drActivityDAO.selectByName("12月年末返利");
		map.put("startDate", activity.get("startTime"));
		map.put("endDate", activity.get("endTime"));
		//查询需要发返现的人
		List<Map<String,Object>> list = drProductInvestDAO.getDouble12CashBackUid(map);
		//获取阵营排行榜
		List<Map<String,Object>> teamTop = drProductInvestDAO.getTeamInvestTop(map);
		Integer top1Team = null;
		Integer top2Team = null;
		Integer top3Team = null;
		if(teamTop.size()>0){
			if(teamTop.size() == 3){
				top1Team = Integer.parseInt(teamTop.get(0).get("team").toString());
				top2Team = Integer.parseInt(teamTop.get(1).get("team").toString());
				top3Team = Integer.parseInt(teamTop.get(2).get("team").toString());
			}else if(teamTop.size() == 2){
				top1Team = Integer.parseInt(teamTop.get(0).get("team").toString());
				top2Team = Integer.parseInt(teamTop.get(1).get("team").toString());
			}else if(teamTop.size() == 1){
				top1Team = Integer.parseInt(teamTop.get(0).get("team").toString());
			}
		}
		
		for (Map<String, Object> item : list) {
			Integer team = Integer.parseInt(item.get("team").toString());
			Integer uid = Integer.parseInt(item.get("uid").toString());
			BigDecimal amount = new BigDecimal(item.get("amount").toString());
			String mobilePhone = item.get("mobilePhone").toString();
			/**
			 *  阵营排名第一：个人返现金额=活动期间个人累计年化投资额*2%
				阵营排名第二：个人返现金额=活动期间个人累计年化投资额*1.5%
				阵营排名第三：个人返现金额=活动期间个人累计年化投资额*1%
			 */
			Map<String,Object> param = new HashMap<String, Object>();
			if(top1Team != null && top1Team == team){
				amount = amount.multiply(new BigDecimal("0.02"));
			}else if(top2Team != null && top2Team == team){
				amount = amount.multiply(new BigDecimal("0.015"));
			}else if(top3Team != null && top3Team == team){
				amount = amount.multiply(new BigDecimal("0.01"));
			}
			param.put("uid", uid);
			param.put("amount", amount.setScale(2,BigDecimal.ROUND_HALF_UP));
			param.put("atid", activity.get("id"));
			param.put("mobilePhone", mobilePhone);
			//发送返现
			sendCashBack(param);
		}
	}
	
	private void sendCashBack(Map<String,Object> map){
		BaseResult result = new BaseResult();
		Integer uid = Integer.parseInt(map.get("uid").toString());
		Integer atid = Integer.parseInt(map.get("atid").toString());
		BigDecimal amount = new BigDecimal(map.get("amount").toString());
		String mobilePhone = map.get("mobilePhone").toString();
		String remitMchntTxnSsn = Utils.createOrderNo(6,uid, "");// 流水号
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("mchnt_txn_ssn", remitMchntTxnSsn);
		param.put("out_cust_no", FuiouConfig.LOGIN_ID);
		param.put("in_cust_no", mobilePhone);
		param.put("amt", amount.toString());
		param.put("rem", "12月年末狂欢返现金");
		param.put("contract_no", "");
		param.put("icd_name", "12月年末狂欢返现金");
		try {
			// 添加领取记录
			map.clear();
			map.put("atid", atid);
			map.put("uid", uid);
			List<JsActivityAwardLog> list = jsActivityAwardLogDAO.selectByMap(map);
			if(list.size() > 0){
				return;
			}
			JsActivityAwardLog awardLog = new JsActivityAwardLog();
			awardLog.setAddTime(new Date());
			awardLog.setAmount(amount);
			awardLog.setAtid(atid);
			awardLog.setUid(uid);
			awardLog.setType(1);
			jsActivityAwardLogDAO.insert(awardLog);
			DrMemberFunds funds = drMemberFundsDAO
					.queryDrMemberFundsByUid(uid);
			// 用户资金记录 修改用户资金
			funds.setFuiou_balance(funds.getFuiou_balance().add(amount));
			funds.setFuiou_investProfit(funds.getFuiou_investProfit()
					.add(amount));// 已收益加上返现收益
			drMemberFundsDAO.updateDrMemberFunds(funds);
			
			DrMemberFundsRecord record = new DrMemberFundsRecord(null,
					null, uid, 4, 1, amount, funds.getFuiou_balance(),
					3, "12月年末狂欢返现金", null);
			drMemberFundsRecordDAO.insert(record);
			
			DrMemberFundsLog logs = new DrMemberFundsLog(uid,
					record.getId(), amount, 30, 1, "12月年末狂欢返现金");
			drMemberFundsLogDAO.insertDrMemberFundsLog(logs);
			
			DrCompanyFundsLog cfundsLog = new DrCompanyFundsLog(11,
					uid, null, amount, 0, "12月年末狂欢返现金", 0);
			drCompanyFundsLogDAO.insertDrCompanyFundsLog(cfundsLog);
			// ....
			// 记公司账户日志 回款营销收益
			JsCompanyAccountLog companyAccountLog = new JsCompanyAccountLog();
			companyAccountLog.setCompanyfunds(30);// 资金类型
			companyAccountLog.setType(0);// 支出
			companyAccountLog.setAmount(amount);// 金额
			// companyAccountLog.setBalance(jsCompanyAccountLogDAO.getBlanceByFuiou().subtract(balanceProfit));//支出
			companyAccountLog.setStatus(3);// 成功
			companyAccountLog.setPid(null);
			companyAccountLog.setRemark(mobilePhone+ "12月年末狂欢返现金");
			companyAccountLog.setAddTime(new Date());
			companyAccountLog.setChannelType(2);// 存管
			companyAccountLog.setUid(uid);// 用户id
			jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);
			
			// 商户营销流水
			JsMerchantMarketing jmm = new JsMerchantMarketing(amount,
					null, null, null, uid, 0, new Date(),
					remitMchntTxnSsn, null, null, null, "12月年末狂欢返现金", 1);
			jsMerchantMarketingDAO.insert(jmm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 //调用fuiou接口
		result = FuiouConfig.transferBmu(param);//转帐				
		if(!result.isSuccess()){// 失败回滚事务
			throw new RuntimeException("12月年末狂欢返现金失败:"+result.getErrorMsg()+",uid="+uid+",amt="+amount);
		}
	}
}
