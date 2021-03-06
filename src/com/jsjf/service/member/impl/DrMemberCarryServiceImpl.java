package com.jsjf.service.member.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.SmsSendUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.member.DrCarryParamDAO;
import com.jsjf.dao.member.DrCompanyFundsLogDAO;
import com.jsjf.dao.member.DrMemberBankDAO;
import com.jsjf.dao.member.DrMemberCarryDAO;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.member.DrMemberFundsDAO;
import com.jsjf.dao.member.DrMemberFundsLogDAO;
import com.jsjf.dao.member.DrMemberFundsRecordDAO;
import com.jsjf.dao.member.JsCompanyAccountLogDAO;
import com.jsjf.dao.system.JsMerchantMarketingDAO;
import com.jsjf.model.member.DrCompanyFundsLog;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.member.DrMemberCarry;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.member.DrMemberFundsLog;
import com.jsjf.model.member.DrMemberFundsRecord;
import com.jsjf.model.member.JsCompanyAccountLog;
import com.jsjf.model.system.JsMerchantMarketing;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.member.DrMemberCarryService;
import com.jsjf.service.system.SysFuiouNoticeLogService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jytpay.config.MockClientMsgBase;
import com.jytpay.vo.JYTResultData;
import com.jytpay.vo.JYTSendData;
import com.jzh.FuiouConfig;
import com.jzh.data.WithdrawalsRspData;
import com.jzh.service.JZHService;

import net.sf.json.JSONObject;

@Service
@Transactional
public class DrMemberCarryServiceImpl implements DrMemberCarryService {
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private DrMemberCarryDAO drMemberCarryDAO;
	@Autowired
	private DrMemberBankDAO drMemberBankDAO;
	@Autowired
	private DrMemberFundsDAO drMemberFundsDAO;
	@Autowired
	private DrMemberDAO drMemberDAO;
	@Autowired
	private SysMessageLogService sysMessageLogService;
	@Autowired
	private DrMemberFundsLogDAO drMemberFundsLogDAO;
	@Autowired
	private DrMemberFundsRecordDAO drMemberFundsRecordDAO;
	@Autowired
	public DrCompanyFundsLogDAO drCompanyFundsLogDAO;
	@Autowired
	public DrCarryParamDAO drCarryParamDAO;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private JsCompanyAccountLogDAO jsCompanyAccountLogDAO;
	@Autowired
	public SysFuiouNoticeLogService sysFuiouNoticeLogService;
	@Autowired
	private JsMerchantMarketingDAO jsMerchantMarketingDAO;
	
	@Override
	public BaseResult getMemberCarryList(DrMemberCarry drMemberCarry, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		if(drMemberCarry.getStartDate()!=null){
			map.put("startDate", sdf.format(drMemberCarry.getStartDate()));
		}
		if(drMemberCarry.getEndDate()!=null){
			map.put("endDate", sdf.format(drMemberCarry.getEndDate()));
		}
		map.put("audStartDate", Utils.format(drMemberCarry.getAudStartDate(), "yyyy-MM-dd"));
		map.put("audEndDate", Utils.format(drMemberCarry.getAudEndDate(), "yyyy-MM-dd"));
		map.put("realName", drMemberCarry.getRealName());
		map.put("phone", drMemberCarry.getPhone());
		map.put("recommCodes", drMemberCarry.getRecommCodes());
		map.put("status", drMemberCarry.getStatus());
		map.put("paymentNum", drMemberCarry.getPaymentNum());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		map.put("startamount",drMemberCarry.getStartamount()); 
		map.put("endamount",drMemberCarry.getEndamount()); 
		map.put("type", drMemberCarry.getType());
		map.put("mgid", drMemberCarry.getMgid());
		List<DrMemberCarry> list = drMemberCarryDAO.getDrMemberCarryList(map);
		Integer total = drMemberCarryDAO.getDrMemberCarryCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}


	public Map<String, Object> getDrMemberCarrySum(DrMemberCarry drMemberCarry,String startDate,String endDate){
		Double memberCarrySum = 0.0;	
		Double memberBalanceSum = 0.0;
		Double memberExpireSum = 0.0;
		Double memberBalanceSumFuiou = 0.0;
		Map<String,Object> map=new HashMap<String,Object>();

		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("audStartDate", Utils.format(drMemberCarry.getAudStartDate(), "yyyy-MM-dd"));
		map.put("audEndDate", Utils.format(drMemberCarry.getAudEndDate(), "yyyy-MM-dd"));
		map.put("realName", drMemberCarry.getRealName());
		map.put("phone", drMemberCarry.getPhone());
		map.put("status", drMemberCarry.getStatus());
		map.put("type", drMemberCarry.getType());
		map.put("mgid", drMemberCarry.getMgid());
		memberCarrySum = drMemberCarryDAO.getDrMemberCarrySum(map);
		memberBalanceSum = drMemberFundsDAO.getDrMemberBalanceSum();
		memberBalanceSumFuiou = drMemberFundsDAO.getDrMemberBalanceSumByFuiou();
		memberExpireSum = drMemberFundsDAO.getDrMemberExpireSum();

		map.clear();
		map.put("memberCarrySum", memberCarrySum == null ? 0.0d : memberCarrySum);
		map.put("memberBalanceSum",memberBalanceSum == null ? 0.0d : memberBalanceSum);
		map.put("memberExpireSum",memberExpireSum == null ? 0.0d : memberExpireSum);
		map.put("memberBalanceSumFuiou",memberBalanceSumFuiou == null ? 0.0d : memberBalanceSumFuiou);
		return map;
	}

	@Override
	public void updateMemberCarryRefuse(int id,SysUsersVo usersVo) throws Exception {
		DrMemberCarry drMemberCarry = drMemberCarryDAO.selectDrMemberCarryById(id);
		if(0 == drMemberCarry.getStatus()){
			drMemberCarry.setStatus(4);//提现状态，0未处理 1处理中 2成功 3失败  4拒绝
			drMemberCarry.setAuditId(usersVo.getUserKy().intValue());
			drMemberCarry.setAuditTime(new Date());
			drMemberCarryDAO.updateStatusById(drMemberCarry);
			
			//解冻会员资金
			DrMemberFunds drMemberFunds = drMemberFundsDAO.queryDrMemberFundsByUid(drMemberCarry.getUid());
			drMemberFunds.setBalance(Utils.nwdBcadd(drMemberFunds.getBalance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
			drMemberFunds.setFreeze(Utils.nwdBcsub(drMemberFunds.getFreeze(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
			drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);
			
			DrMemberFundsRecord record = drMemberFundsRecordDAO.selectByParam(null, drMemberCarry.getPaymentNum());
			
			record.setStatus(2);
			record.setBalance(drMemberFunds.getBalance());
			drMemberFundsRecordDAO.updateStatusById(record);
			
			DrMemberFundsLog log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage()),14,1,
					"提现金额解冻：【"+ drMemberCarry.getAmount().add(drMemberCarry.getPoundage()).setScale(2)  + "】");
			drMemberFundsLogDAO.insertDrMemberFundsLog(log);
			
			DrMember member = drMemberDAO.selectByPrimaryKey(drMemberCarry.getUid());
			
			String sms = PropertyUtil.getProperties("carryFail")
					.replace("${1}", member.getRealName())
					.replace("${2}", Utils.format(drMemberCarry.getAddTime(), "M月d日"))
					.replace("${3}", drMemberCarry.getAmount().setScale(2).toString());
			SysMessageLog logs = new SysMessageLog(member.getUid(), sms, 11, null, member.getMobilephone());
			
			sysMessageLogService.sendMsg(logs);
		}
	}

	@Override
	public int updateMemberCarryAudit(DrMemberCarry drMemberCarry,SysUsersVo usersVo) throws Exception {
		
		drMemberCarry.setAuditId(usersVo.getUserKy().intValue());
		drMemberCarry.setAuditTime(new Date());
		drMemberCarry.setStatus(5);
		int count = drMemberCarryDAO.updateStatusById(drMemberCarry);
		
		return count;
	}
	
	@Override
	public BaseResult saveJYTpayment(int id,SysUsersVo usersVo) throws Exception {
		BaseResult result = new BaseResult();
		DrMemberCarry drMemberCarry = drMemberCarryDAO.selectDrMemberCarryById(id);
		DrMemberBank drMemberBank = drMemberBankDAO.selectDrMemberBankById(drMemberCarry.getBankId());
		DrMember drMember = drMemberDAO.selectByPrimaryKey(drMemberCarry.getUid());
		
		JYTSendData sendData = new JYTSendData();
		sendData.setTran_code(MockClientMsgBase.PAY_TRAN_CODE);
		sendData.setBank_name(drMemberBank.getBankName());
		sendData.setAccount_no(drMemberBank.getBankNum());
		sendData.setAccount_name(drMember.getRealName());
		sendData.setAccount_type("00");
		sendData.setTran_amt(drMemberCarry.getAmount());
		sendData.setCurrency(MockClientMsgBase.CURRENCY);
		sendData.setBsn_code(MockClientMsgBase.PAY_BSN_CODE);
		sendData.setTran_flowid(drMemberCarry.getPaymentNum());
		sendData.setMobile(drMemberBank.getMobilePhone()==null?"":drMemberBank.getMobilePhone());
		
		DrMemberFunds drMemberFunds = drMemberFundsDAO.queryDrMemberFundsByUid(drMemberCarry.getUid()); // 获取会员资金信息
		DrMemberFundsRecord record = drMemberFundsRecordDAO.selectByParam(null, drMemberCarry.getPaymentNum());
		
		JYTResultData resultData = MockClientMsgBase.getInstance().payClientMsg(sendData);
		
		if("S0000000".equals(resultData.getResp_code()) && "01".equals(resultData.getTran_state())){
			drMemberCarry.setStatus(2);//提现状态，0未处理 1处理中 2成功 3失败  4拒绝 5超时
			
			record.setStatus(3);
			drMemberFundsRecordDAO.updateStatusById(record);
			
			//解冻会员资金
			drMemberFunds.setBalance(Utils.nwdBcadd(drMemberFunds.getBalance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
			drMemberFunds.setFreeze(Utils.nwdBcsub(drMemberFunds.getFreeze(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
			
			DrMemberFundsLog log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage()),14,1,
					"提现金额解冻：【"+ drMemberCarry.getAmount().add(drMemberCarry.getPoundage()).setScale(2)  + "】");
			drMemberFundsLogDAO.insertDrMemberFundsLog(log);
			
			log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage()),5,0,
					"提现金额：【"+ drMemberCarry.getAmount().setScale(2) +"】手续费：【"+drMemberCarry.getPoundage().setScale(2)+"】");
			drMemberFundsLogDAO.insertDrMemberFundsLog(log);
			
			if(drMemberCarry.getPoundage().intValue() == 2){
				DrCompanyFundsLog drCompanyFundsLog = new DrCompanyFundsLog(4, drMemberCarry.getUid(), null, drMemberCarry.getPoundage(), 1, "提现手续费：【"+ drMemberCarry.getPoundage().setScale(2)  + "】", null);
				drCompanyFundsLogDAO.insertDrCompanyFundsLog(drCompanyFundsLog);
			}
			drMemberFunds.setBalance(Utils.nwdBcsub(drMemberFunds.getBalance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
			// 提现总额
			drMemberFunds.setCarryCount(drMemberFunds.getCarryCount().add(drMemberCarry.getAmount()).add(drMemberCarry.getPoundage()));
			drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);
			
		}else if("03".equals(resultData.getTran_state()) || (resultData.getResp_code().startsWith("E") && !"E0000000".equals(resultData.getResp_code()))){
			drMemberCarry.setStatus(3);//提现状态，0未处理 1处理中 2成功 3失败  4拒绝
			drMemberCarry.setReason(resultData.getResp_desc());
			
			//解冻会员资金
			drMemberFunds.setBalance(Utils.nwdBcadd(drMemberFunds.getBalance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
			drMemberFunds.setFreeze(Utils.nwdBcsub(drMemberFunds.getFreeze(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
			drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);
			
			DrMemberFundsLog log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage()),14,1,
					"提现金额解冻：【"+ drMemberCarry.getAmount().add(drMemberCarry.getPoundage()).setScale(2)  + "】");
			drMemberFundsLogDAO.insertDrMemberFundsLog(log);
			
			record.setStatus(2);
			record.setBalance(drMemberFunds.getBalance());
			drMemberFundsRecordDAO.updateStatusByNo(record);
			
			String sms = PropertyUtil.getProperties("carryFail")
					.replace("${1}", drMember.getRealName())
					.replace("${2}", Utils.format(drMemberCarry.getAddTime(), "M月d日"))
					.replace("${3}", drMemberCarry.getAmount().setScale(2).toString());
			SysMessageLog logs = new SysMessageLog(drMember.getUid(), sms, 11, null, drMember.getMobilephone());
			
			sysMessageLogService.sendMsg(logs);
		}else{
			drMemberCarry.setStatus(1);//提现状态，0未处理 1处理中 2成功 3失败  4拒绝
		}
		drMemberCarryDAO.updateStatusById(drMemberCarry);
		result.setSuccess(true);
		result.setMsg("审核成功！");
		return result;
	}
	
	@Override
	public void receiveNotifyJYT(HttpServletRequest req,HttpServletResponse resp) throws Exception {
	 	resp.setCharacterEncoding("UTF-8");
	 	String merchantId = req.getParameter("merchant_id");
	 	String xmlEnc = req.getParameter("xml_enc");
	 	String keyEnc = req.getParameter("key_enc");
	 	String sign = req.getParameter("sign");
	 	JYTResultData resultData = MockClientMsgBase.getInstance().resultNoticeMsg(merchantId,xmlEnc,keyEnc,sign);
        DrMemberCarry drMemberCarry = drMemberCarryDAO.selectDrMemberCarryByPaymentNum(resultData.getOri_tran_flowid());
		if(Utils.isObjectNotEmpty(drMemberCarry)){
			DrMember member = drMemberDAO.selectByPrimaryKey(drMemberCarry.getUid()) ;
			DrMemberFundsRecord record = drMemberFundsRecordDAO.selectByParam(null, drMemberCarry.getPaymentNum());
			
			if("S0000000".equals(resultData.getTran_resp_code()) && "01".equals(resultData.getTran_state())){
				synchronized(drMemberCarry.getUid()){
					drMemberCarry.setStatus(2);
					drMemberCarryDAO.updateStatusById(drMemberCarry);
					record.setStatus(3);
					drMemberFundsRecordDAO.updateStatusById(record);
					
					//解冻会员资金
					DrMemberFunds drMemberFunds = drMemberFundsDAO.queryDrMemberFundsByUid(drMemberCarry.getUid());
					drMemberFunds.setBalance(Utils.nwdBcadd(drMemberFunds.getBalance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
					drMemberFunds.setFreeze(Utils.nwdBcsub(drMemberFunds.getFreeze(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
					
					DrMemberFundsLog log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage()),14,1,
							"提现金额解冻：【"+ drMemberCarry.getAmount().add(drMemberCarry.getPoundage()).setScale(2)  + "】");
					drMemberFundsLogDAO.insertDrMemberFundsLog(log);
					
					log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage()),5,0,
							"提现金额：【"+ drMemberCarry.getAmount().setScale(2) +"】手续费：【"+drMemberCarry.getPoundage().setScale(2)+"】");
					drMemberFundsLogDAO.insertDrMemberFundsLog(log);
						
					if(drMemberCarry.getPoundage().intValue() == 2){
						DrCompanyFundsLog drCompanyFundsLog = new DrCompanyFundsLog(4, drMemberCarry.getUid(), null, drMemberCarry.getPoundage(), 1, "提现手续费：【"+ drMemberCarry.getPoundage().setScale(2)  + "】", null);
						drCompanyFundsLogDAO.insertDrCompanyFundsLog(drCompanyFundsLog);
					}
					
					drMemberFunds.setBalance(Utils.nwdBcsub(drMemberFunds.getBalance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
					// 提现总额
					drMemberFunds.setCarryCount(drMemberFunds.getCarryCount().add(drMemberCarry.getAmount()).add(drMemberCarry.getPoundage()));
					drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);
				}
			}else if("03".equals(resultData.getTran_state())){
				synchronized(drMemberCarry.getUid()){
					drMemberCarry.setStatus(3);//提现状态，0未处理 1处理中 2成功 3失败  4拒绝
					drMemberCarry.setReason(Utils.strIsNull(resultData.getRemark())?resultData.getResp_desc():resultData.getRemark());
					drMemberCarryDAO.updateStatusById(drMemberCarry);
					
					//解冻会员资金
					DrMemberFunds drMemberFunds = drMemberFundsDAO.queryDrMemberFundsByUid(drMemberCarry.getUid());
					drMemberFunds.setBalance(Utils.nwdBcadd(drMemberFunds.getBalance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
					drMemberFunds.setFreeze(Utils.nwdBcsub(drMemberFunds.getFreeze(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
					drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);

					DrMemberFundsLog log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage()),14,1,
							"提现金额解冻：【"+ drMemberCarry.getAmount().add(drMemberCarry.getPoundage()).setScale(2)  + "】");
					drMemberFundsLogDAO.insertDrMemberFundsLog(log);
					
					record.setStatus(2);
					record.setBalance(drMemberFunds.getBalance());
					drMemberFundsRecordDAO.updateStatusByNo(record);
					
					String sms = PropertyUtil.getProperties("carryFail")
							.replace("${1}", member.getRealName())
							.replace("${2}", Utils.format(drMemberCarry.getAddTime(), "M月d日"))
							.replace("${3}", drMemberCarry.getAmount().setScale(2).toString());
					SysMessageLog logs = new SysMessageLog(member.getUid(), sms, 11, null, member.getMobilephone());
					
					sysMessageLogService.sendMsg(logs);
				}
			}
		}
	}
	
	@Override
	public void updatePaymentResult() throws Exception {
		long t1 = System.currentTimeMillis(); 
		log.info(Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+":开始实时查询代付结果");
		
		List<DrMemberCarry> list = drMemberCarryDAO.getAllDrMemberCarry();
    	if(!Utils.isEmptyList(list)){
    		for(DrMemberCarry drMemberCarry : list){
    			if(Utils.isObjectNotEmpty(drMemberCarry)){
        			JYTSendData sendData = new JYTSendData();
        			sendData.setOri_tran_flowid(drMemberCarry.getPaymentNum());
        			sendData.setTran_code(MockClientMsgBase.QUERY_PAY_TRAN_CODE);
        			JYTResultData resultData = MockClientMsgBase.getInstance().payClientMsg(sendData);
        			if("S0000000".equals(resultData.getResp_code())){
    					DrMemberFundsRecord record = drMemberFundsRecordDAO.selectByParam(null, drMemberCarry.getPaymentNum());
    					DrMember member = drMemberDAO.selectByPrimaryKey(drMemberCarry.getUid()) ;
    					if("S0000000".equals(resultData.getTran_resp_code()) && "01".equals(resultData.getTran_state())){
    						drMemberCarry.setStatus(2);
    						drMemberCarryDAO.updateStatusById(drMemberCarry);
    						record.setStatus(3);
    						drMemberFundsRecordDAO.updateStatusById(record);
    						
    						//解冻会员资金
    						DrMemberFunds drMemberFunds = drMemberFundsDAO.queryDrMemberFundsByUid(drMemberCarry.getUid());
    						drMemberFunds.setBalance(Utils.nwdBcadd(drMemberFunds.getBalance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
    						drMemberFunds.setFreeze(Utils.nwdBcsub(drMemberFunds.getFreeze(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
    						
    						DrMemberFundsLog log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage()),14,1,
    								"提现金额解冻：【"+ drMemberCarry.getAmount().add(drMemberCarry.getPoundage()).setScale(2)  + "】");
    						drMemberFundsLogDAO.insertDrMemberFundsLog(log);
    						
    						log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage()),5,0,
    								"提现金额：【"+ drMemberCarry.getAmount().setScale(2) +"】手续费：【"+drMemberCarry.getPoundage().setScale(2)+"】");
    						drMemberFundsLogDAO.insertDrMemberFundsLog(log);
    							
    						if(drMemberCarry.getPoundage().intValue() == 2){
    							DrCompanyFundsLog drCompanyFundsLog = new DrCompanyFundsLog(4, drMemberCarry.getUid(), null, drMemberCarry.getPoundage(), 1, "提现手续费：【"+ drMemberCarry.getPoundage().setScale(2)  + "】", null);
    							drCompanyFundsLogDAO.insertDrCompanyFundsLog(drCompanyFundsLog);
    						}
    						drMemberFunds.setBalance(Utils.nwdBcsub(drMemberFunds.getBalance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
    						// 提现总额
    						drMemberFunds.setCarryCount(drMemberFunds.getCarryCount().add(drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
    						drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);
    						
    					}else if("03".equals(resultData.getTran_state())){
    						drMemberCarry.setStatus(3);//提现状态，0未处理 1处理中 2成功 3失败  4拒绝
    						drMemberCarry.setReason(resultData.getRemark());
    						drMemberCarryDAO.updateStatusById(drMemberCarry);
    						
    						//解冻会员资金
    						DrMemberFunds drMemberFunds = drMemberFundsDAO.queryDrMemberFundsByUid(drMemberCarry.getUid());
    						drMemberFunds.setBalance(Utils.nwdBcadd(drMemberFunds.getBalance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
    						drMemberFunds.setFreeze(Utils.nwdBcsub(drMemberFunds.getFreeze(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
    						drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);

    						DrMemberFundsLog log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage()),14,1,
    								"提现金额解冻：【"+ drMemberCarry.getAmount().add(drMemberCarry.getPoundage()).setScale(2)  + "】");
    						drMemberFundsLogDAO.insertDrMemberFundsLog(log);
    						
    						record.setStatus(2);
    						record.setBalance(drMemberFunds.getBalance());
    						drMemberFundsRecordDAO.updateStatusByNo(record);
    						
    						String sms = PropertyUtil.getProperties("carryFail")
    								.replace("${1}", member.getRealName())
    								.replace("${2}", Utils.format(drMemberCarry.getAddTime(), "M月d日"))
    								.replace("${3}", drMemberCarry.getAmount().setScale(2).toString());
    						SysMessageLog logs = new SysMessageLog(member.getUid(), sms, 11, null, member.getMobilephone());
    						
    						sysMessageLogService.sendMsg(logs);
    					}
        			}
    			}
    		}
    	}
		//校验处理结果
    	List<DrMemberCarry> unhandleList = drMemberCarryDAO.getAllDrMemberCarry();
    	if(null != unhandleList && !unhandleList.isEmpty()){
    		try {
				SmsSendUtil.sendMsg("15801868241,15821421008", "代付自动任务处理，还有" + unhandleList.size() + "条未处理！");
			} catch (Exception e1) {
				log.error(e1);
			}
    	}
		long t2 = System.currentTimeMillis(); // 排序后取得当前时间  
        Calendar c = Calendar.getInstance();  
        c.setTimeInMillis(t2 - t1);  
  
        log.info("结束实时查询代付结果 耗时: " + c.get(Calendar.MINUTE) + "分 "  + c.get(Calendar.SECOND) + "秒 " + c.get(Calendar.MILLISECOND)  + " 毫秒"); 
	}

	@Override
	public DrMemberCarry selectDrMemberCarryById(int id) {
		return drMemberCarryDAO.selectDrMemberCarryById(id);
	}
	
	@Override
	public void withdrawalsData(JSONObject json) throws Exception {
			JSONObject message = json.getJSONObject("message");
			
			if(JZHService.verifySignAsynNotice(new WithdrawalsRspData(message), json.getString("signature"))){
				System.out.println("["+Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"]提现验签成功---------------------");
				boolean lockFlag = false;
				
				String paymentnum=(String)message.get("mchnt_txn_ssn");
				String login_id=(String)message.get("login_id");
				DrMember member=drMemberDAO.selectDrMemberByMobilePhone(login_id);
				String  withdrawalsData= "withdrawalsData_"+(String)message.get("mchnt_txn_ssn");
				
				Map<String, Object> map=new HashMap<>();
				map.put("paymentnum", paymentnum);
				DrMemberCarry drMemberCarry=drMemberCarryDAO.selectDrMemberCarryByPaymentnum(map);
				DrMemberFunds drMemberFunds=drMemberFundsDAO.queryDrMemberFundsByUid(member.getUid());//获取用户资金
				
				DrMemberFundsRecord record = new DrMemberFundsRecord(null,null,drMemberCarry.getUid(), 2, 0,drMemberCarry.getAmount().add(drMemberCarry.getPoundage()), drMemberFunds.getFuiou_balance(),1,
						"提现金额：【"+ drMemberCarry.getAmount().setScale(2)  + "】手续费：【"+drMemberCarry.getPoundage().setScale(2)+"】", drMemberCarry.getPaymentNum());
				
				try {
					if("0000".equals((String)message.get("resp_code"))){
						lockFlag = redisClientTemplate.tryLock(withdrawalsData, 3, TimeUnit.SECONDS,false);
						if(lockFlag){
							if(drMemberCarry.getStatus()!=2){
								drMemberCarry.setStatus(2);//提现状态，0未处理 1处理中 2成功 3失败  4拒绝 5超时
								record.setStatus(3);
								record.setBalance(record.getBalance().subtract(record.getAmount()));
								drMemberFundsRecordDAO.insert(record);//新增资金记录状态
								
								drMemberFunds.setFuiou_balance(Utils.nwdBcsub(drMemberFunds.getFuiou_balance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
								
								DrMemberFundsLog log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount(),52,0,
										"提现金额：【"+ drMemberCarry.getAmount().setScale(2) + "】手续费：【"+drMemberCarry.getPoundage().setScale(2)+"】");
								drMemberFundsLogDAO.insertDrMemberFundsLog(log);//提现成功
								
								if(drMemberCarry.getPoundage().compareTo(new BigDecimal(0))==1){//大于0
									log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getPoundage(),60,0,
											"提现金额：【"+ drMemberCarry.getAmount().setScale(2) + "】手续费：【"+drMemberCarry.getPoundage().setScale(2)+"】");
									drMemberFundsLogDAO.insertDrMemberFundsLog(log);//提现手续费
								}
								
								// 提现总额
								drMemberFunds.setFuiou_carrycount(drMemberFunds.getFuiou_carrycount().add(drMemberCarry.getAmount()).add(drMemberCarry.getPoundage()));
								drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);
								
								if(drMemberCarry.getPoundage().intValue() == 2){
									//扣除手续费
									Map<String, Object> params = new HashMap<String, Object>();
									String remitMchntTxnSsn = Utils.createOrderNo(6, drMemberCarry.getUid(), "");// 流水号
									params.put("mchnt_txn_ssn", remitMchntTxnSsn);
									params.put("out_cust_no", member.getMobilephone());
									params.put("in_cust_no", FuiouConfig.LOGIN_ID);
									params.put("amt", "" + drMemberCarry.getPoundage());// 手续费精确到分
									params.put("icd_name", "平台提现手续费");
									params.put("rem", "平台提现手续费");
									params.put("contract_no", "");
									BaseResult br = FuiouConfig.transferBmu(params);
									if (br.isSuccess()) {
										DrCompanyFundsLog drCompanyFundsLog = new DrCompanyFundsLog(4, drMemberCarry.getUid(), null, drMemberCarry.getPoundage(), 1, "提现手续费：【"+ drMemberCarry.getPoundage().setScale(2)  + "】", null);
										drCompanyFundsLogDAO.insertDrCompanyFundsLog(drCompanyFundsLog);
										
										//记公司账户日志 收取手续费
										JsCompanyAccountLog companyAccountLog=new JsCompanyAccountLog();
										companyAccountLog.setCompanyfunds(60);//资金类型
										companyAccountLog.setType(1);//收入
										companyAccountLog.setAmount(drMemberCarry.getPoundage());//金额
										/*companyAccountLog.setBalance(jsCompanyAccountLogDAO.getBlanceByFuiou().add(drMemberCarry.getPoundage()));*/
										companyAccountLog.setStatus(3);//成功
										companyAccountLog.setRemark(member.getMobilephone()+"平台提现手续费(投资人)");
										companyAccountLog.setAddTime(new Date());
										companyAccountLog.setChannelType(2);//存管
										companyAccountLog.setUid(member.getUid());//用户id
										jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);	
										
										//商户营销流水
										JsMerchantMarketing jmm = new JsMerchantMarketing(drMemberCarry.getPoundage(), null, null, null, drMemberCarry.getUid(), 4, new Date(), remitMchntTxnSsn,
												null, null, null, "平台提现手续费", 0);
										jsMerchantMarketingDAO.insert(jmm);
									}
									
								}
								//记公司账户日志 收取手续费
								JsCompanyAccountLog accountLog=new JsCompanyAccountLog();
								accountLog.setCompanyfunds(61);//资金类型
								accountLog.setType(0);//支出
								accountLog.setAmount(new BigDecimal(2));//金额
								/*accountLog.setBalance(jsCompanyAccountLogDAO.getBlanceByFuiou().subtract(new BigDecimal(3)));*/
								accountLog.setStatus(3);//成功
								accountLog.setRemark(member.getMobilephone()+"平台提现手续费(第三方)");
								accountLog.setAddTime(new Date());
								accountLog.setChannelType(2);//存管
								accountLog.setUid(member.getUid());//用户id
								jsCompanyAccountLogDAO.insertCompanyAccountLog(accountLog);		
							}
						}
				}else{
					drMemberCarry.setStatus(3);//提现状态，0未处理 1处理中 2成功 3失败  4拒绝 5超时
					drMemberCarry.setReason((String)message.get("resp_desc"));
					
					record.setStatus(2);
					record.setBalance(drMemberFunds.getFuiou_balance());
					drMemberFundsRecordDAO.insert(record); 
					
					String sms = redisClientTemplate.getProperties("carryFail")
							.replace("${1}", member.getRealName())
							.replace("${2}", Utils.format(drMemberCarry.getAddTime(), "M月d日"))
							.replace("${3}", drMemberCarry.getAmount().setScale(2).toString());
					SysMessageLog logs = new SysMessageLog(member.getUid(), sms, 11, null, member.getMobilephone());
					
					sysMessageLogService.sendMsg(logs,1);
				}
				drMemberCarryDAO.updateStatusById(drMemberCarry);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}finally{
				if(lockFlag){
					redisClientTemplate.del(withdrawalsData);
				}
			}
		}else{
			System.out.println("["+Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"]提现验签失败---------------------");
		}
			
		}


	@Override
	public List<DrMemberCarry> selectFuiouByStatus() {
		return drMemberCarryDAO.selectFuiouByStatus();
	}


	@Override
	public void queryFuiouMemberCarryByStatus(DrMemberCarry drMemberCarry) throws SQLException {
		Map<String, String> map=new HashMap<>();
		map.put("txn_ssn", drMemberCarry.getPaymentNum());
		map.put("busi_tp", "PWTX");
		map.put("start_time", Utils.format(Utils.getDayNumOfAppointDate(drMemberCarry.getAddTime(), 1), "yyyy-MM-dd")+" 00:00:00");
		map.put("end_time",  Utils.format(Utils.getDayNumOfAppointDate(drMemberCarry.getAddTime(), -1), "yyyy-MM-dd")+" 23:59:59");
		map.put("mchnt_txn_ssn", Utils.createOrderNo(6, drMemberCarry.getUid(), ""));
		BaseResult br=FuiouConfig.QueryCzTx(map);
		if(!br.getMsg().contains("0000|成功")){
			DrMemberCarry carry=new DrMemberCarry();
			carry.setId(drMemberCarry.getId());
			carry.setStatus(3);
			drMemberCarryDAO.updateStatusById(carry);
		}else{
			SysFuiouNoticeLog syslog = sysFuiouNoticeLogService.selectObjectBy_txn_ssn(drMemberCarry.getPaymentNum());
			updateWithdrawals(drMemberCarry,syslog,br.getMsg());
		}	
	}


	/**
	 * 提现同步
	 * @throws SQLException 
	 */
	@Override
	public BaseResult withdrawals(String str) throws SQLException {
		SysFuiouNoticeLog syslog = sysFuiouNoticeLogService.selectObjectBy_txn_ssn(str);
		BaseResult result = new BaseResult();
		if(Utils.isObjectNotEmpty(syslog)){
			Map<String, Object> map=new HashMap<>();
			map.put("paymentnum", str);
			DrMemberCarry drMemberCarry=drMemberCarryDAO.selectDrMemberCarryByPaymentnum(map);
			Map<String, String> m=new HashMap<>();
			m.put("txn_ssn", syslog.getMchnt_txn_ssn());
			m.put("busi_tp", "PWTX");
			m.put("start_time", Utils.format(Utils.getDayNumOfAppointDate(drMemberCarry.getAddTime(), 1), "yyyy-MM-dd")+" 00:00:00");
			m.put("end_time",  Utils.format(Utils.getDayNumOfAppointDate(drMemberCarry.getAddTime(), -1), "yyyy-MM-dd")+" 23:59:59");
			m.put("mchnt_txn_ssn", Utils.createOrderNo(6, drMemberCarry.getUid(), ""));
			BaseResult br1=FuiouConfig.QueryCzTx(m);
			if(br1.isSuccess()){
				if(br1.getMsg().contains("0000|成功")){
					result=updateWithdrawals(drMemberCarry,syslog,br1.getMsg());
				}else{
					result.setErrorMsg("订单不存在或提现失败");
					result.setSuccess(false);
				}
			}else{
				result.setErrorMsg("调用存管查询接口失败");
				result.setSuccess(false);
			}
		}else{
			result.setErrorMsg("流水号不存在");
			result.setSuccess(false);
		}
		return result;
	}

	public BaseResult updateWithdrawals(DrMemberCarry drMemberCarry,SysFuiouNoticeLog syslog,String msg) throws SQLException{
		BaseResult result = new BaseResult();
		
		DrMember member=drMemberDAO.selectDrMemberByMobilePhone(syslog.getUser_id());		
		DrMemberFunds drMemberFunds=drMemberFundsDAO.queryDrMemberFundsByUid(member.getUid());//获取用户资金
		
		DrMemberFundsRecord record = new DrMemberFundsRecord(null,null,drMemberCarry.getUid(), 2, 0,drMemberCarry.getAmount().add(drMemberCarry.getPoundage()), drMemberFunds.getFuiou_balance(),1,
				"提现金额：【"+ drMemberCarry.getAmount().setScale(2)  + "】手续费：【"+drMemberCarry.getPoundage().setScale(2)+"】", drMemberCarry.getPaymentNum());
		
	
		
		if(drMemberCarry.getStatus()!=2){
			drMemberCarry.setStatus(2);//提现状态，0未处理 1处理中 2成功 3失败  4拒绝 5超时
			record.setStatus(3);
			drMemberCarry.setReason("手动同步成功");
			record.setBalance(record.getBalance().subtract(record.getAmount()));
			drMemberFundsRecordDAO.insert(record);//新增资金记录状态
			
			drMemberFunds.setFuiou_balance(Utils.nwdBcsub(drMemberFunds.getFuiou_balance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
			
			DrMemberFundsLog log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount(),52,0,
					"提现金额：【"+ drMemberCarry.getAmount().setScale(2) + "】手续费：【"+drMemberCarry.getPoundage().setScale(2)+"】");
			drMemberFundsLogDAO.insertDrMemberFundsLog(log);//提现成功
			
			if(drMemberCarry.getPoundage().compareTo(new BigDecimal(0))==1){//大于0
				log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getPoundage(),60,0,
						"提现金额：【"+ drMemberCarry.getAmount().setScale(2) + "】手续费：【"+drMemberCarry.getPoundage().setScale(2)+"】");
				drMemberFundsLogDAO.insertDrMemberFundsLog(log);//提现手续费
			}
			
			// 提现总额
			drMemberFunds.setFuiou_carrycount(drMemberFunds.getFuiou_carrycount().add(drMemberCarry.getAmount()).add(drMemberCarry.getPoundage()));
			drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);
			
			if(drMemberCarry.getPoundage().intValue() == 2){
				//扣除手续费
				Map<String, Object> params = new HashMap<String, Object>();
				String remitMchntTxnSsn = Utils.createOrderNo(6, drMemberCarry.getUid(), "");// 流水号
				params.put("mchnt_txn_ssn", remitMchntTxnSsn);
				params.put("out_cust_no", member.getMobilephone());
				params.put("in_cust_no", FuiouConfig.LOGIN_ID);
				params.put("amt", "" + drMemberCarry.getPoundage());// 手续费精确到分
				params.put("icd_name", "平台提现手续费");
				params.put("rem", "");
				params.put("contract_no", "");
				BaseResult br2 = FuiouConfig.transferBmu(params);
				if (br2.isSuccess()) {
					DrCompanyFundsLog drCompanyFundsLog = new DrCompanyFundsLog(4, drMemberCarry.getUid(), null, drMemberCarry.getPoundage(), 1, "提现手续费：【"+ drMemberCarry.getPoundage().setScale(2)  + "】", null);
					drCompanyFundsLogDAO.insertDrCompanyFundsLog(drCompanyFundsLog);
					
					//记公司账户日志 收取手续费
					JsCompanyAccountLog companyAccountLog=new JsCompanyAccountLog();
					companyAccountLog.setCompanyfunds(60);//资金类型
					companyAccountLog.setType(1);//收入
					companyAccountLog.setAmount(drMemberCarry.getPoundage());//金额
					companyAccountLog.setStatus(3);//成功
					companyAccountLog.setRemark(member.getMobilephone()+"平台提现手续费(投资人)");
					companyAccountLog.setAddTime(new Date());
					companyAccountLog.setChannelType(2);//存管
					companyAccountLog.setUid(member.getUid());//用户id
					jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);	
					
					//TODO 
					//商户营销流水
					JsMerchantMarketing jmm = new JsMerchantMarketing(drMemberCarry.getPoundage(), null, null, null, drMemberCarry.getUid(), 4, new Date(), remitMchntTxnSsn,
							null, null, null, "平台提现手续费", 0);
					jsMerchantMarketingDAO.insert(jmm);
						
				}
				
			}
			
			drMemberCarryDAO.updateStatusById(drMemberCarry);
			//记公司账户日志 收取手续费
			JsCompanyAccountLog accountLog=new JsCompanyAccountLog();
			accountLog.setCompanyfunds(61);//资金类型
			accountLog.setType(0);//支出
			accountLog.setAmount(new BigDecimal(2));//金额
			accountLog.setStatus(3);//成功
			accountLog.setRemark(member.getMobilephone()+"平台提现手续费(第三方)");
			accountLog.setAddTime(new Date());
			accountLog.setChannelType(2);//存管
			accountLog.setUid(member.getUid());//用户id
			jsCompanyAccountLogDAO.insertCompanyAccountLog(accountLog);
			
			syslog.setStatus(2);//2成功
			syslog.setResp_code("0000");
			syslog.setResp_desc(msg);
			sysFuiouNoticeLogService.update(syslog);
			
			result.setErrorMsg("同步成功");
			result.setSuccess(true);
		}else{
			result.setErrorMsg("订单已处理");
			result.setSuccess(false);
		}
		return result;
	
	}
	@Override
	public DrMemberCarry selectDrMemberCarryByPaymentnum(Map<String, Object> map) {
		return drMemberCarryDAO.selectDrMemberCarryByPaymentnum(map);
	}


	@Override
	public void depositsMemberCarry(DrMemberCarry drMemberCarry, DrMember member) throws SQLException {
			DrMemberFunds drMemberFunds=drMemberFundsDAO.queryDrMemberFundsByUid(member.getUid());//获取用户资金
			
			DrMemberFundsRecord record = new DrMemberFundsRecord(null,null,drMemberCarry.getUid(), 2, 0,drMemberCarry.getAmount().add(drMemberCarry.getPoundage()), drMemberFunds.getFuiou_balance(),1,
					"提现金额：【"+ drMemberCarry.getAmount().setScale(2)  + "】手续费：【"+drMemberCarry.getPoundage().setScale(2)+"】", drMemberCarry.getPaymentNum());
			
					record.setStatus(3);
					record.setBalance(record.getBalance().subtract(record.getAmount()));
					drMemberFundsRecordDAO.insert(record);//新增资金记录状态
					
					drMemberFunds.setFuiou_balance(Utils.nwdBcsub(drMemberFunds.getFuiou_balance(), drMemberCarry.getAmount().add(drMemberCarry.getPoundage())));
				/*	DrMemberFundsLog log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount().add(drMemberCarry.getPoundage()),5,0,
							"提现金额：【"+ drMemberCarry.getAmount().setScale(2) + "】手续费：【"+drMemberCarry.getPoundage().setScale(2)+"】");
					drMemberFundsLogDAO.insertDrMemberFundsLog(log);*/
					
					DrMemberFundsLog log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getAmount(),52,0,
							"提现金额：【"+ drMemberCarry.getAmount().setScale(2) + "】手续费：【"+drMemberCarry.getPoundage().setScale(2)+"】");
					drMemberFundsLogDAO.insertDrMemberFundsLog(log);//提现成功
					
					if(drMemberCarry.getPoundage().compareTo(new BigDecimal(0))==1){//大于0
						log = new DrMemberFundsLog(drMemberCarry.getUid(),record.getId(),drMemberCarry.getPoundage(),60,0,
								"提现金额：【"+ drMemberCarry.getAmount().setScale(2) + "】手续费：【"+drMemberCarry.getPoundage().setScale(2)+"】");
						drMemberFundsLogDAO.insertDrMemberFundsLog(log);//提现手续费
					}
					
					// 提现总额
					drMemberFunds.setFuiou_carrycount(drMemberFunds.getFuiou_carrycount().add(drMemberCarry.getAmount()).add(drMemberCarry.getPoundage()));
					drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);
					
					if(drMemberCarry.getPoundage().intValue() == 2){
						//扣除手续费
						Map<String, Object> params = new HashMap<String, Object>();
						String remitMchntTxnSsn = Utils.createOrderNo(6, drMemberCarry.getUid(), "");// 流水号
						params.put("mchnt_txn_ssn", remitMchntTxnSsn);
						params.put("out_cust_no", member.getMobilephone());
						params.put("in_cust_no", FuiouConfig.LOGIN_ID);
						params.put("amt", "" + drMemberCarry.getPoundage());// 手续费精确到分
						params.put("icd_name", "平台提现手续费");
						params.put("rem", "");
						params.put("contract_no", "");
						BaseResult br = FuiouConfig.transferBmu(params);
						if (br.isSuccess()) {
							DrCompanyFundsLog drCompanyFundsLog = new DrCompanyFundsLog(4, drMemberCarry.getUid(), null, drMemberCarry.getPoundage(), 1, "提现手续费：【"+ drMemberCarry.getPoundage().setScale(2)  + "】", null);
							drCompanyFundsLogDAO.insertDrCompanyFundsLog(drCompanyFundsLog);
							
							//记公司账户日志 收取手续费
							JsCompanyAccountLog companyAccountLog=new JsCompanyAccountLog();
							companyAccountLog.setCompanyfunds(60);//资金类型
							companyAccountLog.setType(1);//收入
							companyAccountLog.setAmount(drMemberCarry.getPoundage());//金额
							companyAccountLog.setStatus(3);//成功
							companyAccountLog.setRemark(member.getMobilephone()+"平台提现手续费(投资人)");
							companyAccountLog.setAddTime(new Date());
							companyAccountLog.setChannelType(2);//存管
							companyAccountLog.setUid(member.getUid());//用户id
							jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);		
								
						}
						
					}
					//记公司账户日志 收取手续费
					JsCompanyAccountLog accountLog=new JsCompanyAccountLog();
					accountLog.setCompanyfunds(61);//资金类型
					accountLog.setType(0);//支出
					accountLog.setAmount(new BigDecimal(2));//金额
					accountLog.setStatus(3);//成功
					accountLog.setRemark(member.getMobilephone()+"平台提现手续费(第三方)");
					accountLog.setAddTime(new Date());
					accountLog.setChannelType(2);//存管
					accountLog.setUid(member.getUid());//用户id
					jsCompanyAccountLogDAO.insertCompanyAccountLog(accountLog);	
					drMemberCarryDAO.updateStatusById(drMemberCarry);
			
	}


	@Override
	public void getSysFuiouNoticeLogByIcd() {
		List<SysFuiouNoticeLog> list = sysFuiouNoticeLogService.getSysFuiouNoticeLogByIcd();
		try {
			if(list.size()>0){
				for (SysFuiouNoticeLog sysFuiouNoticeLog : list) {
					int uid = Integer.parseInt(sysFuiouNoticeLog.getMchnt_txn_ssn().substring(sysFuiouNoticeLog.getMchnt_txn_ssn().length()-20));
					//扣除手续费
					Map<String, Object> params = new HashMap<String, Object>();
					String remitMchntTxnSsn = Utils.createOrderNo(6,uid, "");// 流水号
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
						//商户营销流水
						JsMerchantMarketing jmm = new JsMerchantMarketing(Utils.nwdDivide(sysFuiouNoticeLog.getAmt(), new BigDecimal("100")), null, null, null, uid, 4, new Date(), remitMchntTxnSsn,
								null, null, null, "平台提现手续费", 0);
						jsMerchantMarketingDAO.insert(jmm);

					}
					sysFuiouNoticeLog.setStatus(2);
					sysFuiouNoticeLogService.update(sysFuiouNoticeLog);
				}
			}else{
				log.info("没有失败的数据");
			}
			
		} catch (Exception e) {
			
		}
		
	}

}
	

