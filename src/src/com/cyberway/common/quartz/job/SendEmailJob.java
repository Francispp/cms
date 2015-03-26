package com.cyberway.common.quartz.job;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.cyberway.common.email.domain.CoreEmailLog;
import com.cyberway.common.email.service.EmailLogManagerService;
import com.cyberway.common.email.service.EmailManagerService;
import com.cyberway.core.utils.ServiceLocator;

public class SendEmailJob implements StatefulJob{
	 public SendEmailJob()
	 {
		 
	 }
	 public void execute(JobExecutionContext context)
     throws JobExecutionException {
	 EmailLogManagerService emailLogManagerService = (EmailLogManagerService)ServiceLocator.getBean("emailLogManagerService");	 
	 EmailManagerService emailManagerService = (EmailManagerService)ServiceLocator.getBean("emailManagerService");	
	 List<CoreEmailLog> emails = emailLogManagerService.findBy("status", 0);
	 for(CoreEmailLog email : emails)
	 {
		 try{
		 emailManagerService.reSentEmail(email);
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	 }
 }

}
