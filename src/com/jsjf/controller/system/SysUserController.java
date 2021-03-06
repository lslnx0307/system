package com.jsjf.controller.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.SecurityUtils;
import com.jsjf.common.Utils;
import com.jsjf.model.system.SysMenuVo;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.system.SysMenuVoService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.SysRoleVoService;
import com.jsjf.service.system.SysUserRoleVoService;
import com.jsjf.service.system.SysUsersVoService;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping ( "/user" )
public class SysUserController {
	private Logger log = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	SysRoleVoService sysRoleVoService;
	
	@Autowired
	SysMenuVoService sysMenuVoService;
	
	@Autowired
	SysUsersVoService sysUsersVoService;
	
	@Autowired
	SysUserRoleVoService sysUserRoleVoService;
	
	@Autowired
	SysMessageLogService sysMessageLogService;
	
	
	@RequestMapping("/jsSystemNotice")
	@ResponseBody
	public Map<String,Object> jsSystemNotice(){
		
		return null;
	}
	
	
	/**
	 * @Description 后台登陆页面
	 * @param 
	 * @return
	 */
	@RequestMapping("/toLogin")
	public String tologin(Map<String,Object> model) {
		model.put("sysUser", new SysUsersVo());
		return "/system/sys/login";
	}
	
	/**
	 * 传递到添加页面
	 * @param request.getSession()
	 */
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request,Map<String,Object> model) {
		try {
			model.put("deptCode",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("deptCode")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.put("sysUser",new SysUsersVo());
		return "/system/sys/user/sysUseradd";
	}
	
	/**
	 * 添加用户
	 * @throws Exception 
	 */
	@RequestMapping("/addUser")
	@ResponseBody
	public Map<String,Object> addUser(@ModelAttribute("sysUser") SysUsersVo usersVo,Map<String,Object> map) throws Exception{
		usersVo.setStatus((short)1);
		usersVo.setRegistertime(new Date());		
		usersVo.setLastLoginIp("");
		map = sysUsersVoService.insertUser(usersVo);
		return map;
	}
	
	/**
	 * 分页显示全部
	 * 
	 */
	@RequestMapping("/userList")
	@ResponseBody
	public PageInfo userList(HttpServletRequest request,Integer page,Integer rows, @ModelAttribute("sysUser") SysUsersVo user) {
		if(page==null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows==null){
			rows = PageInfo.DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = sysUsersVoService.getUserList(pi,user);
		return (PageInfo)result.getMap().get("page");
	}
	
	@RequestMapping("toList")
	public String toList(Map<String,Object> model){
		try {
			model.put("deptCode",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("deptCode")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/system/sys/user/userList";
		
	}
	
	/**
	 * 登陆
	 * @param request
	 * @return
	 */
	@RequestMapping("/toLoginUser")
	public String toLoginUser(HttpServletRequest request){
		SysUsersVo vo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			if(vo != null){
				//一级菜单列表
				List<SysMenuVo> list1 = sysMenuVoService.findByListMenu(vo.getUserKy());
				if(list1.size()>0){
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("userKy", vo.getUserKy());
					//2、3级菜单集合
					List<SysMenuVo> list23 = sysMenuVoService.getMapMenuList_2_3(map);
					request.getSession().setAttribute("sysMenuvo1", list1);
					request.getSession().setAttribute("sysMenuvo23", list23);
				}
				
				SysUsersVo usersVo2=new SysUsersVo();
				
				usersVo2.setLastLoginIp(Utils.getIpAddr(request));
				vo.setLastLoginIp(Utils.getIpAddr(request));
				usersVo2.setUserKy(vo.getUserKy());
				
				sysUsersVoService.updateByPrimaryKeySelective(usersVo2);
				request.getSession().setAttribute(ConfigUtil.ADMIN_LOGIN_USER, vo);
				log.info(new Date()+"-【"+vo.getName()+"】登录成功！登录IP:["+Utils.getIpAddr(request)+"]");
				//查权限
				return "/system/sys/index";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/system/sys/login";
	}
	/**
	 * @Description 用户登录验证 
	 * @param usersVo
	 * @return
	 */
	@RequestMapping("/doLoginUser")
	public String doLoginUser(HttpServletRequest request,SysUsersVo usersVo,String randCode,String mobileCode){
		request.getSession().setAttribute(ConfigUtil.ADMIN_LOGIN_USER, null);
		try {
			String randCodes = (String) request.getSession().getAttribute("rand");
			Object mobileCodes = request.getSession().getAttribute("mobileCode");
			String ip = Utils.getIpAddr(request);
			String ips = PropertyUtil.getProperties("nativeIP");
			if(!nativeIPIsTrue(ips, ip)){//
				if(Utils.isObjectEmpty(mobileCode) || !mobileCode.equals(mobileCodes))	//"手机验证码错误"			
					return "/system/sys/login";
			}
			if(Utils.strIsNull(randCode) || Utils.strIsNull(randCodes) ||!randCode.toUpperCase().equals(randCodes.toUpperCase()))//"图片验证码"
				return "/system/sys/login";
			
			usersVo.setPassword(SecurityUtils.MD5AndSHA256(usersVo.getPassword()));
			SysUsersVo vo = sysUsersVoService.getLoginUser(usersVo);
			if(vo != null){
				if(Utils.isObjectEmpty(vo.getLastLoginIp())){
					request.getSession().setAttribute(ConfigUtil.ADMIN_LOGIN_USER, vo);
					//首次登录去修改密码
					return "/system/sys/user/changePwd";
				}else{
					//一级菜单列表
					List<SysMenuVo> list1 = sysMenuVoService.findByListMenu(vo.getUserKy());
					if(list1.size()>0){
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("userKy", vo.getUserKy());
						//2、3级菜单集合
						List<SysMenuVo> list23 = sysMenuVoService.getMapMenuList_2_3(map);
						request.getSession().setAttribute("sysMenuvo1", list1);
						request.getSession().setAttribute("sysMenuvo23", list23);
					}
					
					SysUsersVo usersVo2 = new SysUsersVo();
					
					usersVo2.setLastLoginIp(Utils.getIpAddr(request));
					vo.setLastLoginIp(Utils.getIpAddr(request));
					usersVo2.setUserKy(vo.getUserKy());
					
					sysUsersVoService.updateByPrimaryKeySelective(usersVo2);
					request.getSession().setAttribute(ConfigUtil.ADMIN_LOGIN_USER, vo);
					System.out.println("登陆成功！");
					System.out.println(new Date()+"-【"+vo.getLoginId()+"】登录成功！登录IP:["+Utils.getIpAddr(request)+"]");
					//查权限
					return "/system/sys/index";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(new Date()+"-【"+usersVo.getLoginId()+"】登录失败！登录IP:["+Utils.getIpAddr(request)+"]");
		return "/system/sys/login";
		
	}
	
	public boolean nativeIPIsTrue(String ips,String ip){
		if(Utils.strIsNull(ips)){
			return true;
		}
		String [] ipArray = ips.split(",");
		for (String str : ipArray) {
			if(str.equals(ip))
				return true;
		}
		return false;
	}

	/**
	 * 退出
	 * 
	 * @return
	 */
	@RequestMapping("/exit")
	public String exit(HttpSession session){
		session.invalidate();
		return "/system/sys/login";
	}
	
	@RequestMapping("/tomodify")
	public String toModify(HttpServletRequest request,Long userKy,Map<String,Object> model) {
		SysUsersVo  user = sysUsersVoService.selectByPrimaryKey(userKy);
		model.put("sysUser", user);
		model.put("tabid", request.getParameter("tabid"));
		try {
			model.put("deptCode",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("deptCode")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/system/sys/user/sysUsermodify";
	}
	
	// 更新用户数据操作
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, String> update(@ModelAttribute("sysUser") SysUsersVo user) {
		
		Map<String, String> jsonMap = new HashMap<String, String>(); 
		try {
			sysUsersVoService.updateByPrimaryKeySelective(user);
			jsonMap.put("message", "ok");
		} catch (Exception e) {
			jsonMap.put("message", "error");
			log.error(e);
		}
		return jsonMap;
	}
	
	// 更新用户数据操作
		@RequestMapping("/resetPassword")
		@ResponseBody
		public Map<String, String> resetPassword(@ModelAttribute("sysUser") SysUsersVo user) {
			SysUsersVo suv = new SysUsersVo();
			suv.setUserKy(user.getUserKy());
			suv.setPassword(SecurityUtils.MD5AndSHA256("123456"));
			Map<String, String> jsonMap = new HashMap<String, String>(); 
			try {
				sysUsersVoService.updateByPrimaryKeySelective(suv);
				jsonMap.put("message", "ok");
			} catch (Exception e) {
				jsonMap.put("message", "error");
				log.error(e);
			}
			return jsonMap;
		}
	
	//跳转到修改密码页面
	@RequestMapping("/toChangePwd")
	public String toChangePwd(){
		return "/system/sys/user/changePwd";
	}
	
	//用户修改密码
	@RequestMapping("/updateUserPassword")
	@ResponseBody
	public BaseResult updateUserPassword(HttpServletRequest request,@RequestParam("oldPassword") String password,SysUsersVo user){
		BaseResult result = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		if(usersVo==null){
			result.setSuccess(false);
			result.setErrorMsg("登录时间已过期，请重新登录!");
		}else{
			if(SecurityUtils.MD5AndSHA256(password).equals(usersVo.getPassword().toString())){
				try {
					String pwd = user.getPassword();
					String pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,16}$";
					Pattern r = Pattern.compile(pattern);
					Matcher matcher = r.matcher(pwd);
					if (matcher.find()) {
						pwd=SecurityUtils.MD5AndSHA256(user.getPassword());
						user.setUserKy(usersVo.getUserKy());
						user.setPassword(pwd);
						sysUsersVoService.changPwd(user);
						request.getSession().setAttribute(ConfigUtil.ADMIN_LOGIN_USER, usersVo);
						result.setSuccess(true);
						result.setMsg("修改密码成功！");
					}else{
						result.setSuccess(false);
						result.setErrorMsg("密码不是至少一个字母和一个数字的6~16位的组合");
					}
				} catch (Exception e) {
					result.setSuccess(false);
					result.setErrorMsg("修改密码失败!");
					e.printStackTrace();
				}
			}else{
				result.setSuccess(false);
				result.setErrorMsg("输入原密码不正确!");
			}
		}
		return result;
	}
	
	
	/**
	 * 生成图片验证码
	 * @param request
	 * @param response
	 * @return 
	 */
	@RequestMapping("/validateCode")
	public void generateValidateCode(HttpServletRequest request,HttpServletResponse response){
		OutputStream os = null;
		try{
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			int width = 60, height = 30;
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			os = response.getOutputStream();
			Graphics g = image.getGraphics();
			Random random = new Random();
			g.setColor(getRandColor(200, 250));
			g.fillRect(0, 0, width, height);
	
			g.setFont(new Font("Times New Roman", Font.PLAIN, 25));
			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 155; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}
	        // 设置备选验证码:包括"a-z"和数字"0-9"    
	        String base = "1234567890";    
	        int size = base.length();   
			String sRand = "";
			for (int i = 0; i < 4; i++) {
				//String rand = String.valueOf(random.nextInt(10));
				int start = random.nextInt(size);
				 String strRand = base.substring(start, start + 1);
				sRand += strRand;
				g.setColor(new Color(20 + random.nextInt(110), 20 + random
						.nextInt(110), 20 + random.nextInt(110)));
				g.drawString(strRand+"", 13 * i + 6, 25);
			}
			request.getSession().setAttribute("rand", sRand);
			g.dispose();
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream()); 
//			encoder.encode(image);
			ImageIO.write(image, "JPEG", os);
			os.flush();
			os.close();
			os = null;
			response.flushBuffer();
			request.getSession().setAttribute("rand", sRand);
			
		}catch (IllegalStateException e) {
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}finally{  
	        if(os !=null){
				try {
					os.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	
	

	/**
	 * 发送手机登录验证码
	* @param req
	* @param randCode
	* @return 
	* Map<String,Object> 
	* @throws
	 */
	@RequestMapping("/sendMobileCode")
	@ResponseBody
	public Map<String,Object> sendMobileCode(HttpServletRequest req,String randCode,String loginId,String password){
		Map<String,Object> result = new HashMap<String, Object>();
		Object Rand = req.getSession().getAttribute("rand");
		try {
			if(Utils.isObjectNotEmpty(randCode) && Utils.isObjectNotEmpty(Rand) && randCode.toUpperCase().equals(((String)Rand).toUpperCase())){
				result.put("msg", true);
				SysUsersVo vo = new SysUsersVo();
				vo.setLoginId(loginId);
				vo.setPassword(SecurityUtils.MD5AndSHA256(password));
				SysUsersVo user = sysUsersVoService.getLoginUser(vo);
				if(Utils.isObjectNotEmpty(user) && Utils.isObjectNotEmpty(user.getMobile())){
					String ip = Utils.getIpAddr(req);
					String ips = PropertyUtil.getProperties("nativeIP");
					if(nativeIPIsTrue(ips, ip)){
						result.put("msg", true);
						result.put("native", true);
						return result;
					}
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("type", 19);//登录手机验证码
					map.put("phone", user.getMobile());
					map.put("endDate", Utils.format(new Date(), "yyyy-MM-dd HH-mm-ss"));
					map.put("startDate", Utils.format(Utils.getTypeNumOfAppointDate(new Date(), 1,Calendar.HOUR_OF_DAY), "yyyy-MM-dd HH-mm-ss"));
					
					List<SysMessageLog> logList = sysMessageLogService.selectSysMessageLogList(map);
					
					if(logList.size() > Integer.parseInt(PropertyUtil.getProperties("sysLoginCodeSendNum"))){
						result.put("msg", false);
						result.put("errcode", "4");
						result.put("err", "一小时内获取手机验证码次数过多");
						return result;
					}
					
					String mobileCode = "";
					String base = "1234567890";   
					for(int i=0,index=0;i<4;i++){
						index = (int) (base.length()*Math.random());								
						mobileCode += base.substring(index,index+1);
					}
					req.getSession().setAttribute("mobileCode", mobileCode);
					SysMessageLog logs = new SysMessageLog(user.getUserKy().intValue(), "后台登录发送验证码:"+mobileCode, 19, null, user.getMobile());
					Integer flag = sysMessageLogService.sendMsg(logs);
					if(flag > 0){
						result.put("msg", true);
					}else{
						result.put("msg", false);
						result.put("errcode", "3");
						result.put("err", "手机号未发送成功,稍后再试");
						return result;
					}
					
				}else{
					result.put("msg", false);
					result.put("errcode", "1");
					result.put("err", "用户名或密码错误");
				}
			}else{
				result.put("msg", false);
				result.put("errcode", "2");
				result.put("err", "验证码错误");
			}
		} catch (Exception e) {
			result.put("msg", false);
			result.put("err", "系统错误");
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 验证手机登录成功
	 * @param req
	 * @param randCode
	 * @return
	 */
	@RequestMapping("/checkCode")
	@ResponseBody
	public Map<String,Object> login(HttpServletRequest req,String mobileCode,Integer nativeIp){
		Map<String,Object> result = new HashMap<String, Object>();
		Object mobileCodes = req.getSession().getAttribute("mobileCode");
		if(nativeIp == 1 || Utils.isObjectNotEmpty(mobileCode) && mobileCode.equals(((String)mobileCodes).toUpperCase())){
			result.put("msg", true);
		}else{
			result.put("msg", false);
			result.put("err", "手机验证码错误");
		}
		return result;
	}
	
	public Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	} 
	
	/**
	 * 跳转修改分机号页面
	 */
	@RequestMapping("/toupdateCallNum")
	public String toupdateCallNum(HttpServletRequest req,Map<String,Object> model){//跳转修改分机号页面
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		Map<String,Object> map=new HashMap();
		if(Utils.isObjectNotEmpty(usersVo)){
			map = sysUsersVoService.selectJsCallNull(usersVo.getUserKy());
			if(map!=null){
			model.putAll(map);
			}
		}
		return "system/sys/user/updateCallNum";
	}
	
	/**
	 * 修改分机号
	 */
	@RequestMapping(value="/updateCallNum", method = RequestMethod.POST)
	@ResponseBody
	public String updateCallNum(HttpServletRequest req,String userKy,String phone){//跳转修改分机号页面
		Map<String,Object> map=new HashMap();
		map.put("userKy", userKy);
		map.put("phone", phone);
		sysUsersVoService.updateCallNum(map);
		return "success";
	}
	/**
	 * 查找操作人
	 * @return
	 */
	@RequestMapping(value="/selectOperater")
	@ResponseBody
	public String selectOperater(HttpServletRequest req,Integer status,String rName){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("status", status);
		try {
			map.put("rName", new String(rName.getBytes("iso8859-1"),"utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("rName", "电销经理");
		}
		List<Map<String,Object>> list = sysUsersVoService.selectOperateByMap(map);
		JSONArray jsonArray=JSONArray.fromObject(list);
		return jsonArray.toString();
	}

	
	/**
	 * 跳转修改分机号页面
	 */
	@RequestMapping("/toCallNumManagement")
	public String toCallNumManagement(HttpServletRequest req,Map<String,Object> model){//跳转分机号管理页面
		return "system/sys/user/callNumManagement";
	}
	
	/**
	 * 查询角色为电销 电销主管的用户
	 * @param map
	 * @return
	 */
	@RequestMapping("/selectUserCallNum")
	@ResponseBody
	public String selectUserCallNum(HttpServletRequest req,
			@RequestParam(value="name",required=false) String name,
			@RequestParam(value="sort",required=false) String sort,
			@RequestParam(value="order",required=false) String order
			) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(name!=null && name!=""){
			map.put("name", name);
		}
		map.put("sort", sort);
		map.put("order", order);
		List<Map<String,Object>> list= sysUsersVoService.selectUserCallNum(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}

	
	/**
	 * 删除绑定
	 * @param map
	 * @return
	 */
	@RequestMapping("/deleteUserCallNum")
	@ResponseBody
	public String deleteUserCallNum(HttpServletRequest req,
			@RequestParam(value="userKy",required=false) Integer userKy
			) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userKy", userKy);
		sysUsersVoService.deleteUserCallNum(map);
		return "success";
	}
	
	/**
	 * 绑定
	 * @param map
	 * @return
	 */
	@RequestMapping("/addCallNum")
	@ResponseBody
	public String addCallNum(HttpServletRequest req,
			@RequestParam(value="userKy",required=false) Integer userKy,
			@RequestParam(value="num",required=false) String num,
			@RequestParam(value="phone",required=false) String phone
			) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userKy", userKy);
		map.put("num", num);
		map.put("phone", phone);
		List<Map<String, Object>> list=sysUsersVoService.selectCallNumByUserKy(map);
		if(list.size()<1){
			sysUsersVoService.addCallNum(map);
			return "success";
		}else{
			return "fail";
		}
	}
}
