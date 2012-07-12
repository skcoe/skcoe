package test.am;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import th.co.ais.sff.domain.constants.AppContextPath;
import th.co.ais.sff.service.am.CreditLimitManagementService;
import th.co.ais.sff.service.am.ProfileChangeBatchInboundServices;

public class TestBatchSendSMSPayment {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ctx;
		
		String[] path = {
				 
				"applicationContext_Dao_load.xml", 
				//"applicationContext_am.xml",
				"applicationContext_Service_IV.xml",
				 "applicationContext_Service_AM.xml",
		         "applicationContext_Service_OM.xml", 
		         "applicationContext_Service_GM.xml" ,
		         "applicationContext_Service_TM.xml",
		        "applicationContext_properties.xml",
		         "applicationContext_om_jms.xml",
			        "applicationContext_eservice_corp_jms.xml"
        };
		ProfileChangeBatchInboundServices profileChangeBatchInboundServices;
		ctx = new ClassPathXmlApplicationContext(path);
		profileChangeBatchInboundServices = (ProfileChangeBatchInboundServices)ctx.getBean("profileChangeBatchInboundServices");
		
		try {
			//profileChangeBatchInboundServices.createBatchSendSMS("Payment_SMS_Online", "SFFBATCH");
			profileChangeBatchInboundServices.createPostPaymentChange("Post_Payment_Method_Change",null,null, "SFFBATCH");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
