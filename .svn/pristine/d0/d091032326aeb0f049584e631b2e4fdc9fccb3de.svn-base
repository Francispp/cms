package com.cyberway.cms.site.service;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

import com.cyberway.cms.site.objects.SimpleJob;
import com.cyberway.core.quartz.services.SchedulerServices;
import com.cyberway.core.utils.StringUtil;


public class SiteQuartzService extends SchedulerServices{

	public static final String GroupName="SiteGroup";
	/**
	 * 指定时间发布站点
	 * @return
	 */
	public boolean setIssuanceSiteDate(String siteid,Date date){
		if(StringUtil.isEmpty(siteid)){
			return false;
		}
		if(date==null)
			return false;
		String jobname="Job_IssuanceSite";
		String triggername="Trigger_IssuanceSite_"+siteid;
		
		try{
		
		 Scheduler sched = getSchedulerFactory().getScheduler();
		 JobDetail job = new JobDetail(jobname, GroupName, SimpleJob.class);
         job.getJobDataMap().put("siteid", siteid);
         //sched.rescheduleJob(arg0, arg1, arg2)
         //sched.resumeTriggerGroup(GroupName);
         //sched.addJob(job, false);
		 SimpleTrigger trigger =  new SimpleTrigger(triggername, GroupName, date);
		
		 //sched.scheduleJob(job, trigger);
		 //sched.start();
		 //trigger.setJobName(jobname);
		 //trigger.setJobGroup(GroupName);
		 //sched.rescheduleJob(jobname, GroupName, trigger);
		 //sched.start();
		} catch (SchedulerException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return false;
		}
		
		return true;
	}
}
