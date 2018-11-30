package test;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.SerializeUtil;
import com.jsjf.common.SmsSendUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.product.DrProductInvestDAO;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.service.activity.DrActivityParameterService;
import com.jsjf.service.member.DrMemberCarryService;
import com.jsjf.service.member.DrMemberCrushService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.product.JsNoviceContinueRecordService;
import com.jsjf.service.system.JsMessagePushService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jzh.FuiouConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"../spring.xml","../spring-mvc.xml","../spring-mybatis.xml"})
public class ProductTest1 {

	@Autowired
	private DrProductInfoService drProductInfoService;
	@Autowired
	private DrProductInvestService drProductInvestService;
	@Autowired
	public DrMemberCarryService drMemberCarryService;
	@Autowired
	public DrMemberCrushService drMemberCrushService;
	@Autowired
	public DrActivityParameterService drActivityParameterService;
	@Autowired
	public JsNoviceContinueRecordService jsNoviceContinueRecordService;
	@Autowired
	RedisClientTemplate redisClientTemplate;
	@Autowired
	SysMessageLogService sysMessageLogService;
	@Autowired
	DrMemberService drMemberService;
	@Autowired
	DrProductInvestDAO drProductInvestDAO;
	@Autowired
	private JsMessagePushService jsMessagePushService;
	
	
	@Test
	public void productFullfreezeToFreeze (){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("type", 53);
		param.put("pid", 3298);
		drProductInfoService.productFullfreezeToFreeze(param);
	}
	

	
	
//	@Test//凌晨执行
	public void productExperience() {
		System.out.println("体验标计息开始");
		
		//这段代码 时间过了就可以删除
		String d = "2017-05-07 23:23:23";//指定时间之后体验标计息走 存管回款
		boolean f = false;
		try {
			f = new Date().after(Utils.parse(d, "yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e2) {
			System.out.println("计息失败...........");
			return ;
		}
		
		List<DrProductInfo> infoList = drProductInfoService.getDrProductInfoExperience();
		if(!Utils.isEmptyList(infoList)){
			char[] ary2 = {'0','0','0','0'};
			int nums = 0;
			for(DrProductInfo pInfo:infoList){
				List<DrProductInvest> list = drProductInvestDAO.getDrProductInvestListByPid(pInfo.getId());
				if(!Utils.isEmptyList(list)){
					try {
						List<DrProductInvest> investList = new ArrayList<>();
						for(int i = 0 ; i < list.size() ; i++){
							investList.add(list.get(i));
							if(i != 0 && i%20 == 0){
								if(f){//存管
									drProductInfoService.updateProductToEnd3(pInfo,ary2, nums,investList);
								}else{//平台
									drProductInfoService.updateProductToEnd2(pInfo,ary2, nums,investList);
									
								}
								nums = i;
								investList.clear();
							}
							
						}
						if(!Utils.isEmptyList(investList)){
							if(f){//存管
								drProductInfoService.updateProductToEnd3(pInfo,ary2, nums,investList);
							}else{//平台
								drProductInfoService.updateProductToEnd2(pInfo,ary2, nums,investList);
								
							}
						}
					} catch (Exception e) {
						System.out.println("体验标==>计息任务执行失败,下一个产品开始编号为");
						try {
							SmsSendUtil.sendMsg("17317566419", "体验标==>计息任务执行失败");
						} catch (Exception e1) {
							System.out.println(e1);
						}
					}
				}
			}
		}
		System.out.println("体验标计息结束");
	}
	
//	@Test
	public void testUpdateProductToEnd(){//定时更新已募集完成的产品，并生成回款记录
		System.out.println("执行计息任务");
		List<DrProductInfo> list = drProductInfoService.selectRaiseSuccesProductInfo();
		char[] ary2 = {'0','0','0','0'};
		int nums = 0;//记录计息次数
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DrProductInfo drProductInfo = (DrProductInfo) iterator
					.next();
			try {
				int count = drProductInvestService.selectInvestCountByPid(drProductInfo.getId());
				if(drProductInfo.getProject_no() != null && !"".equals(drProductInfo.getProject_no())){ 
					//恒丰存管项目报备产品计息
					drProductInfoService.updateFuiouProductToEnd(drProductInfo, ary2, nums);
				}else{
					//其他情况产品计息
//					drProductInfoService.updateProductToEnd(drProductInfo, ary2, nums);
				}
				nums = nums + count;
				
			} catch (Exception e) {
				nums += drProductInvestService.selectInvestCountByPid(drProductInfo.getId());
				System.out.println(drProductInfo.getId()+","+drProductInfo.getFullName()+"==>计息任务执行失败,下一个产品开始编号为："+nums);
				e.printStackTrace();
				try {
					SmsSendUtil.sendMsg("15801868241,15821421008,17317566419", drProductInfo.getFullName()+"==>计息任务执行失败");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		System.out.println("计息任务完成");	
	}
	
//	@Test
	public void testSaveInvestRepay(){//查询要回款的项目
		
		System.out.println("执行回款任务开始");
		List<DrProductInfo> infoList = drProductInvestService.selectExpireProductInfo();
		for(int i = 0; i < infoList.size(); i++){
			DrProductInfo info = infoList.get(i);
			try {
				String d = "2017-05-15 23:23:23";//指定时间之后体验标回款走 存管回款
				boolean f = new Date().after(Utils.parse(d, "yyyy-MM-dd HH:mm:ss"));
				
				//注意:发布凌晨之前,体验标有问题
				if((info.getType() ==5 && f) || (info.getProject_no() != null && !"".equals(info.getProject_no()))){
					//恒丰存管项目报备产品回款
//					drProductInvestService.saveInvestRepayByFuiou(info);
				}else{
					//其他情况产品回款
					drProductInvestService.saveInvestRepay(info);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(info.getFullName()+"==>执行回款任务失败"+","+info.getId());
				try {
					SmsSendUtil.sendMsg("15801868241,15821421008,17317566419", info.getFullName()+"==>回款任务执行失败");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		System.out.println("执行回款任务结束");
	}
	
   	
	
}
