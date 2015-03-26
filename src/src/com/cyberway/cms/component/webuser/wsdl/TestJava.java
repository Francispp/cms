package com.cyberway.cms.component.webuser.wsdl;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class TestJava {
	public static void main(String[] args) {
		String url = "http://210.72.1.35/services/WebUserWsdlService.jws?WSDL";
		//String url = "http://127.0.0.1:8080/services/WebUserWsdlService.jws?WSDL";
        try {
        	Service ser = new Service();
        	Call call = (Call)(ser.createCall());
            call.setTargetEndpointAddress(new java.net.URL(url) );
            call.setOperationName("operatorUser" );
            String returnVal = (String) call.invoke(new Object[] {"123456",1} );
            System.out.println(returnVal);
//          String returnVal = (String) call.invoke(new Object[] {"1239292911",0} );
//          System.out.println(returnVal);
        } catch (Exception e) {
            e.printStackTrace();
        }

}
	
	public static void main22() {
		try {
			 java.util.Date curr_date=new java.util.Date();
		     String loginno="admin";
		     long tparm=com.cyberway.core.utils.EncryptionHelper.getTimeParm(curr_date);
		     
		     String after_encrypt=com.cyberway.core.utils.EncryptionHelper.encrypt(loginno,com.cyberway.core.utils.EncryptionHelper.getPassPhrase(curr_date,tparm));
		     //after_encrypt=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_encrypt);
		     String after_tparm= com.cyberway.core.utils.EncryptionHelper.encrypt(String.valueOf(tparm),com.cyberway.core.utils.EncryptionHelper.PASS_PHRASE);     
		      //after_tparm=com.cyberway.core.utils.EncryptionHelper.encodeURL(after_tparm);
			String url = " http://172.24.136.222:8080/synchronization/UserInfo.jws";
			Service ser = new Service();
			Call call = (Call) (ser.createCall());
			call.setTargetEndpointAddress(new java.net.URL(url));
			call.setOperationName("ValidateUser");
			String res = (String) call.invoke(new Object[] {after_encrypt,after_tparm});
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
