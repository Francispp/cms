package com.cyberway.common.quartz.view;

import java.util.List;

import org.ecside.util.RequestUtil;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

import com.cyberway.common.quartz.domain.Job;
import com.cyberway.common.quartz.job.StateJob;
import com.cyberway.common.quartz.service.SchedulerManagerService;
import com.cyberway.core.web.BaseController;

public class SchedulerController extends BaseController{
	protected String keys;
	private String method;
	private List lsJobs;
	public Job domain;
	SchedulerManagerService service =(SchedulerManagerService) this.getServiceById("schedulerManagerService");
	
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		//Map paramMap=ActionContext.getContext().getParameters();
		
		
		//操作
		if(method==null){
			
		}else if(method.equalsIgnoreCase("pauseall")){
			//
			boolean bol=service.doPauseAll();
			logger.info("all jobs pause:"+bol);
			
		}else if(method.equalsIgnoreCase("resumeall")){
			//
			boolean bol=service.doResumeAll();
			logger.info("all jobs resume:"+bol);
			
		}
		
		lsJobs=service.doGetJobNames();
		RequestUtil.setTotalRows(getHttpServletRequest(), lsJobs.size());
	
		logger.info("all jobs count:"+(lsJobs==null?0:lsJobs.size()));
		
		return "quartz_list";
	}
	public String add() throws Exception {
		domain = new Job();
		
		return "input";
	}
	public String pause() throws Exception {
		if(keys != null && keys.length()>0)
		{
			String[] str = keys.split(",");
			for(int i=0;i<str.length;i++)
			{
				String[] s = str[i].split("@");
				if(s.length == 3)
				{
					boolean bol = service.doPause(s[0], s[1],s[2]);
					logger.info(s[0]+" pause:"+bol);
				}
			}

		}
		return execute();
	}
	public String resume() throws Exception {
		if(keys != null && keys.length()>0)
		{
			String[] str = keys.split(",");
			for(int i=0;i<str.length;i++)
			{
				String[] s = str[i].split("@");
				if(s.length == 3)
				{
					boolean bol = service.doResume(s[0], s[1],s[2]);
					logger.info(s[0]+" resume:"+bol);
				}
			}

		}
		return execute();
	}
	public String delete() throws Exception {
		if(keys != null && keys.length()>0)
		{
			String[] str = keys.split(",");
			for(int i=0;i<str.length;i++)
			{
				String[] s = str[i].split("@");
				if(s.length == 2)
				{
					boolean bol = service.doDelete(s[0], s[1]);
					logger.info(s[0]+" delete:"+bol);
				}
			}

		}
		return execute();
	}
	public String saveOrUpdate() throws Exception {
		if(domain != null)
		{
		SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();
        JobDetail job1 = new JobDetail(domain.getJobName(), domain.getJobGroup(), Class.forName(domain.getJobClass()));
        CronTrigger trigger = new CronTrigger(domain.getTrigger(), domain.getJobGroup(), domain.getJobName(),domain.getJobGroup(), domain.getExpression());
      	job1.getJobDataMap().put(StateJob.FAVORITE_COLOR, "Green");
        job1.getJobDataMap().put(StateJob.EXECUTION_COUNT, "1");
        sched.scheduleJob(job1, trigger);
		}
		return execute();
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
	public Job getDomain() {
		return domain;
	}
	public void setDomain(Job domain) {
		this.domain = domain;
	}
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}

}
