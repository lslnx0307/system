package test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jsjf.dao.system.SysFuiouMessageLogDAO;
import com.jsjf.service.claims.DrClaimsInfoService;
import com.jsjf.service.jzh.SysFuiouMessageService;
import com.jsjf.service.member.DrMemberCarryService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.seq.SeqService;
import com.jsjf.service.system.SysFuiouNoticeLogService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"../spring.xml","../spring-mvc.xml","../spring-mybatis.xml"})
public class JzhMessageTest {
	@Autowired
	private DrMemberService drMemberService;
	@Autowired
	private DrClaimsInfoService drClaimsInfoService;
	@Autowired
	private SeqService seqService;
	@Autowired
	private SysFuiouMessageLogDAO sysFuiouMessageLogDAO;
	@Autowired
	private DrProductInfoService drProdectInfoService;
	@Autowired
	private SysFuiouMessageService sysFuiouMessageService;
	
	@Autowired
	private DrProductInfoService drProductInfoService;
	@Autowired
	private DrProductInvestService drProductInvestService;
	
	
	
	 public static void main(String[] args) {  
		  
			 String str2 = new StringBuilder("ja1").append("va").toString();  
			 System.out.println(str2.intern() == str2);  
			 
	        String str1 = new StringBuilder("chaofan").append("wei").toString();  
	        System.out.println(str1.intern() == str1);  
	  
	    }  
	
	@Autowired
	public SysFuiouNoticeLogService sysFuiouNoticeLogService;
	
	@Autowired
	public DrMemberCarryService drMemberCarryService;
	
//	@Test
//	public void selectPersonRegBatchUpload(){
//		JzhMessageTask task = new JzhMessageTask();
//		task.selectPersonRegBatchUpload(drMemberService, seqService, sysFuiouMessageService);
//	}
	//@Test
//	public void productRaiseEnd(){
//		ProducTask task=new ProducTask();
//		task.productRaiseEnd(drProductInfoService, drProductInvestService);
//	}
//	//@Test
//	public void productRepay(){
//		ProducTask task=new ProducTask();
//		task.productRepay(drProductInvestService);
//	}
	
	/*@Test
	public void productExperience(DrProductInfoService drProductInfoService,DrProductInvestDAO drProductInvestDAO){
		ProducTask task=new ProducTask();
		task.productExperience(drProductInfoService,drProductInvestDAO);
	}*/
	
//	//@Test
//	public void queryChangeCard(){
//		ActivityTask task=new ActivityTask();
//		task.queryChangeCard(sysFuiouNoticeLogService,drMemberService);
//	}
	
//	@Test
//	public void queryFuiouMemberCarryByStatus(){
//		ActivityTask task=new ActivityTask();
//		task.queryFuiouMemberCarryByStatus(drMemberCarryService);
//	}
////	@Test
//	public void companyRegBatchUpload(){
//		JzhMessageTask task = new JzhMessageTask();
//		task.companyRegBatchUpload(drClaimsInfoService, seqService,sysFuiouMessageService);
//	}
//	
////	@Test
//	public void projectInfoUpload(){
//		JzhMessageTask task = new JzhMessageTask();
//		task.projectInfoUpload(drProdectInfoService, seqService, sysFuiouMessageService);
//	}
////	@Test
//	public void getProductByProjectNo(){
//		JzhMessageTask task = new JzhMessageTask();
//		task.getProductByProjectNo(drProdectInfoService, seqService, sysFuiouMessageService);
//	}
	/*@Test
	public void getProductFullCreditByProjectNo(){
		JzhMessageTask task = new JzhMessageTask();
		task.getProductFullCreditByProjectNo(drProdectInfoService, seqService, sysFuiouMessageService);
	}*/
	
////	@Test
//	public void getProductInvestRepayInfoByProjectNo(){
//		JzhMessageTask task = new JzhMessageTask();
//		task.getProductInvestRepayInfoByProjectNo(drProdectInfoService, seqService, sysFuiouMessageService);  
//	}
//
//	@Test
//	public void overcheck(){
//		JzhMessageTask task = new JzhMessageTask();
//		task.overcheck(sysFuiouMessageLogDAO, sysFuiouMessageService);
//	}
}
