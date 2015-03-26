package com.cyberway.cms.component.selectlist.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.component.selectlist.domain.ListOption;
import com.cyberway.core.dao.HibernateEntityDao;

public class ListOptionService extends HibernateEntityDao<ListOption> {
	
	/**
	 * 删除列"listTitleId"为null值的行
	 */
	public void deleteByTitleIdIsNull() {
		Criteria c = getEntityCriteria();
		c.add(Restrictions.isNull("listTitleId"));
		List<ListOption> l = c.list();

		for (ListOption opt : l) {
			remove(opt);
		}
	}
}
