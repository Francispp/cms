package com.cyberway.cms.CmsIpDistribution.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.domain.CmsDistribution;
import com.cyberway.cms.domain.CmsIpDistribution;
import com.cyberway.cms.siteDistribution.service.SiteDistributionService;
import com.cyberway.common.domain.CoreSiteDistribution;
import com.cyberway.common.ftpservice.service.FtpServiceService;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.ServiceLocator;

public class CmsIpDistributionService extends HibernateEntityDao<CmsIpDistribution> {
	public List<CmsIpDistribution> findByType(Long siteId, Long did) {
		Criteria c = getEntityCriteria();
		c.add(Restrictions.eq("siteId", siteId));
		c.add(Restrictions.eq("did", did));
		List<CmsIpDistribution> l = (List<CmsIpDistribution>) c.list();
		if (l.isEmpty()) {
			return new ArrayList();
		} else {
			return l;
		}
	}
	
	/**
	 * 根据站点id跟ftp服务器Id获得:分发资源类型跟ftp服务器的中间表的数据
	 * @param siteId
	 * @param ipId
	 * @return
	 */
	public List<CmsIpDistribution> findByFtp(Long siteId, Long ipId) {
		Criteria c = getEntityCriteria();
		c.add(Restrictions.eq("siteId", siteId));
		c.add(Restrictions.eq("ipid", ipId));
		List<CmsIpDistribution> l = (List<CmsIpDistribution>) c.list();
		if (l.isEmpty()) {
			return new ArrayList();
		} else {
			return l;
		}
	}

	/**
	 * 保存分发资源跟ftp服务器的对应关系
	 * 
	 * @param ipids
	 *            ftp服务器id集合
	 * @param dtype
	 *            分发资源类型
	 * @param did
	 *            分发类型id
	 * @param siteId
	 *            站点id
	 */
	public void saveSelectIp(List<Long> ipids, String dtype, Long did, Long siteId) {
		FtpServiceService ftpServiceService = (FtpServiceService) ServiceLocator.getBean("ftpServiceService");
		if (ipids.size() > 0) {
			List<CmsIpDistribution> cidList = this.findByType(siteId, did);
			for (CmsIpDistribution ipDistribution : cidList) {// 删除掉之前保存的记录
				ftpServiceService.removeSiteDistributionFromCache(siteId, dtype, ipDistribution.getIpid());
				this.remove(ipDistribution);
			}
			for (int i = 0; i < ipids.size(); i++) {// 保存
				CmsIpDistribution ipDistribution = new CmsIpDistribution();
				ipDistribution.setDid(did);
				ipDistribution.setIpid(ipids.get(i));
				ipDistribution.setSiteId(siteId);
				this.save(ipDistribution);

				CoreSiteDistribution csdb = ftpServiceService.get(ipids.get(i));
				ftpServiceService.putSiteDistributionInCache(siteId, dtype, csdb);
			}
		} else {
			List<CmsIpDistribution> cidList = this.findByType(siteId, did);
			for (CmsIpDistribution ipDistribution : cidList) {// 删除掉之前保存的记录
				ftpServiceService.removeSiteDistributionFromCache(siteId, dtype, ipDistribution.getIpid());
				this.remove(ipDistribution);
			}
		}
	}
	
	/**
	 * 删除分发资源跟ftp服务器的对应关系
	 * 
	 * @param siteId
	 *            站点id
	 * @param did
	 *            分发资源id
	 * @param dtype
	 *            分发资源类型
	 */
	public void removeIp(Long siteId, Long did, String dtype) {
		FtpServiceService ftpServiceService = (FtpServiceService) ServiceLocator.getBean("ftpServiceService");
		List<CmsIpDistribution> cidList = this.findByType(siteId, did);
		for (CmsIpDistribution ipDistribution : cidList) {
			ftpServiceService.removeSiteDistributionFromCache(siteId, dtype, ipDistribution.getIpid());
			this.remove(ipDistribution);
		}
	}
	
	/**
	 * 更新ftp服务器跟分发资源类型的对应关系的缓存
	 * @param siteId 站点id
	 * @param ftpService CoreSiteDistribution对象
	 */
	public void saveByFtpService(Long siteId, CoreSiteDistribution ftpService) {//保存时判断id null
		FtpServiceService ftpServiceService = (FtpServiceService) ServiceLocator.getBean("ftpServiceService");
		SiteDistributionService siteDistributionService = (SiteDistributionService) ServiceLocator.getBean("siteDistributionService");
		List<CmsIpDistribution> cidList = this.findByFtp(siteId, ftpService.getId());
		for (CmsIpDistribution ipDistribution : cidList) {
			CmsDistribution d = siteDistributionService.get(ipDistribution.getDid());
			ftpServiceService.putSiteDistributionInCache(siteId, d.getDtype(), ftpService);
		}
	}
}
