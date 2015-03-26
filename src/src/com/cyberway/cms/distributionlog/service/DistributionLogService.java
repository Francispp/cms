package com.cyberway.cms.distributionlog.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.Constants;
import com.cyberway.cms.domain.CmsDistributionLog;
import com.cyberway.common.distribution.service.DistributionService;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class DistributionLogService extends HibernateEntityDao<CmsDistributionLog> {
	public List<CmsDistributionLog> findByType(Long siteId, String dtype) {
		Criteria c = getEntityCriteria();
		c.add(Restrictions.eq("siteId", siteId));
		c.add(Restrictions.eq("type", dtype));
		List<CmsDistributionLog> l = (List<CmsDistributionLog>) c.list();
		if (l.isEmpty()) {
			return null;
		} else {
			return l;
		}
	}

	/**
	 * 重新分发
	 * 
	 * @param ids
	 *            多个分发记录的id组成的字符串,各个id之间用","分开
	 * @param siteId
	 *            站点id
	 * @return
	 */
	public boolean rdistribution(String ids, String siteId) {
		DistributionService distributionService = (DistributionService) ServiceLocator.getBean("distributionService");
		if (!StringUtil.isEmpty(ids)) {
			List<Long> list = StringUtil.splitToList(ids, ",");
			for (int i = 0; i < list.size(); i++) {
				CmsDistributionLog c = this.get(list.get(i));
				try {
					if (StringUtils.isBlank(c.getPath())) {
						System.out.println("=======================文件" + c.getName() + "在log中的本地路径为空,所有不能重新分发!=========================");
					} else {
						if (Constants.CMS_FTP_ENABLED.equals("true")) {
							distributionService.uploadByFtp(c.getId(), c.getFtpId(), Long.valueOf(siteId), c.getType(), c.getFtpPath(),
							        c.getPath(), true);
						} else {
							System.out.println("=========================FTP操作的开关被关掉了,不可以执行FTP相关的操作!=========================");
						}
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
}
