package com.jsjf.controller.activity;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.activity.DrGiftCardSetUp;
import com.jsjf.model.activity.JsChannelType;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.activity.HierarchicalStructureService;
import com.jsjf.service.activity.JsChannelTypeService;
import com.jsjf.service.cpa.DrChannelInfoService;
import com.jsjf.service.product.DrProductInvestService;

@Controller
@RequestMapping("/hierarchicalStructure")
public class HierarchicalStructureController {
	
	@Autowired
	DrProductInvestService drProductInvestService;
	@Autowired
	HierarchicalStructureService hierarchicalStructureService;
	@Autowired
	DrChannelInfoService drChannelInfoService;
	@Autowired
	JsChannelTypeService jsChannelTypeService;
	/**
	 * 跳转到平台贴息明细
	 * @return
	 */
	@RequestMapping("/toActivityBudget")
	public String toOrderShare(){
		return "system/finance/activityBudgetList";
	}
	/**
	 * 查询平台贴息明细
	 * @param info
	 * @param param
	 * @return
	 */
	@RequestMapping("/activityBudgetList")
	@ResponseBody
	public PageInfo activityBudgetList(Integer page,Integer rows,@RequestParam Map<String, Object> param){
		BaseResult br = new BaseResult();
		if(param.get("page") == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(param.get("rows") == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		br = drProductInvestService.selectActivityBudget(pi,param);
		return (PageInfo)br.getMap().get("page");
	}
	/**
	 * 导出平台贴息明细
	 * @param info
	 * @param param
	 * @return
	 */
	@RequestMapping("/exportActivityBudgetList")
	public ModelAndView exportActivityBudgetList(@RequestParam Map<String, Object> param){
		try {
			if(Utils.isObjectNotEmpty(param.get("level3"))){
				String level3 = new String(param.get("level3").toString().getBytes("ISO-8859-1"),"UTF-8");
				param.put("level3", level3);
			}
			if(Utils.isObjectNotEmpty(param.get("productName"))){
				String productName = new String(param.get("productName").toString().getBytes("ISO-8859-1"),"UTF-8");
				param.put("productName", productName);
			}
			if(Utils.isObjectNotEmpty(param.get("channel"))){
				String channel = new String(param.get("channel").toString().getBytes("ISO-8859-1"),"UTF-8");
				param.put("channel", channel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Map<String,Object>> list = drProductInvestService.selectActivityBudgetList(param);
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		String[] title = new String[]{"投资时间","产品名称","二级分类","三级分类","投资金额(元)","支出金额(元)","渠道"};
//		Integer[] columnWidth = new Integer[]{30,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		List<Object> lc = null;
		for (Map m : list) {
			lc = new ArrayList<Object>();
			lc.add(m.get("investTime")==null?"":m.get("investTime"));//投资时间
			lc.add(m.get("fullName")==null?"":m.get("fullName"));//项目名称
			lc.add(m.get("level2")==null?"":m.get("level2"));//二级分类
			lc.add(m.get("level3")==null?"":m.get("level3"));//三级分类
			lc.add(m.get("amount")==null?"":m.get("amount"));//投资金额(元)
			lc.add(Utils.isObjectEmpty(m.get("subsidyAmount"))?0:new BigDecimal(m.get("subsidyAmount").toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());//支出金额(元)
			lc.add(m.get("channel")==null?"":m.get("channel"));//渠道
			tableList.add(lc);
		}
		Map<String,Object> map=new HashMap();
		map.put("excelName", "productInfoDetail_"+System.currentTimeMillis()+".xls");
		map.put("titles", title);
//		map.put("columnWidth", columnWidth);
		map.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), map);
	}
	
	/**
	 * 活动预算
	 * @param page
	 * @param rows
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value= "/getActivityBudget")
	@ResponseBody
	public PageInfo getActivityBudget(
		Integer page,Integer rows,String starttime,String oneType,String twoType,String threeType) throws ParseException {
		Map<String, Object> param = new HashMap<String, Object>();
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(formatter.parse(starttime));//预算时间段 
	    calendar.add(calendar.DATE,6);//把日期往后增加6天.整数往后推,负数往前移动 
	    Date endStart=calendar.getTime();   //这个时间就是日期往后推7天的结果 
	    
	    calendar.setTime(formatter.parse(starttime));//预算时间段 
	    calendar.add(calendar.DATE,-28);
	    Date computeStartTime=calendar.getTime();  //计算开始时间
	    
	    calendar.setTime(formatter.parse(starttime));//预算时间段 
	    calendar.add(calendar.DATE,-1);
	    Date computeEndTime=calendar.getTime();  //计算结束时间
	    
	    String budgetTime=starttime+"/"+formatter.format(endStart);//预计时间段
	    String computeTime=formatter.format(computeStartTime)+"/"+formatter.format(computeEndTime);//计算时间段
	    
	    param.put("budgetTime", budgetTime);//预计时间段
	    param.put("computeTime", computeTime);//计算时间段
	    param.put("computeStartTime", formatter.format(computeStartTime));//计算开始时间
	    param.put("computeEndTime", formatter.format(computeEndTime));//计算结束时间
	    param.put("oneType", oneType);
	    param.put("twoType", twoType);
	    param.put("threeType", threeType);
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = hierarchicalStructureService.getActivityBudget(param,pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 跳转活动预算页面
	 */
	@RequestMapping("/toGetActivityBudget")
	public String toGetActivityBudget(Map<String,Object> model){
		Map<String, Object> m1=new HashMap<>();
		m1.put("grade", new Integer[]{1});
		Map<String, Object> m2=new HashMap<>();
		m2.put("grade", new Integer[]{2});
		Map<String, Object> m3=new HashMap<>();
		m3.put("grade", new Integer[]{3});
		model.put("oneType", hierarchicalStructureService.selectHierarchicalStructure(m1));//一级分类
		model.put("twoType", hierarchicalStructureService.selectHierarchicalStructure(m2));//二级分类
		model.put("threeType", hierarchicalStructureService.selectHierarchicalStructure(m3));//三级分类
		return "system/activity/activityBudget";
	}
	
	/**
	 * 获取层级
	 */
	@RequestMapping("/getActivityByFid")
	@ResponseBody
	public String getActivityByFid(Integer fid,Integer grade){
		Map<String, Object> m=new HashMap<>();
		if(fid!=null){
			m.put("fid",new Integer[]{fid} );
		}
		if(grade!=null){
			m.put("grade", new Integer[]{grade});
		}
		List<Map<String, Object>>list=hierarchicalStructureService.selectHierarchicalStructure(m);
		Map<String, Object> map=new HashMap();
		map.put("id", "");
		map.put("name", "全部");
		list.add(map);
		return JSON.toJSONString(list);
	}
	
	@RequestMapping("/toCouponDetail")
	public String toCouponDetail(HttpServletRequest request,Map<String,Object> model){
		try {
			Map<String,Object> map = new HashMap<>();
			map.put("id", new Integer[]{6,7,8,9,16,31});
			model.put("two_flag", hierarchicalStructureService.selectHierarchicalStructure(map));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/system/activity/couponDetail";
	}
	
	@RequestMapping("/couponDetail")
	@ResponseBody
	public PageInfo couponDetail(@RequestParam Map<String,Object> param,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		BaseResult br = new BaseResult();
		PageInfo pi = new PageInfo(page,rows);
		br = hierarchicalStructureService.selectCouponDetail(param,pi);
		return (PageInfo)br.getMap().get("page");
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
    
    @RequestMapping("/getFlag")
    @ResponseBody
    public String getFlag(HttpServletRequest request,@RequestParam Map<String,Object> map){
    	List<Map<String,Object>> list =new ArrayList<>();
    	Map<String,Object> map1 = new HashMap<>();
    	if(map.containsKey("fid")){
    		if(Utils.isObjectNotEmpty(map.get("fid"))){
    			Integer fid = Integer.valueOf(map.get("fid").toString()) ;
    			map.clear();
    			map.put("fid", new Integer[]{fid});
    			list= hierarchicalStructureService.selectHierarchicalStructure(map);
    			map1.put("id", "");
    			map1.put("name", "全部");
    			list.add(map1);
    		}else{
    			map1.put("id", "");
    			map1.put("name", "全部");
    			list.add(map1);
    		}
			return JSON.toJSONString(list);
    	}
    	return null;
    }
    
    @RequestMapping("/exportCouponDetail")
    public ModelAndView exportCouponDetail(@RequestParam Map<String,Object> map){
    	try {
    		if(Utils.isObjectNotEmpty(map.get("simpleName"))){
				String productName = new String(map.get("simpleName").toString().getBytes("ISO-8859-1"),"UTF-8");
				map.put("simpleName", productName);
			}
    		List<Map<String,Object>> list  = hierarchicalStructureService.exportCouponDetail(map);
    		List<List<Object>> tableList = new ArrayList<List<Object>>();
    		String[] title = new String[]{"投资时间","项目名称","二级分类","三级分类","投资金额(元)","支出金额(元)","CPS渠道","渠道"};
    		List<Object> lc = null;
    		for (Map m : list) {
    			lc = new ArrayList<Object>();
    			lc.add(m.get("investTime")==null?"":m.get("investTime"));//投资时间
    			lc.add(m.get("simpleName")==null?"":m.get("simpleName"));//项目名称
    			lc.add(m.get("two_flag")==null?"":m.get("two_flag"));//二级分类
    			lc.add(m.get("three_flag")==null?"":m.get("three_flag"));//三级分类
    			lc.add(m.get("amount")==null?"":m.get("amount"));//投资金额(元)
    			lc.add(Utils.isObjectEmpty(m.get("spend_amount"))?0:new BigDecimal(m.get("spend_amount").toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());//支出金额(元)
    			lc.add(m.get("toFromType")==null?"":m.get("toFromType"));//渠道
    			lc.add(m.get("project_no")==null?"":m.get("project_no"));//渠道
    			tableList.add(lc);
    		}
    		map.clear();
    		map.put("excelName", "couponDetail_"+System.currentTimeMillis()+".xls");
    		map.put("titles", title);
//    		map.put("columnWidth", columnWidth);
    		map.put("list", tableList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return new ModelAndView(new JXLExcelView(), map);
    }
    
    /**
	 * 活动预算导出
	 * @param page
	 * @param rows
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value= "/exportActivityBudget")
	public ModelAndView exportActivityBudget(
		String starttime,String oneType,String twoType,String threeType) throws ParseException {
		Map<String, Object> param = new HashMap<String, Object>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(formatter.parse(starttime));//预算时间段 
	    calendar.add(calendar.DATE,6);//把日期往后增加7天.整数往后推,负数往前移动 
	    Date endStart=calendar.getTime();   //这个时间就是日期往后推7天的结果 
	    
	    calendar.setTime(formatter.parse(starttime));//预算时间段 
	    calendar.add(calendar.DATE,-28);
	    Date computeStartTime=calendar.getTime();  //计算开始时间
	    
	    calendar.setTime(formatter.parse(starttime));//预算时间段 
	    calendar.add(calendar.DATE,-1);
	    Date computeEndTime=calendar.getTime();  //计算结束时间
	    
	    String budgetTime=starttime+"/"+formatter.format(endStart);//预计时间段
	    String computeTime=formatter.format(computeStartTime)+"/"+formatter.format(computeEndTime);//计算时间段
	    
	    param.put("budgetTime", budgetTime);//预计时间段
	    param.put("computeTime", computeTime);//计算时间段
	    param.put("computeStartTime", formatter.format(computeStartTime));//计算开始时间
	    param.put("computeEndTime", formatter.format(computeEndTime));//计算结束时间
	    param.put("oneType", oneType);
	    param.put("twoType", twoType);
	    param.put("threeType", threeType);
		PageInfo pi = new PageInfo(1,10000000);
		BaseResult result = hierarchicalStructureService.getActivityBudget(param,pi);
		List<Map<String, Object>>list1=(List<Map<String, Object>>) pi.getRows();
		List<Map<String, Object>>list2=(List<Map<String, Object>>) pi.getFooter();
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		String[] title = new String[]{"预算时间段","计算时间段","一级分类","二级分类","三级分类","发放方式","实际支出金额","预计支出金额"};
		List<Object> lc = null;
		for (Map m : list1) {
			lc = new ArrayList<Object>();
			lc.add(budgetTime);//预算时间段
			lc.add(computeTime);//计算时间段
			lc.add(m.get("oneTypeName")==null?"":m.get("oneTypeName"));//一级分类
			lc.add(m.get("twoTypeName")==null?"":m.get("twoTypeName"));//二级分类
			lc.add(m.get("threeTypeName")==null?"":m.get("threeTypeName"));//三级分类
			lc.add(m.get("type")==null?"":m.get("type"));//发放方式
			lc.add(m.get("amount")==null?"":m.get("amount"));//实际支出金额
			lc.add(m.get("budgetAmount")==null?"":m.get("budgetAmount"));//预计支出金额
			tableList.add(lc);
		}
		lc = new ArrayList<Object>();
		Map<String,Object> mapcount=list2.get(0);
		
		lc.add("合计");//预算时间段
		lc.add("");//计算时间段
		lc.add("");//一级分类
		lc.add("");//二级分类
		lc.add("");//三级分类
		lc.add("");//发放方式
		lc.add(mapcount.get("amount")==null?"":mapcount.get("amount"));//实际支出金额
		lc.add(mapcount.get("budgetAmount")==null?"":mapcount.get("budgetAmount"));//预计支出金额
		tableList.add(lc);
		Map<String,Object> map=new HashMap();
		map.put("excelName", "activityBudget_"+System.currentTimeMillis()+".xls");
		map.put("titles", title);
//		map.put("columnWidth", columnWidth);
		map.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), map);
	}
	
	/**
	 * 跳转到邀请奖励明细页面
	 * @return
	 */
	@RequestMapping("/toInviteAwarDetail")
	public String toInviteAwarDetail(){
		return "system/activity/inviteAwarDetail";
	}
	
	/**
	 * 邀请奖励明细查询
	 * @param map
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/inviteAwarDetailList")
	@ResponseBody
	public PageInfo getInviteAwarDetailList(@RequestParam Map<String, Object> map,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = hierarchicalStructureService.getInviteAwarDetailList(map, pi);
		return (PageInfo)result.getMap().get("page");
	}
	/**
	 * 导出奖励明细查询
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/exportInviteAwarDetail")
	public ModelAndView exportInviteAwarDetail(@RequestParam Map<String, Object> map){
		try {
    		List<Map<String,Object>> list  = hierarchicalStructureService.exportInviteAwarDetail(map);
    		List<List<Object>> tableList = new ArrayList<List<Object>>();
    		String[] title = new String[]{"投资时间","产品名称","二级分类","三级分类","投资金额(元)","支出金额(元)","渠道"};
    		List<Object> lc = null;
    		for (Map m : list) {
    			lc = new ArrayList<Object>();
    			lc.add(m.get("investTime")==null?"":m.get("investTime"));//投资时间
    			lc.add(m.get("fullName")==null?"":m.get("fullName"));//产品名称
    			lc.add(m.get("typeTwo")==null?"":m.get("typeTwo"));//二级分类
    			lc.add(m.get("typeThree")==null?"":m.get("typeThree"));//三级分类
    			lc.add(m.get("amount")==null?"":m.get("amount"));//投资金额(元)
    			lc.add(Utils.isObjectEmpty(m.get("payAmount"))?0:new BigDecimal(m.get("payAmount").toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());//支出金额(元)
    			lc.add(m.get("project_no")==null?"":m.get("project_no"));//渠道
    			tableList.add(lc);
    		}
    		map.clear();
    		map.put("excelName", "inviteAwarDetail_"+System.currentTimeMillis()+".xls");
    		map.put("titles", title);
//    		map.put("columnWidth", columnWidth);
    		map.put("list", tableList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return new ModelAndView(new JXLExcelView(), map);
	}
	
	@RequestMapping("/getThreeFlag")
	@ResponseBody
	public String getTreeFlag(){
		List<Map<String, Object>> treeFlag = hierarchicalStructureService.getTreeFlag();
		return JSON.toJSONString(treeFlag);
	}
	
	/**
	 * 跳转到渠道结算页面
	 * @return
	 */
	@RequestMapping("/toChannelClearing")
	public String toChannelClearing(Map<String,Object> model){
		model.put("nameType", drChannelInfoService.selectChannelType());
		
		return "system/activity/channelClearing";
	}
	
	/**
	 *  获取渠道结算列表
	 * @param page
	 * @param rows
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value= "/getChannelClearingList")
	@ResponseBody
	public PageInfo getChannelClearingList(
		Integer page,Integer rows,String channelName,String startClearingDate,String endClearingDate){
		Map<String, Object> param = new HashMap<String, Object>();
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		param.put("channelName", channelName);
		param.put("startClearingDate", startClearingDate);
		param.put("endClearingDate", endClearingDate);
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = hierarchicalStructureService.getChannelClearingList(param,pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 跳转到渠道大类管理页面
	 * @return
	 */
	@RequestMapping("/toChannelType")
	public String toChannelType(Map<String,Object> model){		
		return "system/activity/channelType";
	}
	
	/**
	 *  获取渠道大类算列表
	 * @param page
	 * @param rows
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value= "/getChannelTypeList")
	@ResponseBody
	public PageInfo getChannelTypeList(
		Integer page,Integer rows,String name){
		Map<String, Object> param = new HashMap<String, Object>();
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		param.put("name", name);
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = jsChannelTypeService.getChannelTypeList(param, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 新增渠道大类
	 * @param channelType
	 * @return
	 */
	@RequestMapping(value= "/insertChannelType")
	@ResponseBody
	public BaseResult insertChannelType(
			JsChannelType channelType){
		jsChannelTypeService.insertChannelType(channelType);
		BaseResult result=new BaseResult();
		result.setSuccess(true);
		result.setMsg("新增成功");
		return result;
	}
	
	/**
	 * 修改渠道大类
	 * @param channelType
	 * @return
	 */
	@RequestMapping(value= "/updateChannelType")
	@ResponseBody
	public BaseResult updateChannelType(
			JsChannelType channelType){
		jsChannelTypeService.updateChannelType(channelType);
		BaseResult result=new BaseResult();
		result.setSuccess(true);
		result.setMsg("修改成功");
		return result;
	}
	
	/** 跳转至层级配置
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/toCouponHierarchyDeploy")
	public String toCouponHierarchyDeploy(HttpServletRequest request,Map<String,Object> model){
		try {
			Map<String,Object> map = new HashMap<>();
			map.put("grade", new Integer[]{1});
			model.put("oneType", hierarchicalStructureService.selectHierarchicalStructure(map));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "system/activity/couponHierarchyDeploy";
	}

	/**
	 * 查询层级
	 * @param page
	 * @param rows
	 * @param param
	 * @return
	 */
	@RequestMapping("/couponHierarchyDetail")
	@ResponseBody
	public PageInfo selectCouponHierarchyDetail(Integer page,Integer rows,@RequestParam Map<String, Object> param){
		BaseResult br = new BaseResult();
		if(param.get("page") == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(param.get("rows") == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		br = hierarchicalStructureService.selectCouponHierarchyDetail(pi,param);
		return (PageInfo)br.getMap().get("page");
	} 
	
	/**
	 * 添加层级
	 * @param req
	 * @param param
	 * @return
	 */
	@RequestMapping("/addCouponHierarchy")
	@ResponseBody
	public BaseResult addCouponHierarchy(HttpServletRequest req,@RequestParam Map<String, Object> param) {
		BaseResult result = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
				hierarchicalStructureService.insertCouponHierarchy(param);
				result.setSuccess(true);
				result.setMsg("添加成功");
		} catch (Exception e) {
			result.setErrorMsg("保存失败！");
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	@RequestMapping("/updateCouponHierarchy")
	@ResponseBody
	public BaseResult updateCouponHierarchy(HttpServletRequest req,@RequestParam Map<String, Object> param){
		BaseResult result = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			hierarchicalStructureService.updateCouponHierarchy(param);
			result.setSuccess(true);
			result.setMsg("修改成功");
		} catch (Exception e) {
			result.setErrorMsg("修改失败！");
			e.printStackTrace();
		}
		return result;
		
	}
	@RequestMapping(value= "/selectHierarchyByid")
	@ResponseBody
	public BaseResult selectHierarchyByid(HttpServletRequest req,@RequestParam Map<String, Object> map) {
		BaseResult result = new BaseResult();
		try {
			map = hierarchicalStructureService.selectCouponHierarchyById(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setMap(map);
		result.setSuccess(true);
		return result;
	}
}
