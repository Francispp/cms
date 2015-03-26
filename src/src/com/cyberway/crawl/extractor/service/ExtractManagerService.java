package com.cyberway.crawl.extractor.service;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cyberway.cms.form.domain.CoreFormField;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.BeanUtils;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.crawl.extractor.domain.Extract;
import com.cyberway.crawl.jobs.object.Jobs;
import com.cyberway.crawl.jobs.service.JobsManagerService;
import com.cyberway.crawl.regular.domain.Regular;
import com.cyberway.crawl.util.SettingHandler;

public class ExtractManagerService extends HibernateEntityDao<Extract>{
	
	/**
	 * 创建提取条件的 Document对象
	 * @param jobId
	 * @return
	 */
	public Document createExtractXML(Long jobId)
	{
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(SettingHandler.XML_ELEMENT_EXTRACT);
		Element meta = root.addElement(SettingHandler.XML_ELEMENT_META);
		Element jobID = meta.addElement(SettingHandler.XML_ELEMENT_JOBID);
		jobID.addText(jobId.toString());
		Element jobUID = meta.addElement(SettingHandler.XML_ELEMENT_JOBUID);
		JobsManagerService jobsManagerService = (JobsManagerService)ServiceLocator.getBean("jobsManagerService");
		Jobs job = jobsManagerService.get(jobId);
		if(job != null)
		jobUID.addText(job.getJobUID());
		Element filters = root.addElement(SettingHandler.XML_ELEMENT_FILTERS);
		for(Extract extract : getExtractsByJobId(jobId))
		{
			Element filter = filters.addElement(SettingHandler.XML_ELEMENT_FILTER);
			Element tagName = filter.addElement(SettingHandler.XML_ELEMENT_TAGNAME);
			tagName.setText(extract.getTagName());
			Element attribute = filter.addElement(SettingHandler.XML_ELEMENT_ATTRIBUTE);
			attribute.addAttribute(SettingHandler.XML_ATTRIBUTE_NAME, extract.getAttributeName());
			attribute.setText(extract.getAttributeValue());
			Element machs = filter.addElement(SettingHandler.XML_ELEMENT_MATCHS);
			Element mach = machs.addElement(SettingHandler.XML_ELEMENT_MATCH);
			mach.addAttribute(SettingHandler.XML_ATTRIBUTE_FIELD, extract.getFormField().getOid().toString());
			mach.addAttribute(SettingHandler.XML_ATTRIBUTE_FIELDTYPE, extract.getFieldType());
			Extract keyExtract = job.getKeyFiledExtract();
			if(keyExtract != null && keyExtract.equals(extract))
				mach.addAttribute(SettingHandler.XML_ATTRIBUTE_KEYFIELD, SettingHandler.TRUE);
			else
				mach.addAttribute(SettingHandler.XML_ATTRIBUTE_KEYFIELD, SettingHandler.FALSE);
			mach.addCDATA(extract.getRegular().getContent());
			//mach.setText("<![CDATA["+extract.getRegular().getContent()+"]]>");
		}
		return document;
	}
	/**
	 * 通过JobId获取工作任务列表
	 * @param JobId
	 * @return
	 */
	public List<Extract> getExtractsByJobId(Long JobId)
	{
		List<Extract> list = findBy("job.oid", JobId);
		
		return list;
	}
	/**
	 * 通过jobId获取Jobs对象
	 * @param jobId
	 * @return
	 */
	public List<Jobs> getJobs(Long jobId){
		List<Jobs> list =null;
		if(jobId != null && jobId>0)
		 list = find(" from Jobs where oid=? ", new Object[]{jobId});
		else
			list = find(" from Jobs", new Object[]{});	
		
		return list;
	}
	/**
	 * Ajax保存数据
	 */
	public boolean saveByAjax(Map<String, Object> data){

		boolean result = true;

		try{
			Extract entity = buildEntity(data);			
			BeanUtils.forceSetProperty(entity, data);
            if(data.containsKey("job.oid")){
            	String jobid=(String)data.get("job.oid");
            	if(jobid!=null&&jobid.length()>0){
            		Jobs job = new Jobs();
            		job.setOid(new Long(jobid));
            		entity.setJob(job);
            	}
            }
            if(data.containsKey("formField.oid")){
            	String fieldId=(String)data.get("formField.oid");
            	if(fieldId!=null&& fieldId.length()>0){
            		CoreFormField formField = new CoreFormField();
            		formField.setOid(new Long(fieldId));
            		entity.setFormField(formField);
            	}
            }
            if(data.containsKey("regular.oid")){
            	String regularId=(String)data.get("regular.oid");
            	if(regularId!=null&& regularId.length()>0){
            		Regular regular = new Regular();
            		regular.setOid(new Long(regularId));
            		entity.setRegular(regular);
            	}
            }
			if (result && entity !=null) {
				this.save(entity);
				this.flush();
				
				// 取主键值
				String pkidName = this.getIdName(this.entityClass);
				Object pkid = BeanUtils.getProperty(entity, pkidName);
				data.put(pkidName, pkid);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			result = false;		
			
		}
		
		return result;
	}

}
