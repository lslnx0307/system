package com.jsjf.dao.member;

import com.jsjf.model.member.JsMemberRewards;

public interface JsMemberRewardsDAO {
	/**
	 * 根据id获得奖励和等级
	 * @param id
	 * @return
	 */
	JsMemberRewards getJsMemberRewardById(Integer id);
}
