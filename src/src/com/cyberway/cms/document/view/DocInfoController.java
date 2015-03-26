package com.cyberway.cms.document.view;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.Constants;
import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.Template;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.cms.permission.service.CmsPermissionService;
import com.cyberway.cms.site.service.SiteCommonService;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.cms.template.service.TemplateService;
import com.cyberway.common.upload.UploadManager;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

/**
 * 前台信息展示action(已发布的信息)
 * @author caiw 
 */
public class DocInfoController extends BaseBizController<BaseDocument> 
{
	public static final String VIEW_RESULT = "view";
	public static final String INDEX_RESULT = "index";
	
	private String _templateUrl; 
	private Long _channelId;
	private Long _templateId;
	private Long _siteId;
	private String templateName;//模板名称
	private int pageIndex;
	private int pageSize;
	private int preview;
	
	private String upordown;//上一页或下一页
	private String type;
	
	private String code1;
	private String code2;
	private String code;
	
	private File[] _uploadFiles;
	private Channel _channel;

	public DocInfoController()
	{
		super ();
		isEdit=false;
		domain = null;
	}
	
	public String getTemplateUrl()
	{
		return _templateUrl;
	}

	public void setTemplateUrl(String templateUrl)
	{
		_templateUrl = templateUrl;
	}

	public Long getChannelId()
	{
		return _channelId;
	}

	public void setChannelId(Long channelId)
	{
		_channelId = channelId;
	}

	public Long getTemplateId() 
	{
		return _templateId;
	}

	public void setTemplateId(Long templateId) 
	{
		this._templateId = templateId;
	}
	
	public File[] getUploadFiles()
	{
		return _uploadFiles;
	}

	public void setUploadFiles(File[] uploadFiles)
	{
		_uploadFiles = uploadFiles;
	}
	public String getUpordown() {
		return upordown;
	}

	public void setUpordown(String upordown) {
		this.upordown = upordown;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	protected EntityDao<BaseDocument> getService()
	{
		return (DocumentCommonService) getServiceById("documentCommonService");
	}
	
	protected TemplateManagerService geTemplateManagerService ()
	{
		return (TemplateManagerService)getServiceById("templateManagerService");
	}
	
	protected ChannelManagerService getChannelManagerService ()
	{
		return (ChannelManagerService)getServiceById("channelManagerService");
	}
	
	protected TemplateService getTemplateService ()
	{
		return (TemplateService)getServiceById("templateService");
	}
	
	protected UploadManager getUploadManager ()
	{
		return (UploadManager)getServiceById("uploadManager");
	}
	//获得站点service
	public SiteManagerService getSiteManagerService(){
		return (SiteManagerService)getServiceById("siteManagerService");
	}
	/**
	 * 公用的信息service
	 * @return
	 */
	protected DocumentCommonService getDocumentCommonService(){
	  return (DocumentCommonService) getServiceById("documentCommonService");
	}
	/**
	 * 公用的站点service
	 * @return
	 */
	protected SiteCommonService getSiteCommonService(){
		return (SiteCommonService)getServiceById("siteCommonService");
	}


	@Override
	public String edit () throws Exception
	{
		/*//更新文档点击率
		getSiteCommonService().updateClickCount(id,SiteCommonService.DOC_TYPE);
		
		if(id!=null){
			domain=getDocumentCommonService().getDocument(id);
		}else{
			domain=getDomainClass().newInstance();
		}	
		//若模板id，为空，取默认模板
		if(getTemplateId()==null)
			_templateId=getChannelManagerService().getDefualutTemplateId(this.getChannelId(), Template.TYPE_DETAILS);
		setTemplateUrl(getTemplateService().getTemplatePage(getTemplateId()));
		
		return EDIT_RESULT;*/
		return view();
	}
	
    /**
     * 
     * @return
     * @throws Exception
     */
    protected Object getObjectLoginer() throws Exception{
    	return getSession().get(Loginer.LOGININFO_SESSION);    	
    } 
    /**获取当前登录对象
	 * 前台概监模板访问方法
	 * @return
	 * @throws Exception
	 */
	@Override
	public String list()throws Exception
	{
		//检测是否需登录
		CmsSite site=null;
		if(_siteId!=null){
			site=getSiteManagerService().getSiteFromCache(_siteId);
		}else{
		   if(getChannel()!=null)
			  site=getSiteManagerService().getSiteFromCache(getChannel().getSite().getOid());
		   else
		      site=getSiteManagerService().getSite(getHttpServletRequest().getServerName(), getHttpServletRequest().getServerPort());
		 }
		if(site==null){
			throw new Exception("站点不存在，请使用域名访问！");
		}else{
			_siteId = site.getOid();
		}
		
		if(( new Integer(1).equals(site.getIsLogined()))//站点需要登录
				&&(new Long(2).equals(getChannel().getIsOpenChannel()))//频道需要验证
				){//需要登录验证
			
			//未登录,跳转到当前登录地址
			if(getObjectLoginer()==null){
				if(StringUtil.isEmpty(site.getLoginUrl())){
					_templateUrl=Constants.LOGON_DEFAULT_URL;
				}else{//指定登录地址
					_templateUrl=site.getLoginUrl();
				}
				return "input_redirect"; 
			}
		}
		//更新频道点击率
		getSiteCommonService().updateClickCount(getChannelId(),SiteCommonService.CHANNEL_TYPE);
		 //若模板id为空，且指定了模板名称
		 if(_templateId==null && !StringUtil.isEmpty(templateName)){				    
				Template template=geTemplateManagerService().getTemplateByName(getChannel().getSite().getOid(),templateName);
				if(template!=null)
					_templateId=template.getId();
		 }
        //若模板id，为空，取默认模板
		if (getTemplateId() == null)
		{			
		_templateId=getChannelManagerService().getDefualutTemplateId(getChannelId(), Template.TYPE_SUMMARY);

			//setTemplateUrl(getTemplateService().getTemplatePage(getChannelManagerService().get(getChannelId()).getSummaryTemplate().getId()));
		}
		if(getTemplateId()==null)
			throw new Exception("未设置当前频道的默认概览模板！");
		setTemplateUrl(getTemplateService().getTemplatePage(getTemplateId()));
		this.getHttpServletRequest().setAttribute("code1", code1);
		this.getHttpServletRequest().setAttribute("code2", code2);	
		this.getHttpServletRequest().setAttribute("code", code);
		return LIST_RESULT;
	}
	
	
	/**
	 * 细榄模板访问方法(根据频道获取上一个下一个文档)
	 * @return
	 * @throws Exception
	 */
	public String viewdocupdown()throws Exception
	{
		//设置页面为只读
		isEdit=false;
		if(id!=null){
			//检测是否需登录
			CmsSite site=null;
			if(_siteId!=null)
			 site=getSiteManagerService().getSiteFromCache(_siteId);
			else
			 site=getSiteManagerService().getSite(getHttpServletRequest().getServerName(), getHttpServletRequest().getServerPort());
			if(site==null) new Exception("站点不存在，请使用域名访问！");	
			//获得文档信息
			if(upordown==null){
				upordown="";
			}
			domain=getDocumentCommonService().getupdownDocument(id,upordown);
			id=domain.getId();
			//通过文档获得频道id
			if(getChannelId()==null && domain!=null)
				_channelId=domain.getChannel().getId();
			
			if((new Integer(1).equals(site.getIsLogined()))//站点需要登录
					&& (new Long(2).equals(getChannel().getIsOpenChannel()))//频道需要验证
					){//需要登录验证
				//未登录,跳转到当前登录地址
				if(getObjectLoginer()==null){
					if(StringUtil.isEmpty(site.getLoginUrl())){
						_templateUrl=Constants.LOGON_DEFAULT_URL;
					}else{//指定登录地址
						_templateUrl=site.getLoginUrl();
					}
					return "input_redirect"; 
				}else{
			     CmsPermissionService permService=(CmsPermissionService)this.getServiceById("cmsPermissionService");
			     //若为只读时，检测能否有读文档权限或读文档（限作者）
    		     if(!permService.haveThePermission(getLoginer(), "CMS_DOCUMENT_VIEW", id)
    				&& !permService.haveThePermissionAndDocAuthor(getLoginer(), "CMS_DOCUMENT_VIEW_AUTHOR", id)) 
    		    	 throw new Exception(getText("RESOURCE.HINTINFO.NOPERMISSION.VISIT")); 
			 }
			}
    	//更新文档点击率
    	getSiteCommonService().updateClickCount(id,SiteCommonService.DOC_TYPE);   		
    	//domain=getDocumentCommonService().getDocument(id);
		 //amway 在所有状态都可查看
		 //if(domain!=null&&domain.getIssued()==5){
		 if(domain!=null){
			 //_channelId=domain.getChannel().getId();
			 if(domain.getSite()!=null)
			  _siteId=domain.getSite().getOid();
			 /*if(getChannel().getDetailsTemplate()!=null)
				 _templateId=getChannel().getDetailsTemplate().getId();*/
			 //若模板id为空，且指定了模板名称
			 if(_templateId==null && !StringUtil.isEmpty(templateName)){				    
					Template template=geTemplateManagerService().getTemplateByName(getChannel().getSite().getOid(),templateName);
					if(template!=null)
						_templateId=template.getId();
			 }
			 if(_templateId==null)//若未指定模板id,取默认的细览
			  _templateId=getChannelManagerService().getDefualutTemplateId(this.getChannelId(), Template.TYPE_DETAILS);
			 if(_templateId==null)
				 throw new Exception("未设置频道默认细览模板！");
			 
			 setTemplateUrl(getTemplateService().getTemplatePage(getTemplateId()));			
		 }else
			 throw new Exception("信息不存在！");
		}else{
			if(preview==1){//预览
				domain=new BaseDocument();
				setTemplateUrl(getTemplateService().getTemplatePage(getTemplateId()));		
			}else	
			throw new Exception("信息不存在！");
		}
		return VIEW_RESULT;
	}  
	
	
	
	/**
	 * 细榄模板访问方法(根据频道及不同类型获取上一个下一个经典案例)
	 * @return
	 * @throws Exception
	 */
	public String viewupdown()throws Exception
	{
		//设置页面为只读
		isEdit=false;
		if(id!=null){
			//检测是否需登录
			CmsSite site=null;		
			if(_siteId!=null)
			 site=getSiteManagerService().getSiteFromCache(_siteId);
			else
			 site=getSiteManagerService().getSite(getHttpServletRequest().getServerName(), getHttpServletRequest().getServerPort());
			if(site==null) new Exception("站点不存在，请使用域名访问！");	
			//获得文档信息
			if(upordown==null){
				upordown="up";
			}
			if(code1==null){
				code1="";
			}
			if(code2==null){
				code2="";
			}
			if(code==null){
				code="";
			}
			if(code.equalsIgnoreCase("")){
				code=code1;
				if(code.equalsIgnoreCase("")){
					code=code2;
				}
			}
			domain=getDocumentCommonService().getupdownDocument(id,upordown,code);
			id=domain.getId();
			//通过文档获得频道id
			if(getChannelId()==null && domain!=null)
				_channelId=domain.getChannel().getId();
			
			if((new Integer(1).equals(site.getIsLogined()))//站点需要登录
					&& (new Long(2).equals(getChannel().getIsOpenChannel()))//频道需要验证
					){//需要登录验证
				//未登录,跳转到当前登录地址
				if(getObjectLoginer()==null){
					if(StringUtil.isEmpty(site.getLoginUrl())){
						_templateUrl=Constants.LOGON_DEFAULT_URL;
					}else{//指定登录地址
						_templateUrl=site.getLoginUrl();
					}
					return "input_redirect"; 
				}else{
			     CmsPermissionService permService=(CmsPermissionService)this.getServiceById("cmsPermissionService");
			     //若为只读时，检测能否有读文档权限或读文档（限作者）
    		     if(!permService.haveThePermission(getLoginer(), "CMS_DOCUMENT_VIEW", id)
    				&& !permService.haveThePermissionAndDocAuthor(getLoginer(), "CMS_DOCUMENT_VIEW_AUTHOR", id)) 
    		    	 throw new Exception(getText("RESOURCE.HINTINFO.NOPERMISSION.VISIT")); 
			 }
			}
    	//更新文档点击率
    	getSiteCommonService().updateClickCount(id,SiteCommonService.DOC_TYPE);   		
    	//domain=getDocumentCommonService().getDocument(id);
		 //amway 在所有状态都可查看
		 //if(domain!=null&&domain.getIssued()==5){
		 if(domain!=null){
			 //_channelId=domain.getChannel().getId();
			 if(domain.getSite()!=null)
			  _siteId=domain.getSite().getOid();
			 /*if(getChannel().getDetailsTemplate()!=null)
				 _templateId=getChannel().getDetailsTemplate().getId();*/
			 //若模板id为空，且指定了模板名称
			 if(_templateId==null && !StringUtil.isEmpty(templateName)){				    
					Template template=geTemplateManagerService().getTemplateByName(getChannel().getSite().getOid(),templateName);
					if(template!=null)
						_templateId=template.getId();
			 }
			 if(_templateId==null)//若未指定模板id,取默认的细览
			  _templateId=getChannelManagerService().getDefualutTemplateId(this.getChannelId(), Template.TYPE_DETAILS);
			 if(_templateId==null)
				 throw new Exception("未设置频道默认细览模板！");
			 
			 setTemplateUrl(getTemplateService().getTemplatePage(getTemplateId()));			
		 }else
			 throw new Exception("信息不存在！");
		}else{
			if(preview==1){//预览
				domain=new BaseDocument();
				setTemplateUrl(getTemplateService().getTemplatePage(getTemplateId()));		
			}else	
			throw new Exception("信息不存在！");
		}
		//edit ();
		//setTemplateUrl(getTemplateService().getTemplatePage(getTemplateId()));
		return VIEW_RESULT;
	}  
	
	
	
	/**
	 * 细榄模板访问方法
	 * @return
	 * @throws Exception
	 */
	public String view()throws Exception
	{
		//设置页面为只读
		isEdit=false;
		if(id!=null){
			//检测是否需登录
			CmsSite site=null;		
			if(_siteId!=null)
			 site=getSiteManagerService().getSiteFromCache(_siteId);
			else
			 site=getSiteManagerService().getSite(getHttpServletRequest().getServerName(), getHttpServletRequest().getServerPort());
			if(site==null) new Exception("站点不存在，请使用域名访问！");	
			//获得文档信息
			domain=getDocumentCommonService().getDocument(id);
			//如果当前登录者非管理员而且文档未发布，则返回
			if(domain.getIssued()!=5 && (getLoginer()==null || (getLoginer()!=null && !getLoginer().checkIsAdministratorUser()))){
				throw new Exception("信息不存在！");
			}
			//通过文档获得频道id
			if(getChannelId()==null && domain!=null){
				_channelId=domain.getChannel().getId();
			}
			
			if((new Integer(1).equals(site.getIsLogined()))//站点需要登录
					&& (new Long(2).equals(getChannel().getIsOpenChannel()))//频道需要验证
					){//需要登录验证
				//未登录,跳转到当前登录地址
				if(getObjectLoginer()==null){
					if(StringUtil.isEmpty(site.getLoginUrl())){
						_templateUrl=Constants.LOGON_DEFAULT_URL;
					}else{//指定登录地址
						_templateUrl=site.getLoginUrl();
					}
					return "input_redirect";
					//当用户不是发布用户时，进入验证。
				}else if(!Constants.PUBLIC_USERID.equals(getLoginer().getUserid()) || !Constants.PUBLISH_USER.equals(getLoginer().getLoginid()) ){
				     CmsPermissionService permService=(CmsPermissionService)this.getServiceById("cmsPermissionService");
				     //若为只读时，检测能否有读文档权限或读文档（限作者）
	    		     if(!permService.haveThePermission(getLoginer(), "CMS_DOCUMENT_VIEW", id)
	    				&& !permService.haveThePermissionAndDocAuthor(getLoginer(), "CMS_DOCUMENT_VIEW_AUTHOR", id)) 
	    			throw new Exception(getText("RESOURCE.HINTINFO.NOPERMISSION.VISIT"));
				}
			}
    	//更新文档点击率
    	getSiteCommonService().updateClickCount(id,SiteCommonService.DOC_TYPE);   		
    	//domain=getDocumentCommonService().getDocument(id);
		 //amway 在所有状态都可查看
		 //if(domain!=null&&domain.getIssued()==5){
		 if(domain!=null){
			 //_channelId=domain.getChannel().getId();
			 if(domain.getSite()!=null)
			  _siteId=domain.getSite().getOid();
			 /*if(getChannel().getDetailsTemplate()!=null)
				 _templateId=getChannel().getDetailsTemplate().getId();*/
			 //若模板id为空，且指定了模板名称
			 if(_templateId==null && !StringUtil.isEmpty(templateName)){				    
					Template template=geTemplateManagerService().getTemplateByName(getChannel().getSite().getOid(),templateName);
					if(template!=null)
						_templateId=template.getId();
			 }
			 if(_templateId==null)//若未指定模板id,取默认的细览
			  _templateId=getChannelManagerService().getDefualutTemplateId(this.getChannelId(), Template.TYPE_DETAILS);
			 if(_templateId==null)
				 throw new Exception("未设置频道默认细览模板！");
			 
			 setTemplateUrl(getTemplateService().getTemplatePage(getTemplateId()));
			 
			 if(StringUtils.isNotBlank(upordown)){
				 String[] orderByInfos = upordown.trim().split(",");
				 String orderBy = orderByInfos[0];
				 boolean orderByAsc = false;
				 if(orderByInfos.length>1 && "asc".equalsIgnoreCase(orderByInfos[1])){
					 orderByAsc = true;
				 }
				 try{
					 BaseDocument prevDoc = getDocumentCommonService().getupdownDocument(id, true, orderBy, orderByAsc);
					 if(prevDoc != null){
						 code1 = prevDoc.getId().toString();
					 }
					 BaseDocument nextDoc = getDocumentCommonService().getupdownDocument(id, false, orderBy, orderByAsc);
					 if(nextDoc != null){
						 code2 = nextDoc.getId().toString();
					 }
				 } catch (Exception e){
					 e.printStackTrace();
				 }
			 }
		 }else
			 throw new Exception("信息不存在！");
		}else{
			if(preview==1){//预览
				domain=new BaseDocument();
				setTemplateUrl(getTemplateService().getTemplatePage(getTemplateId()));
			}else	
			throw new Exception("信息不存在！");
		}
		this.getHttpServletRequest().setAttribute("code1", code1);
		this.getHttpServletRequest().setAttribute("code2", code2);
		this.getHttpServletRequest().setAttribute("code", code);
		//setTemplateUrl(getTemplateService().getTemplatePage(getTemplateId()));
		return VIEW_RESULT;
	}
	
	/**
	 * 细榄模板访问方法
	 * @return
	 * @throws Exception
	 */
	public String viewWap()throws Exception
	{
		//设置页面为只读
		isEdit=false;
		if(id!=null){
			//检测是否需登录
			CmsSite site=null;		
			if(_siteId!=null)
			 site=getSiteManagerService().getSiteFromCache(_siteId);
			else
			 site=getSiteManagerService().getSite(getHttpServletRequest().getServerName(), getHttpServletRequest().getServerPort());
			if(site==null) new Exception("站点不存在，请使用域名访问！");	
			//获得文档信息
			domain=getDocumentCommonService().getDocument(id);
			//通过文档获得频道id
			if(getChannelId()==null && domain!=null)
				_channelId=domain.getChannel().getId();
			
			if((new Integer(1).equals(site.getIsLogined()))//站点需要登录
					&& (new Long(2).equals(getChannel().getIsOpenChannel()))//频道需要验证
					){//需要登录验证
				//未登录,跳转到当前登录地址
				if(getObjectLoginer()==null){
					if(StringUtil.isEmpty(site.getLoginUrl())){
						_templateUrl=Constants.LOGON_DEFAULT_URL;
					}else{//指定登录地址
						_templateUrl=site.getLoginUrl();
					}
					return "input_redirect"; 
				}else{
			     CmsPermissionService permService=(CmsPermissionService)this.getServiceById("cmsPermissionService");
			     //若为只读时，检测能否有读文档权限或读文档（限作者）
    		     if(!permService.haveThePermission(getLoginer(), "CMS_DOCUMENT_VIEW", id)
    				&& !permService.haveThePermissionAndDocAuthor(getLoginer(), "CMS_DOCUMENT_VIEW_AUTHOR", id)) 
    		    	 throw new Exception(getText("RESOURCE.HINTINFO.NOPERMISSION.VISIT")); 
			 }
			}
    	//更新文档点击率
    	getSiteCommonService().updateClickCount(id,SiteCommonService.DOC_TYPE);   		
    	//domain=getDocumentCommonService().getDocument(id);
		 //amway 在所有状态都可查看
		 //if(domain!=null&&domain.getIssued()==5){
		 if(domain!=null){
			 //_channelId=domain.getChannel().getId();
			 if(domain.getSite()!=null)
			  _siteId=domain.getSite().getOid();
			 /*if(getChannel().getDetailsTemplate()!=null)
				 _templateId=getChannel().getDetailsTemplate().getId();*/
			 //若模板id为空，且指定了模板名称
			 if(_templateId==null && !StringUtil.isEmpty(templateName)){				    
					Template template=geTemplateManagerService().getTemplateByName(getChannel().getSite().getOid(),templateName);
					if(template!=null)
						_templateId=template.getId();
			 }
			 if(_templateId==null)//若未指定模板id,取默认的细览
			  _templateId=getChannelManagerService().getDefualutTemplateId(this.getChannelId(), Template.TYPE_DETAILS_WAP);
			 if(_templateId==null)
				 throw new Exception("未设置频道默认细览模板！");
			 
			 setTemplateUrl(getTemplateService().getTemplatePage(getTemplateId()));			
		 }else
			 throw new Exception("信息不存在！");
		}else{
			if(preview==1){//预览
				domain=new BaseDocument();
				setTemplateUrl(getTemplateService().getTemplatePage(getTemplateId()));		
			}else	
			throw new Exception("信息不存在！");
		}
		return VIEW_RESULT;
	}
	
  /**
   * 复制对象,用于得到脱离Hibernate的对象复制
   * @param object
   * @return
   * @throws Exception
   */
  private Object copyObject(Object object) throws Exception {
  	List<String> exceptList = new ArrayList<String>();
  	exceptList.add("serialVersionUID");
  	exceptList.add("id");
  	exceptList.add("submitTime");
  	exceptList.add("positionId");
  	exceptList.add("position");
  	exceptList.add("isSubmitted");
  	exceptList.add("timeCreated");
  	exceptList.add("timeIssued");
  	exceptList.add("timeLastUpdated");
  	exceptList.add("channel");
  	exceptList.add("site");
  	return copyObject(object, exceptList);
  }
  
  /**
   * 复制对象,用于得到脱离Hibernate的对象复制
   * @param object
   * @return
   * @throws Exception
   */
  private Object copyObject(Object object,List<String> exceptList) throws Exception {
      Class<?> classType = object.getClass();
      Object objectCopy = classType.getConstructor(new Class[] {})
              .newInstance(new Object[] {});
      Field fields[] = classType.getDeclaredFields();
      for (int i = 0; i < fields.length; i++) {
          Field field = fields[i];
          String fieldName = field.getName();
          if(!exceptList.contains(fieldName)){
              String firstLetter = fieldName.substring(0, 1).toUpperCase();
              String getMethodName = "get" + firstLetter + fieldName.substring(1);
              String setMethodName = "set" + firstLetter + fieldName.substring(1);
              try{
                  Method getMethod = classType.getMethod(getMethodName,
                          new Class[] {});
                  Method setMethod = classType.getMethod(setMethodName,
                          new Class[] { field.getType() });
                  Object value = getMethod.invoke(object, new Object[] {});
                  setMethod.invoke(objectCopy, new Object[] { value });
              }catch(NoSuchMethodException e){}
          }
      }
      return objectCopy;
  }
	
	
	
	public Channel getChannel() {
		if(_channel==null)
			_channel= getChannelManagerService().getChannelFromCache(getChannelId());
		return _channel;
	}

	public void setChannel(Channel channel) {
		this._channel = channel;
	}
	
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}


	public int getPreview() {
		return preview;
	}

	public void setPreview(int preview) {
		this.preview = preview;
	}

	public Long getSiteId() {
		return _siteId;
	}

	public void setSiteId(Long siteId) {
		this._siteId = siteId;
	}
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode1() {
		return code1;
	}

	public void setCode1(String code1) {
		this.code1 = code1;
	}

	public String getCode2() {
		return code2;
	}

	public void setCode2(String code2) {
		this.code2 = code2;
	}
}
