package test;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jsjf.service.member.JsMemberGradeService;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"../spring.xml","../spring-mvc.xml","../spring-mybatis.xml"})
public class Test {
	
	@Autowired JsMemberGradeService jsMemberGradeService;
	
	@org.junit.Test
	public void test123213() throws SQLException{
		
	
		Map<String,Object> m = new HashMap<>();
		m.put("type", 6);
		m.put("uid", 246028);
		m.put("investId", 333053);
		m.put("wealth", 0);
		m.put("name", "小胜30第二千一百二十期");
		
		System.out.println(JSONObject.fromObject(m).toString());
	}
	
	public static void main(String[] args) {
		
		String str = "138718,333019;42512,333068;245941,333897";
		
		Map<String,Object> m = new HashMap<>();
		
		m.put("wealth", 0);
		m.put("name", "点亮会员");
		m.put("type", 6);
		
		String [] stra = str.split(";");
		
		for(String strb:stra) {
			String [] strc = strb.split(","); 
			m.put("uid", Integer.valueOf(strc[0]));
			m.put("investId", Integer.valueOf(strc[1]));
			System.out.println(JSONObject.fromObject(m).toString());
		}
		
	}
}
