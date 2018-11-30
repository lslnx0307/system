package com.jsjf.dao.member;

import java.util.List;
import java.util.Map;

import com.jsjf.model.member.JsMemberGrade;

public interface JsMemberGradeDAO {

	/**
	 * 获取会员等级数据
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getMemberGrade();
	/**
	 * 获取会员等级数据
	 * @param map
	 * @return
	 */
	public JsMemberGrade getMemberGradeById(Integer id);
	
	/**
	 * 当前财富值可获得的等级
	 * @param wealth
	 * @return
	 */
	public JsMemberGrade selectLevelByWealth(Integer wealth);
	/**
	 * 查询该会员等级及财富值
	 * @param uid
	 * @return
	 */
	public Integer selectLightQualification(Integer uid);
	/**
	 * 点亮会员
	 * @param uid
	 */
	public void updateLightUpTheMember(Integer uid);
}
