package com.jsjf.service.product;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.claims.DrAuditInfo;
import com.jsjf.model.claims.DrClaimsCustomer;
import com.jsjf.model.member.DrCompanyFundsLog;
import com.jsjf.model.product.DrProductExtend;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.product.DrProductPic;
import com.jsjf.model.product.JsCoverCharge;
import com.jsjf.model.product.JsInvoice;
import com.jsjf.model.subject.DrSubjectInfo;
import com.jsjf.model.system.SysUsersVo;

public interface DrProductInfoService {

	/**
	 * 拿到产品信息列表数据
	 * 
	 * @param DrProductInfo
	 * @param PageInfo
	 * @return BaseResult
	 */
	public BaseResult getDrProductInfoList(DrProductInfo drProductInfo, PageInfo pi);
	
	/**
	 * 查询体验标数据
	 * 
	 * @param DrProductInfo
	 * @param PageInfo
	 * @return BaseResult
	 */
	public BaseResult getDrExperienceProductInfoList(DrProductInfo drProductInfo, PageInfo pi);
	
	/**
	 * 根据id得到产品信息
	 * @param id
	 * @return DrProductInfo
	 */
    public DrProductInfo getDrProductInfoByid(Integer id); 
    /**
     * 获取体验标 募集中或募集完成的
     * @param id
     * @return DrProductInfo
     */
    public List<DrProductInfo> getDrProductInfoExperience(); 
    
 	/**
 	 * 修改产品信息
 	 * @param  DrProductInfo
	 * @param productFiles 产品图片
	 * @param acceptPicFile 承兑图片
 	 * @return BaseResult
 	 * @throws SQLException;
 	 */
 	public BaseResult updateDrProductInfo(DrProductInfo drProductInfo,MultipartFile[] productFiles,@RequestParam MultipartFile acceptPicFile,MultipartFile principlePcFile, MultipartFile principleAppFile) throws Exception; 
 	
 	/**
 	 * 修改产品为上架
 	 * @param  DrProductInfo
 	 * @param  validatorSid 作废、上架、热推、显示，共用这个方法，作废和上架传validatorSid，显示和热推传null
 	 * @return BaseResult
 	 * @throws SQLException;
 	 */
 	public BaseResult updateDrProductStatus(DrProductInfo drProductInfo,@RequestParam MultipartFile[] productFiles,String validatorSid) throws Exception; 
 	
	/**
	 * 修改产品信息
	 * @param  DrProductInfo
	 * @return void
	 * @throws SQLException;
	 */
	public void updateDrProductInfo(DrProductInfo drProductInfo) throws SQLException; 
 	
 	/**
 	 * 修改体验标
 	 * @return BaseResult
 	 * @throws SQLException;
 	 */
 	public BaseResult updateDrExperienceProductInfo(DrProductInfo drProductInfo) throws Exception;  	
 	
 	/**
 	 * 取消预约
 	 * @param  DrProductInfo
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void updateDrProductCancelBespoke(DrProductInfo drProductInfo) throws Exception; 
 	
	/**
	 * 添加产品信息
	 * @param DrProductInfo
	 * @param productFiles 产品图片
	 * @param acceptPicFile 承兑图片
 	 * @return BaseResult
	 * @throws Exception
	 */
	public BaseResult insertDrProductInfo(DrProductInfo drProductInfo,
			@RequestParam MultipartFile[] productFiles,@RequestParam MultipartFile acceptPicFile,MultipartFile principlePcFile, MultipartFile principleAppFile) throws Exception;
	/**
	 * 添加体验标信息
	 * @param DrProductInfo
 	 * @return BaseResult
	 * @throws Exception
	 */
	public BaseResult insertDrExperienceProductInfo(DrProductInfo drProductInfo) throws Exception;
	
	/**
	 * 添加产品续发信息
	 * @param DrProductInfo
	 * @param productFiles 产品图片
	 * @param acceptPicFile 承兑图片
 	 * @return BaseResult
	 * @throws Exception
	 */
	public BaseResult insertDrProductInfoRenewal(DrProductInfo drProductInfo,
			@RequestParam MultipartFile[] productFiles,@RequestParam MultipartFile acceptPicFile) throws Exception;
	
	/**
	 * 添加产品审核
	 * 
	 * @param DrAuditInfo
 	 * @return BaseResult
	 * @throws Exception
	 */
	public BaseResult insertDrAuditInfo(DrAuditInfo drAuditInfo) throws Exception;
	
	/**
	 * 根据MAP得到产品信息
	 * @param getDrProductInfoByMap
	 * @return DrProductInfo
	 */
	public DrProductInfo getDrProductInfoByMap(Map<String,Object> map); 
  
	/**
	 * 定时更新已募集完成的产品，并生成回款记录
	 */
	public void updateProductToEnd(DrProductInfo info, char[] ary2, Integer nums) throws Exception;
	
	/**
	 * 定时更新已募集完成的产品，并生成回款记录
	 */
	public void updateFuiouProductToEnd(DrProductInfo info, char[] ary2, Integer nums) throws Exception;
	
	/**
	 * 生成体验标回款记录
	 * @param info 
	 * @param ary2
	 * @param nums
	 * @param investList
	 * @throws Exception
	 */
	public void updateProductToEnd2(DrProductInfo info, char[] ary2 , Integer nums,List<DrProductInvest> investList) throws Exception;	
	
	/**
	 * 生成体验标回款记录-存管
	 * @param info 
	 * @param ary2
	 * @param nums
	 * @param investList
	 * @throws Exception
	 */
	public void updateProductToEnd3(DrProductInfo info, char[] ary2 , Integer nums,List<DrProductInvest> investList) throws Exception;	
	
	
	/**
     * 查询募集完成的产品 以及募集中的新手产品
     * @return
     */
    public List<DrProductInfo> selectRaiseSuccesProductInfo();
  
 	/**
 	 * 根据条件得到产品图片
 	 * @param pid
 	 * @return List<DrProductPic>
 	 */
     public List<DrProductPic> getDrProductPicByPid(Integer pid); 
     
 	/**
 	 * 根据ID删除产品图片
 	 * @param id
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void deleteDrProductPicById(Integer id) throws SQLException;
 	
 	/**
 	 * 
 	 * @param drProductInfo
 	 * @param pi
 	 * @param map
 	 * @return
 	 */
 	public BaseResult getInvestmentPoolList(DrProductInfo drProductInfo, PageInfo pi);
 	
	/**
	 * 根据PID拿产品扩展信息
	 * @param  pid 产品ID
	 * @return List<DrProductExtend>
	 * @throws SQLException;
	 */
	public List<DrProductExtend> getDrProductExtendByPid(int pid);
	/**
	 * 根据SID拿产品扩展信息
	 * @param  
	 * @return List<DrProductExtend>
	 * @throws SQLException;
	 */
	public List<DrProductExtend> getDrProductExtendBySid(int pid);
	
	/**
	 * 根据参数获取产品列表--用户导出
	 * @param map
	 * @param pi
	 * @return
	 */
	public List<Map<String,Object>> selectDrProductInfoList(Map<String, Object> map);


	public PageInfo getSubjectProductList(Map<String, Object> param, PageInfo pi);
	
	/**
	 * 放款清单列表
	 */
	public BaseResult getProductLoanList(DrProductInfo drProductInfo,String fullStartDate,String fullEndDate,
			PageInfo pi);
	
	/**
	 * 更新放款状态
	 */
	public void updateDrProductLoanStatus(Map<String, Object> map);
	/**
	 * 更新回款状态
	 */
	public void updateReFundDrProductLoanStatus(Integer id);
	/**
	 * 查询放款数据，不做分页
	 * @param drProductInfo
	 * @return
	 */
	public List<DrProductInfo> getDrProductLoanListByParam(DrProductInfo drProductInfo,String fullStartDate,String fullEndDate);
	/**
	 * 查询还款明细数据
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getReturnNoticeList(Integer id);
	/**
	 * 查询新手标对应还款明细数据
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getReturnNoticeRecordList(Map<String, Object> map);
	

	/**
	 * 申请产品的放款
	 * @param id
	 * @return
	 */
	public void updateDrProductLoanStatusSQ(Map<String, Object> map);

	/**
	 * 放款审核列表
	 */
	public BaseResult getProductLoanListSQ(DrProductInfo drProductInfo,PageInfo pi);
	
	/**
 	 * 根据id得到放款账户信息等
 	 * @param id
 	 * @return DrProductInfo
 	 */
     public DrProductInfo getDrProductInfoByidSQ(Integer id); 
     
     /**
      * 根据Prizeid获取产品
      * @param id
      * @return
      */
     public DrProductInfo getDrProductInfoByPrizeId(Integer PrizeId); 
     
     /**
      * 查询当前时间大于等于成立时间，并且以募集金额等于0并且type=2的产品list
      * @param type
      * @return
      */
     public void getDrProductInfoByType(Integer type);
     /**
      * 满标提醒
      */
     public void productFullRemind (String mobliePhone) throws Exception;
     
     /**
      * 更放款状态并生成放款记录
      * @param pid
      * @return
      * @throws Exception
      */
     public BaseResult updateLoanStatus (int pid,int useKy,String actLoanTime,String tran_flowid) throws Exception;
     
     public int getDrProductInfoListCountByMap(Map<String,Object> map);
     
     /**
      * 存管项目状态更新
      * @param drProductInfo
      */
     public boolean productUpdateToDeposits(DrProductInfo drProductInfo);
     /**
      * 查询存管计息失败的产品
      * @return
      */
     public List<DrProductInfo> selectDepositsRepayFail(Map<String,Object> map);
     
     public BaseResult queryTransferBmu();
     
     
     /**
      * 查询待解冻的产品
      */
     public List<Map<String, Object>> getProductByThaw(Map<String,Object> map);
     
     public List<Map<String, Object>> selectProjectInformation();
     
     public void updateFileStatus(List<DrProductInfo> param);
     
     public List<Map<String, Object>> getProductByProjectNo(Map<String, Object>map);
     

     public List<Map<String, Object>> getProductInvestRepayInfoByProjectNo();
     
     /**
      * 更放款状态并生成放款记录
      * @param pid
      * @return
      * @throws Exception
      */
     public BaseResult updateDrProductLoanByMchntTxnSsn (String mchnt_txn_ssn) throws Exception;
     
     /**
      * 项目数据情况列表
      * @param map
      * @return
      */
     public List<Map<String, Object>>getProductInfoDetail(Map<String, Object> map);
     
     public int getProductInfoDetailCount(Map<String, Object> map);
     
     /**
      * 服务费管理列表
      * @param map
      * @return
      */
     public List<Map<String, Object>>getProductServiceManagement(Map<String, Object> map);
     
     public int getProductServiceManagementCount(Map<String, Object> map);
     
     /**
      * 导入服务费
      * @param list
      * @param userKy
      * @return
      */
     public BaseResult importCovercharge(List<JsCoverCharge> list,Long userKy);
     
     /**
      * 导入票据
      * @param list
      * @param userKy
      * @return
      */
     public BaseResult importInvoice(List<JsInvoice> list,Long userKy);
     
     /**
      * 服务费经办
      * @param map
      * @return
      */
     public BaseResult insertCharge(Map<String, Object> map) throws ParseException;
     
     /**
      * 撤销服务费
      * @param pid
      */
     public void deleteCoverCharge(String code);
     
     /**
      * 开票信息经办
      * @param map
      * @return
      */
     public BaseResult insertInvotice(Map<String, Object> map) throws ParseException;
     
     /**
      * 撤销开票信息
      * @param pid
      */
     public void deleteInvoice(String code);
     
     /**
      * 服务费复核
      * @param map
      * @return
      */
     public BaseResult updateCharge(Map<String, Object> map) throws ParseException;
     
     /**
      * 开票信息复核
      * @param map
      * @return
      */
     public BaseResult updateInvoice(Map<String, Object> map) throws ParseException;
     
     /**
      * 获取存管回款列表
      * @param map
      * @return
      */
     public BaseResult getDepositReturnLoanList(DrProductInfo drProductInfo,PageInfo pi);
     
	/**
	 * 产品满标 执行冻结到冻结
	 * @param daynum 
	 */
	public void productFullfreezeToFreeze(Map<String,Object> map);
	
	/**
     * 获取放回款管理的放款金额总数
     * @param map
     * @return
     */
    public Map<String,Object> selectDrProductLoanAmountSum(DrProductInfo drProductInfo);

	/**
	 * 官网显示新手标融资为O的记录数
	 * @return
	 */
	public int getNewFinancingEnd();
	/**
     * 查询未来三天的待还款企业
     * @param daynum 
     */
	public void sendBalanceDeficiencyMsg(int dayNum);
	
	/**
	 * 定时任务3或1天回款的企业发短信
	 * @param dayNum
	 */
	public void sendMsgForRepayment(int dayNum);
	
	 /**
	   * 根据产品id获得  In_cust_no (借款人或企业的账户)
	   * @param id
	   * @return
	  */
	public String getIn_cust_noByProductId(Integer id);
	
	/**
	 * 根据商品ID查询募集中的产品条数
	 * @param prizeId
	 * @return
	 */
	public int getProductPrize(Integer prizeId);
	
	public BaseResult queryRecharge(String mchnt_txn_ssn)throws SQLException;

	/**
	 * 根据产品id查询改产品分期数据
	 * @param id
	 * @param pi 
	 * @return
	 */
	BaseResult getInstallmentRepaymentProductInfo(Integer id, PageInfo pi);
	
	/**
 	 * 根据条件查询放款总额
 	 * @param map
 	 * @return
 	 */
 	public Map<String, Object> getDepositReturnLoanListSum(DrProductInfo drProductInfo);

	/**
	 * 存管回款
	 * @param tran_flowid 
	 * @param drSubjectInfo 
	 * @param alreadyRaiseAmount 
	 * @param phone 
	 * @param drProductInfo 
	 * @param usersVo 
	 * @param actLoanTime 
	 * @param drCompanyFundsLog 
	 */
	public BaseResult cgReturnedMoney(DrProductInfo drProductInfo, 
			String phone, BigDecimal alreadyRaiseAmount, DrSubjectInfo drSubjectInfo,
			String tran_flowid, String actLoanTime, SysUsersVo usersVo, DrCompanyFundsLog drCompanyFundsLog);

}
