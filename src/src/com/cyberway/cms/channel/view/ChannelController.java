package com.cyberway.cms.channel.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.Constants;
import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.permission.service.CmsPermissionService;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.cms.webservice.service.CacheSynchroismService;
import com.cyberway.cms.webservice.service.HtmlSynchroismService;
import com.cyberway.common.fileDownload.BlobFileObject;
import com.cyberway.common.service.BizUtilService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author caiw
 * 频道管理控制器
 *
 */
public class ChannelController extends BaseBizController<Channel> 
{
	private Long _pchannelid;
	private Long _siteid;
	private Long _channelFormTemplateId;
	private Long _channelDetailsTemplateId;
	private Long _channelSummaryTemplateId;
	private Long publicchannelid;
	private String refresh;
	private String _previewUrl;
	private boolean isNew=false;//是否新增
	List resoultDate = new ArrayList();
	private CmsSite site;
	private File[] _file;
	private String[] _fileContentType;
	private String[] _fileFileName;
    private List processMgrs;
	private String status;//区分是否是模板管理还是权限管理
	private String comstatus;//区分是否是模板管理还是权限管理
	private String tabstatus;//区分是否是模板管理还是权限管理
    
  //WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued" list="#{true:'是',false:'否'}" />
  	//改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
  	private Map<Integer, String> trueOfFalseMap1 = null;
  	private Map<Integer, String> trueOfFalseMap2 = null;
  	private Map<Long, String> trueOfFalseMap3 = null;
  	private Map<Long, String> trueOfFalseMap4 = null;
  	
  	
	ChannelManagerService service=(ChannelManagerService) getServiceById("channelManagerService");
	SiteManagerService siteService=(SiteManagerService) getServiceById("siteManagerService");
	TemplateManagerService templateService = (TemplateManagerService) getServiceById("templateManagerService");
	DocumentCommonService documentService = (DocumentCommonService)getServiceById("documentCommonService");
	HtmlSynchroismService htmlSynchroismService = (HtmlSynchroismService) ServiceLocator.getBean("htmlSynchroismService");
	
	public ChannelController()
	{
		setDefaultResult(LIST_RESULT);
	}	
	public String getComstatus() {
		return comstatus;
	}

	public void setComstatus(String comstatus) {
		this.comstatus = comstatus;
	}

	public String getTabstatus() {
		return tabstatus;
	}

	public void setTabstatus(String tabstatus) {
		this.tabstatus = tabstatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Long getChannelFormTemplateId()
	{
		return _channelFormTemplateId;
	}

	public void setChannelFormTemplateId(Long channelFormTemplateId)
	{
		_channelFormTemplateId = channelFormTemplateId;
	}

	public Long getChannelDetailsTemplateId()
	{
		return _channelDetailsTemplateId;
	}

	public void setChannelDetailsTemplateId(Long channelDetailsTemplateId)
	{
		_channelDetailsTemplateId = channelDetailsTemplateId;
	}

	public Long getChannelSummaryTemplateId()
	{
		return _channelSummaryTemplateId;
	}

	public void setChannelSummaryTemplateId(Long channelSummaryTemplateId)
	{
		_channelSummaryTemplateId = channelSummaryTemplateId;
	}

	@Override
	protected EntityDao<Channel> getService()
	{
		return service;
	}
	
	public TemplateManagerService getTemplateManagerService ()
	{
		return (TemplateManagerService)getServiceById("templateManagerService");
	}
	
	@Override
	public String list() throws Exception {
		CmsSite site = siteService.getSiteFromCache(getLoginerSiteId());
		if (site.getIspublished() == 1) {
			CriteriaSetup criteria = new CriteriaSetup();
			List<Criterion> criterias = new ArrayList<Criterion>();
			criterias.add(Restrictions.eq("site.oid", getLoginerSiteId()));
			criterias.add(Restrictions.eq("ispublished", new Integer(1)));
			criterias.add(Restrictions.eq("ispublicchannel", new Integer(0)));
			criteria.setAddCriterions(criterias);
			criteria.setInOrder(Order.asc("sortOrder"));
			doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		}
		return LIST_RESULT;
	}
	
	@Override
	public String execute() throws Exception
	{
		if (ObjectUtils.equals(LIST_RESULT, getDefaultResult()))
		{
			list ();
			
		}
		
		return super.execute();
	}
	
	@Override
	public String edit() throws Exception
	{
		Channel pchannel=null;
		CmsPermissionService permService=(CmsPermissionService)this.getServiceById("cmsPermissionService");
		if(id!=null){ 
			//检测权限
    		isEdit=permService.haveThePermission(getLoginer(), "CMS_CHANNEL_MODI", id);
    		if(!isEdit && !permService.haveThePermission(getLoginer(), "CMS_CHANNEL_VIEW", id))    			
    			throw new Exception(getText("RESOURCE.HINTINFO.NOPERMISSION")); 
    		
			domain = service.get(id);
			if(domain.getPublicchannelid() != null &&  domain.getPublicchannelid() != 0)
			{
				Channel channel = service.getChannelFromCache(domain.getPublicchannelid());
				if(channel != null)
				{
				domain.setPublicchannel(channel.getName());
				}
			}
			 pchannel=domain.getParent();
			if(pchannel!=null && pchannel.getId()!=null){
				domain.setParent(service.getChannelFromCache(pchannel.getId()));
			}
		}else{//新增
			domain=new Channel();
			if(_pchannelid!=null){//新增时指定父频道
				//检测权限
	    		if(!permService.haveThePermission(getLoginer(), "CMS_CHANNEL_ADD", _pchannelid))
	    			throw new Exception(getText("RESOURCE.HINTINFO.NOPERMISSION"));    
    		
				 pchannel=service.getChannelFromCache(_pchannelid);
				domain.setParent(pchannel);
				if(pchannel!=null)
					domain.setSite(pchannel.getSite());
				List peerList = service.findByParentInSameSite(getLoginerSiteId(), _pchannelid);
				domain.setSortOrder(peerList.size());
			}else
				if(_siteid!=null){
					//检测权限
		    		if(!permService.haveThePermission(getLoginer(), "CMS_CHANNEL_ADD",Constants.SITE_TYPE,_siteid))
		    			throw new Exception(getText("RESOURCE.HINTINFO.NOPERMISSION"));  
					CmsSite site=siteService.getSiteFromCache(_siteid);
					//site.setOid(_siteid);
				    domain.setSite(site);
				}
		}
		if(!this.getSession().containsKey("pkgids")){
			/*FlowCommon flowcomm=(FlowCommon)this.getServiceById("flowCommon");
			getSession().put("pkgids",flowcomm.getLoadedPackageIds(getLoginer()));*/
			//去掉了流程
			getSession().put("pkgids",new HashMap());
		}
		//pkgids=(Map)getSession().get("pkgids");
		return EDIT_RESULT;
	}
	
	@Override
	public String saveOrUpdate() throws Exception {
		
		CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");
		if (getDomain().getId() != null) {
			if (!permService.haveThePermission(getLoginer(), "CMS_CHANNEL_MODI", getDomain().getId())){
				throw new Exception(getText("RESOURCE.HINTINFO.NOPERMISSION"));
			}
			Channel channel = service.getChannelFromCache(getDomain().getId());
			if(channel!=null){
				htmlSynchroismService.deleteStaticHtmlByChannelId(domain.getId());
			}
		} else {
			Boolean unique = service.isNotUnique(domain, "site.oid,channelPath");
			String tempError = "";
			// 如果已经存在相同的登录ID
			if (unique){
				tempError += "频道路径已经存在！" ;
			}
			if(!"".equals(tempError)){
				this.addActionMessage(tempError);
				return EDIT_RESULT;
			}
			isNew = true;// 新增频道
		}
		if (getDomain().getFormTemplate().getId() == null)
			getDomain().setFormTemplate(null);
		if (getDomain().getDetailsTemplate().getId() == null)
			getDomain().setDetailsTemplate(null);
		if (getDomain().getSummaryTemplate().getId() == null)
			getDomain().setSummaryTemplate(null);
		if (getDomain().getAdminSummaryTemplate().getId() == null)
			getDomain().setAdminSummaryTemplate(null);

		if (getDomain().getParent().getId() == null)
			getDomain().setParent(null);

		super.saveOrUpdate();
		
		service.updateSubChannelPublish(getLoginerSiteId(), domain);

		if (getDomain().getSite().getOid() != null) {
			CmsSite site = siteService.getSiteFromCache(getDomain().getSite().getOid());
			getDomain().setSite(site);
		}

		/* 缓存同步 */
		if (domain.getId() != null) {
			CacheSynchroismService cacheSynchroismService = (CacheSynchroismService) this.getServiceById("cacheSynchroismService");
			try {
				cacheSynchroismService.updateChannel(domain.getId(), "");
			} catch (Exception ex) {
				
				logger.error("", ex);
				this.addActionMessage("保存失败!");
				return EDIT_RESULT;
			}
		}
		this.addActionMessage("保存成功!");
		return EDIT_RESULT;
	}
	
	public String recycle()throws Exception
	{
		CriteriaSetup criteria = new CriteriaSetup();
		if(getSiteid() != null && getSiteid()!=0)
		{
		CmsSite site = siteService.get(getSiteid());
		criteria.addFilter("site", site);
		}
		criteria.addFilter("status", 0);
		doListEntity(criteria,  getTableId(), Page.DEFAULT_PAGE_SIZE);
		return "recycle_list";
	}
	/**
	 * 
	 * 复制公共模板
	 * @return
	 * @throws Exception
	 */
	public String updateChannelForm()throws Exception
	{
		if(publicchannelid !=0)
		{
			if(getDomain().getId() == null)
			{
				isNew=true;//新增频道
			}
		domain = service.updateChannelForm(domain, publicchannelid);
		}
		return EDIT_RESULT;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String chuck() throws Exception{
		if(!StringUtil.isEmpty(keys)){
			List<Long> list=StringUtil.splitToList(keys,",");
			//增加权限过滤　amway
			Loginer loginer =(Loginer)ActionContext.getContext().getSession().get(com.cyberway.core.Constants.USER_IN_SESSION);
			CmsPermissionService permService=(CmsPermissionService)ServiceLocator.getBean("cmsPermissionService");
			//直接删除，不进入回收站
			List ids=null;
			//缓存同步
			CacheSynchroismService cacheSynchroismService = (CacheSynchroismService) this
			.getServiceById("cacheSynchroismService");
			
			for(int i=0;i<list.size();i++){
			 if(!permService.haveThePermission(loginer, "CMS_CHANNEL_DELETE",com.cyberway.cms.Constants.CHANNEL_TYPE,list.get(i))){
				 throw new Exception(getText("RESOURCE.HINTINFO.NOPERMISSION"));
			 }
			 //查找下级频道
			 List channelList = service.findByParent(list.get(i));
			 if(channelList != null && channelList.size() >0)
			 {
				    getSession().put("ActionError", getText("RESOURCE.HINTINFO.DELETEPARENTCHANNEL"));
					this.addActionError(getText("RESOURCE.HINTINFO.DELETEPARENTCHANNEL"));
					return "chuck";
				 //throw new Exception(getText("RESOURCE.HINTINFO.DELETEPARENTCHANNEL"));
			 }
			 //查找频道下的文档
			    List docList = documentService.findByChannel(list.get(i));
				if(docList != null && docList.size()>0)
				{
					getSession().put("ActionError", getText("RESOURCE.HINTINFO.DELETECHANNEL"));
					this.addActionError(getText("RESOURCE.HINTINFO.DELETECHANNEL"));
					return "chuck";
				}
			}
			
			Iterator it = list.iterator();
			while(it.hasNext())
			{
				domain = service.get(Long.parseLong(it.next().toString()));
				resoultDate.add(domain);
				List childList = getChild(service.getAll(),domain.getId());
				Iterator childit = childList.iterator();
				while(childit.hasNext())
				{
					domain = (Channel)childit.next();
					domain.setStatus(0);
					domain.setTimeDeleted(new Date());
					/* Frank 不放入回收站、直接删除 */
					//service.saveOrUpdate(domain);
					ids=new ArrayList();
					ids.add(domain.getId());
					service.removeWithForm(ids);
					htmlSynchroismService.deleteStaticHtmlBySiteId(domain.getId());
					/*缓存同步*/
					try{
						cacheSynchroismService.updateChannel(domain.getId(),"del");	
					}catch(Exception ex){
						logger.error("", ex);
					}
				}
			}
			this.addActionMessage("删除成功！");
		}else
			this.addActionError("请选择需删除的记录！");
		return "chuck";
	}
	
	public List getChild(List parent,long parentid)
	{
		Iterator it = parent.iterator();
		while(it.hasNext())
		{
			domain  = (Channel)it.next();
			if(domain.getParent() != null && domain.getParent().getId() == parentid)
			{
				resoultDate.add(domain);
				getChild(parent,domain.getId());
				
			}
		}
		return resoultDate;
	}
	
	
	/**
	 * channel还原
	 * @return
	 * @throws Exception
	 */
	public String revivification() throws Exception{
		if(!StringUtil.isEmpty(keys)){
			List list=StringUtil.splitToList(keys,",");
			Iterator it = list.iterator();
			while(it.hasNext())
			{
				domain = service.get(Long.parseLong(it.next().toString()));
				domain.setStatus(1);
				domain.setTimeDeleted(new Date());
				service.saveOrUpdate(domain);
			}
			//getService().removeByIds(list);
			this.addActionMessage("成功还原！");
		}else
			this.addActionError("请选择需还原的记录！");
		if(getSiteid() == null || getSiteid()==0)
			return "site_recycle";
		
		return recycle();
	}
	
	/**
	 * 清空记录
	 * @return
	 * @throws Exception
	 */
	public String cleanup() throws Exception{
		if(!StringUtil.isEmpty(keys)){
			List list=StringUtil.splitToList(keys,",");
			this.logger.info("delete list size:"+list.size());
			service.removeWithForm(list);
			//getService().removeByIds(list);
			this.addActionMessage("清空成功！");
		}else
			this.addActionError("请选择需清空的记录！");
		
		return recycle();
	}
	
	/**
	 * 导出频道所有模板
	 * @return
	 * @throws Exception
	 */
	public String exportChannelData() throws Exception
	{
		
		if(id!=null){
			items=new ArrayList();
			Channel chn = service.get(id);
			items.add(chn);
			items = service.getChild(id, items);
		Channel channel = chn;
		File file;
		if(Constants.IS_REALPATH)
		{
		 file = new File(Constants.ABSOLUTE_PATH+Constants.CHANNEL_FILE);
		}
		else{
			file = new File(this.getHttpServletRequest().getRealPath(Constants.CHANNEL_FILE));
		}
		FileOutputStream outputStream = new FileOutputStream(file);
		service.exportToXml(getItems(), outputStream,chn);
		outputStream.close();
		FileInputStream is = new FileInputStream(file);
		byte[] b = new byte[(int) file.length()];
		is.read(b, 0, (int) file.length());
		BlobFileObject bfo = new BlobFileObject();
		bfo.setContent(b);
		String fileName = channel.getName()+".xml";
	    is.close();
	    if(StringUtils.isEmpty(fileName))
	    {
	       fileName="unknow_channel.xml";
	    }
		 bfo.setFullfilename(fileName);
		 this.getHttpServletRequest().setAttribute(Constants.DOWNLOAD_BLOB_FILE_OBJECT, bfo);
		}
		
		return "export_download";
	}
	 
	//导入频道
	public String importChannelData() throws Exception
	{
		
		if(_file!=null&&_file.length>0){
		FileInputStream inputStream = new FileInputStream(_file[0]);
		CmsSite cmsSite = null;
		if(id != null && id != 0)
		{
			cmsSite = siteService.get(id);
		}
		 service.importFromXml(inputStream, false,cmsSite);
		 inputStream.close();
		}	
		list();
		return "close";
	}
   //更新parent频道
	public String importParentChannelData() throws Exception
	{
		
		if(_file!=null&&_file.length>0){
		FileInputStream inputStream = new FileInputStream(_file[0]);
		Channel channel = null;
		if(id != null && id != 0)
		{
			channel = service.get(id);
		}
		CmsSite cmsSite = null;
		if(channel != null && channel.getSite()!= null)
		{
			cmsSite = channel.getSite();
		}
		 service.importParentFromXml(inputStream, false,cmsSite,channel);
		 inputStream.close();
		}	
		list();
		return "close";
	}
	
	
	/**
	 * 返回tab xml文件
	 * @return
	 * @throws Exception
	 */
	public String tabxml()throws Exception
	{
		return "xml";
	}
	
	
	
	
	/**
	 * 返回tab xml文件(模板管理)
	 * @return
	 * @throws Exception
	 */
	public String comTabxml()throws Exception
	{
		getSession().put("tabstatus", tabstatus);
		
		return "comtabxml";
	}
	public String channelPicker()throws Exception
	{
		return "channel_picker";
	}
	
	/**
	 * 进入频道时显示页面
	 * @return
	 * @throws Exception
	 */
	public String tabbar()throws Exception
	{
		if(id != null && id >0)
		{
		Channel channel = service.get(id);
		if(channel != null && channel.getSite() != null){
			setSiteid(channel.getSite().getOid());
		} 
		}
		return "tabbar";
	}
	
	/**
	 * 进入频道时显示页面(模板管理)
	 * @return
	 * @throws Exception
	 */
	public String comTabbar()throws Exception
	{
		if(id != null && id >0)
		{
		Channel channel = service.get(id);
		if(channel != null && channel.getSite() != null){
			setSiteid(channel.getSite().getOid());
		} 
		}
		getSession().put("comstatus", comstatus);
		return "comtabbar";
	}
	
	public Long getPchannelid() {
		return _pchannelid;
	}

	public void setPchannelid(Long pchannelid) {
		this._pchannelid = pchannelid;
	}

	public Long getSiteid() {
		return _siteid;
	}

	public void setSiteid(Long siteid) {
		this._siteid = siteid;
	}

	public String getRefresh() {
		return refresh;
	}

	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}

	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	public Long getPublicchannelid() {
		return publicchannelid;
	}

	public void setPublicchannelid(Long publicchannelid) {
		this.publicchannelid = publicchannelid;
	}

	/**
	 * 获得站点
	 * @return
	 */
	public CmsSite getSite() {
		if(site==null){
			if(_siteid!=null)
				site=this.siteService.getSiteFromCache(_siteid);
			if(site==null && domain!=null && domain.getSite()!=null)
				site=this.siteService.getSiteFromCache(domain.getSite().getOid());
		}
		return site;
	}

	public void setSite(CmsSite site) {
		this.site = site;
	}
	public String getPreviewUrl()
	{
		return _previewUrl;
	}

	public void setPreviewUrl(String previewUrl)
	{
		_previewUrl = previewUrl;
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
	public List getProcessMgrs() {
		if(processMgrs==null){
			try{
				BizUtilService bizUtilService=(BizUtilService)getServiceById("bizUtilService");
				processMgrs=bizUtilService.getProcessMgrs();
				}catch(Exception e){
					processMgrs=new ArrayList();
				}
		}
		return processMgrs;
	}
	public void setProcessMgrs(List processMgrs) {
		this.processMgrs = processMgrs;
	}

	public Map<Integer, String> getTrueOfFalseMap1() {
		if(trueOfFalseMap1 != null){
			return trueOfFalseMap1;
		}else{
			trueOfFalseMap1 = new HashMap<Integer, String>();
			trueOfFalseMap1.put(new Integer(1), "是");
			trueOfFalseMap1.put(new Integer(0), "否");
			return trueOfFalseMap1;
		}
	}

	public Map<Integer, String> getTrueOfFalseMap2() {
		if(trueOfFalseMap2 != null){
			return trueOfFalseMap2;
		}else{
			trueOfFalseMap2 = new TreeMap<Integer, String>();
			trueOfFalseMap2.put(new Integer(0), "");
			trueOfFalseMap2.put(new Integer(2), "引用");
			trueOfFalseMap2.put(new Integer(1), "复制");
			return trueOfFalseMap2;
		}
	}

	public Map<Long, String> getTrueOfFalseMap3() {
		if(trueOfFalseMap3 != null){
			return trueOfFalseMap3;
		}else{
			trueOfFalseMap3 = new TreeMap<Long, String>();
			trueOfFalseMap3.put(new Long(0), "");
			trueOfFalseMap3.put(new Long(2), "验证");
			trueOfFalseMap3.put(new Long(1), "完全");
			return trueOfFalseMap3;
		}
	}

	public Map<Long, String> getTrueOfFalseMap4() {
		if(trueOfFalseMap4 != null){
			return trueOfFalseMap4;
		}else{
			trueOfFalseMap4 = new HashMap<Long, String>();
			trueOfFalseMap4.put(new Long(1), "是");
			trueOfFalseMap4.put(new Long(0), "否");
			return trueOfFalseMap4;
		}
	}  
	
}
