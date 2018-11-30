package com.jsjf.common;

import java.util.HashMap;
import java.util.Map;

public class ConfigUtil {

	//正式环境
//	private static final String domainName = "https://www.jushengcaifu.com";
//	private static final String IMG_FILE_URL = "/alidata";
	//测试环境
	private static final String domainName = "http://192.168.1.246";
	private static final String IMG_FILE_URL = "/ftp/home/www";

	public static final String ADMIN_LOGIN_USER = "adminLoginUser";// 后台登陆用户

	//加载数据字典
	public static Map<String,Map<Integer,String>> dictionaryMap = new HashMap<String,Map<Integer,String>>();
	
	// 文章存放路劲
	public static final String articleImgPath = "/upload/atricle/";
	// 广告存放路劲
	public static final String bannerImgPath = "/upload/banner/";
	//风控图片
	public static final String claimsPic = "/upload/claimsPic/";
	public static final String productPic = "/upload/productPic/";
	
	public static final String synPayProductUrl = "http://jsapi.iok.la:12004/pay-web/product/synProductInfo.do";
	public static final String synRefundUrl = "http://jsapi.iok.la:12004/pay-web/product/paymentConfirm.do";


	public static String getSynpayproducturl() {
		return synPayProductUrl;
	}

	public static String getSynrefundurl() {
		return synRefundUrl;
	}

	public static String getImgFileUrl() {
		return IMG_FILE_URL;
	}
	
	public static String getDomainname() {
		return domainName;
	}
}
