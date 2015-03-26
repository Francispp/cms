package com.cyberway.cms.survey.view;

import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.hibernate.criterion.Order;

import com.cyberway.cms.component.webuser.domain.WebUser;
import com.cyberway.cms.survey.domain.Question;
import com.cyberway.cms.survey.domain.Questionnaire;
import com.cyberway.cms.survey.domain.SelectOption;
import com.cyberway.cms.survey.service.QuestionReplyService;
import com.cyberway.cms.survey.service.QuestionService;
import com.cyberway.cms.survey.service.QuestionnaireAnswerService;
import com.cyberway.cms.survey.service.QuestionnaireService;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.web.BaseBizController;

public class QuestionnaireController extends BaseBizController<Questionnaire> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QuestionnaireService questionnaireService = (QuestionnaireService) getServiceById("QuestionnaireService");
	private QuestionService questionservice = (QuestionService) getServiceById("QuestionService");
	private QuestionReplyService replyService = (QuestionReplyService)getServiceById("questionReplyService");
	QuestionnaireAnswerService answerService = (QuestionnaireAnswerService)getServiceById("QuestionnaireAnswerService");
	
	private String rs = "";//是否显示结果，为yes 时显示，其它均不显示
	private String tourl;
	
	@Override
	public String delete() throws Exception {
		super.delete();
		return list();
	}
	
	@Override
	public String add() throws Exception {
		domain=getDomainClass().newInstance();
		return "add";
	}
	
	//保存
	public String saveOrUpdate() throws Exception{
		domain=getService().saveOrUpdate(domain);
		this.addActionMessage("保存成功");
		return "add";
	}

	/**
	 * 编辑问卷
	 */
	@Override
	public String edit() throws Exception {
		if(id!=null){
			domain = getService().getFullQuestionnaireById(id);
		}else{
			domain=getDomainClass().newInstance();
		}
		return EDIT_RESULT;
	}
	
	/**
	 * 设置问卷参数
	 * @return
	 * @throws Exception
	 */
	public String editArgs() throws Exception {
		if(id!=null){
			domain = getService().get(id);
		}else{
			domain=getDomainClass().newInstance();
		}
		return "editArgs";
	}
	/**
	 * 设置问卷参数
	 * @return
	 * @throws Exception
	 */
	public void saveArgs() throws Exception {
		saveOrUpdate();
	}
	
	/**
	 * 设置问卷参数
	 * @return
	 * @throws Exception
	 */
	public void publishing() throws Exception {
		String act = domain.getActivated();
		domain = getService().get(id);
		domain.setActivated(act);
		saveOrUpdate();
	}
	
	
	/**
	 * 复制问卷
	 * @return
	 * @throws Exception
	 */
	public String copy() throws Exception {
		preview();
		domain.setId(null);
		domain.setName("copy - " + domain.getName());
		saveOrUpdate();
		
		for(Question q : domain.getQuestions()){
			q.setId(null);
			q.setQuestionnaireId(domain.getId());
			ArrayList<SelectOption> optSet = new ArrayList<SelectOption>(0);
			for(SelectOption opt : q.getSelectOptions()){
				SelectOption newopt = new SelectOption();
				BeanUtils.copyProperties(newopt, opt);
				newopt.setId(null);
				optSet.add(newopt);
			}
			q.setSelectOptions(optSet);
			questionservice.save(q);
		}
		return list();
	}
	
	
	/**
	 * 预览
	 * @return
	 * @throws Exception
	 */
	public String preview() throws Exception {
		if(id!=null){
			domain = getService().getFullQuestionnaireById(id);
		}else{
			domain=getDomainClass().newInstance();
		}
		if(GenericValidator.isBlankOrNull(domain.getStyleFile()))
			return "defaultView";
		return "preview";
	}
	/**
	 * 结果
	 * @return
	 * @throws Exception
	 */
	public String survey() throws Exception {
		preview();
		domain.setTotal(answerService.getTotalNum(id));//总数
		if(StringUtils.isNotBlank(tourl)){
			return "tourl";
		}
		return "preview";
	}
	
	public String doLastQuestionnaire() throws Exception {
		WebUser user = getWebUser();
		if (user != null) {
			domain = questionnaireService.getLastQuestionnaire(domain.getSiteId());
			if(domain==null){
				return outWrite(NONE);//尚未有问卷调查
			}else{
				long checkDate = domain.getRepetition()==null?0:domain.getRepetition()*60*1000;
				String answerer = "S-U-0";//系统默认用户
				if(user!=null){
					if(user.getIsInternal()==null || !user.getIsInternal()){
						answerer = "E-U-"+user.getOid();//外部用户
					}else{
						answerer = "I-S-"+user.getOid();//内部员工
					}
				}else{
					Loginer loginer = (Loginer)getSession().get(Loginer.LOGININFO_SESSION);
					if(loginer!=null){
						answerer = "S-U-"+loginer.getUserid();//系统用户
					}
				}
				if(replyService.checkQuestion(this.getHttpServletRequest().getRemoteAddr(), answerer, domain.getId(), checkDate)){
					domain.setTotal(answerService.getTotalNum(domain.getId()));//总数
				}else{
					domain = getService().getFullQuestionnaireById(domain.getId());
					domain.setTotal(answerService.getTotalNum(domain.getId()));//总数
					return "result";
				}
			}
		}else{
			return outWrite("Not-logged-in");
		}
		return "preview";
	}
	
	public String doQuestionnaire() throws Exception {
		WebUser user = getWebUser();
		if (user != null) {
			domain = getService().getFullQuestionnaireById(id);
			if(domain==null){
				return outWrite(NONE);//尚未有问卷调查
			}else{
				long checkDate = domain.getRepetition()==null?0:domain.getRepetition()*60*1000;
				String answerer = "S-U-0";//系统默认用户
				if(user!=null){
					if(user.getIsInternal()==null || !user.getIsInternal()){
						answerer = "E-U-"+user.getOid();//外部用户
					}else{
						answerer = "I-S-"+user.getOid();//内部员工
					}
				}else{
					Loginer loginer = (Loginer)getSession().get(Loginer.LOGININFO_SESSION);
					if(loginer!=null){
						answerer = "S-U-"+loginer.getUserid();//系统用户
					}
				}
				if(replyService.checkQuestion(this.getHttpServletRequest().getRemoteAddr(), answerer, domain.getId(), checkDate)){
					domain.setTotal(answerService.getTotalNum(domain.getId()));//总数
				}else{
					domain = getService().getFullQuestionnaireById(domain.getId());
					domain.setTotal(answerService.getTotalNum(domain.getId()));//总数
					return "result";
				}
			}
		}else{
			return outWrite("Not-logged-in");
		}
		return "preview";
	}
	
	/**
	 * 统计结果
	 * @return
	 * @throws Exception
	 */
	public String result() throws Exception {
		preview();
		domain.setTotal(answerService.getTotalNum(id));//总数
		return "result";
	}
	
	/**
	 * 感谢页
	 * @return
	 * @throws Exception
	 */
	public String thanks() throws Exception {
		domain = this.getService().get(id);
		domain.setTotal(answerService.getTotalNum(id));//总数
		return "thanks";
	}
	
	public QuestionService getQuestionservice() {
		return questionservice;
	}

	public void setQuestionservice(QuestionService questionservice) {
		this.questionservice = questionservice;
	}

	@Override
	protected QuestionnaireService getService() {
		return questionnaireService;
	}
	
	@Override
	public String list() throws Exception {
		CriteriaSetup cs = new CriteriaSetup();
		cs.setInOrder(Order.desc("id"));
		doListEntity(cs, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}
	
	public String getRs() {
		return rs;
	}

	public void setRs(String rs) {
		this.rs = rs;
	}

	public String getTourl() {
		return tourl;
	}

	public void setTourl(String tourl) {
		this.tourl = tourl;
	}

}
