package com.cyberway.cms.template.service;

import com.cyberway.cms.domain.PagePublisherLog;
import com.cyberway.core.dao.HibernateEntityDao;

public class PagePublisherLogService extends HibernateEntityDao<PagePublisherLog>{
	
	public void save(PagePublisherLog log){
		super.saveOrUpdate(log);
	}
}
