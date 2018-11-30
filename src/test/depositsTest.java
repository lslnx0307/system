package test;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jsjf.common.SerializeUtil;
import com.jsjf.controller.system.SysDepositsController;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jzh.FuiouConfig;
import com.jzh.data.WebLoginData;
import com.jzh.service.JZHService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"../spring.xml","../spring-mvc.xml","../spring-mybatis.xml"})
public class depositsTest {

	@Autowired
	private RedisClientTemplate redis;
	@Autowired DrMemberService drMemberService;
	@Autowired DrProductInvestService drProductInvestService;
	@Autowired RedisClientTemplate redisClientTemplate;
	@Autowired SysDepositsController sysDepositsController;
	
	/**
	 * 通过json 设置redis
	 */
	@Test
	public void testredis(){
		String json = "{\"type\":0,\"uid\":190458}";
		
		Map<String,Object> param = new HashMap<String, Object>();
		
		try {
			param.put("type", 52);
			param.put("uid", 212548);
			param.put("investId", 231805);						
			param.put("project_no", "jzh");
			
			drProductInvestService.cashbackDistributeByFuiouMarketingAccount(param);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 先备份，并查看
	 */
//	@Test
	public void testRedisbak1(){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer uid = null;
		Integer type = null;
		Integer deadline = null;
		BigDecimal amount = BigDecimal.ZERO;
		List<byte[]> list = redisClientTemplate.lrange("regAndVerifySendRedUidList_bak1".getBytes(), 0, -1);
		
		
		for(byte[] b : list){
			if(SerializeUtil.unserialize(b) instanceof byte[]){
				if(SerializeUtil.unserialize((byte[])SerializeUtil.unserialize(b)) instanceof Map){
					byte[] by = (byte[])SerializeUtil.unserialize(b);
					
					param = (Map<String,Object>)SerializeUtil.unserialize(by);
					uid = Integer.parseInt(param.get("uid").toString());
					type = Integer.parseInt(param.get("type").toString());
					
					redisClientTemplate.lpush("regAndVerifySendRedUidList_bak1".getBytes(), by);
				}else if(SerializeUtil.unserialize((byte[])SerializeUtil.unserialize(b)) instanceof byte[]){
					if(SerializeUtil.unserialize((byte[])SerializeUtil.unserialize((byte[])SerializeUtil.unserialize(b))) instanceof Map){
						byte[] by = (byte[])SerializeUtil.unserialize((byte[])SerializeUtil.unserialize(b));
						
						param = (Map<String,Object>)SerializeUtil.unserialize(by);
						uid =  param.get("uid") !=null ? (Integer)param.get("uid") : null;
						type = Integer.parseInt(param.get("type").toString());
						
						redisClientTemplate.lpush("regAndVerifySendRedUidList_bak1".getBytes(), by);
					}
				}
				System.out.println("-----------");
				System.out.println(JSONObject.fromObject(param));
			}else if(SerializeUtil.unserialize(b) instanceof Map){
				byte[] by = b;
				
				param = (Map<String,Object>)SerializeUtil.unserialize(by);
//				uid = Integer.parseInt(param.get("uid").toString());
//				type = Integer.parseInt(param.get("type").toString());
				
				redisClientTemplate.lpush("regAndVerifySendRedUidList_bak1".getBytes(), by);
				
				System.out.println("-----------");
				System.out.println(JSONObject.fromObject(param));
				
			}
//				System.out.println(uid+","+type);
		}
	}
	
	
	
	
//	@Test
	public void tes(){
		
		WebLoginData data = new WebLoginData();
		
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(System.currentTimeMillis()+"");
		data.setCust_no("15618538752");
		String str = JZHService.encaJSONstring(data);
		
		System.out.println("html-json:"+str);
		
		
		
	}
	
//	@org.junit.Test
	public void test123(){
		
		Map<String,Object> map  = new HashMap<>();
		map.put("uid", 16);
		map.put("investId", 124882);
		try {
			drProductInvestService.cashbackDistributeByFuiouMarketingAccount(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
//	@Test
	public void test(){
		Map<String,Object> param = new HashMap<String, Object>();
		try {
			param.put("type", 6);
			param.put("fid", 982907);
			param.put("uid", 124369);
			param.put("investId", 124732);
			param.put("pid", 2499);
			param.put("balanceProfit", new BigDecimal("16"));
			param.put("fullName", "小胜30第六百四十期");
			param.put("investAmount", new BigDecimal("10000"));				
			param.put("project_no", "0032610000000021031");			
			
			
			drProductInvestService.cashbackDistributeByFuiouMarketingAccount(param);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	public static void main(String[] args) {

		  String name="中文";
		  //URL编码
		  String nameStr;
		try {
			nameStr = new String(java.net.URLEncoder.encode("","utf-8").getBytes());
			System.out.println(nameStr);
			
//			String cnStr = "中文";
//			String cnStr1 = "";
//			
//			cnStr1 = new String(java.net.URLEncoder.encode(cnStr, "UTF-8").getBytes(), "ISO-8859-1");
//			System.out.println(cnStr1);
//			//URL解码
//			System.out.println(java.net.URLDecoder.decode(new String(cnStr1.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	/**
	 * 网银充值
	 * @param fuiou_acnt
	 * @param amt
	 * @param user_id
	 * @return
	 */
//	@Test
	public  void onlineBankingRechargeData(){
		
	}
	
//	@Test//充值
	public  void testRecharge(){
		
	}
//	@Test//开户
	public  void testOPenAccount(){
		
		
	}
	
	
}
