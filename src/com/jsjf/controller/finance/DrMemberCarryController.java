package com.jsjf.controller.finance;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.member.DrMemberCarry;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.member.DrMemberCarryService;
import com.jsjf.service.member.JsMemberGradeService;

@Controller
@RequestMapping("/carry")
public class DrMemberCarryController{
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private DrMemberCarryService drMemberCarryService;
	@Autowired
	private JsMemberGradeService jsMemberGradeService;
	
	private static ExecutorService batch = Executors.newFixedThreadPool(10);

	/**
	 * 跳转到提现列表
	 * @return
	 */
	@RequestMapping("/toMemberCarryList")
	public String toMemberCarryList(Map<String,Object> model) {
		try {
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("carrystatus")));
			model.put("grade", jsMemberGradeService.getMemberGrade());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/finance/drMemberCarryList";
	}
	
	/**
	 * 显示提现待审列表数据
	 * @param drMemberCarry
	 * @param page
	 * @param rows
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value= "/memberCarryList")
	@ResponseBody
	public PageInfo memberCarryList(DrMemberCarry drMemberCarry,Integer page,Integer rows,
			String startDate,
			String endDate) throws ParseException {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                
		if(StringUtils.isNotEmpty(startDate)){
			drMemberCarry.setStartDate(sdf.parse(startDate));
		}
		if(StringUtils.isNotEmpty(endDate)){
			drMemberCarry.setEndDate(sdf.parse(endDate));
		}
		BaseResult result = drMemberCarryService.getMemberCarryList(drMemberCarry, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 显示提现金额合计
	 * @param drMemberCarry
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/memberCarrySum")
	@ResponseBody
	public Map<String,Object> memberCarrySum(DrMemberCarry drMemberCarry,HttpServletRequest request,
			String startDate,
			String endDate) throws ParseException {
		Map<String,Object> model = drMemberCarryService.getDrMemberCarrySum(drMemberCarry,startDate,endDate);
		NumberFormat nf = NumberFormat.getInstance();   
		nf.setGroupingUsed(false);  

		/*JYTSendData sendData = new JYTSendData();
		sendData.setMer_viral_acct(MockClientMsgBase.PAY_ACCOUNT);
		sendData.setTran_code(MockClientMsgBase.QUERY_BALANCE_CODE);
		try {
			if(request.getSession().getAttribute("JYTCarryBalance") == null){
				JYTResultData resultData = MockClientMsgBase.getInstance().payClientMsg(sendData);
				if("S0000000".equals(resultData.getResp_code())){
					model.put("JYTCarryBalance", nf.format(resultData.getBalance()));
					request.getSession().setAttribute("JYTCarryBalance", resultData.getBalance());
				}else if("E9000017".equals(resultData.getResp_code())){
					model.put("JYTCarryBalance", 
							nf.format(request.getSession().getAttribute("JYTCarryBalance") == null ? resultData.getBalance() : request.getSession().getAttribute("JYTCarryBalance")));
				}
			}else{
				model.put("JYTCarryBalance", nf.format(request.getSession().getAttribute("JYTCarryBalance")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return model;
	}
	
	/**
	 * 修改状态【拒绝操作】
	 * @param drMemberCarry
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/memberCarryRefuse")
	@ResponseBody
	public BaseResult memberCarryRefuse(int id,HttpServletRequest request) {
		BaseResult result = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			if(Utils.isObjectEmpty(usersVo)){
				result.setSuccess(false);
				result.setErrorMsg("请重新登录！");
			}
			drMemberCarryService.updateMemberCarryRefuse(id,usersVo);
			result.setSuccess(true);
			result.setMsg("拒绝成功！");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorMsg("拒绝失败！请不要再次审核，请尽快联系IT技术部，谢谢！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 审核【拒绝操作】
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/memberCarryAudit")
	@ResponseBody
	public BaseResult memberCarryAudit(int id,HttpServletRequest request) {
		BaseResult result = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			if(Utils.isObjectEmpty(usersVo)){
				result.setSuccess(false);
				result.setErrorMsg("请重新登录！");
			}
			DrMemberCarry drMemberCarry = drMemberCarryService.selectDrMemberCarryById(id);
			if(Utils.isObjectEmpty(drMemberCarry)){
				result.setSuccess(false);
				result.setErrorMsg("该提现记录不存在，请尽快联系IT技术部，谢谢！");
				return result;
			}
			if(0 != drMemberCarry.getStatus()){
	        	result.setSuccess(true);
				result.setMsg("审核成功！");
				return result;
			}
			
			int count = drMemberCarryService.updateMemberCarryAudit(drMemberCarry,usersVo);
			if(count > 0){
				result = drMemberCarryService.saveJYTpayment(id,usersVo);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorMsg("审核失败！请不要再次审核，请尽快联系IT技术部，谢谢！");
			e.getStackTrace();
		}
		return result;
	}
	/**
	 * 批量审核
	 * @param req
	 * @param carryListData
	 * @return
	 */
	@RequestMapping("/batchCarryAudit")
	@ResponseBody
	public BaseResult batchCarryAudit(HttpServletRequest req,String carryListData){
		BaseResult result = new BaseResult();
		final SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		int succ = 0;
		int err = 0;
		int size = 0;
		StringBuffer paymentNum = new StringBuffer(); 
		
		try {
			DrMemberCarry[] array = new ObjectMapper().readValue(carryListData,DrMemberCarry[].class);
			final Integer[] arrayData = new Integer[array.length];
			if(Utils.isObjectEmpty(usersVo)){
				result.setSuccess(false);
				result.setErrorMsg("请重新登录！");
			}
			if(Utils.isObjectNotEmpty(array)){
				size = array.length;
				for(int i=0;i<array.length;i++){
					if(array[i].getType()==null || array[i].getType()!=3){
						DrMemberCarry drMemberCarry = drMemberCarryService.selectDrMemberCarryById(array[i].getId());
						if(Utils.isObjectEmpty(drMemberCarry)){
							err++;
							paymentNum.append(array[i].getPaymentNum()+",");
							continue;
						}
						if(0 != drMemberCarry.getStatus()){
							succ++;
							continue;
						}
						try {
							int count = drMemberCarryService.updateMemberCarryAudit(drMemberCarry,usersVo);
							if(count > 0){
	//							result = drMemberCarryService.saveJYTpayment(array[i].getId(),usersVo);
								arrayData[i] = array[i].getId();
							}
							succ++;
						} catch (Exception e) {
							log.error("批量审核失败:[uid:"+array[i].getUid()+",carryId:"+array[i].getId()+",paymentNum:"+array[i].getPaymentNum()+",msg:"+e.getMessage()+"]", e);
							err++;
							paymentNum.append(array[i].getPaymentNum()+",");
						}
					}
				}
			}
			//批量审核
			
			DrMemberCarryController.batch.execute(new Runnable(){
				@Override
				public void run(){
					if(Utils.isObjectNotEmpty(arrayData)){
						for(int i=0;i<arrayData.length;i++){
							if(!Utils.isBlank(arrayData[i])){
								 try {
									drMemberCarryService.saveJYTpayment(arrayData[i],usersVo);
								} catch (Exception e) {
									log.error("批量审核失败:[id:"+arrayData[i]+",date:"+Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+",msg:"+e.getMessage()+"]", e);
								}
							}
						}
					}
//					System.out.println("threadName"+Thread.currentThread().getName());
				}
			});	
			
			result.setErrorMsg(paymentNum.toString());
			result.setSuccess(true);
			result.setMsg("提现审核:"+size+"条记录,审核成功:"+succ+",未成功:"+err+",");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorMsg("审核失败！请不要再次审核，请尽快联系IT技术部，谢谢！");
			result.setMsg("提现审核:"+size+"条记录,审核成功:"+succ+",未成功:"+err+",");
			e.getStackTrace();
		}
		
		return result;
	}
	
	
	@RequestMapping("/exportMemberCarry")
	public ModelAndView exportMemberCarry(DrMemberCarry drMemberCarry,Integer page,Integer rows,
			String startDate,
			String endDate)throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		
		PageInfo pi = new PageInfo(page,Integer.MAX_VALUE);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                  
		if(StringUtils.isNotEmpty(startDate)){
			drMemberCarry.setStartDate(sdf.parse(startDate));
		}
		if(StringUtils.isNotEmpty(endDate)){
			drMemberCarry.setEndDate(sdf.parse(endDate));
		}
		BaseResult result = drMemberCarryService.getMemberCarryList(drMemberCarry, pi);
		pi = (PageInfo) result.getMap().get("page");
		List<DrMemberCarry> rowsList = (List<DrMemberCarry>)pi.getRows();
		String[] title = new String[]{"商户订单号","用户姓名 ","用户手机号","会员等级","收入总额","支出总额","余额","提现金额","提现手续费","银行名称","银行账号","提现状态","提现时间","提现渠道","备注"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(DrMemberCarry carry:rowsList){
			lc = new ArrayList<Object>();
			lc.add(carry.getPaymentNum());
			lc.add(carry.getRealName()==null?"":carry.getRealName());
			lc.add(carry.getPhone());
			lc.add(carry.getLevel());
			lc.add(carry.getEarningSum());//收入总额
			lc.add(carry.getPaySum());//支出总额
			lc.add(carry.getBalance());//余额
			lc.add(carry.getAmount());
			lc.add(carry.getPoundage());
			lc.add(carry.getBankName()==null?"":carry.getBankName());
			lc.add(carry.getBankNum()==null?"":carry.getBankNum());
			lc.add(carry.getStatusName());
			lc.add(Utils.getparseDate(carry.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
			lc.add(carry.getChannelName());
			lc.add(carry.getReason()==null?"":carry.getReason());
			tableList.add(lc);
		}
		
		param.clear();
		param.put("excelName", "member_carry_"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
		
	}
	
	/**
	 * 金运通异步通知[成功，失败和异常 都通知]
	 * @param req
	 * @param resp
	 * @return void
	 */
	@RequestMapping("/receiveNotifyJYT")
	@ResponseBody
	public void receiveNotifyJYT(HttpServletRequest req, HttpServletResponse resp)
    {
		 resp.setCharacterEncoding("UTF-8");
		try {
			drMemberCarryService.receiveNotifyJYT(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * 表单提交日期绑定
	 * @param binder
	 */
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
    }  
      
	
}
