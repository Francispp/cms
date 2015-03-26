package com.cyberway.crawl.extractor.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ecside.util.ServletUtils;

import com.cyberway.cms.form.domain.CoreFormField;
import com.cyberway.cms.form.service.CoreFormService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.crawl.extractor.domain.Extract;
import com.cyberway.crawl.extractor.service.ExtractManagerService;
import com.cyberway.crawl.jobs.object.Jobs;
import com.cyberway.crawl.jobs.service.JobsManagerService;
import com.cyberway.crawl.regular.domain.Regular;
import com.cyberway.crawl.regular.service.RegularManagerService;

public class ExtractController extends BaseBizController<Extract>{
	ExtractManagerService service = (ExtractManagerService) this.getServiceById("extractManagerService");
	JobsManagerService jobsManagerService = (JobsManagerService) this.getServiceById("jobsManagerService");
	RegularManagerService regularManagerService = (RegularManagerService) this.getServiceById("regularManagerService");
	CoreFormService coreFormService=(CoreFormService)this.getServiceById("coreFormService");
	private List<Jobs> jobs;
	private Long jobId;
	private List<CoreFormField> formFields;
	private List<Regular> regulars;
	private Long keyFiledExtractId;
	
	public Long getKeyFiledExtractId() {
		return keyFiledExtractId;
	}

	public void setKeyFiledExtractId(Long keyFiledExtractId) {
		this.keyFiledExtractId = keyFiledExtractId;
	}

	public List<CoreFormField> getFormFields() {
		return formFields;
	}

	public void setFormFields(List<CoreFormField> formFields) {
		this.formFields = formFields;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}


	@Override
	protected EntityDao getService() {
		return service;
	}
	
	/**
	 * 保存提取条件数据
	 */
	public String saveAjax() {
		logger.info("save user ajax......");
		// TODO Auto-generated method stub
		HttpServletRequest request = this.getHttpServletRequest();
		Map map = ServletUtils.getParameterMap(request);
		boolean result = false;
		try{
		 result = getService().saveByAjax(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		this.ajaxSaveInfo(result);
		
		return AJAX;
	}

	/**
	 * 获取当前抓取任务的提取条件
	 */
	public String list() throws Exception{
		CriteriaSetup critera = new CriteriaSetup();
		if(jobId != null && jobId > 0)
		{
		critera.addFilter("job.oid", jobId);
		Jobs job = jobsManagerService.get(jobId);
		if(job.getKeyFiledExtract() != null)
		{
		keyFiledExtractId = job.getKeyFiledExtract().getOid();
		}
		setFormFields(coreFormService.getFieldsByChannelId(job.getChannel().getId()));
		setRegulars(regularManagerService.getAll());
		
		}
		doListEntity(critera, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}
	
	/**
	 * 设置提取条件的关键字段
	 * @return
	 * @throws Exception
	 */
	public String setKeyField() throws Exception
	{
		if(id != null && id >0)
		{
			Extract extract = service.get(id);
			Long jobOid = extract.getJob().getOid();
			Jobs job = jobsManagerService.get(jobOid);
			job.setKeyFiledExtract(extract);
			jobsManagerService.saveOrUpdate(job);
		}
		return list();
	}
	public String extractInfo() throws Exception
	{
		return "extract_info";
	}
	/*public String extractContent() throws Exception
	{
		Document xmlDOC = service.createExtractXML(jobId);
		ExtractorTemplate ex = new ExtractorTemplate();
		CrawlJobHandler handler = jobsManagerService.getJobHandler();
		Jobs job =jobsManagerService.get(jobId);
		File mirrorDir = new File(handler.getJob(job.getJobUID()).getDirectory().getAbsolutePath()+"/mirror/");
		if(mirrorDir.isDirectory())
		{
			File outputPath = new File(handler.getJob(job.getJobUID()).getDirectory().getAbsolutePath()+"/extract/");
			if(!outputPath.isDirectory())
				outputPath.mkdir();
			File imageDir = new File(outputPath.getAbsolutePath()+"/image/");
			if(!imageDir.isDirectory())
				imageDir.mkdir();
			ex.initialize(xmlDOC, outputPath.getAbsolutePath(), mirrorDir.getAbsolutePath(), imageDir.getAbsolutePath());
			ex.startExtract();
		}
		//ex.initialize(xmlDOC, outputPath, mirrorDir, imageDir)
		return "extract_info";
	}*/
	public List<Jobs> getJobs() {
		if(jobs == null)
			jobs = service.getJobs(jobId);
		return jobs;
	}

	public void setJobs(List<Jobs> jobs) {
		this.jobs = jobs;
	}

	public List<Regular> getRegulars() {
		return regulars;
	}

	public void setRegulars(List<Regular> regulars) {
		this.regulars = regulars;
	}

}
