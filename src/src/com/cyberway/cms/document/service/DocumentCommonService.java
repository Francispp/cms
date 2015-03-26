package com.cyberway.cms.document.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import ognl.Ognl;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocumentFactory;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.ecside.table.limit.Limit;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.cyberway.cms.Constants;
import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.component.docShare.domain.DocShareRecord;
import com.cyberway.cms.component.docShare.domain.DocShareRelation;
import com.cyberway.cms.component.docShare.service.DocShareRecordService;
import com.cyberway.cms.component.docShare.service.DocShareRelationService;
import com.cyberway.cms.component.webuser.domain.WebUser;
import com.cyberway.cms.component.wsr.service.SubcreibeService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsBaseDocument;
import com.cyberway.cms.domain.CmsShareDocument;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.Log;
import com.cyberway.cms.domain.Template;
import com.cyberway.cms.form.domain.BasicDocument;
import com.cyberway.cms.form.domain.CoreForm;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.cms.form.service.CoreFormService;
import com.cyberway.cms.log.service.LogManagerService;
import com.cyberway.cms.permission.service.CmsPermissionService;
import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.cms.webservice.service.HtmlSynchroismService;
import com.cyberway.common.attachment.domain.Attachment;
import com.cyberway.common.attachment.domain.FCKFile;
import com.cyberway.common.attachment.service.AttachmentManagerService;
import com.cyberway.common.attachment.service.FCKFileManagerService;
import com.cyberway.common.user.service.UserManagerService;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.objects.UserFrame;
import com.cyberway.core.utils.BeanUtil;
import com.cyberway.core.utils.FileUtil;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author caiw
 * 
 *         信息通用业务Service
 * 
 * @param <T>
 */
public class DocumentCommonService extends HibernateEntityDao<BaseDocument> {

	private static int i = 0;
	private ChannelManagerService channelService;// 频道管理Service
	private SiteCache siteCache;// 站点缓存service
	private TemplateManagerService templateManagerService;// 模板管理service
	private AttachmentManagerService attachmentManagerService;
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.sss");
	static final int BUFFER = 8888;

	public AttachmentManagerService getAttachmentManagerService() {
		return attachmentManagerService;
	}

	public void setAttachmentManagerService(AttachmentManagerService attachmentManagerService) {
		this.attachmentManagerService = attachmentManagerService;
	}

	public LogManagerService getLogManagerService() {
		return (LogManagerService) ServiceLocator.getBean("logManagerService");
	}

	public UserManagerService getUserManagerService() {
		return (UserManagerService) ServiceLocator.getBean("userManagerService");
	}

	/**
	 * 获得站点下使用所有的表单class
	 * 
	 * @param siteid
	 * @return
	 * @throws Exception
	 */
	public List<Class> getFormsClassBySiteId(Long siteid) throws Exception {
		List<Class> fss = new ArrayList();
		List<Channel> channels = channelService.getChannelsBySite(siteid);
		for (Channel channel : channels) {
			try {
				Class temp = this.getFormClass(channel.getId());
				if (temp != null && !fss.contains(temp))
					fss.add(temp);
			} catch (Exception e) {// 未指定表单时，会抛出

			}
		}
		return fss;
	}

	/**
	 * 获得指定表单模板对应的表单类
	 * 
	 * @param templateid
	 * @return
	 */
	public Class getFormClass(Long channelid) throws Exception {
		// 通过获得指定频道的默认表单模板
		Long _templateId = channelService.getDefualutTemplateId(channelid,
				Template.TYPE_FORM);
		// if(channel.getFormTemplate()==null
		// ||channel.getFormTemplate().getId()==null)
		if (_templateId == null)
			throw new Exception("未设置当前频道的默认表单模板！");
		// Long _templateId=channel.getFormTemplate().getId();

		return templateManagerService.getFormClassByTemplate(_templateId);
	}

	/**
	 * 获得文档的序号
	 * 
	 * @return
	 */
	public synchronized Long getSequence() {
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS");
		String seq = format.format(new Date());
		// String seq=UtilDateTime.getDateStrNumber(new
		// Date());//前20080505163212--yyyyMMddHHmmss
		// seq+=RandomStringUtils.randomNumeric(3);//四位随机数
		Long docID = new Long(seq) + i;
		i++;
		return docID;
	}

	/**
	 * 文档信息是否存在，若不存在，则为新增信息
	 * 
	 * @param docid
	 * @return
	 */
	public boolean isExistDocId(Long docid) {
		Object basedoc = get(CmsBaseDocument.class, docid);
		if (basedoc != null)
			return true;
		else
			return false;
	}

	/**
	 * 重载方法 支持EC控件的查询方法
	 * 
	 * @param filterMap
	 *            过滤条件
	 * @param limit
	 *            EC的limit对象
	 * @return
	 * @throws
	 * @throws Exception
	 *             TODO:去除日期类型模糊查询带的Oralce物理特性
	 */
	public Page findECPage(Limit limit, CriteriaSetup criteriaSetup,
			Channel channel, UserFrame loginer) throws Exception {
		// 获得当前频道的表单对象
		// entityClass=com.cyberway.cms.domain.Document.class;
		this.setEntityObject(getFormClass(channel.getId()));
		// this.getFormClass(channel.getId());

		Page page = super.findECPage(limit, criteriaSetup);

		return page;
	}

	/**
	 * 重载方法 支持EC控件的查询方法
	 * 
	 * @param filterMap
	 *            过滤条件
	 * @param limit
	 *            EC的limit对象
	 * @return
	 * @throws
	 * @throws Exception
	 *             TODO:去除日期类型模糊查询带的Oralce物理特性
	 */
	public Page findECPage(Limit limit, CriteriaSetup criteriaSetup,
			Long siteid, UserFrame loginer) throws Exception {
		// 获得当前频道的表单对象
		// entityClass=com.cyberway.cms.domain.Document.class;
		if (siteid == null)
			throw new Exception("未指定站点id！");
		List<Class> fcs = this.getFormsClassBySiteId(siteid);
		List<Channel> channels = channelService.getChannelsBySite(siteid);
		List<Long> channelids = new ArrayList();// 站点下所有频道id
		for (Channel channel : channels) {
			channelids.add(channel.getId());
		}
		// 增加过滤频道
		criteriaSetup.addFilter("channel.id", channelids);

		Page page = null;
		Page tpage = null;
		for (Class fs : fcs) {
			this.setEntityObject(fs);
			// this.getFormClass(channel.getId());
			tpage = super.findECPage(limit, criteriaSetup);
			if (page == null)
				page = tpage;
			else {// 不为空时,增加数据
				page.addPage(tpage.getTotalCount(), tpage.getData());
			}
		}
		return page;
	}

	/**
	 * 获得指定频道下，独单设置权限的
	 * 
	 * @param channel
	 * @param loginer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> getSelfDocuments0(Channel channel, Loginer loginer) {
		List<Long> selfDocs = null;
		// 查询指定频道下，单独设置权限的文档
		List<CmsBaseDocument> bds = this.find(
				" from CmsBaseDocument where channel.id=? and isInheritPerm=0",
				new Object[] { channel.getId() });
		if (bds != null && bds.size() > 0) {
			selfDocs = new ArrayList();
			CmsPermissionService permService = (CmsPermissionService) ServiceLocator
					.getBean("cmsPermissionService");
			for (CmsBaseDocument db : bds) {// 若无查看当前文档权限，放入selfDocs
				if (!permService.haveThePermission(loginer,
						SiteManagerService.DOCUMENT_VIEW_CODE, 3, db.getId())
						&& !permService.haveThePermissionAndDocAuthor(loginer,
								SiteManagerService.CMS_DOCUMENT_VIEW_AUTHOR,
								db.getId()))
					selfDocs.add(db.getId());
			}
		}
		return selfDocs;
	}
	
	/**
	 * 当有权访问频道时返回 无权访问的文档集合，
	 * 当无权访问频道时返回 有权访问的文档集合，
	 * @author Dicky
	 * @time 2012-9-11下午02:50:52
	 * @version 1.0
	 * @param channel
	 * @param loginer
	 * @param isAdmin 是否管理员
	 * @param channelPermission 是否有权访问此频道
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> getDocumentsByPer(Channel channel, Loginer loginer, boolean channelPermission) {
		List<Long> selfDocs = new ArrayList();
		// 查询指定频道下，单独设置权限的文档
		List<CmsBaseDocument> bds = null;
		if(channelPermission){//有权访问频道时，找出非继承的文档
			bds = this.find("from CmsBaseDocument where channel.id=? and isInheritPerm=0", new Object[] { channel.getId() });
		}else{//无权限访问频道时，找出所有文档
			bds = this.find("from CmsBaseDocument where channel.id=?", new Object[] { channel.getId() });
		}
		if (bds != null && bds.size() > 0) {
			CmsPermissionService permService = (CmsPermissionService) ServiceLocator.getBean("cmsPermissionService");
			for (CmsBaseDocument db : bds) {
				boolean docPermission = permService.haveThePermission(loginer,SiteManagerService.DOCUMENT_VIEW_CODE, Constants.DOCUMENT_TYPE, db.getId())
						|| permService.haveThePermissionAndDocAuthor(loginer, SiteManagerService.CMS_DOCUMENT_VIEW_AUTHOR, db.getId());
				if(channelPermission){
					if(!docPermission) selfDocs.add(db.getId());//找出无权访问的文档
				}else{
					if(docPermission) selfDocs.add(db.getId());//找出有权访问的文档
				}
			}
		}
		return selfDocs;
		
	}

	@SuppressWarnings("unchecked")
	public List<Long> getShareDocuments(Long siteId, Loginer loginer) {
		List<Long> shareDocs = null;
		List<CmsBaseDocument> bds = this.find(
				" from CmsBaseDocument where site.oid=? and isShare=1",
				new Object[] { siteId });
		if (bds != null && bds.size() > 0) {
			shareDocs = new ArrayList();
			for (CmsBaseDocument db : bds) {
				shareDocs.add(db.getId());
			}
		}
		return shareDocs;
	}

	@SuppressWarnings("unchecked")
	public List<BaseDocument> getShareDocumentsByChannel(Long channelId)
			throws Exception {
		Channel channel = channelService.getChannelFromCache(channelId);
		setEntityObject(getFormClass(channel.getId()));
		List<BaseDocument> documents = super.findBy("channel", channel);
		return documents != null ? documents : new ArrayList();
	}

	@SuppressWarnings("unchecked")
	public List<BaseDocument> getAllDocumentsByChannel(Long channelId)
			throws Exception {
		if (channelId == 241l) {
			System.out.println(channelId + "--------------------------------");
		}
		Channel channel = channelService.getChannelFromCache(channelId);
		Long templateId = channelService.getDefualutTemplateId(channelId,
				Template.TYPE_FORM);
		if (templateId != null) {
			setEntityObject(getFormClass(channel.getId()));
			List<BaseDocument> documents = super.findBy("channel", channel);
			return documents != null ? documents : new ArrayList();
		} else
			return new ArrayList();
	}

	public boolean createDocumentByShare(Long channelId, Long documentId)
			throws Exception {
		BaseDocument doc = (BaseDocument) getDocument(documentId);
		BaseDocument newDoc = doc.getClass().newInstance();
		BeanUtilsBean.getInstance().copyProperties(newDoc, doc);
		BeanUtil.updateObject(doc, newDoc);
		Channel channel = doc.getChannel();

		if (getFormClass(channel.getId()) == getFormClass(channelId)) {
			newDoc.setId(getSequence());
			newDoc.setChannel(channelService.getChannelFromCache(channelId));
			newDoc.setSite(doc.getSite());
			newDoc = saveOrUpdate(newDoc);
			CmsShareDocument shareDoc = new CmsShareDocument();
			Ognl.setValue("document.id", shareDoc, new Long(documentId));
			Ognl.setValue("docid", shareDoc, newDoc.getId());
			save(shareDoc);

			return true;
		}
		return false;
	}

	/**
	 * 查询申请记录列表
	 * @param pageIndex
	 * @param logClass
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page findAppLog(int pageIndex, Class logClass) throws Exception{
		int totalCount = 0;
		WebUser user = (WebUser) ActionContext.getContext().getSession().get(WebUser.WEB_USER_IN_SESSION);
		Long userId = -1L;
		if (user != null){
			userId = user.getOid();
		}
		List result = null;
		int startIndex = (pageIndex - 1) * 10;
		result = getSession().createCriteria(logClass).add(
				Restrictions.eq("authorId", userId)).setFirstResult(startIndex)
				.setMaxResults(10).addOrder(Order.desc("submitTime")).list();
		totalCount = Integer.parseInt(getSession().createCriteria(logClass)
				.add(Restrictions.eq("authorId", userId)).setProjection(
						Projections.rowCount()).uniqueResult().toString());
		return new Page(startIndex, totalCount, 10, result);
	}

	/**
	 * 查询已发布的文档列表
	 * 
	 * @param limit
	 * @param criteriaSetup
	 * @param channel
	 * @param where
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page findByPublishPage(Limit limit, CriteriaSetup criteriaSetup,
			Channel channel, String where) throws Exception {
		Channel chl = channelService.getChannelFromCache(channel.getId());

		// 获得当前频道的表单对象
		setEntityObject(getFormClass(channel.getId()));
		criteriaSetup.addFilter("channel", channel);
		CmsSite site = chl.getSite();
		// 只查询已发布的文档
		criteriaSetup.addFilter("issued", 5);
		// 只显示正常文档(正常1 删除0 过期-1)
		criteriaSetup.addFilter("status", 1);
		if (!StringUtil.isEmpty(where)) {// 增加用户自定义条件
			Criterion criterion = Restrictions.sqlRestriction(where);
			criteriaSetup.setInCriterion(criterion);
		}
		// 若需登录，只显示用户有权限的信息，无需登录：显示所有发布的信息
		if((site.getIsLogined()!=null && site.getIsLogined()==1)//站点设置需登录
				&& new Long(2).equals(chl.getIsOpenChannel())) {// 需要登录验证
			Loginer loginer = (Loginer) ActionContext.getContext().getSession().get(com.cyberway.core.Constants.USER_IN_SESSION);
			if (loginer!=null && !loginer.checkIsAdministratorUser()) {
				CmsPermissionService permService = (CmsPermissionService) ServiceLocator.getBean("cmsPermissionService");
				// 验证是否有权访问此频道
				boolean channelPermission = permService.haveThePermission(loginer,"CMS_DOCUMENT_VIEW",Constants.CHANNEL_TYPE,channel.getId());
				// 增加过滤单独指定文档的权限
				List<Long> selfsDoc = this.getDocumentsByPer(channel, loginer, channelPermission);
				if (selfsDoc != null && selfsDoc.size() > 0) {
					List<Criterion> lsCris = criteriaSetup.getAddCriterions();
					if (lsCris == null) lsCris = new ArrayList();
					if(channelPermission){//有权访问频道时则排除无权访问的文档。
						lsCris.add(Restrictions.not(Restrictions.in("id", selfsDoc)));
					}else{//无权访问频道时则采纳有权访问的文档。
						lsCris.add(Restrictions.in("id", selfsDoc));
					}
					criteriaSetup.setAddCriterions(lsCris);
				}else if(!channelPermission){//无权访问且无文档可访问时返回空集
					criteriaSetup.setFilters(new HashMap<String, Object>());
					criteriaSetup.addFilter("id", -1L);
				}
			}
		}
		Page page = super.findECPage(limit, criteriaSetup);
		return page;
	}

	/**
	 * 查询子频道已发布的文档列表
	 * 
	 * @param limit
	 * @param criteriaSetup
	 * @param channel
	 * @param where
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page findChildByPublishPage(Limit limit,
			CriteriaSetup criteriaSetup, Channel channel, String where)
			throws Exception {
		Channel chl = channelService.getChannelFromCache(channel.getId());
		// 设置子频道
		List childChannels = new ArrayList();
		chl.setChildren(channelService.getChild(chl.getId(), childChannels));

		// 获得 子频道表单对象
		{
			setEntityObject(getFormClass(((Channel) channelService.getFirstLeafChild(chl.getId())).getId()));
			List<Criterion> lsCris = criteriaSetup.getAddCriterions();
			if (lsCris == null)
				lsCris = new ArrayList();
			Criterion criterion = null;
			if (chl.getChildren().size() > 0) {
				criterion = Restrictions.in("channel", chl.getChildren());
				lsCris.add(criterion);
				criteriaSetup.setAddCriterions(lsCris);
			} else {
				criteriaSetup.addFilter("id", 0l);
			}
		}
		CmsSite site = chl.getSite();
		// 只查询已发布的文档
		criteriaSetup.addFilter("issued", 5);
		// 只显示正常文档(正常1 删除0 过期-1)
		criteriaSetup.addFilter("status", 1);

		if (!StringUtil.isEmpty(where)) {// 增加用户自定义条件
			Criterion criterion = Restrictions.sqlRestriction(where);
			criteriaSetup.setInCriterion(criterion);
		}
		// 若需登录，只显示用户有权限的信息，无需登录：显示所有发布的信息
		if (site.getIsLogined() != null && site.getIsLogined() == 1) {// 需要登录验证
			// 增加权限过滤　amway
			Loginer loginer = (Loginer) ActionContext.getContext().getSession().get(com.cyberway.core.Constants.USER_IN_SESSION);
			if (!loginer.checkIsAdministratorUser()) {
				List<Long> selfDocs = getChildDocsByPer(childChannels, loginer);
				if (selfDocs.size() > 0) {
					List<Criterion> lsCris = criteriaSetup.getAddCriterions();
					if (lsCris == null) lsCris = new ArrayList();
					lsCris.add(Restrictions.in("id", selfDocs));
					criteriaSetup.setAddCriterions(lsCris);
				}else{
					criteriaSetup.addFilter("id", 0l);
				}
			}
		}
		Page page = super.findECPage(limit, criteriaSetup);
		return page;
	}

	@SuppressWarnings("unchecked")
	private List<Long> getChildDocsByPer(List childChannels, Loginer loginer) {
		CmsPermissionService permService = (CmsPermissionService) ServiceLocator.getBean("cmsPermissionService");
		
		// 增加过滤单独指定文档的权限
		List<Long> selfDocs = new ArrayList<Long>();
		for (Object object : childChannels) {
			Channel chn = (Channel)object;
			// 验证是否有权访问此频道
			boolean channelPermission = permService.haveThePermission(loginer,"CMS_DOCUMENT_VIEW",Constants.CHANNEL_TYPE,chn.getId());
			List<CmsBaseDocument> bds = this.find("from CmsBaseDocument where channel.id=?", new Object[] { chn.getId() });
			if (bds != null && bds.size() > 0) {
				for (CmsBaseDocument db : bds) {
					if(db.getIsInheritPerm()==1 && channelPermission){
						selfDocs.add(db.getId());
					}else{
						boolean docPermission = permService.haveThePermission(loginer,SiteManagerService.DOCUMENT_VIEW_CODE, Constants.DOCUMENT_TYPE, db.getId())
							|| permService.haveThePermissionAndDocAuthor(loginer, SiteManagerService.CMS_DOCUMENT_VIEW_AUTHOR, db.getId());
						if(docPermission) selfDocs.add(db.getId());//找出有权访问的文档
					}
				}
			}
		}
		return selfDocs;
	}

	/**
	 * 查询站点所有已发布的文档列表
	 * 
	 * @param limit
	 * @param criteriaSetup
	 * @param where
	 * @param siteid
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	public Page findByPublishPageSiteDocs(Limit limit,
			CriteriaSetup criteriaSetup, String where, Long siteid,
			Channel channel) throws Exception {
		CmsSite site = null;
		if (channel == null || channel.getId() == null) {// 未指定频道，获得任意一频道
			if (siteid != null) {
				List<Channel> channels = channelService
						.getChannelsBySite(siteid);
				if (channels != null && channels.size() > 0) {
					channel = channels.get(0);
					site = channel.getSite();
				}
			} else
				throw new Exception("对不起，未指定站点Id或频道Id！");
		} else {
			Channel chl = channelService.getChannelFromCache(channel.getId());
			site = chl.getSite();
		}
		// 按站点过滤
		criteriaSetup.addFilter("site", site);
		// 获得当前频道的表单对象
		// entityClass=com.cyberway.cms.domain.Document.class;
		this.setEntityObject(getFormClass(channel.getId()));
		// 只查询已发布的文档
		// criteriaSetup.addFilter("issued", 5);
		// 只显示正常文档(正常1 删除0 过期-1)
		criteriaSetup.addFilter("status", 1);

		if (!StringUtil.isEmpty(where)) {// 增加用户自定义条件
			Criterion criterion = Restrictions.sqlRestriction(where);
			criteriaSetup.setInCriterion(criterion);
		}
		// 若需登录，只显示用户有权限的信息，无需登录：显示所有发布的信息
		if (site.getIsLogined() != null && site.getIsLogined() == 1) {// 需要登录验证
			// 增加权限过滤　amway
			Loginer loginer = (Loginer) ActionContext.getContext().getSession()
					.get(com.cyberway.core.Constants.USER_IN_SESSION);
			CmsPermissionService permService = (CmsPermissionService) ServiceLocator
					.getBean("cmsPermissionService");

			// 则查询当前站点下文档

			List<Channel> chls = channelService.getChannelsBySite(channel
					.getSite().getOid());
			List<Criterion> lsCris = criteriaSetup.getAddCriterions();
			if (lsCris == null)
				lsCris = new ArrayList();
			if (chls != null) {
				List permchl = new ArrayList();
				for (Channel chl : chls) {
					if (permService.haveThePermission(loginer,
							"CMS_DOCUMENT_VIEW", Constants.CHANNEL_TYPE,
							chl.getId())) {
						permchl.add(chl);
					}
				}
				Criterion criterion = null;
				if (permchl.size() > 0) {
					criterion = Restrictions.in("channel", permchl);
				} else
					criterion = Restrictions.sqlRestriction("1>2");
				if (criterion != null)

					lsCris.add(criterion);
			}

			criteriaSetup.setAddCriterions(lsCris);

			// 安利文档过滤规则 未发布的文档，只对作者可见，发布后有权限可见
			if (!loginer.checkIsAdministratorUser()) {
				List<Criterion> lsCris2 = criteriaSetup.getAddCriterions();
				if (lsCris2 == null)
					lsCris2 = new ArrayList();
				Criterion criterion = Restrictions
						.sqlRestriction("(this_.issued=5 or (this_.issued<5 and this_.author_id="
								+ loginer.getUserid() + "))");
				lsCris2.add(criterion);
				criteriaSetup.setAddCriterions(lsCris2);
			}
		} else {// 站点，无需登录验证
			// 只查询已发布的文档
			criteriaSetup.addFilter("issued", 5);
		}
		// this.getFormClass(channel.getId());
		Page page = super.findECPage(limit, criteriaSetup);

		return page;
	}

	/**
	 * 发布文档
	 * 
	 * @param ids
	 * @return
	 */
	public boolean saveIssueDocs(List<Long> ids, Long channelid)
			throws Exception {
		// Document doc=null;
		boolean succ = true;
		Class clazz = this.getFormClass(channelid);
		for (Long id : ids) {
			// doc=this.get(id);
			// 发布文档
			if (!saveIssueDoc(id, clazz)) {
				succ = false;
				break;
			}
		}
		// 一文多发
		if (succ)
			moreIssueDocs(ids, channelid);
		return succ;
	}
	
	
	


	/**
	 * 移除一文多发文档
	 * 
	 * @param documentId
	 * @throws Exception
	 */
	public void moreRemoveDocs(BaseDocument doc) throws Exception {
		Long channelId = doc.getChannel().getId();
		DocShareRelationService docShareRelationService = (DocShareRelationService) ServiceLocator
				.getBean("docShareRelationService");
		DocShareRecordService docShareRecordService = (DocShareRecordService) ServiceLocator
				.getBean("docShareRecordService");
		List<DocShareRelation> rels = docShareRelationService.findBy(
				"baseChannelId", channelId);
		List<DocShareRecord> records = docShareRecordService.findBy(
				"baseChannelId", channelId);
		for (DocShareRelation relation : rels) {
			if (relation.getIsDelete() == 1) {
				for (DocShareRecord record : records) {
					if (ObjectUtils.equals(doc.getId(), record.getBaseDocId())
							&& ObjectUtils.equals(relation.getBaseChannelId(),
									record.getBaseChannelId())
							&& ObjectUtils.equals(
									relation.getTargetChannelId(),
									record.getTargetChannelId())) {
						try {
							this.removeById(record.getTargetDocId());
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					}

				}
			}
		}

	}

	/**
	 * 一文多发
	 * 
	 * @param ids
	 * @param channelid
	 * @return
	 * @throws Exception
	 */
	public boolean moreIssueDocs(List<Long> ids, Long channelid)
			throws Exception {
		boolean succ = true;
		DocShareRelationService docShareRelationService = (DocShareRelationService) ServiceLocator
				.getBean("docShareRelationService");
		DocShareRecordService docShareRecordService = (DocShareRecordService) ServiceLocator
				.getBean("docShareRecordService");
		AttachmentManagerService attachService = (AttachmentManagerService) ServiceLocator
				.getBean("attachmentManagerService");

		List<DocShareRelation> rels = docShareRelationService.getAll();
		Loginer loginer = (Loginer) ActionContext.getContext().getSession()
				.get(com.cyberway.core.Constants.USER_IN_SESSION);
		// List<DocShareRecord> records = docShareRecordService.getAll();
		for (Long documentId : ids) {
			BaseDocument doc = (BaseDocument) getDocument(documentId);
			for (DocShareRelation relation : rels) {
				if (relation.getIsDefault() == 1
						&& ObjectUtils.equals(channelid,
								relation.getBaseChannelId())) {
					Long toChannelId = relation.getTargetChannelId();

					BaseDocument newDoc = doc.getClass().newInstance();
					BeanUtilsBean.getInstance().copyProperties(newDoc, doc);
					BeanUtil.updateObject(doc, newDoc);
					Channel channel = doc.getChannel();

					if (getFormClass(channel.getId()) == getFormClass(toChannelId)) {
						newDoc.setId(getSequence());
						newDoc.setChannel(channelService
								.getChannelFromCache(toChannelId));
						newDoc.setSite(doc.getSite());
						newDoc = saveOrUpdate(newDoc);
						List<Attachment> atts = attachService
								.getAttachsByDocId(documentId);
						for (Attachment att : atts) {
							Attachment newAtt = new Attachment();
							BeanUtilsBean.getInstance().copyProperties(newAtt,
									att);
							newAtt.setDocumentId(newDoc.getId());
							newAtt.setId(null);
							attachService.saveOrUpdate(newAtt);
						}
						DocShareRecord record = new DocShareRecord();
						record.setBaseChannelId(relation.getBaseChannelId());
						record.setBaseChannelName(relation.getBaseChannelName());
						record.setBaseDocId(documentId);
						record.setBaseSiteId(relation.getBaseSiteId());
						record.setBaseSiteName(relation.getBaseSiteName());
						record.setIsDefault(relation.getIsDefault());
						record.setTargetChannelId(relation.getTargetChannelId());
						record.setTargetChannelName(relation
								.getTargetChannelName());
						record.setTargetDocId(newDoc.getId());
						record.setTargetSiteId(relation.getTargetSiteId());
						record.setTargetSiteName(relation.getTargetSiteName());
						record.setUpdateTime(new Date());
						record.setUserId(loginer.getUserid());
						record.setUserName(loginer.getUsername());
						record.setRelationId(relation.getOid());
						docShareRecordService.saveOrUpdate(record);
					}

				}
			}
		}

		return succ;
	}

	public boolean saveHeadlineDocs(Long id, Long channelid) throws Exception {
		boolean succ = true;
		Class clazz = this.getFormClass(channelid);
		List<BaseDocument> docs = find("from " + clazz.getName()
				+ " where channel.id = ? and isHeadline = ?", new Object[] {
				channelid, 1l });
		for (BaseDocument doc : docs) {
			if (doc.getId() == id.longValue())
				continue;
			doc.setIsHeadline(0l);
			this.save(doc);
		}
		BaseDocument doc = (BaseDocument) this.get(clazz, id);
		if (doc != null) {
			Channel channel = channelService.getChannelFromCache(doc
					.getChannel().getId());
			if (doc.getIsHeadline() != null && doc.getIsHeadline() == 1l) {
				doc.setIsHeadline(0l);
			} else
				doc.setIsHeadline(1l);

			this.save(doc);
		} else {
			succ = false;
		}
		return succ;
	}

	/**
	 * 取消发布
	 * 
	 * @param ids
	 * @return
	 */
	public boolean saveUnIssueDocs(List<Long> ids, Long channelid)
			throws Exception {
		// Document doc=null;
		boolean succ = true;
		Class clazz = this.getFormClass(channelid);
		for (Long id : ids) {
			// doc=this.get(id);
			// 发布文档
			if (!saveUnIssueDoc(id, clazz)) {
				succ = false;
				break;
			}
		}
		return succ;
	}

	/**
	 * 修改文档属性值
	 * 
	 * @param ids
	 * @return
	 */
	public boolean saveFieldDocs(Long channelid, List<Long> ids,
			String fieldName, int type) throws Exception {
		boolean succ = true;
		for (Long id : ids) {

			try {
				BaseDocument doc = (BaseDocument) this.get(
						this.getFormClass(channelid), id);
				Ognl.setValue(fieldName, doc, new Long(type));
				save(doc);
			} catch (RuntimeException e) {
				e.printStackTrace();
				succ = false;
				break;
			}
		}
		return succ;
	}
	
	
	
	
	

	/**
	 * 发布文档
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean saveIssueDoc(Long id, Class clazz) throws Exception {
		BaseDocument doc = (BaseDocument) this.get(clazz, id);
		// 发布文档 0草稿 2待发 4已返
		if (doc.getIssued() == 0 || doc.getIssued() == 2
				|| doc.getIssued() == 4) {
			Channel channel = channelService.getChannelFromCache(doc
					.getChannel().getId());
			if (channel.getIsflow() == 1 && doc.getIssued() == 0)// 带流程的频道，不能在草稿状态下发布
				return false;
			/*
			 * List<Long> ids = new ArrayList<Long>(); ids.add(id);
			 */
			doc.setIssued(5);
			if (doc.getTimeIssued() == null)
				doc.setTimeIssued(new Date());
			this.save(doc);
			SubcreibeService subService = (SubcreibeService) ServiceLocator
					.getBean("SubcreibeService");
			subService.checkSub(doc.getId());// RSS订阅
			/* moreIssueDocs(ids, channel.getId()); */
			// createHtmlPage(doc);
			// 有可能发布成静态页面
		} else
			return false;
		return true;
	}

	/**
	 * 取消发布的文档
	 * 
	 * @param id
	 * @return
	 */
	public boolean saveUnIssueDoc(Long id, Class clazz) {
		BaseDocument doc = (BaseDocument) this.get(clazz, id);
		// 发布文档
		if (doc.getIssued() == 5) {
			doc.setIssued(4);
			// doc.setTimeIssued(new Date());
			// doc.setTimeEnd(new Date());
			this.save(doc);
			// deleteHtmlpage(doc);
			// 有可能发布成静态页面
		} else
			return false;
		return true;
	}

	/**
	 * {"草稿","正审","待发","已否","已返","已发"} {0,1,2,3,4,5}
	 * 
	 * @param ids
	 * @param issued
	 *            更新文档审核状态
	 * @throws Exception
	 */
	public void updateDocumentsIssued(List<Long> ids, int issued)
			throws Exception {

		if (ids != null && issued >= 0
				&& issued < BaseDocument.DOCUMENT_STATE.length) {
			for (Long id : ids) {
				updateDocumentIssued(id, issued);
			}
		} else
			throw new Exception("指定更新状态无效!");
	}

	/**
	 * {"草稿","正审","待发","已否","已返","已发"} {0,1,2,3,4,5}
	 * 
	 * @param id
	 * @param issued
	 *            更新文档审核状态
	 * @throws Exception
	 */
	public void updateDocumentIssued(Long id, int issued) throws Exception {

		if (id != null && issued >= 0
				&& issued < BaseDocument.DOCUMENT_STATE.length) {

			BaseDocument obj = getDocument(id);
			obj.setIssued(issued);
			if (issued == 5) {// 发布信息,设置发布时间
				obj.setTimeIssued(new Date());
			}
			save(obj);
		} else
			throw new Exception("指定更新状态无效!");
	}

	/**
	 * 保存文档信息
	 * 
	 * @param obj
	 *            信息对象
	 * @param channelid
	 *            指定信息保存的频道
	 * @return
	 */
	public BaseDocument saveDocInfo(BaseDocument obj, Long channelid) {
		// 在新增时，增加基础信息
		CmsBaseDocument basedoc = (CmsBaseDocument) get(CmsBaseDocument.class,
				obj.getId());

		if (basedoc == null) {// 新增
			basedoc = new CmsBaseDocument();
			if (channelid != null) {//
				Channel channel = channelService.getChannelFromCache(channelid);
				if (channel != null) {// 重新设置频道
					if (obj.getChannel() == null
							|| obj.getChannel().getId() == null)
						obj.setChannel(channel);
					if (obj.getSite() == null || obj.getSite().getOid() == null)
						obj.setSite(channel.getSite());
				}
			}
			basedoc.setId(obj.getId());
			basedoc.setChannel(obj.getChannel());
			basedoc.setSite(obj.getSite());
			save(basedoc);
			// this.flush();
			obj.setTimeLastUpdated(obj.getTimeCreated());
			// obj.setId(basedoc.getId());
		} else {
			obj.setTimeLastUpdated(new Date());
			// 已创建basedoc对象，
			if (basedoc.getChannel() == null
					|| basedoc.getChannel().getId() == null
					|| basedoc.getSite() == null) {
				// basedoc.setId(obj.getId());
				basedoc.setChannel(obj.getChannel());
				basedoc.setSite(obj.getSite());
				save(basedoc);
			}
		}

		save(obj);
		// this.flush();
		return obj;
	}

	/**
	 * 保存并返回对象
	 * 
	 * @param obj
	 * @return
	 */
	public BaseDocument saveOrUpdate(BaseDocument obj) {
		// 在新增时，增加基础信息
		CmsBaseDocument basedoc = (CmsBaseDocument) this.get(
				CmsBaseDocument.class, obj.getId());
		Log log = new Log();
		// Loginer loginer = new Loginer();
		Loginer loginer;
		if (ActionContext.getContext().getSession() != null)
			loginer = (Loginer) ActionContext.getContext().getSession()
					.get(com.cyberway.core.Constants.USER_IN_SESSION);
		else
			loginer = new Loginer();
		// CoreUser user = getUserManagerService().get(loginer.getUserid());

		if (basedoc == null) {
			// Object cbd=get(CmsBaseDocument.class, obj.getId());
			// if(cbd==null)
			basedoc = new CmsBaseDocument();
			/*
			 * else basedoc=(CmsBaseDocument)cbd;
			 */
			basedoc.setId(obj.getId());
			basedoc.setChannel(obj.getChannel());
			basedoc.setSite(obj.getSite());
			this.save(basedoc);

			log.setAction("新增");
			if (obj.getAuthorId() == null) {
				obj.setAuthorId(loginer.getUserid());
				obj.setAuthorCname(loginer.getUsername());
			}

			obj.setTimeLastUpdated(obj.getTimeCreated());
			// obj.setId(basedoc.getId());
		} else {
			log.setAction("修改");
			obj.setTimeLastUpdated(new Date());
			// 已创建basedoc对象，
			if (basedoc.getChannel() == null
					|| basedoc.getChannel().getId() == null
					|| basedoc.getSite() == null) {
				// basedoc.setId(obj.getId());
				basedoc.setChannel(obj.getChannel());
				basedoc.setSite(obj.getSite());
				save(basedoc);
				log.setAction("新增");
				if (obj.getAuthorId() == null) {
					obj.setAuthorId(loginer.getUserid());
					obj.setAuthorCname(loginer.getUsername());
				}
			}
		}

		// 增加修改记录
		log.setTarget(basedoc.getId());
		log.setTargetType(Constants.LOG_TARGET_TYPE_DOCUMENT);
		if (loginer != null) {
			log.setOperator(loginer.getUsername());
			log.setOperatorId(loginer.getUserid());
			log.setOperatorDepartment(loginer.getDeptname());
			log.setOperatorDepartmentId(loginer.getDeptcode());
		} else {
			log.setOperatorId(obj.getAuthorId());
			log.setOperator(obj.getAuthorCname());
		}
		getLogManagerService().saveOrUpdate(log);
		this.flush();
		
		save(obj);
		return obj;
	}

	/**
	 * 根据ID移除对象.
	 */
	public void removeById(Serializable id) {
		try {
			BaseDocument doc = getDocument((Long) id);
			moreRemoveDocs(doc);
			remove(doc);
		} catch (Exception e) {
			logger.error("--", e);
		}
	}
	
	public void removeByIds(List<Long> ids, Long channelId) {
		if(channelId!=null){
    		for (Long id : ids){
    			BaseDocument doc = null;
    			try {
    				Class objclass = this.getFormClass(channelId);
	    			doc = (BaseDocument) this.get(objclass, id);
    			} catch (Exception e) {
					logger.error("--", e);
				}
    			try {
					moreRemoveDocs(doc);
					getHibernateTemplate().delete(doc);
				} catch (Exception e1) {
					logger.error("--", e1);
				}
    			try {
	    			remove(doc);
    			} catch (Exception e) {
					logger.error("--", e);
				}
    		}
		}else{
			super.removeByIds(ids);
		}
	}

	/*
	 * 删除信息
	 * 
	 * @see com.cyberway.core.dao.HibernateGenericDao#remove(java.lang.Object)
	 */
	@Override
	public void remove(Object o) {
		// Loginer loginer
		// =(Loginer)ActionContext.getContext().getSession().get(com.cyberway.core.Constants.USER_IN_SESSION);
		// CoreUser user = getUserManagerService().get(loginer.getUserid());

		if (o instanceof BaseDocument) {
			Long id = ((BaseDocument) o).getId();
			/*
			 * Log log = new Log (); log.setAction("删除");
			 * log.setTarget(((BaseDocument)o).getId());
			 * log.setTargetType(Constants.LOG_TARGET_TYPE_DOCUMENT);
			 * log.setOperator(loginer.getUsername());
			 * log.setOperatorId(user.getUserid());
			 * log.setOperatorDepartment(loginer.getDeptname());
			 * log.setOperatorDepartmentId(user.getDeptcode());
			 */

			removeById(CmsBaseDocument.class, id);
			// 先删除与其相关附件
			attachmentManagerService.deleteByDocument(id);
			// 删除文档日志
			getLogManagerService().deleteByDocument(id);
		}
		super.remove(o);
	}

	/**
	 * 获得指定id的文档对象
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BaseDocument getDocument(Long id) throws Exception {
		CmsBaseDocument basedoc = (CmsBaseDocument) this.get(CmsBaseDocument.class, id);
		if(basedoc==null){
			throw new Exception("信息不存在！");
		}
		Class objclass = this.getFormClass(basedoc.getChannel().getId());
		BaseDocument doc = (BaseDocument) this.get(objclass, id);
		return doc;
	}

	/**
	 * 获得指定id的文档对象
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BaseDocument getFullDocument(Class entityClass,Serializable id){
		
		List list=this.find("from "+entityClass.getName()+" where "+getIdName(entityClass)+"=?", new Object[]{id});
		BaseDocument doc=null;
		if(list!=null&&list.size()>0){
			Object obj = list.get(0);
			if(obj instanceof BaseDocument){
				doc = (BaseDocument)obj;
				doc.setChannel(siteCache.getChannelFromCach(doc.getChannel().getId()));
			}
		}
		return doc;
	}
	
	/**
	 * 
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public BaseDocument getLastDocByChannel(Long channelId) throws Exception {
		Class<?> objclass=this.getFormClass(channelId);
		DetachedCriteria criteria = DetachedCriteria.forEntityName(objclass.getName());
		criteria.add(Restrictions.eq("channel.id", channelId));
		criteria.add(Restrictions.eq("issued", 5));
		criteria.addOrder(Order.desc("timeLastUpdated"));
		List list = getHibernateTemplate().findByCriteria(criteria, 0, 1);
		if(list!=null&&list.size()>0){
			return (BaseDocument)(list.get(0));
		}else{
			throw new Exception("信息不存在，请在后台发布！");
		}
	}
	
	/**
	 * 根据id,类型获取上一个下一次经典案例文档对象
	 * @param id
	 * @param upordown
	 * @param code1
	 * @param code2
	 * @return
	 * @throws Exception
	 */
	public BaseDocument getupdownDocument(Long id,String upordown,String code) throws Exception {
		
		BasicDocument basedoc=(BasicDocument)this.get(BasicDocument.class, id);
		Class objclass=this.getFormClass(basedoc.getChannel().getId());
		List list=new ArrayList();
		if(upordown.equalsIgnoreCase("up")){
			list=this.find("from "+objclass.getName()+" where channel.id=? and (fieldString1=? or fieldString2=?) and "+getIdName(objclass)+"<? and issued=? order by "+getIdName(objclass)+"  desc", new Object[]{basedoc.getChannel().getId(),code,code,id,5});
		}else if(upordown.equalsIgnoreCase("down")){
			list=this.find("from "+objclass.getName()+" where channel.id=? and (fieldString1=? or fieldString2=?) and  "+getIdName(objclass)+">? and issued=? order by "+getIdName(objclass), new Object[]{basedoc.getChannel().getId(),code,code,id,5});
		}
		if(list.size()==0){
			list=this.find("from "+objclass.getName()+" where channel.id=?  and "+getIdName(objclass)+" =? and issued=? ", new Object[]{basedoc.getChannel().getId(),id,5});
		}
		Object obj=null;
		if(list!=null&&list.size()>0)
			obj=list.get(0);	
		BaseDocument doc=(BaseDocument)obj;
		return doc;
	}
	
	
	
	/**
	 * 根据id,频道获取上一个下一个文档对象
	 * @param id
	 * @param upordown
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public BaseDocument getupdownDocument(Long id,String upordown) throws Exception {
		CmsBaseDocument basedoc = (CmsBaseDocument) this.get(
				CmsBaseDocument.class, id);
		Class objclass = this.getFormClass(basedoc.getChannel().getId());
		List list=new ArrayList();
		if(upordown.equalsIgnoreCase("down")){
			list=this.find("from "+objclass.getName()+" where channel.id=?  and "+getIdName(objclass)+"<? and issued=? order by "+getIdName(objclass)+"  desc", new Object[]{basedoc.getChannel().getId(),id,5});
		}else if(upordown.equalsIgnoreCase("up")){
			list=this.find("from "+objclass.getName()+" where channel.id=? and  "+getIdName(objclass)+">? and issued=? order by "+getIdName(objclass), new Object[]{basedoc.getChannel().getId(),id,5});
		}
		if(list.size()==0){
			list=this.find("from "+objclass.getName()+" where channel.id=?  and "+getIdName(objclass)+" =? and issued=? ", new Object[]{basedoc.getChannel().getId(),id,5});
		}
		Object obj=null;
		if(list!=null&&list.size()>0)
			obj=list.get(0);	
		BaseDocument doc=(BaseDocument)obj;
		return doc;
	}
	
	/**
	 * 根据id和指定排序字段,频道获取上一个下一个文档对象,注意前台有列表展示的话一定要采用相同排序,
	 * 如果排序字段非Id,则前台列表必须加id降序排序
	 * @param id 当前文档ID
	 * @param getUp 是否获取上一个(否则获取下一个)
	 * @param orderBy 排序字段
	 * @param asc 是否升序
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public BaseDocument getupdownDocument(Long id,boolean getUp,String orderBy, boolean asc) throws Exception {
		StringBuilder orderByBuilder = new StringBuilder(" order by ");
		StringBuilder sqlBuilder = new StringBuilder("from ");
		String selectValueSql = "";
		CmsBaseDocument basedoc = (CmsBaseDocument) this.get(
				CmsBaseDocument.class, id);
		Class objclass = this.getFormClass(basedoc.getChannel().getId());
		String idName = getIdName(objclass);
		boolean hasOrderBy = StringUtils.isNotBlank(orderBy);
		boolean orderById = idName.equalsIgnoreCase(orderBy);

		sqlBuilder.append(objclass.getName());
		sqlBuilder.append(" where channel.id=? and issued=?");

		if(hasOrderBy){
			orderByBuilder.append(orderBy);
			sqlBuilder.append(" and ");
			sqlBuilder.append(orderBy);
			StringBuilder selectValueSqlBuilder = new StringBuilder("(select ");
			selectValueSqlBuilder.append(orderBy);
			selectValueSqlBuilder.append(" from ");
			selectValueSqlBuilder.append(objclass.getName());
			selectValueSqlBuilder.append(" where ");
			selectValueSqlBuilder.append(idName);
			selectValueSqlBuilder.append("=");
			selectValueSqlBuilder.append(id);
			selectValueSqlBuilder.append(")");
			selectValueSql = selectValueSqlBuilder.toString();
			if(asc){
				if(getUp){
					orderByBuilder.append(" desc");
					sqlBuilder.append(orderById?"<?":("<"+selectValueSql));
				} else {
					orderByBuilder.append(" asc");
					sqlBuilder.append(orderById?">?":(">"+selectValueSql));
				}
			} else {
				if(getUp){
					orderByBuilder.append(" asc");
					sqlBuilder.append(orderById?">?":(">"+selectValueSql));
				} else {
					orderByBuilder.append(" desc");
					sqlBuilder.append(orderById?"<?":("<"+selectValueSql));
				}
			}
		}
		if(!orderById){
			if(hasOrderBy){
				sqlBuilder.append(" or(");
				sqlBuilder.append(orderBy);
				sqlBuilder.append("=");
				sqlBuilder.append(selectValueSql);
				orderByBuilder.append(",");
			}
			sqlBuilder.append(" and ");
			sqlBuilder.append(idName);
			orderByBuilder.append(idName);
			if(getUp){
				sqlBuilder.append(">?");
				orderByBuilder.append(" asc");
			} else {
				sqlBuilder.append("<?");
				orderByBuilder.append(" desc");
			}
			if(hasOrderBy){
				sqlBuilder.append(")");
			}
		}
		sqlBuilder.append(orderByBuilder.toString());
		List list=new ArrayList();
		list = this.find(sqlBuilder.toString(), new Object[]{basedoc.getChannel().getId(), 5, id});
		
		Object obj=null;
		if(list!=null&&list.size()>0)
			obj=list.get(0);	
		BaseDocument doc=(BaseDocument)obj;
		return doc;
	}
	

	/**
	 * 获得公共文档对象
	 * 
	 * @return
	 * @throws Exception
	 */
	public CmsBaseDocument getCmsBaseDocument(Long id) throws Exception {
		List<CmsBaseDocument> list = this.find(
				"from CmsBaseDocument where id=?", new Object[] { id });
		CmsBaseDocument obj = null;
		if (list != null && list.size() > 0)
			obj = list.get(0);
		return obj;
	}

	/**
	 * 更新文档的状态
	 * 
	 * @param ids
	 * @param state
	 *            <-1过期 0 删除 1正常>
	 */
	public void updateDocumentsState(List<Long> ids, int state)
			throws Exception {
		if (ids != null) {
			for (Long id : ids) {
				BaseDocument obj = getDocument(id);
				obj.setStatus(state);
				if (state == 0) {// 删除操作时，记录删除时间
					obj.setTimeDeleted(new Date());
				}
				save(obj);
			}
		}
	}

	/**
	 * 设置文档是否共享
	 * 
	 * @param ids
	 * @param state
	 * @throws Exception
	 */
	public void updateDocumentsIsShare(List<Long> ids, int isShare)
			throws Exception {
		if (ids != null) {
			for (Long id : ids) {
				// BaseDocument obj=this.getDocument(id);
				CmsBaseDocument basedoc = (CmsBaseDocument) this.get(
						CmsBaseDocument.class, id);
				basedoc.setIsShare(isShare);
				save(basedoc);
			}
		}
	}

	/**
	 * 获取多个频道下的文档信息
	 * 
	 * @param limit
	 * @param channels
	 * @param where
	 * @return Map 以频道id 为key 文档信息集合为 值的 Msp对象
	 * @throws Exception
	 */
	public Map getDocsByChannels(Limit limit, List<Channel> channels,
			String where) throws Exception {
		Map<String, List> chn_doc = new HashMap<String, List>();

		for (Channel channel : channels) {

			CriteriaSetup criteriaSetup = new CriteriaSetup();

			List docs = null;
			// 判断是否还存在子频道
			if (channel.getChildren() != null
					&& channel.getChildren().size() > 0) {
				docs = (List) findChildByPublishPage(limit, criteriaSetup,
						channel, where).getData();
			} else {
				docs = (List) findByPublishPage(limit, criteriaSetup, channel,
						where).getData();
			}

			// 判断文档信息是否为空
			if (docs == null) {
				docs = new ArrayList();
			}
			// 添加到map对象中
			chn_doc.put(channel.getChannelPath(), docs);
		}

		return chn_doc;
	}

	/**
	 * XML数据导入
	 * 
	 * @param inputStream
	 * @param channel
	 * @throws Exception
	 */
	public void importFromXml(Element root, Channel channel) throws Exception {
		Element docsE = root.element("Docs");
		Iterable<Element> docs = docsE.elements("Doc");
		getDocFromXml(docs, channel);
	}

	/**
	 * 从XML文件中提取数据
	 * 
	 * @param docs
	 * @param chn
	 * @throws Exception
	 */
	public void getDocFromXml(Iterable<Element> docs, Channel chn)
			throws Exception {
		CoreFormService formService = (CoreFormService) ServiceLocator
				.getBean("coreFormService");
		Template template = chn.getPublicchannelid() != null
				&& chn.getPublicchannelid() > 0 && chn.getIscopy() == 2 ? channelService
				.getChannelFromCache(chn.getPublicchannelid())
				.getFormTemplate() : chn.getFormTemplate();

		Map<String, String> formFields;
		if (template != null) {
			try {
				//根据指定表单模板名称，获得对应表单
				CoreForm cf = formService.getCoreFormByTemplateName(
						template.getName(), chn.getSite().getOid());
				// Long
				// cformtempid=channelService.getDefualutTemplateId(chn.getId(),
				// Template.TYPE_FORM);
				// Long
				// cformid=templateManagerService.getTemplateFormByTemplateId(cformtempid).getOid();
				//获得指定表单的字段
				formFields = formService.getFieldsByForm(cf.getOid());
			} catch (Exception e) {
				formFields = new HashMap();
			}
			for (Element docE : docs) {
				BaseDocument baseDoc = (BaseDocument) getFormClass(chn.getId())
						.newInstance();
				CmsSite site = CmsSite.class.newInstance();
				site.setOid(chn.getSite().getOid());
				Ognl.setValue("site", baseDoc, site);
				Ognl.setValue("channel", baseDoc, chn);
				Element fieldsE = docE.element("Fields");
				Iterable<Element> fields = fieldsE.elements("Field");
				for (Element fieldE : fields) {
					if (formFields.containsKey(fieldE.attributeValue("name"))) {
						if (fieldE.getTextTrim() != null
								&& fieldE.getTextTrim().length() > 0)
							Ognl.setValue(
									fieldE.attributeValue("name"),
									baseDoc,
									exchangeType(fieldE.attributeValue("type"),
											fieldE.getTextTrim()));
					}
				}
				Ognl.setValue("id", baseDoc, getSequence());
				Element attachmentsE = docE.element("Attachments");
				Iterable<Element> attachments = attachmentsE
						.elements("Attachment");
				for (Element attachmentE : attachments) {
					Attachment attachment = Attachment.class.newInstance();
					Ognl.setValue("fileExt", attachment,
							attachmentE.element("fileExt").getTextTrim());
					Ognl.setValue("fileSize", attachment, Double
							.valueOf(attachmentE.element("fileSize")
									.getTextTrim()));
					Ognl.setValue("filePath", attachment,
							attachmentE.element("filePath").getTextTrim());
					Ognl.setValue("name", attachment,
							attachmentE.element("name").getTextTrim());
					Ognl.setValue("remark", attachment,
							attachmentE.element("remark").getTextTrim());
					Ognl.setValue("updateTime", attachment, dateFormat
							.parse(attachmentE.element("updateTime")
									.getTextTrim()));
					Ognl.setValue("documentId", attachment, baseDoc.getId());
					//文件内容
					String codeStr = attachmentE.element("fileContent")
							.getText();
					if (StringUtils.isNotEmpty(codeStr)) {
						BASE64Decoder decoder = new BASE64Decoder();
						byte[] newBytes = unzip(decoder.decodeBuffer(codeStr));
						String filePath = attachment.getFilePath();
						if (!Constants.IS_REALPATH)
							filePath = ServletActionContext.getServletContext()
									.getRealPath(filePath);
						// File file = new File(filePath);
						// if(file.exists())
						// {
						// String fileName =
						// System.currentTimeMillis()+attachment.getFileExt();
						// filePath =
						// filePath.substring(0,filePath.lastIndexOf("\\"))+"\\"+fileName;
						FileUtil.getFileFromBytes(newBytes, filePath);
						// if(!Constants.IS_REALPATH)
						// filePath = Constants.UPLOADS_PATH+fileName;
						// }
						// Ognl.setValue("filePath", attachment, filePath);
					}
					attachmentManagerService.save(attachment);
				}

				Element fckFilesE = docE.element("FckFiles");
				FCKFileManagerService FCKFileService = (FCKFileManagerService) ServiceLocator
						.getBean("FCKFileManagerService");
				Iterable<Element> fckFiles = fckFilesE.elements("FckFile");
				for (Element fckFileE : fckFiles) {
					FCKFile fckFile = FCKFile.class.newInstance();
					Ognl.setValue("fileExt", fckFile,
							fckFileE.element("fileExt").getTextTrim());
					Ognl.setValue("fileSize", fckFile, Double.valueOf(fckFileE
							.element("fileSize").getTextTrim()));
					Ognl.setValue("fileUrl", fckFile,
							fckFileE.element("fileUrl").getTextTrim());
					Ognl.setValue("userId", fckFile, fckFileE.element("userId")
							.getTextTrim());
					Ognl.setValue("fileName", fckFile,
							fckFileE.element("fileName").getTextTrim());
					Ognl.setValue("createTime", fckFile,
							dateFormat.parse(fckFileE.element("createTime")
									.getTextTrim()));
					Ognl.setValue("docId", fckFile, baseDoc.getId());
					String codeStr = fckFileE.element("fileContent").getText();
					if (StringUtils.isNotEmpty(codeStr)) {
						BASE64Decoder decoder = new BASE64Decoder();
						byte[] newBytes = unzip(decoder.decodeBuffer(codeStr));
						String filePath = fckFile.getFileUrl();
						if (!Constants.IS_REALPATH)
							filePath = ServletActionContext.getServletContext()
									.getRealPath(filePath);
						File file = new File(filePath);
						// if(file.exists())
						// {
						// String fileName =
						// System.currentTimeMillis()+fckFile.getFileExt();
						// filePath =
						// filePath.substring(0,filePath.lastIndexOf("\\"))+"\\"+fileName;
						FileUtil.getFileFromBytes(newBytes, filePath);
						// }
					}
					FCKFileService.saveOrUpdate(fckFile);
				}
				Element officeE = docE.element("offices");
				String codeStr = officeE.getText();
				if (StringUtils.isNotEmpty(codeStr)) {
					BASE64Decoder decoder = new BASE64Decoder();
					byte[] newBytes = unzip(decoder.decodeBuffer(codeStr));
					File file = new File(ServletActionContext
							.getServletContext()
							.getRealPath(Constants.ZIP_FILE));
					FileOutputStream dest = new FileOutputStream(file);
					dest.write(newBytes);
					dest.close();
					ZipFile zipFile = new ZipFile(file);
					Enumeration emu = zipFile.entries();
					while (emu.hasMoreElements()) {
						ZipEntry entry = (ZipEntry) emu.nextElement();
						if (entry.isDirectory()) {
							if (Constants.IS_REALPATH)
								new File(Constants.ABSOLUTE_PATH
										+ Constants.INFO_OFFICE_PATH
										+ baseDoc.getId() + "/"
										+ entry.getName()).mkdirs();
							else
								new File(ServletActionContext
										.getServletContext().getRealPath(
												Constants.INFO_OFFICE_PATH
														+ baseDoc.getId())
										+ "/" + entry.getName()).mkdirs();
							continue;
						}
						BufferedInputStream bis = new BufferedInputStream(
								zipFile.getInputStream(entry));
						File outputFile = new File(ServletActionContext
								.getServletContext().getRealPath(
										Constants.INFO_OFFICE_PATH
												+ baseDoc.getId())
								+ File.separatorChar + entry.getName());
						File parent = outputFile.getParentFile();
						if (parent != null && (!parent.exists())) {
							parent.mkdirs();
						}
						FileOutputStream fos = new FileOutputStream(outputFile);
						BufferedOutputStream bos = new BufferedOutputStream(
								fos, BUFFER);
						int count;
						byte data[] = new byte[BUFFER];
						while ((count = bis.read(data, 0, BUFFER)) != -1) {
							bos.write(data, 0, count);
						}
						bos.flush();
						bos.close();
						bis.close();
					}
					zipFile.close();
				}
				saveOrUpdate(baseDoc);
				
				this.flush();
			}
		}
	}

	/**
	 * 导出数据
	 * 
	 * @param ids
	 * @param outputStream
	 * @param chn
	 * @throws Exception
	 */
	public void exportDatas(List<Long> ids, OutputStream outputStream,
			Channel chn) throws Exception {
		if (ids != null) {
			OutputFormat format = new OutputFormat();
			format.setIndent(true);
			format.setNewlines(true);
			XMLWriter writer = new XMLWriter(outputStream, format);
			Document document = DOMDocumentFactory.getInstance()
					.createDocument();
			Element root = document.addElement("data");
			List<BaseDocument> datas = new ArrayList();
			for (Long id : ids) {
				BaseDocument baseDoc = (BaseDocument) get(id);
				if (baseDoc != null)
					datas.add(baseDoc);
			}
			changeToXml(datas, root, chn);
			writer.write(document);
		}
	}

	/**
	 * 将字段内容和附件转成XML
	 * 
	 * @param baseDocs
	 * @param root
	 * @param chn
	 * @throws Exception
	 */
	public void changeToXml(Collection<BaseDocument> baseDocs, Element root,
			Channel chn) throws Exception {
		Element DocsE = root.addElement("Docs");
		Map<String, String> formFields;
		if (!CollectionUtils.isEmpty(baseDocs)) {
			CoreFormService formService = (CoreFormService) ServiceLocator
					.getBean("coreFormService");

			TemplateManagerService templateManagerService = (TemplateManagerService) ServiceLocator
					.getBean("templateManagerService");

			// if((chn.getFormTemplate()!=null &&
			// chn.getFormTemplate().getId()!=null)){
			try {
				Long cformtempid = channelService.getDefualutTemplateId(
						chn.getId(), Template.TYPE_FORM);
				Long cformid = templateManagerService
						.getTemplateFormByTemplateId(cformtempid).getOid();
				formFields = formService.getFieldsAndTypeByForm(cformid);
			} catch (Exception e) {
				formFields = new HashMap();
			}
			for (BaseDocument baseDoc : baseDocs) {
				Element element = DocsE.addElement("Doc");
				Set entries = formFields.entrySet();
				Iterator iter = entries.iterator();
				Element fields = element.addElement("Fields");
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					Object val = entry.getKey();
					if (val != null && StringUtils.isNotEmpty(val.toString())) {
						Element fieldE = fields.addElement("Field");
						fieldE.addAttribute("name", val.toString());
						fieldE.addAttribute("type", entry.getValue().toString());
						fieldE.setText(Ognl.getValue(val.toString(), baseDoc) == null ? StringUtils.EMPTY
								: Ognl.getValue(val.toString(), baseDoc)
										.toString());
					}

				}
				Element attachments = element.addElement("Attachments");
				AttachmentManagerService attachmentManagerService = (AttachmentManagerService) ServiceLocator
						.getBean("attachmentManagerService");
				for (Attachment attach : attachmentManagerService
						.getAttachsByDocId(baseDoc.getId())) {
					Element attachment = attachments.addElement("Attachment");
					attachment
							.addElement("fileExt")
							.setText(
									Ognl.getValue("fileExt", attach) == null ? StringUtils.EMPTY
											: Ognl.getValue("fileExt", attach)
													.toString());
					attachment.addElement("fileSize").setText(
							String.valueOf(attach.getFileSize()));
					attachment
							.addElement("updateTime")
							.setText(
									Ognl.getValue("updateTime", attach) == null ? StringUtils.EMPTY
											: Ognl.getValue("updateTime",
													attach).toString());
					attachment
							.addElement("uploader")
							.setText(
									Ognl.getValue("uploader", attach) == null ? StringUtils.EMPTY
											: Ognl.getValue("uploader.userid",
													attach).toString());
					attachment
							.addElement("name")
							.setText(
									Ognl.getValue("name", attach) == null ? StringUtils.EMPTY
											: Ognl.getValue("name", attach)
													.toString());
					attachment
							.addElement("remark")
							.setText(
									Ognl.getValue("remark", attach) == null ? StringUtils.EMPTY
											: Ognl.getValue("remark", attach)
													.toString());
					attachment
							.addElement("filePath")
							.setText(
									Ognl.getValue("filePath", attach) == null ? StringUtils.EMPTY
											: Ognl.getValue("filePath", attach)
													.toString());
					attachment
							.addElement("documentId")
							.setText(
									Ognl.getValue("documentId", attach) == null ? StringUtils.EMPTY
											: Ognl.getValue("documentId",
													attach).toString());
					File file;
					if (Constants.IS_REALPATH) {
						file = new File(Constants.ABSOLUTE_PATH
								+ attach.getFilePath());
					} else {
						file = new File(ServletActionContext.getRequest()
								.getRealPath(attach.getFilePath()));
					}
					if (file.isFile()) {
						attachment.addElement("fileContent").setText(zip(file));
					} else
						attachment.addElement("fileContent").setText(
								StringUtils.EMPTY);

				}

				Element fckFiles = element.addElement("FckFiles");
				FCKFileManagerService FCKFileService = (FCKFileManagerService) ServiceLocator
						.getBean("FCKFileManagerService");
				for (FCKFile fckFile : FCKFileService
						.getFckFilesByDocId(baseDoc.getId())) {
					Element fckFileE = fckFiles.addElement("FckFile");
					fckFileE.addElement("fileExt")
							.setText(
									Ognl.getValue("fileExt", fckFile) == null ? StringUtils.EMPTY
											: Ognl.getValue("fileExt", fckFile)
													.toString());
					fckFileE.addElement("fileSize").setText(
							String.valueOf(fckFile.getFileSize()));
					fckFileE.addElement("createTime")
							.setText(
									Ognl.getValue("createTime", fckFile) == null ? StringUtils.EMPTY
											: Ognl.getValue("createTime",
													fckFile).toString());
					fckFileE.addElement("userId")
							.setText(
									Ognl.getValue("userId", fckFile) == null ? StringUtils.EMPTY
											: Ognl.getValue("userId", fckFile)
													.toString());
					fckFileE.addElement("fileName")
							.setText(
									Ognl.getValue("fileName", fckFile) == null ? StringUtils.EMPTY
											: Ognl.getValue("fileName", fckFile)
													.toString());
					fckFileE.addElement("fileUrl")
							.setText(
									Ognl.getValue("fileUrl", fckFile) == null ? StringUtils.EMPTY
											: Ognl.getValue("fileUrl", fckFile)
													.toString());
					fckFileE.addElement("docId")
							.setText(
									Ognl.getValue("docId", fckFile) == null ? StringUtils.EMPTY
											: Ognl.getValue("docId", fckFile)
													.toString());
					File file;
					if (Constants.IS_REALPATH) {
						file = new File(Constants.ABSOLUTE_PATH
								+ fckFile.getFileUrl());
					} else {
						file = new File(ServletActionContext.getRequest()
								.getRealPath(fckFile.getFileUrl()));
					}
					if (file.isFile()) {
						fckFileE.addElement("fileContent").setText(zip(file));
					} else
						fckFileE.addElement("fileContent").setText(
								StringUtils.EMPTY);
				}

				Element officeE = element.addElement("offices");
				File officeFile;
				if (Constants.IS_REALPATH)
					officeFile = new File(Constants.INFO_OFFICE_PATH
							+ baseDoc.getId());
				else
					officeFile = new File(ServletActionContext
							.getServletContext().getRealPath(
									Constants.INFO_OFFICE_PATH
											+ baseDoc.getId()));
				if (officeFile.isDirectory()) {
					BufferedInputStream origin = null;
					File file = new File(ServletActionContext
							.getServletContext()
							.getRealPath(Constants.ZIP_FILE));
					FileOutputStream dest = new FileOutputStream(file);
					ZipOutputStream out = new ZipOutputStream(
							new BufferedOutputStream(dest));
					File[] ps = officeFile.listFiles();
					byte data[] = new byte[BUFFER];
					if (ps != null && ps.length > 0) {
						for (int i = 0; i < ps.length; i++) {
							File f = ps[i];
							if (f.isFile()) {
								FileInputStream fi = new FileInputStream(f);
								origin = new BufferedInputStream(fi, BUFFER);
								ZipEntry entry = new ZipEntry(f.getName());
								out.putNextEntry(entry);
								int count;
								while ((count = origin.read(data, 0, BUFFER)) != -1) {
									out.write(data, 0, count);
								}
								origin.close();
							}
						}

					}
					out.close();
					officeE.setText(zip(file));

				}

			}
			// }
		}
	}

	/**
	 * 移动文档
	 * 
	 * @param ids
	 * @param channelId
	 * @throws Exception
	 */
	public void updateDocumentsChannel(List<Long> ids, Long channelId)
			throws Exception {
		if (ids != null) {
			Channel channel = channelService.getChannelFromCache(channelId);
			for (Long id : ids) {
				BaseDocument obj = this.getDocument(id);
				obj.setChannel(channel);
				save(obj);
				this.flush();
				// 保存公共对象
				CmsBaseDocument cbdoc = this.getCmsBaseDocument(id);
				cbdoc.setChannel(channel);
				save(cbdoc);
				this.flush();
			}
		}
	}

	/**
	 * 取得频道下的所有文档
	 * 
	 * @param channelId
	 * @return
	 */
	public List findByChannel(Long channelId) {
		List list = this.find(" from CmsBaseDocument where channel.id=?",
				new Object[] { channelId });

		return list;
	}

	/**
	 * 通过where条件获取一条信息
	 */
	public Object findBywhere(Long channelid, String where, Object... objs) throws Exception {

		// 获得当前频道的表单对象
		this.setEntityObject(getFormClass(channelid));

		String hql = "from " + this.getEntityClass().getName();

		if (where.indexOf("where") < 0) {
			hql += " where ";
		}

		hql += where;

		List list = this.getHibernateTemplate().find(hql, objs);

		if (list == null || list.size() <= 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	/**
	 * 更新文档状态
	 * 
	 * @param id
	 */
	public void updateDocumentState(Long id) {
		BaseDocument doc = this.get(id);
		/*
		 * CoreFlow flow=null; if(doc.getFlowinfo()!=null &&
		 * doc.getFlowinfo().getFid()!=null)
		 * flow=(CoreFlow)this.get(CoreFlow.class,doc.getFlowinfo().getFid());
		 * if(flow==null||flow.getState()==null) return ; int
		 * state=flow.getState().intValue(); if(state==1)
		 * doc.setIssued(2);//流程已完成，设置成待发布 else if(state==-1)
		 * doc.setIssued(3);//流程已中止，设置成已否状态 else if(doc.getIssued()==0)
		 * doc.setIssued(1); else return ;
		 */
		this.save(doc);
	}

	/**
	 * 更新文档正文内容
	 * 
	 * @param docid
	 * @param content
	 * @return
	 */
	public CmsBaseDocument updateDocumentDody(Long docid, String content) {
		CmsBaseDocument cbd = null;
		Object obj = this.get(CmsBaseDocument.class, docid);
		if (obj == null) {
			cbd = new CmsBaseDocument();
			cbd.setId(docid);
		} else {
			cbd = (CmsBaseDocument) obj;
		}
		cbd.setBody(content);
		this.save(cbd);
		return cbd;
	}

	public Limit documentLimit(Integer pageIndex, Integer pageSize,
			Integer recordCount, String orderBy, boolean ascending) {
		Limit limit = (Limit) new com.cyberway.common.message.utils.Limit(
				pageIndex, pageSize, recordCount, orderBy, ascending);
		return limit;
	}

	public void getEntityObject() {
		logger.info("entityClass:" + entityClass);
	}

	public void setEntityObject(Class clazz) {
		entityClass = clazz;
	}

	public void setChannelService(ChannelManagerService channelService) {
		this.channelService = channelService;
	}

	public void setSiteCache(SiteCache siteCache) {
		this.siteCache = siteCache;
	}

	public void setTemplateManagerService(
			TemplateManagerService templateManagerService) {
		this.templateManagerService = templateManagerService;
	}

	public Object exchangeType(String type, String value) throws Exception {
		if (ObjectUtils.equals(type, "java.lang.Long"))
			return Long.parseLong(value);
		else if (ObjectUtils.equals(type, "java.util.Date"))
			return dateFormat.parse(value);
		else if (ObjectUtils.equals(type, "java.lang.Float"))
			return Float.valueOf(value);
		else if (ObjectUtils.equals(type, "int"))
			return Integer.valueOf(value);
		else
			return value;
	}

	public static byte[] unzip(byte[] buffer) throws IOException {
		GZIPInputStream gin = new GZIPInputStream(new ByteArrayInputStream(
				buffer));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[buffer.length];
		int len;
		while ((len = gin.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		gin.close();
		out.close();
		return out.toByteArray();
	}

	public static String zip(File file) throws Exception {
		FileInputStream inputStream = new FileInputStream(file);
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		GZIPOutputStream gop = new GZIPOutputStream(arrayOutputStream);
		byte[] buffer = new byte[(int) file.length()];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			gop.write(buffer, 0, len);
		}
		gop.finish();
		gop.close();
		BASE64Encoder encoder = new BASE64Encoder();
		String codeStr = encoder.encode(arrayOutputStream.toByteArray());
		return codeStr;
	}

	/**
	 * 根据频道id查找该频道下包含的文档id
	 * 
	 * @param channelId
	 *            频道id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> findDocIdByChannelId(Long channelId) {
		return find("select id from CmsBaseDocument where channel.id=?",
				new Object[] { channelId });
	}
	/**
	 * 根据id查找该对象
	 * 
	 * @param channelId
	 *            频道id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BasicDocument findDocById(Long docId) {
		BasicDocument doc=null;
		Criteria c = getCriteria(BasicDocument.class);
		c.add(Restrictions.eq("id", docId));
		List<BasicDocument> list=(List<BasicDocument>)c.list();
		if(list!=null&&list.size()>0)
			doc=list.get(0);
		return doc;
	
	}
	/**
	 * 根据频道id查找该频道下包含的文档数量
	 * 
	 * @param channelId
	 *            频道id
	 * @return
	 */
	public Integer getDocCountByChannelId(Long channelId) {
		try {
			this.setEntityObject(getFormClass(channelId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CriteriaSetup c = new CriteriaSetup();
		c.addFilter("channel.id", channelId);
		c.addFilter("issued", new Integer(5));
		//Criteria c = getEntityCriteria();
		//c.add(Restrictions.eq("channel.id", channelId));
		return getCount(c);
	}
	
	/**
	 * 根据频道id,分页获取文档
	 * 
	 * @param firstResult
	 *            开始数
	 * @param maxResult
	 *            每一次获取的数量
	 * @param virtualCount
	 *            总数
	 * @param channelId
	 *            频道id
	 * @return
	 */
	public List<BaseDocument> getLimitData(int firstResult, int maxResult, int virtualCount, Long channelId,int tempateType,Boolean isSustainWap) {
		if (firstResult + maxResult > virtualCount) {
			maxResult = virtualCount - firstResult;
		}
		Criteria c = getEntityCriteria();
		c.setProjection(null);
		c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		c.add(Restrictions.eq("channel.id", channelId));
		c.add(Restrictions.eq("issued", 5));
		if(isSustainWap != null && isSustainWap ==true){
			if(tempateType==Template.TYPE_DETAILS){
				c.add( Restrictions.or(Restrictions.eq("docType", 1l),
						Restrictions.eq("docType", 0l)));
			}else if(tempateType==Template.TYPE_DETAILS_WAP){
				c.add( Restrictions.or(Restrictions.eq("docType", 2l),
						Restrictions.eq("docType", 0l)));
			}
		}

		c.setFirstResult(firstResult);
		c.setMaxResults(maxResult);
		return c.list();
	}

	public Page listPage(Limit limit, CriteriaSetup criteriaSetup, Class cs)
			throws Exception {
		this.setEntityObject(cs);
		Criteria criteria = this.getEntityCriteria();
		if (criteriaSetup == null)
			criteriaSetup = new CriteriaSetup();
		criteriaSetup.setup(criteria);
		return pagedQuery(criteria, limit.getPage(),
				limit.getCurrentRowsDisplayed());
	}
	
	/**
	 * WAP首页列表方法
	 * 查询子频道已发布的文档列表
	 * 
	 * @param limit
	 * @param criteriaSetup
	 * @param channel
	 * @param where
	 * @param docType
	 * @return
	 * @throws Exception
	 *  2012-9-11 15:40:30
	 */
	@SuppressWarnings("unchecked")
	public Page findChildByPublishPage(Limit limit,
			CriteriaSetup criteriaSetup, Channel channel, String where,Integer docType)
			throws Exception {
		Channel chl = channelService.getChannelFromCache(channel.getId());
		// 设置子频道
		List childChannels = new ArrayList();
		chl.setChildren(channelService.getChild(chl.getId(), childChannels));

		// 获得 子频道表单对象
		{
			setEntityObject(getFormClass(((Channel) channelService
					.getFirstLeafChild(chl.getId())).getId()));

			List<Criterion> lsCris = criteriaSetup.getAddCriterions();
			if (lsCris == null)
				lsCris = new ArrayList();
			Criterion criterion = null;
			if (chl.getChildren().size() > 0) {
				criterion = Restrictions.in("channel", chl.getChildren());
				lsCris.add(criterion);
				criteriaSetup.setAddCriterions(lsCris);
			} else {
				criteriaSetup.addFilter("id", 0l);
			}
		}

		CmsSite site = chl.getSite();
		// 只查询已发布的文档
		criteriaSetup.addFilter("issued", 5);
		// 只显示正常文档(正常1 删除0 过期-1)
		criteriaSetup.addFilter("status", 1);

		if (!StringUtil.isEmpty(where)) {// 增加用户自定义条件
			Criterion criterion = Restrictions.sqlRestriction(where);
			criteriaSetup.setInCriterion(criterion);
		}
		// 若需登录，只显示用户有权限的信息，无需登录：显示所有发布的信息
		if (site.getIsLogined() != null && site.getIsLogined() == 1) {// 需要登录验证
			Loginer loginer = (Loginer) ActionContext.getContext().getSession().get(com.cyberway.core.Constants.USER_IN_SESSION);
			if (!loginer.checkIsAdministratorUser()) {
				List<Long> selfDocs = getChildDocsByPer(childChannels, loginer);
				if (selfDocs.size() > 0) {
					List<Criterion> lsCris = criteriaSetup.getAddCriterions();
					if (lsCris == null) lsCris = new ArrayList();
					lsCris.add(Restrictions.in("id", selfDocs));
					criteriaSetup.setAddCriterions(lsCris);
				}else{
					criteriaSetup.addFilter("id", 0l);
				}
			}
		} else {// 站点，无需登录验证
			//查询相匹配的文档类型
			List<Criterion> lsCris = criteriaSetup.getAddCriterions();
			if (lsCris == null)
				lsCris = new ArrayList();
			Criterion criterion = Restrictions.sqlRestriction("(docType=0 or docType="+docType+")");
			lsCris.add(criterion);
			criteriaSetup.setAddCriterions(lsCris);
		}
		Page page = super.findECPage(limit, criteriaSetup);

		return page;
	}

	/**
	 * 查询站点所有有权限信息
	 * 
	 * @param limit
	 * @param criteriaSetup
	 * @param where
	 * @param siteid
	 * @param channel
	 * @param docType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page findByPublishPageSiteDocs(Limit limit,
			CriteriaSetup criteriaSetup, String where, Long siteid,
			Channel channel,Integer docType) throws Exception {
		CmsSite site = null;
		if (channel == null || channel.getId() == null) {// 未指定频道，获得任意一频道
			if (siteid != null) {
				List<Channel> channels = channelService
						.getChannelsBySite(siteid);
				if (channels != null && channels.size() > 0) {
					channel = channels.get(0);
					site = channel.getSite();
				}
			} else
				throw new Exception("对不起，未指定站点Id或频道Id！");
		} else {
			Channel chl = channelService.getChannelFromCache(channel.getId());
			site = chl.getSite();
		}
		// 按站点过滤
		criteriaSetup.addFilter("site", site);
		// 获得当前频道的表单对象
		// entityClass=com.cyberway.cms.domain.Document.class;
		this.setEntityObject(getFormClass(channel.getId()));
		// 只查询已发布的文档
		// criteriaSetup.addFilter("issued", 5);
		// 只显示正常文档(正常1 删除0 过期-1)
		criteriaSetup.addFilter("status", 1);

		if (!StringUtil.isEmpty(where)) {// 增加用户自定义条件
			Criterion criterion = Restrictions.sqlRestriction(where);
			criteriaSetup.setInCriterion(criterion);
		}
		// 若需登录，只显示用户有权限的信息，无需登录：显示所有发布的信息
		if (site.getIsLogined() != null && site.getIsLogined() == 1) {// 需要登录验证
			// 增加权限过滤　amway
			Loginer loginer = (Loginer) ActionContext.getContext().getSession()
					.get(com.cyberway.core.Constants.USER_IN_SESSION);
			CmsPermissionService permService = (CmsPermissionService) ServiceLocator
					.getBean("cmsPermissionService");

			// 则查询当前站点下文档

			List<Channel> chls = channelService.getChannelsBySite(channel
					.getSite().getOid());
			List<Criterion> lsCris = criteriaSetup.getAddCriterions();
			if (lsCris == null)
				lsCris = new ArrayList();
			if (chls != null) {
				List permchl = new ArrayList();
				for (Channel chl : chls) {
					if (permService.haveThePermission(loginer,
							"CMS_DOCUMENT_VIEW", Constants.CHANNEL_TYPE,
							chl.getId())) {
						permchl.add(chl);
					}
				}
				Criterion criterion = null;
				if (permchl.size() > 0) {
					criterion = Restrictions.in("channel", permchl);
				} else
					criterion = Restrictions.sqlRestriction("1>2");
				if (criterion != null)
					lsCris.add(criterion);
			}

			criteriaSetup.setAddCriterions(lsCris);

			// 安利文档过滤规则 未发布的文档，只对作者可见，发布后有权限可见
			if (!loginer.checkIsAdministratorUser()) {
				List<Criterion> lsCris2 = criteriaSetup.getAddCriterions();
				if (lsCris2 == null)
					lsCris2 = new ArrayList();
				Criterion criterion = Restrictions
						.sqlRestriction("(this_.issued=5 or (this_.issued<5 and this_.author_id="
								+ loginer.getUserid() + "))");
				lsCris2.add(criterion);
				criteriaSetup.setAddCriterions(lsCris2);
			}
		} else {// 站点，无需登录验证
			// 只查询已发布的文档
			criteriaSetup.addFilter("issued", 5);
			//查询相匹配的文档类型
			List<Criterion> lsCris = criteriaSetup.getAddCriterions();
			if (lsCris == null)
				lsCris = new ArrayList();
			Criterion criterion = Restrictions
					.sqlRestriction("(docType=0 or docType="+docType+")");
			lsCris.add(criterion);
			criteriaSetup.setAddCriterions(lsCris);
		}
		// this.getFormClass(channel.getId());
		Page page = super.findECPage(limit, criteriaSetup);

		return page;
	}
	/**
	 * wap
	 * 查询已发布的文档列表
	 * 
	 * @param limit
	 * @param criteriaSetup
	 * @param channel
	 * @param where
	 * @param docType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page findByPublishPage(Limit limit, CriteriaSetup criteriaSetup,
			Channel channel, String where,Integer docType) throws Exception {
		Channel chl = channelService.getChannelFromCache(channel.getId());
		// 设置子频道
//		List childChannels = new ArrayList();

		// 获得当前频道的表单对象
		setEntityObject(getFormClass(channel.getId()));
		criteriaSetup.addFilter("channel", channel);

		CmsSite site = chl.getSite();
		// 只查询已发布的文档
		criteriaSetup.addFilter("issued", 5);
		// 只显示正常文档(正常1 删除0 过期-1)
		criteriaSetup.addFilter("status", 1);
		if (!StringUtil.isEmpty(where)) {// 增加用户自定义条件
			Criterion criterion = Restrictions.sqlRestriction(where);
			criteriaSetup.setInCriterion(criterion);
		}
		// 若需登录，只显示用户有权限的信息，无需登录：显示所有发布的信息
		if (site.getIsLogined() != null && site.getIsLogined() == 1) {// 需要登录验证
			Loginer loginer = (Loginer) ActionContext.getContext().getSession().get(com.cyberway.core.Constants.USER_IN_SESSION);
			if (!loginer.checkIsAdministratorUser()) {
				CmsPermissionService permService = (CmsPermissionService) ServiceLocator.getBean("cmsPermissionService");
				// 验证是否有权访问此频道
				boolean channelPermission = permService.haveThePermission(loginer,"CMS_DOCUMENT_VIEW",Constants.CHANNEL_TYPE,channel.getId());
				// 增加过滤单独指定文档的权限
				List<Long> selfsDoc = this.getDocumentsByPer(channel, loginer, channelPermission);
				if (selfsDoc != null && selfsDoc.size() > 0) {
					List<Criterion> lsCris = criteriaSetup.getAddCriterions();
					if (lsCris == null) lsCris = new ArrayList();
					if(channelPermission){//有权访问频道时则排除无权访问的文档。
						lsCris.add(Restrictions.not(Restrictions.in("id", selfsDoc)));
					}else{//无权访问频道时则采纳有权访问的文档。
						lsCris.add(Restrictions.in("id", selfsDoc));
					}
					criteriaSetup.setAddCriterions(lsCris);
				}else if(!channelPermission){//无权访问且无文档可访问时返回空集
					criteriaSetup.setFilters(new HashMap<String, Object>());
					criteriaSetup.addFilter("id", -1L);
				}
			}
		} else {// 站点，无需登录验证
			//查询相匹配的文档类型
			List<Criterion> lsCris = criteriaSetup.getAddCriterions();
			if (lsCris == null) lsCris = new ArrayList();
			Criterion criterion = Restrictions
					.sqlRestriction("(docType=0 or docType="+docType+")");
			lsCris.add(criterion);
			criteriaSetup.setAddCriterions(lsCris);
		}
		Page page = super.findECPage(limit, criteriaSetup);

		return page;
	}
	
	/**
	 * 7天之前的文档更新 为非新
	 * @author Dicky
	 * @time 2012-9-19 17:51:12
	 * @version 1.0
	 * @param siteId
	 * @param excludeIds
	 */
	@SuppressWarnings("unchecked")
	public void updateMark(Long siteId, String excludeIds){
		final List<Long> list1 = new ArrayList<Long>();
		CmsSite site0 = null;
		try{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			List<BasicDocument> list0 = this.find("from BasicDocument where site.oid=? and fieldLong1=? and timeLastUpdated<? and channel.id not in ("+excludeIds+")",new Object[] { siteId,1L,cal.getTime() });
			if(list0.size()==0) return;
			for (BasicDocument basicDocument : list0) {
				basicDocument.setFieldLong1(0L);
				saveOrUpdate(basicDocument);
				if(basicDocument.getChannel()!=null && !list1.contains(basicDocument.getChannel().getId())){
					list1.add(basicDocument.getChannel().getId());
				}
				if(site0 == null && basicDocument.getSite()!=null){
					site0 = basicDocument.getSite();
				}
			}
			for (Long channelId : list1) {
				getHtmlSynchroismService().deleteSummaryHtmlByChannelId(channelId);
			}
		}catch (Exception e) {
			logger.error("更新new失败!",e);
		}
	}

	public HtmlSynchroismService getHtmlSynchroismService() {
		return (HtmlSynchroismService)ServiceLocator.getBean("htmlSynchroismService");
	}
	
	/**
	 * 复制对象,用于得到脱离Hibernate的对象复制
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private Object copyObject(Object object) throws Exception {
		Class<?> classType = object.getClass();
		Object objectCopy = classType.getConstructor(new Class[] {})
				.newInstance(new Object[] {});
		Field fields[] = classType.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			if(!"serialVersionUID".equals(fieldName)){
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
	
	public static String base64ImageStr(String imgFilePath) {
		byte[] data = null;
		InputStream in = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		// 返回Base64编码过的字节数组字符串
		return encoder.encode(data);
	}
}
