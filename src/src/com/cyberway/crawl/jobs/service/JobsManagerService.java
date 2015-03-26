package com.cyberway.crawl.jobs.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import ognl.Ognl;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.Element;

import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.form.domain.CoreFormField;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.cms.form.service.CoreFormFieldService;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.crawl.extractor.domain.ExtractLog;
import com.cyberway.crawl.extractor.service.ExtractLogManagerService;
import com.cyberway.crawl.jobs.object.Jobs;
import com.cyberway.crawl.util.SettingHandler;
import com.cyberway.issue.crawler.Heritrix;
import com.cyberway.issue.crawler.admin.CrawlJobHandler;

public class JobsManagerService extends HibernateEntityDao<Jobs> {

	/**
	 * 获取抓取任务
	 * @return
	 * @throws Exception
	 */
	public CrawlJobHandler getJobHandler() throws Exception {
		CrawlJobHandler handler =(CrawlJobHandler)ServletActionContext.getServletContext().getAttribute("handler");
		if(handler == null)
		{
		Heritrix heritrix = null;
		if (Heritrix.isSingleInstance()) {
			heritrix = Heritrix.getSingleInstance();
			File file = heritrix.getJobsdir();
			handler = heritrix.getJobHandler();
			ServletActionContext.getServletContext().setAttribute("handler", handler);
		}
		}
		return handler;
	}
	/**
	 * 移除抓取任务
	 */
	 public void removeByIds(List<Long> ids){
		 try{
		 JobsManagerService jobsManagerService = (JobsManagerService)ServiceLocator.getBean("jobsManagerService");
		 CrawlJobHandler handler = jobsManagerService.getJobHandler();
	    	if(ids!=null){
	    		for (Long id : ids)
	    		{
	    			Jobs job = get(id);
	    			String jobUID = job.getJobUID();
	    			removeById(id);
	    		if (handler != null) {
	    			handler.deleteJob(jobUID);
	    		}
	    		}
	    	}
		 }catch(Exception e){e.printStackTrace();}
	    }
	
	 /**
	  * 用于改变当前JOB的状态，在Crawl包中的CrawlJob中调用。
	  * @param UID
	  * @param status
	  */
	public void updateStatusByJobUID(String UID,String status)
	{
		List<Jobs> jobs = this.findBy("jobUID", UID);
		if(jobs != null && jobs.size() >0 )
		{
			Jobs job = jobs.get(0);
			if(job !=null)
			{
			job.setStatus(status);
			saveOrUpdate(job);
			}
		}
	}
	
	
	/**
	 * 读取已提取的XML文件，将数据填充到数据库中。
	 * @param file
	 * @param loginer
	 * @throws Exception
	 */
  public void traverse(Document document) throws Exception {
	/*FileInputStream inputStream = new FileInputStream(file.getAbsoluteFile());
	SAXReader reader = new SAXReader();
	Document document = reader.read(inputStream);*/
	//CoreFormService formService = (CoreFormService)ServiceLocator.getBean("coreFormService");
	Loginer loginer = (Loginer)ServletActionContext.getContext().getSession().get(Loginer.LOGININFO_SESSION);
	CoreFormFieldService formFieldService = (CoreFormFieldService)ServiceLocator.getBean("coreFormFieldService");
	DocumentCommonService documentCommonService = (DocumentCommonService) ServiceLocator.getBean("documentCommonService");
	JobsManagerService jobsManagerService = (JobsManagerService)ServiceLocator.getBean("jobsManagerService");
	ExtractLogManagerService extractLogManagerService = (ExtractLogManagerService)ServiceLocator.getBean("extractLogManagerService");
	Element root = document.getRootElement();
	Long jobId = Long.parseLong(root.element(SettingHandler.XML_ELEMENT_META).element(SettingHandler.XML_ELEMENT_JOBID).getText());
	Channel channel = jobsManagerService.get(jobId).getChannel();
	CmsSite site = CmsSite.class.newInstance();
	  site.setOid(channel.getSite().getOid());
	Long channelId =  channel.getId();
	Iterable<Element> crawlDatas = root.elements(SettingHandler.XML_ELEMENT_DATAS);
	for(Element crawlData : crawlDatas)
	{  BaseDocument doc = (BaseDocument)documentCommonService.getFormClass(channelId).newInstance();
	  Ognl.setValue("id", doc, documentCommonService.getSequence());	  
	  Ognl.setValue("site", doc, site);
	  Ognl.setValue("channel", doc, channel);
	  Ognl.setValue("authorId", doc, loginer.getUserid());
	  Ognl.setValue("authorCname", doc, loginer.getUsername());
	  ExtractLog extractLog = null;
	  Iterable<Element> datas = (Iterable)crawlData.elements(SettingHandler.XML_ELEMENT_DATA);
		for(Element data : datas)
		{
			CoreFormField formField = formFieldService.get(Long.parseLong(data.attributeValue(SettingHandler.XML_ATTRIBUTE_FIELD)));
			Ognl.setValue(formField.getFieldCode(), doc,parseTypeByClass(formField.getFieldType(),data.getText()));	
			boolean keyField = Boolean.parseBoolean(data.attributeValue(SettingHandler.XML_ELEMENT_KEYFIELD));
			if(keyField)
			{
				extractLog = ExtractLog.class.newInstance();
				Ognl.setValue("keyField", extractLog, data.getText());
				Ognl.setValue("channelId", extractLog, String.valueOf(channelId));
				Jobs job = Jobs.class.newInstance();
				job.setOid(jobId);
				Ognl.setValue("job", extractLog, job);
				Ognl.setValue("channelName", extractLog, channel.getName());
				Ognl.setValue("author", extractLog, loginer.getUsername());
				Ognl.setValue("createTime", extractLog, new Date(System.currentTimeMillis()));
				
			}
		}
		if(extractLog != null)
		{
			Boolean unique = extractLogManagerService.isNotUnique(extractLog, "keyField,channelId");
			if(!unique)
			{
			documentCommonService.saveOrUpdate(doc);
		    extractLogManagerService.saveOrUpdate(extractLog);
			}
		}
	}
  }

  /**
   * 匹配数据类型并进行转换
   * @param fieldType
   * @param value
   * @return
   * @throws Exception
   */
  public Object parseTypeByClass(String fieldType,String value) throws Exception
  {
	  if(Class.forName(fieldType).equals(Long.class))
	  {
		  return (Object)Long.parseLong(value);
	  }
	  else if(Class.forName(fieldType).equals(int.class))
	  {
		  return (Object)Integer.parseInt(value);
	  }
	  else if(Class.forName(fieldType).equals(Date.class))
	  {
		  return (Object)Date.parse(value);
	  }
	  else if (Class.forName(fieldType).equals(Float.class))
	  {
		  return (Object)Float.parseFloat(value);
	  }
	  else
		  return value;
  }
  public static void main(String[] args) throws Exception
  {
	  JobsManagerService ma = new JobsManagerService();
	  System.out.println(ma.parseTypeByClass("java.lang.Long", "123").toString());;
  }
  
}

