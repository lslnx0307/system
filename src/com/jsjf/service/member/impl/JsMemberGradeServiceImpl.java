package com.jsjf.service.member.impl;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.Utils;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.member.DrMemberMsgDAO;
import com.jsjf.dao.member.JsMemberGradeDAO;
import com.jsjf.dao.member.JsMemberTaskDAO;
import com.jsjf.dao.member.JsTaskDetailsDAO;
import com.jsjf.dao.member.JsWealthHistoryDAO;
import com.jsjf.dao.product.DrProductInfoDAO;
import com.jsjf.dao.product.DrProductInvestDAO;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.model.member.JsMemberGrade;
import com.jsjf.model.member.JsMemberTask;
import com.jsjf.model.member.JsTaskDetails;
import com.jsjf.model.member.JsWealthHistory;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.service.member.JsMemberGradeService;


@Service
@Transactional
public class JsMemberGradeServiceImpl implements JsMemberGradeService{
	@Autowired JsMemberGradeDAO jsMemberGradeDAO;
	@Autowired JsTaskDetailsDAO jsTaskDetailsDAO;
	@Autowired JsWealthHistoryDAO jsWealthHistoryDAO;
	@Autowired JsMemberTaskDAO jsMemberTaskDAO;
	@Autowired DrMemberDAO drMembedDAO;
	@Autowired DrMemberMsgDAO drMembedMsgDAO;
	@Autowired DrProductInvestDAO drProductInvestDAO;
	@Autowired DrProductInfoDAO drProductInfoDAO;

	@Override
	public List<Map<String, Object>> getMemberGrade() {
		return jsMemberGradeDAO.getMemberGrade();
	}
	public void incrMemberWealth(Map<String, Object> param) throws Exception {
		Integer  uid = Integer.valueOf(param.get("uid").toString());
		Integer investId = Utils.isObjectEmpty(param.get("investId"))?null:Integer.valueOf(param.get("investId").toString());
		Integer wealth = Utils.isObjectEmpty(param.get("wealth"))?null:Integer.valueOf(param.get("wealth").toString());
		String name = Utils.isObjectEmpty(param.get("name"))?null:param.get("name").toString();
		String taskName = Utils.isObjectEmpty(param.get("taskName"))?null:param.get("taskName").toString();
		
		DrMember m = new DrMember();
		m.setUid(uid);
		m.setIsBlack(0);//是否为黑名单 0否1是
		drMembedDAO.updateDrMemberByUid(m);//锁定事务防止脏读
		DrMember member = drMembedDAO.selectByPrimaryKey(uid);
		
		if (Utils.isObjectNotEmpty(member)) {
			JsWealthHistory wistory = null;
			Integer type = 0;
			Integer tdid = null;
			
			if(!Utils.isBlank(investId) && !Utils.isBlank(wealth)){//投资加财富值
				tdid = investId;
				type = 1;
				
				if(member.getIsMember()!=1){
					Integer newAmount =  jsMemberGradeDAO.selectLightQualification(uid);//查询该会员财富值
					if(newAmount !=null && newAmount>=0){
						jsMemberGradeDAO.updateLightUpTheMember(uid);//点亮
						//站内信  亲爱的会员，恭喜您的会员已经点亮啦，快去看看您的权益吧~
						DrMemberMsg dmsg = null;
						Date now = new Date();
						dmsg = new DrMemberMsg(member.getUid(), 0, 1, "会员点亮", now, 0, 0,
								"亲爱的会员，恭喜您的会员已经点亮啦，"+" <font color='#c5342a'><a href='jsmp://page=45?' style='color:#FF5657'>快去看看新权益</a></font>吧~ ");
						drMembedMsgDAO.insertDrMemberMsg(dmsg);
					}
				}	
				
			}else if(!Utils.strIsNull(taskName)){//做任务加财富值
				
				Map<String,Object> map = finishTask(param);
				
				if (Utils.isObjectNotEmpty(map) && map.containsKey("flag") && (boolean) map.get("flag")) {
					tdid = Integer.valueOf(map.get("taskId").toString());				
					wealth = Integer.valueOf(map.get("wealth").toString());
					uid = Integer.valueOf(map.get("uid").toString());
					name = taskName;
					
				}else{
					return;
				}
				
			}else{
				return;
			}
			
			wistory = jsWealthHistoryDAO.selectObjectByTdid(tdid,type);
			
			if (Utils.isObjectEmpty(wistory)) {//财富值没有增加过
				Date now = new Date();
				if(member.getUid() != uid){
					member = drMembedDAO.selectByPrimaryKey(uid);
				}
				//增加财富值
				member.setWealth(member.getWealth()+wealth); 
				
				//财富值历史
				wistory = new JsWealthHistory(name, wealth, now, member.getUid(), tdid,type);
				jsWealthHistoryDAO.insert(wistory);
				
				Integer  sum = jsWealthHistoryDAO.selectWealthSum(member.getUid());
				
				if (sum != null && member.getWealth() != null && sum.intValue() > member.getWealth().intValue()) {
					member.setWealth(sum);
				}
				
				//是否可升级
				
				JsMemberGrade grade = jsMemberGradeDAO.selectLevelByWealth(member.getWealth());
				
				DrMemberMsg dmsg = null;
				
				if (Utils.isObjectNotEmpty(grade) && ( member.getMgid()==null || grade.getGrade().intValue() > member.getMgid())) {
					
					JsMemberGrade mGrade = jsMemberGradeDAO.getMemberGradeById(member.getMgid());
					
					member.setMgid(grade.getId());//升级
					
					//站内信    亲爱的会员，恭喜您的等级已经从V2升级到V3啦，快去看看新权益吧~
					dmsg = new DrMemberMsg(member.getUid(), 0, 1, "会员升级", now, 0, 0,
							"亲爱的会员，恭喜您的等级已经从"+(Utils.isObjectEmpty(mGrade)?"普通用户":mGrade.getLevel())+"升级到"+grade.getLevel()+"啦，<font color='#c5342a'><a href='jsmp://page=45?' style='color:#FF5657'>快去看看新权益</a></font>吧~");
				}
				
				
				
				
				m.setMgid(member.getMgid());
				m.setWealth(member.getWealth());
								
				drMembedDAO.updateDrMemberByUid(m);				
				
				if (dmsg != null) {
					drMembedMsgDAO.insertDrMemberMsg(dmsg);
				}
				member = null;
				m = null;
				dmsg = null;
			}
		}
				
	}
	
	
	/**
	 * 增加财富值, 投资增加财富值,通过产品id
	 * 任务增加财富值通过 任务名字
	 * @param param
	 * @throws SQLException
	 */
	@Override
	public void incrWealth(Map<String, Object> param) throws Exception {
		
		boolean isInvestIncrWeaLth = Utils.isObjectEmpty(param.get("isInvestIncrWeaLth"))?false:(boolean)param.get("isInvestIncrWeaLth");
		
		if (isInvestIncrWeaLth) {// 是投资增加财富值
			
			Integer productId = Utils.isObjectEmpty(param.get("productId"))?null:Integer.valueOf(param.get("productId").toString());
			
			DrProductInfo info = drProductInfoDAO.getDrProductInfoByid(productId);
			if (info != null && (info.getType()==2 || info.getType()==6)) {
				List<DrProductInvest> list = drProductInvestDAO.selectProductInvestByPid(info.getId());
				if (!Utils.isEmptyList(list)) {
					Map<String, Object> m = new HashMap<>();
					for(DrProductInvest invest:list) {
						if (invest.getStatus() ==1) {
							//计算财富值
							int wealth=invest.getAmount().multiply(new BigDecimal(info.getDeadline())).divide(new BigDecimal(360),2,
									BigDecimal.ROUND_FLOOR).intValue();
							m.put("type", 6);
							m.put("uid", invest.getUid());
							m.put("investId", invest.getId());
							m.put("wealth", wealth);
							m.put("name", info.getFullName());
							
							incrMemberWealth(m);
						}
					}
				}
			}
			
		}else {//做任务增加财富值
			incrMemberWealth(param);
		}
	}
	
	public Map<String,Object> finishTask(Map<String, Object> map) {
		Map<String,Object> param = new HashMap<String, Object>();
		try {
			Date playingTime = Utils.format("2017-09-12", "yyyy-MM-dd");
			String taskName = map.get("taskName").toString();
			Integer uid = Integer.parseInt(map.get("uid").toString());
			Integer rid = null;
			if(Utils.isObjectNotEmpty(map.get("rid"))){
				rid = Integer.parseInt(map.get("rid").toString());
			}
			BigDecimal taskAmount = BigDecimal.ZERO;
			if(Utils.isObjectNotEmpty(map.get("amount"))){
				taskAmount = new BigDecimal(map.get("amount").toString());
			}
			boolean flag = true;
			//只有首次充值才能赠送财富值
			if(taskName.equals("首次充值任意金额")){
				Integer count = jsMemberTaskDAO.getCrushCount(uid);
				if(count > 1){
					flag = false;
				}
			}else if(taskName.equals("首次邀请好友成功注册")){
				Integer count = jsMemberTaskDAO.getRecommendedCountByUid(uid);
				if(count > 1){
					flag = false;
				}
			}else if(taskName.equals("首次投资指定产品金额≥1000元")){
				param.put("uid", uid);
//				param.put("isJHS", 1);
				//除新手体验标外，判断是否首次投资
				Map<String,Object> resultMap = jsMemberTaskDAO.getInvestCount(param);
				//投资数量
				Integer count = Integer.parseInt(resultMap.get("count").toString());								
				if(count > 1 ){
					flag = false;
				}
			}else if(taskName.equals("当月单次投资指定产品≥10,000元")){
				param.put("name", taskName);
				param.put("uid", uid);
				param.put("nowDate", new Date());
				List<JsTaskDetails> details = jsTaskDetailsDAO.getJstaskDetailByMap(param);
				//当月已经完成过则不赠送
				if(details.size()>0){
					flag = false;
				}
			}else if(taskName.equals("当月单次投资指定产品≥50,000元")){
				param.put("name", taskName);
				param.put("uid", uid);
				param.put("nowDate", new Date());
				List<JsTaskDetails> details = jsTaskDetailsDAO.getJstaskDetailByMap(param);
				//当月已经完成过则不赠送
				if(details.size()>0){
					flag = false;
				}
			}else if(taskName.equals("当月投资指定产品次数≥3次 ,且投资总额≥30,000元")){
				param.put("uid", uid);
				param.put("nowDate", new Date());
//				param.put("isJHS", 1);
				param.put("playingTime", playingTime);
				Map<String,Object> resultMap = jsMemberTaskDAO.getInvestCount(param);
				//投资数量
				Integer count = Integer.parseInt(resultMap.get("count").toString());
				//金额sum
				BigDecimal amount = new BigDecimal(resultMap.get("amount").toString());
				param.clear();
				param.put("name", taskName);
				param.put("uid", uid);
				param.put("nowDate", new Date());
				List<JsTaskDetails> details = jsTaskDetailsDAO.getJstaskDetailByMap(param);
				//当月已经完成过则不赠送
				if(!(count > 2 && amount.compareTo(new BigDecimal("30000"))!=-1 && details.size() < 1)){
					flag = false;
				}
			}else if(taskName.equals("当月投资指定产品次数≥7次 ,且投资总额≥70,000元")){
				param.put("uid", uid);
				param.put("nowDate", new Date());
//				param.put("isJHS", 1);
				param.put("playingTime", playingTime);
				Map<String,Object> resultMap = jsMemberTaskDAO.getInvestCount(param);
				//投资数量
				Integer count = Integer.parseInt(resultMap.get("count").toString());
				//金额sum
				BigDecimal amount = new BigDecimal(resultMap.get("amount").toString());
				param.clear();
				param.put("name", taskName);
				param.put("uid", uid);
				param.put("nowDate", new Date());
				List<JsTaskDetails> details = jsTaskDetailsDAO.getJstaskDetailByMap(param);
				//当月已经完成过则不赠送
				if(!(count > 6 && amount.compareTo(new BigDecimal("70000"))!=-1 && details.size() < 1)){
					flag = false;
				}
			}else if (taskName.equals("邀请的好友当月投资指定产品人数≥5个")) {
				//查询邀请的好友投资指定标人数
				param.clear();
				param.put("playingTime", playingTime);
				param.put("uid", uid);
				Map<String,Object> result = jsMemberTaskDAO.getRecommendedInvestCount(param);
				Integer count = Integer.parseInt(result.get("count").toString());
				param.put("name", taskName);
				param.put("uid", rid);
				param.put("nowDate", new Date());
				List<JsTaskDetails> details = jsTaskDetailsDAO.getJstaskDetailByMap(param);
				if(!(count > 4 && details.size() < 1)){
					flag = false;
				}
			}
			
			if(flag){
				//添加任务完成记录
				param.clear();
				param.put("name", taskName);
				List<JsMemberTask> list = jsMemberTaskDAO.selectjsmembTasksList(param);
				if (!Utils.isEmptyList(list)) {				
					Integer taskId = list.get(0).getId();
					JsTaskDetails taskDetails = new JsTaskDetails();
					taskDetails.setMid(rid != null?rid:uid);
					taskDetails.setMtid(taskId);
					taskDetails.setType(1);
					taskDetails.setAddDate(new Date());
					jsTaskDetailsDAO.insert(taskDetails);
					//赠送财富值
					
					param.put("taskId", taskDetails.getId());
					param.put("wealth",list.get(0).getWealth());
					param.put("uid",taskDetails.getMid());
					param.put("flag", true);
					return param;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			param.clear();
			return param;
		}
		return param;
		
	} 
	
	

}
