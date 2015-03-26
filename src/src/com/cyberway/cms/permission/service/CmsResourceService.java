package com.cyberway.cms.permission.service;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import com.cyberway.cms.permission.domain.CmsResource;
import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.cms.staticResource.service.StaticResourceService;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.exception.BizException;
import com.cyberway.core.utils.ServiceLocator;

public class CmsResourceService extends HibernateEntityDao<CmsResource> implements InitializingBean, ServletContextAware {
	public static String[]	ObjectResourceTypes	= { "", "站点", "频道", "文档", "模板" };
	// private static GeneralCacheAdministrator resCache = new
	// GeneralCacheAdministrator();
	// 资源编码--资源对象
	private File	       storeDir;	                                          // 文件对象
	private ServletContext	servletContext;
	private String	       permResourceListPrefix;
	private String	       permResourceListSuffix;
	private SiteCache	   siteCache;

	/**
	 * 初始化资源
	 */
	public synchronized void init() {
		List<CmsResource> ress = getAll();
		if (!ress.isEmpty()) {
			for (CmsResource res : ress) {
				setResourceCache(res.getResourceCode(), res);
			}
		}
	}

	/**
	 * 设置到缓冲区中
	 * 
	 * @param resCode
	 * @param resString
	 */
	public void setResourceCache(String resCode, CmsResource res) {
		siteCache.putResourceInCache(res);
	}

	/**
	 * 从缓冲区内取出
	 * 
	 * @param resCode
	 * @return
	 */
	public CmsResource getResourceCache(String resCode) {
		CmsResource cr = siteCache.getResourceFromCache(resCode);
		if (cr == null) {
			cr = this.findUniqueBy("resourceCode", resCode);
			siteCache.putResourceInCache(cr);
		}
		return cr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cyberway.core.dao.HibernateEntityDao#saveOrUpdate(java.lang.Object)
	 */
	public synchronized CmsResource saveOrUpdate(CmsResource obj) {
		this.evit(obj);
		Boolean unique = this.isNotUnique(obj, "resourceCode");
		// 如果已经存在相同的登录ID
		if (unique) {
			throw new BizException("CMS资源编码不能重复！");
		}
		obj = super.saveOrUpdate(obj);
		setResourceCache(obj.getResourceCode(), obj);
		// 重新生成对应的页面
		getPermResourcePage(obj.getObjectType());
		// 若为频道层的，重新生成站点层的
		if (obj.getObjectType() == 2)
			getPermResourcePage(obj.getObjectType() - 1);
		return obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.core.dao.HibernateEntityDao#removeByIds(java.util.List)
	 */
	public void removeByIds(List<Long> ids) {
		List<Integer> types = new ArrayList();
		for (Long id : ids) {
			CmsResource rs = this.get(id);
			// 删除权限资源时，同时删除缓冲内容
			this.siteCache.removeStaticResourceFromCache(rs.getResourceCode());
			types.add(rs.getObjectType());
			this.remove(rs);
		}
		// super.removeByIds(ids);
		// 重新生成权限界面
		for (Integer type : types)
			this.getPermResourcePage(type);
	}

	/**
	 * 根据缓冲区内容，重新生成页面
	 * 
	 * @param type
	 * @return
	 */
	private synchronized String getPermResourcePage(int type) {

		File file = null;
		String body = "";
		String encoding = "UTF-8";
		String filename = "_perm_resource_list.jsp";
		if (type == 1)
			filename = "site" + filename;
		else if (type == 2)
			filename = "channel" + filename;
		else if (type == 3)
			filename = "document" + filename;
		else if (type == 4)// 模板权限页面
			filename = "template" + filename;

		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		List<CmsResource> crs = this.find("from CmsResource where objectType=? order by orderNo", new Object[] { type });
		for (CmsResource cr : crs) {
			if (cr.getLevelIsView() == 0)
				writer.write(String.format("<c:if test=\"${objectType!=type}\">\n"));
			writer.write(String.format("<ec:column property=\"%s\" title=\"%s\" editable=\"false\" filterable=\"false\" />\n",
			        cr.getResourceCode(), cr.getResourceName()));
			if (cr.getLevelIsView() == 0)
				writer.write(String.format("</c:if>\n"));
		}
		if (type < 2) {// 增下级权限设置 在站点上增加频道权限设置　在频道上，增加文档权限设置
			crs = this.find("from CmsResource where objectType=? order by orderNo", new Object[] { type + 1 });
			for (CmsResource cr : crs) {
				if (cr.getLevelIsView() == 0)
					writer.write(String.format("<c:if test=\"${objectType!=type}\">\n"));
				writer.write(String.format("<ec:column property=\"%s\" title=\"%s\" editable=\"false\" filterable=\"false\" />\n",
				        cr.getResourceCode(), cr.getResourceName()));
				if (cr.getLevelIsView() == 0)
					writer.write(String.format("</c:if>\n"));
			}
		}
		body = StringUtils.join(new Object[] { getPermResourceListPrefix(), stringWriter.toString(), getPermResourceListSuffix() });

		String path = FilenameUtils.concat(getStoreDir().getAbsolutePath(), filename);
		file = new File(path);
		try {
			FileUtils.writeStringToFile(file, body, encoding);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		/*
		 * String virtualPath =
		 * file.getAbsolutePath().substring(getServletContext
		 * ().getRealPath("/").length()); virtualPath = "/" +
		 * FilenameUtils.separatorsToUnix(virtualPath);
		 */
		return "";
	}

	/**
	 * 根据类型跳到指定页面
	 * 
	 * @param type
	 * @return
	 */
	public String getResourcePageUrlByType(int type) {
		String filename = "_perm_resource_list.jsp";
		if (type == 1)
			filename = "site" + filename;
		else if (type == 2)
			filename = "channel" + filename;
		else if (type == 3)
			filename = "document" + filename;
		else if (type == 4)
			filename = "template" + filename;
		return "/WEB-INF/pages/cms/permission/" + filename;
		// return FilenameUtils.concat(getStoreDir().getPath(), filename);

	}

	/**
	 * 获得指定类型的权限资源
	 * 
	 * @param type
	 * @return
	 */
	public Map<String, String> getResourceByType(int type) {
		Map<String, String> ress = new TreeMap();
		// List<String> keys=this.siteCache.getAllResourcesKey();
		List<CmsResource> crs = this.find("from CmsResource where objectType=? order by orderNo", new Object[] { type });
		/*
		 * for (String key :keys) { CmsResource
		 * cr=siteCache.getResourceFromCache(key); if(cr.getObjectType()==type)
		 * ress.put(cr.getResourceCode(), cr.getResourceName()); }
		 */
		// this.siteCache.get
		for (CmsResource cr : crs) {
			ress.put(cr.getResourceCode(), cr.getResourceName());
		}
		return ress;
	}

	public File getStoreDir() {
		return storeDir;
	}

	public void setStoreDir(File storeDir) {
		this.storeDir = storeDir;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public String getPermResourceListPrefix() {
		return permResourceListPrefix;
	}

	public void setPermResourceListPrefix(String permResourceListPrefix) {
		this.permResourceListPrefix = permResourceListPrefix;
	}

	public String getPermResourceListSuffix() {
		return permResourceListSuffix;
	}

	public void setPermResourceListSuffix(String permResourceListSuffix) {
		this.permResourceListSuffix = permResourceListSuffix;
	}

	public SiteCache getSiteCache() {
		return siteCache;
	}

	public void setSiteCache(SiteCache siteCache) {
		this.siteCache = siteCache;
	}

	/**
	 * 初始化资源缓存和静态资源缓存
	 */
	public void initAllResource() {
		StaticResourceService staticResourceService = (StaticResourceService) ServiceLocator.getBean("staticResourceService");
		staticResourceService.init();
		this.init();
	}
	
	/**
	 * 清除所有资源缓存
	 */
	public void removeAllCache(){
		siteCache.removeAllResource();
	}
	
	/**
	 * 获取所有资源缓存中的key
	 * @return
	 */
	public List<String> getAllCacheKeys(){
		return siteCache.getAllResourcesKey();
	}
	
	/**
	 * 获得指定的权限缓存
	 * 
	 * @param key
	 * @return
	 */
	public net.sf.ehcache.Element getElementFromCache(String key) {
		if (StringUtils.isNotEmpty(key)){
			net.sf.ehcache.Element element = null;
			element = siteCache.getElementFromResource(key);
			return element;
		}
		else
			return null;
	}
	
	/**
	 * 判断资源编码是否唯一
	 * 
	 * @param id
	 *            资源id
	 * @param resourceCode
	 *            资源编码
	 * @return "0"表示不唯一,"1"表示唯一
	 */
	public int resourceCodeIsUnique(Long id, String resourceCode) {
		List<CmsResource> resourceList;
		if (!id.equals(0L)) {
			CmsResource resource = get(id);
			if (resource.getResourceCode().equals(resourceCode)) {
				return 1;
			} else {
				resourceList = findBy("resourceCode", resourceCode);
				if (resourceList.size() > 0) {
					return 0;
				} else {
					return 1;
				}
			}
		} else {
			resourceList = findBy("resourceCode", resourceCode);
			if (resourceList.size() > 0) {
				return 0;
			} else {
				return 1;
			}
		}
	}
}
