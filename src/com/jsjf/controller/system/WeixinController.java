package com.jsjf.controller.system;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.http.protocol.HTTP;
import org.apache.http.message.BasicHeader;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.Utils;
import com.jsjf.service.system.impl.RedisClientTemplate;


public class WeixinController {

	private final String url="https://api.weixin.qq.com/cgi-bin";
	private final String appID="wxbed392326c3fa799";
	private final String appsecret="c913ad1d48a9e6de85acc3007771595a";
	
	@Autowired
	public RedisClientTemplate redisClientTemplate;
	/**
	 * 获取access_token接口2个小时变一次
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public String GetAccessToken() throws ClientProtocolException, IOException{
		String accessToken=null;
		accessToken = redisClientTemplate.get("WeixinAccessToken");		
		if(!StringUtils.isNotEmpty(accessToken)){
			String accessTokenUrl=url+"/token?grant_type=client_credential&appid="+appID+"&secret="+appsecret;
			HttpClient httpClient = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(accessTokenUrl);  
	        HttpResponse response = httpClient.execute(httpGet);
	        String httpEntityContent = getHttpEntityContent(response);
	        JSONObject jsStr = JSONObject.parseObject(httpEntityContent);
	        accessToken= jsStr.get("access_token").toString();
			redisClientTemplate.setex("WeixinAccessToken",7200, accessToken);	
			System.out.println(accessToken);
		}
		return accessToken;		
	}
	
	/**
	 * 获取用户列表
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public void GetUser() throws ClientProtocolException, IOException{
		String accessToken=null;
		accessToken = redisClientTemplate.get("WeixinAccessToken");		
		if(!StringUtils.isNotEmpty(accessToken)){
			String accessTokenUrl=url+"/user/get?access_token=Kmjv90xaII8pWjyap8Qpeo3NoX3a-WivwqMwK8pBYOIES2wd9IiN_c0KLtTfsixchrEVqtXQFaWfGzOl7Ss6ULKvB2WMDwHn-ldjyltaqIIM3JncS1nVyEDVaoMjBc-tJBZiAAAASQ";
			HttpClient httpClient = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(accessTokenUrl);  
	        HttpResponse response = httpClient.execute(httpGet);
	        String httpEntityContent = getHttpEntityContent(response);
	        JSONObject jsStr = JSONObject.parseObject(httpEntityContent);
	        System.out.println(jsStr);
		}
	}
	
	/**
	 * 群发消息推送
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public void send() throws ClientProtocolException, IOException{
		String accessToken=null;
		//accessToken = redisClientTemplate.get("WeixinAccessToken");		
		if(!StringUtils.isNotEmpty(accessToken)){
			String accessTokenUrl=url+"/message/mass/send?access_token=Kmjv90xaII8pWjyap8Qpeo3NoX3a-WivwqMwK8pBYOIES2wd9IiN_c0KLtTfsixchrEVqtXQFaWfGzOl7Ss6ULKvB2WMDwHn-ldjyltaqIIM3JncS1nVyEDVaoMjBc-tJBZiAAAASQ";
			HttpClient httpClient = new DefaultHttpClient();
			JSONObject json = new JSONObject();
			List<String> list=new ArrayList<>();
			list.add("oKzUG0zRVeDli7FiWlbZfl6N_jcI");
			list.add("oKzUG094TTPjQhBFbaIdPnWr0T1A");
 			json.put("touser", list);
 			json.put("msgtype", "text");
 			Map<String, Object> map=new HashMap<>();
 			map.put("content", "哈哈哈哈");
 			json.put("text", map);
 			
 			HttpPost post = new HttpPost(accessTokenUrl);
 			        
 		    post.setHeader("Content-Type", "application/json");
 		    post.addHeader("Authorization", "Basic YWRtaW46"); 			        
 			       
 		    StringEntity s = new StringEntity(json.toString(), "utf-8");
 		    s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
 		                     "application/json"));
 			post.setEntity(s);
 			
 			// 发送请求
 		    HttpResponse httpResponse = httpClient.execute(post);
 		    String httpEntityContent = getHttpEntityContent(httpResponse);
 		    System.out.println(httpEntityContent);  
		}
	}

	 /**
     * 获得响应HTTP实体内容
     * @param response
     * @return
     * @throws java.io.IOException
     * @throws java.io.UnsupportedEncodingException
     */
    private static String getHttpEntityContent(HttpResponse response) throws IOException, UnsupportedEncodingException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line + "\n");
                line = br.readLine();
            }
            return sb.toString();
        }
        return "";
    }
    
    private void test(BigDecimal amount){
    	List<Map<String, Object>> list=new ArrayList<>();//假设是一个从大到小的集合
    	BigDecimal loanAmount=null;
    	List userList= new ArrayList<>();
	    	for (int i = 0; i < list.size(); i++) {
				Map map=list.get(i);//得到当前数据
				BigDecimal amt=new BigDecimal(map.get("amount").toString());
				
				loanAmount=loanAmount.add(amt);
				
				if(loanAmount.compareTo(amount)== -1){//当前借款金额小于打包金额
					userList.add(map);
				}else if(loanAmount.compareTo(amount)== 0){//当前借款金额等于打包金额
					userList.add(map);
					break;
				}else{//大于
					loanAmount=loanAmount.subtract(amt);
				}	
			}
       }
}
