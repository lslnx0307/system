package com.jzh;

import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.sf.json.JSONObject;
import sun.util.logging.resources.logging;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.SerializeUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.claims.DrClaimsCustomer;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jsjf.service.system.SysFuiouNoticeLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jzh.data.ArtifRegReqData;
import com.jzh.data.BalanceActionReqData;
import com.jzh.data.BaseRspdata;
import com.jzh.data.FreezeReqData;
import com.jzh.data.FuiouOnlineBankingReq;
import com.jzh.data.FuiouOnlineBankingRspData;
import com.jzh.data.PreAuthCancelReqData;
import com.jzh.data.PreAuthReqData;
import com.jzh.data.QueryChangeCardReqData;
import com.jzh.data.QueryCzTxReqData;
import com.jzh.data.QueryTxnReqData;
import com.jzh.data.QueryUserInfs_v2ReqData;
import com.jzh.data.QueryUserInsfReqData;
import com.jzh.data.RechargeReqData;
import com.jzh.data.TransferBmuReqData;
import com.jzh.data.TransferBuAndFreeze2FreezeReqData;
import com.jzh.data.TransferBuReqData;
import com.jzh.data.UnFreezeReqData;
import com.jzh.data.UserChangeCardReqData;
import com.jzh.data.WithdrawalsReqData;
import com.jzh.data.WtwithdrawReqData;
import com.jzh.data.enterpriseAccountReq;
import com.jzh.service.JZHService;
import com.jzh.util.ConfigReader;
import com.jzh.util.SecurityUtils;
import com.jzh.util.StringUtil;

import cn.jiguang.common.utils.StringUtils;
/**
 * 恒丰存管2.0
 * @author Ernes
 *
 */
public class FuiouConfig {
	
	public final static String MCHNT_CD;//商户代码
	public final static String VER;//版本
	public final static String LOGIN_ID;//登录id
	
	public final static String OPENACCOUNT_BACK_NOTIFY_URL;//商户异步地址
	public final static String OPENACCOUNT_PAGE_NOTIFY_URL;//商户同步通知地址
	public final static String WTWITHDRAW_BACK_NOTIFY_URL;//委托提现退票异步通知
	
	public final static String ENTERPRISE_ACCOUNT_PAGE_NOTIFY_URL;//企业开户前台地址
	
	public final static String PREAUTHURL;//预授权接口地址
	public final static String PREAUTHCANCELURL;//撤销预授权接口地址	
	public final static String FREEZEURL;//冻结
	public final static String TRANSFERBUURL;//划拨(个人与个人之间)接口地址
	public final static String TRANSFERBMUURL;//转账（商户与个人之）
	public final static String WEBARTIFREGURL;//法人用户自助开户注册（网页版）
	public final static String UNFREEZEURL;//解冻（直连）
	public final static String BRECHARGEURL;//商户P2P网站免登录网银充值（网页版）
	public final static String WITHDRAWURL;//商户P2P网站免登录提现接口（网页版）
	public final static String RESETPASSWORDURL;//用户密码修改重置免登陆接口(网页版)
	public final static String QUERYUSERINFSURL;//用户信息查询地址
	public final static String PCQRECHARGE500405URL;//PC端个人用户免登录快捷充值(网页版)
	public final static String ARTIFREGURL;//法人开户注册
	public final static String BALANCEACTIONURL;//余额查询
	public final static String WTWITHDRAWURL;//委托提现
	public final static String QUERYCZTXURL;//充值提现查询接口
	public final static String QUERYUSERINFS_V2;//查询个人开户
	public final static String USERCHANGECARDURL;//用户更换银行卡接口（直连）
	public final static String QUERYCHANGECARDURL;//用户更换银行卡查询
	public final static String TRANSFERBUANDFREEZE2FREEZEURL;//冻结到冻结
	public final static String QUERYTXNURL;//交易查询



	public final static String REG = "000000";//开户注册
	public final static String ARTIFREG = "000001";//法人开户注册
	public final static String PREAUTH = "000002";//预授权
	public final static String PREAUTHCANCEL = "000003";//撤销预授权
	public final static String TRANSFERBMU = "000004";//转账（商户与个人之）
	public final static String TRANSFERBU = "000005";//划拨(个人与个人之间)
	public final static String FREEZE = "000006";//冻结
	public final static String TRANSFERBMUANDFREEZE = "000007";//转账预冻结（直连）
	public final static String TRANSFERBUANDFREEZE = "000008";//划拨预冻结（直连）
	public final static String TRANSFERBUANDFREEZE2FREEZE = "000009";//冻结到冻结接口（直连）
	public final static String UNFREEZE = "000010";//解冻（直连）
	public final static String USERCHANGECARD = "000011";//用户更换银行卡接口（直连）
	public final static String WEBREG = "000012";//个人用户自助开户注册（网页版）
	public final static String WEBARTIFREG = "000013";//法人用户自助开户注册（网页版）
	public final static String QRECHARGE = "000014";//商户P2P网站免登录快速充值（网页版）
	public final static String BRECHARGE = "000015";//商户P2P网站免登录网银充值（网页版）
	public final static String WITHDRAW = "000016";//商户P2P网站免登录提现接口（网页版）
	public final static String RESETPASSWORD = "000017";//用户密码修改重置免登陆接口(网页版)
	public final static String PCQRECHARGE500405 = "000018";//PC端个人用户免登录快捷充值
	public final static String APPWEBREG = "000019";//APP个人用户自助开户注册
	public final static String APPSIGN_CARD = "000020";//APP免登签约
	public final static String APPQRECHARGE = "000021";//商户APP个人用户免登录快速充值
	public final static String APPQRECHARGE2 = "000022";//商户APP个人用户免登录快捷充值
	public final static String APPWITHDRAW = "000023";//商户APP个人用户免登录提现
	public final static String APPRESETPASSWORD = "000024";//用户密码修改重置免登陆接口(app版)
	public final static String QUERYUSERINFS ="000025";//用户信息查询
	public final static String WTWITHDRAW="000036";//委托提现
	public final static String QUERYCHANGECARD="000027";//用户更换银行卡查询
	public final static String BRECHARGE12 = "000115";//网银跳银行
	
	@Autowired
	private SysFuiouNoticeLogService sysFuiouNoticeLogService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	private static FuiouConfig fuiouConfig;
	public FuiouConfig(){
		
	}
	public void setSysFuiouNoticeLogService(SysFuiouNoticeLogService sysFuiouNoticeLogService) {  
        this.sysFuiouNoticeLogService = sysFuiouNoticeLogService;  
    }
	public void setRedisClientTemplate(RedisClientTemplate redisClientTemplate) {
		this.redisClientTemplate = redisClientTemplate;
	}
	//类似声明了，当你加载一个类的构造函数之后执行的代码块，也就是在加载了构造函数之后，就将service复制给一个静态的service
	@PostConstruct  
    public void init() {  
		fuiouConfig = this;
		fuiouConfig.sysFuiouNoticeLogService = this.sysFuiouNoticeLogService; 
		fuiouConfig.redisClientTemplate = this.redisClientTemplate;
    }  
	static { ////注意 properties 里统一小写
		MCHNT_CD = ConfigReader.getConfig("mchnt_cd");
		VER = ConfigReader.getConfig("ver");
		LOGIN_ID = ConfigReader.getConfig("login_id");
		OPENACCOUNT_BACK_NOTIFY_URL = ConfigReader.getConfig("openaccount_back_notify_url");
		OPENACCOUNT_PAGE_NOTIFY_URL = ConfigReader.getConfig("openaccount_page_notify_url");
		ENTERPRISE_ACCOUNT_PAGE_NOTIFY_URL = ConfigReader.getConfig("enterprise_account_page_notify_url");
		PREAUTHURL =  ConfigReader.getConfig("jzh_url")+ConfigReader.getConfig("preauthurl");
		PREAUTHCANCELURL =  ConfigReader.getConfig("jzh_url")+ConfigReader.getConfig("preauthcancelurl");
		BRECHARGEURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("500002url");
		WITHDRAWURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("500003url");
		RESETPASSWORDURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("resetpasswordurl");
		FREEZEURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("freezeurl");
		TRANSFERBUURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("transferbuurl");
		TRANSFERBMUURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("transferbmuurl");
		WEBARTIFREGURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("webartifregurl");
		UNFREEZEURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("unfreezeurl");
		QUERYUSERINFSURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("queryuserinfsurl");
		PCQRECHARGE500405URL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("500405url");
		ARTIFREGURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("artifRegurl");
		BALANCEACTIONURL =ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("balanceactionurl");
		WTWITHDRAWURL=ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("wtwithdrawurl");
		QUERYCZTXURL=ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("querycztxurl");
		QUERYUSERINFS_V2=ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("queryuserinfs_v2");
		WTWITHDRAW_BACK_NOTIFY_URL=ConfigReader.getConfig("wtwithdraw_back_notify_url");
		USERCHANGECARDURL=ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("userchangcardurl");
		QUERYCHANGECARDURL=ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("querychangecardurl");
		TRANSFERBUANDFREEZE2FREEZEURL=ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("transferbuandfreeze2freezeurl");
		QUERYTXNURL=ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("querytxnurl");
	}
	
	
	/**
	 *冻结
	 * @param params
	 * @return
	 */
	public static BaseResult freeze(Map<String, Object>params){
		BaseResult br = new BaseResult();
		FreezeReqData data = new FreezeReqData();
		data.setVer(VER);
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, Integer.parseInt(params.get("uid").toString()), ""));
		data.setCust_no(params.get("cust_no").toString());
		data.setAmt(yuanToCent(params.get("amt").toString()));
		data.setRem((String)params.get("rem"));
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(FREEZE);
		sysFuiouNoticeLog.setAmt(params.get("amt").toString());
		sysFuiouNoticeLog.setIcd_name("冻结接口（直连）");
		sysFuiouNoticeLog.setUser_id(data.getCust_no());
		sysFuiouNoticeLog.setRemark(params.get("rem").toString());

		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		try {
			JSONObject jsonObject = JZHService.sendHttp(FREEZEURL, data);
			
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());
			
			if(!"0000".equals((String)jsonObject.get("resp_code"))){
				
				sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);
				
				br.setSuccess(false);
				br.setErrorMsg((String)jsonObject.get("resp_desc"));
				br.setErrorCode(jsonObject.getString("resp_code"));
				br.setMap(jsonObject);
				return br;
			}
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);	
			br.setMap(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	
	/**
	 * 预授权接口
	 * @param params
	 * @return
	 */
	public static BaseResult preAuth(Map<String, Object>params){
		BaseResult br = new BaseResult();
		PreAuthReqData data = new PreAuthReqData();
		data.setVer(VER);
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, (Integer)params.get("uid"), ""));
		data.setOut_cust_no((String)params.get("memberPhone"));
		data.setIn_cust_no((String)params.get("loanPhones"));
		data.setAmt(yuanToCent(params.get("amt").toString()));
		data.setRem((String)params.get("rem"));
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(PREAUTH);
		sysFuiouNoticeLog.setIcd_name("预授权接口（直连）");
		sysFuiouNoticeLog.setUser_id(data.getOut_cust_no());
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		try {
			JSONObject jsonObject = JZHService.sendHttp(PREAUTHURL, data);
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setContract_no(jsonObject.getString("contract_no"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);	
			br.setMap(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	/**
	 * 撤销预授权接口
	 * @param params
	 * @return
	 */
	public static BaseResult preAuthCancel(Map<String, Object>params){
		BaseResult br = new BaseResult();
		PreAuthCancelReqData data = new PreAuthCancelReqData();
		data.setVer(VER);
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, (Integer)params.get("uid"), ""));
		data.setOut_cust_no((String)params.get("memberPhone"));
		data.setIn_cust_no((String)params.get("loanPhones"));
		data.setRem((String)params.get("rem"));
		data.setContract_no((String)params.get("contract_no"));
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(PREAUTHCANCEL);
		sysFuiouNoticeLog.setIcd_name("撤销预授权接口（直连）");
		sysFuiouNoticeLog.setUser_id(data.getIn_cust_no());
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		try {
			JSONObject jsonObject = JZHService.sendHttp(PREAUTHCANCELURL, data);
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);	
			br.setMap(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	
	/**
	 * 企业开户
	 * @param drClaimsCustomer
	 * @return
	 */
	public static String enterpriseAccountData(DrClaimsCustomer drClaimsCustomer) {
		enterpriseAccountReq data = new enterpriseAccountReq();
		data.setVer(FuiouConfig.VER);
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, drClaimsCustomer.getId(), ""));
		data.setUser_id_from("");
		data.setCust_nm(drClaimsCustomer.getCompanyName()!=null?drClaimsCustomer.getCompanyName():"");
		data.setArtif_nm(drClaimsCustomer.getName()!=null?drClaimsCustomer.getName():"");
		data.setMobile_no(drClaimsCustomer.getPhone()!=null?drClaimsCustomer.getPhone():"");
		data.setCertif_id(drClaimsCustomer.getCertificateNo()!=null?drClaimsCustomer.getCertificateNo():"");
		data.setEmail(drClaimsCustomer.getCompanyMail()!=null?drClaimsCustomer.getCompanyMail():"");
		data.setCity_id("");
		data.setParent_bank_id("");
		data.setCapAcntNo("");
		data.setBank_nm("");
		data.setBack_notify_url(FuiouConfig.OPENACCOUNT_BACK_NOTIFY_URL);
		data.setPage_notify_url(FuiouConfig.ENTERPRISE_ACCOUNT_PAGE_NOTIFY_URL);
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(WEBARTIFREG);
		sysFuiouNoticeLog.setIcd_name("企业开户（页面）");
		sysFuiouNoticeLog.setUser_id(data.getMobile_no());
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		return sysFuiouNoticeLog.getReq_message();
	}
	
	/**
	 * 提现
	 * @param amt
	 * @param login_id
	 * @return
	 */
	public static String withdrawals(String login_id,String paymentNum,String amount){		
		WithdrawalsReqData data = new WithdrawalsReqData();
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(paymentNum);//流水号
		data.setLogin_id(login_id);//登录ID
		data.setAmt(yuanToCent(amount));//金额
		
		data.setPage_notify_url(OPENACCOUNT_PAGE_NOTIFY_URL);
		data.setBack_notify_url(OPENACCOUNT_BACK_NOTIFY_URL);

		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(WITHDRAW);
		sysFuiouNoticeLog.setUser_id(data.getLogin_id());
		sysFuiouNoticeLog.setAmt(amount);
		sysFuiouNoticeLog.setIcd_name("提现（页面）");
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);		
		
		return sysFuiouNoticeLog.getReq_message();
	}
	
	/**
	 * 充值
	 * @param fuiou_acnt
	 * @param amt
	 * @param user_id
	 * @return
	 */
	public static String rechargeData(Map<String,Object> map){
		RechargeReqData data = new RechargeReqData();
		String amt = yuanToCent(map.get("amt").toString());
		
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(map.get("mchnt_txn_ssn").toString());
		data.setLogin_id(map.get("login_id").toString());
		data.setAmt(amt);
		
		data.setPage_notify_url(FuiouConfig.OPENACCOUNT_PAGE_NOTIFY_URL);
		data.setBack_notify_url(FuiouConfig.OPENACCOUNT_BACK_NOTIFY_URL);
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(map.get("icd").toString());
		sysFuiouNoticeLog.setUser_id(data.getLogin_id());
		sysFuiouNoticeLog.setAmt(map.get("amt").toString());
		
		if(PCQRECHARGE500405.equals(map.get("icd").toString()))
			sysFuiouNoticeLog.setIcd_name("快捷充值pc（页面）");
		else
			sysFuiouNoticeLog.setIcd_name("快捷充值p2p（页面）");
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		
		return sysFuiouNoticeLog.getReq_message();
	}
	/**
	 * 网银充值
	 * @param fuiou_acnt
	 * @param amt
	 * @param user_id
	 * @return
	 */
	public static String onlineBankingRechargeData(Map<String,Object> param){
		FuiouOnlineBankingReq data = new FuiouOnlineBankingReq();
		String amt = yuanToCent(param.get("amt").toString());
//		data.setVer(FuiouConfig.VER);
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(param.get("mchnt_txn_ssn").toString());
		data.setAmt(amt);
		data.setLogin_id(param.get("login_id").toString());
		data.setPage_notify_url(FuiouConfig.OPENACCOUNT_PAGE_NOTIFY_URL);
		data.setBack_notify_url(FuiouConfig.OPENACCOUNT_BACK_NOTIFY_URL);
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(BRECHARGE);
		sysFuiouNoticeLog.setAmt(param.get("amt").toString());
		sysFuiouNoticeLog.setIcd_name("网银充值（页面）");
		if(param.get("pdId")!=null){
			sysFuiouNoticeLog.setPdId(Integer.parseInt(param.get("pdId").toString()));
		}
		sysFuiouNoticeLog.setUser_id(data.getLogin_id());
		if(param.get("project_no")!=null){
			sysFuiouNoticeLog.setProject_no(param.get("project_no").toString());
		}	
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		return sysFuiouNoticeLog.getReq_message();
	}
	/**
	 * 解冻
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public static BaseResult unFreeze(Map<String, String>params){
		BaseResult br = new BaseResult();
		br.setSuccess(true);
		UnFreezeReqData data = new UnFreezeReqData();
		data.setVer(VER);
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(params.get("mchnt_txn_ssn"));
		

		data.setCust_no(params.get("cust_no"));
		data.setAmt(yuanToCent(params.get("amt")));
		data.setRem(params.get("rem"));

		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setAmt(params.get("amt"));
		sysFuiouNoticeLog.setIcd_name("解冻（直连）");
		sysFuiouNoticeLog.setIcd(UNFREEZE);
		sysFuiouNoticeLog.setUser_id(params.get("cust_no"));
		sysFuiouNoticeLog.setRemark(params.get("rem"));
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		
		try {
			JSONObject json = JZHService.sendHttp(UNFREEZEURL,data);
			sysFuiouNoticeLog.setResp_code(json.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(json.getString("resp_desc"));
			sysFuiouNoticeLog.setMessage(json.toString());		
			
			if(!"0000".equals((String)json.get("resp_code"))){
				
				sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);		
				
				br.setSuccess(false);
				br.setErrorMsg((String)json.get("resp_desc"));
				br.setErrorCode(json.getString("resp_code"));
				br.setMap(json);
				return br;
			}
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);			
			br.setMap(json);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	
	
	/**
	 * 分转元
	 * @param amount
	 * @return
	 */
	public static BigDecimal centToYuan(String amount){
		return new BigDecimal(amount).divide(new BigDecimal("100"));
	}
	/**
	 * 元转分
	 * @param amount
	 * @return
	 */
	public static String yuanToCent(String amount){
		return new BigDecimal(amount).multiply(new BigDecimal("100")).intValue()+"";
	}
	/**
	 * 是否授权
	 * @param auth 授权状态码
	 * @param type 1投资 2还款
	 * @return
	 */
	public static boolean  isAuth(String auth_st,int type){
		
		if(auth_st != null && !"".equals(auth_st)){
			if(type == 1){
				auth_st = auth_st.substring(0,1);
			}else if(type ==2){
				auth_st = auth_st.substring(1,2);
			}
			if("1".equals(auth_st))
				return true;
		}else{
			return false;
		}
		return false;
	}
	
	
	/**
	 * 商户响应通知
	 */
	public static String  resNotice(String mchnt_txn_ssn,String resp_code){
		
		BaseRspdata data = new BaseRspdata();
		
		data.setResp_code(resp_code);
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(mchnt_txn_ssn);
		String message=StringUtil.encaJSONstring(data);
		System.out.println("存管商户响应通知:" + message);
		return message;
	}
	
	/**
	 * 金账户委托提现通知返回XML
	 * @param resp_code
	 * @param mchnt_cd
	 * @param mchnt_txn_ssn
	 * @return
	 * @throws Exception 
	 */
	public static String notifyRspXml(String resp_code,String mchnt_txn_ssn) {
	
		String plain = "<plain>";
		plain +="<resp_code>"+resp_code+"</resp_code>";
		plain +="<mchnt_cd>"+MCHNT_CD+"</mchnt_cd>";
		plain +="<mchnt_txn_ssn>"+mchnt_txn_ssn+"</mchnt_txn_ssn>";
		plain +="</plain>";
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<ap>");
		sb.append(plain);
		sb.append("<signature>"+SecurityUtils.sign(plain)+"</signature>");
		sb.append("</ap>");
		return sb.toString();
	}
	
	/**
	 * 划拨
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public static BaseResult transferBu(Map<String, String>params){
		BaseResult br = new BaseResult();
		br.setSuccess(true);
		TransferBuReqData data = new TransferBuReqData();
		data.setVer(VER);
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(params.get("mchnt_txn_ssn"));
		
		
		data.setOut_cust_no(params.get("out_cust_no"));//付款账户
		data.setIn_cust_no(params.get("in_cust_no"));//收款账户
		data.setAmt(yuanToCent(params.get("amt")));//金额 分为单位
		data.setRem(params.get("rem"));
		data.setContract_no(params.get("contract_no"));//预授权合同号

		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(TRANSFERBU);
		sysFuiouNoticeLog.setUser_id(data.getOut_cust_no());
		sysFuiouNoticeLog.setAmt(data.getAmt());
		sysFuiouNoticeLog.setIcd_name("划拨直连");//划拨类型
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		
		try {
			JSONObject json = JZHService.sendHttp(TRANSFERBUURL,data);
			//修改日志日志
			sysFuiouNoticeLog.setMessage(json.toString());
			sysFuiouNoticeLog.setResp_code(json.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(json.getString("resp_desc"));
			
			if(!"0000".equals((String)json.get("resp_code")) && !"3122".equals((String)json.get("resp_code")) ){//3122原授权交易已完成
				br.setSuccess(false);
				br.setErrorMsg((String)json.get("resp_desc"));
				br.setMap(json);
				sysFuiouNoticeLog.setStatus(3);
				sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);	
				return br;
			}
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);	
			br.setMap(json);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	
	/**
	 * 查询用户信息
	 * @param map
	 * @return
	 */
	public static BaseResult queryUserInfs(Map<String, Object> map){
		BaseResult br = new BaseResult();
		QueryUserInsfReqData data = new QueryUserInsfReqData();
		data.setVer(FuiouConfig.VER);
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, 1, ""));
		Date newDate=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		data.setMchnt_txn_dt(sdf.format(newDate));
		data.setUser_ids(map.get("userId").toString());
		
		
		try {
			JSONObject json = JZHService.sendHttp(QUERYUSERINFSURL,data);
			if(!"0000".equals((String)json.get("resp_code"))){
				br.setSuccess(false);
				br.setErrorMsg((String)json.get("resp_desc"));
				br.setMap(json);
				return br;
			}
			br.setMap(json);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
		
	}
	
	
	/**
	 * 转账
	 * @param map
	 * @return
	 */
	public static BaseResult transferBmu(Map<String, Object> map){
		BaseResult br = new BaseResult();
		TransferBmuReqData data = new TransferBmuReqData();
		data.setVer(FuiouConfig.VER);
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(map.get("mchnt_txn_ssn").toString());
		data.setOut_cust_no(map.get("out_cust_no").toString());
		data.setIn_cust_no(map.get("in_cust_no").toString());
		data.setAmt(yuanToCent(map.get("amt").toString()));
		data.setRem((String)map.get("rem"));
		data.setContract_no(map.get("contract_no").toString());
		
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(TRANSFERBMU);
		if(map.get("icd_name").toString().equals("平台提现手续费")){
			sysFuiouNoticeLog.setUser_id(data.getOut_cust_no());
		}else{
			sysFuiouNoticeLog.setUser_id(data.getIn_cust_no());
		}
		sysFuiouNoticeLog.setAmt(data.getAmt());
		sysFuiouNoticeLog.setRemark((String)map.get("rem"));		
		sysFuiouNoticeLog.setIcd_name(map.get("icd_name").toString());//划拨类型
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
				
		try {
			JSONObject json = JZHService.sendHttp(TRANSFERBMUURL,data);
			
			//修改日志
			sysFuiouNoticeLog.setMessage(json.toString());
			sysFuiouNoticeLog.setResp_code(json.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(json.getString("resp_desc"));
			sysFuiouNoticeLog.setStatus(2);
			if(!"0000".equals((String)json.get("resp_code"))){
				br.setSuccess(false);
				br.setMsg((String)json.get("resp_desc"));
				br.setMap(json);
				sysFuiouNoticeLog.setStatus(3);
				sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);
				return br;
			}
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);
			br.setMap(json);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
		
	}
	
	//法人注册开户
	public static BaseResult artifReg(Map<String, Object> map){
		BaseResult br = new BaseResult();
		ArtifRegReqData data=new ArtifRegReqData();
		data.setVer(FuiouConfig.VER);
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, 2, ""));
		data.setCust_nm(map.get("cust_nm").toString());
		data.setArtif_nm(map.get("artif_nm").toString());
		data.setCertif_id(map.get("certif_id").toString());
		data.setMobile_no(map.get("mobile_no").toString());
		data.setEmail(map.get("email").toString());
		data.setCity_id(map.get("city_id").toString());
		data.setParent_bank_id(map.get("parent_bank_id").toString());
		data.setBank_nm(map.get("bank_nm").toString());
		data.setCapAcntNo(map.get("capAcntNo").toString());
		data.setPassword(map.get("password").toString());
		data.setLpassword(map.get("lpassword").toString());
		data.setRem(map.get("rem").toString());

		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(ARTIFREG);
		sysFuiouNoticeLog.setUser_id(data.getCapAcntNo());
		sysFuiouNoticeLog.setIcd_name("法人注册开户 直连");
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
				
		try {
			JSONObject json = JZHService.sendHttp(ARTIFREGURL,data);
			
			//修改日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);
	
			if(!"0000".equals((String)json.get("resp_code"))){
				br.setSuccess(false);
				br.setErrorMsg((String)json.get("resp_desc"));
				br.setMap(json);
				return br;
			}
			br.setMap(json);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;		
	}
	
	
	/**
	 * 查询余额
	 * @param map
	 * @return
	 */
	public static BaseResult balanceAction(Map<String, String> map){
		BaseResult br = new BaseResult();
		BalanceActionReqData data = new BalanceActionReqData();
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, 1, ""));
		Date newDate=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		data.setMchnt_txn_dt(sdf.format(newDate));
		data.setCust_no(map.get("cust_no").toString());
				
		try {
			JSONObject json = JZHService.sendHttp(BALANCEACTIONURL,data);
			if(!"0000".equals((String)json.get("resp_code"))){
				br.setSuccess(false);
				br.setErrorMsg((String)json.get("resp_desc"));
				br.setMap(json);
				return br;
			}
			br.setMap(json);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
		
	}
	
	/**
	 * 委托提现
	 * @param map
	 * @return
	 */
	public static BaseResult wtwithdraw(Map<String, String> map){
		BaseResult br = new BaseResult();
		WtwithdrawReqData data=new WtwithdrawReqData();
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(map.get("mchnt_txn_ssn"));
		data.setRem(map.get("rem"));
		data.setLogin_id(map.get("login_id"));
		data.setBack_notify_url(FuiouConfig.WTWITHDRAW_BACK_NOTIFY_URL);	
		data.setAmt(yuanToCent(map.get("amt")));
		
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(WTWITHDRAW);
		sysFuiouNoticeLog.setUser_id(data.getLogin_id());
		sysFuiouNoticeLog.setAmt(data.getAmt());
		sysFuiouNoticeLog.setIcd_name("委托提现");//划拨类型
		sysFuiouNoticeLog.setRemark(map.get("rem"));
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		try {
			JSONObject json = JZHService.sendHttp(WTWITHDRAWURL,data);
			
			sysFuiouNoticeLog.setResp_code(json.getString("resp_code"));
			sysFuiouNoticeLog.setMessage(json.toString());	
			
			if(!"0000".equals((String)json.get("resp_code")) && !"0001".equals((String)json.get("resp_code"))){
				
				sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);
				
				br.setSuccess(false);
/*				br.setErrorMsg((String)json.get("resp_desc"));
*/				br.setErrorCode(json.getString("resp_code"));
				br.setMap(json);
				return br;
			}
			//修改日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);
			br.setMap(json);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
		
	}

	
	/** 充值提现查询
	 * @param map
	 * @return
	 */
	public static BaseResult QueryCzTx(Map<String, String> map){
		BaseResult br = new BaseResult();
		QueryCzTxReqData data=new QueryCzTxReqData();
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(map.get("mchnt_txn_ssn"));//m
		data.setVer(VER);
		data.setTxn_ssn(map.get("txn_ssn"));// m -交易流水
		data.setBusi_tp(map.get("busi_tp"));//m -PW11 充值PWTX 提现PWTP 退票
		data.setStart_time(map.get("start_time"));//m
		data.setEnd_time(map.get("end_time"));//m
		data.setTxn_st(map.get("txn_st"));//o-1 交易成功2 交易失败
		data.setCust_no(map.get("cust_no"));//o-个人 
		data.setPage_no(map.get("page_no"));//o-大于零的整数；默认为1;
		data.setPage_size(map.get("page_size"));//o-[10,100]之间整数; 默认值:10; 最大值:100;
	
		try {
			JSONObject json = JZHService.sendHttp(QUERYCZTXURL,data);
			br.setMap(json);
			if(!"0000".equals((String)json.get("resp_code"))){
				br.setSuccess(false);
				br.setErrorMsg((String)json.get("resp_desc"));
				br.setMap(json);
				return br;
			}else{			
				if(json.containsKey("total_number") && !"0".equals(json.get("total_number"))){
					JSONObject result = json.getJSONObject("results").getJSONObject("result");
					br.setMsg( result.get("txn_rsp_cd")+"|"+result.get("rsp_cd_desc"));
				}else{
					br.setMsg("定单不存在或交易失败");
				}
			}
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
		
	}
	/** 个人用户信息查询
	 * @param map
	 * @return
	 */
	public static BaseResult queryUserInfs_v2(Map<String, String> map){
		BaseResult br = new BaseResult();
		QueryUserInfs_v2ReqData data=new QueryUserInfs_v2ReqData();
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(map.get("mchnt_txn_ssn"));//m
		data.setVer(VER);
		data.setMchnt_txn_dt(map.get("mchnt_txn_dt"));//商户查询当前日期yyyyMMdd
		data.setUser_bankCards(map.get("user_bankCards"));//待查询的登录银行卡列表-M
		data.setUser_idNos(map.get("user_idNos"));//待查询查询注册的身份证-M
		data.setUser_ids(map.get("user_ids"));//查询注册的手机号
	
		try {
			JSONObject json = JZHService.sendHttp(QUERYUSERINFS_V2,data);
			if("0000".equals((String)json.get("resp_code"))){
				br.setMap(json);
				br.setSuccess(true);
			}else{			
				br.setErrorMsg((String)json.get("resp_desc"));
			}
		} catch(RuntimeException re){
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}

	/**
	 * 用户更换银行卡查询
	 * @param map
	 * @return
	 */
	public static BaseResult queryChangeCard(Map<String, Object> map){
		BaseResult br = new BaseResult();
		QueryChangeCardReqData data=new QueryChangeCardReqData();
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(map.get("mchnt_txn_ssn").toString());
		data.setLogin_id(map.get("login_id").toString());
		data.setTxn_ssn(map.get("txn_ssn").toString());
		
		try {
			JSONObject json = JZHService.sendHttp(QUERYCHANGECARDURL,data);
			if(!"0000".equals((String)json.get("resp_code"))){
				br.setSuccess(false);
				br.setErrorMsg((String)json.get("resp_desc"));
				br.setMap(json);
				return br;
			}
			br.setMap(json);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;	
	}

	
	/**
	 * 用户更换银行卡
	 * @param map
	 * @param file1
	 * @param file2
	 * @return
	 * @throws Exception
	 */
	public static BaseResult  userChangeCard(Map<String, Object> map,File file1,File file2)throws Exception{
			BaseResult br=new BaseResult();
		    UserChangeCardReqData userChangeCardReqData=new UserChangeCardReqData();
		    userChangeCardReqData.setMchnt_cd(FuiouConfig.MCHNT_CD);
		    userChangeCardReqData.setMchnt_txn_ssn(Utils.createOrderNo(6, 1, null));
		    userChangeCardReqData.setLogin_id(map.get("login_id").toString());
		    userChangeCardReqData.setCity_id(map.get("city_id").toString());
		    userChangeCardReqData.setCard_no(map.get("card_no").toString());
		    userChangeCardReqData.setBank_cd(map.get("bank_cd").toString());
			
			//记录日志
			SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog();
			sysFuiouNoticeLog = new SysFuiouNoticeLog(userChangeCardReqData);
			sysFuiouNoticeLog.setIcd(USERCHANGECARD);
			sysFuiouNoticeLog.setUser_id(userChangeCardReqData.getLogin_id());
			sysFuiouNoticeLog.setIcd_name("用户修改银行卡直连");
			sysFuiouNoticeLog.setMobile(map.get("mobile").toString());
			sysFuiouNoticeLog.setUpdate_status(1);//发起申请
			sysFuiouNoticeLog.setCard_no(map.get("card_no").toString());
			sysFuiouNoticeLog.setRemark(map.get("remark").toString());
			sysFuiouNoticeLog.setBank_cd(map.get("bank_cd").toString());

			sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
			
			userChangeCardReqData.setFile1(file1);
			userChangeCardReqData.setFile2(file2);
			
			String  result = null; 
			CloseableHttpClient httpClient = null;
	        CloseableHttpResponse response = null;
	        
	            httpClient = HttpClients.createDefault();
	            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
	            HttpPost httpPost = new HttpPost(USERCHANGECARDURL);
	            // 把文件转换成流对象FileBody
	            
	            FileBody bin = new FileBody(userChangeCardReqData.getFile1());
	            FileBody bin2 =new FileBody(userChangeCardReqData.getFile2());
	            StringBody binStr = new StringBody("", Consts.UTF_8);
	            StringBody bin2Str = new StringBody("", Consts.UTF_8);
	            
	            
	            StringBody mchnt_cd = new StringBody(userChangeCardReqData.getMchnt_cd(), Consts.UTF_8);
	            StringBody mchnt_txn_ssn = new StringBody(userChangeCardReqData.getMchnt_txn_ssn(), Consts.UTF_8);
	            StringBody login_id = new StringBody(userChangeCardReqData.getLogin_id(),Consts.UTF_8);
	            StringBody city_id = new StringBody(userChangeCardReqData.getCity_id().toString(), Consts.UTF_8);
	            StringBody bank_cd = new StringBody(userChangeCardReqData.getBank_cd().toString(),Consts.UTF_8);
	            StringBody card_no = new StringBody(userChangeCardReqData.getCard_no(), Consts.UTF_8);
	            StringBody signature = new StringBody(SecurityUtils.sign(userChangeCardReqData.regSignVal()),Consts.UTF_8);
	            
	            HttpEntity reqEntity = MultipartEntityBuilder.create()
	                    .addPart("file1", bin.getFilename().equals("")?binStr:bin)
	            		.addPart("file2", bin2.getFilename().equals("")?bin2Str:bin2)
	                    .addPart("mchnt_cd", mchnt_cd)
	                    .addPart("mchnt_txn_ssn", mchnt_txn_ssn)
	                    .addPart("login_id", login_id)
	                    .addPart("city_id", city_id)
	                    .addPart("bank_cd", bank_cd)
	                    .addPart("card_no", card_no)
	                    .addPart("signature", signature)
	                    .build();
	            httpPost.setEntity(reqEntity);
	            // 发起请求 并返回请求的响应
	            response = httpClient.execute(httpPost);
	         	// 获取响应对象
	            HttpEntity resEntity = response.getEntity();
	            if (resEntity != null) {
	                result = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
	                // 打印响应内容
	                System.out.println(result);
	            }
	            // 销毁
	            EntityUtils.consume(resEntity);
	            
	            
	            if(StringUtils.isEmpty(result)){
	            	br.setErrorCode("返回报文为空");
	            	br.setSuccess(false);
	    		}
	    		JSONObject json =JZHService.verifySignAndParse(result);
	    		if(!"0000".equals((String)json.get("resp_code"))){			
					sysFuiouNoticeLog.setResp_code((String)json.get("resp_code"));
					sysFuiouNoticeLog.setResp_desc((String)json.get("resp_desc"));
					sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);
					
					br.setSuccess(false);
					br.setErrorMsg((String)json.get("resp_desc"));
					br.setMap(json);
					return br;
				}
	    		//修改日志
				sysFuiouNoticeLog.setStatus(2);//成功
				sysFuiouNoticeLog.setUpdate_status(2);//待审核
				sysFuiouNoticeLog.setResp_code((String)json.get("resp_code"));
				sysFuiouNoticeLog.setResp_desc((String)json.get("resp_desc"));
				sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);
				br.setMap(json);
				br.setSuccess(true);
	    		return br;
		
	}
	
	
	/**
	 * 冻结到冻结
	 * @param map
	 * @return
	 */
	public static BaseResult transferBuAndFreeze2Freeze(Map<String, String> map){
		BaseResult br = new BaseResult();
		TransferBuAndFreeze2FreezeReqData data=new TransferBuAndFreeze2FreezeReqData();
		data.setVer(VER);
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(map.get("mchnt_txn_ssn"));
			
		data.setOut_cust_no(map.get("out_cust_no"));//付款账户
		data.setIn_cust_no(map.get("in_cust_no"));//收款账户
		data.setAmt(yuanToCent(map.get("amt")));//金额 分为单位
		data.setRem(map.get("rem"));
		
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(TRANSFERBUANDFREEZE2FREEZE);
		sysFuiouNoticeLog.setUser_id(data.getOut_cust_no());
		sysFuiouNoticeLog.setAmt(data.getAmt());
		sysFuiouNoticeLog.setIcd_name("冻结到冻结");//划拨类型
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		
		try {
			JSONObject json = JZHService.sendHttp(TRANSFERBUANDFREEZE2FREEZEURL,data);
			//修改日志
			
			sysFuiouNoticeLog.setResp_code(json.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(json.getString("resp_desc"));
			sysFuiouNoticeLog.setMessage(json.toString());
			
			sysFuiouNoticeLog.setStatus(2);//成功
			
			if(!"0000".equals((String)json.get("resp_code"))){
				br.setSuccess(false);
				br.setErrorMsg((String)json.get("resp_desc"));
				br.setMap(json);
				sysFuiouNoticeLog.setStatus(3);//失败
				sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);
				return br;
			}
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);
			br.setMap(json);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;	
	}
	
	/** 交易查询
	 * @param map
	 * @return
	 */
	public static BaseResult queryTxn(Map<String, String> map){
		BaseResult br = new BaseResult();
		QueryTxnReqData data=new QueryTxnReqData();
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(map.get("mchnt_txn_ssn"));//m
		data.setTxn_ssn(map.get("txn_ssn"));// m -交易流水
		data.setBusi_tp(map.get("busi_tp"));//m -PW11 充值PWTX 提现PWTP 退票
		data.setStart_day(map.get("start_day"));//m
		data.setEnd_day(map.get("end_day"));//m
		data.setTxn_st(map.get("txn_st"));//o-1 交易成功2 交易失败
		data.setCust_no(map.get("cust_no"));//o-个人 
		data.setPage_no(map.get("page_no"));//o-大于零的整数；默认为1;
		data.setPage_size(map.get("page_size"));//o-[10,100]之间整数; 默认值:10; 最大值:100;
	
		try {
			JSONObject json = JZHService.sendHttp(QUERYTXNURL,data);
			br.setMap(json);
			if(!"0000".equals((String)json.get("resp_code"))){
				br.setSuccess(false);
				br.setErrorMsg((String)json.get("resp_desc"));
				br.setMap(json);
				return br;
			}else{			
				if(json.containsKey("total_number") && !"0".equals(json.get("total_number"))){
					JSONObject result = json.getJSONObject("results").getJSONObject("result");
					br.setMsg( result.get("txn_rsp_cd")+"|"+result.get("rsp_cd_desc"));
				}else{
					br.setMsg("定单不存在或交易失败");
				}
			}
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setErrorMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
		
	}
	//日志队列
	public static void incrMg(SysFuiouNoticeLog fuiouNoticeLog) {
		fuiouConfig.redisClientTemplate.lpush("fuiouMessageList".getBytes(), SerializeUtil.serialize(fuiouNoticeLog));
	}
}
