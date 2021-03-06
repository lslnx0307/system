package com.jsjf.dao.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvestRepayInfo;

public interface DrProductInvestRepayInfoDAO {
	
	
	public DrProductInvestRepayInfo selectObjectById(@Param("id")Integer id);

	/**
	 * 批量插入回款记录
	 * @param list
	 */
	public void batchInsert(@Param("list") List<DrProductInvestRepayInfo> list);
	
	/**
	 * 待还款记录
	 * @return
	 */
	public List<DrProductInvestRepayInfo> selectShouldRepayInfo(@Param("pid")Integer pid);
	/**
	 * 待还款记录
	 * @return
	 */
	public List<DrProductInvestRepayInfo> selectRepayInfoListByPid(@Param("pid")Integer pid);
	
	/**
	 * 修改还款记录
	 * @param repayinfo
	 * @throws Exception
	 */
	public void updateById(DrProductInvestRepayInfo repayinfo)throws Exception;
	/**
	 * 按月付息产品，产品明细ID与回款记录关联
	 * @param productInfo
	 * @throws Exception
	 */
	public void updateRepayDetailIdByPid(DrProductInfo productInfo)throws Exception;
	/**
	 * 修改还款记录（恒丰存管）
	 * @param repayinfo
	 * @throws Exception
	 */
	public void updateByIdFuiou(DrProductInvestRepayInfo repayinfo)throws Exception;
	
	public void updateFileStatus(List<DrProductInvestRepayInfo> param);
	
    public List<Map<String, Object>> getProductInvestRepayInfoByProjectNo();
    
    public void updateRemit(Map<String,Object> map);
    
    /**
     * 批量根据id修改 存管 有关数据
     * @param map
     */
    public void updateRemitBatch(List<Map<String,Object>> list);
    
    /**
     * 查询回款其他收益发放失败 回款记录
     * @param map
     * @return
     */
    public List<DrProductInvestRepayInfo> selectTransferFailDrProductInvestRepayInfo();
    
	/**
	 * 根据投资ID查询回款数据
	 * @param map
	 * @return
	 */
    public List<Map<String, Object>> getInvestRepayinfo(Map<String, Object> map);
    
    /**
     * 根据投资ID查询剩余回款合计
     * @param map
     * @return
     */
    public Map<String, Object> getInvestRepayinfoResidueSum(Map<String, Object> map); 
    
    /**
     * 根据投资ID查询已回款合计
     * @param map
     * @return
     */
    public Map<String, Object> getInvestRepayinfoAlreadySum(Map<String, Object> map); 
	
}
