package com.cyberway.cms.survey.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.survey.domain.QuestionReply;
import com.cyberway.cms.survey.domain.QuestionnaireAnswer;
import com.cyberway.core.dao.HibernateEntityDao;

public class QuestionnaireAnswerService extends HibernateEntityDao<QuestionnaireAnswer> {

	/**
	 * 答卷总数
	 * @param questionnaireId 问卷id
	 * @return
	 */
	public Integer getTotalNum(Long questionnaireId){
		Session session = getSession();
		Integer rs = (Integer)session.createCriteria(getEntityClass())
		    .setProjection( Projections.rowCount() )
		    .add( Restrictions.eq("questionnaireId", questionnaireId) ).uniqueResult();
		
		return rs;
	}
	
}
