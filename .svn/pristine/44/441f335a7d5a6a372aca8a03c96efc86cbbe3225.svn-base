package com.cyberway.cms.siteDistribution.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.domain.CmsDistribution;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.cms.siteDistribution.view.SiteDistributionController;
import com.cyberway.cms.webservice.service.HtmlSynchroismService;
import com.cyberway.common.distribution.service.DistributionService;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

/**
 * 站点分发管理
 * 
 * @author taoz
 * 
 *         2011-11-3下午02:57:37
 */
public class SiteDistributionService extends HibernateEntityDao<CmsDistribution> {
	/**
	 * 根据站点id,资源类型获取CmsDistribution的集合
	 * 
	 * @param siteId
	 * @param resourceType
	 * @return
	 */
	public List<CmsDistribution> getBySiteAndResourceType(Long siteId, String resourceType) {
		Criteria c = getEntityCriteria();
		c.add(Restrictions.eq("siteId", siteId));
		c.add(Restrictions.eq("dtype", resourceType));
		return c.list();
	}

	/**
	 * 根据站点,是否分发,搜索该站点下的资源类型
	 * 
	 * @param siteId
	 *            站点id
	 * @param dipId
	 *            "0"为不需要分发,"1"为需要分发
	 * @return
	 */
	public List<CmsDistribution> getBySiteAndDipId(Long siteId, String dipId) {
		Criteria c = getEntityCriteria();
		c.add(Restrictions.eq("siteId", siteId));
		c.add(Restrictions.eq("dipId", dipId));
		return c.list();
	}
	/**
	 * 分发
	 * @param siteId
	 * @param flag
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public boolean distribution(String ids, String siteId) {
		DistributionService distributionService = (DistributionService) ServiceLocator.getBean("distributionService");
		SiteManagerService siteManagerService = (SiteManagerService) ServiceLocator.getBean("siteManagerService");
		if (!StringUtil.isEmpty(ids)) {
			List<Long> list = StringUtil.splitToList(ids, ",");
			for (int i = 0; i < list.size(); i++) {
				try {
					CmsDistribution c = this.get(list.get(i));
					if (c.getDtype().trim().equals(SiteDistributionController.STATIC_RESOURCE)) {// 站点静态资源
						distributionService.sendStaticResource(Long.valueOf(siteId), c.getDtype().trim(), true);
					} else if (c.getDtype().trim().equals(SiteDistributionController.JSP_RESOURCE)) {// 站点jsp动态资源
						distributionService.sendJspResource(Long.valueOf(siteId), c.getDtype().trim(), true);
					} else if (c.getDtype().trim().equals(SiteDistributionController.WORD_RESOURCE)) {// 站点word文档资源
						distributionService.sendOfficeResource(Long.valueOf(siteId), c.getDtype().trim(), true);
					} else if (c.getDtype().trim().equals(SiteDistributionController.HTML_RESOURCE)) {// 站点静态html文件资源
						HtmlSynchroismService htmlSynchroismService = (HtmlSynchroismService) ServiceLocator.getBean("htmlSynchroismService");
						htmlSynchroismService.deleteStaticHtmlBySiteId(Long.parseLong(siteId));
						htmlSynchroismService.deleteLocalHtmlBySite(Long.parseLong(siteId));
						CmsSite site = siteManagerService.getSiteFromCache(Long.parseLong(siteId));
						distributionService.sendHtmlResource(site, c.getDtype().trim(), true);
					} else if (c.getDtype().trim().equals(SiteDistributionController.OTHER_RESOURCE)) {// 站点其他资源
						distributionService.sendOtherResource(Long.valueOf(siteId), c.getDtype().trim(), true);
					} else if (c.getDtype().trim().equals(SiteDistributionController.LUCENE_RESOURCE)) {// 站点lucene资源
						distributionService.sendLuceneResource(Long.valueOf(siteId), c.getDtype().trim(), true);
					} else if (c.getDtype().trim().equals(SiteDistributionController.MEDIA_RESOURCE)) {// 站点media资源
						distributionService.sendMediaResource(Long.valueOf(siteId), c.getDtype().trim(), true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return true;
	}
	
	public boolean deleteDistribution(String ids, String siteId) {
		DistributionService distributionService = (DistributionService) ServiceLocator.getBean("distributionService");

		SiteManagerService siteManagerService = (SiteManagerService) ServiceLocator.getBean("siteManagerService");

		if (!StringUtil.isEmpty(ids)) {
			List<Long> list = StringUtil.splitToList(ids, ",");
			for (int i = 0; i < list.size(); i++) {
				try {
					CmsDistribution c = this.get(list.get(i));
					if (c.getDtype().trim().equals(SiteDistributionController.STATIC_RESOURCE)) {// 站点静态资源
						distributionService.deleteStaticResource(Long.valueOf(siteId), c.getDtype().trim());
					} else if (c.getDtype().trim().equals(SiteDistributionController.JSP_RESOURCE)) {// 站点jsp动态资源
						distributionService.deleteJspResource(Long.valueOf(siteId), c.getDtype().trim());
					} else if (c.getDtype().trim().equals(SiteDistributionController.WORD_RESOURCE)) {// 站点word文档资源
						distributionService.deleteOfficeResource(Long.valueOf(siteId), c.getDtype().trim());
					} else if (c.getDtype().trim().equals(SiteDistributionController.HTML_RESOURCE)) {// 站点静态html文件资源
						CmsSite site = siteManagerService.getSiteFromCache(Long.parseLong(siteId));
						distributionService.deleteHtmlResource(site, c.getDtype().trim());
					} else if (c.getDtype().trim().equals(SiteDistributionController.OTHER_RESOURCE)) {// 站点其他资源
						distributionService.deleteOtherResource(Long.valueOf(siteId), c.getDtype().trim());
					} else if (c.getDtype().trim().equals(SiteDistributionController.LUCENE_RESOURCE)) {// 站点lucene资源
						distributionService.deleteLuceneResource(Long.valueOf(siteId), c.getDtype().trim());
					} else if (c.getDtype().trim().equals(SiteDistributionController.MEDIA_RESOURCE)) {// 站点media资源
						distributionService.deleteMediaResource(Long.valueOf(siteId), c.getDtype().trim());
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return true;
	}
	
	/**
	 * 判断分发资源类型是否唯一
	 * 
	 * @param id
	 *            分发资源类型id
	 * @param siteId
	 *            站点id
	 * @param resType
	 *            分发资源类型
	 * @return "0"表示不唯一,"1"表示唯一
	 */
	public int resTypeIsUnique(Long id, Long siteId, String resType) {
		List<CmsDistribution> resList;
		if (!id.equals(0L)) {
			CmsDistribution res = get(id);
			if (res.getDtype().equals(resType)) {
				return 1;
			} else {
				resList = getBySiteAndResourceType(siteId, resType);
				if (resList.size() > 0) {
					return 0;
				} else {
					return 1;
				}
			}
		} else {
			resList = getBySiteAndResourceType(siteId, resType);
			if (resList.size() > 0) {
				return 0;
			} else {
				return 1;
			}
		}
	}
}
