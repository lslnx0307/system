package com.jsjf.controller.task;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jsjf.common.SerializeUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.service.activity.DrActivityParameterService;
import com.jsjf.service.activity.DrActivityService;
import com.jsjf.service.member.JsMemberGradeService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Component
public class TaskController implements InitializingBean {
	
	@Autowired
	private DrActivityParameterService drActivityParameterService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private DrProductInvestService drProductInvestService;
	@Autowired
	private DrProductInfoService drProductInfoService;
	@Autowired
	private DrActivityService drActivityService;
	@Autowired
	private JsMemberGradeService jsMemberGradeService;
	@Override
	public void afterPropertiesSet() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Map<String, Object> param = new HashMap<String, Object>();
				Integer uid = null;
				Integer type = null;
				Integer newUser = null;
				Integer deadline = null;
				BigDecimal amount = BigDecimal.ZERO;
				byte[] uidMap = null;
				while(true){
					try {
						Thread.sleep(500l);
						uidMap = redisClientTemplate.rpop("regAndVerifySendRedUidList".getBytes());
						param = (Map<String,Object>)SerializeUtil.unserialize(uidMap);
						
						if(param == null || Utils.isObjectEmpty(param.get("type")))continue;
						//业务逻辑
						uid  = param.get("uid") !=null ? (Integer)param.get("uid") : null;
						type = param.get("type") != null ? (Integer)param.get("type") : null;
						newUser = param.get("newUser") != null ? (Integer)param.get("newUser") : null;
						System.out.println("redis 队列   开始 ..."+uid+","+type);
						boolean flag = true;
						if (type == 1) {
							deadline = Integer.parseInt(param.get("deadline").toString());
							amount = new BigDecimal(String.valueOf(param.get("amount"))); 
							// 发年终奖
							param.clear();
							param.put("uid",uid);
							param.put("type",type);
							param.put("deadline",deadline);
							param.put("amount",amount);
							drActivityParameterService.annualBonus(param);
						} else if (type == 0 || type == 3 || type == 7) {//7开通存管送
							// 发优惠券
							drActivityParameterService.valentineActivitys(uid, type ,newUser);
							if(type != 7){
								//邀请好有三重礼  
								drActivityParameterService.threePresentMultipleCoupon(uid);
							}
						}else if(type == 4){//感恩活动 发放优惠券
							drActivityParameterService.gratitudeBlessing(uid);							
							
						}else if(type == 5){//会员发放权益奖励
							drActivityParameterService.drawEquity(param);	
						}else if(type == 6){//会员财富值增加
							jsMemberGradeService.incrWealth(param);
							
						}else if(type == 50){//自动发标
							flag = false;
							drProductInvestService.toAutoReleaseProduct(param);								
						}else if(type == 51){//回款发放收益走营销存管账号
							System.out.println("--------回款发放收益走营销存管账号");
							drProductInvestService.productOtherInterestDistributeByFuiouMarketingAccount(param);
							
						}else if(type == 52){//投资红包返现走营销存管账号
							System.out.println("--------投资红包返现走营销存管账号");
							drProductInvestService.cashbackDistributeByFuiouMarketingAccount(param);
						}else if(type == 53){//满标 执行冻结到冻结 
							System.out.println("----满标冻结到冻结");
							drProductInfoService.productFullfreezeToFreeze(param);
							
						}else if(type == 54){//满标 执行冻结到冻结 							
							System.out.println("----周年庆投资分享红包发放");
							drActivityService.receiveAnniversaryAmount(param);
							
						}
						
						//发成功统计
						if(flag)
							redisClientTemplate.lpush("regAndVerifySendRedUidListCount", uid+"_"+type);
					} catch (Exception e) {
						e.printStackTrace();
						
						if(type ==52 || type ==6)
							redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), uidMap);
						else
							redisClientTemplate.lpush("regAndVerifySendRedUidList_bak".getBytes(), uidMap);
					}
				}
			}
		});
//	}).start();
//		System.out.println("送红包线程开始");
	}

	
}
