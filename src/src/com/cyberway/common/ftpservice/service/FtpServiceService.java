package com.cyberway.common.ftpservice.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.CmsIpDistribution.service.CmsIpDistributionService;
import com.cyberway.cms.domain.CmsDistribution;
import com.cyberway.cms.domain.CmsIpDistribution;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.cms.siteDistribution.service.SiteDistributionService;
import com.cyberway.common.domain.CoreSiteDistribution;
import com.cyberway.common.service.CommonCache;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.staticer.ftp.SiteFTPClient;
import com.cyberway.staticer.ftp.SiteFTPClientImpl;

/**
 * ftp服务器配置管理
 * 
 * @author taoz
 * 
 *         2011-11-3下午02:57:37
 */
public class FtpServiceService extends HibernateEntityDao<CoreSiteDistribution> {

	private CommonCache	             commonCache;	              // 公共缓存管理器
	private SiteManagerService	     siteService;
	private SiteDistributionService	 siteDistributionService;
	private CmsIpDistributionService	cmsIpDistributionService;

	/**
	 * 初始化ftp服务器配置管理
	 */
	public synchronized void init() {
		List<CmsSite> siteList = siteService.getAllSites();
		for (CmsSite site : siteList) {
			List<CmsDistribution> cmsDistributionList = siteDistributionService.findBy("siteId", site.getOid());
			for (CmsDistribution cmsDistribution : cmsDistributionList) {
				List<CmsIpDistribution> cmsIpDistributionList = cmsIpDistributionService.findBy("did", cmsDistribution.getId());
				for (CmsIpDistribution c : cmsIpDistributionList) {
					CoreSiteDistribution ftpConfig = this.get(c.getIpid());
					commonCache.putSiteDistributionInCache(site.getOid(), cmsDistribution.getDtype(), ftpConfig);
				}
			}
		}
		List<CoreSiteDistribution> l = commonCache.getAllSiteDistributions();
	}

	/**
	 * 从缓存中取ftp服务器配置管理信息
	 * 
	 * @param userid
	 * @return
	 */
	public CoreSiteDistribution getSiteDistributionFromCache(String id) {
		return commonCache.getSiteDistributionFromCache(id);
	}

	/**
	 * 从缓存中获得所有ftp服务器配置管理信息
	 * 
	 * @return
	 */
	public List<CoreSiteDistribution> getAllSiteDistributionsFromCache() {
		return commonCache.getAllSiteDistributions();
	}

	/**
	 * 从缓存中删除对应的ftp服务器配置管理信息
	 * 
	 * @return
	 */
	public void removeSiteDistributionsFromCache(String id) {
		commonCache.removeSiteDistributionFromCache(id);
	}

	/**
	 * 从缓存中删除所有ftp服务器配置管理信息
	 * 
	 * @return
	 */
	public void removeAllSiteDistributionsFromCache() {
		commonCache.removeAllSiteDistributions();
	}

	/**
	 * 根据站点id,站点资源类型,获取站点资源类型跟ftp服务器的关系集合
	 * 
	 * @param siteId
	 * @param resourceType
	 * @return
	 */
	public List<CoreSiteDistribution> getDistributionBySiteAndResource(Long siteId, String resourceType) {
		return commonCache.getDistributionBySiteAndResource(siteId, resourceType);
	}

	public void putSiteDistributionInCache(Long siteId, String resourceType, CoreSiteDistribution resourceDetails) {
		commonCache.putSiteDistributionInCache(siteId, resourceType, resourceDetails);
	}

	public void removeSiteDistributionFromCache(Long siteId, String resourceType, Long ftpId) {
		commonCache.removeSiteDistributionFromCache(siteId, resourceType, ftpId);
	}

	public CoreSiteDistribution getSiteDistributionByKey(Long siteId, String resourceType, Long ftpId) {
		return commonCache.getSiteDistributionByKey(siteId, resourceType, ftpId);
	}

	public CommonCache getCommonCache() {
		return commonCache;
	}

	public void setCommonCache(CommonCache commonCache) {
		this.commonCache = commonCache;
	}

	public SiteManagerService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteManagerService siteService) {
		this.siteService = siteService;
	}

	public SiteDistributionService getSiteDistributionService() {
		return siteDistributionService;
	}

	public void setSiteDistributionService(SiteDistributionService siteDistributionService) {
		this.siteDistributionService = siteDistributionService;
	}

	public CmsIpDistributionService getCmsIpDistributionService() {
		return cmsIpDistributionService;
	}

	public void setCmsIpDistributionService(CmsIpDistributionService cmsIpDistributionService) {
		this.cmsIpDistributionService = cmsIpDistributionService;
	}

	/**
	 * 清除所有ftp服务器缓存
	 */
	public void removeAllCache() {
		commonCache.removeAllFtpServer();
	}

	/**
	 * 获取所有ftp服务器缓存中的key
	 * 
	 * @return
	 */
	public List<String> getAllCacheKeys() {
		return commonCache.getFtpServerCacheKeys();
	}

	/**
	 * 获得指定的ftp服务器缓存
	 * 
	 * @param key
	 * @return
	 */
	public net.sf.ehcache.Element getElementFromCache(String key) {
		if (StringUtils.isNotEmpty(key)) {
			net.sf.ehcache.Element element = null;
			element = commonCache.getElementFromFtpServer(key);
			return element;
		} else
			return null;
	}

	/**
	 * 根据站点id获取分发的服务器的集合
	 * 
	 * @param id
	 *            站点id
	 * @return
	 */
	public List<CoreSiteDistribution> getFtpBySiteId(Long id) {
		Criteria c = getEntityCriteria();
		c.add(Restrictions.eq("siteId", id));
		return c.list();
	}
	
	/**
	 * 测试ftp是否可以连接上服务器
	 * 
	 * @param id
	 *            ftp服务器配置对象的id
	 * @return "1"代表可以连接上服务器,"0"代表不可以连接上服务器
	 */
	public int checkFtpConnect(Long id) {
		CoreSiteDistribution ftpConfig = get(id);

		// 解密
		String ftpPassWord = ftpConfig.getPassWord();
		if (StringUtils.isNotBlank(ftpPassWord)) {
			ThreeDES des = new ThreeDES();
			ftpPassWord = des.getDesString(ftpPassWord);
		}

		SiteFTPClient siteFtpClient = new SiteFTPClientImpl(ftpConfig.getId(), ftpConfig.getFtpIp(), ftpConfig.getPort(),
		        ftpConfig.getUserName(), ftpPassWord, ftpConfig.getIsFtp().equals("2") ? true : false);
		if (siteFtpClient.checkFtpConnect()) {
			return 1;
		} else {
			return 0;
		}
	}
}
