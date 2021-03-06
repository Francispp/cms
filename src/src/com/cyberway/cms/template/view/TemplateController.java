package com.cyberway.cms.template.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.Constants;
import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.Template;
import com.cyberway.cms.form.service.CoreFormService;
import com.cyberway.cms.permission.service.CmsPermissionService;
import com.cyberway.cms.permission.service.CmsResourceService;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.cms.template.TemplateConverter;
import com.cyberway.cms.template.TemplateParser;
import com.cyberway.cms.template.service.TemplateGatherService;
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.cms.template.service.TemplateService;
import com.cyberway.cms.webservice.service.CacheSynchroismService;
import com.cyberway.cms.webservice.service.HtmlSynchroismService;
import com.cyberway.common.fileDownload.BlobFileObject;
import com.cyberway.common.service.DynamicPageService;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.exception.BizException;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class TemplateController extends BaseBizController<Template>
{

	

	public final static String ASK_RESULT = "ask";

	private String _previewUrl;

	private Long _channelId;

	private Integer _templateType;

	private Long _siteId;

	private Long _formTemplateId;

	private Long _defaultTemplateId;
    private File[] _file;
    private String[] _fileContentType;
    private String[] _fileFileName;
	private Map<Long,String> forms;
	private Map<String,String> formFields;
	private Map <String,String> resources;
	
	private Integer ispublished;
	
	private Boolean isPublishStatic;
	
	private String isCloseWindow=new String();
	private HtmlSynchroismService htmlSynchroismService = (HtmlSynchroismService) ServiceLocator.getBean("htmlSynchroismService");
	
	//WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued" list="#{true:'是',false:'否'}" />
	//改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
	private Map<Boolean, String> trueOfFalseMap1 = null;
	
	public Map<Boolean, String> getTrueOfFalseMap1() {
		if(trueOfFalseMap1 != null){
			return trueOfFalseMap1;
		}else{
			trueOfFalseMap1 = new HashMap<Boolean, String>();
			trueOfFalseMap1.put(new Boolean(true), "是");
			trueOfFalseMap1.put(new Boolean(false), "否");
			return trueOfFalseMap1;
		}
    }

	public Integer getIspublished() {
    	return ispublished;
    }

	public void setIspublished(Integer ispublished) {
    	this.ispublished = ispublished;
    }

	public TemplateController()
	{
		setDefaultResult(LIST_RESULT);
	}

	public String getPreviewUrl()
	{
		return _previewUrl;
	}

	public void setPreviewUrl(String previewUrl)
	{
		_previewUrl = previewUrl;
	}

	public Long getChannelId()
	{
		return _channelId;
	}

	public void setChannelId(Long channelId)
	{
		_channelId = channelId;
	}

	public Long getSiteId()
	{
		return _siteId;
	}

	public void setSiteId(Long siteId)
	{
		_siteId = siteId;
	}

	public Integer getTemplateType()
	{
		return _templateType;
	}
	
	public void setTemplateType(Integer templateType)
	{
		_templateType = templateType;
	}

	public Long getFormTemplateId()
	{
		return _formTemplateId;
	}

	public void setFormTemplateId(Long formTemplateId)
	{
		_formTemplateId = formTemplateId;
	}

	public SiteManagerService getSiteManagerService()
	{
		return (SiteManagerService) getServiceById("siteManagerService");
	}

	public ChannelManagerService getChannelManagerService()
	{
		return (ChannelManagerService) getServiceById("channelManagerService");
	}

	public DynamicPageService getDynamicPageService()
	{
		return (DynamicPageService) getServiceById("dynamicPageService");
	}
	
	public TemplateParser getTemplateParser ()
	{
		return (TemplateParser)getServiceById("template.parser.default");
	}

	@Override
	protected TemplateManagerService getService()
	{
		return (TemplateManagerService) getServiceById("templateManagerService");
	}

	protected TemplateService getTemplateService()
	{
		return (TemplateService) getServiceById("templateService");
	}
	
	protected Map<Integer, String> getTemplateApplies ()
	{
		return (Map<Integer, String>)getServiceById("template.applies");
	}

	@Override
	public String list() throws Exception
	{
		CriteriaSetup criteria = new CriteriaSetup();
		CmsPermissionService permService=(CmsPermissionService)this.getServiceById("cmsPermissionService");
		if (getLoginerSiteId() != null)
		{
			//检测是否有权限
			if(!permService.haveThePermission(getLoginer(), "CMS_TEMPLATE_MANAGER",1, getLoginerSiteId()))
				throw new Exception(getText("RESOURCE.HINTINFO.NOPERMISSION"));  
			CmsSite site = getSiteManagerService().getSiteFromCache(getLoginerSiteId());
			setIspublished(site.getIspublished());
			criteria.addFilter("site_id", getLoginerSiteId());
			// 获得默认站点首页模板
			if (getTemplateType() != null
					&& getTemplateType().intValue() == Template.TYPE_INDEX 
					&& site.getTemplate()!=null)
				_defaultTemplateId = site.getTemplate().getId();
			else if(getTemplateType() != null
					&& getTemplateType().intValue() == Template.TYPE_INDEX_WAP
					&& site.getTemplateWap()!=null)
				_defaultTemplateId = site.getTemplateWap().getId();
		}
		if (getChannelId() != null)
		{
			//检测是否有权限
			if(!permService.haveThePermission(getLoginer(), "CMS_TEMPLATE_MANAGER",2, getChannelId()))
				throw new Exception(getText("RESOURCE.HINTINFO.NOPERMISSION")); 
			Channel channel = getChannelManagerService().getChannelFromCache(getChannelId());
			setIspublished(channel.getSite().getIspublished());
			criteria.setInCriterion(Restrictions.eq("channel_id", getChannelId()));
			if (getTemplateType() != null)
			{
				// 获得指定类型的默认模板ID
				if (getTemplateType().intValue() == Template.TYPE_FORM
						&& channel.getFormTemplate() != null)
					_defaultTemplateId = channel.getFormTemplate().getId();
				else if (getTemplateType().intValue() == Template.TYPE_DETAILS
						&& channel.getDetailsTemplate() != null)
					_defaultTemplateId = channel.getDetailsTemplate().getId();
				else if (getTemplateType().intValue() == Template.TYPE_SUMMARY
						&& channel.getSummaryTemplate() != null)
					_defaultTemplateId = channel.getSummaryTemplate().getId();
				else if (getTemplateType().intValue() == Template.TYPE_DETAILS_WAP
						&& channel.getDetailsTemplateWap() != null)
					_defaultTemplateId = channel.getDetailsTemplateWap().getId();
				else if (getTemplateType().intValue() == Template.TYPE_SUMMARY_WAP
						&& channel.getSummaryTemplateWap() != null)
					_defaultTemplateId = channel.getSummaryTemplateWap().getId();
				else if (getTemplateType().intValue() == Template.TYPE_ADMIN_SUMMARY
						&& channel.getAdminSummaryTemplate() != null)
					_defaultTemplateId = channel.getAdminSummaryTemplate()
							.getId();
			}
		}
		if (getTemplateType() != null)
		{
			criteria.addFilter("type", getTemplateType());
		}
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		
		return LIST_RESULT;
	}
	
	@Override
	public String delete() throws Exception {
		for (Long id : StringUtil.splitToList(keys, ",")) {
			Template template = getService().get(id);
			if (template != null) {
				if (template.getType() > 0 && template.getType() < 5) {// 与表单相关的模板
					Channel channel = getChannelManagerService().get(template.getChannel_id());
					if (ObjectUtils.equals(template.getType(), Template.TYPE_ADMIN_SUMMARY)
					        && (channel.getAdminSummaryTemplate() != null && ObjectUtils.equals(channel.getAdminSummaryTemplate().getId(),
					                template.getId()))) {
						channel.setAdminSummaryTemplate(null);

						getChannelManagerService().saveOrUpdate(channel);
					} else if (ObjectUtils.equals(template.getType(), Template.TYPE_DETAILS)
					        && (channel.getDetailsTemplate() != null && ObjectUtils.equals(channel.getDetailsTemplate().getId(),
					                template.getId()))) {
						channel.setDetailsTemplate(null);

						getChannelManagerService().saveOrUpdate(channel);
					} else if (ObjectUtils.equals(template.getType(), Template.TYPE_FORM)
					        && (channel.getFormTemplate() != null && ObjectUtils
					                .equals(channel.getFormTemplate().getId(), template.getId()))) {
						channel.setFormTemplate(null);

						getChannelManagerService().saveOrUpdate(channel);
					} else if (ObjectUtils.equals(template.getType(), Template.TYPE_SUMMARY)
					        && (channel.getSummaryTemplate() != null && ObjectUtils.equals(channel.getSummaryTemplate().getId(),
					                template.getId()))) {
						channel.setSummaryTemplate(null);

						getChannelManagerService().saveOrUpdate(channel);
					}
				} else if (template.getType() == Template.TYPE_INDEX) {// 首页模板
					CmsSite site = getSiteManagerService().get(template.getSite_id());
					if (ObjectUtils.equals(template.getType(), Template.TYPE_INDEX)
					        && (site.getTemplate() != null && ObjectUtils.equals(site.getTemplate().getId(), template.getId()))) {
						site.setTemplate(null);

						getSiteManagerService().saveOrUpdate(site);
					}
				}
				
				if (template.getType() == Template.TYPE_ANY || template.getType() == Template.TYPE_INDEX) {
					getService().removeTemplate(template.getSite_id(), template.getType(), template.getIsPublishStatic(), template.getId());
				} else {
					getService().removeTemplate(template.getChannel_id(), template.getType(), template.getIsPublishStatic(),
					        template.getId());
				}
				
			} else {
				return execute();
			}
			TemplateGatherService templateGatherService=(TemplateGatherService)getServiceById("templateGatherService");
			templateGatherService.deleteOfTemplate(template.getId());
		}

		return super.delete();
	}

	@Override
	public String edit() throws Exception
	{
		String result = super.edit();

		if (getTemplateType() != null)//指定模板类型
		{
			getDomain().setType(getTemplateType());
		}

		if (getDomain().getId() == null)//新增模板
		{
			 if (getFormTemplateId() != null && getDomain().getType()!=null 
					 &&getDomain().getType().intValue() == Template.TYPE_ADMIN_SUMMARY)//从表单模板中，重新生成概览模板
			 {
				TemplateConverter converter = (TemplateConverter)getServiceById("template.convert.detailsToSummary");
				//表单模板对象
				Template formtemp=getService().get(getFormTemplateId());
				String tempListName=formtemp.getName() + "_List";
				Template tempSum=((TemplateManagerService)getService()).getTemplateByName(formtemp.getSite_id(), tempListName);
				if(tempSum!=null)//当前表单模板对应的概览模板存在，
					this.setDomain(tempSum);
				else{//重新生成模板
				getDomain().setName(tempListName);
				getDomain().setSite_id(formtemp.getSite_id());
				getDomain().setChannel_id(formtemp.getChannel_id());
				getDomain().setType(getTemplateType());
				//根据表单模板，生成内容
				getDomain().setBody(converter.convert(getTemplateParser().parseTemplate(getService().get(getFormTemplateId()).getBody())).getBody());			
				}				
			 }
			 else
			 {
				 getDomain().setBody(getTemplateApplies().get(getDomain().getType()));
			 }
		}else{//修改
			//若为修改，获得当前对象（站点、频道）的默认模板
			if (getSiteId() != null)
			{
				CmsSite site = getSiteManagerService().getSiteFromCache(getSiteId());
				
				// 获得默认站点首页模板
				if (getTemplateType() != null
						&& getTemplateType().intValue() == Template.TYPE_INDEX 
						&& site.getTemplate()!=null)
					_defaultTemplateId = site.getTemplate().getId();
				else if (getTemplateType() != null
						&& getTemplateType().intValue() == Template.TYPE_INDEX_WAP 
						&& site.getTemplateWap()!=null)
					_defaultTemplateId = site.getTemplateWap().getId();
			}
			if (getChannelId() != null)
			{
				Channel channel = getChannelManagerService().getChannelFromCache(getChannelId());				
				if (getTemplateType() != null)
				{
					// 获得指定类型的默认模板ID
					if (getTemplateType().intValue() == Template.TYPE_FORM
							&& channel.getFormTemplate() != null)
						_defaultTemplateId = channel.getFormTemplate().getId();
					else if (getTemplateType().intValue() == Template.TYPE_DETAILS
							&& channel.getDetailsTemplate() != null)
						_defaultTemplateId = channel.getDetailsTemplate().getId();
					else if (getTemplateType().intValue() == Template.TYPE_SUMMARY
							&& channel.getSummaryTemplate() != null)
						_defaultTemplateId = channel.getSummaryTemplate().getId();
					else if (getTemplateType().intValue() == Template.TYPE_DETAILS_WAP
							&& channel.getDetailsTemplateWap() != null)
						_defaultTemplateId = channel.getDetailsTemplateWap().getId();
					else if (getTemplateType().intValue() == Template.TYPE_SUMMARY_WAP
							&& channel.getSummaryTemplateWap() != null)
						_defaultTemplateId = channel.getSummaryTemplateWap().getId();
					else if (getTemplateType().intValue() == Template.TYPE_ADMIN_SUMMARY
							&& channel.getAdminSummaryTemplate() != null)
						_defaultTemplateId = channel.getAdminSummaryTemplate()
								.getId();
				}
			}			
		}

		return result;
	}

	public String setDefault() throws Exception
	{
		if(id!=null)//不为空，指定对象的默认模板
		   get();
		else//若为空，取消指定对象的默认模板
			domain=null;

		
		if (getChannelId() != null)
		{
			Channel channel = getChannelManagerService().getChannelFromCache(getChannelId());
			if (ObjectUtils.equals(_templateType, Template.TYPE_FORM))
			{
				channel.setFormTemplate(getDomain());
			}
			else if (ObjectUtils.equals(_templateType, Template.TYPE_DETAILS))
			{
				channel.setDetailsTemplate(getDomain());
			}
			else if (ObjectUtils.equals(_templateType, Template.TYPE_SUMMARY))
			{
				channel.setSummaryTemplate(getDomain());
			}
			else if (ObjectUtils.equals(_templateType, Template.TYPE_DETAILS_WAP))
			{
				channel.setDetailsTemplateWap(getDomain());
			}
			else if (ObjectUtils.equals(_templateType, Template.TYPE_SUMMARY_WAP))
			{
				channel.setSummaryTemplateWap(getDomain());
			}
			else if (ObjectUtils.equals(_templateType,
					Template.TYPE_ADMIN_SUMMARY))
			{
				channel.setAdminSummaryTemplate(getDomain());
			}
			getChannelManagerService().saveOrUpdate(channel);
			
			 /*缓存同步*/
			 if(channel.getId()!=null){
				CacheSynchroismService cacheSynchroismService = (CacheSynchroismService) this
				.getServiceById("cacheSynchroismService");
				try{
					cacheSynchroismService.updateChannel(channel.getId(),"");
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			 
		}else if (getSiteId() != null)
		{
			// 设置站点首页模板
			CmsSite site = getSiteManagerService().getSiteFromCache(getSiteId());
			if(ObjectUtils.equals(_templateType, Template.TYPE_INDEX)){
				site.setTemplate(getDomain());
			}else if(ObjectUtils.equals(_templateType, Template.TYPE_INDEX_WAP)){
				site.setTemplateWap(getDomain());
			}
			getSiteManagerService().saveOrUpdate(site);

			/*缓存同步*/
			if(site.getOid()!=null){
				CacheSynchroismService cacheSynchroismService = (CacheSynchroismService) this
				.getServiceById("cacheSynchroismService");
				try{
					cacheSynchroismService.updateSite(site.getOid(),"");
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
			
		}
        
		return list();
	}

	@Override
	public String saveOrUpdate() throws Exception
	{
		String result = EDIT_RESULT;
		if (getChannelId() != null)
		{
			Channel chn=getChannelManagerService().getChannelFromCache(getChannelId());
			setSiteId(chn.getSite().getOid());//设置站点id
			getDomain().setChannel_id(getChannelId());
			
		}
		if (getSiteId() != null)
		{
			getDomain().setSite_id(getSiteId());
		}
		
		boolean isNew = getDomain().getId() == null;
		/*
		if(getDomain().getType() == Template.TYPE_ANY_WAP || getDomain().getType() == Template.TYPE_INDEX_WAP||getDomain().getType() == Template.TYPE_SUMMARY_WAP || getDomain().getType() == Template.TYPE_DETAILS_WAP){
				getDomain().setBody(getDomain().getBody().replace("<html>", "<html xmlns=\"http://www.w3.org/1999/xhtml\">"));
		}
		*/
		if(isNew){
			getDomain().setTimeCreated(new java.util.Date());
		}else{
			 getDomain().setTimeCreated(getService().get(getDomain().getId()).getTimeCreated());
		}
		
		//增加最后修改时间
		getDomain().setLastModified(new java.util.Date());
		try{
			result = super.saveOrUpdate();
			htmlSynchroismService.deleteStaticHtmlByChannelId(domain.getChannel_id());
		}catch(BizException e){
			addActionMessage(e.getMessage());
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		isCloseWindow="close";
		if (getDomain().getType() == Template.TYPE_ANY || getDomain().getType() == Template.TYPE_INDEX||getDomain().getType() == Template.TYPE_ANY_WAP || getDomain().getType() == Template.TYPE_INDEX_WAP) {
			getService().putTemplate(getDomain().getSite_id(), getDomain().getType(), getDomain().getIsPublishStatic(),
			        getDomain().getId(), getDomain());
		} else {
			getService().putTemplate(getDomain().getChannel_id(), getDomain().getType(), getDomain().getIsPublishStatic(),
			        getDomain().getId(), getDomain());
		}

		//设置默认模板
		Channel chn=null;
	     if(getDomain().getChannel_id()!=null)
	    	 chn=getChannelManagerService().getChannelFromCache(getDomain().getChannel_id());
		setDefaultTemplate(getDomain().getType(), chn,getSiteManagerService().getSiteFromCache(getDomain().getSite_id()), getDomain());

		//新增表单模板时，自动生成后台概览模板
		if (isNew && getDomain().getChannel_id() != null && ObjectUtils.equals(getDomain().getType(), Template.TYPE_FORM))
		{
			TemplateConverter converter = (TemplateConverter)getServiceById("template.convert.detailsToSummary");
			Template summaryTemplate = new Template ();
			//检测对应的后台概览模板是否存在
			String templateName_list=getDomain().getName() + "_List";
			Template tempList=((TemplateManagerService)getService()).getTemplateByName(getDomain().getSite_id(), templateName_list);
			if(tempList!=null)
				summaryTemplate=tempList;
			
			summaryTemplate.setType(Template.TYPE_ADMIN_SUMMARY);
			summaryTemplate.setName(getDomain().getName() + "_List");
			summaryTemplate.setBody(converter.convert(getTemplateParser().parseTemplate(getDomain().getBody())).getBody());
			
			summaryTemplate.setChannel_id(getDomain().getChannel_id());
			summaryTemplate.setSite_id(getDomain().getSite_id());
			summaryTemplate.setTimeCreated(new java.util.Date());
			summaryTemplate=getService().saveOrUpdate(summaryTemplate);
			//设置默认模板
			setDefaultTemplate(summaryTemplate.getType(), getChannelManagerService().getChannelFromCache(getDomain().getChannel_id()),getSiteManagerService().getSiteFromCache(getDomain().getSite_id()), summaryTemplate);

		}
		
		return result;
	}
	

	@Override
	public String execute() throws Exception
	{
		/*if (ObjectUtils.equals(LIST_RESULT, getDefaultResult()))
		{
			list();
		}
		CriteriaSetup criteria = new CriteriaSetup();
		List<Criterion> cList = new ArrayList<Criterion>();
		criteria.setAddCriterions(cList);
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);*/
		return list();
	}

	/**
	 * 预览模板
	 * @return
	 * @throws Exception
	 */
	public synchronized String preview() throws Exception
	{
		StringBuffer sb=new StringBuffer();
		sb.append(this.getHttpServletRequest().getContextPath());
		if(id!=null)
		 domain=getService().get(id);
		else 
			throw new Exception("未指定模板Id!");
		if(getDomain().getType()==Template.TYPE_ANY){//通用模板
			sb.append("/cms/index!view.action?preview=1&siteId="+getDomain().getSite_id()+"&templateId="+id);
		}else if(getDomain().getType()==Template.TYPE_INDEX){//首页模板
			sb.append("/cms/index.action?preview=1&siteId="+getDomain().getSite_id()+"&templateId="+id);
		}else if(getDomain().getType()==Template.TYPE_FORM){//表单模板
			sb.append("/cms/document!adminEdit.action?preview=1&channelId="+getDomain().getChannel_id()+"&templateId="+id);
		}else if(getDomain().getType()==Template.TYPE_ADMIN_SUMMARY){//后台概览模板
			sb.append("/cms/document!adminList.action?preview=1&channelId="+getDomain().getChannel_id()+"&templateId="+id);
		}else if(getDomain().getType()==Template.TYPE_DETAILS){//细览模板 
			sb.append("/cms/docInfo!view.action?preview=1&channelId="+getDomain().getChannel_id()+"&templateId="+getDomain().getId());
		}else if(getDomain().getType()==Template.TYPE_SUMMARY){//概览模板
			sb.append("/cms/docInfo!list.action?preview=1&channelId="+getDomain().getChannel_id()+"&templateId="+getDomain().getId());
		}else if(getDomain().getType()==Template.TYPE_ANY_WAP){//通用模板
			sb.append("/cms/index!view.action?preview=1&siteId="+getDomain().getSite_id()+"&templateId="+id);
		}else if(getDomain().getType()==Template.TYPE_INDEX_WAP){//首页模板
			sb.append("/cms/index.action?preview=1&siteId="+getDomain().getSite_id()+"&templateId="+id);
		}else if(getDomain().getType()==Template.TYPE_DETAILS_WAP){//细览模板 
			sb.append("/cms/docInfo!view.action?preview=1&channelId="+getDomain().getChannel_id()+"&templateId="+getDomain().getId());
		}else if(getDomain().getType()==Template.TYPE_SUMMARY_WAP){//概览模板
			sb.append("/cms/docInfo!list.action?preview=1&channelId="+getDomain().getChannel_id()+"&templateId="+getDomain().getId());
		}
		setPreviewUrl(sb.toString());
		return "preview";
	}
/**
 * 导出模板数据
 * @return
 * @throws Exception
 */
	public String exportData() throws Exception
	{
		TemplateManagerService service = (TemplateManagerService) getService();
		items=new ArrayList();
		for (Long id : StringUtil.splitToList(keys,","))
		{
			items.add(service.get(id));
		
		}
		File file;
		if(Constants.IS_REALPATH)
		{
		 file = new File(Constants.ABSOLUTE_PATH+Constants.TEMPLATE_FILE);
		}
		else{
			file = new File(this.getHttpServletRequest().getRealPath(Constants.TEMPLATE_FILE));
		}
		FileOutputStream outputStream = new FileOutputStream(file);
		service.exportToXml(getItems(), outputStream);
		outputStream.close();
		FileInputStream is = new FileInputStream(file);
		byte[] b = new byte[(int) file.length()];
		is.read(b, 0, (int) file.length());
		BlobFileObject bfo = new BlobFileObject();
		bfo.setContent(b);
        is.close();
		bfo.setFullfilename("template.xml");
		this.getHttpServletRequest().setAttribute(Constants.DOWNLOAD_BLOB_FILE_OBJECT, bfo);
		//this.setPreviewUrl("/dynamicPage/template.xml");
		return "export_data";
	}

	/**
	 * 导入模板数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String importData() throws Exception {
		TemplateManagerService service = (TemplateManagerService) getService();
		if (_file != null && _file.length > 0) {
			if (StringUtils.equals(_fileContentType[0], "text/xml")) {
				FileInputStream inputStream = new FileInputStream(_file[0]);
				Channel channel = null;
				if (id != null && id != 0) {
					channel = getChannelManagerService().get(id);
				}
				CmsSite site = null;
				if (getSiteId() != null && getSiteId() != null) {
					site = getSiteManagerService().get(getSiteId());
				} else if (channel != null) {
					site = channel.getSite();
				}
				try{
					service.importFromXml(inputStream, false, channel, site);
				}catch(Exception e){
					inputStream.close();
					addActionMessage("导入出错,选择的xml文件内容不规范!");
					list();
					return "close";
				}
				
				inputStream.close();
			} else {
				addActionMessage("导入出错,必须选择xml文件!");
			}
		}
		list();
		return "close";
	}
	
	/**
	 * @author lan
	 * 若当前没有设置默认模板,则保存模板的同时设置当前template为默认模板
	 * @param type
	 * @param channel
	 * @param site
	 * @param template
	 * @throws Exception
	 */
	 private void setDefaultTemplate(int type,Channel channel,CmsSite site,Template template) throws Exception {
		   
		    switch (type) {
		    case Template.TYPE_FORM:
			      if (channel.getFormTemplate()== null)
			      {
			       channel.setFormTemplate(template);
			       getChannelManagerService().saveOrUpdate(channel);
			      }
			      break;
		    case Template.TYPE_DETAILS:
			      if (channel.getDetailsTemplate() == null)
			      {
			       channel.setDetailsTemplate(template);
			       getChannelManagerService().saveOrUpdate(channel);
			      }
			      break;
		    case Template.TYPE_SUMMARY:
			      if (channel.getSummaryTemplate()== null)
			      {
			       channel.setSummaryTemplate(template);
			       getChannelManagerService().saveOrUpdate(channel);

			      }
			      break;
		    case Template.TYPE_ADMIN_SUMMARY:
			      if (channel.getAdminSummaryTemplate()== null)
			      {
			       channel.setAdminSummaryTemplate(template);
			       getChannelManagerService().saveOrUpdate(channel);

			      }
			      break;
		    case Template.TYPE_INDEX:
			      if (site.getTemplate()== null)
			      {
			       site.setTemplate(template);
			       getSiteManagerService().saveOrUpdate(site);

			      }
			      break;
		    case Template.TYPE_DETAILS_WAP:
			      if (channel.getDetailsTemplateWap() == null)
			      {
			       channel.setDetailsTemplateWap(template);
			       getChannelManagerService().saveOrUpdate(channel);
			      }
			      break;
		    case Template.TYPE_SUMMARY_WAP:
			      if (channel.getSummaryTemplateWap()== null)
			      {
			       channel.setSummaryTemplateWap(template);
			       getChannelManagerService().saveOrUpdate(channel);

			      }
			      break;
		    case Template.TYPE_INDEX_WAP:
			      if (site.getTemplateWap()== null)
			      {
			       site.setTemplateWap(template);
			       getSiteManagerService().saveOrUpdate(site);

			      }
			      break;
		    }
		  }

	/**
	 * 编辑模板公用脚本
	 * @return
	 */
	public String editscript(){
		if(getId()!=null)
			domain=getService().get(getId());
		return "editscript";
	}
	public Long getDefaultTemplateId()
	{
		return _defaultTemplateId;
	}

	public void setDefaultTemplateId(Long defaultTemplateId)
	{
		this._defaultTemplateId = defaultTemplateId;
	}
	public File[] get_file() {
		return _file;
	}
	public void set_file(File[] _file) {
		this._file = _file;
	}
	public String[] get_fileContentType() {
		return _fileContentType;
	}
	public void set_fileContentType(String[] contentType) {
		_fileContentType = contentType;
	}
	public String[] get_fileFileName() {
		return _fileFileName;
	}
	public void set_fileFileName(String[] fileName) {
		_fileFileName = fileName;
	}	
	public Map getForms() {
		if(forms==null){
			CoreFormService formService=(CoreFormService)this.getServiceById("coreFormService");
			forms=formService.getAllForm();
		}
		return forms;
	}

	/**
	 * 获得表单字段
	 * @return
	 */
	public Map<String, String> getFormFields() {
		if(formFields==null){//若为空，自动取模板中对应的表单对象
			CoreFormService formService=(CoreFormService)this.getServiceById("coreFormService");
			if(domain!=null&&domain.getForm()!=null&&domain.getForm().getOid()!=null){
				
				formFields=formService.getFieldsByForm(domain.getForm().getOid());
			}else{
				//新增时，取默认的表单ID
				if(this.getTemplateType()==Template.TYPE_FORM)
				  formFields=formService.getFieldsByForm(Constants.CMS_DEFAULT_TEMPLATEFORMID);
				else{//若为频道下的细览、概览模板，获得当前频道默认的表单字段
				 if(getChannelId()==null && domain!=null)
					 setChannelId(domain.getChannel_id());
				 if(this.getChannelId()!=null&&(getTemplateType()==Template.TYPE_DETAILS||getTemplateType()==Template.TYPE_SUMMARY||getTemplateType()==Template.TYPE_DETAILS_WAP||getTemplateType()==Template.TYPE_SUMMARY_WAP)){
					 Channel chn=getChannelManagerService().getChannelFromCache(getChannelId());
					 if(chn.getFormTemplate()!=null && chn.getFormTemplate().getId()!=null){//频道表单默认模板
					  try{
						  //获得默认的表单模板id
					      Long cformtempid=getChannelManagerService().getDefualutTemplateId(getChannelId(), Template.TYPE_FORM);
					      Long cformid=((TemplateManagerService)getService()).getTemplateFormByTemplateId(cformtempid).getOid();
					      formFields=formService.getFieldsByForm(cformid);
						 }catch(Exception e){
							 formFields=new HashMap(); 
						 }
					 }else//未设置默认表单
						formFields=new HashMap();
				 }
				}
			}
			}		
		return formFields;
	}

	public void setFormFields(Map<String, String> formFields) {
		this.formFields = formFields;
	}

	public Map<String, String> getResources() {
		if(resources==null){//若为空，则取得资源
			CmsResourceService resService=(CmsResourceService)this.getServiceById("cmsResourceService");

			//resources=resService.getResourceByType(2);
			//resources.putAll(resService.getResourceByType(3));
			//安利修改只取文档资料
			resources=resService.getResourceByType(3);
		}
		return resources;
	}

	public void setResources(Map<String, String> resources) {
		this.resources = resources;
	}
	
	/**
	 * 是否静态采集
	 * 
	 * @return
	 * @throws Exception
	 */
	public String setIsPublishStatic() throws Exception {
		Validate.notNull(id);
		Template template = getService().get(id);

		// 先删除缓存
		if (template.getType() == Template.TYPE_ANY || template.getType() == Template.TYPE_INDEX||template.getType() == Template.TYPE_ANY_WAP || template.getType() == Template.TYPE_INDEX_WAP) {
			getService().removeTemplate(template.getSite_id(), template.getType(), template.getIsPublishStatic(), template.getId());
		} else {
			getService().removeTemplate(template.getChannel_id(), template.getType(), template.getIsPublishStatic(), template.getId());
		}

		template.setIsPublishStatic(isPublishStatic);
		getService().saveOrUpdate(template);

		// 再添加缓存
		if (template.getType() == Template.TYPE_ANY || template.getType() == Template.TYPE_INDEX||template.getType() == Template.TYPE_ANY_WAP || template.getType() == Template.TYPE_INDEX_WAP) {
			getService().putTemplate(template.getSite_id(), template.getType(), template.getIsPublishStatic(), template.getId(), template);
		} else {
			getService().putTemplate(template.getChannel_id(), template.getType(), template.getIsPublishStatic(), template.getId(),
			        template);
		}

		return list();
	}

	public Boolean getIsPublishStatic() {
    	return isPublishStatic;
    }

	public void setIsPublishStatic(Boolean isPublishStatic) {
    	this.isPublishStatic = isPublishStatic;
    }
	
	public String getIsCloseWindow() {
		return isCloseWindow;
	}

	public void setIsCloseWindow(String isCloseWindow) {
		this.isCloseWindow = isCloseWindow;
	}
}
