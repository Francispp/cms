package com.cyberway.cms.site.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.Constants;
import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.permission.service.CmsPermissionService;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.cms.webservice.service.CacheSynchroismService;
import com.cyberway.cms.webservice.service.HtmlSynchroismService;
import com.cyberway.common.distribution.service.DistributionService;
import com.cyberway.common.fileDownload.BlobFileObject;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class SiteController extends BaseBizController<CmsSite> {

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getTabstatus() {
		return tabstatus;
	}

	public void setTabstatus(String tabstatus) {
		this.tabstatus = tabstatus;
	}

	public String getComstatus() {
		return comstatus;
	}

	public void setComstatus(String comstatus) {
		this.comstatus = comstatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List getListSite() {
		return listSite;
	}

	public void setListSite(List listSite) {
		this.listSite = listSite;
	}

	public List getAllsite() {
		return allsite;
	}

	public void setAllsite(List allsite) {
		this.allsite = allsite;
	}

	SiteManagerService service = (SiteManagerService) this.getServiceById("siteManagerService");
	ChannelManagerService channelService=(ChannelManagerService) getServiceById("channelManagerService");
	DistributionService distributionService=(DistributionService) this.getServiceById("distributionService");
	HtmlSynchroismService htmlSynchroismService = (HtmlSynchroismService) ServiceLocator.getBean("htmlSynchroismService");
	 private File[] _file;
	 private String[] _fileContentType;
	 private String[] _fileFileName;
	// SiteQuartzService
	// quartzService=(SiteQuartzService)this.getServiceById("siteQuartzService");
	private String _treeXml;

	private String pid;// 动态树时传递,当前节点id,sitrid 如：T_1

	private List sites;
	
	private Long siteid;
	private List allsite;
	
	private List listSite;
	
	private String revi;
	private Long channelId;//频道id
	
	
	private boolean isNew=false;//是否新增
	private Long loginType=0l;//表示当前登录类型，用于区分前台、后台树
	
	private int isCollectReload = 0;//页面采集区的菜单树是否需要刷新:"0"为不需要刷新,"1"为要刷新.
	
	private String status="";//区分是否是模板管理还是权限管理
	private String comstatus;//区分是否是模板管理还是权限管理
	private String tabstatus;//区分是否是模板管理还是权限管理
	private String siteName;//页面传过来的父节点的名称
	
	private Boolean docPer;//是否是文档权限管理
	
	public Boolean getDocPer() {
		return docPer;
	}

	public void setDocPer(Boolean docPer) {
		this.docPer = docPer;
	}

	//WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued" list="#{true:'是',false:'否'}" />
	//改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
	private Map<Integer, String> trueOfFalseMap1 = null;
	private Map<Integer, String> trueOfFalseMap2 = null;
	private Map<Boolean, String> trueOfFalseMap3 = null;
	private Map<Boolean, String> trueOfFalseMap4 = null;
	
	public String getRevi() {
		return revi;
	}

	public void setRevi(String revi) {
		this.revi = revi;
	}

	public String getTreeXml() {
		return _treeXml;
	}

	public void setTreeXml(String treeXml) {
		_treeXml = treeXml;
	}

	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		return service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.core.web.BaseBizController#saveOrUpdate()
	 */
	@SuppressWarnings("unchecked")
	public String saveOrUpdate() throws Exception {
		if (domain.getTemplate() != null && domain.getTemplate().getId() == null)
			domain.setTemplate(null);
		if (domain.getOid() == null) {
			isNew = true;// 表示新增站点保存
			// 只有超级管理员才能创建站点
			if (!getLoginer().checkIsAdministratorUser()){
				this.addActionMessage(getText("RESOURCE.HINTINFO.NOPERMISSION"));
				return EDIT_RESULT;
			}
		}

		CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");
		// 若为修改，检测是否有修改站点权限
		if (domain.getOid() != null) {
			if (!permService.haveThePermission(getLoginer(), "CMS_SITE_MODI", domain.getOid())){
				this.addActionMessage(getText("RESOURCE.HINTINFO.NOPERMISSION"));
				return EDIT_RESULT;
			}
			//发现与之前的站点权限不同则删除静态HTML文件
			CmsSite site = service.getSiteFromCache(domain.getOid());
			if(site!=null && !ObjectUtils.equals(domain.getIsLogined(), site.getIsLogined()))
			{
				htmlSynchroismService.deleteStaticHtmlBySiteId(domain.getOid());
			}
		}

		String rt = super.saveOrUpdate();
		Map apps = getActionContext().getApplication();
		apps.put(com.cyberway.cms.Constants.SITES_IN_APPLICATION, service.getAllSites());
		
		//setIsCollectReload(1);//刷新页面采集区的菜单树
		
		channelService.init();
		
		/*缓存同步*/
		if(domain.getOid()!=null){
			CacheSynchroismService cacheSynchroismService = (CacheSynchroismService) this
			.getServiceById("cacheSynchroismService");
			try{
				cacheSynchroismService.updateSite(domain.getOid(),"");	
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		addActionMessage("保存成功!");
		return rt;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.core.web.BaseBizController#edit()
	 */
	public String edit() throws Exception {
		if (id != null) {
			CmsPermissionService permService = (CmsPermissionService) this
					.getServiceById("cmsPermissionService");
			isEdit = permService.haveThePermission(getLoginer(),
					"CMS_SITE_MODI", id);
		} else {
			// isEdit=getLoginer().checkIsAdministratorUser();
			if (!getLoginer().checkIsAdministratorUser())
				throw new Exception(getText("RESOURCE.HINTINFO.NOPERMISSION"));
		}
		return super.edit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	public String execute() throws Exception {
		//setSites(service.getHaveTheSites(this.getLoginer(),1));
		if(getLoginerSiteId()!=0){
			setSites(service.getHaveTheSite(this.getLoginer(),getLoginerSiteId(),1));
		}
		// CmsPermissionService
		// permService=(CmsPermissionService)this.getServiceById("cmsPermissionService");

		return TREE_RESULT;
	}
	
	
	
	public String form() throws Exception {
		//setSites(service.getHaveTheSites(this.getLoginer(),1));
		if(getLoginerSiteId()!=0){
			setSites(service.getHaveTheSite(this.getLoginer(),getLoginerSiteId(),1));
		}
		// CmsPermissionService
		// permService=(CmsPermissionService)this.getServiceById("cmsPermissionService");
		getSession().put("status", status);
		if(status.equalsIgnoreCase("1")){
			return "tree_form_tree";
		}else{
			return "tree_permisson_tree";
		}
	}
	
	
	/*public String  listSite() throws Exception{
		//setSites(service.getHaveTheSites(this.getLoginer(),1));
		setListSite(service.getHaveTheSites(this.getLoginer(),1));
		// CmsPermissionService
		// permService=(CmsPermissionService)this.getServiceById("cmsPermissionService");
		return "list_result";
		
	}*/
	
	public String reviChannel() throws Exception {
		setSites(service.getHaveTheSites(this.getLoginer(),0));
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
		}
		return "revichannel";
	}

	/**
	 * 列表使用的action
	 */
	public String list() throws Exception{
		
		List<Long> ids = new ArrayList<Long>();
		ids = service.getSitesByLoginer(getLoginer(), 1);
		if(ids.size() >0)
		{
		CriteriaSetup criteria = new CriteriaSetup();
		List<Criterion> cList = new ArrayList<Criterion>();
		cList.add(Restrictions.in("oid", ids));
		criteria.setAddCriterions(cList);
		criteria.setInOrder(Order.asc("oid"));
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		}
		return LIST_RESULT;
	}
	


	/**
	 * 信息采集树
	 * 
	 * @return
	 */
	public String itree() throws Exception {
		setSites(service.getPublishedSites(this.getLoginer(),1));
		return "itree";
	}
	
	/**
	 * 信息采集树
	 * 
	 * @return
	 */
	public String channeltree() throws Exception {
		//this.getLoginer().setSiteId(siteid);
		//getSession().put(Loginer.LOGININFO_SESSION,this.getLoginer());
		if(getLoginerSiteId()!=0){
			setSites(service.getPublishedSite(this.getLoginer(),getLoginerSiteId(),1));
		}
		return "itree";
	}
	
	/**
	 * 选择设置为公共的频道
	 * @return
	 * @throws Exception
	 */
	public String publicItree() throws Exception {
		
		return "public";
	}
	/**
	 * 选择频道的
	 * @return
	 * @throws Exception
	 */
	public String selectChanelTree() throws Exception {
		if(this.siteid==null && this.channelId!=null){			
			ChannelManagerService channelService=(ChannelManagerService)getServiceById("channelManagerService");
		    Channel channel=channelService.getChannelFromCache(channelId);
		    if(channel!=null)
			  siteid=channel.getSite().getOid();
		}
		return "selectChannel";
	}    
	/**
	 * 获得树根节点xml  
	 * @return
	 * @throws Exception
	 */
	public String xml() throws Exception {
		Loginer loginer=getLoginer();
		if(loginType!=null)//设置传递过来的参数
			loginer.setLoginType(loginType.intValue());
        if(!StringUtil.isEmpty(pid)){//传pid是指定频道id，为根节点
        	_treeXml = service.getChannelTreeXml(loginer, new Long(pid),this.getRevi());
        	 
        }else//站点为根节点
        	//siteName=StringUtil.toUTF8(siteName);
        	if(siteName != null)
        	{
        siteName=java.net.URLDecoder.decode(siteName, "UTF-8");
        	}
        	else
        		siteName = service.getSiteFromCache(id).getSitename();
		_treeXml = service.getSiteTreeXml(loginer, id,this.getRevi(),siteName);


		return HTMLXTREE_RESULT;
	}
	
	public String publicXml() throws Exception {
		Loginer loginer=getLoginer();
		if(this.loginType!=null)//设置传递过来的参数
			loginer.setLoginType(loginType.intValue());
        if(!StringUtil.isEmpty(pid)){//传pid是指定频道id，为根节点
        	_treeXml = service.getChannelTreeXml(loginer, new Long(pid),this.getRevi());
        	 
        }else//站点为根节点
		_treeXml = service.getPublicSiteTreeXml(loginer, id,this.getRevi());

		return HTMLXTREE_RESULT;
	}

	public String recycle() throws Exception {
		
		return "recycle";
	}

	public String recycletabxml() throws Exception {
		return "recycletabbar";
	}
	
	/**
	 * chuck to recycle
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String chuck() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> list = StringUtil.splitToList(keys, ",");
			// Iterator it = list.iterator();
			CacheSynchroismService cacheSynchroismService = (CacheSynchroismService) this.getServiceById("cacheSynchroismService");

			for (Long id : list) {
				domain = service.get(id);
				if (domain != null) {
					List channelList = channelService.getChannelsBySite(domain.getOid());
					// 删除标记时，若有频道，不能作删除标记
					if (channelList != null && channelList.size() > 0)
						throw new Exception("站点下存在频道，请先删除频道！");
					domain.setStatus(0);
					/* Frank 不放入回收站、直接删除 */
					// service.saveOrUpdate(domain);

					getService().removeByIds(list);
					// 更新当前所有站点
					Map apps = getActionContext().getApplication();
					apps.put(com.cyberway.cms.Constants.SITES_IN_APPLICATION, service.getAllSites());
					/* 缓存同步 */
					cacheSynchroismService.updateSite(domain.getOid(), "del");
					htmlSynchroismService.deleteStaticHtmlBySiteId(domain.getOid());
				}
			}
			// getService().removeByIds(list);
			this.addActionMessage("删除成功！");
		} else
			this.addActionError("请选择需删除的记录！");
		return "chuck";
	}
	
	/**
	 * chuck to recycle
	 * 
	 * @return
	 */
	public String chuckto() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> list = StringUtil.splitToList(keys, ",");
			// Iterator it = list.iterator();
			CacheSynchroismService cacheSynchroismService = (CacheSynchroismService) this.getServiceById("cacheSynchroismService");

			String errorSiteName = "";
			for (Long id : list) {
				domain = service.get(id);
				if (domain != null) {
					List channelList = channelService.getChannelsBySite(domain.getOid());
					// 删除标记时，若有频道，不能作删除标记
					if (channelList != null && channelList.size() > 0) {
						// throw new Exception("站点下存在频道，请先删除频道！");
						errorSiteName += " " + domain.getSitename() + " ";
						continue;
					} else {
						domain.setStatus(0);
						/* Frank 不放入回收站、直接删除 */

						// service.saveOrUpdate(domain);

						getService().removeByIds(list);
						// 更新当前所有站点
						Map apps = getActionContext().getApplication();
						apps.put(com.cyberway.cms.Constants.SITES_IN_APPLICATION, service.getAllSites());
						/* 缓存同步 */
						cacheSynchroismService.updateSite(domain.getOid(), "del");
					}
				}
			}
			if(StringUtils.isNotBlank(errorSiteName)){
				this.addActionMessage("站点" + errorSiteName + "下存在频道，请先删除频道！");
				return list();
			}else{
				this.addActionMessage("删除成功！");
			}
		} else
			this.addActionMessage("请选择需删除的记录！");
		if (!StringUtil.isEmpty(keys)) {
			List<Long> lists = StringUtil.splitToList(keys, ",");
			for (int i = 0; i < lists.size(); i++) {
				if (this.getLoginerSiteId() == Long.parseLong(lists.get(i).toString())) {
					return "index";
				}
			}
			return list();
		} else {
			return list();
		}
	}
	
	public String recyclelist()throws Exception
	{
		CriteriaSetup criteria = new CriteriaSetup();
		criteria.addFilter("status", 0);
		doListEntity(criteria,  getTableId(), Page.DEFAULT_PAGE_SIZE);
		return "recycle_list";
	}
	
	
	
	public String getAllSite() throws Exception{
		//setAllsite(service.getListCmsSite(this.getLoginer(),1));
		
		return "allsite";
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
		return "revivification";
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
			getService().removeByIds(list);
			//更新当前所有站点
			Map apps = getActionContext().getApplication();
			apps.put(com.cyberway.cms.Constants.SITES_IN_APPLICATION, service
					.getAllSites());
			this.addActionMessage("清空成功！");
		}else
			this.addActionError("请选择需清空的记录！");
		return "revivification";
	}

	/**
	 * 获得频道的树xml
	 * 
	 * @return
	 * @throws Exception
	 */
	public String channelxml() throws Exception {
		Loginer loginer=getLoginer();
		if(this.loginType!=null)//设置传递过来的参数
			loginer.setLoginType(loginType.intValue());
		if (StringUtil.isNumber(pid))// pid为频道id
			_treeXml = service.getChannelTreeXml(loginer, id,new Long(pid),this.getRevi());
		else
			// pid为站点id,形式如：T_1
			_treeXml = service.getChannelTreeXml(loginer, id, null,this.getRevi());

		return HTMLXTREE_RESULT;
	}
	
	public String PublicChannelxml() throws Exception {
			_treeXml = service.getPublicTreeXml(getLoginer(), id);
		return HTMLXTREE_RESULT;
	}
	
	public String exportSiteInfo() throws Exception
	{
		
		if(id!=null){
		CmsSite site = service.get(id);
		File file;
		if(Constants.IS_REALPATH)
		{
		 file = new File(Constants.ABSOLUTE_PATH+Constants.SITE_FILE);
		}
		else{
			file = new File(this.getHttpServletRequest().getRealPath(Constants.SITE_FILE));
		}
		FileOutputStream outputStream = new FileOutputStream(file);
		service.exportInfoToXml(site, outputStream);
		outputStream.close();
		FileInputStream is = new FileInputStream(file);
		byte[] b = new byte[(int) file.length()];
		is.read(b, 0, (int) file.length());
		BlobFileObject bfo = new BlobFileObject();
		bfo.setContent(b);
		String fileName = site.getSitename()+".xml";
	    is.close();
	    if(StringUtils.isEmpty(fileName))
	    {
	       fileName="unknow.xml";
	    }
		 bfo.setFullfilename(fileName);
		 this.getHttpServletRequest().setAttribute(Constants.DOWNLOAD_BLOB_FILE_OBJECT, bfo);
		}
		
		return "export_download";
	}
	public String exportSiteData() throws Exception
	{
		
		if(id!=null){
			items=new ArrayList();
			items.add(service.get(id));
		CmsSite site = service.get(id);
		File file;
		if(Constants.IS_REALPATH)
		{
		 file = new File(Constants.ABSOLUTE_PATH+Constants.SITE_FILE);
		}
		else{
			file = new File(this.getHttpServletRequest().getRealPath(Constants.SITE_FILE));
		}
		FileOutputStream outputStream = new FileOutputStream(file);
		service.exportToXml(getItems(), outputStream);
		outputStream.close();
		FileInputStream is = new FileInputStream(file);
		byte[] b = new byte[(int) file.length()];
		is.read(b, 0, (int) file.length());
		BlobFileObject bfo = new BlobFileObject();
		bfo.setContent(b);
		String fileName = site.getSitename()+".xml";
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
	
	public String importSiteData() throws Exception
	{
		
		if(_file!=null&&_file.length>0){
		FileInputStream inputStream = new FileInputStream(_file[0]);
		service.importFromXml(inputStream, false);
		 inputStream.close();
		}	
		list();
		return "close";
	}
	
	
	public String tabxml() throws Exception {
		return "xml";
	}
	
	public String comTabxml() throws Exception {
		getSession().put("tabstatus", tabstatus);
		return "comtabxml";
	}

	public String tabbar() throws Exception {
		return "tabbar";
	}
	
	
	public String comTabbar() throws Exception {
		getSession().put("comstatus", comstatus);
		return "comtabbar";
	}

	public List getSites() {
		return sites;
	}

	public void setSites(List sites) {
		this.sites = sites;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	public Long getSiteid() {
		return siteid;
	}

	public void setSiteid(Long siteid) {
		this.siteid = siteid;
	}

	public Long getLoginType() {
		return loginType;
	}

	public void setLoginType(Long loginType) {
		this.loginType = loginType;
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

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public int getIsCollectReload() {
    	return isCollectReload;
    }

	public void setIsCollectReload(int isCollectReload) {
    	this.isCollectReload = isCollectReload;
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
			trueOfFalseMap2 = new HashMap<Integer, String>();
			trueOfFalseMap2.put(new Integer(0), "完全");
			trueOfFalseMap2.put(new Integer(1), "验证");
			return trueOfFalseMap2;
		}
    }
	
	public Map<Boolean, String> getTrueOfFalseMap3() {
		if(trueOfFalseMap3 != null){
			return trueOfFalseMap3;
		}else{
			trueOfFalseMap3 = new HashMap<Boolean, String>();
			trueOfFalseMap3.put(new Boolean(true), "是");
			trueOfFalseMap3.put(new Boolean(false), "否");
			return trueOfFalseMap3;
		}
    }

	public Map<Boolean, String> getTrueOfFalseMap4() {
		if(trueOfFalseMap4 != null){
			return trueOfFalseMap4;
		}else{
			trueOfFalseMap4 = new HashMap<Boolean, String>();
			trueOfFalseMap4.put(new Boolean(true), "是");
			trueOfFalseMap4.put(new Boolean(false), "否");
			return trueOfFalseMap4;
		}
    }
	

}
