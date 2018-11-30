package com.jsjf.controller.task;


import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jsjf.common.Utils;
import com.jsjf.model.activity.ActivityFriend;
import com.jsjf.model.member.DrMemberCarry;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.service.activity.ActivityFriendService;
import com.jsjf.service.activity.DrActivityParameterService;
import com.jsjf.service.activity.DrActivityService;
import com.jsjf.service.member.DrMemberCarryService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.system.SysFuiouNoticeLogService;
import com.jsjf.service.system.SysMessageLogService;

@Component
public class ActivityTask {
	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	public DrActivityParameterService drActivityParameterService;
	@Autowired
	public SysMessageLogService sysMessageLogService;
	@Autowired
	public DrMemberService drMemberService;
	@Autowired
	public DrProductInvestService drProductInvestService;
	@Autowired
	public  ActivityFriendService activityFriendService;
	@Autowired
	public SysFuiouNoticeLogService sysFuiouNoticeLogService;
	@Autowired
	public DrMemberCarryService drMemberCarryService;
	@Autowired
	public DrActivityService drActivityService;
	
	/**
	 * 定义每半小时执行一次好友邀请返现任务
	 */
	@Scheduled(cron="0 23/30 * * * ?") 
   	public void insertFriendMemberActivityAmount() {
    	try {
    		/*
	    		//执行一次好友邀请返现任务
	    		if(new Date().before(Utils.parse("2017-04-14", "yyyy-MM-dd")))
	    			drActivityParameterService.insertFriendMemberActivityAmount();
    		*/
    		
    		if(new Date().before(Utils.parse("2017-09-01", "yyyy-MM-dd"))){//三重好礼的逻辑
    			//插入好友邀请三重好礼 返现到活动账户
    			drActivityParameterService.insertThreePresent();    		
    			//三重好礼排行榜
    			drActivityParameterService.selectThreePresentTop();
    			
    		}else{//双重礼的逻辑
    			
    			drActivityParameterService.insertTwinPresent();
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
   	}
	/**
	 * 定义每半小时执行一次好友邀请返现任务
	 */
	@Scheduled(cron="0 26/30 * * * ?") 
	public void insertSendThreePresentToAccount() {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", 1);
		List<ActivityFriend> list = activityFriendService.selectObjectByMap(map);
		
		if(!Utils.isObjectEmpty(list)){
			for(ActivityFriend af:list){
				List<Integer> uidList =  activityFriendService.selectIsSend(af.getId());
				if(!Utils.isObjectEmpty(uidList)){
					for(Integer uid:uidList){
						try {
							drActivityParameterService.insertSendThreePresentToAccount(uid, af);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				System.out.println("----好友邀请三重礼返现任务,活动:"+af.getName()+"发放完成");
			}
		}
	}
	
	/**
	 * 定义每半小时执行一次好友邀请返现任务
	 */
//	@Scheduled(cron="0 3/30 * * * ?") 
	public void insertSendThreePresentToAccount111111111() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", 0);
		List<ActivityFriend> list = activityFriendService.selectActivityFriendByMap(map);
		
		for(ActivityFriend af:list){
			List<Integer> uidList =  activityFriendService.selectIsSend(af.getId());
			if(!Utils.isObjectEmpty(uidList)){
				for(Integer uid:uidList){
					try {
						drActivityParameterService.insertSendThreePresentToAccount(uid, af);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("----好友邀请三重礼返现任务,活动:"+af.getName()+"发放完成");
		}
	}
	
	
	/**
	 * 分配客户
	 */
	@Scheduled(cron="0 0 8,12 * * ?") 
	public void allotCustomer() {
		try {
			drMemberService.allotCustomer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行促复投红包发放()
	 */
	@Scheduled(cron="0 50 8 * * ?")
	public void insertCpsFavourable(){
		try {
			drActivityParameterService.insertCpsFavourables();
		} catch (Exception e) {
			log.error("促复投红包发放失败", e);
		}
	}
	/**
	 * 定时短信应急发送
	 */
	@Scheduled(cron="0 15 10 * * ?")
	public void sendSmsHandle(){
		System.out.println("定时短信应急发送处理开始...");
		sysMessageLogService.sendSmsHandle();
		System.out.println("定时短信应急发送处理结束...");
	}
	
	
	
//	/**
//	 * 营销短信-压岁钱领取提醒
//	 */
//	@Scheduled(cron="0 00 10,21 27,28,29 1 ?")
//	public void yasuiqianTiXingSms(){
//		System.out.println("####### " + Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + " 压岁钱领取提醒开始#######");
//		sysMessageLogService.luckyMoneySms();
//		System.out.println("####### " + Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + " 压岁钱领取提醒结束########");
//	}
//	
//
//	
//	@Scheduled(cron="0 00 10 3 2 ?")
//	public void yasuiqianGuoQiSms(){
//		System.out.println("#######体验金过期提醒#######："+Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//		sysMessageLogService.tiYanJinGuoQiSms();
//		System.out.println("#######体验金过期短信提醒结束#######："+Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//	}
	@Scheduled(cron="0 15 10 * * ?")
	public void SMSMarketing(){
		
		//注册第2天 -未绑卡
//		drMemberService.SMSMarketNoTCertified("noTCertified");
			
		//注册第3天-未投资type=2 and if(atid or prizeId,0,1) 
//		drProductInvestService.marketSMSNotInvest(3,"reg3DayNotInvest",false);
		
		//注册第7天-未投资
//		drProductInvestService.marketSMSNotInvest(7,"reg7DayNotInvest",false);
		
		//注册第8天-未投资
//		drProductInvestService.marketSMSNotInvest(8,"reg8DayNotInvest",true);//（暂定15天标，未开始）
		
		//首投后第5天-首投用户，投资后第5天-
		//1%加息券
		drActivityParameterService.marketFirstInvestLaterdays(5, "firstInvest5Day","首投奖励-加息券",2);
		//营销防流失短信
//		drMemberService.customerLossPrevention();
		drMemberService.customerLossPrevention2();
	}
	
	/**
	 * 查询修改银行卡是否通过审核
	 */
	@Scheduled(cron="0 23 * * * ?") //凌晨执行
	public void queryChangeCard() {
		try {
			List<SysFuiouNoticeLog> list=sysFuiouNoticeLogService.queryChangeCard();
			if(list.size()>0){
				for (SysFuiouNoticeLog sysFuiouNoticeLog : list) {
					drMemberService.queryChangeCard(sysFuiouNoticeLog);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/**
 * 查询订单在存管是否存在
 */
	@Scheduled(cron="0 20 2,11,16,22 * * ?") //凌晨执行
	public void queryFuiouMemberCarryByStatus() {
	try {
		List<DrMemberCarry> list=drMemberCarryService.selectFuiouByStatus();
		if(list.size()>0){
			for (DrMemberCarry drMemberCarry : list) {
				drMemberCarryService.queryFuiouMemberCarryByStatus(drMemberCarry);
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	
	//年末红包领取期限到期前7天发送手机短信提醒
	@Scheduled(cron="0 0 11 22 1 ?")
	public void sendYearPresentSMS(){
		drMemberService.sendYearPresentSMS();
	}
	
	//生日礼包领取期限到期前7天发送手机短信提醒
	@Scheduled(cron="0 0 11 * * ?")
	public void sendBirthdayPresentSMS(){
		drMemberService.sendBirthdayPresentSMS();
	}
	/**
	 * 新手引导短信发送
	 */
	@Scheduled(cron="0 0 10 * * ?")
	public void sendnewMemberGuidance(){
		drMemberService.sendNoBbinNoCard("noBbinNoCard");//功能上线后注册的用户，未使用体验金、未绑卡
		drMemberService.sendNoInvest("noInvest");//此功能上线注册的用户，已使用体验金，为投资
	}
	
	/**
	 * 双11往期排行榜获取
	 */
	@Scheduled(cron="0 10 0 * * ?")
	public void double11LastKing(){
		try {
			
			if(new Date().before(Utils.parse("2017-12-02", "yyyy-MM-dd"))){
				drActivityService.getDouble11LastKing();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 双11 发放现金
	 */
	@Scheduled(cron="0 * 8 * * ?")
	public void provideAwardAtDoubleEleven(){
		try {
			drMemberService.selectQualificationMember();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 双12活动结束发返现金
	 */
	@Scheduled(cron="0 0 9 * * ?")
	public void insertDouble12CashBack(){
		try {
			if(Utils.parse(new Date(), "yyyy-MM-dd").equals(Utils.format("2018-1-1", "yyyy-MM-dd"))){
				drActivityService.insertDouble12CashBack();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}