package com.cyberway.common.message.service;

import java.util.Date;
import java.util.List;

import org.ecside.table.limit.Limit;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessResourceFailureException;

import com.cyberway.common.domain.CoreUser;
import com.cyberway.common.message.domain.Message;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;

public class MessageManagerService extends HibernateEntityDao<Message>{
	
	public Page messagePage(Limit limit,CriteriaSetup criteriaSetup) throws Exception {
		Criteria criteria = this.getEntityCriteria();
		if(criteriaSetup==null)
			criteriaSetup = new CriteriaSetup();		
		criteriaSetup.setup(criteria);
		return pagedQuery(criteria, limit.getPage(), limit.getCurrentRowsDisplayed());
	}
	
	public List<Message> getMessage(Long docId) throws Exception {
		Criteria criteria = getSession().createCriteria(Message.class);
		criteria.add(Restrictions.eq("docid", docId));
		return criteria.list();
	}
	
	public String setMessage(String title, CoreUser coreUser, String face,
			String content, String docId) {
		try {
			Message m = new Message();
			m.setContent(content);
			m.setCreatetime(new Date());
			m.setDocid(Long.valueOf(docId));
			m.setFace(face);
			m.setTitle(title);
			m.setUser(coreUser);
			getSession().saveOrUpdate(m);
		} catch (Exception e) {
			return e.getMessage();
		}
		return "";
	}
	
	public int getTotalScore(Long docId) throws Exception {
		List<Message> mList = getMessage(docId);
		int total = 0;
		for (Message message : mList) {
			total += message.getScore();
		}
		return total;
	}
}
