package com.cyberway.cms.document.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.script.ScriptEngine;

import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.ecside.table.limit.Limit;
import org.ecside.util.RequestUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.Constants;
import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.Template;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.cms.log.service.LogManagerService;
import com.cyberway.cms.permission.service.CmsPermissionService;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.cms.template.service.TemplateService;
import com.cyberway.cms.webservice.service.HtmlSynchroismService;
import com.cyberway.cms.webservice.service.LuceneSynchroismService;
import com.cyberway.common.ExpressionEvaluator;
import com.cyberway.common.distribution.service.DistributionService;
import com.cyberway.common.fileDownload.BlobFileObject;
import com.cyberway.common.upload.UploadManager;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

public class DocumentController extends BaseBizController<BaseDocument> implements Preparable {
	public String getIsCloseWindow() {
		return isCloseWindow;
	}

	public void setIsCloseWindow(String isCloseWindow) {
		this.isCloseWindow = isCloseWindow;
	}

	public static final String	VIEW_RESULT	            = "view";
	public static final String	INDEX_RESULT	        = "index";
	public static final String	AJAX	        = "ajax";
	private static final String	Channel	                = null;
	private static final String	CmsSite	                = null;
	SiteManagerService	        siteService	            = (SiteManagerService) getServiceById("siteManagerService");
	DocumentCommonService	    service	                = (DocumentCommonService) getServiceById("documentCommonService");
	DistributionService	        dService	            = (DistributionService) getServiceById("distributionService");
	LuceneSynchroismService	    luceneSynchroismService	= (LuceneSynchroismService) getServiceById("luceneSynchroismService");
	ChannelManagerService	    channelService	        = (ChannelManagerService) getServiceById("channelManagerService");
	HtmlSynchroismService htmlSynchroismService = (HtmlSynchroismService) ServiceLocator.getBean("htmlSynchroismService");
	private String	            _templateUrl;	                                                                               // 模板地址
	private Long	            _channelId;	                                                                                   // 频道id
	private String	            _templateName;	                                                                               // 模板名称
	private Long	            _templateId;	                                                                               // 模板id
	private Long	            _siteId;	                                                                                   // 站点id
	private String	            isPublic;	                                                                                   // 保存并发布
	private int	                isShare;	                                                                                   // 是否共享

	private File[]	            _uploadFiles;
	private Channel	            _channel;	                                                                                   // 频道对象
	private static Class	    _formClass	            = com.cyberway.cms.domain.Document.class;	                           // 默认为动态表单
	private String	            optName;	                                                                                   // 操作名称
	private int	                preview;	                                                                                   // 预览
	private int	                _type;
	private String	            fromUrl;	                                                                                   // 跳转的地址
	private String	            returnValue;
	private String	            activityid;	                                                                                   // 流程活动实例id
	private String	            activityname;	                                                                               // 流程名称
	private int	                issued	                = 5;	                                                               // 是否发布
	private String	            flowname;
	private String	            fieldName;
	private File[]	            _file;
	private String[]	        _fileFileName;

	private String	            isCloseWindow;

	/* amway */
	private int	                pageIndex;
	private int	                pageSize;
	private Page	            pageData;

	public int getPageSize() {
		return pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Page getPageData() {
		return pageData;
	}

	public void setPageData(Page pageData) {
		this.pageData = pageData;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public DocumentController() {
		// super ();

		domain = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.core.web.BaseFlowController#getModuleCode()
	 */
	public String getModuleCode() {
		return null;
	}

	@Override
	public BaseDocument getDomain() {
		if (domain == null) {
			if (this._channelId != null) {
				try {
					// 获得指定频道的form class对象
					_formClass = this.service.getFormClass(_channelId);
				} catch (Exception e) {
					logger.error("", e);
				}
			}
			if (getHttpServletRequest().getParameter("id") != null) {
				setId(Long.valueOf(getHttpServletRequest().getParameter("id")));
				get();
			}
			if (domain == null) {
				try {
					setDomain((BaseDocument) getDomainClass().newInstance());
					if (getId() == null) {// 若新增则重新获得ID
						setId(service.getSequence());
					}
					domain.setId(getId());// 设置ID

				} catch (InstantiationException e) {
					logger.error("", e);
				} catch (IllegalAccessException e) {
					logger.error("", e);
				}
			}
		}

		return domain;
	}

	@Override
	protected EntityDao<BaseDocument> getService() {
		return service;
	}

	protected LogManagerService getLogManagerService() {
		return (LogManagerService) getServiceById("logManagerService");
	}

	protected TemplateManagerService geTemplateManagerServcie() {
		return (TemplateManagerService) getServiceById("templateManagerService");
	}

	protected ChannelManagerService getChannelManagerService() {
		return (ChannelManagerService) getServiceById("channelManagerService");
	}

	protected TemplateService getTemplateService() {
		return (TemplateService) getServiceById("templateService");
	}

	protected UploadManager getUploadManager() {
		return (UploadManager) getServiceById("uploadManager");
	}

	public ExpressionEvaluator getEvaluator() {
		return (ExpressionEvaluator) getServiceById("expressionEvaluator");
	}

	/**
	 * 信息回收站
	 * 
	 * @return
	 * @throws Exception
	 */
	public String recycle() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		CmsSite site = siteService.getSiteFromCache(getSiteId());
		// criteria.addFilter("site", site);
		criteria.addFilter("status", 0);
		doAllList(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);

		return "revivification";
	}

	/**
	 * 过期文档
	 * 
	 * @return
	 * @throws Exception
	 */
	public String overdueDoc() throws Exception {
		if (!StringUtil.isEmpty(keys) && !StringUtil.isEmpty(optName)
				&& optName.equalsIgnoreCase("cleanup"))// 若keys不为空，则删除指定的keys
			cleanup();// 删除文档，不可恢复

		// 查询过期文档
		CriteriaSetup criteria = new CriteriaSetup();
		CmsSite site = siteService.getSiteFromCache(getSiteId());
		// criteria.addFilter("site", site);
		criteria.addFilter("status", -1);// 过期
		doAllList(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		if (this._templateId == null && !StringUtil.isEmpty(this._templateName)) {
			TemplateManagerService templateService = (TemplateManagerService) ServiceLocator.getBean("templateManagerService");
			Template template = templateService.getTemplateByName(site.getOid(), _templateName);
			if (template != null)
				this._templateId = template.getId();
		}
		// 指定模板id
		if (_templateId != null) {
			setTemplateUrl(getTemplateService().getTemplatePage(_templateId));
			return LIST_RESULT;
		}

		return "overdue_doc";
	}

	/**
	 * 共享文档列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String shareDocList() throws Exception {

		CriteriaSetup criteria = new CriteriaSetup();
		List<Long> shareDoc = service.getShareDocuments(this.getSiteId(), this.getLoginer());
		if (shareDoc != null) {
			Criterion inCriterion = Restrictions.in("id", shareDoc);
			criteria.setInCriterion(inCriterion);
			doAllList(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		}
		return "share_doc";
	}

	/**
	 * 不通过channel获取document列表
	 * 
	 * @param criteriaSetup
	 * @param tableId
	 * @param pageSize
	 * @throws Exception
	 */
	protected void doAllList(CriteriaSetup criteriaSetup, String tableId, int pageSize) throws Exception {
		int maxCount = 1000000000;
		int totalRows1 = RequestUtils.getTotalRowsFromRequest(getHttpServletRequest(), tableId);
		if (totalRows1 < 0) {
			totalRows1 = maxCount;// getService().getCount(criteriaSetup);
		}
		Limit limit = RequestUtils.getLimit(getHttpServletRequest(), tableId, totalRows1, Page.DEFAULT_PAGE_SIZE);
		limit.setRowAttributes(maxCount, maxCount);// 对查询数据不在数据库层分页
		Page page = service.findECPage(limit, criteriaSetup, this._siteId, this.getLoginer());
		this.setItems((List) page.getResult());

		getHttpServletRequest().setAttribute(tableId + "_totalRows", page.getTotalCount());
		// getHttpServletRequest().setAttribute(tableId+"_totalRows",
		// Page.DEFAULT_PAGE_SIZE);
		RequestUtils.setTotalRows(getHttpServletRequest(), page.getTotalCount());
	}

	/**
	 * 修改status,信息还原
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String revivification() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List list = StringUtil.splitToList(keys, ",");
			Iterator it = list.iterator();
			while (it.hasNext()) {
				domain = service.get(Long.parseLong(it.next().toString()));
				domain.setStatus(1);
				domain.setTimeDeleted(new Date());
				service.saveOrUpdate(domain);
			}
			// getService().removeByIds(list);
			this.addActionMessage("成功还原！");
		} else
			this.addActionError("请选择需还原的记录！");
		return "revivification";
	}

	/**
	 * 清空回收站信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String cleanup() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List list = StringUtil.splitToList(keys, ",");
			this.logger.info("delete list size:" + list.size());
			service.removeByIds(list);
			this.addActionMessage("清空成功！");
		} else
			this.addActionError("请选择需清空的记录！");

		return "revivification";
	}

	/*
	 * 获得主键方法，若不为默认oid,需重载
	 */
	protected String getKeyName() {
		return "id";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	public String execute() throws Exception {
		return adminList();
	}

	public String shareTree() throws Exception {
		return "share_tree";
	}

	public String createDocumentByShare() throws Exception {
		try {
			boolean isSuccess = service.createDocumentByShare(_channelId, id);
			setReturnValue((isSuccess) ? "true" : "false");
		} catch (Exception e) {
			setReturnValue("false");
			return "share_success";
		}
		return "share_success";
	}

	public String adminOrder() throws Exception {

		return adminList();
	}

	/**
	 * 显示表单信息列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String adminList() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");

		// 验证是否有权访问此频道
		boolean channelPermission = permService.haveThePermission(getLoginer(), "CMS_DOCUMENT_VIEW", Constants.CHANNEL_TYPE, getChannelId());
		
		_channel = getChannelManagerService().getChannelFromCache(getChannelId());
		setSiteId(_channel.getSite().getOid());
		// 增加过滤单独指定文档的权限 FIXME 2012-11-21 18:20:26
		if (!getLoginer().checkIsAdministratorUser() && !channelPermission) {
			throw new Exception("无权访问文档！");
		}

		criteria.addFilter("channel.id", getChannelId());
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.desc("orderNumber"));
		orders.add(Order.desc("timeCreated"));

		criteria.setInOrders(orders);

		// 安利文档直接删除
		criteria.addFilter("status", 1);

		// 安利文档过滤规则 未发布的文档，只对作者可见，发布后有权限可见
		if (!getLoginer().checkIsAdministratorUser()) {
			if (isFlow()) {// 流程
				List<Criterion> lsCris = criteria.getAddCriterions();
				if (lsCris == null)
					lsCris = new ArrayList();
				// 能看到已发布和自己非草稿状态
				Criterion criterion = Restrictions
						.sqlRestriction("(this_.issued=5 or this_.issued>0 or (this_.issued<5 and this_.author_id="
								+ getLoginer().getUserid() + "))");
				lsCris.add(criterion);
				criteria.setAddCriterions(lsCris);
			} else {// 非流程

			}
		}

		doListEntity(criteria, "myTable", Page.DEFAULT_PAGE_SIZE);
		// 获得后台概览模板
		Long tempid = getChannelManagerService().getDefualutTemplateId(_channel.getId(), Template.TYPE_ADMIN_SUMMARY);
		if (preview == 1 && _templateId != null) {// 预览
			tempid = this._templateId;
		}
		if (tempid == null)
			throw new Exception("未设置当前频道的默认后台概览模板！");
		setTemplateUrl(getTemplateService().getTemplatePage(tempid));
		return LIST_RESULT;
	}

	/**
	 * 个人简历用,增加默认条件去除未投递简历
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String adminResumeList() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");

		// 验证是否有权访问此频道
		boolean channelPermission = permService.haveThePermission(getLoginer(), "CMS_DOCUMENT_VIEW", Constants.CHANNEL_TYPE, getChannelId());
		
		_channel = getChannelManagerService().getChannelFromCache(getChannelId());
		setSiteId(_channel.getSite().getOid());
		// 增加过滤单独指定文档的权限 FIXME 2012-11-21 18:20:26
		if (!getLoginer().checkIsAdministratorUser() && !channelPermission) {
			throw new Exception("无权访问文档！");
		}

		criteria.addFilter("channel.id", getChannelId());
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.desc("orderNumber"));
		orders.add(Order.desc("timeCreated"));

		criteria.setInOrders(orders);

		// 安利文档直接删除
		criteria.addFilter("status", 1);

		// 安利文档过滤规则 未发布的文档，只对作者可见，发布后有权限可见
		if (!getLoginer().checkIsAdministratorUser()) {
			if (isFlow()) {// 流程
				List<Criterion> lsCris = criteria.getAddCriterions();
				if (lsCris == null)
					lsCris = new ArrayList();
				// 能看到已发布和自己非草稿状态
				Criterion criterion = Restrictions
						.sqlRestriction("(this_.issued=5 or this_.issued>0 or (this_.issued<5 and this_.author_id="
								+ getLoginer().getUserid() + "))");
				lsCris.add(criterion);
				criteria.setAddCriterions(lsCris);
			} else {// 非流程

			}
		}

		//增加职位非空过滤
		List<Criterion> addCriterions = criteria.getAddCriterions();
		if (addCriterions == null){
			addCriterions = new ArrayList<Criterion>();
		}
		addCriterions.add(Restrictions.isNotNull("position"));
		addCriterions.add(Restrictions.ne("position",""));
		addCriterions.add(Restrictions.eq("isSubmitted",true));
		criteria.setAddCriterions(addCriterions);
		
		doListEntity(criteria, "myTable", Page.DEFAULT_PAGE_SIZE);
		// 获得后台概览模板
		Long tempid = getChannelManagerService().getDefualutTemplateId(_channel.getId(), Template.TYPE_ADMIN_SUMMARY);
		if (preview == 1 && _templateId != null) {// 预览
			tempid = this._templateId;
		}
		if (tempid == null)
			throw new Exception("未设置当前频道的默认后台概览模板！");
		setTemplateUrl(getTemplateService().getTemplatePage(tempid));
		return LIST_RESULT;
	}

	/**
	 * 查询站点所有有权限信息
	 */
	public String adminAllList() throws Exception {

		CriteriaSetup criteriaSetup = new CriteriaSetup();

		Channel channel = getChannelManagerService().getChannelFromCache(getChannelId());
		CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");

		List<Channel> chls = getChannelManagerService().getChannelsBySite(channel.getSite().getOid());
		List<Criterion> lsCris = criteriaSetup.getAddCriterions();
		if (lsCris == null)
			lsCris = new ArrayList();
		if (chls != null) {
			List permchl = new ArrayList();
			for (Channel chl : chls) {
				// 检测权限
				if (!permService.haveThePermission(getLoginer(), "CMS_DOCUMENT_VIEW", Constants.CHANNEL_TYPE, chl.getId())) {
					if (!permService.haveThePermission(getLoginer(), "CMS_DOCUMENT_VIEW_AUTHOR", Constants.CHANNEL_TYPE, chl.getId()))
						continue;
				}
				permchl.add(chl);
			}

			Criterion criterion = null;
			if (permchl.size() > 0) {
				criterion = Restrictions.in("channel", permchl);
			} else
				return NoPermissionHint();

			if (criterion != null)
				lsCris.add(criterion);
		}

		criteriaSetup.addFilter("authorId", getLoginer().getUserid());
		criteriaSetup.addFilter("issued", getIssued());
		criteriaSetup.setAddCriterions(lsCris);

		doListEntity(criteriaSetup, "myTable", Page.DEFAULT_PAGE_SIZE);
		// 获得后台概览模板
		Long tempid = getChannelManagerService().getDefualutTemplateId(_channel.getId(), Template.TYPE_ADMIN_SUMMARY);
		if (preview == 1 && _templateId != null) {// 预览
			tempid = this._templateId;
		}
		if (tempid == null)
			throw new Exception("未设置当前频道的默认后台概览模板！");
		setTemplateUrl(getTemplateService().getTemplatePage(tempid));
		return LIST_RESULT;
	}

	public String adminBaList() throws Exception {
		addActionMessage(getText("RESOURCE.HINTINFO.SAVESUCCESS"));
		return adminList();
	}

	/**
	 * 修改表单信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String adminEdit() throws Exception {
		CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");

		Boolean isOpen = false;

		// 先检测权限
		if (getId() != null) {// 设置频道id
			setChannelId(getDomain().getChannel().getId());
			Channel channel = siteService.getSiteCache().getChannelFromCach(getDomain().getChannel().getId());
			// 若频道设置为‘完全开放’，无需进行权限检测
			isOpen = ((channel != null && channel.getIsOpenChannel() != null && channel.getIsOpenChannel() == 1) ? true : false);
			// 安利已发布内容可以再修改
			/*
			 * if(getDomain().getIssued()!=0 && getDomain().getIssued()!=4 &&
			 * getDomain().getIssued()!=1)//文档在草稿、在审、已返状态可以修改 isEdit=false;
			 */
			if (!isOpen) {
				// 检测权限
				if (isEdit) {
					isEdit = permService.haveThePermission(getLoginer(), "CMS_DOCUMENT_MODI", id);
					if (!isEdit) {
						// 有修改文档权限（限作者），且当前作者是登录者时，可修改文档
						isEdit = permService.haveThePermissionAndDocAuthor(getLoginer(), "CMS_DOCUMENT_MODI_AUTHOR", id);
					}
				}
				// 若为只读时，检测能否有读文档权限或读文档（限作者）
				if (!isEdit && !permService.haveThePermission(getLoginer(), "CMS_DOCUMENT_VIEW", id)
				        && !permService.haveThePermissionAndDocAuthor(getLoginer(), "CMS_DOCUMENT_VIEW_AUTHOR", id))
					return NoPermissionHint();
			}
		} else {// 新增
			    // 检测权限
			Channel channel = siteService.getSiteCache().getChannelFromCach(getChannelId());
			// 若频道设置为‘完全开放’，无需进行权限检测
			isOpen = ((channel != null && channel.getIsOpenChannel() != null && channel.getIsOpenChannel() == 1) ? true : false);
			if (!isOpen) {
				if (!permService.haveThePermission(getLoginer(), "CMS_DOCUMENT_ADD", Constants.CHANNEL_TYPE, getChannelId()))
					return NoPermissionHint();
			}
			// 设置用户信息

		}
		// 若带流程
		getDomain();
		Long templateId = null;
		// 获得表单模板
		// if (isEdit)
		if(_templateId!=null){
			templateId = this._templateId;
		} else {
			templateId = getChannelManagerService().getDefualutTemplateId(getChannel().getId(), Template.TYPE_FORM);
		}
		/*
		 * else { templateId = getChannelManagerService().getDefualutTemplateId(
		 * getChannel().getId(), Template.TYPE_DETAILS); if (templateId == null)
		 * templateId = getChannelManagerService().getDefualutTemplateId(
		 * getChannel().getId(), Template.TYPE_FORM); }
		 */
		if (preview == 1 && _templateId != null) {// 预览
			templateId = this._templateId;
		}
		if (templateId == null)
			throw new Exception("未设置当前频道的默认表单模板或细览模板！");
		/*
		 * if(getChannel().getFormTemplate()==null
		 * ||getChannel().getFormTemplate().getId()==null) throw new
		 * Exception("未设置当前频道的默认表单模板！");
		 */
		// this.service.get
		if (getId() == null) {
			// setId(((DocumentCommonService)getService()).allocateUniqueId());
			// _channel=getChannelManagerService().getChannelFromCache(
			// getChannelId());
			// long templateId =
			// getChannel().getFormTemplate().getId().intValue();
			setTemplateUrl(getTemplateService().getTemplatePage(templateId));
			getDomain().setChannel(getChannel());
			domain.setAuthorCname(getLoginer().getUsername());
			domain.setAuthorId(getLoginer().getUserid());
			setSiteId(_channel.getSite().getOid());
		} else {
			setTemplateUrl(getTemplateService().getTemplatePage(templateId));
			domain.setSite(getChannel().getSite());
			domain.getSite().setOid(getChannel().getSite().getOid());
			setSiteId(_channel.getSite().getOid());
		}
		if (domain.getTimeIssued() == null) {
			domain.setTimeIssued(new Date());
		}
		return EDIT_RESULT;
	}

	/**
	 * 编辑Office item
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editOfficeItem() throws Exception {

		return "edit_office_item";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.core.web.BaseFlowController#saveOrUpdate()
	 */
	public String saveOrUpdate() throws Exception {
		return adminSaveOrUpdate();
	}

	/**
	 * 保存表单信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String adminSaveOrUpdate() throws Exception {

		TemplateManagerService tManagerService = (TemplateManagerService) getServiceById("templateManagerService");

		// String result = EDIT_RESULT;// 操作完后返回
		String result = EDIT_RESULT;// 操作完后返回
		_channel = getChannelManagerService().getChannelFromCache(getChannelId());
		Boolean isOpen = ((_channel != null && new Long(2).equals(_channel.getIsOpenChannel())) ? false : true);//等于2时为验证，不开放。
		getDomain().setChannel(getChannel());// 设置当前频道
		getDomain().setSite(getChannel().getSite());// 设置当前站点
		CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");
		if (getDomain().getId() != null && getDomain().getAuthorId() != null) {// 修改
			if (!isOpen && !permService.haveThePermission(getLoginer(), "CMS_DOCUMENT_MODI", getDomain().getId())
			        && !permService.haveThePermissionAndDocAuthor(getLoginer(), "CMS_DOCUMENT_MODI_AUTHOR", getDomain().getId()))
				return NoPermissionHint();
		} else {// 新增
			if (!isOpen && !permService.haveThePermission(getLoginer(), "CMS_DOCUMENT_ADD", 2, getChannelId()))
				return NoPermissionHint();
		}
		// Lucene 同步
		Long isAuthor = domain.getAuthorId();
		if (isOpen && getDomain().getAuthorId() == null) {
			getDomain().setAuthorId(Constants.PUBLIC_USERID);
			getDomain().setAuthorCname(getLoginer().getUsername());
		}
		// 获得模板对象
		Long templateId = getChannelManagerService().getDefualutTemplateId(getChannel().getId(), Template.TYPE_FORM);
		Template template = tManagerService.get(templateId);
		// 获得动态脚本执行对象
		com.cyberway.common.service.ScriptService scriptService = (com.cyberway.common.service.ScriptService) com.cyberway.core.utils.ServiceLocator
				.getBean("scriptService");
		ScriptEngine scriptEngine = scriptService.getEngine();
		// 需设置当前运行环境

		if (!StringUtil.isEmpty(template.getBeforsavescript()))// 执行保存前脚本
			scriptEngine.eval(template.getBeforsavescript());

		setTemplateUrl(getTemplateService().getTemplatePage(templateId));
		
		domain = service.saveOrUpdate(domain);
		if (Constants.LUCENESYNCHROISMTYPE) {
			if (isAuthor == null) {
				luceneSynchroismService.documnetLucene(domain.getId().toString(), "insert");
			} else {
				luceneSynchroismService.documnetLucene(domain.getId().toString(), "update");
			}
		}

		setSiteId(_channel.getSite().getOid());

		// 执行保存后脚本
		// 重需设置当前运行环境(domain)
		if (!StringUtil.isEmpty(template.getAftersavescript()))
			scriptEngine.eval(template.getAftersavescript());
		setId(domain.getId());
		addActionMessage(getText("RESOURCE.HINTINFO.SAVESUCCESS"));
		// 保存并发布
		if (getIsPublic() != null && getIsPublic().equals("TRUE")) {
			this.setKeys(domain.getId() + "");
			htmlSynchroismService.deleteStaticHtmlByDocumentId(domain.getId(), getChannel().getId());//删除文件
			if(getChannel().getId()==173L){
				htmlSynchroismService.deleteStaticHtmlByChannelId(33L);
			}
			return adminIssue();
		} else if (domain.getIssued() == 5) {
			CmsSite cmsSite = _channel.getSite();
			if (cmsSite != null && (cmsSite.getIsSustainWap()== null || !cmsSite.getIsSustainWap())) {
			} else {
				if (getDomain().getDocType() == null) {
					domain.setDocType(0l);
				}
			}
			htmlSynchroismService.deleteStaticHtmlByDocumentId(domain.getId(), getChannel().getId());//删除文件
			if(getChannel().getId()==173L){
				htmlSynchroismService.deleteStaticHtmlByChannelId(33L);
			}
			pubStaDoc();//异步分发
		}

		isCloseWindow = "close";

		// 操作完后，跳转地址
		if (!StringUtil.isEmpty(fromUrl)) {
			return "tofrom";
		}
		return result;
	}

	/**
	 * 静态异步分发
	 * 
	 * @author Dicky
	 * @time 2012-9-13 17:44:54
	 * @version 1.0
	 */
	private void pubStaDoc() {
		new Thread() {
			@Override
			public void run() {
				try {
					TemplateManagerService tManagerService = (TemplateManagerService) getServiceById("templateManagerService");
					CmsSite cmsSite = _channel.getSite();
					if (cmsSite != null
							&& (cmsSite.getIsSustainWap() == null || !cmsSite
									.getIsSustainWap())) {
						tManagerService.pubStaDoc(
								Constants.CMS_HYPERTEXT_TRANSFER_PROTOCOL
										+ cmsSite.getSitehttp() + ":"
										+ cmsSite.getSiteport(), getDomain()
										.getId().toString(), getChannelId(),
								1l, false);
					} else {
						if (getDomain().getDocType() == null) {
							domain.setDocType(0l);
						}
						if (getDomain().getDocType() == 0) {
							tManagerService.pubStaDoc(
									Constants.CMS_HYPERTEXT_TRANSFER_PROTOCOL
											+ cmsSite.getSitehttp() + ":"
											+ cmsSite.getSiteport(),
									getDomain().getId().toString(),
									getChannelId(), 1l, cmsSite
											.getIsSustainWap());
							tManagerService.pubStaDoc(
									Constants.CMS_HYPERTEXT_TRANSFER_PROTOCOL
											+ cmsSite.getSitehttp() + ":"
											+ cmsSite.getSiteport(),
									getDomain().getId().toString(),
									getChannelId(), 2l, cmsSite
											.getIsSustainWap());
						} else {
							tManagerService.pubStaDoc(
									Constants.CMS_HYPERTEXT_TRANSFER_PROTOCOL
											+ cmsSite.getSitehttp() + ":"
											+ cmsSite.getSiteport(),
									getDomain().getId().toString(),
									getChannelId(), getDomain().getDocType(),
									cmsSite.getIsSustainWap());
						}
					}

				} catch (Exception e) {
					logger.error("-静态分发失败-", e);
				}
			}
		}.start();
	}

	/**
	 * 删除表单信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String adminDelete() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> ids = StringUtil.splitToList(keys, ",");

			final String resCodeIssue = "CMS_DOCUMENT_DELETE";
			CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");
			final TemplateManagerService tManagerService = (TemplateManagerService) getServiceById("templateManagerService");
			for (Long id : ids) {
				if (!permService.haveThePermission(getLoginer(), resCodeIssue, id)
				        && !permService.haveThePermissionAndDocAuthor(getLoginer(), "CMS_DOCUMENT_DELETE_AUTHOR", id)) {
					return NoPermissionHint();
				}
			}

			// amway 直接删除文档
			try {
				if (isFlow()) {
					// 流程删除
					// getFlowService().deleteBizAndFlow(ids,getLoginer());
				} else {
					service.removeByIds(ids, getChannelId());
				}
			} catch (Exception e) {
				throw new Exception("删除失败");
			}

			this.addActionMessage("删除成功！");
			// lucene 同步
			if (Constants.LUCENESYNCHROISMTYPE) {
				luceneSynchroismService.documnetLucene(keys, "del");
			}
			for (Long documentId : ids) {
				//删除静态文件
				htmlSynchroismService.deleteStaticHtmlByDocumentId(documentId, getChannel().getId());
				if(getChannel().getId()==173L){
					htmlSynchroismService.deleteStaticHtmlByChannelId(33L);
				}
			}
			try {
				final CmsSite site = getChannel().getSite();
				final List<Long> docIds = ids;
				new Thread() {
					@Override
					public void run() {
						tManagerService.removePubDoc(site.getSitehttp(), getChannel(), docIds,true);
					}
				}.start();
			} catch (Exception e) {
				logger.error("", e);
				return adminList();

			}
		} else
			this.addActionError("请选择需删除的记录！");

		// 操作完后，跳转地址
		if (!StringUtil.isEmpty(fromUrl)) {
			return "tofrom";
		}
		return adminList();
	}

	public String adminShareDoc() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> ids = StringUtil.splitToList(keys, ",");

			final String resCodeIssue = "CMS_DOCUMENT_MODI";
			CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");
			for (Long id : ids) {
				if (!permService.haveThePermission(getLoginer(), resCodeIssue, id))
					return NoPermissionHint();
			}
			this.service.updateDocumentsIsShare(ids, getIsShare());
			this.addActionMessage("设置成功！");
		} else
			this.addActionError("请选择记录！");
		// 操作完后，跳转地址
		if (!StringUtil.isEmpty(fromUrl)) {
			return "tofrom";
		}
		return adminList();
	}

	public String adminExport() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> ids = StringUtil.splitToList(keys, ",");

			final String resCode = "CMS_DOCUMENT_EXPORT";
			CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");
			for (Long id : ids) {
				if (!permService.haveThePermission(getLoginer(), resCode, id))
					return NoPermissionHint();
			}
			File file;
			if (Constants.IS_REALPATH) {
				file = new File(Constants.ABSOLUTE_PATH + Constants.TEMPLATE_FILE);
			} else {
				file = new File(this.getHttpServletRequest().getRealPath(Constants.TEMPLATE_FILE));
			}
			FileOutputStream outputStream = new FileOutputStream(file);
			service.exportDatas(ids, outputStream, getChannel());
			outputStream.close();
			FileInputStream is = new FileInputStream(file);
			byte[] b = new byte[(int) file.length()];
			is.read(b, 0, (int) file.length());
			BlobFileObject bfo = new BlobFileObject();
			bfo.setContent(b);
			is.close();
			bfo.setFullfilename("doc.xml");
			this.getHttpServletRequest().setAttribute(
					Constants.DOWNLOAD_BLOB_FILE_OBJECT, bfo);
			// this.service.updateDocumentsIsShare(ids,getIsShare());
			return "export_download";
		} else
			this.addActionError("请选择记录！");
		// 操作完后，跳转地址
		if (!StringUtil.isEmpty(fromUrl)) {
			return "tofrom";
		}
		return adminList();
	}

	// 导入
	public String adminImport() throws Exception {
		if (_file != null && _file.length > 0) {
			String fileName = _fileFileName[0].toLowerCase();
			if (fileName.endsWith(".xml")) {
				FileInputStream inputStream = new FileInputStream(_file[0]);
				Validate.notNull(inputStream);
				SAXReader reader = new SAXReader();
				Document document = reader.read(inputStream);
				Element root = document.getRootElement();
				service.importFromXml(root, getChannel());
				inputStream.close();
				// 静态发布导入的文件
				try {
					Channel channel = channelService.get(getChannelId());
					CmsSite csmSite = siteService.get(channel.getSite().getOid());
					channelService.channelTemplate(csmSite, getChannel());
				} catch (Exception e) {
					logger.error("--", e);
					return "close";
				}
			} else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
				/**
				 * 读取Excel文件
				 */

				FileInputStream fis = new FileInputStream(_file[0]);
				Workbook wb = null;
				try {
					if (fileName.endsWith(".xls")) {
						wb = new HSSFWorkbook(fis);
					} else if (fileName.endsWith(".xlsx")) {
						wb = new XSSFWorkbook(fis);
					}
				} catch (Exception e) {
					logger.error("--", e);
				}
				//FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
				Sheet sheet = wb.getSheetAt(0);
				/**
				 * 获取总行数
				 */
				int rowNum = sheet.getPhysicalNumberOfRows();
				/***
				 * 获取标题、字段、方法、属性参数类型等
				 */
				Row row = sheet.getRow(0);
				int cellLength = row.getPhysicalNumberOfCells();
				String[] fieldName = new String[cellLength];
				Class<?>[] clazzes = new Class<?>[cellLength];
				Method[] ms = new Method[cellLength];
				try {
					Class<?> clazz = service.getFormClass(getChannelId());
					int i = 0;
					for (Cell cell : row) {
						String field = cell.getStringCellValue();
						String name = field.substring(field.lastIndexOf('(')+1,field.lastIndexOf(')'));
						fieldName[i] = name;
						Method mt0 = null;
						try{
							mt0 = clazz.getMethod("get"+StringUtils.capitalize(name));
						}catch(NoSuchMethodException e){
							addActionMessage("Excel的格式不对!");
							logger.error("--", e);
							return "close";
						}
						/**
						 * 参数属性类型
						 */
						Class<?> fieldType = mt0.getReturnType();
						clazzes[i] = fieldType;
						/**
						 * 寻找setter方法
						 */
						String m0 = "set" + StringUtils.capitalize(name);
						try {
							ms[i] = clazz.getMethod(m0, fieldType);
						} catch (NoSuchMethodException e) {
							Method[] ms_ = clazz.getMethods();
							for (int j = 0; j < ms_.length; j++) {
								if (ms_[j].getName().equals(m0)) {
									ms[i] = ms_[j];
									break;
								}
							}
						}
						i++;
					}
					/**
					 * 放值
					 * 
					 */
					Loginer loginer = getLoginer();
					Channel channel = channelService.get(getChannelId());
					CmsSite csmSite = siteService.get(channel.getSite().getOid());
					channelService.channelTemplate(csmSite, getChannel());
					Transmorph typeConverter = new Transmorph(new DefaultConverters());
					for (int j = 1; j < rowNum; j++) {
						Row row_ = sheet.getRow(j);
						BaseDocument doc = (BaseDocument)clazz.newInstance();
						doc.setId(service.getSequence());
						doc.setChannel(channel);
						doc.setSite(csmSite);
						doc.setAuthorId(loginer.getUserid());
						doc.setAuthorCname(loginer.getUsername());
						doc.setTimeLastUpdated(new Date());
						for (int k = 0; k < cellLength; k++) {
							Cell cell = row_.getCell(k);
							Object arg = getCellValue(cell, clazzes[k]);
							try {
								ms[k].invoke(doc, arg);
							} catch (Exception e) {
								try {
									Object arg_ = typeConverter.convert(arg,
											clazzes[k]);
									ms[k].invoke(doc, arg_);
								} catch (Exception ex) {
									logger.error("--", ex);
								}
							}
						}
						service.saveOrUpdate(doc);
					}
				} catch (Exception e) {
					addActionMessage("导入失败!");
					logger.error("--", e);
					return "close";
				}
			}
		}
		return "close";
	}

	private Object getCellValue(Cell cell, Class<?> paramType) {
		Object arg = null;
		int type = cell.getCellType();
		switch (type) {
		case Cell.CELL_TYPE_STRING:
			arg = cell.getStringCellValue().trim();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			arg = cell.getNumericCellValue();
			break;
		case Cell.CELL_TYPE_BLANK:
			arg = null;
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			arg = cell.getBooleanCellValue();
			break;
		default:
			try {
				if (paramType.getName().equals("java.util.Date")) {
					arg = cell.getDateCellValue();
				} else {
					arg = cell.getCellFormula();
				}
			} catch (Exception e) {
				logger.error("--", e);
			}
		}
		return arg;
	}

	/**
	 * 过期操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public String adminOverdue() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> ids = StringUtil.splitToList(keys, ",");

			final String resCodeIssue = "CMS_DOCUMENT_MODI";
			CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");
			for (Long id : ids) {
				if (!permService.haveThePermission(getLoginer(), resCodeIssue, id))
					return NoPermissionHint();
			}
			service.updateDocumentsState(ids, -1);
			/*
			 * Iterator it = ids.iterator(); while(it.hasNext()) { domain =
			 * service.get((Long)it.next()); if(domain != null) {
			 * domain.setStatus(-1);//过期标记 domain.setTimeDeleted(new Date());
			 * service.saveOrUpdate(domain); } }
			 */
			this.addActionMessage("标记过期成功！");
		} else
			this.addActionError("请选择需标记过期的记录！");
		// 操作完后，跳转地址
		if (!StringUtil.isEmpty(fromUrl)) {
			return "tofrom";
		}
		return adminList();
	}

	/**
	 * 移动文档
	 * 
	 * @return
	 * @throws Exception
	 */
	public String adminMove() throws Exception {
		if (getId() == null) {
			this.addActionError("请选择移动的目标频道！");
		}
		if (!StringUtil.isEmpty(keys)) {
			List<Long> ids = StringUtil.splitToList(keys, ",");

			// 检测移动文档时，表单类型是否相同
			BaseDocument bd = service.getDocument(ids.get(0));
			if (bd != null && bd.getChannel() != null) {
				if (!getChannelManagerService().checkCanMoveDocBychannel(bd.getChannel().getId(), getId()))
					throw new Exception(getText("文档不能被移动，表单结构不相同！"));
			}

			final String resCodeIssue = "CMS_DOCUMENT_MODI";
			CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");
			for (Long id : ids) {
				if (!permService.haveThePermission(getLoginer(), resCodeIssue, id))
					return NoPermissionHint();
			}

			this.service.updateDocumentsChannel(ids, getId());
			this.addActionMessage("文档移动成功！");
		} else
			this.addActionError("请选择需移动的记录！");
		// 操作完后，跳转地址
		if (!StringUtil.isEmpty(fromUrl)) {
			return "tofrom";
		}
		return adminList();
	}

	/**
	 * 设置置顶
	 * 
	 * @return
	 * @throws Exception
	 */
	public String adminSetTop() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> ids = StringUtil.splitToList(keys, ",");

			final String resCodeIssue = "CMS_DOCUMENT_MODI";
			CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");
			for (Long id : ids) {
				if (!permService.haveThePermission(getLoginer(), resCodeIssue, id))
					return NoPermissionHint();
			}
			if (((DocumentCommonService) getService()).saveFieldDocs(getChannelId(), ids, fieldName, getType()))
				this.addActionMessage(_type == 0 ? "撤销置顶成功!" : "设置成功！");
			else
				this.addActionMessage(_type == 0 ? "撤销置顶失败!" : "设置失败！");
		} else
			this.addActionError("请选择需设置的记录！");
		// 操作完后，跳转地址
		if (!StringUtil.isEmpty(fromUrl)) {
			return "tofrom";
		}
		return adminList();
	}

	/**
	 * 设置最新
	 * 
	 * @return
	 * @throws Exception
	 */
	public String adminSetNew() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> ids = StringUtil.splitToList(keys, ",");

			final String resCodeIssue = "CMS_DOCUMENT_MODI";
			CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");
			for (Long id : ids) {
				if (!permService.haveThePermission(getLoginer(), resCodeIssue, id))
					return NoPermissionHint();
			}
			if (((DocumentCommonService) getService()).saveFieldDocs(getChannelId(), ids, fieldName, getType()))
				this.addActionMessage(_type == 0 ? "撤销最新成功!" : "设置最新成功！");
			else
				this.addActionMessage(_type == 0 ? "撤销最新失败!" : "设置最新失败！");
		} else
			this.addActionError("请选择需设置的记录！");
		// 操作完后，跳转地址
		if (!StringUtil.isEmpty(fromUrl)) {
			return "tofrom";
		}
		return adminList();
	}

	/**
	 * 设置某属性值
	 * 
	 * @return
	 * @throws Exception
	 */
	public String setProperteisItem() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> ids = StringUtil.splitToList(keys, ",");

			final String resCodeIssue = "CMS_DOCUMENT_MODI";
			CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");
			for (Long id : ids) {
				if (!permService.haveThePermission(getLoginer(), resCodeIssue, id))
					return NoPermissionHint();
			}
			if (((DocumentCommonService) getService()).saveFieldDocs(getChannelId(), ids, fieldName, getType()))
				this.addActionMessage(_type == 0 ? "撤销成功!" : "设置成功！");
			else
				this.addActionMessage(_type == 0 ? "撤销失败!" : "设置失败！");
		} else
			this.addActionError("请选择需设置的记录！");
		// 操作完后，跳转地址
		if (!StringUtil.isEmpty(fromUrl)) {
			return "tofrom";
		}
		return adminList();
	}

	/**
	 * 还原过期操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public String adminUnoverdue() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> ids = StringUtil.splitToList(keys, ",");

			final String resCodeIssue = "CMS_DOCUMENT_DELETE";
			CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");
			for (Long id : ids) {
				if (!permService.haveThePermission(getLoginer(), resCodeIssue, id))
					return NoPermissionHint();
			}
			this.service.updateDocumentsState(ids, 1);// 还原成正常文档

			this.addActionMessage("还原文档成功！");
		} else
			this.addActionError("请选择需还原文档的记录！");

		return overdueDoc();
	}

	/**
	 * 信息静态采集
	 * 
	 * @return
	 * @throws Exception
	 */
	public String adminStaticIssue() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> ids = StringUtil.splitToList(keys, ",");
			// 去掉重复的IDS防止选中了，再选中更多记录的分页按钮将产生重复信息
			ids = removeDuplicateWithOrder(ids);
			final String resCodeIssue = "CMS_DOCUMENT_ISSUE";
			CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");
			for (Long id : ids) {
				if (!permService.haveThePermission(getLoginer(), resCodeIssue, id))
					return NoPermissionHint();
			}
			try {
				this.addActionMessage("静态采集分发成功！");
				_channel = getChannelManagerService().getChannelFromCache(getChannelId());
				pubStaDocs(ids);
			} catch (Exception e) {
				logger.error("", e);
			}
		} else
			this.addActionError("请选择需静态采集的记录！");
		// 操作完后，跳转地址
		if (!StringUtil.isEmpty(fromUrl)) {
			return "tofrom";
		}
		return adminList();
	}

	/**
	 * 信息发布
	 * 
	 * @return
	 * @throws Exception
	 */
	public String adminIssue() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> ids = StringUtil.splitToList(keys, ",");
			// 去掉重复的IDS防止选中了，再选中更多记录的分页按钮将产生重复信息
			ids = removeDuplicateWithOrder(ids);
			final String resCodeIssue = "CMS_DOCUMENT_ISSUE";
			CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");
			for (Long id : ids) {
				if (!permService.haveThePermission(getLoginer(), resCodeIssue, id))
					return NoPermissionHint();
			}
			try {

				if (((DocumentCommonService) getService()).saveIssueDocs(ids, _channelId)) {
					((DocumentCommonService) getService()).flush();
					this.addActionMessage("发布成功！");
					CmsSite site = getChannel().getSite();
					if(new Integer(1).equals(site.getIsLogined()) && new Long(2).equals(getChannel().getIsOpenChannel())){
						for (Long documentId : ids) {
							boolean rs = htmlSynchroismService.deleteStaticHtmlByDocumentId(documentId, getChannel().getId());
						}
						if(getChannel().getId()==173L){
							htmlSynchroismService.deleteStaticHtmlByChannelId(33L);
						}
					}
					// lucene 同步
					if (Constants.LUCENESYNCHROISMTYPE) {
						luceneSynchroismService.documnetLucene(keys, "update");
					}
					_channel = getChannelManagerService().getChannelFromCache(getChannelId());
					pubStaDocs(ids);
				} else {
					this.addActionMessage("发布失败！");
				}

			} catch (Exception e) {
				logger.error("", e);
			}
		} else
			this.addActionError("请选择需发布的记录！");
		// 操作完后，跳转地址
		if (!StringUtil.isEmpty(fromUrl)) {
			return "tofrom";
		}
		return adminList();
	}

	/**
	 * 异步静态分发
	 * 
	 * @author Dicky
	 * @time 2012-9-13 17:44:24
	 * @version 1.0
	 * @param ids
	 */
	private void pubStaDocs(final List<Long> ids) {
		new Thread() {
			@Override
			public void run() {
				try {
					TemplateManagerService tManagerService = (TemplateManagerService) getServiceById("templateManagerService");
					CmsSite cmsSite = _channel.getSite();
					if (cmsSite != null && !cmsSite.getIsSustainWap()) {
						tManagerService.pubStaDoc(
								Constants.CMS_HYPERTEXT_TRANSFER_PROTOCOL
										+ cmsSite.getSitehttp() + ":"
										+ cmsSite.getSiteport(), keys,
								_channelId, 1l, cmsSite.getIsSustainWap());
					} else {
						BaseDocument baseDocument = null;
						if (ids.size() > 0) {
							baseDocument = service.get(ids.get(0));
							if (baseDocument.getDocType() == null) {
								baseDocument.setDocType(0l);
							}
							if (baseDocument.getDocType() == 0) {
								tManagerService
										.pubStaDoc(
												Constants.CMS_HYPERTEXT_TRANSFER_PROTOCOL
														+ cmsSite.getSitehttp()
														+ ":"
														+ cmsSite.getSiteport(),
												keys, _channelId, 1l, cmsSite
														.getIsSustainWap());
								tManagerService
										.pubStaDoc(
												Constants.CMS_HYPERTEXT_TRANSFER_PROTOCOL
														+ cmsSite.getSitehttp()
														+ ":"
														+ cmsSite.getSiteport(),
												keys, _channelId, 2l, cmsSite
														.getIsSustainWap());
							} else {
								tManagerService
										.pubStaDoc(
												Constants.CMS_HYPERTEXT_TRANSFER_PROTOCOL
														+ cmsSite.getSitehttp()
														+ ":"
														+ cmsSite.getSiteport(),
												keys, _channelId, baseDocument
														.getDocType(), cmsSite
														.getIsSustainWap());
							}
						}
					}
				} catch (Exception e) {
					logger.error("-静态分发失败-", e);
				}
			}
		}.start();
	}

	public String adminHeadline() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			Long id = Long.valueOf(keys);
			final String resCodeIssue = "CMS_DOCUMENT_HEADLINE";
			CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");
			if (!permService.haveThePermission(getLoginer(), resCodeIssue, id))
				return NoPermissionHint();
			if (((DocumentCommonService) getService()).saveHeadlineDocs(id, _channelId))
				this.addActionMessage("设置成功！");
			else
				this.addActionMessage("设置失败！");
		} else
			this.addActionError("请选择需设置头条的记录！");
		// 操作完后，跳转地址
		if (!StringUtil.isEmpty(fromUrl)) {
			return "tofrom";
		}
		return adminList();
	}

	/**
	 * 撤销发布
	 * 
	 * @return
	 * @throws Exception
	 */
	public String adminUnIssue() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> ids = StringUtil.splitToList(keys, ",");
			// 去掉重复的IDS防止选中了，再选中更多记录的分页按钮将产生重复信息
			ids = removeDuplicateWithOrder(ids);
			final String resCodeIssue = "CMS_DOCUMENT_UNISSUE";
			CmsPermissionService permService = (CmsPermissionService) this.getServiceById("cmsPermissionService");
			final TemplateManagerService tManagerService = (TemplateManagerService) getServiceById("templateManagerService");
			for (Long id : ids) {
				if (!permService.haveThePermission(getLoginer(), resCodeIssue, id))
					return NoPermissionHint();
			}

			try {

				if (((DocumentCommonService) getService()).saveUnIssueDocs(ids, _channelId)) {
					this.addActionMessage("撤销发布成功！");
					final CmsSite site = getChannel().getSite();
					for (Long documentId : ids) {
						htmlSynchroismService.deleteStaticHtmlByDocumentId(documentId, getChannel().getId());
					}
					if(getChannel().getId()==173L){
						htmlSynchroismService.deleteStaticHtmlByChannelId(33L);
					}
					// lucene 同步
					if (Constants.LUCENESYNCHROISMTYPE) {
						luceneSynchroismService.documnetLucene(keys, "update");
					}

					final List<Long> docIds = ids;
					new Thread() {
						@Override
						public void run() {
							tManagerService.removePubDoc(site.getSitehttp(), getChannel(), docIds,false);
						}
					}.start();
				} else {
					this.addActionMessage("撤销发布失败！");
				}
			} catch (Exception e) {
				logger.error("", e);
			}

		} else
			this.addActionError("请选择需撤销发布的记录！");
		// 操作完后，跳转地址
		if (!StringUtil.isEmpty(fromUrl)) {
			return "tofrom";
		}
		return adminList();
	}

	/**
	 * 获取业务对象列表的函数.
	 */
	protected void doListEntity(CriteriaSetup criteriaSetup, String tableId, int pageSize) throws Exception {
		int totalRows1 = RequestUtils.getTotalRowsFromRequest(getHttpServletRequest(), tableId);
		if (totalRows1 < 0) {
			totalRows1 = 100000000;// getService().getCount(criteriaSetup);
		}
		Limit limit = RequestUtils.getLimit(getHttpServletRequest(), tableId, totalRows1, Page.DEFAULT_PAGE_SIZE);
		Page page = null;
		if (isFlow()) {
			// page = getService().findECPage(limit, criteriaSetup);
			page = ((DocumentCommonService) getService()).findECPage(limit, criteriaSetup, getChannel(), getLoginer());
		} else
			// page = getService().findECPage(limit, criteriaSetup);
			page = ((DocumentCommonService) getService()).findECPage(limit, criteriaSetup, getChannel(), getLoginer());
		setItems((List) page.getResult());
		// limit = RequestUtils.getLimit(getHttpServletRequest(),
		// tableId,page.getTotalCount(), Page.DEFAULT_PAGE_SIZE);
		getHttpServletRequest().setAttribute(tableId + "_totalRows", page.getTotalCount());
		RequestUtils.setTotalRows(getHttpServletRequest(), page.getTotalCount());
	}

	/**
	 * 返回当前频道，是否带流程
	 * 
	 * @return
	 */
	private boolean isFlow() {
		// getDomain().getIssued()<2 &&
		if (getChannel() != null && getChannel().getIsflow() == 1)
			return true;
		else
			return false;
	}

	public Channel getChannel() {
		if (_channel == null)
			_channel = getChannelManagerService().getChannelFromCache(getChannelId());
		return _channel;
	}

	public void setChannel(Channel channel) {
		this._channel = channel;
	}

	public String getTemplateUrl() {
		return _templateUrl;
	}

	public void setTemplateUrl(String templateUrl) {
		_templateUrl = templateUrl;
	}

	public Long getChannelId() {
		return _channelId;
	}

	public void setChannelId(Long channelId) {
		_channelId = channelId;
	}

	public Long getTemplateId() {
		// 若_templateId为空，则自动取当前频道的表单模板id
		/*
		 * if(_templateId==null&&getChannel().getFormTemplate()!=null){
		 * _templateId=getChannel().getFormTemplate().getId(); }
		 */
		return _templateId;
	}

	public void setTemplateId(Long templateId) {
		this._templateId = templateId;
	}

	public File[] getUploadFiles() {
		return _uploadFiles;
	}

	public void setUploadFiles(File[] uploadFiles) {
		_uploadFiles = uploadFiles;
	}

	public Long getSiteId() {
		return _siteId;
	}

	public void setSiteId(Long siteid) {
		this._siteId = siteid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.core.web.BaseFlowController#getDomainClass()
	 */
	public Class getDomainClass() {
		return _formClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.core.web.BaseFlowController#get()
	 */
	public void get() {
		domain = (BaseDocument) service.get(_formClass, id);
	}

	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public String getTemplateName() {
		return _templateName;
	}

	public void setTemplateName(String templateName) {
		this._templateName = templateName;
	}

	public int getPreview() {
		return preview;
	}

	public void setPreview(int preview) {
		this.preview = preview;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		this._type = type;
	}

	public String getFromUrl() {
		return fromUrl;
	}

	public void setFromUrl(String fromUrl) {
		this.fromUrl = fromUrl;
	}

	public int getIsShare() {
		return isShare;
	}

	public void setIsShare(int isShare) {
		this.isShare = isShare;
	}

	public String getActivityname() {
		return activityname;
	}

	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}

	public String getFlowname() {
		return flowname;
	}

	public void setFlowname(String flowname) {
		this.flowname = flowname;
	}

	public String getActivityid() {
		return activityid;
	}

	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}

	public File[] get_file() {
		return _file;
	}

	public void set_file(File[] _file) {
		this._file = _file;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getIssued() {
		return issued;
	}

	public void setIssued(int issued) {
		this.issued = issued;
	}

	public void loadDocument() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		criteria.addFilter("channel.id", _channelId);
		if (pageIndex == 0)
			pageIndex = 1;
		if (pageSize == 0)
			pageSize = Page.DEFAULT_PAGE_SIZE;
		_formClass = this.service.getFormClass(_channelId);
		com.cyberway.common.message.utils.Limit limit = new com.cyberway.common.message.utils.Limit(getPageIndex(), pageSize);
		Page page = service.listPage(limit, criteria, _formClass);
		setPageData(page);

	}

	/*
	 * 移除List中重复的记录
	 */
	@SuppressWarnings("unchecked")
	public List<Long> removeDuplicateWithOrder(List<Long> arlList) {
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = arlList.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		arlList.clear();
		arlList.addAll(newList);
		return arlList;
	}

	/**
	 * 检查doc文档是否存在
	 * 
	 * @throws IOException
	 */
	public void checkUpHaveDoc() throws IOException {
		String path = ServletActionContext.getServletContext().getRealPath(getHttpServletRequest().getParameter("path"));
		String msgStr = "";
		if (new File(path).exists()) {// 文档存在
			msgStr = "1";
		} else {// 文档不存在
			msgStr = "0";
		}
		outWrite(msgStr);
	}

	public String[] get_fileFileName() {
		return _fileFileName;
	}

	public void set_fileFileName(String[] name) {
		_fileFileName = name;
	}

	@Override
	public void prepare() throws Exception {
		String method = ActionContext.getContext().getActionInvocation().getProxy().getMethod();
		if("saveResume".equals(method)){
			
		}
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

}
