package com.jsjf.dao.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.system.SysChooseOption;

public interface SysChooseOptionDAO {
	
	/**
	 * 查询数据字典
	 * @param category 类型
	 * @return List<SysChooseOption>
	 */
    List<SysChooseOption> selectByCategory(@Param(value="category") String category);
    
    List<SysChooseOption> select();
    
}