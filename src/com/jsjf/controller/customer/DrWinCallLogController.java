package com.jsjf.controller.customer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.FileUtil;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.system.SysChooseOption;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.member.DrWinCallLogService;
import com.jsjf.service.system.SysChooseOptionService;

@RequestMapping("/membercall")
@Controller
public class DrWinCallLogController {
	private Logger log = Logger.getLogger(DrWinCallLogController.class);
	
	@Autowired
	private DrWinCallLogService callLogService;
	
	@Autowired
	private SysChooseOptionService sysChooseOptionService;
	
	@Autowired
	private DrMemberService drMemberService;
	
	
	
	
	
	/**
	 * 查询客户的电话记录
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/selWincallLog",method = RequestMethod.POST)
	@ResponseBody
	public String selWincallLog(HttpServletRequest req,Integer page,Integer rows,
			@RequestParam(value="moblie",required=false) String moblie,String recommCodes) {
		Map<String,Object> map=new HashMap();
		if((moblie!=null && !moblie.equals("")) || (recommCodes!=null && !recommCodes.equals(""))){
			map.put("moblie",moblie);
			map.put("recommCodes",recommCodes);
		}else{
			map.put("moblie","00000000000000000");
		}
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = 5;
		}
		PageInfo pi = new PageInfo(page,rows);
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<Map<String,Object>> list= callLogService.selWincallLog(map);
		int count=callLogService.selWincallLogCount(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		resultMap.put("total", count);
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	/**
	 * 新增客户的电话记录
	 * @param map
	 * @return
	 */
	@RequestMapping("/savewincalllog")
	@ResponseBody
	public String savewincalllog(HttpServletRequest req,
			@RequestParam(value="moblie",required=false) String moblie,
			@RequestParam(value="remerk",required=false) String remerk,
			/*@RequestParam(value="title",required=false) String title,*/
			@RequestParam(value="calldate",required=false) String calldate,
			@RequestParam(value="name",required=false) String name,
			@RequestParam(value="type",required=false) Integer type,
			@RequestParam(value="appointDate",required=false) String appointDate) {
		Map<String,Object> map=new HashMap();
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		if(moblie.length()>6){//moblie可能为手机号也可能为推荐码
			map.put("moblie", moblie);//手机号
		}else{//推荐码
			DrMember drMember=new DrMember();
			drMember.setRecommCodes(moblie);
			drMember= drMemberService.selectByMobilephone(drMember);
			map.put("moblie", drMember.getMobilephone());//手机号
		}
		
		map.put("remerk", remerk);
		map.put("title", "");
		map.put("calldate",calldate);
		map.put("name", name);
		map.put("userKy", usersVo.getUserKy());
		map.put("type", type);
		if(appointDate!=null && !appointDate.equals("")){
			map.put("appointDate", appointDate);
		}else{
			map.put("appointDate", null);
		}
		callLogService.insert(map);
		return "success";
	}
	
	
	/**
	 * 查询客户zhuangt
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/seltype",method = RequestMethod.POST)
	@ResponseBody
	public String seltype() {
		List<SysChooseOption> list=sysChooseOptionService.select();
		JSONArray jsonArray=JSONArray.fromObject(list);
		return jsonArray.toString();
	}
	
	/**
	 * 新增客户的电话记录
	 * @param map
	 * @return
	 */
	@RequestMapping("/updatewincalllog")
	@ResponseBody
	public String updatewincalllog(HttpServletRequest req,
			@RequestParam(value="id",required=false) Integer id) {
		Map<String,Object> map=new HashMap();
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		map.put("userKy", usersVo.getUserKy());
		map.put("id", id);
		callLogService.update(map);
		return "success";
	}
	/**
	 * 跳转到首投详细信息页面
	 * @param req
	 * @param isCps
	 * @param userKy
	 * @return
	 */
	@RequestMapping("/toFirstShotInfo")
	public String toFirstShotInfo(HttpServletRequest req,Integer isCps,Integer userKy,String startDate) {
		req.setAttribute("isCps",isCps);
		req.setAttribute("userKy",userKy);
		req.setAttribute("startDate",startDate);
		return "system/customer/firstShotInfo";
	}
	/**
	 * 首投详细信息
	 * @param req
	 * @param isCps
	 * @param userKy
	 * @return
	 */
	@RequestMapping(value = "/firstShotInfo",method = RequestMethod.POST)
	@ResponseBody
	public PageInfo firstShotInfo(@RequestParam Map<String,Object> param,Integer page,Integer rows) {
		BaseResult br = new BaseResult();
		if(param.get("page") == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(param.get("rows") == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		br = callLogService.getFirShotInfo(pi,param);
		return (PageInfo)br.getMap().get("page");
	}
	/**
	 * 跳转到复投详细信息页面
	 * @param req
	 * @param isCps
	 * @param userKy
	 * @return
	 */
	@RequestMapping("/toDoubleShotInfo")
	public String toDoubleShotInfo(HttpServletRequest req,Integer userKy,String startDate) {
		req.setAttribute("userKy",userKy);
		req.setAttribute("startDate",startDate);
		return "system/customer/doubleShotInfo";
	}
	/**
	 * 复投详细信息
	 * @param req
	 * @param isCps
	 * @param userKy
	 * @return
	 */
	@RequestMapping(value = "/doubleShotInfo",method = RequestMethod.POST)
	@ResponseBody
	public PageInfo doubleShotInfo(@RequestParam Map<String,Object> param,Integer page,Integer rows) {
		BaseResult br = new BaseResult();
		if(param.get("page") == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(param.get("rows") == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		br = callLogService.getDoubleShotInfo(pi,param);
		return (PageInfo)br.getMap().get("page");
	}
	/**
	 * 绑卡明细跳转页面
	 * @param req
	 * @param userKy
	 * @param startDate
	 * @return
	 */
	@RequestMapping("/toTiedCard")
	public String toTiedCard(HttpServletRequest req,Integer userKy,String startDate) {
		req.setAttribute("userKy",userKy);
		req.setAttribute("startDate",startDate);
		return "system/customer/tiedCard";
	}
	/**
	 * 绑卡明细list
	 * @param param
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/tiedCard",method = RequestMethod.POST)
	@ResponseBody
	public PageInfo tiedCard(@RequestParam Map<String,Object> param,Integer page,Integer rows) {
		BaseResult br = new BaseResult();
		if(param.get("page") == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(param.get("rows") == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		br = callLogService.getTiedCard(pi,param);
		return (PageInfo)br.getMap().get("page");
	}
	
	/**
	 *  进入电销业绩核算
	 * @param map
	 * @return
	 */
	@RequestMapping("/toSalesCallsAchievement")
	public String toSalesCallsAchievement(HttpServletRequest req,Map<String,Object> model){
		
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		if ("1".equals(usersVo.getDeptId())){
			model.put("dept", 1);
		}else if ("2".equals(usersVo.getDeptId())) {
			model.put("dept", 2);
		}		
		return "system/customer/salesCallsAchievementList";
	}
	
	/**
	 *  电销业绩核算
	 * @param map
	 * @return
	 */
	@RequestMapping("/salesCallsAchievement")
	@ResponseBody
	public PageInfo salesCallsAchievement(HttpServletRequest req,Integer page, Integer rows,Integer dept,String startDate){
				
		PageInfo info = new PageInfo(page, rows);
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("dept", dept);
		map.put("offset", info.getPageInfo().getOffset());
		map.put("limit", info.getPageInfo().getLimit());
		if (!Utils.strIsNull(startDate)) {
			try {
				if (Utils.format(Utils.parse(startDate, "yyyy-MM"), "yyyyMM").equals(Utils.format(new Date(), "yyyyMM"))) {
					map.put("nowDate", Utils.format(new Date(), "yyyyMMdd"));
				}
				map.put("startDate", Utils.format(Utils.parse(startDate, "yyyy-MM"), "yyyyMM"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			map.put("nowDate", Utils.format(new Date(), "yyyyMMdd"));
			map.put("startDate", Utils.format(new Date(), "yyyyMM"));
		}
		info = callLogService.salesCallsAchievement(map);
		return info;
	}
	 /**
	  * 导出电销业绩核算
	  * @return
	  * @throws exportSalesAchievement 
	  */
 	 @RequestMapping("/exportSalesAchievement")
 	 @ResponseBody
 	 public String exportSalesAchievement(HttpServletRequest request, HttpServletResponse response,Integer dept,String startDate)  {
 		BaseResult br = new BaseResult();
 		FileOutputStream os = null;
 		String exportSalesFlag = (String) request.getSession().getAttribute("exportSalesFlag");
		try {
			if (exportSalesFlag ==null) {
				request.getSession().setAttribute("exportSalesFlag", "");
				
				long l = System.currentTimeMillis();
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("dept", dept);
				if (!Utils.strIsNull(startDate)) {
					try {
						if (Utils.format(Utils.parse(startDate, "yyyy-MM"), "yyyyMM").equals(Utils.format(new Date(), "yyyyMM"))) {
							map.put("nowDate", Utils.format(new Date(), "yyyyMMdd"));
						}
						map.put("startDate", Utils.format(Utils.parse(startDate, "yyyy-MM"), "yyyyMM"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}else{
					map.put("nowDate", Utils.format(new Date(), "yyyyMMdd"));
					map.put("startDate", Utils.format(new Date(), "yyyyMM"));
				}
				
				InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/template/exportSalesAchievement.xlsx");
				XSSFWorkbook workeBook = new XSSFWorkbook(in);//in表示在内存中多少行，超过in这个参数值的显示到磁盘中，-->创建了一个excel文件
				
				//读取模板
				SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmss");
				String filename = "exportSalesAchievement-" + sdf1.format(new Date());
				File file =  new File(  request.getRealPath( "/" ) + filename +".xlsx");
				os = new FileOutputStream(file);
				System.out.println("业绩导出初始化耗时:"+(System.currentTimeMillis()-l)/1000);
				generateSalesAchievementExcel(workeBook,map);// 业绩总计
				System.out.println("业绩总计耗时:"+(System.currentTimeMillis()-l)/1000);
				generateStDetailExcel(workeBook,map);// 首投
				System.out.println("首投耗时:"+(System.currentTimeMillis()-l)/1000);
				generateFtDetailesAchievementExcel(workeBook,map);// 复投
				System.out.println("复投耗时:"+(System.currentTimeMillis()-l)/1000);
				generateBkDetailExcel(workeBook,map);// 绑卡
				System.out.println("绑卡耗时:"+(System.currentTimeMillis()-l)/1000);
				
				workeBook.write(os);
				
				os.flush();//flush是清空不是刷新，一般用在io中，当用读写流的时候，数据是先被读到内存中，然后用数据写到文件中的时候，读完但是不代表写完了，所以需要先将数据清空，意思就是将数据立刻写出。
				os.close();
				FileUtil.download(file,response);
				if(file.exists()){
					file.delete();
				}
				
				br.setSuccess(true);
				br.setMsg("Exprot success!");
				
				request.getSession().removeAttribute("exportSalesFlag");
				
				System.out.println("业绩导出最终耗时:"+(System.currentTimeMillis()-l)/1000);
				
			}else{
				br.setSuccess(false);
				br.setMsg("Waiting for the last request to end");
			}
		} catch (IOException e) {
			e.printStackTrace();
			br.setSuccess(false);
			br.setMsg("System exception!");
			if (os !=null) {
				try {
					os.close();
				} catch (IOException e1) {
				}
			}
			request.getSession().removeAttribute("exportSalesFlag");
		}
		JSONObject jsonObject = JSONObject.fromObject(br);
		return jsonObject.toString();
 	 	
 	 }
 	
 	 
 	 
 	 /**
 	  * 生成导出电销业绩-绑卡-明细核算表Excel
 	  * @param osw
 	  * @param productList
 	  */
 	 private void generateBkDetailExcel(XSSFWorkbook workeBook, Map<String,Object> map){
 		 try {
 			 List<Map<String,Object>> list = callLogService.exportTiedCard(map);
 			 if(null != list && !list.isEmpty()){
 				 
 				 XSSFSheet sheet = workeBook.getSheetAt(3);//创建一个工作
 				 XSSFRow row = null;
 				 XSSFRow row1 = sheet.getRow(1);
 				 int start = 1;
 				 for(int i = 0; i < list.size(); i ++){
 					 if(i == 0){
 						 //第四行，模板已经创建，不需要新增
 						 setBkRowValues(list.get(i), row1);
 						 if(list.size() > 1){//
 							 sheet.shiftRows(2, sheet.getLastRowNum()+1, list.size() - 1, true, false);//插入行
 						 }
 					 }else{
 						 row = sheet.createRow(start + i);
 						 copyRow(row1, row);
 						 setBkRowValues(list.get(i), row);
 					 }
 				 }
 				 sheet.setForceFormulaRecalculation(true); 
 				 list.clear();
 			 }
 		 } catch (ParseException e) {
 			 // TODO Auto-generated catch block
 			 e.printStackTrace();
 		 }
 	 }
 	 /**
 	  * 生成导出电销业绩-复投-明细核算表Excel
 	  * @param osw
 	  * @param productList
 	  */
 	 private void generateFtDetailesAchievementExcel(XSSFWorkbook workeBook, Map<String,Object> map){
 		 try {
 			 List<Map<String,Object>> list = callLogService.exportDoubleShotInfoList(map);
 			 if(null != list && !list.isEmpty()){
 				 
 				 XSSFSheet sheet = workeBook.getSheetAt(2);//创建一个工作
 				 XSSFRow row = null;
 				 XSSFRow row1 = sheet.getRow(1);
 				 int start = 1;
 				 for(int i = 0; i < list.size(); i ++){
 					 if(i == 0){
 						 //第四行，模板已经创建，不需要新增
 						 setFtRowValues(list.get(i), row1);
 						 if(list.size() > 1){//
 							 sheet.shiftRows(2, sheet.getLastRowNum()+1, list.size() - 1, true, false);//插入行
 						 }
 					 }else{
 						 row = sheet.createRow(start + i);
 						 copyRow(row1, row);
 						 setFtRowValues(list.get(i), row);
 					 }
 				 }
 				 sheet.setForceFormulaRecalculation(true); 
 				 list.clear();
 			 }
 		 } catch (ParseException e) {
 			 // TODO Auto-generated catch block
 			 e.printStackTrace();
 		 }
 	 }
 	 /**
 	  * 生成导出电销业绩-首投-明细核算表Excel
 	  * @param osw
 	  * @param productList
 	  */
 	 private void generateStDetailExcel(XSSFWorkbook workeBook, Map<String,Object> map){
 		 try {
 			 List<Map<String,Object>> list = callLogService.exportFirShotInfoList(map);
 			 if(null != list && !list.isEmpty()){
 				 
 				 XSSFSheet sheet = workeBook.getSheetAt(1);//创建一个工作
 				 XSSFRow row = null;
 				 XSSFRow row1 = sheet.getRow(1);
 				 int start = 1;
 				 for(int i = 0; i < list.size(); i ++){
 					 if(i == 0){
 						 //第四行，模板已经创建，不需要新增
 						setStRowValues(list.get(i), row1);
 						 if(list.size() > 1){//
 							 sheet.shiftRows(2, sheet.getLastRowNum()+1, list.size() - 1, true, false);//插入行
 						 }
 					 }else{
 						 row = sheet.createRow(start + i);
 						 copyRow(row1, row);
 						setStRowValues(list.get(i), row);
 					 }
 				 }
 				 sheet.setForceFormulaRecalculation(true); 
 				 list.clear();
 			 }
 		 } catch (ParseException e) {
 			 // TODO Auto-generated catch block
 			 e.printStackTrace();
 		 }
 	 }
 	/**
 	 * 生成导出电销业绩核算表Excel
 	 * @param osw
 	 * @param productList
 	 */
 	private void generateSalesAchievementExcel(XSSFWorkbook workeBook, Map<String,Object> map){
 		try {
 			List<Map<String,Object>> list = callLogService.exportSalesAchievement(map);
 			if(null != list && !list.isEmpty()){
			
				XSSFSheet sheet = workeBook.getSheetAt(0);//创建一个工作

				XSSFRow row = null;
				XSSFRow row3 = sheet.getRow(3);
				int start = 3;
				for(int i = 0; i < list.size(); i ++){
					if(i == 0){
						//第四行，模板已经创建，不需要新增
						setRowValues(list.get(i), row3);
						if(list.size() > 2){// list 里包涵了总计所以这里 >2
							sheet.shiftRows(4, sheet.getLastRowNum(), list.size() - 2, true, false);//插入行
						}
					}else if(i == list.size()-1){
						XSSFRow rowLast = sheet.getRow(list.size()-1+3);
						setRowValues(list.get(i), rowLast);
					}else{
						row = sheet.createRow(start + i);
						copyRow(row3, row);
						setRowValues(list.get(i), row);
					}
				}
				sheet.setForceFormulaRecalculation(true); 
				list.clear();
			}
 		} catch (ParseException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 	}
 	
 	/**
 	 * 首投赋值
 	 * @param product
 	 * @param sheet
 	 * @param index
 	 * @throws ParseException
 	 */
 	private void setStRowValues(Map<String,Object> map, XSSFRow row) throws ParseException{
 		getCell(row, 0 ).setCellValue(map.get("allot").toString());//		
 		getCell(row, 1 ).setCellValue(map.get("stid").toString());//		
 		getCell(row, 2 ).setCellValue(map.get("recommCodes").toString());//		
 		getCell(row, 3 ).setCellValue(map.get("uid").toString());//		
 		getCell(row, 4 ).setCellValue(map.get("mobilePhone").toString());//		
 		getCell(row, 5 ).setCellValue(map.get("type").toString());//		
 		getCell(row, 6 ).setCellValue(map.get("amount").toString());//		
 		getCell(row, 7 ).setCellValue(map.get("deadline").toString());//		
 		getCell(row, 8 ).setCellValue(map.get("investTime").toString());//		
 		getCell(row, 9 ).setCellValue(map.get("regdate").toString());//		
 		getCell(row, 10).setCellValue(map.get("calldate").toString());//		
 		getCell(row, 11).setCellValue(map.get("name").toString());//		
 	}                
 	/**
 	 * 复投赋值
 	 * @param product
 	 * @param sheet
 	 * @param index
 	 * @throws ParseException
 	 */
 	private void setFtRowValues(Map<String,Object> map, XSSFRow row) throws ParseException{
 		getCell(row, 0 ).setCellValue(map.get("allot").toString());//		
 		getCell(row, 1 ).setCellValue(map.get("id").toString());//		
 		getCell(row, 2 ).setCellValue(map.get("recommCodes").toString());//		
 		getCell(row, 3 ).setCellValue(map.get("uid").toString());//		
 		getCell(row, 4 ).setCellValue(map.get("mobilePhone").toString());//		
 		getCell(row, 5 ).setCellValue(map.get("regdate").toString());//		
 		getCell(row, 6 ).setCellValue(map.get("investTime").toString());//		
 		getCell(row, 7 ).setCellValue(map.get("amount").toString());//		
 		getCell(row, 8 ).setCellValue(map.get("deadline").toString());//		
 		getCell(row, 9 ).setCellValue(map.get("calldate").toString());//		
 		getCell(row, 10).setCellValue(map.get("name").toString());//		
 	
 	}                
 	/**
 	 * 绑卡赋值
 	 * @param product
 	 * @param sheet
 	 * @param index
 	 * @throws ParseException
 	 */
 	private void setBkRowValues(Map<String,Object> map, XSSFRow row) throws ParseException{
 		getCell(row, 0 ).setCellValue(map.get("allot").toString());//		
 		getCell(row, 1 ).setCellValue(map.get("recommCodes").toString());//		
 		getCell(row, 2 ).setCellValue(map.get("uid").toString());//		
 		getCell(row, 3 ).setCellValue(map.get("mobilePhone").toString());//		
 		getCell(row, 4 ).setCellValue(map.get("regdate").toString());//		
 		getCell(row, 5 ).setCellValue(map.get("mchnt_txn_ssn").toString());//		
 		getCell(row, 6 ).setCellValue(map.get("calldate").toString());//		
 		getCell(row, 7 ).setCellValue(map.get("name").toString());//
 	}                
 	/**
	 * 业绩赋值
	 * @param product
	 * @param sheet
	 * @param index
	 * @throws ParseException
	 */
	private void setRowValues(Map<String,Object> map, XSSFRow row) throws ParseException{
		if (!"总计".equals(map.get(map.get("userKy").toString()))) {
			getCell(row, 0).setCellValue(map.get("dept").toString());//
			getCell(row, 1).setCellValue(map.get("name").toString());//	
		}
		int i=2;
		getCell(row, i++ ).setCellValue(map.get("sc30").toString());//		
		getCell(row, i++ ).setCellValue(map.get("sc35").toString());//		
		getCell(row, i++ ).setCellValue(map.get("sc45").toString());//		
		getCell(row, i++ ).setCellValue(map.get("sc60").toString());//		
		getCell(row, i++ ).setCellValue(map.get("sc70").toString());//		
		getCell(row, i++ ).setCellValue(map.get("sc75").toString());//		
		getCell(row, i++ ).setCellValue(map.get("sc90").toString());//		
		getCell(row, i++ ).setCellValue(map.get("sc150").toString());//		
		getCell(row, i++ ).setCellValue(map.get("sc180").toString());//		
		getCell(row, i++ ).setCellValue(map.get("schz").toString());//		
		getCell(row, i++ ).setCellValue(map.get("s30").toString());//		
		getCell(row, i++ ).setCellValue(map.get("s35").toString());//		
		getCell(row, i++ ).setCellValue(map.get("s45").toString());//		
		getCell(row, i++ ).setCellValue(map.get("s60").toString());//		
		getCell(row, i++ ).setCellValue(map.get("s70").toString());//		
		getCell(row, i++ ).setCellValue(map.get("s75").toString());//		
		getCell(row, i++ ).setCellValue(map.get("s90").toString());//		
		getCell(row, i++ ).setCellValue(map.get("s150").toString());//		
		getCell(row, i++ ).setCellValue(map.get("s180").toString());//		
		getCell(row, i++ ).setCellValue(map.get("shz").toString());//		
		getCell(row, i++ ).setCellValue(map.get("f30").toString());//		
		getCell(row, i++ ).setCellValue(map.get("f35").toString());//		
		getCell(row, i++ ).setCellValue(map.get("f45").toString());//		
		getCell(row, i++ ).setCellValue(map.get("f60").toString());//		
		getCell(row, i++ ).setCellValue(map.get("f70").toString());//		
		getCell(row, i++ ).setCellValue(map.get("f75").toString());//		
		getCell(row, i++ ).setCellValue(map.get("f90").toString());//		
		getCell(row, i++ ).setCellValue(map.get("f150").toString());//		
		getCell(row, i++ ).setCellValue(map.get("f180").toString());//		
		getCell(row, i++ ).setCellValue(map.get("fhz").toString());//		
		getCell(row, i++ ).setCellValue(map.get("total").toString());//		
		getCell(row, i++ ).setCellValue(map.get("realverifys").toString());//
	}                
	                 
	/**              
	 * 获取单元格    
	 * @param row
	 * @param index
	 * @return
	 */
	private XSSFCell getCell(XSSFRow row, int index){
		XSSFCell cell = row.getCell(index);
		if(cell == null){
			cell = row.createCell(index);
		}
		return cell;
	}
	//复制行
	private void copyRow(XSSFRow sourceRow, XSSFRow targetRow){
        int columnCount = sourceRow.getLastCellNum();  
        if (sourceRow != null) {  
        	targetRow.setHeight(sourceRow.getHeight());  
            for (int j = 0; j < columnCount; j++) {  
            	XSSFCell templateCell = sourceRow.getCell(j);  
                if (templateCell != null) {  
                    XSSFCell newCell = targetRow.createCell(j);  
                    copyCell(templateCell, newCell);
                }  
            }  
        }  
	}
	//复制单元格
	private void copyCell(XSSFCell srcCell, XSSFCell distCell) {  
        distCell.setCellStyle(srcCell.getCellStyle());  
        if (srcCell.getCellComment() != null) {  
            distCell.setCellComment(srcCell.getCellComment());  
        }  
        int srcCellType = srcCell.getCellType();  
        distCell.setCellType(srcCellType);  
        if (srcCellType == XSSFCell.CELL_TYPE_NUMERIC) {  
            if (HSSFDateUtil.isCellDateFormatted(srcCell)) {  
                distCell.setCellValue(srcCell.getDateCellValue());  
            } else {  
                distCell.setCellValue(srcCell.getNumericCellValue());  
            }  
        } else if (srcCellType == XSSFCell.CELL_TYPE_STRING) {  
            distCell.setCellValue(srcCell.getRichStringCellValue());  
        } else if (srcCellType == XSSFCell.CELL_TYPE_BLANK) {  
            // nothing21  
        } else if (srcCellType == XSSFCell.CELL_TYPE_BOOLEAN) {  
            distCell.setCellValue(srcCell.getBooleanCellValue());  
        } else if (srcCellType == XSSFCell.CELL_TYPE_ERROR) {  
            distCell.setCellErrorValue(srcCell.getErrorCellValue());  
        } else if (srcCellType == XSSFCell.CELL_TYPE_FORMULA) {  
            distCell.setCellFormula(srcCell.getCellFormula());  
        } else { // nothing29  
  
        }  
    } 
 	 
}
