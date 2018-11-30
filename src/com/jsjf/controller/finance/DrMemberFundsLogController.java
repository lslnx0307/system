package com.jsjf.controller.finance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMemberFundsLog;
import com.jsjf.model.system.SysChooseOption;
import com.jsjf.service.activity.HierarchicalStructureService;
import com.jsjf.service.member.DrMemberFundsLogService;
import com.jsjf.service.member.JsMemberGradeService;
import com.jsjf.service.system.SysChooseOptionService;

@Controller
@RequestMapping("/memberFundsLog")
public class DrMemberFundsLogController{
	@Autowired
	private DrMemberFundsLogService drMemberFundsLogService;
	@Autowired
	SysChooseOptionService sysChooseOptionService;
	@Autowired
	JsMemberGradeService jsMemberGradeService;
	@Autowired
	HierarchicalStructureService hierarchicalStructureService;
	
	/**
	 * 跳转到客户收支记录列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/toMemberFundsLogList")
	public String toMemberCarryList(Map<String,Object> model,String mobilePhone,String recommCodes) {
		List<SysChooseOption>list=sysChooseOptionService.queryByCategory("fundType");		
		model.put("memberFundsType", list);
		/*model.put("memberFundsType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("fundType")));*/
		model.put("mobilePhone",  mobilePhone);
		model.put("recommCodes",  recommCodes);
		model.put("grade", jsMemberGradeService.getMemberGrade());
		Map<String, Object> m1=new HashMap<>();
		m1.put("grade", new Integer[]{1});
		Map<String, Object> m2=new HashMap<>();
		m2.put("grade", new Integer[]{2});
		Map<String, Object> m3=new HashMap<>();
		m3.put("grade", new Integer[]{3});
		model.put("oneType", hierarchicalStructureService.selectHierarchicalStructure(m1));//一级分类
		model.put("twoType", hierarchicalStructureService.selectHierarchicalStructure(m2));//二级分类
		model.put("threeType", hierarchicalStructureService.selectHierarchicalStructure(m3));//三级分类
		return "system/finance/drMemberFundsLogList";
	}
	
	/**
	 * 显示客户收支记录列表数据
	 * @param drMemberFundsLog
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/memberFundsLogList")
	@ResponseBody
	public PageInfo memberFundsLogList(DrMemberFundsLog drMemberFundsLog,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		
		
		
		BaseResult result = drMemberFundsLogService.getMemberFundsLogList(drMemberFundsLog, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 统计收入支出
	 * @param drMemberFundsLog
	 * @return
	 */
	@RequestMapping(value= "/memberFundsLogSum")
	@ResponseBody
	public Map<String,Object> memberFundsLogSum(DrMemberFundsLog drMemberFundsLog) {
		
		
		
		return drMemberFundsLogService.getDrMemberFundsLogSum(drMemberFundsLog);
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
