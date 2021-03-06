package com.jsjf.controller.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.DbcontextHolder;
import com.jsjf.common.ExcelUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrActivityDAO;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.activity.DrCouponsIssuedRules;
import com.jsjf.model.activity.DrManuallySendCoupon;
import com.jsjf.model.activity.JsActivityAggregationPage;
import com.jsjf.model.activity.JsChannelCouponPut;
import com.jsjf.model.article.SysArticle;
import com.jsjf.model.cpa.DrChannelInfo;
import com.jsjf.model.cpa.DrChannelKeyWords;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.activity.DrActivityParameterService;
import com.jsjf.service.activity.DrCouponsIssuedRulesService;
import com.jsjf.service.activity.JsActivityAggregationPageService;
import com.jsjf.service.activity.JsChannelCouponPutService;
import com.mysql.fabric.xmlrpc.base.Param;

@Controller
@RequestMapping("/activity")
public class ActivityController {
	private Logger log = Logger.getLogger(ActivityController.class);

	@Autowired
	private DrActivityParameterService drActivityParameterService;
	@Autowired
	private DrCouponsIssuedRulesService drCouponsIssuedRulesService;
	@Autowired
	private JsActivityAggregationPageService jsActivityAggregatioPageService;
	@Autowired
	private JsChannelCouponPutService jsChannelCouponPutService;
	
	
	//--------------- 分割线--聚胜活动优惠券发放  ---------------
	
	/**
	 * 审核发放渠道优惠券
	 * @param req
	 * @param id
	 * @return
	 */
	@RequestMapping("toAuditCouponPut")
	@ResponseBody
	public BaseResult toAuditCouponPut(HttpServletRequest req,Integer id,Integer ispass,String remark){//ispass:1通过,0:驳回
		BaseResult result = new BaseResult();
		try {
			SysUsersVo vo = (SysUsersVo)req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);			
			JsChannelCouponPut put = jsChannelCouponPutService.selectObjectById(id);
			if(Utils.isObjectNotEmpty(put) && put.getStatus()==2){
				if(ispass != null && ispass == 1){
					put.setAuditUser(vo.getUserKy().intValue());
					put.setAddtime(new Date());
					put.setRemark(remark);
					jsChannelCouponPutService.auditCouponPut(result, put);
				}else if(ispass != null && ispass == 0){
					put.setAuditUser(vo.getUserKy().intValue());
					put.setAddtime(new Date());
					put.setRemark(remark);
					put.setStatus(4);//不通过
					jsChannelCouponPutService.update(put);
					result.setMsg("审核成功!重置查询");
				}else{
					result.setMsg("已审核!重置查询");
				}
			}else{
				result.setMsg("已审核!重置查询");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			result.setErrorMsg("系统错误");
		}
		return result;
	}
	/**
	 * 校验发放渠道优惠券数据
	 * @param req
	 * @param id
	 * @return
	 */
	@RequestMapping("toCheckOutCouponPut")
	@ResponseBody
	public BaseResult toCheckOutCouponPut(HttpServletRequest req,Integer id){
		BaseResult result = new BaseResult();
		try {
			SysUsersVo vo = (SysUsersVo)req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);	
			JsChannelCouponPut put = jsChannelCouponPutService.selectObjectById(id);
			if(Utils.isObjectNotEmpty(put) && put.getStatus()==0){
				put.setUpdateUser(vo.getUserKy().intValue());
				put.setUpdateTime(new Date());
				jsChannelCouponPutService.checkOutCouponPut(result,put);
			}else{
				result.setMsg("错误!刷新页面");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			result.setErrorMsg("系统错误");
		}
		return result;
	}
	
	/**
	 * 进入渠道优惠券发放或审核
	 * isAudit: 1 =审核,2=查看明细 
	 * @return
	 */
	@RequestMapping("/toChannelCouponPutDetail")
	public String toChannelCouponPutDetail(HttpServletRequest req,Integer id,Integer isAudit,Map<String, Object> model){
		JsChannelCouponPut jsChannelCouponPut = jsChannelCouponPutService.selectObjectById(id);
		model.put("couponPut", jsChannelCouponPut);
		model.put("isAudit", isAudit);
		return "system/activity/jsChannelCouponPutDetailList";	//发放
	}
	
	/**
	 * 获取发放任务详情
	 * @param req
	 * @return
	 */
	@RequestMapping("/getCouponPutDetailList")
	@ResponseBody
	public PageInfo getCouponPutDetailList(HttpServletRequest req,Integer id,
			Integer page, Integer rows){
		PageInfo info = new PageInfo(page, rows);
		try {
			jsChannelCouponPutService.selectCouponDetailList(info,id);
		} catch (Exception e) {
			e.printStackTrace();
//			log.error(e.getMessage(), e);
		}
		return info;
	}
	/**
	 * 添加/修改发放详情
	 * @param req
	 * @param type
	 * @param fileImport
	 * @return
	 */
	@RequestMapping(value="/addCouponPut",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String addCouponPut(HttpServletRequest req,Integer type,MultipartFile fileImport,Integer isResetUploadId){
		BaseResult result = new BaseResult();
		SysUsersVo vo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			JsChannelCouponPut put = new JsChannelCouponPut();
			if(!Utils.isBlank(isResetUploadId) && isResetUploadId >0){//修改
				put.setUpdateTime(new Date());
				put.setUpdateUser(vo.getUserKy().intValue());
				put.setId(isResetUploadId);
				put.setType(type);
				result = jsChannelCouponPutService.UpDateCouponPut(fileImport, put);
			}else{//添加
				put.setType(type);
				put.setAddUser(vo.getUserKy().intValue());
				result = jsChannelCouponPutService.addCouponPut(fileImport, put);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			result.setErrorMsg("系统错误");
		}
		return JSON.toJSONString(result);
	}
	
	/**
	 * 获取发放任务
	 * @param req
	 * @return
	 */
	@RequestMapping("/getCouponPutList")
	@ResponseBody
	public PageInfo getCouponPutList(HttpServletRequest req,JsChannelCouponPut jsChannelCouponPut,
			Integer page, Integer rows){
		PageInfo info = new PageInfo(page, rows);
		try {
			jsChannelCouponPutService.selectObjectList(info, jsChannelCouponPut);
		} catch (Exception e) {
			e.printStackTrace();
//			log.error(e.getMessage(), e);
		}
		return info;
	}
	/**
	 * 进入渠道优惠券发放或审核
	 * isAudit: null=发放,1 =审核,2=查看明细 
	 * @return
	 */
	@RequestMapping("/toChannelCouponPut")
	public String toChannelCouponPut(HttpServletRequest req,Integer isAudit,Map<String,Object> model){
		if(isAudit != null && isAudit == 1){
			model.put("isAudit", 1);	//审核
		}
		return "system/activity/jsChannelCouponPutList";	//发放	
	}
	//--------------- 分割线 --聚胜活动优惠券发放 ---------------
	
	// 跳转到红包活动管理
	@RequestMapping("/toRedEnvelopeManager")
	public String toRedEnvelopeList(HttpServletRequest req,Map<String,Object> model) {
		try {
			model.put("repayType", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/activity/redEnvelopeManager";
	}

	/**
	 * 红包活动列表
	 * 
	 * @param sysActivityParameter
	 * @param info
	 * @return
	 */
	@RequestMapping("/redEnvelopeManager")
	@ResponseBody
	public PageInfo redEnvelopeManager(DrActivityParameter drActivityParameter,
			Integer page, Integer rows, HttpServletRequest request) {
		PageInfo info = new PageInfo(page, rows);
		try {
			if(drActivityParameter.getType()==null || drActivityParameter.getType().equals("")){
				drActivityParameter.setTypes(new Integer[]{1,5});
			}
			
			info = drActivityParameterService.getActivityList(info,drActivityParameter);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return info;
	}
	

	/**
	 * 添加活动
	 * 
	 * @param request
	 * @param sysProgram
	 * @return
	 */
	@RequestMapping("/addActivityParameter")
	@ResponseBody
	public BaseResult addActivityParameter(
			HttpServletRequest request,
			DrActivityParameter drActivityParameter,
			@RequestParam(value = "applicableScenarios", required = false) int[] applicableScenarios) {
		String scenarios = Utils.arrayConvertStr(applicableScenarios);
		BaseResult result = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(
				ConfigUtil.ADMIN_LOGIN_USER);
		drActivityParameter.setApplicableScenarios(scenarios);

		if (drActivityParameter.getType() == 1) {
			drActivityParameter.setCode("HB-" + Utils.getTime());
			drActivityParameter.setRaisedRates(null);
		} else if (drActivityParameter.getType() == 2) {
			drActivityParameter.setCode("JXQ-" + Utils.getTime());
		}else if(drActivityParameter.getType() ==3){
			drActivityParameter.setCode("TYJ-"+Utils.getTime());
			drActivityParameter.setSurplusQty(drActivityParameter.getGrantQty());
			drActivityParameter.setProductDeadline(1);
		}else if(drActivityParameter.getType() ==4){
			/*if("1".equals(scenarios)){
				//活动双倍券有且只能存在一个（更新活动翻倍券为无效状态，然后新增）
				DrActivityParameter activity = new DrActivityParameter();
				activity.setType(4);
				activity.setApplicableScenarios("1");
				try {
					drActivityParameterService.updateStatus(activity, usersVo);
				} catch (Exception e) {
					result.setErrorMsg("保存失败！");
					e.printStackTrace();
					return result;
				}
			}*/
			drActivityParameter.setSurplusQty(drActivityParameter.getGrantQty());
			drActivityParameter.setCode("FBQ-"+Utils.getTime());
		}else if(drActivityParameter.getType() ==5){
			drActivityParameter.setCode("BL-" + Utils.getTime());
			drActivityParameter.setAmount(null);
		}

		try {
			result = drActivityParameterService.insertActivity(drActivityParameter, usersVo);
			result.setSuccess(true);
			result.setMsg("保存成功！");
		} catch (Exception e) {
			result.setErrorMsg("保存失败！");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 进入修改页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/toActivityModify")
	public String toActivityModify(Integer id, Map<String, Object> model) {
		DrActivityParameter drActivityParameter = new DrActivityParameter();
		try {
			drActivityParameter = drActivityParameterService.getActivityParameterById(id);
			model.put("drActivityParameter", drActivityParameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (drActivityParameter.getType() == 1) {
			return "system/activity/redActivityModify";
		} else if (drActivityParameter.getType() == 2) {
			return "system/activity/incrRestModify";
		} else if(drActivityParameter.getType() == 4) {
			return "system/activity/doubleCouponsModify";
		}else if(drActivityParameter.getType() == 5){
			return "system/activity/redActivityModify";
		}else{
			return "";
		}

	}

	// 跳转到加息券活动管理页面
	@RequestMapping("/toIncrRestManager")
	public String toIncrRest(HttpServletRequest req) {
		return "system/activity/incrRestManager";
	}

	/**
	 * 加息券列表
	 * 
	 * @param sysActivityParameter
	 * @param info
	 * @return
	 */
	@RequestMapping("/incrRestManager")
	@ResponseBody
	public PageInfo incrRestManager(DrActivityParameter drActivityParameter,
			Integer page, Integer rows) {
		PageInfo info = new PageInfo(page, rows);
		try {
			drActivityParameter.setType(2);
			info = drActivityParameterService.getActivityList(info,drActivityParameter);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return info;
	}

	//跳转到翻倍券活动管理页面
	@RequestMapping("toDoubleCouponsManager")
	public String toDoubleCouponsManager(HttpServletRequest req){
		return "system/activity/doubleCouponsManager";
	}
	
	/**
	 * 翻倍券列表
	 * @param sysActivityParameter
	 * @param info
	 * @return
	 */
	@RequestMapping("/doubleCouponsManager")
	@ResponseBody
	public PageInfo doubleCouponsManager(DrActivityParameter drActivityParameter,Integer page, Integer rows) {
		PageInfo info = new PageInfo(page, rows);
		try {
			drActivityParameter.setType(4);
			info = drActivityParameterService.getActivityList(info,drActivityParameter);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return info;
	}
	
	
	/**
	 * 进入加息券修改页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/toIncrRest")
	public String toIncrRestModify(Integer id, Map<String, Object> model) {
		DrActivityParameter drActivityParameter;
		try {
			drActivityParameter = drActivityParameterService
					.getActivityParameterById(id);
			model.put("drActivityParameter", drActivityParameter);
//			String scenarios = drActivityParameter.getApplicableScenarios();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "system/activity/incrRestModify";
	}

	@RequestMapping("/modifyActivity")
	@ResponseBody
	public BaseResult modifyActivity(HttpServletRequest request,
			DrActivityParameter drActivityParameter) {
		BaseResult result = new BaseResult();
		try {
			SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			DrActivityParameter activity = drActivityParameterService.getActivityParameterById(drActivityParameter.getId());
			int surplusQty = activity.getSurplusQty()+ drActivityParameter.getGrantQty()- activity.getGrantQty();
			if (surplusQty < 0) {
				result.setErrorMsg("剩余数量小于0张");
				result.setSuccess(false);
			} else {
				drActivityParameter.setSurplusQty(surplusQty);
				drActivityParameterService.toModifyActivity(drActivityParameter, usersVo);
				result.setMsg("修改成功!");
				result.setSuccess(true);
			}
		} catch (Exception e) {
			result.setErrorMsg("系统错误!");
			result.setSuccess(false);
			log.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping("/modifyTasteMoney")
	@ResponseBody
	public BaseResult modifyTasteMoney(HttpServletRequest request,DrActivityParameter drActivityParameter) {
		BaseResult result = new BaseResult();
		try {
			SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			DrActivityParameter activity = new DrActivityParameter(); 
			activity.setId(drActivityParameter.getId());
			drActivityParameterService.updateStatus(activity,usersVo);
			
			drActivityParameter.setType(3);
			drActivityParameter.setApplicableProducts("0");
			drActivityParameterService.insertActivity(drActivityParameter, usersVo);
			result.setMsg("修改成功!");
			result.setSuccess(true);
			
		} catch (Exception e) {
			result.setErrorMsg("系统错误!");
			result.setSuccess(false);
			log.error(e.getMessage(), e);
		}
		return result;
	}

	@RequestMapping("/updateStatus")
	@ResponseBody
	public BaseResult updateStatus(HttpServletRequest request,
			DrActivityParameter drActivityParameter,Integer id) {
		BaseResult result = new BaseResult();
		try {
			SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			DrActivityParameter activity = new DrActivityParameter(); 
			activity.setId(drActivityParameter.getId());
			drActivityParameterService.updateStatus(activity,usersVo);
			result.setMsg("关闭成功!");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setErrorMsg("系统错误!");
			result.setSuccess(false);
			log.error(e.getMessage(), e);
		}
		return result;
	}

	// 跳转到体验金设置
	@RequestMapping("/toTasteMoneyManager")
	public String toTasteMoneyManager() {
		return "system/activity/tasteMoneyManager";
	}
	
	
	/**
	 * 显示体验金设置列表数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/tasteMoneyManager")
	@ResponseBody
	public PageInfo tasteMoneyManager(DrActivityParameter drActivityParameter,Integer page, Integer rows) {
		PageInfo info = new PageInfo(page, rows);
		try {

			drActivityParameter.setType(3);
			info = drActivityParameterService.getActivityList(info,drActivityParameter);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return info;
	}

	@RequestMapping(value= "/drActivityByCouponTypeList")
	@ResponseBody
	public List<DrActivityParameter> drActivityByCouponTypeList(HttpServletRequest req,Integer scene,Integer[] types){
		List<DrActivityParameter> activityParameter=null;
		try {
			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("type", type);//优惠券类型
			map.put("types", types);//优惠券类型
			map.put("scenarios", scene);//适用场景
			map.put("status",0);
			map.put("offset", 0);
			map.put("limit",  999);
			activityParameter = drActivityParameterService.getActivityParameterList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return activityParameter;
	}
	
	/**
	 * 根据ID查询体验金设置信息
	 * @param id
	 * @return BaseResult
	 */
	@RequestMapping(value= "/queryTasteMoney")
	@ResponseBody
	public BaseResult queryTasteMoney(Integer id) {
		BaseResult result = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		DrActivityParameter activity;
		try {
			activity = drActivityParameterService.getActivityParameterById(id);
			map.put("drActivityParameter",activity);
			result.setMap(map);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setErrorMsg("系统错误!");
			result.setSuccess(false);
			log.error(e.getMessage(), e);
		}
		return result;
	}
	
	// 
	@RequestMapping("/toCouponsIssuedRulesManager")
	public String toCouponsIssuedRulesManager(HttpServletRequest req, Map<String, Object> model) {
		try {
			model.put("couponType", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("couponType")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "system/activity/couponsIssuedRulesManager";
	}
	
	@RequestMapping("/couponsIssuedRulesManager")
	@ResponseBody
	public PageInfo couponsIssuedRulesManager(DrCouponsIssuedRules drCouponsIssuedRules,
			Integer page, Integer rows, HttpServletRequest request, Map<String, Object> model) {
		PageInfo info = new PageInfo(page, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		BaseResult result = new BaseResult();
		try {
			if(drCouponsIssuedRules.getType()==null){
				map.put("types", new Integer[]{0,1,2,3,4,5,7,8,9});
			}else{
				map.put("types", new Integer[]{drCouponsIssuedRules.getType()});
			}
			
//			if(drCouponsIssuedRules.getCouponType() ==null){
//				map.put("couponTypes", new Integer[]{1,2,3,4});
//			}else{
//				map.put("couponTypes", new Integer[]{drCouponsIssuedRules.getCouponType()});
//			}
			
			if(drCouponsIssuedRules.getStatus() ==null){
				map.put("statuses", new Integer[]{0,1,2});
			}else{
				map.put("statuses", new Integer[]{drCouponsIssuedRules.getStatus()});
			}
			result = drCouponsIssuedRulesService.getCouponsIssuedRulesList(map, info);
			model.put("couponType", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("couponType")));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return (PageInfo)result.getMap().get("page");
	}
	
	@RequestMapping("/addIssuedRules")
	@ResponseBody
	public BaseResult addIssuedRules(HttpServletRequest request,DrCouponsIssuedRules drCouponsIssuedRules) {
		BaseResult result = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		drCouponsIssuedRules.setAddUser(usersVo.getUserKy().intValue());
		try{
			drCouponsIssuedRulesService.insertCouponsIssuedRules(drCouponsIssuedRules);
			result.setSuccess(true);
			result.setMsg("新增成功");
		}catch (Exception e) {
			result.setErrorMsg("新增失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 进入规则设置改页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/toCouponsIssuedRulesModify")
	public String toCouponsIssuedRulesModify(Integer id, Map<String, Object> model) {
		DrCouponsIssuedRules drCouponsIssuedRules;
		try {
			drCouponsIssuedRules = drCouponsIssuedRulesService.getCouponsIssuedRulesById(id);
			model.put("drCouponsIssuedRules", drCouponsIssuedRules);
			model.put("couponTypes", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("couponType")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "system/activity/couponsIssuedRulesModify";
	}
	
	@RequestMapping("/updateIssuedRules")
	@ResponseBody
	public BaseResult updateIssuedRules(HttpServletRequest request,DrCouponsIssuedRules cir,
			@RequestParam(value = "sid", required = false) int sid){
		BaseResult result = new BaseResult();
		try {
			boolean flag = false;
			SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			if(Utils.isObjectNotEmpty(sid)&&sid>0){
				DrCouponsIssuedRules rules = drCouponsIssuedRulesService.getCouponsIssuedRulesById(sid);
				rules.setUpdateUser(usersVo.getUserKy().intValue());
				if(rules.getStatus()==0){
					rules.setStatus(1);
				}else if(rules.getStatus()==1){
					rules.setStatus(2);
					flag=true;
				}
				drCouponsIssuedRulesService.updateCouponsIssuedRules(rules);
			/*	if(flag){
					drActivityParameterService.updateStatusByRules(rules.getCoupons());
				}*/
			}else{
				cir.setUpdateUser(usersVo.getUserKy().intValue());
				cir.setStatus(0);
				drCouponsIssuedRulesService.updateCouponsIssuedRules(cir);
			}
			result.setSuccess(true);
			result.setMsg("修改成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorMsg("修改失败");
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("uploadMemberInfo")
	@ResponseBody
	public BaseResult uploadMemberInfo(HttpServletRequest request,
			@RequestParam(value="file", required=true) MultipartFile multipartFile, 
			String coupons,String couponNames,Integer cirId){
		BaseResult br = new BaseResult();
		SysUsersVo user = (SysUsersVo)request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		File uploadfile = null;
		try {
			String base = request.getSession().getServletContext().getRealPath("/")+"uploadExcel/";
			File file = new File(base);
			if(!file.exists()){
				file.mkdirs();
			}
			String path = base+"/"+System.currentTimeMillis()
					+multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."),multipartFile.getOriginalFilename().length());
			uploadfile = new File(path);
			multipartFile.transferTo(uploadfile);
			File read = new File(path);
			InputStream inputStream = new FileInputStream(read);
			LinkedHashMap<String,String> fieldMap = new LinkedHashMap<String, String>();
			fieldMap.put("客户手机号", "mobilePhone");
			
			String[] uniqueFields = new String[]{"客户手机号"};
			List<DrManuallySendCoupon> list = ExcelUtil.excelToList(inputStream, 0, DrManuallySendCoupon.class, fieldMap, uniqueFields);
			List<DrManuallySendCoupon> insertList = new ArrayList<DrManuallySendCoupon>();
			if(list.size()>0&&!Utils.isBlank(cirId)){
				for (DrManuallySendCoupon send : list) {
					send.setCirId(cirId);
					send.setCouponNames(couponNames);
					send.setCoupons(coupons);
					send.setAddUser(user.getUserKy().intValue());
					insertList.add(send);
				}
				drCouponsIssuedRulesService.batchInsertDrManuallySendCoupon(insertList);
				br.setMsg("成功导入"+list.size()+"个用户");
				br.setSuccess(true);
			}else{
				br.setErrorMsg("导入失败！");
				br.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("导入用户失败",e);
			br.setErrorMsg(e.getMessage());
			br.setSuccess(false);
		}
		return br;
	}
	
	@RequestMapping("queryManuallySendCouponLis")
	@ResponseBody
	public PageInfo queryManuallySendCouponLis(HttpServletRequest req,DrManuallySendCoupon sendCoupon,
			Integer page, Integer rows ){
		PageInfo pi = new PageInfo(page, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cirId", sendCoupon.getCirId());
		map.put("addTime", sendCoupon.getAddTime());
		try {
			pi = drCouponsIssuedRulesService.getDrManuallySendCouponList(map, pi);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pi;
	}
	
	
	@RequestMapping("manuallySendCoupon")
	@ResponseBody
	public BaseResult manuallySendCoupon(HttpServletRequest req,DrManuallySendCoupon sendCoupon){
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		SysUsersVo user = (SysUsersVo)req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try{
			map.put("uid", user.getUserKy().intValue());
			map.put("cirId", sendCoupon.getCirId());
			map.put("addTime", sendCoupon.getAddTime());
			//调用发券操作
			int sendCouponTotal=drCouponsIssuedRulesService.executeManuallySendCoupon(map);
			//更改发送状态
			int updateTotal = drCouponsIssuedRulesService.updateSendParameter(map);
			
			//判断信息是否为空  不为空则发送站内信
			if(null!=sendCoupon.getMessage()&&!"".equals(sendCoupon.getMessage().trim())){
				map.put("message", sendCoupon.getMessage());
				int sendMessageTotal = drCouponsIssuedRulesService.executeSendMessage(map);
				sb.append("发送成功!用户：").append(updateTotal).append("人，共计发券：").
					append(sendCouponTotal).append(",站内信：").append(sendMessageTotal);
			}else{
				sb.append("发送成功!用户：").append(updateTotal).append("人，共计发券：").append(sendCouponTotal);
			}
			br.setSuccess(true);
			br.setMsg(sb.toString());
		}catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg("操作失败");
			log.error(e.getMessage(),e);
		}
		return br;
	}

	/**
	 * 表单提交日期绑定
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	/**
	 * 跳转到活动聚合页
	 * @return
	 */
	@RequestMapping(value ="/toAllActivityList")
	public String getAllActivity(){
		return "system/activity/aggregationPageList";
	}
	/**
	 * 跳转到新增活动聚合页
	 * @return
	 */
	@RequestMapping(value ="/toAddAggregationPage")
	public String toAddAggregationPage(){
		return "system/activity/addAggregationPage";
	}
	
	/**
	 * 跳转到修改活动聚合页
	 * @return
	 */
	@RequestMapping(value ="/toUpdateAggregationPage")
	public String toUpdateAggregationPage(JsActivityAggregationPage jsActivityAggregationPage,Map<String, Object> model){
		model.put("jsActivityAggregationPage", jsActivityAggregatioPageService.selectJsActivityAggregationPageById(jsActivityAggregationPage.getId()));
		return "system/activity/updateAggregationPage";
	}
	
	/**
	 * 活动聚合页列表
	 * 
	 * @param sysActivityParameter
	 * @param info
	 * @return
	 */
	@RequestMapping("/getAllActivity")
	@ResponseBody
	public PageInfo redEnvelopeManager(JsActivityAggregationPage jsActivityAggregationPage,Integer page, Integer rows, HttpServletRequest request) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo info = new PageInfo(page, rows);
		BaseResult result = jsActivityAggregatioPageService.selectJsActivityAggregationPageList(jsActivityAggregationPage,info);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 添加活动聚合页
	 * @param request
	 * @param sysArticle
	 * @return
	 */
	@RequestMapping(value="/addAggregationPage",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String addAggregationPage(HttpServletRequest request,MultipartFile pcPicFile,MultipartFile appPicFile,
			JsActivityAggregationPage jsActivityAggregationPage,HttpServletResponse response) {
		BaseResult result = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(
				ConfigUtil.ADMIN_LOGIN_USER);
		
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
        Pattern pattern = Pattern.compile(reg);
        Map<String,Object> fileMap = new HashMap<String,Object>();
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		if(pcPicFile != null){
			fileMap.put("pcPicFile", pcPicFile);
			list.add(pcPicFile);
		}
		if(appPicFile != null){
			fileMap.put("appPicFile", appPicFile);
			list.add(appPicFile);
		}
		for(int i=0;i<list.size();i++){
			Matcher matcher = pattern.matcher(list.get(i).getOriginalFilename().toLowerCase());
			if(!matcher.find()){
				result.setSuccess(false);
				result.setErrorMsg("请上传正确的图片格式!");
				JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
				return jsonObject.toString();
			}
			
			long fileSize = list.get(i).getSize();
			if(fileSize>1024*5000){
				result.setSuccess(false);
				result.setErrorMsg("图片不能大于5M！");
				JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
				return jsonObject.toString();
			}
		}
		try {
			jsActivityAggregatioPageService.addAggregationPage(jsActivityAggregationPage, usersVo, pcPicFile, appPicFile);
			result.setSuccess(true);
			result.setMsg("保存成功！");
		} catch (Exception e) {
			result.setErrorMsg("保存失败！");
			e.printStackTrace();
		}

		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
		return jsonObject.toString();
	}
	
	/**
	 * 修改活动聚合页
	 * @param request
	 * @param sysArticle
	 * @return
	 */
	@RequestMapping(value="/updateAggregationPage",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String updateAggregationPage(HttpServletRequest request,MultipartFile pcPicFile,MultipartFile appPicFile,
			JsActivityAggregationPage jsActivityAggregationPage,HttpServletResponse response) {
		BaseResult result = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(
				ConfigUtil.ADMIN_LOGIN_USER);
		
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
        Pattern pattern = Pattern.compile(reg);
        Map<String,Object> fileMap = new HashMap<String,Object>();
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		if(pcPicFile != null){
			fileMap.put("pcPicFile", pcPicFile);
			list.add(pcPicFile);
		}
		if(appPicFile != null){
			fileMap.put("appPicFile", appPicFile);
			list.add(appPicFile);
		}
		for(int i=0;i<list.size();i++){
			Matcher matcher = pattern.matcher(list.get(i).getOriginalFilename().toLowerCase());
			if(!matcher.find()){
				result.setSuccess(false);
				result.setErrorMsg("请上传正确的图片格式!");
				JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
				return jsonObject.toString();
			}
			
			long fileSize = list.get(i).getSize();
			if(fileSize>1024*5000){
				result.setSuccess(false);
				result.setErrorMsg("图片不能大于5M！");
				JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
				return jsonObject.toString();
			}
		}
		try {
			jsActivityAggregatioPageService.updateAggregationPage(jsActivityAggregationPage, usersVo, pcPicFile, appPicFile);
			result.setSuccess(true);
			result.setMsg("修改成功！");
		} catch (Exception e) {
			result.setErrorMsg("修改失败！");
			e.printStackTrace();
		}

		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
		return jsonObject.toString();
	}
	
	@RequestMapping("/getRepayType")
	@ResponseBody
	public 	String getRepayType(){
		List<Map<String, Object>> map = drActivityParameterService.getRepayType();
		return JSON.toJSONString(map);
	}
	
}
