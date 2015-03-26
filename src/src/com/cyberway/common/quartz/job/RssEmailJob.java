package com.cyberway.common.quartz.job;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.mail.HtmlEmail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.cyberway.cms.Constants;
import com.cyberway.common.email.domain.CoreEmailLog;
import com.cyberway.common.email.service.EmailLogManagerService;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class RssEmailJob implements StatefulJob{
	 public RssEmailJob() { }
	 public void execute(JobExecutionContext context) throws JobExecutionException {
		 
		 System.out.println("========================================email ======================================================");
		 EmailLogManagerService emailLogManagerService = (EmailLogManagerService)ServiceLocator.getBean("EmailLogManagerService");	 
		 List<CoreEmailLog> logs = emailLogManagerService.findBy("status", new Integer(0));
		 for(CoreEmailLog emailLog : logs)
		 {
			 if(emailLog.getStatus() == 0)
			 {
			 try {
				 String body="";   
			      String line = "";  
			      HtmlEmail email = new HtmlEmail ();
			      if(emailLog.getContentURL() != null && (emailLog.getEmailContent()==null || ObjectUtils.equals(emailLog.getEmailContent(), "")))
			      {
				 URL url = new URL  (emailLog.getContentURL());   
				 HttpURLConnection urlConnection  = (HttpURLConnection)url.openConnection();   
				
				 BufferedReader in  = new BufferedReader(   
	                     new InputStreamReader(   
	                             urlConnection.getInputStream(),"UTF-8"));   
	             while ((line = in.readLine()) != null) {    
	            	 body+=line;   
	             }   
	             
			      }
			      else
			    	  body = emailLog.getEmailContent();
			      
				email.setHostName("mail.cyberway.net.cn");
				email.setCharset("gb2312"); 
				email.setSubject(emailLog.getEmailSubject());
				//email.setTextMsg("中文"); 
				email.setHtmlMsg(body);
				email.addTo(emailLog.getTo());
				if(emailLog.getCc() != null)
				email.addCc(emailLog.getCc());
				email.setFrom( Constants.DEFAULT_FROMEMAIL);   
				email.setAuthentication(Constants.DEFAULT_FROMEMAIL,Constants.DEFAULT_FROMEMAILPASS);   
				email.send();
				emailLog.setStatus(1);
				emailLogManagerService.saveOrUpdate(emailLog);
				System.out.println("OK");
				} catch (Exception e) { 
					e.printStackTrace();
				}  
			 }
		 }
		
	 }
}
