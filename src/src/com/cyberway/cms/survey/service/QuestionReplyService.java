package com.cyberway.cms.survey.service;

import java.util.List;

import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.cms.survey.domain.*;

public class QuestionReplyService extends HibernateEntityDao<QuestionReply> {
	/**
	 * 根据参数设置防止重复提交调查卷
	 * @param bip
	 * @param answerer
	 * @param questionId
	 * @param checkDate 此项为开始设置多久可以重复提交/ 0 或 null 都不能重复投
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean checkQuestion(String bip, String answerer, long questionId,long checkDate){
		boolean blag=false;
		List<QuestionReply> list = this.find("from QuestionReply q where q.bip=? and q.questionId=? and q.answerer=? order by q.oid desc", new Object[]{bip,questionId,answerer});
		if(list.size()==0){
			blag=true;
		}else{
			if(checkDate==0)return blag;
			Long ctm=System.currentTimeMillis();
			QuestionReply reply = list.get(0);
			if(ctm-reply.getBdate()>checkDate){
				blag=true;
			}
		}
		return blag;
	}
}
