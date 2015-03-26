package com.cyberway.cms.survey.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cyberway.cms.survey.domain.Questionnaire;
import com.cyberway.core.dao.HibernateEntityDao;

public class QuestionnaireService extends HibernateEntityDao<Questionnaire> {

	/**
	 * 加载问卷 
	 * @param id 问卷id
	 * @return 
	 */
	public Questionnaire getQuestionnaireById(final Long questionnaireId) {
		Questionnaire res = (Questionnaire)super.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session sess) throws HibernateException, SQLException {
				String hql = "from Questionnaire que left outer join fetch que.questions where que.id = ?";
				Questionnaire res = (Questionnaire) sess.createQuery(hql)
														.setParameter(0, questionnaireId)
														.uniqueResult();
				return res;
			}
		});
		
		if(res!=null)
			return res;
		else
			return new Questionnaire();
	}
	
	/**
	 * 问卷
	 * @param id 问卷id
	 * @return
	 */
	public Questionnaire getFullQuestionnaireById(final Long questionnaireId){
		Questionnaire res = (Questionnaire)getSession().get(this.getEntityClass(), questionnaireId);
		res.getQuestions().size();
		if(res!=null)
			return res;
		else
			return new Questionnaire();
	}

	/**
	 * 查找最近发布且有效的问卷
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Questionnaire getLastQuestionnaire(Long siteId){
		List<Questionnaire> list = getHibernateTemplate().find("from Questionnaire where siteId=? and (cutoffDate = null or (cutoffDate>=? and (publishDate<=? or publishDate = null))) and activated=? order by id desc", new Object[]{siteId, new Date(), new Date(), "1"});
		if(list.size()>0){
			Questionnaire a = list.get(0);
			a.getQuestions().size();
			return a;
		}
		return null;
	}
}
