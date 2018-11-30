package com.jsjf.controller.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.model.product.DrProductInfoVo;
import com.jsjf.service.product.SynPayProductInfoService;
import com.reapal.utils.ReapalSubmit;

import net.sf.json.JSONArray;
@Component
public class SynPayProductTask {
	@Autowired 
	private SynPayProductInfoService synPayProductInfoService;
	
	private static Logger LOGGER = LoggerFactory.getLogger(SynPayProductTask.class);
	
//	@Scheduled(cron="0 0/3 * * * ?")//每半小时执行一次定时任务
	public void getSynProjectInfo(){
		BaseResult br = new BaseResult();
		//根据代收付收同步的债权查询出相对应的产品信息
		List<DrProductInfoVo> productList = synPayProductInfoService.getSynProjectInfo();
		if(!productList.isEmpty()){
			String url = ConfigUtil.getSynpayproducturl();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productList", productList);
			map.put("json", JSONArray.fromObject(productList).toString());
			LOGGER.info("请求的参数是："+JSONArray.fromObject(productList).toString());
			try {
				String resultPost = ReapalSubmit.bulidSubmitObj(map, url);
				//拿到resultPost进行解密
				boolean b  = (boolean) JSON.parseObject(resultPost).get("success");
				if(b){
					for (DrProductInfoVo drProductInfo : productList) {
						synPayProductInfoService.updateSynStatus(drProductInfo.getId());
					}
					LOGGER.info("同步成功");
				}else{
					String errorCode = (String) JSON.parseObject(resultPost).get("errorCode");
					LOGGER.error(errorCode,"系统错误");
					System.out.println("同步失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.info("9999");
				System.out.println("系统错误");
			}
	    }else{
	    	LOGGER.info("暂未获取到数据");
	    }
    }
}
