package com.jsjf.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.EmailException;  
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import com.jsjf.model.system.Mail;  
  
/**  
 * 邮件发送工具实现类  
 * @author DELL
 *
 */
public class MailUtil {  
    protected static final Logger logger = Logger.getLogger(MailUtil.class); 
    public static String host = "smtp.exmail.qq.com";// 设置邮件服务器,如果不用163的,自己找找看相关的
    public static int smtpPort = 25;//端口号
    public static String sender = "system@jushengcaifu.com";//发件人
    public static String name = "system";//昵称
    public static String username = "system@jushengcaifu.com";// 登录账号,一般都是和邮箱名一样吧  
    public static String password = "Js8888";// 发件人邮箱的登录密码
 
    public static boolean send(Mail mail) {  
        // 发送email  
        HtmlEmail email = new HtmlEmail();  
        try {  
            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"  
            email.setHostName(host); 
            //设置端口
            email.setSmtpPort(smtpPort);
            // 字符编码集的设置  
            email.setCharset(Mail.ENCODEING);  
            // 收件人的邮箱  
            email.addTo(mail.getReceiver());  
            // 发送人的邮箱  
            email.setFrom(sender, name);  
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码  
            email.setAuthentication(username, password);  
            // 要发送的邮件主题  
            email.setSubject(mail.getSubject());  
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签  
            email.setMsg(mail.getMessage());  
            // 发送  
            email.send();  
            if (logger.isDebugEnabled()) {  
                logger.debug(sender + " 发送邮件到 " + mail.getReceiver());  
            }  
            return true;  
        } catch (EmailException e) {  
            e.printStackTrace();  
            logger.info(sender + " 发送邮件到 " + mail.getReceiver()  
                    + " 失败");  
            return false;  
        }  
    }   
    public static boolean sendMail(String message){
		//如果短信发送失败，发送邮件
        Mail mail = new Mail(); 
        mail.setReceiver("gerald@jushengcaifu.com"); // 接收人 
        mail.setSubject("sys短信发送失败！");  
        mail.setMessage(message);  
        MailUtil.send(mail); 
        mail.setReceiver("wei.feng@js-fax.com");
    	return send(mail);
    }
    
    public static void main(String[] args) {  
        Mail mail = new Mail(); 
        String properties;
		try {
			properties = PropertyUtil.getProperties("mailtos");
			mail.setReceiver(properties); // 接收人 
	        
	        mail.setSubject("系统异常");  
	        mail.setMessage("短信发送失败！");  
	        
	        boolean result = MailUtil.send(mail);  
	        System.out.println("mail==="+result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }  
    
    /**
     * 给多个人发送邮件
     */
    public static void sendBatchMail(String subject,String content){
    	try {
    		Mail mail = new Mail();
    		String[] mailtos = PropertyUtil.getProperties("mailtos").split(",");//将收件人写到配置文件中去
			for(int i=0;i<mailtos.length;i++){
				String mailto = mailtos[i];//收件人
				mail.setReceiver(mailto);
				mail.setSubject(subject);
				mail.setMessage(content);
				boolean sendResult = MailUtil.send(mail);
				System.out.println("mail=="+sendResult);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	/**
	 * 群发邮件
	 * @param content
	 * @param mailtos
	 * @throws InterruptedException
	 */
	public static void sendBatch(String content, String mailtos,String subject) throws InterruptedException{
		SimpleEmail email = new SimpleEmail();
		try {
			List<InternetAddress> list = new ArrayList();
			String[] ms = mailtos.split(",");
			for(int i=0;i<ms.length;i++ ){
				InternetAddress mailto = new InternetAddress(ms[i]);
				list.add(mailto);
			}
			email.setTo(list);
			email.setSubject(subject);
			email.setMsg(content);
			send(email);
		} catch (EmailException | AddressException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 简单邮件
	 * @param email
	 * @throws InterruptedException
	 */
	public static void send(SimpleEmail email) throws InterruptedException{
		email.setHostName(host);
		email.setAuthentication(username, password);
		email.setCharset("utf-8");
        try {  
        	email.setFrom(sender);
            Thread t = new Thread(email.send());
            t.start();
            System.out.println ( "邮件发送成功!" );
        } catch (EmailException e) {  
            e.printStackTrace();  
            System.out.println ( "邮件发送失败!" + e );
        }  finally{
        }
		
	}
	
}