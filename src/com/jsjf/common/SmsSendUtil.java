package com.jsjf.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jsjf.model.member.DrCarryParam;
import com.jsjf.service.member.DrCarryParamService;
import com.mysql.fabric.xmlrpc.base.Data;

@Component
public class SmsSendUtil {
	private static Logger log = Logger.getLogger(SmsSendUtil.class);
	//正式环境配置，后台访问地址www.10690221.com
	private static final String url = "http://210.5.158.31/hy/";
	
	private static final String companyCode = "";//企业代码

	private static final String username = "";//短信账号
	
	private static final String pwd = "";//密码
	@Autowired
	private DrCarryParamService drCarryParamService;
	private static SmsSendUtil smssUtil;
	public SmsSendUtil(){
		
	}
	public void setDrCarryParamService(DrCarryParamService drCarryParamService) {  
        this.drCarryParamService = drCarryParamService;  
    }
	//类似声明了，当你加载一个类的构造函数之后执行的代码块，也就是在加载了构造函数之后，就将service复制给一个静态的service
	@PostConstruct  
    public void init() {  
		smssUtil = this;  
		System.out.println("------" + smssUtil);
		smssUtil.drCarryParamService = this.drCarryParamService;  
    }  
	
	/**
	 * 即时发送
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @return 发送成功返回99，失败返回-1
	 * @throws Exception
	 */
	public static int sendMsg(String mobile,String content) throws Exception{
		int result = 0;
		DrCarryParam drCarryParam = smssUtil.drCarryParamService.getDrCarryParam();
		Integer smsChannel = 1;
		if(null != drCarryParam && drCarryParam.getSmsChanel() != null){
			smsChannel = drCarryParam.getSmsChanel();
		}
		final String mobileStr = mobile;
		final String contentStr = content;
		if(smsChannel == 1){
			//希奥短信接口
			result = sendMsgByXiAo(mobile, content);
			if(result < 0){
				result = sendMsgByQxt(mobile, content);//切换企信通短信通道
				//发送邮件
				ExecutorService cachedThreadPool = Executors.newFixedThreadPool(10);
				cachedThreadPool.execute(new Runnable(){
					@Override
					public void run(){
				        MailUtil.sendMail("希奥短信发送失败！mobile："+mobileStr+" 内容："+contentStr); 
					}
				});
			}
		}else{
			//企信通短信接口
			result = sendMsgByQxt(mobile, content);
			if(result < 0){
				result = sendMsgByXiAo(mobile, content);//切换希奥短信通道
				//发送邮件
				ExecutorService cachedThreadPool = Executors.newFixedThreadPool(10);
				cachedThreadPool.execute(new Runnable(){
					@Override
					public void run(){
				        MailUtil.sendMail("企信通短信发送失败！mobile："+mobileStr+" 内容："+contentStr); 
					}
				});
			}
		}
		return result;
	}
	/**
	 * 即时发送-希奥
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @return 发送成功返回99，失败返回-99
	 * @throws Exception
	 */
	public static int sendMsgByXiAo(String mobile,String content) throws Exception{
		HttpClient httpClient = new HttpClient();
        content=java.net.URLEncoder.encode(content, "gbk");
        String auth = SecurityUtils.MD5(companyCode+pwd);
		PostMethod postMethod = new PostMethod(url);
		
//		NameValuePair[] data = {
//					new NameValuePair("uid", username),
//					new NameValuePair("auth", auth),
//					new NameValuePair("mobile", mobile),
//					new NameValuePair("expid", "0"),
//					new NameValuePair("msg",content ) };
//		postMethod.setRequestBody(data);
//		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);  
//		httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
//		int statusCode = httpClient.executeMethod(postMethod);
//		
//		if (statusCode == HttpStatus.SC_OK) {
//			BufferedReader br = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
//			StringBuffer stringBuffer = new StringBuffer();
//			String str= "";
//			while((str = br.readLine()) != null){
//				stringBuffer .append(str );
//			}
//			String[] array = stringBuffer.toString().split(",");
//			return Integer.parseInt(array[0])==0?99:Integer.parseInt(array[0]);
//		}
//		return -99;
		return 99;
	}
	
	/**
	 * 定时短信发送
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @param time 短信发送时间
	 * @return 发送成功返回88，失败返回-88
	 * @throws Exception
	 */
	public static int sendTimeMsg(String mobile,String content,String time) throws Exception{
		HttpClient httpClient = new HttpClient();
        content=java.net.URLEncoder.encode(content, "gbk");
        String auth = SecurityUtils.MD5(companyCode+pwd);
		PostMethod postMethod = new PostMethod(url);
		
//		NameValuePair[] data = {
//					new NameValuePair("uid", username),
//					new NameValuePair("auth", auth),
//					new NameValuePair("mobile", mobile),
//					new NameValuePair("expid", "0"),
//					new NameValuePair("msg",content ),
//					new NameValuePair("time", time ) };
//		postMethod.setRequestBody(data);
//		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);  
//		httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
//		int statusCode = httpClient.executeMethod(postMethod);
//		
//		if (statusCode == HttpStatus.SC_OK) {
//			BufferedReader br = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
//			StringBuffer stringBuffer = new StringBuffer();
//			String str= "";
//			while((str = br.readLine()) != null){
//				stringBuffer .append(str );
//			}
//			String[] array = stringBuffer.toString().split(",");
//			return Integer.parseInt(array[0])==0?88:Integer.parseInt(array[0]);
//		}
//		return -88;
		return 88;
	}
	/**
	 * 即时发送-企信通
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @return 发送成功返回77，失败返回-77
	 * @throws Exception
	 */
	public static int sendMsgByQxt(String mobile,String content) throws Exception{
		String username="";
        String password = SecurityUtils.MD5("");
		String msg=URLEncoder.encode(content,"GBK");
		String post="http://58.83.147.85:8080/qxt/smssenderv2?";
		String url="";
		url=post+"user="+username+"&password="+password+"&tele="+mobile+"&msg="+msg+"&extno=4455";
//		System.clearProperty("java.classpath");
//		String output = null;
//		try {
//			output = Http.httpGet(url,"GBK");
//			if(null != output){
//				String[]result = output.split(":");
//				log.info("qxt msg return："+output);
//				if(result.length > 0){
//					if("ok".equals(result[0])){
//						return 77;
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return -77;
		return 77;
	}
	/**
	 * 创蓝营销短信
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @return 发送成功返回666，失败返回-666
	 * @throws Exception
	 */
	public static String sendMsgByMarketing(String mobile,String content) throws Exception{
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod("http://zapi.253.com/msg/HttpBatchSendSM");
		
//		try {
//			NameValuePair[] data = {
//					new NameValuePair("account", ""),
//					new NameValuePair("pswd", ""),
//					new NameValuePair("mobile", mobile),
//					new NameValuePair("needstatus", String.valueOf(false)),
//					new NameValuePair("msg",content ),
//					new NameValuePair("extno", "5415" ) };
//			postMethod.setRequestBody(data);
//			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);  
//			httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
//			int result = httpClient.executeMethod(postMethod);
//			if (result == HttpStatus.SC_OK) {
//				InputStream in = postMethod.getResponseBodyAsStream();
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				byte[] buffer = new byte[1024];
//				int len = 0;
//				while ((len = in.read(buffer)) != -1) {
//					baos.write(buffer, 0, len);
//				}
//				return URLDecoder.decode(baos.toString(), "UTF-8");
//			} else {
//				throw new Exception("HTTP ERROR Status: " + postMethod.getStatusCode() + ":" + postMethod.getStatusText());
//			}
//		} finally {
//			postMethod.releaseConnection();
//		}
		return null;
	}
	
	/**
	 * 创蓝营销短信
	 * 营销短信批量发送
	 */
	public static void  batchSMSMarketing(String[] arrayMobile,String content) throws Exception{
		StringBuffer mobile = new StringBuffer();
		for(int i=0;i<arrayMobile.length;i++){
			mobile.append(arrayMobile[i]).append(",");
			if(i!=0 && i%100==0){
				SmsSendUtil.sendMsgByMarketing(mobile.deleteCharAt(mobile.length()-1).toString(), content);
				mobile = new StringBuffer();
			}
		}
		if(mobile.length()>0){
			SmsSendUtil.sendMsgByMarketing(mobile.deleteCharAt(mobile.length()-1).toString(), content);
		}
		
	}
	
	public static void main(String[] args){
//		System.out.println(Utils.parseDate(Utils.format(new Date(), "yyyy-MM-dd 10:00:00"),"yyyy-MM-dd HH:mm:ss"));
//		sendMsg("15801868241", "测试及时");
		try {
			int result = sendMsg("15801868241", "短信");
			System.out.println("result:" + result);
//			sendMsgByXiAo("15801868241", "测试及时，验证码：2383930");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
