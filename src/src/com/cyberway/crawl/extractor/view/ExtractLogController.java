package com.cyberway.crawl.extractor.view;

import java.io.File;
import java.util.List;

import org.dom4j.Document;

import com.cyberway.cms.form.service.CoreFormService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.crawl.extractor.ExtractorTemplate;
import com.cyberway.crawl.extractor.domain.ExtractLog;
import com.cyberway.crawl.extractor.service.ExtractLogManagerService;
import com.cyberway.crawl.extractor.service.ExtractManagerService;
import com.cyberway.crawl.jobs.object.Jobs;
import com.cyberway.crawl.jobs.service.JobsManagerService;
import com.cyberway.issue.crawler.admin.CrawlJobHandler;

public class ExtractLogController extends BaseBizController<ExtractLog>{
	ExtractLogManagerService service = (ExtractLogManagerService) this.getServiceById("extractLogManagerService");
	JobsManagerService jobsManagerService = (JobsManagerService) this.getServiceById("jobsManagerService");
	CoreFormService coreFormService=(CoreFormService)this.getServiceById("coreFormService");
	ExtractManagerService extractManagerService = (ExtractManagerService) this.getServiceById("extractManagerService");
	@Override
	protected EntityDao getService() {
		return service;
	}
	private Long jobId;
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	/**
	 * 删除已经提取的数据，只针对提取历史记录，而并不删除已经保存到Document中的数据
	 */
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
	
	/**
	 * 返回当前抓取任务中提取的数据列表
	 */
	public String list() throws Exception{
		CriteriaSetup criteria = new CriteriaSetup();
		if(id!= null && id >0)
		criteria.addFilter("job.oid", id);
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}
	
	/**
	 * 执行提取任务并将数据插入指定的栏目中
	 * @return
	 * @throws Exception
	 */
	public String extractContent() throws Exception
	{
		if(id != null && id >0)
		{
		Document xmlDOC = extractManagerService.createExtractXML(id);
		ExtractorTemplate ex = new ExtractorTemplate();
		CrawlJobHandler handler = jobsManagerService.getJobHandler();
		Jobs job =jobsManagerService.get(id);
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
		}
		//ex.initialize(xmlDOC, outputPath, mirrorDir, imageDir)
		return list();
	}
	

}
