package com.cyberway.core.quartz.web;

import java.util.List;

import com.cyberway.core.quartz.services.SchedulerServices;
import com.cyberway.core.web.BaseController;

public class SechedulerAdmin extends BaseController {

	private String method;
	private List lsJobs;
	
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		//Map paramMap=ActionContext.getContext().getParameters();
		SchedulerServices sched=(SchedulerServices) getServiceById("SchedulerServices");
		
		//操作
		if(method==null){
			
		}else if(method.equalsIgnoreCase("pauseall")){
			//
			boolean bol=sched.doPauseAll();
			logger.info("all jobs pause:"+bol);
			
		}else if(method.equalsIgnoreCase("resumeall")){
			//
			boolean bol=sched.doResumeAll();
			logger.info("all jobs resume:"+bol);
			
		}
		
		lsJobs=sched.doGetJobNames();
		logger.info("all jobs count:"+(lsJobs==null?0:lsJobs.size()));
		
		return "list";
	}

	public List getLsJobs() {
		return lsJobs;
	}
	

	public void setLsJobs(List lsJobs) {
		this.lsJobs = lsJobs;
	}

	public String getMethod() {
		return method;
	}	

	public void setMethod(String method) {
		this.method = method;
	}
	
	

}
