package com.cyberway.core.quartz.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.cyberway.core.quartz.vo.Jobs;

public class SchedulerServices{
	protected Log logger = LogFactory.getLog(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 3257849883124707380L;
	
	Scheduler sched = null;
	static SchedulerFactory sf=null;
	static{
		// First we must get a reference to a scheduler
		try {
			 sf = new StdSchedulerFactory("/quartz.properties");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}


	/* (non-Javadoc)
	 * @see com.jh.quartz.services.SchedulerServices#doGetJobNames()
	 */
	public List doGetJobNames() {
		
		logger.info("======doGetJobNames=====");
		
		//所有任务列表
		List lsJobs=new ArrayList();
	
		try {		

			sched = sf.getScheduler();

			String[] triggerGroups = sched.getTriggerGroupNames();
			logger.info("======triggerGroups:"+(triggerGroups==null ? 0:triggerGroups.length));
			
			for (int i = 0; i < triggerGroups.length; i++) {
				String[] triggerNames = sched.getTriggerNames(triggerGroups[i]);
				for (int j = 0; j < triggerNames.length; j++) {
					//任务VO
					Jobs jobs=new Jobs();
					
					Trigger trigger = sched.getTrigger(triggerNames[j],triggerGroups[i]);
					JobDetail jobDetail = sched.getJobDetail(trigger.getJobName(), trigger.getJobGroup());
					// get the job info
					String jobName = jobDetail.getName();
					String jobClass = jobDetail.getJobClass().getName();
					String jobDescription = jobDetail.getDescription();
					String jobGroup = jobDetail.getGroup();
					// get the trigger info
					String triggerName = trigger.getName();
					String triggerGroup = trigger.getGroup();
					int triggerState = sched.getTriggerState(triggerName,
							triggerGroup);

					logger.info("-----" + j + "-----");
					logger.info("jobName:" + jobName);
					logger.info("jobClass:" + jobClass);
					logger.info("jobDescription:" + jobDescription);
					logger.info("jobGroup:" + jobGroup);
					logger.info("triggerName:" + triggerName);
					logger.info("triggerGroup:" + triggerGroup);
					logger.info("triggerState:" + triggerState);
					
					jobs.setJobName(jobName);
					jobs.setJobClass(jobClass);
					jobs.setJobDescription(jobDescription);
					jobs.setJobGroup(jobGroup);
					jobs.setTriggerName(triggerName);
					jobs.setTriggerGroup(triggerGroup);
					jobs.setState(triggerState);
					
					String state="";
					if(Trigger.STATE_NORMAL==triggerState){
						state="STATE_NORMAL";
					}else if(Trigger.STATE_PAUSED==triggerState){
						state="STATE_PAUSED";
					}else if(Trigger.STATE_COMPLETE==triggerState){
						state="STATE_COMPLETE";
					}else if(Trigger.STATE_ERROR==triggerState){
						state="STATE_ERROR";
					}else if(Trigger.STATE_BLOCKED==triggerState){
						state="STATE_BLOCKED";
					}else if(Trigger.STATE_NONE==triggerState){
						state="STATE_NONE";
					}
					jobs.setTriggerState(state);					
					
					lsJobs.add(jobs);
				}
			}

		} catch (SchedulerException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		return lsJobs;

	}
	
    /**
     * @return
     */
    public boolean doPauseAll() {    	
        try {
            sched.pauseAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
	
    /**
     * @return
     */
    public boolean doResumeAll() {
        try {
            sched.resumeAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * 
     * @return
     */
    public SchedulerFactory getSchedulerFactory(){
    	return this.sf;
    }
}
