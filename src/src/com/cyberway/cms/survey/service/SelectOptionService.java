package com.cyberway.cms.survey.service;

import java.util.List;

import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.cms.survey.domain.*;

public class SelectOptionService extends HibernateEntityDao<SelectOption> {

	@SuppressWarnings("unchecked")
	public List<SelectOption> getOptionsByQuestionId(Long question_id){
		return this.find("from SelectOption where question_id=? order by id asc", question_id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> getOptionIdsByQuestionId(Long question_id){
		return this.find("select id from SelectOption where question_id=? order by id asc", question_id);
	}
	
}
