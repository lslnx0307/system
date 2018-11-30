package test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jsjf.common.SerializeUtil;
import com.jsjf.common.SmsSendUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.product.DrProductInvestDAO;
import com.jsjf.model.activity.ActivityFriend;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.service.activity.ActivityFriendService;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"../spring.xml","../spring-mvc.xml","../spring-mybatis.xml"})
public class ProductTest {

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
	@Autowired
	private ActivityFriendService activityFriendService;
	
//	@Test
	public void test123(){
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", 0);
		List<ActivityFriend> list = activityFriendService.selectActivityFriendByMap(map);
		
		for(ActivityFriend af:list){
			List<Integer> uidList =  activityFriendService.selectIsSend(af.getId());
			if(!Utils.isObjectEmpty(uidList)){
				for(Integer uid:uidList){
					try {
						drActivityParameterService.insertSendThreePresentToAccount(uid, af);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("----好友邀请三重礼返现任务,活动:"+af.getName()+"发放完成");
		}
		
	}
	
//	@Test
	public void test123213(){
		
		Integer [] uids = new Integer[]{109125,
				109126,
				109127
		};
		
		for(int i= 0; i<uids.length;i++){			
			Integer uid =uids[i];
			Map<String, Object> map2 = new HashMap<String,Object>();
			map2.put("type", 0);
			map2.put("uid", uid);
			redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(),SerializeUtil.serialize(map2));
		}
	}
	
//	@Test
//	public String taskPush() {
//		try {
//			jsMessagePushService.taskPush();
//			return "true";
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "false";
//		}
//	}
//	@Test
	public void customerLossPrevention(){
		System.out.println("测试 营销登录短信开始");
		drMemberService.customerLossPrevention();
		System.out.println("测试 营销登录短信结束");
	}
	
//	@Test
	public void allotCustomer(){
		drMemberService.allotCustomer();
	}
	
	
	
	/**
	 * 失败到到正常的里面去
	 */
//	@Test
	public void testReidsbak(){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer uid = null;
		Integer type = null;
		Integer deadline = null;
		BigDecimal amount = BigDecimal.ZERO;
		byte[] b = null;
		boolean flag = true;
		while(flag){
			b = redisClientTemplate.rpop("regAndVerifySendRedUidList_bak".getBytes());
			
			if(SerializeUtil.unserialize(b) instanceof byte[]){
				if(SerializeUtil.unserialize((byte[])SerializeUtil.unserialize(b)) instanceof Map){
					byte[] by = (byte[])SerializeUtil.unserialize(b);
					
					param = (Map<String,Object>)SerializeUtil.unserialize(by);
					uid = Integer.parseInt(param.get("uid").toString());
					type = Integer.parseInt(param.get("type").toString());
					
					redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), by);
				}else if(SerializeUtil.unserialize((byte[])SerializeUtil.unserialize(b)) instanceof byte[]){
					if(SerializeUtil.unserialize((byte[])SerializeUtil.unserialize((byte[])SerializeUtil.unserialize(b))) instanceof Map){
						byte[] by = (byte[])SerializeUtil.unserialize((byte[])SerializeUtil.unserialize(b));
						
						param = (Map<String,Object>)SerializeUtil.unserialize(by);
						uid = Integer.parseInt(param.get("uid").toString());
						type = Integer.parseInt(param.get("type").toString());
						
						redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), by);
					}
				}
			}else if(SerializeUtil.unserialize(b) instanceof Map){
				byte[] by = b;
				
				param = (Map<String,Object>)SerializeUtil.unserialize(by);
				uid = Integer.parseInt(param.get("uid").toString());
				type = Integer.parseInt(param.get("type").toString());
				
				redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), by);
			}
			if(Utils.isObjectEmpty(b)){
				flag= false;
			}else{
				System.out.println(uid+","+type);
			}
				
		}
			
	}
	/**
	 * 先备份，并查看
	 */
	@Test
	public void testRedisbak1(){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer uid = null;
		Integer type = null;
		Integer deadline = null;
		BigDecimal amount = BigDecimal.ZERO;
		List<byte[]> list = redisClientTemplate.lrange("regAndVerifySendRedUidList_bak".getBytes(), 0, -1);
		
		
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
						uid = Integer.parseInt(param.get("uid").toString());
						type = Integer.parseInt(param.get("type").toString());
						
						redisClientTemplate.lpush("regAndVerifySendRedUidList_bak1".getBytes(), by);
					}
				}
			}else if(SerializeUtil.unserialize(b) instanceof Map){
				byte[] by = b;
				
				param = (Map<String,Object>)SerializeUtil.unserialize(by);
				uid = Integer.parseInt(param.get("uid").toString());
				type = Integer.parseInt(param.get("type").toString());
				
				redisClientTemplate.lpush("regAndVerifySendRedUidList_bak1".getBytes(), by);
			}
				System.out.println(uid+","+type);
		}
	}
	
	
//	@Test
	public void SMSMarketing(){//营销短信
		
		//注册第2天 -未绑卡
		drMemberService.SMSMarketNoTCertified("noTCertified");
			
		//注册第3天-未投资type=2 and if(atid or prizeId,0,1)
		drProductInvestService.marketSMSNotInvest(3,"reg3DayNotInvest",false);
		
		//注册第7天-未投资
		drProductInvestService.marketSMSNotInvest(7,"reg7DayNotInvest",false);
		
		//注册第8天-未投资
		drProductInvestService.marketSMSNotInvest(8,"reg8DayNotInvest",true);
		
		//首投后第5天-首投用户，投资后第5天-
		//1%加息券
		drActivityParameterService.marketFirstInvestLaterdays(5, "firstInvest5Day","首投奖励-加息券",2);
	
		try {
    		drActivityParameterService.insertCpsFavourables();
		} catch (Exception e) {
			System.out.println("促复投红包发放失败");
		}
	
		
	}
	
	
	
	/**
	 * 定时短信应急发送
	 */
//	@Test
	public void sendSmsHandle(){
		sysMessageLogService.sendSmsHandle();
	}
	
//	@Test//凌晨执行
	public void productExperience() {
		System.out.println("体验标计息开始");
		List<DrProductInfo> infoList = drProductInfoService.getDrProductInfoExperience();
		if(!Utils.isEmptyList(infoList)){
			char[] ary2 = {'0','0','0','0'};
			int nums = 0;
			for(DrProductInfo pInfo:infoList){
				List<DrProductInvest> list = drProductInvestDAO.getDrProductInvestListByPid(pInfo.getId());
				if(Utils.isObjectNotEmpty(list)){
					try {
						List<DrProductInvest> investList = new ArrayList<>();
						for(int i = 0 ; i < list.size() ; i++){
							investList.add(list.get(i));
							if(i != 0 && i%20 == 0){
								drProductInfoService.updateProductToEnd2(pInfo,ary2, nums,investList);
								nums = i;
								investList.clear();
							}
							
						}
						if(Utils.isObjectNotEmpty(investList)){
							drProductInfoService.updateProductToEnd2(pInfo,ary2, nums,investList);
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
			System.out.println("########################nums = " + nums);
			try {
				Integer  count= drProductInvestService.selectInvestCountByPid(drProductInfo.getId());
				drProductInfoService.updateProductToEnd(drProductInfo, ary2, nums);
				nums = nums + count;
			} catch (Exception e) {
				e.printStackTrace();
				nums += drProductInvestService.selectInvestCountByPid(drProductInfo.getId());
				System.out.println(drProductInfo.getFullName()+"==>计息任务执行失败,下一个产品开始编号为："+nums);
				try {
					SmsSendUtil.sendMsg("15801868241,15821421008,17317566419", drProductInfo.getFullName()+"==>计息任务执行失败");
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
			}
		}
		System.out.println("计息任务完成");
	}
	
//	@Test
	public void testSaveInvestRepay(){//查询要回款的项目
		List<DrProductInfo> infoList = drProductInvestService.selectExpireProductInfo();
		for(int i = 0; i < infoList.size(); i++){
			DrProductInfo info = infoList.get(i);
			System.out.println(info.getFullName()+"==>执行回款任务开始");
			try {
				drProductInvestService.saveInvestRepay(info);
			}catch (Exception e) {
				System.out.println(info.getFullName()+"==>回款任务执行失败");
				e.printStackTrace();
			}
		}
	}
	
//	@Test
	public void testredisSet(){
		System.out.println("取数据");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", 16);
		map.put("type", 0);
		List<byte[]> set =  redisClientTemplate.lrange("regAndVerifySendRedUidList_bak".getBytes(),0,-1);
		Map<String,Object> maps=null;
		for(byte[] b:set){
			maps = (Map<String, Object>) SerializeUtil.unserialize(b);
			if(maps == null)continue;
			System.out.println(maps.get("uid")+"_"+maps.get("type"));
		}
		
		
	}
//	@Test
	public void testredis(){
		System.out.println("插入数据");
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("uid", 38980);
//		map.put("type", 0);
//		long l1 = redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), SerializeUtil.serialize(map));
//		map.put("uid", 38980);
//		map.put("type", 3);
//		long l2 =redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), SerializeUtil.serialize(map));
//		
//		System.out.println(l1+"-----------------"+l2);
		
		byte[] s = redisClientTemplate.rpop("regAndVerifySendRedUidList_bak".getBytes());
		
		
		Map<String,Object> map = (Map<String, Object>) SerializeUtil.unserialize(s);
		System.out.println(s);
	}
	
//	@Test
	public void test1(){
		//注冊发送红包体验金
		try {
//			drActivityParameterService.valentineActivitys("regAndVerifySendRedUidList");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testInsertFriendMemberActivityAmount() {//插入好友邀请返现到活动账户
    	try {
    		drActivityParameterService.insertFriendMemberActivityAmount();
		} catch (Exception e) {
			e.printStackTrace();
		}
   	}
	
//	@Test
	public void newHandContinueInvest(){//新手标续投
		System.out.println("新手标续投开始");
		jsNoviceContinueRecordService.insertInvestNewHandContinue();
		System.out.println("新手标续投结束");
	}
	

//	@Test
	public void insertCpsFavourable(){//促复投红包发放
		try {
    		drActivityParameterService.insertCpsFavourables();
    		System.out.println("促复投红包发放");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("促复投红包发放失败");
		}
	}
	
	/**
	 * 债券匹配
	 */
//	@Test
	public void testInsertInvestTransfer(){//债券匹配
		drProductInvestService.insertInvestTransfer();
	}
	
//	@Test
   	public void queryPaymentResult() {//查询代付
    	try {
    		drMemberCarryService.updatePaymentResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
   	}
//   	@Test
   	public void smsSend(){
		try {
			int result = SmsSendUtil.sendMsg("15801868241", "恭喜您，已经通过认证！");
			System.out.println("result:" + result);
//			sendMsgByXiAo("15801868241", "测试及时，验证码：2383930");
		} catch (Exception e) {
			e.printStackTrace();
		}
   	}
	
//	@Test
   	public void queryPayResult() {//查询支付结果
    	try {
    		drMemberCrushService.updatePayResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
   	}
   	
//   	@Test
//   	public void testRRl(){
//   		Map<String, Object> resultMap = new HashMap<String, Object>();
//		 try {
//			String ip = InetAddress.getLocalHost().getHostAddress();
//			if("139.196.175.171".equals(ip)){
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("channel", "rrl");
//				map.put("endDate", Utils.format(new Date(), "yyyy-MM-dd 21:00:00"));
//				map.put("startDate", Utils.format(Utils.getDayNumOfAppointDate(new Date(), 1), "yyyy-MM-dd 21:00:00"));
//				List<Map<String, Object>> list = drProductInvestService.QueryChannelInvestList(map);
//				resultMap.put("Cust_id", RrlBase.rrl_cust_id);
//				resultMap.put("Sign_type", RrlBase.rrl_type);
//				resultMap.put("Sign", SecurityUtils.MD5(RrlBase.rrl_username+RrlBase.rrl_password+RrlBase.rrl_cust_id+RrlBase.rrl_key));
//				resultMap.put("Data", list);
//				System.out.println(JSON.toJSONString(list));
//				RrlBase.getInstance().pushInvestData(list);
//			}
			
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("����ʧ��");
//		}
//   		
//   	}
   	
	
}
