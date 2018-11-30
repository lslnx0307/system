package test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.model.claims.DrClaimsCustomer;
import com.jsjf.model.system.SysUsersVo;
import com.jzh.FuiouConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"../spring.xml","../spring-mvc.xml","../spring-mybatis.xml"})
public class withdrawalsTest {
	
	public final static String mchnt_user = "0003310F0352406";
	public final static String mchnt_m_user = "2062060000216700";
	
	
	
	
	
	
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
	
	//@Test//提现
	public void withdrawals(){		
		String str = FuiouConfig.withdrawals("15601820519",""+System.currentTimeMillis(),"100");
		System.out.println("html-json:"+str);
	}
	
	//@Test//转账
	public void transferBmu(){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("mchnt_txn_ssn", ""+System.currentTimeMillis());
		map.put("out_cust_no","17721050742");
		map.put("amt","200");
		map.put("rem", "平台提现手续费");
		map.put("in_cust_no", FuiouConfig.LOGIN_ID);
		map.put("contract_no", "");
		BaseResult br=FuiouConfig.transferBmu(map);
		System.out.println("html-json:"+br);
	}
	
	
	//@Test//用户信息查询
	public void queryUserInfs(){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userId","15092248939");
		BaseResult br=FuiouConfig.queryUserInfs(map);
		System.out.println("html-json:"+br);
	}
	
	//@Test//法人开户注册 
	public void artifReg(){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("cust_nm","上海景泰融资租赁有限公司");
		map.put("artif_nm","章承宇"); 
		map.put("certif_id","341281199111259031");
		map.put("mobile_no","15092248939");
		map.put("email","");
		map.put("city_id","1000");
		map.put("parent_bank_id","0308");
		map.put("bank_nm","");
		map.put("capAcntNo","15092248939");
		map.put("password","");
		map.put("lpassword","");
		map.put("rem","");
		map.put("mchnt_txn_ssn",""+System.currentTimeMillis());
		
		BaseResult br=FuiouConfig.artifReg(map);
		System.out.println("html-json:"+br);
	}
	
	
	//@Test//划拨
	public void transferBu(){
		Map<String, String> map=new HashMap<String, String>();
		map.put("contract_no", "000038064421");
		map.put("out_cust_no", "13959772003");
		map.put("in_cust_no", "15890170923");
		map.put("amt", "100");
		map.put("rem", "");
		map.put("mchnt_txn_ssn", ""+System.currentTimeMillis());
		BaseResult br=FuiouConfig.transferBu(map);
		System.out.println("html-json:"+br);
	}
	
	@Test//划拨
	public void wtwithdraw(){
		Map<String, String> map=new HashMap<String, String>();
		map.put("login_id", "15092248939");
		map.put("amt", "100");
		map.put("rem", "");
		BaseResult br=FuiouConfig.wtwithdraw(map);
		System.out.println("html-json:"+br);
	}
	
	
	
}
