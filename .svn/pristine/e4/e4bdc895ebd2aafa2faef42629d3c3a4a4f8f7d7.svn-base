package com.cyberway.cms.log.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cyberway.cms.Constants;
import com.cyberway.cms.domain.Log;
import com.cyberway.cms.internal.template.token.ListWriter.Limit;
import com.cyberway.common.attachment.domain.Attachment;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;

public class LogManagerService extends HibernateEntityDao<Log> {
	public List<Log> findBy(final Long target, final String targetType) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Log.class);
				criteria.add(Restrictions.eq("target", target));
				criteria.add(Restrictions.eq("targetType", targetType));

				return criteria.list();
			}
		});
	}
	public Page listById(int pageIndex, int pageSize, Long target, String targetType)
			throws Exception {

		CriteriaSetup criteriaSetup = new CriteriaSetup();
		Criteria criteria = this.getEntityCriteria();
		List<org.hibernate.criterion.Order> inOrders = new ArrayList<org.hibernate.criterion.Order>();
		inOrders.add(org.hibernate.criterion.Order.desc("time"));
		criteriaSetup.setInOrders(inOrders);
		criteria.add(Restrictions.eq("target", target));
		criteria.add(Restrictions.eq("targetType", targetType));
		criteriaSetup.setup(criteria);
		com.cyberway.common.message.utils.Limit limit = new com.cyberway.common.message.utils.Limit(
				pageIndex, pageSize);

		return pagedQuery(criteria, limit.getPage(),
				limit.getCurrentRowsDisplayed());

	}

	/**
	 * 根据docid清除log
	 * 
	 * @param docid
	 */
	public void deleteByDocument(Long docid) {
		if (docid != null && docid != 0) {
			List<Log> logs = this.findBy(docid,
					Constants.LOG_TARGET_TYPE_DOCUMENT);
			if (logs != null && logs.size() > 0) {
				for (Log log : logs)
					this.remove(log);
			}
		}
	}
}
