package com.cyberway.staticer.gather;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cyberway.core.utils.ServiceLocator;

/**
 * 支持 QUARTZ 工作的页面发布对象<br />
 * JOB 上下文变量包括 url(要采集的页面地址), role(访问时所使用的角色)
 * @author helfen
 *
 */
public class PagePublishJob implements Job
{
	public void execute(JobExecutionContext ctx) throws JobExecutionException
	{	
		String url = ctx.getMergedJobDataMap().getString("url");
		String role = ctx.getMergedJobDataMap().getString("role");
		
		PagePublisher pagePublisher = (PagePublisher)ServiceLocator.getBean ("cms.staticer.pagePublisher");
		pagePublisher.publish(url, role);
	}
}
