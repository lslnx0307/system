package com.jsjf.controller.task;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jsjf.common.Utils;
import com.jsjf.dao.product.DrProductInvestRepayInfoDAO;
import com.jsjf.dao.system.SysFuiouMessageLogDAO;
import com.jsjf.model.claims.DrClaimsCustomer;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.product.DrProductInvestRepayInfo;
import com.jsjf.model.system.SysFuiouMessageLog;
import com.jsjf.service.claims.DrClaimsInfoService;
import com.jsjf.service.jzh.JzhMessageService;
import com.jsjf.service.jzh.SysFuiouMessageService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.seq.SeqService;
import com.jzh.FuiouConfig;
import com.jzh.util.ConfigReader;
import com.jzh.util.JzhSFtpUtil;

@Component
public class JzhMessageTask {
	@Autowired
	private JzhMessageService jzhMessageService;
	/**
	 * 个人开户报文报备
	 * 每天早上的1点钟报备昨日的个人开户
	 */
//	@Scheduled(cron="0 0 1 * * ?") 
   	public void selectPersonRegBatchUpload() {
   		jzhMessageService.selectPersonRegBatchUpload();
   	}
	
	/**
	 * 法人开户报文报备
	 * 每天早上的1点钟报备昨日的法人开户
	 */
//	@Scheduled(cron="0 0 1 * * ?") 
		//获取数据
   		public void companyRegBatchUpload(){
   			jzhMessageService.companyRegBatchUpload();
   	}
    /**
     * P2P商户交易（冻结，动账）
  	 * 获取投标记录
  	 * 每天早上的3:00进行投标交易报备
  	 */
	// @Scheduled(cron="0 0 3 * * ?")
		public void getProductByProjectNo(){
			jzhMessageService.getProductByProjectNo();
	}
	
	/**
	 * 满标放款
	 * @param drProductInfoService
	 * @param seqService
	 * @param sysFuiouMessageService
	 * 每天早上3:15报备满标放款
	 */
	// @Scheduled(cron="0 15 3 * * ?")
		public void getProductFullCreditByProjectNo(){
			jzhMessageService.getProductFullCreditByProjectNo();
	}

	/**
	 * P2P商户交易（冻结，动账）
	 * 获取回款记录
	 * 每天早上的3:00报回款
	 */
	// @Scheduled(cron="0 0 3 * * ?")
		public void getProductInvestRepayInfoByProjectNo(){
			jzhMessageService.getProductInvestRepayInfoByProjectNo();
	}
     	
	/**
	 * P2P 项目信息
	 * 定义每5分钟执行一次项目信息报备
	 * 
	 */
//	@Scheduled(cron="0 0/30 * * * ?") 
   	public void projectInfoUpload(){	
   		jzhMessageService.projectInfoUpload();
   	}
   	/**
	 * 报文回调
	 * 定义每半小时执行一次改掉overcheck中匹配成功的数据状态
	 */
//	@Scheduled(cron="0 0/30 * * * ?") 
	   	public void overcheck() {
	   		jzhMessageService.overcheck();
   	}
   	
}
