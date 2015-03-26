package com.cyberway.common.quartz.job;

import java.util.Date;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

public class StateJob implements StatefulJob{
	 public static final String FAVORITE_COLOR = "favorite color";
	    public static final String EXECUTION_COUNT = "count";
	 public StateJob()
	 {
		 
	 }
	 public void execute(JobExecutionContext context)
     throws JobExecutionException {
     String jobName = context.getJobDetail().getFullName();
     JobDataMap data = context.getJobDetail().getJobDataMap();
     String favoriteColor = data.getString(FAVORITE_COLOR);
     // favoriteColor = "green";
     int count = Integer.valueOf(data.getString(EXECUTION_COUNT));
   System.out.println(jobName + "-----" + new Date() + "----"+ favoriteColor + "--------" +count);
     count++;
    data.put(EXECUTION_COUNT, String.valueOf(count));
 }

}
