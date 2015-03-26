package com.cyberway.cms.survey.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;

import com.cyberway.cms.component.webuser.domain.WebUser;
import com.cyberway.cms.survey.domain.QuestionAnswer;
import com.cyberway.cms.survey.domain.QuestionReply;
import com.cyberway.cms.survey.domain.Questionnaire;
import com.cyberway.cms.survey.domain.QuestionnaireAnswer;
import com.cyberway.cms.survey.service.QuestionReplyService;
import com.cyberway.cms.survey.service.QuestionnaireAnswerService;
import com.cyberway.cms.survey.service.QuestionnaireService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.web.BaseBizController;

public class QuestionnaireAnswerController extends BaseBizController<QuestionnaireAnswer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7572378213988699503L;
	
	private QuestionnaireAnswerService answerService = (QuestionnaireAnswerService)getServiceById("QuestionnaireAnswerService");
	private QuestionReplyService replyService = (QuestionReplyService)getServiceById("questionReplyService");
	private QuestionnaireService questionnaireService = (QuestionnaireService) getServiceById("QuestionnaireService");
	
	private List<String[]> questionIds = new ArrayList<String[]>();
	private List<String[]> optAnswers = new ArrayList<String[]>();
	private List<String[]> txtAnswers = new ArrayList<String[]>();
	
	//跳转
	private String tourl = "";
	
	@Override
	protected EntityDao<QuestionnaireAnswer> getService() {
		return answerService;
	}

	@Override
	public String saveOrUpdate() throws Exception {
		WebUser user = getWebUser();
		String answerer = "S-U-0";
		if(user!=null){
			domain.setUserId(user.getOid());
			if(user.getIsInternal()==null || !user.getIsInternal()){
				answerer = "E-U-"+user.getOid();
			}else{
				answerer = "I-S-"+user.getOid();
			}
		}else{
			Loginer loginer = (Loginer)getSession().get(Loginer.LOGININFO_SESSION);
			if(loginer!=null){
				domain.setUserId(loginer.getUserid());
				answerer = "S-U-"+loginer.getUserid();
			}
		}
		Questionnaire qnaire = questionnaireService.get(domain.getQuestionnaireId());
		if("0".equals(qnaire.getAnonymity()) && domain.getUserId()==null){
			super.addActionError("对不起，本次调查需要登录，不可匿名！");
			if(this.isAjax){
				return outWrite("Not-logged-in");
			}
			return "error";
		}
		Date today = new Date();
		if(qnaire.getPublishDate()!= null && qnaire.getPublishDate().after(today)){
			super.addActionError("对不起，投票失败（该问卷调查活动未开始）");
			if(this.isAjax){
				return outWrite("Not_Started");
			}
			return "error";
		} else if(qnaire.getCutoffDate()!=null && qnaire.getCutoffDate().before(today)){
			super.addActionError("对不起，投票失败（该问卷调查活动已结束）");
			if(this.isAjax){
				return outWrite("Closed");
			}
			return "error";
		}
		if(!replyService.checkQuestion(this.getHttpServletRequest().getRemoteAddr(), answerer, qnaire.getId(),qnaire.getRepetition()==null?0:qnaire.getRepetition()*60*1000)){
			super.addActionError("对不起，您不能重复投票");
			if(this.isAjax){
				return outWrite("Repeat_Vote");
			}
			return "error";
		} else {
			QuestionReply reply=new QuestionReply();
			reply.setBdate(System.currentTimeMillis());
			reply.setBip(this.getHttpServletRequest().getRemoteAddr());
			reply.setQuestionId(domain.getQuestionnaireId());
			reply.setAnswerer(answerer);
			replyService.saveOrUpdate(reply);
			//选项
			for(int i=0;i<optAnswers.size();i++){
				String [] optids = optAnswers.get(i);
				if(optids!=null){
					//0:'短答',1:'单选',2:'多选',3:'单选短答',4:'多选短答',5:'自由长答'
					for(String optId : optids){
						if(GenericValidator.isBlankOrNull(optId)) break;
						QuestionAnswer qa = new QuestionAnswer();
						qa.setOptId(new Long(optId));
						qa.setQuestionId(new Long(questionIds.get(i)[0]));
						String txtAns = getHttpServletRequest().getParameter("txtAnswers_"+optId);
						qa.setTxtAns(txtAns);
						domain.getQuestionAns().add(qa);
					}
				}
			}

			domain.setSubmitTime(new Date());
			super.saveOrUpdate();
		}
		if(StringUtils.isBlank(tourl) && qnaire.getTurnToPage()!=null){
			tourl = qnaire.getTurnToPage().toString();
		}
		if("1".equals(tourl))
			return "result";
		else if("3".equals(tourl))
			return "thanks";
		else if("2".equals(tourl)){
			if(this.isAjax){
				return outWrite("Close_Window");
			}
			return "close";
		}else {
			this.tourl = "/survey/questionnaire!survey.action?id="+domain.getQuestionnaireId()+"&amp;tourl="+this.tourl;
			return "tourl";
		}
	}

	public QuestionnaireAnswerService getAnswerService() {
		return answerService;
	}

	public void setAnswerService(QuestionnaireAnswerService answerService) {
		this.answerService = answerService;
	}

	public List<String[]> getOptAnswers() {
		return optAnswers;
	}

	public void setOptAnswers(List<String[]> optAnswers) {
		this.optAnswers = optAnswers;
	}

	public List<String[]> getTxtAnswers() {
		return txtAnswers;
	}

	public void setTxtAnswers(List<String[]> txtAnswers) {
		this.txtAnswers = txtAnswers;
	}

	public List<String[]> getQuestionIds() {
		return questionIds;
	}

	public void setQuestionIds(List<String[]> questionIds) {
		this.questionIds = questionIds;
	}

	public String getTourl() {
		return tourl;
	}

	public void setTourl(String tourl) {
		this.tourl = tourl;
	}

}
