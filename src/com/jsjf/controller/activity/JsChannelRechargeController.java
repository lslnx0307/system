package com.jsjf.controller.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.service.activity.JsChannelRechargeService;
import com.jsjf.service.cpa.DrChannelInfoService;

@Controller
@RequestMapping("/jsChannelRechargeController")
public class JsChannelRechargeController {

	@Autowired
	private JsChannelRechargeService channelRechargeService;
	@Autowired
	private DrChannelInfoService drChannelInfoService;
	
	@RequestMapping("/toJsChannelRecharge")
	public String toJsChannelRecharge(Map<String,Object> model,String menuType){
		try {
			model.put("categoryList", drChannelInfoService.selectChannelType());
			model.put("menuType", menuType);//1-可操作 2-仅查看
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/system/activity/jsChannelRechargeList";
	}

	@RequestMapping("/jsChannelRechargeList")
	@ResponseBody
	public PageInfo jsChannelRechargeList(@RequestParam Map<String,Object> param,Integer page,Integer rows ){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult br = channelRechargeService.getJsChannelRecharge(param, pi);
		return (PageInfo) br.getMap().get("page");
	}
	
	@RequestMapping("/insert")
	@ResponseBody
	public String insert(@RequestParam Map<String,Object> param){
		BaseResult br = new BaseResult();
		if(Utils.isObjectEmpty(param.get("channel_id"))){
			br.setErrorMsg("渠道名称不能为空!");
			return JSON.toJSONString(br);
		}
		if(Utils.isObjectEmpty(param.get("recharge_date"))){
			br.setErrorMsg("投资时间不能为空!");
			return JSON.toJSONString(br);
		}
		if(Utils.isObjectEmpty(param.get("recharge_amount"))){
			br.setErrorMsg("投资金额不能为空!");
			return JSON.toJSONString(br);
		}
		br = channelRechargeService.insert(param);
		return JSON.toJSONString(br);
	}
	
	/**
	 * 表单提交日期绑定
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	@RequestMapping("getChannelInfoCategory")
	@ResponseBody
	public String getChannelInfoCategory(){
		List<Map<String,Object>> list = new ArrayList<>();
		
		try {
			Map<String,Object> map = new HashMap<>();
			map.put("name", "全部");
			map.put("id", "");
			list = drChannelInfoService.selectChannelType();
			list.add(0, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JSON.toJSONString(list);
	}
}
