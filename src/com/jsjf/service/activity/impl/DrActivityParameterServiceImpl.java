package com.jsjf.service.activity.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.SerializeUtil;
import com.jsjf.common.SmsSendUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.ActivityFriendDAO;
import com.jsjf.dao.activity.DrActivityParameterDAO;
import com.jsjf.dao.activity.DrCouponsIssuedRulesDAO;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.dao.activity.JsActivityMemberAccountDAO;
import com.jsjf.dao.activity.JsGratitudeBlessingDAO;
import com.jsjf.dao.cpa.DrChannelInfoDAO;
import com.jsjf.dao.member.DrCompanyFundsLogDAO;
import com.jsjf.dao.member.DrMemberCpsFavourableRuleDAO;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.member.DrMemberFundsDAO;
import com.jsjf.dao.member.DrMemberFundsLogDAO;
import com.jsjf.dao.member.DrMemberFundsRecordDAO;
import com.jsjf.dao.member.DrMemberMsgDAO;
import com.jsjf.dao.member.DrMemberRecommendedDAO;
import com.jsjf.dao.member.JsCompanyAccountLogDAO;
import com.jsjf.dao.member.JsMemberRewardsDAO;
import com.jsjf.dao.member.JsRewardsDetailsDAO;
import com.jsjf.dao.product.DrProductInvestDAO;
import com.jsjf.dao.system.JsMerchantMarketingDAO;
import com.jsjf.model.activity.ActivityFriend;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.activity.DrCouponsIssuedRules;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.activity.JsGratitudeBlessing;
import com.jsjf.model.cpa.DrChannelInfo;
import com.jsjf.model.member.DrCompanyFundsLog;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberCpsFavourableRule;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.member.DrMemberFundsLog;
import com.jsjf.model.member.DrMemberFundsRecord;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.model.member.DrMemberRecommended;
import com.jsjf.model.member.JsCompanyAccountLog;
import com.jsjf.model.member.JsMemberRewards;
import com.jsjf.model.member.JsRewardsDetails;
import com.jsjf.model.system.JsMerchantMarketing;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.activity.DrActivityParameterService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jzh.FuiouConfig;
import com.mysql.jdbc.Util;

@Service
@Transactional
public class DrActivityParameterServiceImpl implements
		DrActivityParameterService {

	@Autowired
	private DrActivityParameterDAO drActivityParameterDAO;
	@Autowired
	private DrProductInvestDAO drProductInvestDAO;
	@Autowired
	private DrMemberCpsFavourableRuleDAO drMemberCpsFavourableRuleDAO;
	@Autowired
	private DrMemberFavourableDAO drMemberFavourableDAO;
	@Autowired
	private DrMemberMsgDAO drMemberMsgDAO;
	@Autowired
	private SysMessageLogService sysMessageLogService;
	@Autowired
	RedisClientTemplate redisClientTemplate;
	@Autowired
	DrMemberDAO drMemberDAO;
	@Autowired
	public DrChannelInfoDAO drChannelInfoDAO;
	@Autowired
	public DrCouponsIssuedRulesDAO drCouponsIssuedRulesDAO;
	// @Autowired
	// private JsActivityLuckyMoneyDAO jsActivityLuckyMoneyDAO;
	@Autowired
	private DrMemberRecommendedDAO drMemberRecommendedDAO;
	@Autowired
	private ActivityFriendDAO activityFriendDAO;
	@Autowired
	private DrMemberFundsRecordDAO drMemberFundsRecordDAO;
	@Autowired
	private DrMemberFundsDAO drMemberFundsDAO;
	@Autowired
	private DrMemberFundsLogDAO drMemberFundsLogDAO;
	@Autowired
	private DrCompanyFundsLogDAO drCompanyFundsLogDAO;
	@Autowired
	private JsActivityMemberAccountDAO jsActivityMemberAccountDAO;
	@Autowired
	private JsCompanyAccountLogDAO jsCompanyAccountLogDAO;
	@Autowired
	private JsMerchantMarketingDAO jsMerchantMarketingDAO;
	@Autowired
	private JsGratitudeBlessingDAO jsGratitudeBlessingDAO;
	@Autowired
	private JsRewardsDetailsDAO jsRewardsDetailsDAO;
	@Autowired
	private JsMemberRewardsDAO jsMemberRewardsDAO;

	@Override
	public BaseResult insertActivity(DrActivityParameter drActivityParameter,
			SysUsersVo usersVo) throws Exception {
		BaseResult result = new BaseResult();
		try {
			drActivityParameter.setAddUser(usersVo.getUserKy().intValue());
			drActivityParameter.setAddTime(new Date());
			drActivityParameter.setStatus(0);
			drActivityParameter
					.setSurplusQty(drActivityParameter.getGrantQty());
			drActivityParameterDAO.insertActivityParameter(drActivityParameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public PageInfo getActivityList(PageInfo info,DrActivityParameter drActivityParameter) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", info.getPageInfo().getOffset());
		map.put("limit", info.getPageInfo().getLimit());

		map.put("types", drActivityParameter.getTypes());
		map.put("name", drActivityParameter.getName());
		map.put("code", drActivityParameter.getCode());
		map.put("amount", drActivityParameter.getAmount());
		map.put("raisedRates", drActivityParameter.getRaisedRates());
		map.put("deadline", drActivityParameter.getDeadline());
		map.put("type", drActivityParameter.getType());
		map.put("status", drActivityParameter.getStatus());
		map.put("scenarios", drActivityParameter.getApplicableScenarios());
		map.put("multiple", drActivityParameter.getMultiple());
		map.put("statuses", drActivityParameter.getStatuses());
		map.put("surplusQty", drActivityParameter.getSurplusQty());// 表示剩余数量大于打这个数值
		map.put("startTime", drActivityParameter.getStartTime());
		map.put("endTime", drActivityParameter.getEndTime());
		List<DrActivityParameter> list = drActivityParameterDAO.getDrActivityParameterList(map);
		int tatal = drActivityParameterDAO.getDrActivityParameterTotal(map);
		List<Map<String, Object>> footer = drActivityParameterDAO.getDrActivityParameterListCensus(map);

		info.setTotal(tatal);
		info.setRows(list);
		info.setFooter(footer);

		return info;
	}

	public PageInfo getGiveOutAPList(PageInfo info,
			DrActivityParameter drActivityParameter) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", info.getPageInfo().getOffset());
		map.put("limit", info.getPageInfo().getLimit());

		map.put("type", drActivityParameter.getType());
		map.put("scenarios", drActivityParameter.getApplicableScenarios());
		map.put("statuses", drActivityParameter.getStatuses());
		map.put("surplusQty", drActivityParameter.getSurplusQty());// 表示剩余数量大于打这个数值
		List<DrActivityParameter> list = drActivityParameterDAO.getGiveOutAPList(map);
		int tatal = drActivityParameterDAO.getDrActivityParameterTotal(map);
		info.setTotal(tatal);
		info.setRows(list);

		return info;
	}

	@Override
	public DrActivityParameter getActivityParameterById(Integer id)
			throws Exception {
		return drActivityParameterDAO.getActivityParameterById(id);
	}

	@Override
	public void toModifyActivity(DrActivityParameter drActivityParameter,
			SysUsersVo usersVo) throws Exception {
		drActivityParameter.setUpdateUser(usersVo.getUserKy().intValue());
		drActivityParameter.setUpdateTime(new Date());
		drActivityParameterDAO.updateActivityParameter(drActivityParameter);

	}

	@Override
	public void updateStatus(DrActivityParameter activity, SysUsersVo usersVo)
			throws Exception {
		// DrActivityParameter activity = new DrActivityParameter();
		activity.setStatus(2);
		activity.setUpdateTime(new Date());
		activity.setUpdateUser(usersVo.getUserKy().intValue());
		drActivityParameterDAO.updateActivityParameter(activity);
	}

	@Override
	public List<DrActivityParameter> getActivityList(
			DrActivityParameter drActivityParameter) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", drActivityParameter.getType());
		map.put("scenarios", drActivityParameter.getApplicableScenarios());
		map.put("offset", 0);
		map.put("limit", 1);
		List<DrActivityParameter> list = drActivityParameterDAO
				.getDrActivityParameterList(map);
		return list;
	}

	@Override
	public List<DrActivityParameter> getActivityParameterList(
			Map<String, Object> map) throws Exception {
		return drActivityParameterDAO.getActivityParameter(map);
	}

	@Override
	public void updateStatusByRules(String coupons) {
		drActivityParameterDAO.updateStatusByRules(coupons);
	}

	@Override
	public void insertFriendMemberActivityAmount() {
		// 插入好友邀请返现到活动账户
		drActivityParameterDAO.insertFriendMemberActivityAmount();
		// 插入好友复投的180天投资返现到活动账户
		drActivityParameterDAO.insertFriendMemberActivityAmountFor180();
		// 发送站内信
		drActivityParameterDAO.sendJsActivityMemberAccountMsg();
		drActivityParameterDAO.updateJsActivityMemberAccount();
	}

	@Override
	public void insertCpsFavourable() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysMessageLog> smsList = new ArrayList<SysMessageLog>();
		Date now = new Date();

		map.put("now", Utils.format(now, "yyyy-MM-dd"));
		map.put("limit", 30);
		map.put("offset", 0);
		map.put("status", 1);

		// 1首笔
		map.put("isFirst", 1);// 首笔
		List<Map<String, Object>> investList = drProductInvestDAO
				.selectWillSevenDayRapyInvest(map);// 回款的投资记录
		List<DrMemberCpsFavourableRule> Rule = drMemberCpsFavourableRuleDAO
				.selectByParam(map);// 首投CPS渠道促复投发放规则

		for (int i = 0, length = investList.size(); i < length; i++) {
			Map<String, Object> record = investList.get(i);
			BigDecimal factAmount = new BigDecimal(record.get("amount")
					.toString());// 用户投资金额
			Integer type = Utils.isObjectEmpty(record.get("type")) ? 0
					: Integer.valueOf(record.get("type") + "");// 是否CPS用户

			for (int j = 0, length_j = Rule.size(); j < length_j; j++) {
				DrMemberCpsFavourableRule rule = Rule.get(j);
				Integer deadline = 0;
				BigDecimal dmfAmount = BigDecimal.ZERO;
				if (rule.getMinAmount().compareTo(factAmount) <= 0
						&& rule.getMaxAmount().compareTo(factAmount) >= 0
						&& type == rule.getIsCps().intValue()) { // 是否为CPS渠道
					// 发放第一档红包
					if (Utils.isObjectNotEmpty(rule.getActivityId_1())) {
						DrActivityParameter activity_1 = drActivityParameterDAO
								.getActivityParameterById(rule
										.getActivityId_1());
						// 比例红包转换成用户返现红包
						BigDecimal amount = Utils.nwdMultiply(
								factAmount,
								Utils.nwdDivide(activity_1.getRaisedRates(),
										100))
								.setScale(0, BigDecimal.ROUND_DOWN);

						if (amount.compareTo(dmfAmount) > 0) {
							if (activity_1.getDeadline() > deadline) {
								deadline = activity_1.getDeadline();
							}
							dmfAmount = amount;
							DrMemberFavourable dmf = new DrMemberFavourable(
									activity_1.getId(), Integer.parseInt(record
											.get("uid") + ""), 1,
									activity_1.getCode(), activity_1.getName(),
									amount, null, factAmount, 0,
									Utils.getDayNumOfAppointDate(now,
											-activity_1.getDeadline()), null,
									"福利发放", 0, 0,
									activity_1.getProductDeadline(),
									activity_1.getMultiple());
							drMemberFavourableDAO.insertIntoInfo(dmf);
							DrMemberMsg msg = new DrMemberMsg(
									Integer.parseInt(record.get("uid") + ""),
									0,
									2,
									"福利发放",
									now,
									0,
									0,
									"尊敬的用户，"
											+ dmf.getAmount()
											+ "元理财返现红包已发放到您的账户，请到“我的福利”-“我的优惠券”查看。");
							drMemberMsgDAO.insertDrMemberMsg(msg);
						}

					}
					// 发放第二档红包
					if (Utils.isObjectNotEmpty(rule.getActivityId_2())) {
						DrActivityParameter activity_1 = drActivityParameterDAO
								.getActivityParameterById(rule
										.getActivityId_2());
						if (activity_1.getDeadline() > deadline) {
							deadline = activity_1.getDeadline();
						}
						if (activity_1.getAmount().compareTo(dmfAmount) > 0) {
							dmfAmount = activity_1.getAmount();
						}
						DrMemberFavourable dmf = new DrMemberFavourable(
								activity_1.getId(), Integer.parseInt(record
										.get("uid") + ""), 1,
								activity_1.getCode(), activity_1.getName(),
								activity_1.getAmount(), null,
								activity_1.getEnableAmount(), 0,
								Utils.getDayNumOfAppointDate(now,
										-activity_1.getDeadline()), null,
								"福利发放", 0, 0, activity_1.getProductDeadline(),
								activity_1.getMultiple());
						drMemberFavourableDAO.insertIntoInfo(dmf);
						DrMemberMsg msg = new DrMemberMsg(
								Integer.parseInt(record.get("uid") + ""), 0, 2,
								"福利发放", now, 0, 0, "尊敬的用户，" + dmf.getAmount()
										+ "元理财返现红包已发放到您的账户，请到“我的福利”-“我的优惠券”查看。");
						drMemberMsgDAO.insertDrMemberMsg(msg);
					}

					// 发送短信和站内信
					String sms = "主人！我是聚胜财富送给你的红包啊，最高" + dmfAmount + "元，有效期"
							+ deadline + "天。已经在您账户了，快来激活提现我吧！！";
					SysMessageLog smsVo = new SysMessageLog(
							Integer.parseInt(record.get("uid") + ""), sms, 18,
							Utils.parseDate(
									Utils.format(now, "yyyy-MM-dd 10:00:00"),
									"yyyy-MM-dd HH:mm:ss"), record.get(
									"mobilephone").toString());
					smsList.add(smsVo);
					break;

				}

			}

		}

		// 2.非首笔
		map.put("isFirst", 0);// 非首笔
		investList = drProductInvestDAO.selectWillSevenDayRapyInvest(map);// 回款的投资记录
		Rule = drMemberCpsFavourableRuleDAO.selectByParam(map);// 首投CPS渠道促复投发放规则

		for (int i = 0, length = investList.size(); i < length; i++) {
			Map<String, Object> record = investList.get(i);
			BigDecimal factAmount = new BigDecimal(record.get("amount")
					.toString());
			Integer type = Utils.isObjectEmpty(record.get("type")) ? 0
					: Integer.valueOf(record.get("type") + "");

			for (int j = 0, length_j = Rule.size(); j < length_j; j++) {
				DrMemberCpsFavourableRule rule = Rule.get(j);
				Integer deadline = 0;
				BigDecimal dmfAmount = BigDecimal.ZERO;

				if (rule.getMinAmount().compareTo(factAmount) <= 0
						&& rule.getMaxAmount().compareTo(factAmount) >= 0
						&& rule.getIsCps().intValue() == type) { // 是否为CPS渠道
					// 发放第一档红包
					if (Utils.isObjectNotEmpty(rule.getActivityId_1())) {
						DrActivityParameter activity_1 = drActivityParameterDAO
								.getActivityParameterById(rule
										.getActivityId_1());
						// 比例红包转换成用户返现红包
						BigDecimal amount = Utils.nwdMultiply(
								factAmount,
								Utils.nwdDivide(activity_1.getRaisedRates(),
										100))
								.setScale(0, BigDecimal.ROUND_DOWN);
						if (amount.compareTo(dmfAmount) > 0) {
							dmfAmount = amount;
							if (activity_1.getDeadline() > deadline) {
								deadline = activity_1.getDeadline();
							}

							DrMemberFavourable dmf = new DrMemberFavourable(
									activity_1.getId(), Integer.parseInt(record
											.get("uid") + ""), 1,
									activity_1.getCode(), activity_1.getName(),
									amount, null, factAmount, 0,
									Utils.getDayNumOfAppointDate(now, -7),
									null, "福利发放", 0, 0,
									activity_1.getProductDeadline(),
									activity_1.getMultiple());
							drMemberFavourableDAO.insertIntoInfo(dmf);
							DrMemberMsg msg = new DrMemberMsg(
									Integer.parseInt(record.get("uid") + ""),
									0,
									2,
									"福利发放",
									now,
									0,
									0,
									"尊敬的用户，"
											+ dmf.getAmount()
											+ "元理财返现红包已发放到您的账户，请到“我的福利”-“我的优惠券”查看。");
							drMemberMsgDAO.insertDrMemberMsg(msg);
						}

					}
					// 发放第二档红包
					if (Utils.isObjectNotEmpty(rule.getActivityId_2())) {
						DrActivityParameter activity_1 = drActivityParameterDAO
								.getActivityParameterById(rule
										.getActivityId_2());
						if (activity_1.getDeadline() > deadline) {
							deadline = activity_1.getDeadline();
						}
						if (activity_1.getAmount().compareTo(dmfAmount) > 0) {
							dmfAmount = activity_1.getAmount();
						}
						DrMemberFavourable dmf = new DrMemberFavourable(
								activity_1.getId(), Integer.parseInt(record
										.get("uid") + ""), 1,
								activity_1.getCode(), activity_1.getName(),
								activity_1.getAmount(), null,
								activity_1.getEnableAmount(), 0,
								Utils.getDayNumOfAppointDate(now,
										-activity_1.getDeadline()), null,
								"福利发放", 0, 0, activity_1.getProductDeadline(),
								activity_1.getMultiple());
						drMemberFavourableDAO.insertIntoInfo(dmf);
						DrMemberMsg msg = new DrMemberMsg(
								Integer.parseInt(record.get("uid") + ""), 0, 2,
								"福利发放", now, 0, 0, "尊敬的用户，" + dmf.getAmount()
										+ "元理财返现红包已发放到您的账户，请到“我的福利”-“我的优惠券”查看。");
						drMemberMsgDAO.insertDrMemberMsg(msg);
					}
					// 发放第三档红包
					if (Utils.isObjectNotEmpty(rule.getActivityId_3())) {

						DrActivityParameter activity_1 = drActivityParameterDAO
								.getActivityParameterById(rule
										.getActivityId_3());
						if (activity_1.getDeadline() > deadline) {
							deadline = activity_1.getDeadline();
						}
						if (activity_1.getAmount().compareTo(dmfAmount) > 0) {
							dmfAmount = activity_1.getAmount();
						}

						DrMemberFavourable dmf = new DrMemberFavourable(
								activity_1.getId(), Integer.parseInt(record
										.get("uid") + ""), 1,
								activity_1.getCode(), activity_1.getName(),
								activity_1.getAmount(), null,
								activity_1.getEnableAmount(), 0,
								Utils.getDayNumOfAppointDate(now,
										-activity_1.getDeadline()), null,
								"福利发放", 0, 0, activity_1.getProductDeadline(),
								activity_1.getMultiple());
						drMemberFavourableDAO.insertIntoInfo(dmf);
						DrMemberMsg msg = new DrMemberMsg(
								Integer.parseInt(record.get("uid") + ""), 0, 2,
								"福利发放", now, 0, 0, "尊敬的用户，" + dmf.getAmount()
										+ "元理财返现红包已发放到您的账户，请到“我的福利”-“我的优惠券”查看。");
						drMemberMsgDAO.insertDrMemberMsg(msg);
					}

					// 发送短信和站内信
					String sms = "主人！我是聚胜财富送给你的红包啊，最高" + dmfAmount + "元，有效期"
							+ deadline + "天。已经在您账户了，快来激活提现我吧！！";
					SysMessageLog smsVo = new SysMessageLog(
							Integer.parseInt(record.get("uid") + ""), sms, 18,
							Utils.parseDate(
									Utils.format(now, "yyyy-MM-dd 10:00:00"),
									"yyyy-MM-dd HH:mm:ss"), record.get(
									"mobilephone").toString());
					smsList.add(smsVo);
					break;

				}

			}

		}
		// 短信发送
		for (SysMessageLog smsLog : smsList) {
			sysMessageLogService.sendMsg(smsLog);
		}

	}

	@Override
	public void valentineActivitys(int uid, Integer type,Integer newUser) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		DrMember m = drMemberDAO.selectByUid(uid);
		map.put("code", m.getToFrom());
		List<DrChannelInfo> channelList = drChannelInfoDAO
				.getDrChannelInfoListForMap(map);// 获取toFrom对应的渠道
		map.clear();
		map.put("type", type);// 0:注册赠送 1：投资赠送 2:手动发送 3:绑卡送 4:邀请好友送一张翻倍券 5:感恩活动 7：开通存管送红包
		map.put("status", 1);
		map.put("regTime", m.getRegDate());
		Integer isCps = 0;
		if (!Utils.isEmptyList(channelList)) {
			DrChannelInfo channel = channelList.get(0);// 获取渠道
			isCps = channel.getType() == null ? 0 : channel.getType();
			map.put("isCpses", new Integer[] { 2, isCps });// 渠道类型，如果为null默认为非CPS
		} else {
			map.put("isCpses", new Integer[] { 2, isCps });// 如果渠道不存在，则默认是非CPS
		}

		// if(type==0 && Utils.isObjectNotEmpty(m.getReferrer())){
		// //判断推荐人是否有未发送的体验金
		// Map<String,Object> param = new HashMap<String,Object>();
		// param.put("mobilePhone", m.getReferrer());//推荐人的手机号
		// param.put("flag", -1);
		// param.put("time", new Date());
		// List<JsActivityLuckyMoney> shareList =
		// jsActivityLuckyMoneyDAO.selectByshaerPhone(param);//是否分享过
		// //更新
		// if(shareList.size() >0){
		// JsActivityLuckyMoney jsActivityLuckyMoney = shareList.get(0);
		// jsActivityLuckyMoney.setUid(m.getUid());
		// jsActivityLuckyMoneyDAO.update(jsActivityLuckyMoney);
		// //发放体验金
		// //体验金过期时间为10天
		// Calendar calendar = new GregorianCalendar();
		// calendar.setTime(new Date());
		// calendar.add(calendar.DATE,10);
		// DrMemberFavourable drMemberFavourable = new DrMemberFavourable(null,
		// uid, 3,null,
		// "压岁钱-体验金", jsActivityLuckyMoney.getAmount(), null , null,
		// 0,calendar.getTime(),"压岁钱-体验金", 0, 100, null, 1);
		// drMemberFavourableDAO.insertIntoInfo(drMemberFavourable);
		// }
		//
		// }
		List<DrCouponsIssuedRules> list = drCouponsIssuedRulesDAO
				.getCouponsIssuedRulesListForReg(map);
		if (!Utils.isEmptyList(list)) {
			for (int i = 0; i < list.size(); i++) {
				DrCouponsIssuedRules rules = list.get(i);
				String coupons = rules.getCoupons();
				String[] couponIds = coupons.split(",");
				map.clear();
				map.put("uid", uid);
				map.put("fids", Utils.StringConvertInteger(couponIds));
				map.put("remark", rules.getName());
				map.put("newUser", newUser);
				// 查询用户能使用的大礼包
				List<DrMemberFavourable> mfList = drMemberFavourableDAO
						.getMemberFavourableByValentine(map);
				if (!Utils.isEmptyList(mfList)) {
					// 批量发放优惠券
					drMemberFavourableDAO.batchInsert(mfList);
					// 发送站内信
					if (Utils.isObjectNotEmpty(rules.getMessage())) {
						DrMemberMsg msg = new DrMemberMsg(uid, 0, 1,
								rules.getName(), new Date(), 0, 0,
								rules.getMessage());
						drMemberMsgDAO.insertDrMemberMsg(msg);
					}
				}
			}
		}

	}

	@Override
	public void annualBonus(Map<String, Object> param) {
		BigDecimal annualBonusAmount = BigDecimal.ZERO;
		BigDecimal amount = new BigDecimal(String.valueOf(param.get("amount")));
		if (Integer.parseInt(param.get("deadline").toString()) == 30) {
			annualBonusAmount = amount;
		} else if (Integer.parseInt(param.get("deadline").toString()) == 60) {
			annualBonusAmount = amount.multiply(new BigDecimal(5));
		} else if (Integer.parseInt(param.get("deadline").toString()) == 180) {
			annualBonusAmount = amount.multiply(new BigDecimal(30));
		}
		try {
			// 体验金过期时间为10天
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			calendar.add(calendar.DATE, 10);
			DrMemberFavourable drMemberFavourable = new DrMemberFavourable(
					null, Integer.parseInt(param.get("uid").toString()), 3,
					null, "年终奖-体验金", annualBonusAmount, null, null, 0,
					calendar.getTime(), "年终奖-体验金", 0, 0, null, 1);
			drMemberFavourableDAO.insertIntoInfo(drMemberFavourable);
			// 发送站内信
			DrMemberMsg msg = new DrMemberMsg(Integer.parseInt(param.get("uid")
					.toString()), 0, 1, "年终奖金", new Date(), 0, 0,
					"尊敬的用户，您收到年终奖" + annualBonusAmount + "元");
			drMemberMsgDAO.insertDrMemberMsg(msg);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void marketFirstInvestLaterdays(Integer day, String key,
			String name, int type) {
		try {
			String content = PropertyUtil.getProperties(key);
			Map<String, Object> map = new HashMap<String, Object>();

			if (Utils.isObjectNotEmpty(content)) {
				map.put("name", name);
				map.put("type", type);
				map.put("orders", " amount,raisedRates,multiple ");
				map.put("offset", 0);
				map.put("limit", 1);
				List<DrActivityParameter> list = drActivityParameterDAO
						.selectDrActivityParameterByParam(map);

				if (list.size() > 0) {
					// 发券
					drActivityParameterDAO.insertCouponByFirstInvestLaterdays(
							list.get(0).getId(), day - 1);
					// 发短信
					String[] array = drProductInvestDAO
							.selectfirstInvest(day - 1);// 首投资n天后type=2 and
														// if(atid or
														// prizeId,0,1)
					String text = "";
					if (list.get(0).getType() == 1) {
						text = list.get(0).getAmount() + "元红包";
					} else if (list.get(0).getType() == 2) {
						text = list.get(0).getRaisedRates().setScale(2)
								+ "%加息券";
					} else if (list.get(0).getType() == 3) {
						text = list.get(0).getAmount() + "元 体验金";
					} else if (list.get(0).getType() == 4) {
						text = list.get(0).getMultiple() + "翻倍券";
					}
					SmsSendUtil.batchSMSMarketing(array,
							content.replace("${text}", text));
					//记录短信日志
					for (int a = 0; a < array.length; a++) {
						SysMessageLog smsLog = new SysMessageLog(0, content.replace("${text}", text), 1, null, array[a]);
						sysMessageLogService.sendMsg(smsLog, 1);
					}

					System.out.println("营销短信-首投" + day + "天送" + text + ":"
							+ array.length + "条,发送完成");
					// System.out.println("moblie:"+StringUtils.join(array,","));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("营销短信-首投送券,发送失败");
		}

	}

	public void insertCpsFavourables() throws Exception {

		String content = PropertyUtil.getProperties("paymentBerfore5Day");
		List<Map<String, Object>> smsList = new ArrayList<Map<String, Object>>();
		Map<String, Object> sms = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Date now = new Date();
		String redeliveryAmount = PropertyUtil
				.getProperties("redeliveryAmount");
		map.put("now", Utils.format(now, "yyyy-MM-dd"));
		map.put("limit", 30);
		map.put("offset", 0);
		map.put("status", 1);

		// 1首笔
		map.put("isFirst", 1);// 首笔
		List<Map<String, Object>> investList = drProductInvestDAO
				.selectWillSevenDayRapyInvest(map);// 回款的投资记录
		List<DrMemberCpsFavourableRule> Rule = drMemberCpsFavourableRuleDAO
				.selectByParam(map);// 首投CPS渠道促复投发放规则

		for (int i = 0, length = investList.size(); i < length; i++) {
			Map<String, Object> record = investList.get(i);
			BigDecimal factAmount = new BigDecimal(record.get("amount")
					.toString());// 用户投资金额
			Integer type = Utils.isObjectEmpty(record.get("type")) ? 0
					: Integer.valueOf(record.get("type") + "");// 是否CPS用户

			for (int j = 0, length_j = Rule.size(); j < length_j; j++) {
				DrMemberCpsFavourableRule rule = Rule.get(j);

				BigDecimal dmfAmount = BigDecimal.ZERO;
				BigDecimal couponAmount = BigDecimal.ZERO;
				String expireDate = "";
				if (rule.getMinAmount().compareTo(factAmount) <= 0
						&& rule.getMaxAmount().compareTo(factAmount) >= 0
						&& type == rule.getIsCps().intValue()) { // 是否为CPS渠道
					// 发放第一档红包
					Map<String,Object> result = insertFavourable(now,
							Integer.parseInt(record.get("uid") + ""),
							factAmount, rule.getActivityId_1(), dmfAmount,couponAmount,
							Integer.parseInt(record.get("count") + ""),
							redeliveryAmount, type);
					if(Utils.isObjectNotEmpty(result)){
						dmfAmount = new BigDecimal(result.get("dmfAmount").toString());
						couponAmount = new BigDecimal(result.get("couponAmount").toString());
						expireDate = Utils.format((Date)result.get("expireDate"), "yyyy年MM月dd日");
					}
					// 发放第二档红包
					Map<String,Object> result2 = insertFavourable(now,
							Integer.parseInt(record.get("uid") + ""),
							factAmount, rule.getActivityId_2(), dmfAmount,couponAmount,
							Integer.parseInt(record.get("count") + ""),
							redeliveryAmount, type);
					if(Utils.isObjectNotEmpty(result2)){
						dmfAmount = new BigDecimal(result2.get("dmfAmount").toString());
						couponAmount = new BigDecimal(result2.get("couponAmount").toString());
					}
					// 发放第三档红包
					Map<String,Object> result3 = insertFavourable(now,
							Integer.parseInt(record.get("uid") + ""),
							factAmount, rule.getActivityId_3(), dmfAmount,couponAmount,
							Integer.parseInt(record.get("count") + ""),
							redeliveryAmount, type);
					if(Utils.isObjectNotEmpty(result3)){
						dmfAmount = new BigDecimal(result3.get("dmfAmount").toString());
						couponAmount = new BigDecimal(result3.get("couponAmount").toString());
					}
					if (dmfAmount.compareTo(BigDecimal.ZERO) > 0) {
						sms = new HashMap<String, Object>();
						sms.put("mobilephone", record.get("mobilephone")
								.toString());
						sms.put("content",
								content.replace("${dmfAmount}",
										dmfAmount.toString()).replace("${couponAmount}", couponAmount.setScale(2).toString()).replace("${expireDate}", expireDate));
						smsList.add(sms);
					}
					break;

				}

			}

		}
		// 2.非首笔
		map.put("isFirst", 0);// 非首笔
		investList = drProductInvestDAO.selectWillSevenDayRapyInvest(map);// 回款的投资记录
		Rule = drMemberCpsFavourableRuleDAO.selectByParam(map);// 首投CPS渠道促复投发放规则
		for (int i = 0, length = investList.size(); i < length; i++) {
			Map<String, Object> record = investList.get(i);
			BigDecimal factAmount = new BigDecimal(record.get("amount")
					.toString());
			Integer type = Utils.isObjectEmpty(record.get("type")) ? 0
					: Integer.valueOf(record.get("type") + "");

			for (int j = 0, length_j = Rule.size(); j < length_j; j++) {
				DrMemberCpsFavourableRule rule = Rule.get(j);
				BigDecimal dmfAmount = BigDecimal.ZERO;
				BigDecimal couponAmount = BigDecimal.ZERO;
				String expireDate = "";
				if (rule.getMinAmount().compareTo(factAmount) <= 0
						&& rule.getMaxAmount().compareTo(factAmount) >= 0
						&& type == rule.getIsCps().intValue()) { // 是否为CPS渠道
					// 发放第一档红包
					Map<String,Object> result = insertFavourable(now,
							Integer.parseInt(record.get("uid") + ""),
							factAmount, rule.getActivityId_1(), dmfAmount,couponAmount,
							Integer.parseInt(record.get("count") + ""),
							redeliveryAmount, type);
					if(Utils.isObjectNotEmpty(result)){
						dmfAmount = new BigDecimal(result.get("dmfAmount").toString());
						couponAmount = new BigDecimal(result.get("couponAmount").toString());
						expireDate = Utils.format((Date)result.get("expireDate"), "yyyy年MM月dd日");
					}
					// 发放第二档红包
					Map<String,Object> result2 = insertFavourable(now,
							Integer.parseInt(record.get("uid") + ""),
							factAmount, rule.getActivityId_2(), dmfAmount,couponAmount,
							Integer.parseInt(record.get("count") + ""),
							redeliveryAmount, type);
					if(Utils.isObjectNotEmpty(result2)){
						dmfAmount = new BigDecimal(result2.get("dmfAmount").toString());
						couponAmount = new BigDecimal(result2.get("couponAmount").toString());
					}
					// 发放第三档红包
					Map<String,Object> result3 = insertFavourable(now,
							Integer.parseInt(record.get("uid") + ""),
							factAmount, rule.getActivityId_3(), dmfAmount,couponAmount,
							Integer.parseInt(record.get("count") + ""),
							redeliveryAmount, type);
					if(Utils.isObjectNotEmpty(result3)){
						dmfAmount = new BigDecimal(result3.get("dmfAmount").toString());
						couponAmount = new BigDecimal(result3.get("couponAmount").toString());
					}
					if (dmfAmount.compareTo(BigDecimal.ZERO) > 0) {
						sms = new HashMap<String, Object>();
						sms.put("mobilephone", record.get("mobilephone")
								.toString());
						sms.put("content",
								content.replace("${dmfAmount}",
										dmfAmount.toString()).replace("${couponAmount}", couponAmount.setScale(2).toString()).replace("${expireDate}", expireDate));
						smsList.add(sms);
					}
					break;

				}

			}

		}
		// 3.全部
		map.put("isFirst", 3);//全部
		investList = drProductInvestDAO.selectWillSevenDayRapyInvest(map);// 回款的投资记录
		Rule = drMemberCpsFavourableRuleDAO.selectByParam(map);// 全部CPS渠道促复投发放规则
		for (int i = 0, length = investList.size(); i < length; i++) {
			Map<String, Object> record = investList.get(i);
			BigDecimal factAmount = new BigDecimal(record.get("amount")
					.toString());
			Integer type = Utils.isObjectEmpty(record.get("type")) ? 0
					: Integer.valueOf(record.get("type") + "");

			for (int j = 0, length_j = Rule.size(); j < length_j; j++) {
				DrMemberCpsFavourableRule rule = Rule.get(j);
				BigDecimal dmfAmount = BigDecimal.ZERO;
				BigDecimal couponAmount = BigDecimal.ZERO;
				String expireDate = "";
				if (rule.getMinAmount().compareTo(factAmount) <= 0
						&& rule.getMaxAmount().compareTo(factAmount) >= 0
						&& type == rule.getIsCps().intValue()) { // 是否为CPS渠道
					// 发放第一档红包
					Map<String,Object> result = insertFavourable(now,
							Integer.parseInt(record.get("uid") + ""),
							factAmount, rule.getActivityId_1(), dmfAmount,couponAmount,
							Integer.parseInt(record.get("count") + ""),
							redeliveryAmount, type);
					if(Utils.isObjectNotEmpty(result)){
						dmfAmount = new BigDecimal(result.get("dmfAmount").toString());
						couponAmount = new BigDecimal(result.get("couponAmount").toString());
						expireDate = Utils.format((Date)result.get("expireDate"), "yyyy年MM月dd日");
					}
					// 发放第二档红包
					Map<String,Object> result2 = insertFavourable(now,
							Integer.parseInt(record.get("uid") + ""),
							factAmount, rule.getActivityId_2(), dmfAmount,couponAmount,
							Integer.parseInt(record.get("count") + ""),
							redeliveryAmount, type);
					if(Utils.isObjectNotEmpty(result2)){
						dmfAmount = new BigDecimal(result2.get("dmfAmount").toString());
						couponAmount = new BigDecimal(result2.get("couponAmount").toString());
					}
					// 发放第三档红包
					Map<String,Object> result3 = insertFavourable(now,
							Integer.parseInt(record.get("uid") + ""),
							factAmount, rule.getActivityId_3(), dmfAmount,couponAmount,
							Integer.parseInt(record.get("count") + ""),
							redeliveryAmount, type);
					if(Utils.isObjectNotEmpty(result3)){
						dmfAmount = new BigDecimal(result3.get("dmfAmount").toString());
						couponAmount = new BigDecimal(result3.get("couponAmount").toString());
					}
					if (dmfAmount.compareTo(BigDecimal.ZERO) > 0) {
						sms = new HashMap<String, Object>();
						sms.put("mobilephone", record.get("mobilephone")
								.toString());
						sms.put("content",
								content.replace("${dmfAmount}",
										dmfAmount.toString()).replace("${couponAmount}", couponAmount.setScale(2).toString()).replace("${expireDate}", expireDate));
						smsList.add(sms);
					}
					break;

				}

			}

		}
		if (!Utils.isEmptyList(smsList)) {
			// 短信发送
			for (Map<String, Object> maps : smsList) {
				SmsSendUtil.sendMsgByMarketing(maps.get("mobilephone")
						.toString(), maps.get("content").toString());
				//记录短信日志
				SysMessageLog smsLog = new SysMessageLog(0, maps.get("content").toString(), 1, null, maps.get("mobilephone")
						.toString());
				sysMessageLogService.sendMsg(smsLog, 1);
			}
			// 修改为已发送
			drProductInvestDAO.updateRapyInvestIsgrant();

		}

	}

	private Map<String,Object> insertFavourable(Date now, int uid,
			BigDecimal factAmount, Integer activityId, BigDecimal dmfAmount,BigDecimal couponAmount,
			Integer iSSplit, String redeliveryAmount, Integer type)
			throws SQLException, Exception {
		Map<String, Object> map = null;
		if (Utils.isObjectNotEmpty(activityId)) {
			map = new HashMap<String, Object>();
			DrActivityParameter act = drActivityParameterDAO
					.getActivityParameterById(activityId);
			Integer flag = 0;// 保存是否可拆的值
			if (act.getType() == 5) {
				// 比例红包转换成用户返现红包
				BigDecimal amount1 = Utils.nwdMultiply(factAmount,
						Utils.nwdDivide(act.getRaisedRates(), 100)).setScale(0,
						BigDecimal.ROUND_DOWN);
				act.setAmount(amount1.compareTo(BigDecimal.ZERO) < 1 ? BigDecimal.ONE
						: amount1);
				// 只有比例红包才会显示是否可拆
				if (iSSplit > 1) {
					flag = 1;
				} else if (iSSplit == 1
						&& factAmount
								.compareTo(new BigDecimal(redeliveryAmount)) >= 0
						&& type == 1) {
					flag = 1;
				}
			}
			DrMemberFavourable dmf = new DrMemberFavourable(act.getId(), uid,
					act.getType(), act.getCode(), act.getName(), act.getAmount(), act.getRaisedRates(),
					act.getType() == 5 ? factAmount : act.getEnableAmount(), 0,
					Utils.getDayNumOfAppointDate(now, -act.getDeadline()),
					null, "福利发放", 0, 0, act.getProductDeadline(),
					act.getMultiple());
			// 只有比例红包才会显示是否可拆
			if (act.getType() == 5) {
				dmf.setiSSplit(flag);// 是否可拆
			}
			drMemberFavourableDAO.insertIntoInfo(dmf);
			if(act.getType() == 1){
				dmfAmount = Utils.nwdBcadd(dmfAmount, act.getAmount());
			}else if(act.getType() == 2){
				couponAmount = Utils.nwdBcadd(couponAmount, act.getRaisedRates());
			}
			map.put("dmfAmount", dmfAmount);
			map.put("couponAmount", couponAmount);
			map.put("expireDate", dmf.getExpireDate());
		}
		return map;
	}

	@Override
	public void insertThreePresent() {
		// 一重
		drActivityParameterDAO.insertThreePresentFirst();

		// 二重
		drActivityParameterDAO.insertThreePresentSecond();

		// 三重
		drActivityParameterDAO.insertThreePresentThird();

	}

	@Override
	public void insertTwinPresent() {
		// 一重
		drActivityParameterDAO.insertThreePresentFirst();

		// 二重
		drActivityParameterDAO.insertTwinPresentSecond();

	}

	@Override
	public void selectThreePresentTop() throws Exception {
		List<Map<String, Object>> list = drActivityParameterDAO
				.selectThreePresentTop();
		Map<String, Object> param = new HashMap<String, Object>();

		if (!Utils.isEmptyList(list)) {
			for (Map<String, Object> map : list) {
				param.put(map.get("uid").toString(), map);// 没个推荐人的
															// 排名,手机,uid,投资年化总额
			}
			param.put(
					"top",
					list.size() > 10 ? new ArrayList<Map<String, Object>>(list
							.subList(0, 10)) : list);// 排行榜
		}
		redisClientTemplate.set("threePresentMap".getBytes(),
				SerializeUtil.serialize(param));
	}

	@Override
	public void threePresentMultipleCoupon(Integer uid) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", 1);
		List<ActivityFriend> list = activityFriendDAO.selectObjectByMap(map);
		if (!Utils.isEmptyList(list)) {
			DrMemberRecommended dmr = drMemberRecommendedDAO
					.selectByPrimaryKey(uid);
			DrMember dm = drMemberDAO.selectByPrimaryKey(dmr.getReferrerId());
			if (Utils.isObjectNotEmpty(dm)) {// 推荐人存在
				valentineActivitys(uid, 4,null);
			}
		}

	}

	@Override
	public void insertSendThreePresentToAccount(int uid, ActivityFriend jaf)
			throws Exception {
		Map<String, Object> map = new HashMap<>();
		BaseResult result = new BaseResult();
		DrMember dm = drMemberDAO.selectByPrimaryKey(uid);
		if (Utils.isObjectNotEmpty(dm) && dm.getIsFuiou() == 1) {
			boolean lockFlag = redisClientTemplate.tryLock(
					uid + "_" + jaf.getId(), 3, TimeUnit.SECONDS, true);
			if (lockFlag) {
				// 查询当期 活动返利 未领取金额
				map.put("uid", uid);
				map.put("afid", jaf.getId());
				map.put("status", 0);
				List<Map<String, Object>> list=jsActivityMemberAccountDAO.selectActivityRewardsList(map);
				BigDecimal unclaimed = jsActivityMemberAccountDAO
						.selectActivityRewardsSum(map);
				
				if (null != unclaimed
						&& unclaimed.compareTo(BigDecimal.ZERO) > 0) {

					Map<String, Object> mapTrans = new HashMap<String, Object>();

					String remitMchntTxnSsn = Utils.createOrderNo(6, uid, "");// 流水号
					mapTrans.put("mchnt_txn_ssn", remitMchntTxnSsn);
					mapTrans.put("out_cust_no", FuiouConfig.LOGIN_ID);
					mapTrans.put("in_cust_no", dm.getMobilephone());
					mapTrans.put("amt", unclaimed.toString());
					mapTrans.put("rem", "好友投资返现");
					mapTrans.put("contract_no", "");
					mapTrans.put("icd_name", "好友投资返现");

					// 修改活动返利记录表为已领
					map.clear();
					map.put("uid", uid);
					map.put("afid", jaf.getId());
					map.put("status", 1);
					map.put("toStatus", 0);
					jsActivityMemberAccountDAO.update(map);

					// 用户资金变动
					DrMemberFunds funds = drMemberFundsDAO
							.queryDrMemberFundsByUid(uid);
					funds.setFuiou_balance(funds.getFuiou_balance().add(
							unclaimed));
					funds.setFuiou_investProfit(funds.getFuiou_investProfit()
							.add(unclaimed));// 已收益加上红包收益
					drMemberFundsDAO.updateDrMemberFunds(funds);

					// 用户资金交易记录表
					DrMemberFundsRecord fundsRecord = new DrMemberFundsRecord(
							null, null, uid, 4, 1, unclaimed,
							funds.getFuiou_balance(), 3, jaf.getName() + "第"
									+ jaf.getPeriods() + "期领奖", null);
					drMemberFundsRecordDAO.insert(fundsRecord);
					
					
					for (Map<String, Object> m : list) {
						if(m.get("isRepeat").equals(0)){//首投
							// 记公司账户日志 邀请好友返现
							JsCompanyAccountLog companyAccountLog = new JsCompanyAccountLog();
							companyAccountLog.setCompanyfunds(10);// 资金类型
							companyAccountLog.setType(0);// 支出
							companyAccountLog.setAmount(new BigDecimal(m.get("amount").toString()));// 金额
							companyAccountLog.setStatus(3);// 成功
							companyAccountLog.setRemark(dm.getMobilephone() + "好友首投返现");
							companyAccountLog.setAddTime(new Date());
							companyAccountLog.setChannelType(2);// 存管
							companyAccountLog.setUid(dm.getUid());// 用户id
							jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);
							
							// 用户资金日志明细表
							DrMemberFundsLog logs = new DrMemberFundsLog(uid,
									fundsRecord.getId(), new BigDecimal(m.get("amount").toString()), 10, 1,
									jaf.getName() + "第" + jaf.getPeriods() + "期领奖");
							drMemberFundsLogDAO.insertDrMemberFundsLog(logs);
						}else{//复投
							// 记公司账户日志 邀请好友返现
							JsCompanyAccountLog companyAccountLog = new JsCompanyAccountLog();
							companyAccountLog.setCompanyfunds(11);// 资金类型
							companyAccountLog.setType(0);// 支出
							companyAccountLog.setAmount(new BigDecimal(m.get("amount").toString()));// 金额
							companyAccountLog.setStatus(3);// 成功
							companyAccountLog.setRemark(dm.getMobilephone() + "好友复投返现");
							companyAccountLog.setAddTime(new Date());
							companyAccountLog.setChannelType(2);// 存管
							companyAccountLog.setUid(dm.getUid());// 用户id
							jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);
							
							// 用户资金日志明细表
							DrMemberFundsLog logs = new DrMemberFundsLog(uid,
									fundsRecord.getId(), new BigDecimal(m.get("amount").toString()), 11, 1,
									jaf.getName() + "第" + jaf.getPeriods() + "期领奖");
							drMemberFundsLogDAO.insertDrMemberFundsLog(logs);
						}
						
					}


					// 公司资金交易记录表
					DrCompanyFundsLog cfundsLog = new DrCompanyFundsLog(11,
							uid, null, unclaimed, 0, jaf.getName() + "第"
									+ jaf.getPeriods() + "期领奖", 0);
					drCompanyFundsLogDAO.insertDrCompanyFundsLog(cfundsLog);

					// 商户营销流水
					JsMerchantMarketing jmm = new JsMerchantMarketing(
							unclaimed, null, null, null, dm.getUid(), 3,
							new Date(), remitMchntTxnSsn, null, null, null,
							"好友注册返现", 1);

					jsMerchantMarketingDAO.insert(jmm);

					result = FuiouConfig.transferBmu(mapTrans);// 转帐

					if (!result.isSuccess()) {// 存管处理失败 ，事务回滚
						throw new RuntimeException("推荐好友红包返现失败:"
								+ result.getErrorMsg() + ",uid=" + uid
								+ ",mobile=" + dm.getMobilephone());//
					}

				}

			}
		}
	}

	@Override
	public void gratitudeBlessing(int uid) throws Exception {

		JsGratitudeBlessing obj = jsGratitudeBlessingDAO
				.selectObjectOneByUid(uid);

		if (Utils.isObjectEmpty(obj)) {
			obj = new JsGratitudeBlessing();
			obj.setUid(uid);
			obj.setSplit(1);
			jsGratitudeBlessingDAO.insert(obj);
		} else if (obj.getSplit() == 0) {
			obj.setSplit(1);
			jsGratitudeBlessingDAO.update(obj);
		} else {
			return;
		}
		valentineActivitys(uid, 5,null);
	}

	@Override
	public void drawEquity(Map<String, Object> map) {
		BaseResult result = new BaseResult();
		Integer uid = Integer.parseInt(map.get("uid").toString());
		Integer rewardId = Integer.parseInt(map.get("rewardId").toString());
		DrMember member = drMemberDAO.selectByPrimaryKey(uid);
		map.put("mid", uid);
		map.put("addDate", 1);
		map.put("rewardId", rewardId);
//		List<Map<String, Object>> list = jsRewardsDetailsDAO.selectJsRewardsDetailsByMap(map);
		JsMemberRewards jsMemberRewards = jsMemberRewardsDAO.getJsMemberRewardById(rewardId);
		if (Utils.isObjectNotEmpty(jsMemberRewards)) {
			Date bstartTime = null ,bendTime = null,ystartTime = null,yendTime = null;
			Integer way = jsMemberRewards.getWay()==null?0:jsMemberRewards.getWay();
			BigDecimal amount = jsMemberRewards.getAmount();
			String apid = jsMemberRewards.getApid();// 优惠券id
			try {
				Map<String, Object> param = new HashMap<String,Object>();
				param.put("mid", uid);
				//年末礼包、生日礼包时根据type一年只能领一次
				Calendar c = Calendar.getInstance();  
				if(jsMemberRewards.getType() == 3){
					int birthDateInt = Integer.valueOf(Utils.format(member.getBirthdate(), "MMdd"));
					int newDateInt = Integer.valueOf(Utils.format(new Date(), "MMdd"));
					if (1231 >= birthDateInt && birthDateInt >= 1201 
							&& 129 >= newDateInt &&  newDateInt >=101								
							){
						c.setTime(new Date());
						c.add(Calendar.YEAR, -1);
					}else{
						c.setTime(new Date());
					}
					
					Date now = c.getTime();
					//今年的生日
					bstartTime = Utils.parse(Utils.format(now, "yyyy")+"-"+Utils.format(member.getBirthdate(), "MM-dd"), "yyyy-MM-dd");
					c.setTime(bstartTime);
					c.add(Calendar.DAY_OF_MONTH, 29); 
					bendTime = c.getTime();
					param.put("startTime", bstartTime);
					param.put("endTime", bendTime);
					param.put("type", jsMemberRewards.getType());
				}else if(jsMemberRewards.getType() == 4){
					Date now = new Date();
					if (!Utils.format(new Date(), "MM-dd").equals("12-31")) {//跨年处理
						   c.setTime(new Date());
					       c.add(Calendar.YEAR, -1);
					       now = c.getTime();
						}
					ystartTime = Utils.parse(Utils.format(now, "yyyy")+"-12-31", "yyyy-MM-dd");
					c.setTime(ystartTime);
					c.add(Calendar.DAY_OF_MONTH, 29); 
					yendTime = c.getTime();
					param.put("startTime", ystartTime);
					param.put("endTime", yendTime);
					param.put("type", jsMemberRewards.getType());
				}else{
					param.put("mrid", rewardId);
				}
				List<Map<String,Object>> detailsList = jsRewardsDetailsDAO.selectJsRewardsDetailsByMap(param);
				if(detailsList.size() == 0){
					// 红包
					if (way != 100) {
						// 添加领取记录
						JsRewardsDetails details = new JsRewardsDetails();
						details.setMid(uid);
						details.setMrid(rewardId);
						details.setAddDate(new Date());
						jsRewardsDetailsDAO.insert(details);
						// 发送奖励
						sendPrize(apid, uid);
						result.setSuccess(true);
					}
					// 现金
					else if (way == 100) {
						// 添加领取记录
						JsRewardsDetails details = new JsRewardsDetails();
						details.setMid(uid);
						details.setMrid(rewardId);
						details.setAddDate(new Date());
						jsRewardsDetailsDAO.insert(details);
						String remitMchntTxnSsn = Utils.createOrderNo(6,
								member.getUid(), "");// 流水号
						DrMemberFunds funds = drMemberFundsDAO
								.queryDrMemberFundsByUid(uid);
						// 用户资金记录 修改用户资金
						funds.setFuiou_balance(funds.getFuiou_balance().add(amount));
						funds.setFuiou_investProfit(funds.getFuiou_investProfit()
								.add(amount));// 已收益加上红包收益
						drMemberFundsDAO.updateDrMemberFunds(funds);
						
						DrMemberFundsRecord record = new DrMemberFundsRecord(null,
								null, uid, 8, 1, amount, funds.getFuiou_balance(),
								3, "领取会员权益返现金", null);
						drMemberFundsRecordDAO.insert(record);
						
						DrMemberFundsLog logs = new DrMemberFundsLog(uid,
								record.getId(), amount, 33, 1, "领取会员权益返现金");
						drMemberFundsLogDAO.insertDrMemberFundsLog(logs);//50
						
						DrCompanyFundsLog cfundsLog = new DrCompanyFundsLog(11,
								uid, null, amount, 0, "领取会员权益返现金", 0);
						drCompanyFundsLogDAO.insertDrCompanyFundsLog(cfundsLog);
						// ....
						// 记公司账户日志 回款营销收益
						JsCompanyAccountLog companyAccountLog = new JsCompanyAccountLog();
						companyAccountLog.setCompanyfunds(33);// 资金类型
						companyAccountLog.setType(0);// 支出
						companyAccountLog.setAmount(amount);// 金额
						// companyAccountLog.setBalance(jsCompanyAccountLogDAO.getBlanceByFuiou().subtract(balanceProfit));//支出
						companyAccountLog.setStatus(3);// 成功
						companyAccountLog.setPid(null);
						companyAccountLog.setRemark(member.getMobilephone()
								+ "领取会员权益返现金");
						companyAccountLog.setAddTime(new Date());
						companyAccountLog.setChannelType(2);// 存管
						companyAccountLog.setUid(member.getUid());// 用户id
						jsCompanyAccountLogDAO
						.insertCompanyAccountLog(companyAccountLog);
						
						// 商户营销流水
						JsMerchantMarketing jmm = new JsMerchantMarketing(amount,
								null, null, null, member.getUid(), 0, new Date(),
								remitMchntTxnSsn, null, null, null, "领取会员权益返现金", 1);
						jsMerchantMarketingDAO.insert(jmm);
						// 调用fuiou接口
						Map<String, Object> param2 = new HashMap<String, Object>();
						param2.put("mchnt_txn_ssn", remitMchntTxnSsn);
						param2.put("out_cust_no", FuiouConfig.LOGIN_ID);
						param2.put("in_cust_no", member.getMobilephone());
						param2.put("amt", amount.toString());
						param2.put("rem", "领取会员权益返现金");
						param2.put("contract_no", "");
						param2.put("icd_name", "领取会员权益返现金");
						result = FuiouConfig.transferBmu(param2);// 转帐
					}
				}else{
					result.setSuccess(true);
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			if (!result.isSuccess()) {// 失败回滚事务
				throw new RuntimeException("领取会员权益返现金失败:"
						+ result.getErrorMsg() + ",uid=" + uid + ",amt="
						+ amount);// 外面捕捉到异常 ,会保存到redis里
			}
		}
	}
	private void sendPrize(String apid,Integer uid) {
		try {
			if(Utils.isObjectNotEmpty(apid)){
				String[] apids = apid.split(",");
				for (String item : apids) {
					Integer id = Integer.parseInt(item);
					DrActivityParameter dap = drActivityParameterDAO.getActivityParameterById(id);
					DrMemberFavourable dmf = new DrMemberFavourable(
							id,uid,
							dap.getType(), dap.getCode(), dap.getName(), dap.getAmount(),
							dap.getRaisedRates(), dap.getEnableAmount(), 0,
							Utils.getDayNumOfDate(1 - dap.getDeadline()), "会员权益", 0,
							3, dap.getProductDeadline(), dap.getMultiple(), null);
					drMemberFavourableDAO.insertIntoInfo(dmf);
					Double amount = 0.0;
					String str = "";
					if(dap.getType() == 1 || dap.getType() == 3){//红包、体验金
						amount = dap.getAmount().doubleValue();
						str = "元";
					}else if(dap.getType() == 2){//加息券
						str = "%";
						amount = dap.getRaisedRates().doubleValue();
					}else if(dap.getType() == 4){//翻倍券
						str = "倍";
						amount = dap.getMultiple().doubleValue();
					}
					// 发送站内信
					DrMemberMsg msg = new DrMemberMsg(uid, 0, 1, "会员权益红包",
							new Date(), 0, 0, "尊敬的用户，您收到"+ dap.getName() + amount + str);
					drMemberMsgDAO.insertDrMemberMsg(msg);
				}
			}
		} catch (Exception e) {
			System.out.println("会员权益发奖品失败，uid "+uid+"couponId："+apid);
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>> getRepayType() {
		return drActivityParameterDAO.getRepayType();
	}
}
