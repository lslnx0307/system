package com.jsjf.controller.task;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.SerializeUtil;
import com.jsjf.common.SmsSendUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.member.JsCompanyAccountLogDAO;
import com.jsjf.dao.product.DrProductInvestDAO;
import com.jsjf.model.member.JsCompanyAccountLog;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.product.DrProductLoanRecodService;
import com.jsjf.service.system.SysFuiouNoticeLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jzh.FuiouConfig;

@Component
public class ProducTask {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private DrProductInfoService drProductInfoService;
	@Autowired
	private DrProductInvestService drProductInvestService;
	@Autowired
	private DrProductLoanRecodService drProductLoanRecodService;
	@Autowired
	private DrProductInvestDAO drProductInvestDAO;
	@Autowired
	private SysFuiouNoticeLogService sysFuiouNoticeLogService;
	@Autowired
	private JsCompanyAccountLogDAO jsCompanyAccountLogDAO;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	/**
	 * 优选产品计息
	 * @throws  
	 */
	@Scheduled(cron="0 01 00 * * ?") //凌晨执行
	public void productRaiseEnd() {
		log.info("执行计息任务");
		List<DrProductInfo> list = drProductInfoService.selectRaiseSuccesProductInfo();
		char[] ary2 = {'0','0','0','0'};
		int nums = 0;//记录计息次数
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DrProductInfo drProductInfo = (DrProductInfo) iterator
					.next();
			try {
				int count = drProductInvestService.selectInvestCountByPid(drProductInfo.getId());
				if(drProductInfo.getProject_no() != null && !"".equals(drProductInfo.getProject_no())){ 
					//恒丰存管项目报备产品计息
					drProductInfoService.updateFuiouProductToEnd(drProductInfo, ary2, nums);
					
					//计算财富值
					if(drProductInfo.getType()==2 || drProductInfo.getType()==6){
						
						Map<String, Object>m=new HashMap<>();
						m.put("type", 6);						
						m.put("productId", drProductInfo.getId());						
						m.put("isInvestIncrWeaLth", true);
						redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(),SerializeUtil.serialize(m));
					}
					
				}else{
					//其他情况产品计息
					drProductInfoService.updateProductToEnd(drProductInfo, ary2, nums);
				}
				nums = nums + count;
				
			} catch (Exception e) {
				nums += drProductInvestService.selectInvestCountByPid(drProductInfo.getId());
				log.error(drProductInfo.getFullName()+"==>计息任务执行失败,下一个产品开始编号为："+nums,e);
				try {
					SmsSendUtil.sendMsg("15821421008,17317566419", drProductInfo.getFullName()+"==>计息任务执行失败");
				} catch (Exception e1) {
					log.error(e1);
				}
			}
		}
		log.info("计息任务完成");	
	}
	
	
	/**
	 *扣除手续费失败重新跑扣除手续费
	 * @throws  
	 */
	@Scheduled(cron="0 00 05 * * ?") //凌晨执行
	public void getSysFuiouNoticeLogByIcd() {
		log.info("执行扣除手续费任务");
		List<SysFuiouNoticeLog> list = sysFuiouNoticeLogService.getSysFuiouNoticeLogByIcd();
			try {
				if(list.size()>0){
					for (SysFuiouNoticeLog sysFuiouNoticeLog : list) {
						//扣除手续费
						Map<String, Object> params = new HashMap<String, Object>();
						String remitMchntTxnSsn = Utils.createOrderNo(6,sysFuiouNoticeLog.getId(), "");// 流水号
						params.put("mchnt_txn_ssn", remitMchntTxnSsn);
						params.put("out_cust_no", sysFuiouNoticeLog.getUser_id());
						params.put("in_cust_no", FuiouConfig.LOGIN_ID);
						params.put("amt", "" + sysFuiouNoticeLog.getAmt());// 手续费精确到分
						params.put("icd_name", "平台提现手续费");
						params.put("rem", "");
						params.put("contract_no", "");
						BaseResult br = FuiouConfig.transferBmu(params);
						if(br.isSuccess()){
							JsCompanyAccountLog companyAccountLog=new JsCompanyAccountLog();
							companyAccountLog.setCompanyfunds(4);//资金类型
							companyAccountLog.setType(1);//收入
							companyAccountLog.setAmount(new BigDecimal(2));//金额
							/*companyAccountLog.setBalance(jsCompanyAccountLogDAO.getBlanceByFuiou().add(drMemberCarry.getPoundage()));*/
							companyAccountLog.setStatus(3);//成功
							companyAccountLog.setRemark(sysFuiouNoticeLog.getUser_id()+"平台提现手续费(投资人)");
							companyAccountLog.setAddTime(new Date());
							companyAccountLog.setChannelType(2);//存管
/*							companyAccountLog.setUid(member.getUid());//用户id
*/							jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);		
						}
						sysFuiouNoticeLog.setStatus(2);
						sysFuiouNoticeLogService.update(sysFuiouNoticeLog);
					}
				}else{
					log.info("没有失败的数据");
				}
				
			} catch (Exception e) {
				
			}
		log.info("扣除手续费任务完成");	
	}
	
	/**
	 * 体验标计息  
	 * @throws  
	 */
	@Scheduled(cron="0 10 00 * * ?") //凌晨执行
	public void productExperience() {
		log.info("执行体验标计息任务"); 
		
		//这段代码 时间过了就可以删除
		String d = "2017-05-07 23:23:23";//指定时间之后体验标计息走 存管回款
		boolean f = false;
		try {
			f = new Date().after(Utils.parse(d, "yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e2) {
			System.out.println("计息失败...........");
			return ;
		}
		
		
		List<DrProductInfo> infoList = drProductInfoService.getDrProductInfoExperience();
		if(!Utils.isEmptyList(infoList)){
			char[] ary2 = {'0','0','0','0'};
			int nums = 0;
			for(DrProductInfo pInfo:infoList){
				List<DrProductInvest> list = drProductInvestDAO.getDrProductInvestListByPid(pInfo.getId());
				if(!Utils.isEmptyList(list)){
					try {
						List<DrProductInvest> investList = new ArrayList<>();
						for(int i = 0 ; i < list.size() ; i++){
							investList.add(list.get(i));
							if(i != 0 && i%20 == 0){
								if(f){//存管
									drProductInfoService.updateProductToEnd3(pInfo,ary2, nums,investList);
								}else{//平台
									drProductInfoService.updateProductToEnd2(pInfo,ary2, nums,investList);
									
								}
								nums = i;
								investList.clear();
							}
						}
						if(!Utils.isEmptyList(investList)){
							if(f){//存管
								drProductInfoService.updateProductToEnd3(pInfo,ary2, nums,investList);
							}else{//平台
								drProductInfoService.updateProductToEnd2(pInfo,ary2, nums,investList);
								
							}
						}
					} catch (Exception e) {
						log.error("体验标==>计息任务执行失败,下一个产品开始编号为",e);
						try {
							SmsSendUtil.sendMsg("17317566419", "体验标==>计息任务执行失败");
						} catch (Exception e1) {
							log.error(e1);
						}
					}
				}
			}
		}
		log.info("体验标计息任务完成");
	}
	
	/**
	 * 首投返利任务
	 * 此任务需要在产品计息任务之后执行
	 */
	//@Scheduled(cron="0 10 00 * * ?")
	public void friendsFirstInvestmentReward(){
		try {
			log.info("首投返利任务开始");
			drProductInvestService.updateFriendsFirstInvestmentReward();
			log.info("首投返利任务结束");
		} catch (Exception e) {
			log.error("首投返利任务失败",e);
		}
	}
	
	/**
	 * 债权匹配
	 */
//	s@Scheduled(cron="0 00 01 * * ?")
	public void insertInvestTransfer(){
		try {
			log.info("债权匹配任务开始");
			drProductInvestService.insertInvestTransfer();
			log.info("债权匹配任务结束");
		} catch (Exception e) {
			log.error("债权匹配任务执行失败",e);
		}
	}
	
	/**
	 * 产品回款
	 */
	@Scheduled(cron="0 0 5 * * ?") //凌晨5点执行回款
	public void productRepay(){
		
		log.info("执行回款任务开始");
		List<DrProductInfo> infoList = drProductInvestService.selectExpireProductInfo();
		for(int i = 0; i < infoList.size(); i++){
			DrProductInfo info = infoList.get(i);
			try {
				String d = "2017-05-07 23:23:23";//指定时间之后体验标回款走 存管回款
				boolean f = new Date().after(Utils.parse(d, "yyyy-MM-dd HH:mm:ss"));
				
				//注意:发布凌晨之前,体验标有问题
				if((info.getType() ==5 && f) || (info.getProject_no() != null && !"".equals(info.getProject_no()))){
					//恒丰存管项目报备产品回款
					drProductInvestService.saveInvestRepayByFuiou(info);
				}else{
					//其他情况产品回款
					drProductInvestService.saveInvestRepay(info);
				}
			} catch (Exception e) {
				log.error(info.getFullName()+"==>执行回款任务失败", e);
				try {
					SmsSendUtil.sendMsg("15821421008,17317566419", info.getFullName()+"==>回款任务执行失败");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		log.info("执行回款任务结束");
	}
	
	/**
	 * 产品回款(只跑回款失败的记录)
	 */
	@Scheduled(cron="0 0 10,15 * * ?") //凌晨5点执行回款
	public void failproductRepay(){
		
		log.info("执行回款失败再次回款任务开始");
		List<DrProductInfo> infoList = drProductInvestService.selectfailExpireProductInfo(null);
		for(int i = 0; i < infoList.size(); i++){
			DrProductInfo info = infoList.get(i);
			try {
				if((info.getType() ==5) || (info.getProject_no() != null && !"".equals(info.getProject_no()))){
					//恒丰存管项目报备产品回款
					drProductInvestService.saveFailInvestRepayByFuiou(info);
				}
			} catch (Exception e) {
				log.error(info.getFullName()+"==>执行回款任务失败", e);
				try {
					SmsSendUtil.sendMsg("15821421008,17317566419", info.getFullName()+"==>回款任务执行失败");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		log.info("执行回款失败再次回款任务结束");
	}

	/**
	 *生成前一天新手标放款回款记录
	 */
	@Scheduled(cron="0 0 6 02 * ?") //凌晨6点执行
	public void productLoanRecord(){
		log.info("执行生成新手标放款回款记录任务开始");
		drProductLoanRecodService.insert();
		log.info("执行生成新手标放款回款记录任务结束");
	}
	
	/**
	 * 每天凌晨00:30执行流标操作
	 */
	@Scheduled(cron="0 0/30 00 * * ?") //凌晨00:30点执行
	public void productFaile(){
		log.info("执行流标开始");
		try {
			drProductInfoService.getDrProductInfoByType(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("执行流标结束");
	} 
	/**
	 * 满标提醒
	 */
	@Scheduled(cron="0 0 8,20 * * ?")//每天8:00提醒
	public void productFullRemind(){
		try {
			log.info("执行满标提醒开始...");
			String mobliePhone = PropertyUtil.getProperties("productFullRemind");			
			if(Utils.isObjectEmpty(mobliePhone)) mobliePhone = "15000896419";			
			drProductInfoService.productFullRemind(mobliePhone);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
