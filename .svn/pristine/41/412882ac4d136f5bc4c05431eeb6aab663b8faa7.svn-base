package com.cyberway.cms.template.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocumentFactory;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.context.ServletContextAware;

import com.cyberway.cms.Constants;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.Template;
import com.cyberway.cms.form.domain.CoreForm;
import com.cyberway.cms.form.service.CoreFormService;
import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.cms.webservice.service.HtmlSynchroismService;
import com.cyberway.common.distribution.service.DistributionService;
import com.cyberway.common.media.album.domain.MediaIntermediate;
import com.cyberway.common.media.album.service.MediaIntermediateService;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.exception.BizException;
import com.cyberway.core.utils.FileUtil;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.staticer.gather.PagePublisher;

public class TemplateManagerService extends HibernateEntityDao<Template> implements ServletContextAware {
	private Log	               _log	               = LogFactory.getLog(TemplateManagerService.class);
	private SiteCache	       siteCache;	                                                          // 站点所有缓存
	private ServletContext	   _servletContext;
	private CoreFormService	   coreFormservice;
	
	public CoreFormService getCoreFormservice() {
		return coreFormservice;
	}

	public void setCoreFormservice(CoreFormService coreFormservice) {
		this.coreFormservice = coreFormservice;
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	/**
	 * 初始化站点
	 */
	public synchronized void init() {
		List<Template> temps = getAll(); // find(" from Template where type=? order by id",
										 // new Object[]{Template.TYPE_FORM});
		List siteids = new ArrayList();
		for (Template temp : temps) {
			// 若为表单模板
			if (temp.getForm() != null && temp.getType().intValue() == Template.TYPE_FORM) {
				CoreForm cf = (CoreForm) get(CoreForm.class, temp.getForm().getOid());
				siteCache.putTemplateFormInCache(temp.getId().toString(), cf);
			}
			// 初始化模板缓存
			if (temp.getType() == Template.TYPE_ANY || temp.getType() == Template.TYPE_INDEX||temp.getType() == Template.TYPE_ANY_WAP || temp.getType() == Template.TYPE_INDEX_WAP) {
				siteCache.putTemplate(temp.getSite_id(), temp.getType(), temp.getIsPublishStatic(), temp.getId(), temp);
			} else {
				siteCache.putTemplate(temp.getChannel_id(), temp.getType(), temp.getIsPublishStatic(), temp.getId(), temp);
			}

			// 模板对象从缓冲中获得
			// putTemplateObjectInCache(temp);
			// 复制站点下模板
			if (!siteids.contains(temp.getSite_id())) {
				siteids.add(temp.getSite_id());
				// 若使用绝对路径，则同步更新模板
				if (temp.getSite_id() != null && Constants.IS_REALPATH) {

					String tempPath = FilenameUtils.concat(Constants.getProperty("template.path", "/templates/"), temp.getSite_id()
					        .toString());
					// 更新文件
					try {
						FileUtil.CopyDir(Constants.ABSOLUTE_PATH + tempPath, _servletContext.getRealPath(tempPath));
					} catch (Exception e) {
					}
					System.out.println("templatePath:" + tempPath);
				}
			}
		}
	}

	/**
	 * 获得把指定模板的表单对象
	 * 
	 * @param templateid
	 * @return
	 */
	public CoreForm getTemplateFormByTemplateId(Long templateid) {
		if (templateid != null)
			return siteCache.getTemplateFormFromCache(templateid.toString());
		else
			return null;
	}

	/**
	 * 获得指定表单模板对应的表单类
	 * 
	 * @param templateid
	 * @return
	 */
	public Class<?> getFormClassByTemplate(Long templateid) {
		CoreForm cf = getTemplateFormByTemplateId(templateid);
		Class<?> cl = com.cyberway.cms.domain.Document.class;// 默认为动态表单
		if (cf != null) {
			try {
				cl = Class.forName(cf.getPojoName());
			} catch (Exception e) {
				this.logger.error(cf.getFormCode() + "类找不到！");
				logger.error("",e);
			}
		}
		return cl;
	}

	/**
	 * put template url to cache
	 * 
	 * @param key
	 *            --templateId
	 * @param url
	 *            --template Url
	 */
	public void putTemplateUrlInCache(Long templateid, String url) {
		if (templateid != null && !StringUtil.isEmpty(url)) {
			this.siteCache.putTemplateUrlInCache(templateid.toString(), url);
		}
	}

	/**
	 * 获得指定模板对应的URL
	 * 
	 * @param templateid
	 * @return
	 */
	public String getTemplateUrlByTemplateId(Long templateid) {
		if (templateid != null) {
			return siteCache.getTemplateUrlFromCache(templateid.toString());
		} else
			return null;
	}

	/**
	 * put template object to cache
	 * 
	 * @param template
	 *            模板对象
	 */
	public void putTemplateObjectInCache(Template template) {
		if (template != null) {
			this.siteCache.putTemplateObjectInCache(getTemplateObjectKey(template.getId()), template);
		}
	}

	/**
	 * 移除指定template object
	 * 
	 * @param resKey
	 */
	public void removeTemplateObjectFromCache(Long templateid) {
		if (templateid != null) {
			siteCache.removeTemplateObjectFromCache(getTemplateObjectKey(templateid));
		}
	}

	/**
	 * 获得模板对象
	 * 
	 * @param templateid
	 * @return
	 */
	public Template getTemplateObjectByTemplateId(Long templateid) {
		Template template = null;
		if (templateid != null) {
			template = siteCache.getTemplate(templateid);
			// 若缓冲中不存在，从数据库中获得
			if (template == null) {
				try {
					this.flush();
					template = get(templateid);
					if (template != null)
						putTemplateObjectInCache(template);

				} catch (Exception e) {
					logger.error("",e);
				}
			}
		}
		return template;
	}

	// 获得模板对象在缓存中的对角
	public static String getTemplateObjectKey(Long id) {
		return id.toString() + "_OBJECT";
	}

	/**
	 * 根据模板名称，获得指定模板
	 * 
	 * @param site
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Template getTemplateByName(Long siteid, String templateName) {
		Validate.notNull(siteid);
		Validate.notNull(templateName);
		DetachedCriteria criteria = DetachedCriteria.forClass(Template.class);
		criteria.add(Restrictions.eq("site_id", siteid));
		criteria.add(Restrictions.eq("name", templateName));
		List temps = (List<Template>) getHibernateTemplate().findByCriteria(criteria);
		if (temps != null && temps.size() > 0)
			return (Template) temps.get(0);
		else
			return null;
	}

	/**
	 * 根据模板名称，获得最新的模板对象（模板名称可能会重复）
	 * 
	 * @param templateName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Template getNewTemplateByName(String templateName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Template.class);
		criteria.add(Restrictions.eq("name", templateName));
		criteria.addOrder(Order.desc("id"));
		List<Template> temps = null;
		try {
			this.flush();
			temps = find("from Template where name=? ", new Object[] { templateName });// (List<Template>)getHibernateTemplate().findByCriteria(criteria);
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		if (temps != null && temps.size() > 0)
			return temps.get(0);
		else
			return null;
	}

	public Template getById(Long id) {
		return super.get(id);
	}

	@SuppressWarnings("unchecked")
	public Collection<Template> findBy(Integer type, Boolean saveToDesigner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Template.class);
		if (type != null) {
			criteria.add(Restrictions.eq("type", type));
		}
		if (saveToDesigner != null) {
			criteria.add(Restrictions.eq("saveToDesigner", saveToDesigner));
		}
		return (List<Template>) getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * 返回频道下的所有模板
	 * 
	 * @param channelId
	 * @return
	 */
	public Collection<Template> findByChannel(Long channelId) {
		return findBy("channel_id", channelId);
	}

	@SuppressWarnings("unchecked")
	public List<Template> findByChannel(Long channelId, Integer type) {
		return find("from Template where channel_id=? and type=?", new Object[] { channelId, type });
	}

	/**
	 * 删除频道下的模板
	 * 
	 * @param channelId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean removeByChannel(Long channelId) {
		Collection<Template> coll = this.findByChannel(channelId);
		Iterator it = coll.iterator();
		while (it.hasNext()) {
			Template template = (Template) it.next();
			if (template != null) {
				this.remove(template);
			}
		}
		return true;
	}

	/**
	 * 返回站点下所有模板
	 * 
	 * @param siteId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Collection<Template> findBySite(Long siteId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Template.class);
		criteria.add(Restrictions.eq("site_id", siteId));
		return (List<Template>) getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * 返回站点下模板,不属于频道的
	 * 
	 * @param siteId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Collection<Template> findBySiteNotChannel(Long siteId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Template.class);
		criteria.add(Restrictions.eq("site_id", siteId));
		criteria.add(Restrictions.isNull("channel_id"));
		return (List<Template>) getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * 删除站点下所有模板
	 * 
	 * @param siteId
	 * @return
	 */
	public boolean removeBySite(Long siteId) {
		Collection<Template> coll = this.findBySite(siteId);
		Iterator<Template> it = coll.iterator();
		while (it.hasNext()) {
			Template template = (Template) it.next();
			if (template != null) {
				this.remove(template);
			}
		}
		return true;
	}

	/**
	 * 通过站点ID和type获取模板
	 * 
	 * @param siteId
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Collection<Template> findBySite(Long siteId, int type) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Template.class);

		criteria.add(Restrictions.eq("site_id", siteId));
		criteria.add(Restrictions.eq("type", type));

		return (List<Template>) getHibernateTemplate().findByCriteria(criteria);
	}

	public Collection<Template> findCommons() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Template.class);

		criteria.add(Restrictions.isNull("channel_id"));
		criteria.add(Restrictions.isNull("site_id"));

		return (List<Template>) getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * 将模板转换为XML
	 * 
	 * @param templates
	 * @param root
	 */
	public void changeToXml(Collection<Template> templates, Element root) {
		if (!CollectionUtils.isEmpty(templates)) {

			for (Template template : templates) {
				// template=get(template.getId());
				Element element = root.addElement("Template");
				element.addElement("id").setText(ObjectUtils.toString(template.getId()));
				element.addElement("name").setText(ObjectUtils.toString(template.getName()));
				element.addElement("description").addCDATA(ObjectUtils.toString(template.getDescription()));
				element.addElement("body").addCDATA(ObjectUtils.toString(template.getBody()));
				// element.addElement("saveToDesigner").setText(ObjectUtils.toString(template.isSaveToDesigner()));
				if (template.getTimeCreated() == null) {
					element.addElement("timeCreated").setText(StringUtils.EMPTY);
				} else {
					element.addElement("timeCreated").setText(DateFormatUtils.format(template.getTimeCreated(), com.cyberway.common.base.objects.Constants.EXPORT_DATE_FORMAT));
				}
				if (template.getLastModified() == null) {
					element.addElement("lastModified").setText(StringUtils.EMPTY);
				} else {
					element.addElement("lastModified").setText(DateFormatUtils.format(template.getTimeCreated(), com.cyberway.common.base.objects.Constants.EXPORT_DATE_FORMAT));
				}
				element.addElement("type").setText(ObjectUtils.toString(template.getType()));
				element.addElement("channel_id").setText(ObjectUtils.toString(template.getChannel_id()));
				element.addElement("site_id").setText(ObjectUtils.toString(template.getSite_id()));
				if (template.getType() == template.TYPE_FORM) {
					element.addElement("form").setText(
					        ObjectUtils.toString(getTemplateFormByTemplateId(template.getId()) == null ? null
					                : getTemplateFormByTemplateId(template.getId()).getOid()));
				} else {
					element.addElement("form").setText(ObjectUtils.toString(null));
				}
				// element.addElement("default").setText(ObjectUtils.toString(template.isDefault()));
				element.addElement("beforsavescript").setText(ObjectUtils.toString(template.getBeforsavescript()));
				element.addElement("aftersavescript").setText(ObjectUtils.toString(template.getAftersavescript()));
			}
		}
	}

	/**
	 * 导出XML
	 * 
	 * @param data
	 * @param outputStream
	 * @throws IOException
	 */
	public void exportToXml(Collection<Template> data, OutputStream outputStream) throws IOException {
		Validate.notNull(outputStream);

		if (!CollectionUtils.isEmpty(data)) {
			OutputFormat format = new OutputFormat();
			format.setIndent(true);
			format.setNewlines(true);
			XMLWriter writer = new XMLWriter(outputStream, format);

			Document document = DOMDocumentFactory.getInstance().createDocument();
			Element root = document.addElement("Templates");
			this.changeToXml(data, root);
			writer.write(document);
		}
	}

	/**
	 * @author lan 从XML中获取template
	 * @param templates
	 * @param overwrite
	 *            ：是否替换原来的数据
	 * @param channel
	 *            更新模板的Channel
	 * @param site
	 *            更新模板的Site
	 */
	public HashMap changeFromXml(Iterable<Element> templates, boolean overwrite, Channel channel, CmsSite site, HashMap map) {
		for (Element templateE : templates) {
			try {
				String id = templateE.element("id").getTextTrim();
				String name = templateE.element("name").getText();

				String description = templateE.element("description").getText();
				String body = templateE.element("body").getText();
				String saveToDesigner = "false";
				if(templateE.element("saveToDesigner") != null){
					saveToDesigner = templateE.element("saveToDesigner").getTextTrim();
				}
				String timeCreated = templateE.element("timeCreated").getTextTrim();
				String lastModified = templateE.element("lastModified").getTextTrim();
				String type = templateE.element("type").getTextTrim();
				String form = templateE.element("form").getTextTrim();
				String channelId;
				if (channel != null && channel.getId() != null && channel.getId() != 0) {
					channelId = String.valueOf(channel.getId());
				} else {
					channelId = templateE.element("channel_id").getTextTrim();
				}
				String siteId;
				if (site != null && site.getOid() != null && site.getOid() != 0) {
					siteId = String.valueOf(site.getOid());
				} else {
					siteId = templateE.element("site_id").getTextTrim();
				}

				Template template = null;
				if (!overwrite) {
					template = new Template();
				} else {
					Template exists = StringUtils.isNumeric(id) ? get(Long.valueOf(id)) : null;
					template = exists == null ? new Template() : exists;
				}

				template.setName(name);
				template.setDescription(description);
				template.setBody(body);
				template.setType(StringUtils.isEmpty(type) ? null : Integer.valueOf(type));
				if (template.getType() == template.TYPE_FORM) {
					coreFormservice = (CoreFormService) ServiceLocator.getBean("coreFormService");
					template.setForm(StringUtils.isEmpty(form) ? null : coreFormservice.get(Long.parseLong(form)));
				}
				template.setSaveToDesigner((Boolean) new BooleanConverter().convert(Boolean.class, saveToDesigner));
				template.setTimeCreated(StringUtils.isEmpty(timeCreated) ? null : DateUtils.parseDate(timeCreated,
				        new String[] { com.cyberway.common.base.objects.Constants.EXPORT_DATE_FORMAT }));
				template.setLastModified(StringUtils.isEmpty(lastModified) ? null : DateUtils.parseDate(lastModified,
				        new String[] { com.cyberway.common.base.objects.Constants.EXPORT_DATE_FORMAT }));
				template.setChannel_id(StringUtils.isEmpty(channelId) ? null : Long.valueOf(channelId));
				template.setSite_id(StringUtils.isEmpty(siteId) ? null : Long.valueOf(siteId));
				Boolean unique = this.isNotUnique(template, "site_id,name");
				if (unique) {
					template.setName(template.getName() + "_import" + new Date().getTime());
				}
				template = saveOrUpdate(template);
				if (map != null) {
					map.put(id, template.getId());
				}
				// flush();
			} catch (Exception ex) {
				ex.printStackTrace();
				_log.error("cann't import template from xml", ex);
			}
		}
		return map;
	}

	/**
	 * 模板导入
	 * 
	 * @param inputStream
	 * @param overwrite
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public void importFromXml(InputStream inputStream, boolean overwrite, Channel channel, CmsSite site) throws DocumentException {
		Validate.notNull(inputStream);
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Iterable<Element> templates = document.getRootElement().elements("Template");
		this.changeFromXml(templates, overwrite, channel, site, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.core.dao.HibernateEntityDao#saveOrUpdate(java.lang.Object)
	 */
	public Template saveOrUpdate(Template obj) throws BizException{
		// 检测是否在同一站点下模板名称是否重名
		Boolean unique = this.isNotUnique(obj, "site_id,name");
		if (unique)
			throw new BizException("模板名称不能重复！");
		obj = super.saveOrUpdate(obj);
		// 更新缓冲中模板
		// putTemplateObjectInCache(obj);
		if (obj.getType().intValue() == Template.TYPE_FORM && obj.getForm() != null) {// 若为表单模板，更新表单内容
			siteCache.putTemplateFormInCache(obj.getId().toString(), (CoreForm) get(CoreForm.class, obj.getForm().getOid()));
		}
		return obj;
	}

	/**
	 * 获得指定站点下，对应模板名的跳转地址
	 * 
	 * @param serverName
	 * @param templateName
	 * @return
	 */
	public String getTemplateUrl(String serverName, String templateName) throws Exception {
		CmsSite site = siteCache.getSiteFromCache(serverName, 0);
		String templateUrl = "";
		// 若不在缓存中，查询获得，同时放入缓存
		if (StringUtil.isEmpty(templateUrl)) {
			Template template = getTemplateByName(site.getOid(), templateName);
			if (template != null && template.getChannel_id() != null) {
				templateUrl = "/cms/docInfo!list.action?channelId=" + template.getChannel_id() + "&templateName=" + templateName;
			} else {
				templateUrl = "/cms/index.action?siteId=" + site.getOid() + "&templateName=" + templateName;
			}
		}
		return templateUrl;
	}



	public void setSiteCache(SiteCache siteCache) {
		this.siteCache = siteCache;
	}

	/**
	 * 将概览模板发布为静态文件
	 * 
	 * @param baseURL
	 * @param variables
	 *            docId
	 * @param role
	 *            角色
	 * @throws Exception
	 */
	public void pubStaDetails(String baseURL, Map<String, String[]> variables, String role) throws Exception {
		PagePublisher pagePublisher = (PagePublisher) ServiceLocator.getBean("cms.staticer.pagePublisher");
		pagePublisher.publish(baseURL, variables, role);
	}

	/**
	 * 文档管理员保存“已发布文档”或发布文档执行此方法
	 * 根据docids
	 * 
	 * @param url
	 * @param docids
	 *            文档ids(用","分隔)
	 * @param channelId
	 *            频道id
	 * @param docType
	 *            频道1 WEB 、2 WAP    
	 *            
	 *  @param isSustainWap  
	 *          
	 */
	public void pubStaDoc(String url, String docids, Long channelId,Long docType,Boolean isSustainWap) {
		// 格式化文档id:docids
		String[] keys = null;
		if (docids.indexOf(",") > 0) {
			keys = docids.split(",");
		} else {
			keys = new String[] { docids };
		}

		Channel channel = siteCache.getChannelFromCach(channelId);
		CmsSite site = channel.getSite();
		/**
		 * 文件分发
		 * 2012-9-20 11:16:51
		 */
		DistributionService distributionService = (DistributionService) ServiceLocator.getBean("distributionService");
		for (String docid : keys) {
			try {
				distributionService.getOfficeWordById(site.getOid(), Long.valueOf(docid));
				distributionService.getOther(site.getOid(), Long.valueOf(docid));
				distributionService.getAttachment(site.getOid(), Long.valueOf(docid));
				List<MediaIntermediate> mediaIntermediates=siteCache.getMediaIntermediateByStartWith(docid);
				if(mediaIntermediates!=null&&mediaIntermediates.size()>0){
					distributionService.addFlv(mediaIntermediates,site.getOid());
				}
			} catch (Exception e) {
				logger.error("--单个文档分发word/other/att/media失败--", e);
				continue;
			}
		}
	}

	/**
	 * 撤销发布时,删除已经发布为静态页面的文档,跟文档所依赖的资源
	 * @param host
	 * @param channel
	 * @param docIds
	 *            包含文档id的数组
	 */
	public void removePubDoc(String host, Channel channel, List<Long> docIds,Boolean isDel) {
		DistributionService distributionService = (DistributionService) ServiceLocator.getBean("distributionService");
		for (Long id : docIds) {
			// 删除ftp offie文档和附件
			try {
				distributionService.deleteOfficeWordById(channel.getSite().getOid(), id);
				distributionService.deleteOther(channel.getSite().getOid(), id);
				distributionService.deleteAnnexById(channel.getSite().getOid(), id);
				//判断此文档是否有流媒体
				List<MediaIntermediate> mediaIntermediates=siteCache.getMediaIntermediateByStartWith(id.toString());
				MediaIntermediateService mediaIntermediateService = (MediaIntermediateService) ServiceLocator.getBean("mediaIntermediateService");
				for(MediaIntermediate mediaIntermediate:mediaIntermediates){
					//取流媒体后缀为type_mediaId
					List<MediaIntermediate> list=siteCache.getMediaIntermediateByEndWith(mediaIntermediate.getType()+"_"+mediaIntermediate.getMediaId());				
					for(MediaIntermediate _mediaIntermediate:list){
						if(list.size()==1){
							//没有被其他地方引用,可以删除FTP上的文件
							distributionService.removeFlv(_mediaIntermediate);
						}
						if(isDel==true&&StringUtil.ifEqual(String.valueOf(_mediaIntermediate.getDocId()),String.valueOf(id))){
							mediaIntermediateService.remove(_mediaIntermediate);
							siteCache.removeMedia(_mediaIntermediate);
						}
					}	
				}
			} catch (Exception e) {
				logger.error("",e);
			}
		}
	}
	
	/**
	 * 首页静态采集
	 * @param siteId
	 * @return
	 */
	public boolean indexStatic(String siteId) {
		if (!StringUtil.isEmpty(siteId)) {
			HtmlSynchroismService htmlSynchroismService = (HtmlSynchroismService) ServiceLocator.getBean("htmlSynchroismService");
			htmlSynchroismService.deleteIndexHtmlBySiteId(Long.valueOf(siteId));
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 根据频道id,模板类型,是否静态采集,获取包含模板的列表
	 * 
	 * @param channelId
	 * @param templateType
	 * @param isPublishStatic
	 * @return
	 */
	public List<Template> getTemplateList(Long channelId, int templateType, Boolean isPublishStatic) {
		return siteCache.getTemplateList(channelId, templateType, isPublishStatic);
	}

	/**
	 * put template object to cache
	 * 
	 * @param channelId
	 * @param templateType
	 * @param isPublishStatic
	 * @param templateId
	 * @param template
	 */
	public void putTemplate(Long channelId, int templateType, Boolean isPublishStatic, Long templateId, Template template) {
		siteCache.putTemplate(channelId, templateType, isPublishStatic, templateId, template);
	}

	/**
	 * 删除模板缓存
	 * 
	 * @param channelId
	 * @param templateType
	 * @param isPublishStatic
	 * @param templateId
	 */
	public void removeTemplate(Long channelId, int templateType, Boolean isPublishStatic, Long templateId) {
		siteCache.removeTemplate(channelId, templateType, isPublishStatic, templateId);
	}

	/**
	 * 清除所有模板表单缓存
	 */
	public void removeAllTemplateFormCache() {
		siteCache.removeAllTemplateForm();
	}

	/**
	 * 清除所有模板缓存
	 */
	public void removeAllTemplateUrlCache() {
		siteCache.removeAllTemplateUrl();
	}

	/**
	 * 获取所有模板表单缓存的keys
	 * 
	 * @return
	 */
	public List<String> getTemplateFormCacheKeys() {
		return siteCache.getTemplateFormCacheKeys();
	}
	
	/**
	 * 获取所有模板缓存的keys
	 * 
	 * @return
	 */
	public List<String> getTemplateCacheKeys() {
		return siteCache.getTemplateUrlCacheKeys();
	}
	
	/**
	 * 获得指定的模板表单对象
	 * 
	 * @param templateid
	 * @return
	 */
	public net.sf.ehcache.Element getElementFromTemplateForm(String templateid) {
		if (StringUtils.isNotEmpty(templateid)){
			net.sf.ehcache.Element element = null;
			element = siteCache.getElementFromTemplateForm(templateid);
			return element;
		}
		else
			return null;
	}
	
	/**
	 * 获得指定的模板对象
	 * 
	 * @param templateid
	 * @return
	 */
	public net.sf.ehcache.Element getElementFromTemplate(String templateid) {
		if (StringUtils.isNotEmpty(templateid)){
			net.sf.ehcache.Element element = null;
			element = siteCache.getElementFromTemplate(templateid);
			return element;
		}
		else
			return null;
	}
	
}