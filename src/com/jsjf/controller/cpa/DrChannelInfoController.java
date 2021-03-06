package com.jsjf.controller.cpa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.DbcontextHolder;
import com.jsjf.common.ExcelUtil;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.cpa.DrChannelInfo;
import com.jsjf.model.cpa.DrChannelKeyWords;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.system.SysChooseOption;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.cpa.DrChannelInfoService;
import com.jsjf.service.cpa.DrChannelKeyWordsService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.system.SysChooseOptionService;

@RequestMapping("/channel")
@Controller
public class DrChannelInfoController {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private DrChannelInfoService drChannelInfoService;
	@Autowired
	private DrChannelKeyWordsService drChannelKeyWordsService;
	@Autowired
	private SysChooseOptionService sysChooseOptionService;
	@Autowired
	private DrMemberService drMemberService;
	
	/**
	 * 跳转到渠道列表页面
	 */
	@RequestMapping("/toDrChannelInfoList")
	public String toDrChannelInfoList(Map<String,Object> model) {
		 model.put("chooseOptions", ConfigUtil.dictionaryMap.get("channelType"));
		 model.put("nameType", drChannelInfoService.selectChannelType());
		return "system/cpa/drChannelInfoList";
	}
	
	/**
	 * 显示渠道列表数据
	 * @param DrProductInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/drChannelInfoList")
	@ResponseBody
	public PageInfo drChannelInfoList(DrChannelInfo drChannelInfo,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drChannelInfoService.getDrChannelInfoList(drChannelInfo, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 跳转到渠道用户列表页面
	 */
	@RequestMapping("/toDrChannelInfoUserList")
	public String toDrChannelInfoUserList(Map<String,Object> model,HttpServletRequest request) {
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		
		List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(null);
		model.put("isAuth", 0);
		for(DrChannelInfo drChannelInfo : list){
			if(usersVo.getLoginId().toString().equals(drChannelInfo.getCode())){
				model.put("isAuth", 1);
				model.put("code", usersVo.getLoginId());
			}
		}
		model.put("channel", list);
		
		return "system/cpa/drChannelInfoUserList";
	}
	
	/**
	 * 显示渠道用户列表数据
	 * @param DrProductInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/drChannelInfoUserList")
	@ResponseBody
	public PageInfo drChannelInfoUserList(DrChannelInfo drChannelInfo,Integer page,Integer rows,HttpServletRequest request) {
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		
		List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(null);
		for(DrChannelInfo info : list){
			if(usersVo.getLoginId().toString().equals(info.getCode())){
				drChannelInfo.setCode(usersVo.getLoginId());
			}
		}
		
		BaseResult result = drChannelInfoService.getDrChannelInfoUserList(drChannelInfo, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 跳转到渠道订单列表页面
	 * @throws IOException 
	 */
	@RequestMapping("/toDrChannelInfoOrderList")
	public String toDrChannelInfoOrderList(Map<String,Object> model,HttpServletRequest request) throws IOException {
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(null);
		model.put("isAuth", 0);
		for(DrChannelInfo drChannelInfo : list){
			if(usersVo.getLoginId().toString().equals(drChannelInfo.getCode())){
				model.put("isAuth", 1);
				model.put("code", usersVo.getLoginId());
			}
		}
		model.put("channel", list);
		model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
		return "system/cpa/drChannelInfoOrderList";
	}
	
	/**
	 * 显示渠道订单列表数据
	 * @param DrProductInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/drChannelInfoOrderList")
	@ResponseBody
	public PageInfo drChannelInfoOrderList(DrChannelInfo drChannelInfo,Integer page,Integer rows,HttpServletRequest request) {
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(null);
		for(DrChannelInfo info : list){
			if(usersVo.getLoginId().toString().equals(info.getCode())){
				drChannelInfo.setCode(usersVo.getLoginId());
			}
		}
		
		BaseResult result = drChannelInfoService.getDrChannelInfoOrderList(drChannelInfo, pi);
		return (PageInfo)result.getMap().get("page");
	}
	/**
	 * 显示首投渠道订单列表数据
	 * @param DrProductInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/drFistChannelInfoOrderList")
	@ResponseBody
	public PageInfo drFistChannelInfoOrderList(DrChannelInfo drChannelInfo,Integer page,Integer rows,HttpServletRequest request) {
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(null);
		for(DrChannelInfo info : list){
			if(usersVo.getLoginId().toString().equals(info.getCode())){
				drChannelInfo.setCode(usersVo.getLoginId());
			}
		}
		
		BaseResult result = drChannelInfoService.getFirstDrChannelInfoOrderList(drChannelInfo, pi);
		return (PageInfo)result.getMap().get("page");
	}
	/**
	 * 导出渠道用户信息
	 * @param req
	 * @param drClaimsLoan
	 * @return
	 */
	@RequestMapping("/exportChannelUserRecord")
	public ModelAndView exportChannelUserRecord(HttpServletRequest req,DrChannelInfo drChannelInfo){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			map = drChannelInfoService.exportChannelUser(drChannelInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView(new JXLExcelView(), map);
	}
	
	/**
	 * 导出渠道订单列表信息
	 * @param req
	 * @param drClaimsLoan
	 * @return
	 */
	@RequestMapping("/exportChannelOrderRecord")
	public ModelAndView exportChannelOrderRecord(HttpServletRequest req,DrChannelInfo drChannelInfo){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			map = drChannelInfoService.exportChannelOrder(drChannelInfo);
		} catch (Exception e) {
			log.error("导出渠道用户信息失败", e);
		}
		
		return new ModelAndView(new JXLExcelView(), map);
	}
	/**
	 * 导出首单渠道用户信息
	 * @param req
	 * @param drClaimsLoan
	 * @return
	 */
	@RequestMapping("/exportFistChannelOrderRecord")
	public ModelAndView exportFistChannelOrderRecord(HttpServletRequest req,DrChannelInfo drChannelInfo){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			map = drChannelInfoService.exportFirstChannelOrder(drChannelInfo);
		} catch (Exception e) {
			log.error("导出渠道用户信息失败", e);
		}
		
		return new ModelAndView(new JXLExcelView(), map);
	}
	
	/**
	 * 添加渠道信息
	 * @param DrChannelInfo
	 * @return
	 */
	@RequestMapping(value= "/addDrChannelInfo")
	@ResponseBody
	public BaseResult addDrChannelInfo(DrChannelInfo drChannelInfo,HttpServletRequest request) {
		BaseResult br = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("code",drChannelInfo.getCode());
			map.put("inverted", drChannelInfo.getCode());
			List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(map);
			if(list.size()>0){
				br.setErrorMsg("渠道号已存在!");
				br.setSuccess(false);
				return br;
			}
			drChannelInfo.setAddUser(usersVo.getUserKy().intValue());
			drChannelInfo.setStatus(1);
			drChannelInfoService.insertDrChannelInfo(drChannelInfo);
			br.setSuccess(true);
			br.setMsg("添加成功！");
		} catch (Exception e) {
			log.error("添加渠道信息失败", e);
			br.setErrorMsg("添加失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 根据ID查询提现设置信息
	 * @param id
	 * @return BaseResult
	 */
	@RequestMapping(value= "/queryDrChannelInfo")
	@ResponseBody
	public BaseResult queryDrChannelInfo(Integer id) {
		BaseResult result = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		DrChannelInfo drChannelInfo = drChannelInfoService.getDrChannelInfoByid(id);
		map.put("drChannelInfo",drChannelInfo);
		result.setMap(map);
		result.setSuccess(true);
		return result;
	}
	
	/**
	 * 根据ID查询提现设置信息
	 * @param id
	 * @return BaseResult
	 */
	@RequestMapping(value= "/drEffectiveChannelInfoList")
	@ResponseBody
	public List<DrChannelInfo> drEffectiveChannelInfoList() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("status", "1");
		List<DrChannelInfo> drChannelInfo = drChannelInfoService.getDrChannelInfoListForMap(map);
		return drChannelInfo;
	}
	
	/**
	 * 根据ID查询提现设置信息
	 * @param id
	 * @return BaseResult
	 */
	@RequestMapping(value= "/drAllChannelInfoList")
	@ResponseBody
	public List<DrChannelInfo> drAllChannelInfoList(String orders,HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		List<DrChannelInfo> result = new ArrayList<DrChannelInfo>(); 
		if("1".equals(orders))
			map.put("orders", " status desc,DATE_FORMAT(addDate,'%Y%m%d') desc,CONVERT( name USING gbk ) COLLATE gbk_chinese_ci ASC");
		else if("0".equals(orders))
			map.put("orders", "CONVERT( name USING gbk ) COLLATE gbk_chinese_ci ASC");
		else if(Utils.isObjectNotEmpty(orders))
			map.put("orders", orders);
		List<DrChannelInfo> drChannelInfo = drChannelInfoService.getDrChannelInfoListForMap(map);
		for(DrChannelInfo dr:drChannelInfo){
			if(usersVo.getLoginId().toString().equals(dr.getCode())){
				result.add(dr);
				return result;
			}
		}
		
		return drChannelInfo;
	}
	
	/**
	 * 修改渠道信息
	 * @param DrChannelInfo
	 * @return
	 */
	@RequestMapping(value= "/updateDrChannelInfo")
	@ResponseBody
	public BaseResult updateDrChannelInfo(DrChannelInfo drChannelInfo,HttpServletRequest request) {
		BaseResult br = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			drChannelInfo.setUpdUser(usersVo.getUserKy().intValue());
			drChannelInfoService.updateDrChannelInfo(drChannelInfo);
			br.setSuccess(true);
			br.setMsg("修改成功！");
		} catch (Exception e) {
			log.error("修改渠道信息失败", e);
			br.setErrorMsg("修改失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 修改状态
	 * @param request
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/updateDrChannelInfoStatus")
	@ResponseBody
	public BaseResult updateDrChannelInfoStatus(Integer id,Integer status,HttpServletRequest request){
		BaseResult br = new BaseResult();
		try {
			request.setCharacterEncoding("UTF-8");
			SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			
			DrChannelInfo drChannelInfo = drChannelInfoService.getDrChannelInfoByid(id);
			if(drChannelInfo.getStatus() == status){
				if(0 == status){
					drChannelInfo.setStatus(1);
				}else{
					drChannelInfo.setStatus(0);
				}
			}
			
			drChannelInfo.setUpdUser(usersVo.getUserKy().intValue());
			drChannelInfoService.updateDrChannelInfo(drChannelInfo);
			
			br.setMsg("更改成功!");
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("更改渠道状态失败", e);
			br.setErrorMsg("更改失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 导入渠道关键字
	 * @param request
	 * @return
	 */
	@RequestMapping("/uploadChannelKeyWord")
	@ResponseBody
	public BaseResult uploadChannelKeyWord(HttpServletRequest request,
			@RequestParam(value="file", required=true) MultipartFile multipartFile, Integer id){
		SysUsersVo user = (SysUsersVo)request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		BaseResult br = new BaseResult();
//		String id = request.getParameter("id");
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
			fieldMap.put("渠道号", "code");
			fieldMap.put("关键字", "keyword");
			fieldMap.put("关键字号", "kCode");
			fieldMap.put("状态", "status");
			
			String[] uniqueFields = new String[]{"渠道号","关键字","关键字号","状态"};
			List<DrChannelKeyWords> list = ExcelUtil.excelToList(inputStream, 0, DrChannelKeyWords.class, fieldMap, uniqueFields);
			DrChannelInfo channelInfo = drChannelInfoService.getDrChannelInfoByid(id);
			if(list.size()>0&&channelInfo!=null){
				if(channelInfo.getCode().equals(list.get(0).getCode())){
					for (DrChannelKeyWords drChannelKeyWords : list) {
						drChannelKeyWords.setCid(id);
						drChannelKeyWords.setCode(channelInfo.getCode());
						drChannelKeyWords.setAddUserKey(user.getUserKy().intValue());
						drChannelKeyWordsService.saveOrUpdate(drChannelKeyWords);
					}
					br.setMsg("导入成功");
					br.setSuccess(true);
				}else{
					br.setErrorMsg("excel中渠道号不正确");
					br.setSuccess(false);
				}
			}else{
				br.setErrorMsg("导入数据为空！");
				br.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("导入渠道关键字失败",e);
			br.setErrorMsg(e.getMessage());
			br.setSuccess(false);
		}
		return br;
	}
	
	@RequestMapping("/queryChannelKeyword")
	@ResponseBody
	public PageInfo queryChannelKeyword(HttpServletRequest request, DrChannelKeyWords keywords,
			Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", keywords.getCode());
		map.put("keyword", keywords.getKeyword());
		pi = drChannelKeyWordsService.selectKeywordListByParam(map, pi);
		return pi;
	}
	
	@RequestMapping("/updateKeyWordStatus")
	@ResponseBody
	public BaseResult updateKeyWordStatus(DrChannelKeyWords keywords){
		BaseResult br = new BaseResult();
		try {
			Integer row = drChannelKeyWordsService.updateKeyWordStatusById(keywords.getId(), keywords.getStatus());
			if(row>0){
				br.setMsg("修改成功");
				br.setSuccess(true);
			}else{
				br.setMsg("修改失败");
				br.setSuccess(true);
			}
		} catch (Exception e) {
			log.error("修改关键字状态失败",e);
			br.setMsg("修改失败");
		}
		return br;
	}
	
	
	/**
	 * 修改是否参与推荐首投返利活动
	 * @param request
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/updateDrChannelInfoIsParticipation")
	@ResponseBody
	public BaseResult updateDrChannelInfoIsParticipation(Integer id,Integer isParticipation,HttpServletRequest request){
		BaseResult br = new BaseResult();
		try {
			request.setCharacterEncoding("UTF-8");
			SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			
			DrChannelInfo drChannelInfo = drChannelInfoService.getDrChannelInfoByid(id);
			if(drChannelInfo.getIsParticipation() == isParticipation){
				if(0 == isParticipation){
					drChannelInfo.setIsParticipation(1);
				}else{
					drChannelInfo.setIsParticipation(0);
				}
			}
			
			drChannelInfo.setUpdUser(usersVo.getUserKy().intValue());
			drChannelInfoService.updateDrChannelInfo(drChannelInfo);
			
			br.setMsg("更改成功!");
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("更改失败", e);
			br.setErrorMsg("更改失败!");
			br.setSuccess(false);
		}
		return br;
	}
	/**
	 * 跳转到渠道订单(首投)列表页面
	 * @throws IOException 
	 */
	@RequestMapping("/toDrChannelInfoOrderListFirst")
	public String toDrChannelInfoOrderListFirst(Map<String,Object> model,HttpServletRequest request) throws IOException {
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		
		List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(null);
		model.put("isAuth", 0);
		for(DrChannelInfo drChannelInfo : list){
			if(usersVo.getLoginId().toString().equals(drChannelInfo.getCode())){
				model.put("isAuth", 1);
				model.put("code", usersVo.getLoginId());
			}
		}
		model.put("channel", list);
		model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
		return "system/cpa/drChannelInfoOrderListFirst";
	}
	
	/**
	 * 跳转到渠道订单变更页面
	 */
	@RequestMapping("/toDrChannelInfoOrderUpdate")
	public String toDrChannelInfoOrderUpdate(Map<String,Object> model,HttpServletRequest request) {
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		
		List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(null);
		model.put("isAuth", 0);
		for(DrChannelInfo drChannelInfo : list){
			if(usersVo.getLoginId().toString().equals(drChannelInfo.getCode())){
				model.put("isAuth", 1);
				model.put("code", usersVo.getLoginId());
			}
		}
		model.put("channel", list);
		return "system/cpa/drChannelInfoOrderUpdate";
	}
	
	/**
	 * 渠道订单变更
	 */
	@RequestMapping("/drChannelInfoOrderUpdate")
	@ResponseBody
	public BaseResult drChannelInfoOrderUpdate(DrChannelInfo drChannelInfo,HttpServletRequest request) {
		BaseResult br = new BaseResult();
		try {
			DrMember drMember = new DrMember();
			drMember.setToFrom(drChannelInfo.getCode());
			drMember.setUid(drChannelInfo.getUid());
			drMemberService.updateDrmember(drMember);
			br.setSuccess(true);
			br.setMsg("成功！");
		} catch (Exception e) {
			e.printStackTrace();
			br.setSuccess(false);
			br.setErrorMsg("失败！");
		}
		return br;
	}
	
	@RequestMapping(value= "/drChannelInfoOrderUpdateList")
	@ResponseBody
	public PageInfo drChannelInfoOrderUpdateList(DrMember drMember,Integer page,Integer rows,HttpServletRequest request) {
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = drChannelInfoService.selectDrMemberByChannel(drMember, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 获取渠道列表
	 */
	@RequestMapping("/getDrChannelInfoList")
	@ResponseBody
	public List<DrChannelInfo> drChannelInfoList(Map<String,Object> model,HttpServletRequest request) {
		List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(null);
		return list;
	}
	/**
	 * 显示用户渠道订单列表数据（首投）
	 * @param DrProductInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/drChannelInfoOrderListFirst")
	@ResponseBody
	public PageInfo drChannelInfoOrderListFirst(DrChannelInfo drChannelInfo,Integer page,Integer rows,HttpServletRequest request) {
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(null);
		for(DrChannelInfo info : list){
			if(usersVo.getLoginId().toString().equals(info.getCode())){
				drChannelInfo.setCode(usersVo.getLoginId());
			}
		}
		
		BaseResult result = drChannelInfoService.getDrChannelInfoOrderListFirst(drChannelInfo, pi);
		return (PageInfo)result.getMap().get("page");
	}
	/**
	 * 用户渠道订单导出（首投）
	 */
	@RequestMapping("/exportChannelOrderRecordFirst")
	public ModelAndView exportChannelOrderRecordFirst(HttpServletRequest req,DrChannelInfo drChannelInfo){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			map = drChannelInfoService.exportChannelOrderFirst(drChannelInfo);
		} catch (Exception e) {
			log.error("导出渠道用户信息失败", e);
		}
		
		return new ModelAndView(new JXLExcelView(), map);
	}
	
	@RequestMapping("/exportChannelUpdateList")
	public ModelAndView exportChannelUpdateList(HttpServletRequest req,DrChannelInfo drChannelInfo){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			drChannelInfo.setIsUpdate(1);
			map = drChannelInfoService.exportChannelOrderFirst(drChannelInfo);
		} catch (Exception e) {
			log.error("导出渠道用户信息失败", e);
		}
		
		return new ModelAndView(new JXLExcelView(), map);
	}
	
	/**
	 * 跳转到渠道订单列表页面
	 * @throws IOException 
	 */
	@RequestMapping("/toDrChannelInfoOrderListNew")
	public String toDrChannelInfoOrderListNew(Map<String,Object> model,HttpServletRequest request) throws IOException {
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(null);
		model.put("isAuth", 0);
		for(DrChannelInfo drChannelInfo : list){
			if(usersVo.getLoginId().toString().equals(drChannelInfo.getCode())){
				model.put("isAuth", 1);
				model.put("code", usersVo.getLoginId());
			}
		}
		model.put("channel", list);
		model.put("repayType",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("repayType")));
		return "system/cpa/drChannelInfoOrderListNew";
	}
	
	/**
	 * 显示渠道订单列表数据
	 * @param DrProductInfo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/drChannelInfoOrderListNew")
	@ResponseBody
	public PageInfo drChannelInfoOrderListNew(DrChannelInfo drChannelInfo,Integer page,Integer rows,HttpServletRequest request) {
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(null);
		for(DrChannelInfo info : list){
			if(usersVo.getLoginId().toString().equals(info.getCode())){
				drChannelInfo.setCode(usersVo.getLoginId());
			}
		}
		
		BaseResult result = drChannelInfoService.getDrChannelInfoOrderListNew(drChannelInfo, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	@RequestMapping(value= "/firstDrChannelInfoOrderListNew")
	@ResponseBody
	public PageInfo firstDrChannelInfoOrderListNew(DrChannelInfo drChannelInfo,Integer page,Integer rows,HttpServletRequest request) {
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		List<DrChannelInfo> list = drChannelInfoService.getDrChannelInfoListForMap(null);
		for(DrChannelInfo info : list){
			if(usersVo.getLoginId().toString().equals(info.getCode())){
				drChannelInfo.setCode(usersVo.getLoginId());
			}
		}
		
		BaseResult result = drChannelInfoService.getFirstDrChannelInfoOrderListNew(drChannelInfo, pi);
		return (PageInfo)result.getMap().get("page");
	}
}
