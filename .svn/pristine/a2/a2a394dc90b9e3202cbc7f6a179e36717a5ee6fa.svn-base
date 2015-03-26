package com.cyberway.crawl.jobs.view;

import java.util.List;

import ognl.Ognl;

import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.crawl.jobs.object.Jobs;
import com.cyberway.crawl.jobs.service.JobsManagerService;
import com.cyberway.issue.crawler.admin.CrawlJob;
import com.cyberway.issue.crawler.admin.CrawlJobHandler;
import com.cyberway.issue.crawler.datamodel.CrawlOrder;
import com.cyberway.issue.crawler.settings.CrawlerSettings;
import com.cyberway.issue.crawler.settings.XMLSettingsHandler;
import com.cyberway.issue.crawler.util.LogReader;

public class JobsController extends BaseBizController<Jobs> {
	JobsManagerService service = (JobsManagerService) this.getServiceById("jobsManagerService");
	
	@Override
	protected EntityDao getService() {
		return service;
	}

	private int pending;
	
	private int completed;
	private List<CrawlJob> jobs;

	private List<CrawlJob> pendingJobs;

	private List<CrawlJob> completedJobs;

	private String jobUID;

	private String orderXml;
	private CrawlJob theJob;
	
	private CrawlerSettings orderfile;
	
	private String orderfileObject;

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
	public String getOrderfileObject() {
		return orderfileObject;
	}
	public void setOrderfileObject(String orderfileObject) {
		this.orderfileObject = orderfileObject;
	}
	public CrawlerSettings getOrderfile() {
		return orderfile;
	}
	public void setOrderfile(CrawlerSettings orderfile) {
		this.orderfile = orderfile;
	}
	public CrawlJob getTheJob() {
		return theJob;
	}
	public void setTheJob(CrawlJob theJob) {
		this.theJob = theJob;
	}
	public String execute() throws Exception {
		doListEntity(new CriteriaSetup(), getTableId(), Page.DEFAULT_PAGE_SIZE);
		/*CrawlJobHandler handler = service.getJobHandler();
		setJobs(handler.getProfiles());
		setPendingJobs(handler.getPendingJobs());
		setCompletedJobs(handler.getCompletedJobs());
		setPending(handler.getPendingJobs().size());
		setCompleted(getCompletedJobs().size());*/
		return LIST_RESULT;

	}
	public String delete() throws Exception{
		if(!StringUtil.isEmpty(keys)){
			List list=StringUtil.splitToList(keys,",");
			this.logger.info("delete list size:"+list.size());
			getService().removeByIds(list);
			this.addActionMessage(this.getText("RESOURCE.HINTINFO.DELETESUCCESS"));
			
		}else
			this.addActionError(this.getText("RESOURCE.HINTINFO.LETSELECTDELETERECORDWORD"));
		return list();
	}
	public String list() throws Exception{
		doListEntity(new CriteriaSetup(), getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}
    /**
     * 获取当前JOB的order.xml
     * @return
     * @throws Exception
     */
	public String viewOrder() throws Exception {
		CrawlJobHandler handler = service.getJobHandler();
		CrawlJob job = handler.getJob(jobUID);
		setOrderXml(job != null ? LogReader.get(((XMLSettingsHandler) job.getSettingsHandler()).getOrderFile().getAbsolutePath()) : "");
		return "view_order";
	}
	/**
	 * 编辑当前抓取工作任务
	 */
	public String edit()throws Exception{
		CrawlJobHandler handler =  service.getJobHandler();
		if(id!=null){
			get();
			CrawlJob theJob = handler.getJob(domain.getJobUID());
			setJobUID(theJob.getUID());
			XMLSettingsHandler settingsHandler = theJob.getSettingsHandler();
		    setOrderfile(settingsHandler.getSettingsObject(null));
		}else{
			domain=getDomainClass().newInstance();
			CrawlJob theJob = handler.getDefaultProfile();
			setJobUID(theJob.getUID());
			XMLSettingsHandler settingsHandler = theJob.getSettingsHandler();
		    setOrderfile(settingsHandler.getSettingsObject(null));
		    Ognl.setValue("jobUID", domain, theJob.getUID());
		    Ognl.setValue("metaName", domain, getOrderfile().getName());
		    Ognl.setValue("jobDescription", domain, getOrderfile().getDescription());
		}			
		return EDIT_RESULT;
	}
	/**
	 * 从现有模板中新建抓取任务
	 * @return
	 * @throws Exception
	 */
	public String newJob() throws Exception {
		CrawlJobHandler handler =  service.getJobHandler();
		CrawlJob theJob = handler.getDefaultProfile();//获取Default模板
		setJobUID(theJob.getUID());
		XMLSettingsHandler settingsHandler = theJob.getSettingsHandler();
	    CrawlOrder crawlOrder = settingsHandler.getOrder();
	    this.setOrderfile(settingsHandler.getSettingsObject(null));
	    domain = new Jobs();
	    Ognl.setValue("jobUID", domain, theJob.getUID());
	    Ognl.setValue("metaName", domain, getOrderfile().getName());
	    Ognl.setValue("jobDescription", domain, getOrderfile().getDescription());
		
	    
		return "new_job";
	}
	/**
	 * 创建抓取任务的基础Order.xml文件和记录当前任务记录
	 * @return
	 * @throws Exception
	 */
	public String modules() throws Exception {
		CrawlJob theJob;
		CrawlJobHandler handler =  service.getJobHandler();
		if(domain.getJobUID() == null || domain.getJobUID().length()==0 || domain.getJobUID().equals("default"))
		{
			theJob = handler.getDefaultProfile();
			CrawlJob newJob = handler.newJob(theJob, domain.getRecovery(),domain.getMetaName(), domain.getJobDescription(),domain.getSeeds(),CrawlJob.PRIORITY_AVERAGE);
			handler.ensureNewJobWritten(newJob, domain.getMetaName(), domain.getJobDescription());
			domain.setJobUID(newJob.getUID());
			setJobUID(newJob.getUID());
			handler.addJob(newJob);
		}
		//theJob = handler.getJob(domain.getJobUID());
		
		service.saveOrUpdate(domain);
		
		return execute();
	}
	/**
	 * 重新加裁抓取任务，启动后相当于重新抓取
	 * @return
	 * @throws Exception
	 */
	public String reStart() throws Exception {
		CrawlJobHandler handler =  service.getJobHandler();
		CrawlJob theJob = handler.getJob(jobUID);
		if(theJob !=null && (!theJob.getStatus().equals(CrawlJob.STATUS_PENDING) || !theJob.isNew()))
		handler.addJob(theJob);
		return "reStart";
	}
	
	/*public String importData() throws Exception
	{
		File file = new File("D:/Project/CMS3/extracts/111.xml");
		FileInputStream inputStream = new FileInputStream(file.getAbsoluteFile());
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		service.traverse(document);
		return "extract_log";
	}*/
	public List<CrawlJob> getJobs() {
		return jobs;
	}

	public void setJobs(List<CrawlJob> jobs) {
		this.jobs = jobs;
	}

	public List<CrawlJob> getCompletedJobs() {
		return completedJobs;
	}

	public void setCompletedJobs(List<CrawlJob> completedJobs) {
		this.completedJobs = completedJobs;
	}

	public List<CrawlJob> getPendingJobs() {
		return pendingJobs;
	}

	public void setPendingJobs(List<CrawlJob> pendingJobs) {
		this.pendingJobs = pendingJobs;
	}

	public String getJobUID() {
		return jobUID;
	}

	public void setJobUID(String jobUID) {
		this.jobUID = jobUID;
	}

	public String getOrderXml() {
		return orderXml;
	}

	public void setOrderXml(String orderXml) {
		this.orderXml = orderXml;
	}

}
