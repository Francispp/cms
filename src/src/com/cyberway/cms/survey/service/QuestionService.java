package com.cyberway.cms.survey.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.cms.survey.domain.*;

public class QuestionService extends HibernateEntityDao<Question> {

	@Override
	public Question saveOrUpdate(Question obj) {
		obj.setCreateDate(new Timestamp(System.currentTimeMillis()));
		return super.saveOrUpdate(obj);
	}
	
	
	public Question getQuestionWithOpts(final Long id){
		Question res = (Question) super.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session sess) throws HibernateException, SQLException {
				String hql = "from Question q left outer join fetch q.selectOptions where q.id = ?";
				Question res = (Question) sess.createQuery(hql)
														.setParameter(0, id)
														.uniqueResult();
				return res;
			}
		});
		if(res!=null)
			return res;
		else
			return new Question();
	}
	
	
}
