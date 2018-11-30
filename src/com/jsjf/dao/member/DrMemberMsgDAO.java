package com.jsjf.dao.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrMemberMsg;


public interface DrMemberMsgDAO {
    /**
	 * @Description 添加
	 * @param DrMemberCount
	 * @return void
	 * @throws SQLException
	 */
    public void insertDrMemberMsg(DrMemberMsg drMemberMsg) throws SQLException;
    
    /**
	 * @Description 批量添加
	 * @param DrMemberCount
	 * @return void
	 * @throws SQLException
	 */
    public void batchInsert(List<DrMemberMsg> list) throws SQLException;
    /**
     * 根据uid批量添加
     * @param list
     * @throws SQLException
     */
    public void batchInsertByUid(Map<String,Object> map) throws SQLException;
}