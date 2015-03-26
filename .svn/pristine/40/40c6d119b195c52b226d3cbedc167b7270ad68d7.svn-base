package com.cyberway.crawl.console.view;

import com.cyberway.core.web.BaseController;
import com.cyberway.crawl.jobs.service.JobsManagerService;
import com.cyberway.issue.crawler.admin.CrawlJob;
import com.cyberway.issue.crawler.admin.CrawlJobHandler;
import com.cyberway.issue.crawler.admin.StatisticsTracker;
import com.cyberway.issue.crawler.framework.CrawlController;

public class ConsoleController extends BaseController {

	JobsManagerService jobsManagerService = (JobsManagerService) this.getServiceById("jobsManagerService");

	private String jobUID;

	private boolean running;

	private CrawlJob currentJob;

	private int completedBar;

	private int queuedBar;
	
	private int pending;
	
	private int completed;
	
	private Long id;
	
	private boolean extract;

	private boolean crawling;
	private boolean resume;
	private boolean checkpointing;
	

	public boolean isCheckpointing() {
		return checkpointing;
	}

	public void setCheckpointing(boolean checkpointing) {
		this.checkpointing = checkpointing;
	}

	public boolean isResume() {
		return resume;
	}

	public void setResume(boolean resume) {
		this.resume = resume;
	}

	public boolean isCrawling() {
		return crawling;
	}

	public void setCrawling(boolean crawling) {
		this.crawling = crawling;
	}

	public int getCompletedBar() {
		return completedBar;
	}

	public void setCompletedBar(int completedBar) {
		this.completedBar = completedBar;
	}

	public int getQueuedBar() {
		return queuedBar;
	}

	public void setQueuedBar(int queuedBar) {
		this.queuedBar = queuedBar;
	}


	public String getJobUID() {
		return jobUID;
	}

	public void setJobUID(String jobUID) {
		this.jobUID = jobUID;
	}

	public String execute() throws Exception {
		/*
		 * File file = new
		 * File(ServletActionContext.getServletContext().getRealPath("/crawl/jobs/crawl/order.xml"));
		 * XMLSettingsHandler xmlSetting = new XMLSettingsHandler(file);
		 * xmlSetting.initialize(); CrawlOrder order = xmlSetting.getOrder();
		 * CrawlController crawl = new CrawlController();
		 * crawl.initialize(xmlSetting); crawl.requestCrawlStart();
		 */
		CrawlJobHandler handler = jobsManagerService.getJobHandler();
		setRunning(handler.isRunning());
		setCurrentJob(handler.getCurrentJob());
		if(getCurrentJob() != null)
		{
			setCompletedBar((int)(getRatio()/2));
			setQueuedBar((int) ((100-getRatio())/2));
			setCheckpointing(getCurrentJob().isCheckpointing());
		}
		setPending(handler.getPendingJobs().size());
		setCompleted(handler.getCompletedJobs().size());
		setCrawling(handler.isCrawling());
		 if (handler.getCurrentJob() != null && (handler.getCurrentJob().getStatus().equals(CrawlJob.STATUS_PAUSED) ||handler.getCurrentJob().getStatus().equals(CrawlJob.STATUS_WAITING_FOR_PAUSE)))
			 setResume(true);
		 
		return "index";

	}

	/**
	 * 启动抓取任务
	 * @return
	 * @throws Exception
	 */
	public String startJobs() throws Exception {
		CrawlJobHandler handler = jobsManagerService.getJobHandler();
		if (handler != null) {
			CrawlJob job = handler.getCurrentJob();
			if (job != null) {
				CrawlController controller = job.getController();
				if (controller != null) {
					controller.installThreadContextSettingsHandler();
				}
			}
			handler.startCrawler();
			setRunning(handler.isRunning());
		}
		return execute();
	}
	/**
	 * 唤醒当前抓取任务
	 * @return
	 * @throws Exception
	 */
	public String ResumeJob() throws Exception {
		CrawlJobHandler handler = jobsManagerService.getJobHandler();
			handler.resumeJob();
		return execute();
	}
	/**
	 * 暂停抓取
	 * @return
	 * @throws Exception
	 */
	public String PauseJob() throws Exception {
		CrawlJobHandler handler = jobsManagerService.getJobHandler();
			handler.pauseJob();
		return execute();
	}
	/**
	 * 强行终止当前抓取任务
	 * @return
	 * @throws Exception
	 */
	public String TerminateJob() throws Exception {
		CrawlJobHandler handler = jobsManagerService.getJobHandler();
			if(handler.getCurrentJob()!=null){
                handler.deleteJob(handler.getCurrentJob().getUID());
            }
		return execute();
	}
	
	public String tabxml() throws Exception {
		return "xml";
	}

	public String tabbar() throws Exception {
		return "tabbar";
	}
	/**
	 * 停止抓取任务
	 * @return
	 * @throws Exception
	 */
	public String stopJobs() throws Exception {
		CrawlJobHandler handler = jobsManagerService.getJobHandler();
		if (handler != null) {
			handler.stopCrawler();
		}
		setRunning(handler.isRunning());
		return execute();
	}
	/**
	 * 移除抓取任务
	 * @return
	 * @throws Exception
	 */
	public String deleteJob() throws Exception {
		CrawlJobHandler handler = jobsManagerService.getJobHandler();
		if (handler != null) {
			handler.deleteJob(jobUID);
		}
		return execute();
	}

	/**
	 * 获取抓取百分比例
	 * @return
	 * @throws Exception
	 */
	public int getRatio() throws Exception {
		long begin, end;
		StatisticsTracker stats = (StatisticsTracker) getCurrentJob()
				.getStatisticsTracking();
		if (stats != null) {
			begin = stats.successfullyFetchedCount();
			end = stats.totalCount();
			if (end < 1) {
				end = 1;
			}
		} else {
			begin = 0;
			end = 1;
		}
		if (getCurrentJob() != null) {
			final long timeElapsed, timeRemain;
			if (stats == null) {
				timeElapsed = 0;
				timeRemain = -1;
			} else {
				timeElapsed = (stats.getCrawlerTotalElapsedTime());
				if (begin == 0) {
					timeRemain = -1;
				} else {
					timeRemain = ((long) (timeElapsed * end / (double) begin))- timeElapsed;
				}
			}
		}
		int ratio = (int) (100 * begin / end);
		return ratio;
	}

	public boolean getRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public CrawlJob getCurrentJob() {
		return currentJob;
	}

	public void setCurrentJob(CrawlJob currentJob) {
		this.currentJob = currentJob;
	}

	public int getCompleted() {
		return completed;
	}

	public void setCompleted(int completed) {
		this.completed = completed;
	}

	public int getPending() {
		return pending;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isExtract() {
		return extract;
	}

	public void setExtract(boolean extract) {
		this.extract = extract;
	}
}
