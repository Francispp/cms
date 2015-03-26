package com.cyberway.cms.permission.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;

import com.cyberway.cms.Constants;
import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsBaseDocument;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.cms.permission.domain.CmsPermResource;
import com.cyberway.cms.permission.domain.CmsPermission;
import com.cyberway.cms.permission.domain.CmsResource;
import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.cms.webservice.service.CacheSynchroismService;
import com.cyberway.cms.webservice.service.HtmlSynchroismService;
import com.cyberway.common.domain.CoreRole;
import com.cyberway.common.role.service.RoleManagerService;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class CmsPermissionService extends HibernateEntityDao<CmsPermission> {
	private SiteCache siteCache;
	private ChannelManagerService channelManagerService;
	private DocumentCommonService documentCommonService;

	/**
	 * 初始化站点权限
	 */
	public synchronized void init() throws Exception {
		List<CmsPermission> perms = getAll();
		for (CmsPermission perm : perms)
			siteCache.putCmsPermissionInCache(getPermissionCacheKey(perm), perm);
	}

	/*
	 * 采用ajax保存权限 (non-Javadoc)
	 * 
	 * @see com.cyberway.core.dao.HibernateEntityDao#saveByAjax(java.util.Map)
	 */
	public boolean saveByAjax(Map<String, Object> data) {
		String oid = (String) data.get("recordKey");
		if (StringUtil.isEmpty(oid))
			return false;
		CmsPermission cmsperm = this.get(new Long(oid));
		if (data.containsKey("managerName")) {// 包含设置的角色权限
			String managerRole = (String) data.get("managerName");
			if (StringUtil.isNumber(managerRole))
				cmsperm.setManagerRole(new Long(managerRole));
			else
				cmsperm.setManagerRole(null);
			this.save(cmsperm);
		}
		if (cmsperm != null) {
			delStaticHtml(cmsperm);
			Assert.notNull(data);

			Set<String> keySet = data.keySet();
			Assert.notNull(keySet);

			Iterator<String> keys = keySet.iterator();
			String property = null;
			CmsPermResource pr = null;
			while (keys.hasNext()) {// 检测更改的权限
				property = keys.next();
				Object obj = data.get(property);
				if (obj != null && "true".equalsIgnoreCase(obj.toString())) {
					// 增加记录
					pr = cmsperm.getCmsPermResourceByCode(property);
					if (pr == null) {
						CmsResource cmsres = siteCache
								.getResourceFromCache(property);// 从缓存中获得已有资源
						if (cmsres != null) {
							pr = new CmsPermResource();
							pr.setPermission(cmsperm);
							pr.setResource(cmsres);
							pr.setResourceCode(cmsres.getResourceCode());
							this.save(pr);
							cmsperm.updateCmsPermResourceByCode(cmsres
									.getResourceCode(), pr);
						}
					}
				} else if (obj != null
						&& "false".equalsIgnoreCase(obj.toString())) {

					// 删除记录
					pr = cmsperm.getCmsPermResourceByCode(property);
					if (pr != null)
						this.remove(pr);
					cmsperm.removeCmsPermResourceByCode(property);
				}
			}

		} else
			return false;
		siteCache.putCmsPermissionInCache(getPermissionCacheKey(cmsperm),
				cmsperm);
		return true;
	}

	/**
	 * 增加　角色对象到指定权限当中
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean insertPermission(Integer roleType, String roleId,
			String roleName, Integer objectType, String objectId,
			Integer setType) {

		CmsPermission cmsperm = new CmsPermission();
		cmsperm.setRoleType(roleType);
		// 角色或用户id，若安利外部用户，可id不为数值，则进行转换
		Long troleid = null;
		if (StringUtil.isNumber(roleId))
			troleid = new Long(roleId);
		else
			troleid = new Long("1008" + Math.abs(roleId.hashCode()));

		cmsperm.setRoleId(troleid);
		cmsperm.setRoleName(roleName);
		cmsperm.setObjectId(new Long(objectId));
		cmsperm.setObjectType(objectType);
		cmsperm.setSetType(setType);
		String perKey = getPermissionCacheKey(cmsperm);
		// 检测是否存在
		List<CmsPermission> list = this
				.find("from CmsPermission where roleType=? and roleId=? and objectId=? and objectType=? and setType=?",
						new Object[] { cmsperm.getRoleType(),
								cmsperm.getRoleId(), cmsperm.getObjectId(),
								cmsperm.getObjectType(), cmsperm.getSetType() });
		if (list != null && !list.isEmpty()) {
			if (siteCache.getCmsPermissionFromCache(perKey) == null) {
				siteCache.putCmsPermissionInCache(perKey, list.get(0));
			}
			return false;
		}
		this.saveOrUpdate(cmsperm);
		siteCache.putCmsPermissionInCache(perKey, cmsperm);
		delStaticHtml(cmsperm);
		return true;
	}

	/**
	 * 删除指定的权限id
	 * 
	 * @param ids
	 * @return
	 */
	public boolean deletePermission(String ids) {
		List<Long> lid = StringUtil.splitToList(ids, ",");
		CacheSynchroismService cacheSynchroismService = (CacheSynchroismService) ServiceLocator.getBean("cacheSynchroismService");
		for (Long id : lid) {
			CmsPermission cmsperm = get(id);
			String key = getPermissionCacheKey(cmsperm);
			siteCache.removeCmsPermissionFromCache(key);
			/* 缓存同步 */
			try {
				cacheSynchroismService.updateCmsPermission(key, "del");
			} catch (Exception ex) {
				logger.error("-权限删除,同步失败-", ex);
				continue;
			}
			remove(cmsperm);
			delStaticHtml(cmsperm);
		}
		return true;
	}
	
	private void delStaticHtml(final CmsPermission cmsperm){
		new Thread(){
			@Override
			public void run() {
				try{
					int type = cmsperm.getSetType();
					switch (type) {
						case 1:
							getHtmlSynchroismService().deleteStaticHtmlBySiteId(cmsperm.getObjectId());
							break;
						case 2:
							getHtmlSynchroismService().deleteStaticHtmlByChannelId(cmsperm.getObjectId());
							break;
						case 3:
							getHtmlSynchroismService().deleteStaticHtmlByDocumentId(cmsperm.getObjectId(), 0L);
							break;
						default:
							break;
					}
				}catch (Exception e) {
					logger.error("-删除静态HTML-", e);
				}
			}
		}.start();
	}

	/**
	 * 检测当前登录者，是否拥有权限
	 * 
	 * @param loginer
	 * @param resCode
	 *            访问资源编码
	 * @param objectid
	 *            访问对象id
	 * @return
	 */
	public boolean haveThePermission(Loginer loginer, String resCode,
			Long objectid) {
		CmsResource cr = siteCache.getResourceFromCache(resCode);
		if (cr == null)
			return false;
		return haveThePermission(loginer, resCode, cr.getObjectType(), objectid);
	}

	/**
	 * 当前登录者，是否拥有权限，若有权限，检测是否为文档创建者
	 * 
	 * @param loginer
	 * @param resCode
	 * @param objectid
	 *            文档id
	 * @return
	 */
	public boolean haveThePermissionAndDocAuthor(Loginer loginer,
			String resCode, Long objectid) {
		if (loginer == null)// 未登录，则表示无权限
			return false;
		if (haveThePermission(loginer, resCode, objectid)) {//若有作者操作权限，检测是否为文档作者
			BaseDocument doc = null;
			try {
				doc = documentCommonService.getDocument(objectid);
				long author = doc.getAuthorId();
				if (author == loginer.getUserid().longValue())
					return true;
			} catch (Exception e) {// 可能文档不存在或频道对应表单对象改变

			}
			;
		}
		return false;
	}

	/**
	 * 检测当前登录者，是否拥有权限
	 * 
	 * @param loginer
	 * @param resCode
	 *            访问资源编码
	 * @param objectType
	 *            对象类型(站点、频道、文档)
	 * @param objectid
	 *            访问对象id
	 * @return
	 */
	public synchronized boolean haveThePermission(Loginer loginer,
			String resCode, int objectType, Long objectid) {
		if (loginer == null) {// 未登录，则表示无权限
			return false;
		}
		if (loginer.checkIsAdministratorUser())
			return true;
		CmsResource cr = siteCache.getResourceFromCache(resCode);
		if (cr == null)
			return false;
		// 缓冲key
		// 对象类型(objectType)+位置类型(setType)+对象id(objectid)+角色类型(roleType)+角色id
		// (roleId)
		if (objectType == Constants.SITE_TYPE) {// 站点
			return haveSiteThePermission(loginer, cr, objectid);
		}
		if (objectType == Constants.CHANNEL_TYPE) {// 频道
			return haveChannelThePermission(loginer, cr, objectType, objectid);
		}
		if (objectType == Constants.DOCUMENT_TYPE) {// 信息
			return haveDocuemntThePermission(loginer, cr, objectType, objectid);
		}
		return false;
	}

	/**
	 * 检测站点的权限,具体规则可自已实现
	 * 
	 * @param loginer
	 * @param cr
	 * @param objectid
	 * @return
	 */
	private boolean haveSiteThePermission(Loginer loginer, CmsResource cr,
			Long objectid) {
		String key = null;
		int objectType = cr.getObjectType();
		if (objectType == 2)// 在频道的权限设置，合在站点的设置上
			objectType = 1;
		// 检测当前人
		key = getPermissionCacheKey(objectType, Constants.SITE_TYPE, objectid,
				Constants.USER_TYPE, loginer.getUserid());
		if (haveThePerm(key, cr.getResourceCode()))
			return true;
		// 检测当前人所属部门
		if (loginer.getDeptcode() != null) {
			key = getPermissionCacheKey(objectType, Constants.SITE_TYPE,
					objectid, Constants.DEPT_TYPE, loginer.getDeptcode());
			if (haveThePerm(key, cr.getResourceCode()))
				return true;
		}

		// 检测当前人所属角色
		if (haveObjectThePermissionByRole(loginer, cr, Constants.SITE_TYPE,
				objectid))
			return true;

		// 若为浏览站点，取当前站点下所有频道检测，若有浏览某一频道的权限，则代表有浏览站点的权限 ---amway add
		if (cr.getResourceCode().equalsIgnoreCase("CMS_SITE_VIEW")) {
			List<Channel> channels = siteCache
					.getChannelsFromcachBySite(objectid.toString());
			CmsResource crc = siteCache
					.getResourceFromCache("CMS_CHANNEL_VIEW");
			for (Channel channel : channels) {
				if (haveChannelThePermission(loginer, crc, 2, channel.getId()))
					return true;
			}
		}
		// 若为浏览文档，取当前站点下所有频道检测，若有浏览某一频道的权限，则代表有浏览前台站点的权限 ---amway add
		if (cr.getResourceCode().startsWith("CMS_DOCUMENT_VIEW")) {
			List<Channel> channels = siteCache
					.getChannelsFromcachBySite(objectid.toString());
			CmsResource crc = siteCache
					.getResourceFromCache("CMS_DOCUMENT_VIEW");// 查看所频道文档的权限
			CmsResource crc_auth = siteCache
					.getResourceFromCache("CMS_DOCUMENT_VIEW_AUTHOR");// 检测是否有作者读权限
			for (Channel channel : channels) {
				if (haveChannelThePermission(loginer, crc, 2, channel.getId()))
					return true;
				if (haveChannelThePermission(loginer, crc_auth, 2, channel
						.getId()))
					return true;
			}
		}
		return false;
	}

	/**
	 * 检测频道类型的权限
	 * 
	 * @param loginer
	 * @param cr
	 * @param objectid
	 * @return
	 */
	public boolean haveChannelThePermission(Loginer loginer, CmsResource cr,
			int objectType, Long objectid) {
		String key = "";
		// 检测当前人
		key = getPermissionCacheKey(cr.getObjectType(), objectType, objectid,
				Constants.USER_TYPE, loginer.getUserid());
		if (haveThePerm(key, cr.getResourceCode()))
			return true;
		// 检测当前人所属部门
		if (loginer.getDeptcode() != null) {
			key = getPermissionCacheKey(cr.getObjectType(), objectType,
					objectid, Constants.DEPT_TYPE, loginer.getDeptcode());
			if (haveThePerm(key, cr.getResourceCode()))
				return true;
		}

		// 检测当前人所属角色
		if (haveObjectThePermissionByRole(loginer, cr, objectType, objectid))
			return true;

		if (objectType == Constants.CHANNEL_TYPE) {
			Channel channel = siteCache.getChannelFromCach(objectid);// inherit
			// 若对象为空，返回无权限
			if (channel == null)
				return false;
			if (cr.getObjectType() == Constants.DOCUMENT_TYPE
					&& (channel.getIsInheritDocPerm() == null || channel
							.getIsInheritDocPerm() == 1l)) {
				if (haveChannelThePermissionByParent(loginer, cr, channel, true))
					return true;
			} else if (cr.getObjectType() != Constants.DOCUMENT_TYPE
					&& channel.getIsInheritPerm() == 1) { // 检测是否继承父对象权限
				if (haveChannelThePermissionByParent(loginer, cr, channel,
						false))
					return true;
			}
		}
		// 若为浏览站点，取当前站点下所有频道检测，若有浏览某一频道的权限，则代表有浏览站点的权限 ---amway add
		if (cr.getResourceCode().equalsIgnoreCase("CMS_CHANNEL_VIEW")
				|| cr.getResourceCode().startsWith("CMS_DOCUMENT_VIEW")) {
			Channel channel = siteCache.getChannelFromCach(objectid);
			List<Channel> channels = siteCache
					.getChannelsFromcachBySite(channel.getSite().getOid()
							.toString());
			if (channels != null
					&& haveChannelThePermissionContainSubChannel(loginer, cr,
							channels, objectid))
				return true;
		}
		return false;
	}

	/**
	 * 检测频道下的子频道是否具有某项权限 递归检测
	 * 
	 * @param loginer
	 * @param cr
	 * @param channels
	 * @param objectid
	 * @return
	 */
	private boolean haveChannelThePermissionContainSubChannel(Loginer loginer,
			CmsResource cr, List<Channel> channels, Long objectid) {

		for (Channel channel : channels) {
			if (channel.getParent() != null
					&& channel.getParent().getId().longValue() == objectid
							.longValue()) {
				if (haveChannelThePermission(loginer, cr, 2, channel.getId()))
					return true;
			}

		}
		return false;
	}

	/**
	 * 检测父频道是否拥有此权限
	 * 
	 * @param loginer
	 * @param cr
	 * @param channel
	 * @param isDoc
	 * @return
	 */
	private boolean haveChannelThePermissionByParent(Loginer loginer,
			CmsResource cr, Channel channel, Boolean isDoc) {
		String key = null;
		Long parentId = null;
		int setType = Constants.CHANNEL_TYPE;// 默认为频道类型
		if (channel.getParent() != null) {
			parentId = channel.getParent().getId();
		}
		if (parentId == null) {// 若父对象为空，检测是否继承站点权限
			if (isDoc ? ((channel.getIsInheritDocPerm() == null || channel
					.getIsInheritDocPerm() > 0) ? true : false) : channel
					.getIsInheritPerm() == 1) {
				setType = Constants.SITE_TYPE;// 站点类型
				parentId = channel.getSite().getOid();// 站点id
			} else
				return false;
		}
		int objectType = cr.getObjectType();
		// 解决在站点上，设置的频道项权限对下都有作用 amway
		if (setType == Constants.SITE_TYPE
				&& cr.getObjectType() == Constants.CHANNEL_TYPE)
			objectType = Constants.SITE_TYPE;
		// 检测当前人
		key = getPermissionCacheKey(objectType, setType, parentId,
				Constants.USER_TYPE, loginer.getUserid());
		if (haveThePerm(key, cr.getResourceCode()))
			return true;
		// 检测当前人所属部门
		if (loginer.getDeptcode() != null) {
			key = getPermissionCacheKey(objectType, setType, parentId,
					Constants.DEPT_TYPE, loginer.getDeptcode());
			if (haveThePerm(key, cr.getResourceCode()))
				return true;
		}
		// 检测当前人所属角色
		if (isDoc ? (channel.getIsInheritDocPerm()==null || channel.getIsInheritDocPerm() == 1) : channel.getIsInheritPerm() == 1){
			if (haveObjectThePermissionByRole(loginer, cr, setType, parentId))
				return true;
		}

		// 检测是否继承父对象权限
		if (channel.getParent() != null
				&& channel.getParent().getId() != null
				&& (isDoc ? ((channel.getIsInheritDocPerm() == null || channel
						.getIsInheritDocPerm() == 1l) ? true : false) : channel
						.getIsInheritPerm() == 1)) {
			key = siteCache.getChannelCacheKey(channel.getSite().getOid()
					.toString(), channel.getParent().getId().toString());
			Channel parentChannel = siteCache.getChannelFromCache(key);
			if (parentChannel != null)
				return haveChannelThePermissionByParent(loginer, cr,
						parentChannel, isDoc);
		}
		return false;
	}

	/**
	 * 检测文档类型的权限
	 * 
	 * @param loginer
	 * @param cr
	 * @param objectid
	 * @return
	 */
	private boolean haveDocuemntThePermission(Loginer loginer, CmsResource cr,
			int objectType, Long objectid) {
		if (objectType == Constants.CHANNEL_TYPE) {// 传递channel id进行检测
			if (haveChannelThePermission(loginer, cr, objectType, objectid))
				return true;
		}
		if (objectType == Constants.DOCUMENT_TYPE) {// 传文档类型对象，进行检测
			// 检测当前人所属角色
			if (haveObjectThePermissionByRole(loginer, cr, objectType, objectid))
				return true;
			String key = null;
			// 检测当前人
			key = getPermissionCacheKey(cr.getObjectType(), objectType,
					objectid, Constants.USER_TYPE, loginer.getUserid());
			if (haveThePerm(key, cr.getResourceCode()))
				return true;
			// 检测当前人所属部门
			if (loginer.getDeptcode() != null) {
				key = getPermissionCacheKey(cr.getObjectType(), objectType,
						objectid, Constants.DEPT_TYPE, loginer.getDeptcode());
				if (haveThePerm(key, cr.getResourceCode()))
					return true;
			}

			// 再检测频道是否拥有的当前权限
			// BaseDocument doc=null;
			CmsBaseDocument doc = null;
			try {
				// doc=documentCommonService.getDocument(objectid);
				doc = documentCommonService.getCmsBaseDocument(objectid);
			} catch (Exception e) {// 可能文档不存在或频道对应表单对象改变

			}
			if (doc != null && (new Integer(1).equals(doc.getIsInheritPerm())
					|| !"CMS_DOCUMENT_VIEW".equals(cr.getResourceCode()) 
					)) {// IsInheritPerm　 表示是否继承权限
				
				if (haveChannelThePermission(loginer, cr,
						Constants.CHANNEL_TYPE, doc.getChannel().getId()))
					return true;
			}
		}
		return false;
	}

	/**
	 * 检测当前用户的角色是否有权限操作指定对象 易联网自带角色连接进入网站时使用的loginer.getRoleCode()
	 * 
	 * @param loginer
	 * @param cr
	 * @param objectType
	 * @param objectid
	 * @return 2012-9-11 16:42:57
	 */
	@SuppressWarnings("unchecked")
	private boolean haveObjectThePermissionByRole(Loginer loginer,
			CmsResource cr, int objectType, Long objectid) {
		// 检测当前人所属角色
		List roles = loginer.getRoles();
		int setobjectType = cr.getObjectType();
		// 解决在站点上，设置的频道项权限对下都有作用 amway
		if (objectType == Constants.SITE_TYPE
				&& cr.getObjectType() == Constants.CHANNEL_TYPE)
			setobjectType = Constants.SITE_TYPE;
		String key;
		if (roles != null && roles.size() > 0) {
			for (int i = 0; i < roles.size(); i++) {
				CoreRole role = (CoreRole) roles.get(i);
				if (role == null)
					continue;
				key = getPermissionCacheKey(setobjectType, objectType,
						objectid, Constants.ROLE_TYPE, role.getOid());
				if (haveThePerm(key, cr.getResourceCode()))
					return true;
			}
		}
		String roleCode = loginer.getRoleCode();
		if (StringUtils.isNotBlank(roleCode)) {
			List<Long> roleIds = this
					.find(
							"select oid from CoreRole where identityGradeString=? or identityGradeString like ? or identityGradeString like ? or identityGradeString like ?",
							roleCode, "%&" + roleCode, "%&" + roleCode + "&%",
							roleCode + "&%");
			for (Long roleId : roleIds) {
				key = getPermissionCacheKey(setobjectType, objectType,
						objectid, Constants.ROLE_TYPE, roleId);
				if (haveThePerm(key, cr.getResourceCode()))
					return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param key
	 * @param resCode
	 * @return
	 */
	private boolean haveThePerm(String key, String resCode) {
		CmsPermission cmsperm = siteCache.getCmsPermissionFromCache(key);
		if (cmsperm != null) {
			return cmsperm.getCmsPermResourceByCode(resCode) != null ? true
					: false;
		}
		return false;
	}

	// 缓冲key
	// 对象类型(objectId)+位置类型(setType)+对象id(objectid)+角色类型(roleType)+角色id(roleId)
	public static String getPermissionCacheKey(int objectType, int setType,
			Long objectId, int roleType, Long roleId) {
		String sep = "_";
		StringBuffer sb = new StringBuffer();
		sb.append(objectType).append(sep).append(setType);
		sb.append(sep).append(objectId);
		sb.append(sep).append(roleType);
		sb.append(sep).append(roleId);
		return sb.toString();
	}

	/**
	 * 获得当前类型的对象，是否可继承父系权限
	 * 
	 * @param type
	 * @param objectId
	 * @return
	 */
	public int getIsInheritPerm(Integer objectType, Integer setType,
			Long objectId) {
		int inher = 1;// 默认为继承
		if (setType == Constants.CHANNEL_TYPE
				&& objectType == Constants.DOCUMENT_TYPE) {// 频道文档对象
			Channel channel = siteCache.getChannelFromCach(objectId);
			if (channel != null)
				inher = (channel.getIsInheritDocPerm() == null) ? new Integer(1)
						: channel.getIsInheritDocPerm().intValue();
		} else if (objectType == Constants.CHANNEL_TYPE) {// 频道对象
			Channel channel = siteCache.getChannelFromCach(objectId);
			if (channel != null)
				inher = channel.getIsInheritPerm();
		} else if (objectType == Constants.DOCUMENT_TYPE) {// 文档对象
			CmsBaseDocument baseDoc = (CmsBaseDocument) this.get(
					CmsBaseDocument.class, objectId);
			if (baseDoc != null)
				inher = baseDoc.getIsInheritPerm();
		}
		return inher;
	}

	/**
	 * 更新对象的继承权限选项
	 * 
	 * @param type
	 * @param objectId
	 * @param isInheritPerm
	 * @return
	 */
	public Boolean updateIsInheritPerm(Integer objectType, String objectId,
			Integer isInheritPerm, Integer setType) {
		if (objectType == Constants.DOCUMENT_TYPE
				&& setType == Constants.CHANNEL_TYPE) {
			Channel channel = this.channelManagerService.get(new Long(objectId));
			channel.setIsInheritDocPerm(isInheritPerm.longValue());
			channelManagerService.saveOrUpdate(channel);
			synchroismCache(channel.getId());
		} else if (objectType == Constants.CHANNEL_TYPE) {// 频道对象
			Channel channel = this.channelManagerService.get(new Long(objectId));
			channel.setIsInheritPerm(isInheritPerm);
			channelManagerService.saveOrUpdate(channel);
			synchroismCache(channel.getId());
		} else if (objectType == Constants.DOCUMENT_TYPE) {// 文档对象
			CmsBaseDocument baseDoc = (CmsBaseDocument) this.get(
					CmsBaseDocument.class, new Long(objectId));
			if (baseDoc == null) {// 信息不存在
				baseDoc = new CmsBaseDocument();
				baseDoc.setId(new Long(objectId));
			}else{
				if(baseDoc.getChannel()!=null){
					HtmlSynchroismService htmlSynchroismService = (HtmlSynchroismService) ServiceLocator.getBean("htmlSynchroismService");
					htmlSynchroismService.deleteStaticHtmlByDocumentId(baseDoc.getId(), baseDoc.getChannel().getId());
				}
			}
			baseDoc.setIsInheritPerm(isInheritPerm);
			this.save(baseDoc);
		}
		return true;
	}
	
	private void synchroismCache(final Long channelId){
		new Thread(){
			@Override
			public void run() {
				try{
					CacheSynchroismService cacheSynchroismService = (CacheSynchroismService) ServiceLocator.getBean("cacheSynchroismService");
					cacheSynchroismService.updateChannel(channelId, "");
					HtmlSynchroismService htmlSynchroismService = (HtmlSynchroismService) ServiceLocator.getBean("htmlSynchroismService");
					htmlSynchroismService.deleteStaticHtmlByChannelId(channelId);
				}catch (Exception e) {
					logger.error("-缓存同步失败-", e);
				}
			}
		}.start();
	}

	/**
	 * 获得当前对象在缓中的字符串
	 * 
	 * @param perm
	 * @return
	 */
	public static String getPermissionCacheKey(CmsPermission perm) {
		return getPermissionCacheKey(perm.getObjectType(), perm.getSetType(),
				perm.getObjectId(), perm.getRoleType(), perm.getRoleId());
	}

	public SiteCache getSiteCache() {
		return siteCache;
	}

	public void setSiteCache(SiteCache siteCache) {
		this.siteCache = siteCache;
	}

	public void setChannelManagerService(
			ChannelManagerService channelManagerService) {
		this.channelManagerService = channelManagerService;
	}

	public DocumentCommonService getDocumentCommonService() {
		return documentCommonService;
	}

	public void setDocumentCommonService(
			DocumentCommonService documentCommonService) {
		this.documentCommonService = documentCommonService;
	}

	/**
	 * 连接CmsPermResource表,获取CmsPermission
	 * 
	 * @param roleType
	 *            角色类型:"1"为用户,"2"为角色
	 * @param objectType
	 * @param objectId
	 * @param resourceCode
	 * @return
	 */
	public List<CmsPermission> findPermission(int roleType, int objectType,
			Long objectId, String resourceCode) {
		DetachedCriteria permissionCriteria = DetachedCriteria.forClass(
				getEntityClass(), "p");
		DetachedCriteria permResourceCriteria = permissionCriteria.createAlias(
				"permResources", "pr");
		permissionCriteria.add(Restrictions.eq("roleType", roleType));
		permissionCriteria.add(Restrictions.eq("objectType", objectType));
		permissionCriteria.add(Restrictions.eq("objectId", objectId));
		permResourceCriteria.add(Restrictions.eq("pr.resourceCode",
				resourceCode));
		return getHibernateTemplate().findByCriteria(permissionCriteria);
	}

	/**
	 * 
	 * @author Dicky
	 * @param roleType
	 * @param objectType
	 * @param setType
	 * @param objectId
	 * @param resourceCode
	 * @return
	 * @time 2012-9-6 16:11:07
	 */
	@SuppressWarnings("unchecked")
	public List<CmsPermission> findPermission(int roleType, int objectType,
			int setType, Long objectId, String resourceCode) {
		DetachedCriteria permissionCriteria = DetachedCriteria.forClass(
				getEntityClass(), "p");
		DetachedCriteria permResourceCriteria = permissionCriteria.createAlias(
				"permResources", "pr");
		permissionCriteria.add(Restrictions.eq("roleType", roleType));
		permissionCriteria.add(Restrictions.eq("objectType", objectType));
		permissionCriteria.add(Restrictions.eq("setType", setType));
		permissionCriteria.add(Restrictions.eq("objectId", objectId));
		permResourceCriteria.add(Restrictions.eq("pr.resourceCode",resourceCode));
		return getHibernateTemplate().findByCriteria(permissionCriteria);
	}

	/**
	 * 查找对象objectId的所有权限包括父系权限
	 * 
	 * @author Dicky
	 * @time 2012-9-13 19:22:17
	 * @version 1.0
	 * @param roleType
	 * @param objectType
	 * @param setType
	 *            权限位置: 1站点、2频道、3文档
	 * @param objectId
	 * @param resourceCode
	 * @return
	 */
	public List<CmsPermission> findAllPermissions(int roleType, int objectType,
			int setType, Long objectId, String resourceCode) {
		List<CmsPermission> list0 = findPermission(roleType, objectType,
				setType, objectId, resourceCode);
		if (Constants.SITE_TYPE == setType) {
		} else if (Constants.CHANNEL_TYPE == setType) {
			Channel channel = (Channel) getHibernateTemplate().get(
					Channel.class, objectId);
			if (channel.getIsInheritPerm() == 1 && channel.getParent() != null
					&& channel.getParent().getId() != null) {
				List<CmsPermission> list1 = findAllPermissions(roleType,
						objectType, Constants.CHANNEL_TYPE, channel.getParent()
								.getId(), resourceCode);
				list0 = putAll(list0, list1);
			} else if (channel.getIsInheritPerm() == 1
					&& channel.getParent() == null) {
				List<CmsPermission> list1 = findAllPermissions(roleType,
						objectType, Constants.SITE_TYPE, channel.getSite()
								.getOid(), resourceCode);
				list0 = putAll(list0, list1);
			}
		} else if (Constants.DOCUMENT_TYPE == setType) {
			CmsBaseDocument document = (CmsBaseDocument) getHibernateTemplate()
					.get(CmsBaseDocument.class, objectId);
			if (document.getIsInheritPerm() == 1
					&& document.getChannel() != null
					&& document.getChannel().getId() != null) {
				List<CmsPermission> list1 = findAllPermissions(roleType,
						objectType, Constants.CHANNEL_TYPE, document
								.getChannel().getId(), resourceCode);
				list0 = putAll(list0, list1);
			}
		}
		return list0;
	}

	private List<CmsPermission> putAll(List<CmsPermission> list0,
			List<CmsPermission> list1) {
		if (list0 == null || list0.size() == 0)
			return list1;
		if (list1 == null || list1.size() == 0)
			return list0;
		for (CmsPermission cmsPermission : list1) {
			if (!list0.contains(cmsPermission)) {
				list0.add(cmsPermission);
			}
		}
		return list0;
	}

	public List<CoreRole> findRoles(int roleType, int objectType, int setType,
			Long objectId, String resourceCode) {
		List<CmsPermission> list0 = findAllPermissions(roleType, objectType,
				setType, objectId, resourceCode);
		RoleManagerService roleManagerService = (RoleManagerService) ServiceLocator
				.getBean("roleManagerService");
		List<CoreRole> list1 = new ArrayList<CoreRole>();
		for (CmsPermission cmsPermission : list0) {
			CoreRole role = roleManagerService.getRoleFromCache(cmsPermission
					.getRoleId()
					+ "_T" + cmsPermission.getRoleType());
			if (!list1.contains(role)) {
				list1.add(role);
			}
		}
		return list1;
	}

	/**
	 * 清除所有权限缓存
	 */
	public void removeAllCache() {
		siteCache.removeAllPermission();
	}

	/**
	 * 获取所有权限缓存中的key
	 * 
	 * @return
	 */
	public List<String> getAllCacheKeys() {
		return siteCache.getPermissionCacheKeys();
	}

	/**
	 * 获得指定的权限缓存
	 * 
	 * @param key
	 * @return
	 */
	public net.sf.ehcache.Element getElementFromCache(String key) {
		if (StringUtils.isNotEmpty(key)) {
			net.sf.ehcache.Element element = null;
			element = siteCache.getElementFromPermission(key);
			return element;
		} else
			return null;
	}

	/**
	 * 用角色(CoreRole)ID删除关联的权限(CmsPermission)信息
	 * 
	 * @remark add by liaozhiyong 2012-03-22
	 * @param CoreRoleId
	 * @return boolean
	 */
	public boolean deletePermissionByRoleId(String CoreRoleId) {
		if (!StringUtils.isNotEmpty(CoreRoleId)) {
			return false;
		}
		List list = this.find("from CmsPermission where roleId=" + CoreRoleId);
		if (list != null && !list.isEmpty()) {
			CacheSynchroismService cacheSynchroismService = (CacheSynchroismService) ServiceLocator
					.getBean("cacheSynchroismService");
			for (int i = 0; i < list.size(); i++) {
				CmsPermission cmsperm = (CmsPermission) list.get(i);
				siteCache.removeCmsPermissionFromCache(getPermissionCacheKey(cmsperm));
				/* 缓存同步 */
				try {
					cacheSynchroismService.updateCmsPermission(cmsperm.getOid().toString(), "del");
				} catch (Exception ex) {
					ex.printStackTrace();
					continue;
				}
				remove(cmsperm);
			}
		}
		return true;
	}

	/**
	 * dwr 验证 : 删除角色(CoreRole)时,判断有没有并联 CmsPermission ;
	 * 根据角色(CoreRoleId)，获得CmsPermission信息列表
	 * 
	 * @remark add by liaozhiyong 2012-03-24
	 * @param CoreRoleId
	 * @return String
	 */
	public String dwrCoreRoleValidateHaveCmsPermission(String CoreRoleId) {
		StringBuilder strCoreCommonInfo = new StringBuilder();
		if (CoreRoleId != null && CoreRoleId.length() > 0) {
			String[] tempArray = CoreRoleId.split(",");
			String tempEmpty = "  ";
			for (String string : tempArray) {
				Long id = Long.valueOf(string);
				List<CmsPermission> tempList = this.find("from CmsPermission where roleId=" + id);
				if (tempList != null && tempList.size() > 0) {
					strCoreCommonInfo.append(tempEmpty);
					break;
				}
			}
			if (strCoreCommonInfo.length() > 0) {
				strCoreCommonInfo.insert(0, "选择的角色有关联权限信息!\n").append("\n");
			}
		} else {
			strCoreCommonInfo.append("");
		}
		return strCoreCommonInfo.toString();
	}

	/**
	 * 保存单个文档权限
	 * 
	 * @author Dicky
	 * @time 2012-9-12 20:47:40
	 * @version 1.0
	 * @param objectId
	 * @param roleIds
	 *            外部角色
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean saveDocumentPermission(String objectId, String roleIds) {
		// 历史权限
		List<Long> perIds = this.find(
				"select oid from CmsPermission where objectId=? and setType=?",
				Long.valueOf(objectId), Constants.DOCUMENT_TYPE);
		String[] ids = roleIds.split(",");
		CmsPermResource pr = null;
		CmsResource crc = siteCache.getResourceFromCache("CMS_DOCUMENT_VIEW");
		for (int i = 0; i < ids.length; i++) {
			Long roleId = null;
			try {
				roleId = Long.valueOf(ids[i]);
			} catch (Exception e) {
				continue;
			}
			if (roleId == null)
				continue;
			CoreRole role = (CoreRole) this.get(CoreRole.class, roleId);
			if (role == null)
				continue;
			String perKey = getPermissionCacheKey(Constants.DOCUMENT_TYPE,
					Constants.DOCUMENT_TYPE, Long.valueOf(objectId),
					Constants.ROLE_TYPE, role.getOid());
			CmsPermission permission = siteCache.getCmsPermissionFromCache(perKey);
			if (permission != null) {
				perIds.remove(permission.getOid());
				continue;
			}
			boolean notExsit = insertPermission(Constants.ROLE_TYPE, role
					.getOid().toString(), role.getRolename(),
					Constants.DOCUMENT_TYPE, objectId, Constants.DOCUMENT_TYPE);
			permission = siteCache.getCmsPermissionFromCache(perKey);
			if (notExsit) {
				List<CmsPermResource> list0 = this.find("from CmsPermResource where permission.oid=? and resource.oid=?",
								permission.getOid(), crc.getOid());
				if (list0.size() == 0) {
					pr = new CmsPermResource();
					pr.setPermission(permission);
					pr.setResource(crc);
					pr.setResourceCode(crc.getResourceCode());
					this.save(pr);
					permission.getPermResources().add(pr);
					siteCache.putCmsPermissionInCache(perKey, permission);
				}
			} else {
				if (perIds.contains(permission.getOid())) {
					perIds.remove(permission.getOid());
				}
			}
		}
		for (Long perId : perIds) {
			List<CmsPermResource> list1 = this.find("from CmsPermResource where permission.oid=?", perId);
			for (CmsPermResource cmsPermResource : list1) {
				getHibernateTemplate().delete(cmsPermResource);
			}
			deletePermission(perId.toString());
		}
		if(perIds.size()>0){
			getHtmlSynchroismService().deleteStaticHtmlByDocumentId(Long.valueOf(objectId), 0L);
		}
		return true;
	}
	
	public HtmlSynchroismService getHtmlSynchroismService() {
		return (HtmlSynchroismService) ServiceLocator.getBean("htmlSynchroismService");
	}
}
