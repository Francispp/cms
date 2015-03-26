package com.cyberway.cms.survey.view;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.GenericValidator;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.survey.domain.Question;
import com.cyberway.cms.survey.domain.SelectOption;
import com.cyberway.cms.survey.service.QuestionService;
import com.cyberway.cms.survey.service.SelectOptionService;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class QuestionController extends BaseBizController<Question> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5041004127303688764L;
	
	private QuestionService questionService = (QuestionService)super.getServiceById("QuestionService");
	//private QuestionnaireService questionnaireService = (QuestionnaireService)super.getServiceById("QuestionnaireService");
	private SelectOptionService selectOptionService = (SelectOptionService)super.getServiceById("SelectOptionService");
	
	private Long questionnaireId;
	private String selectOptions;

	public QuestionService getQuestionService() {
		return questionService;
	}

	@Override
	protected QuestionService getService() {
		return questionService;
	}
	
	public boolean acceptableParameterName(String arg0) {
		return true;
	}

	@Override
	public String edit() throws Exception {
		if(this.questionnaireId == null){
			logger.info("问卷ID为null。");
		}
		if(id!=null){
			domain = getService().getQuestionWithOpts(id);
		}else{
			domain=getDomainClass().newInstance();
			domain.setQuestionnaireId(questionnaireId);
		}			
		return EDIT_RESULT;
	}
	
	@Override
	public String saveOrUpdate() throws Exception {
		if(selectOptions != null){
			List<Long> ids = selectOptionService.getOptionIdsByQuestionId(domain.getId());
			int oldSize = ids.size();
			String[] options = selectOptions.split("\n");
			for(int i=0;i<options.length;i++){
				String opt = options[i].trim();
				Long id = (oldSize-1) >= i ? ids.get(i) : null;
				if(!GenericValidator.isBlankOrNull(opt)){
					SelectOption option = new SelectOption(id, opt,i);
					domain.getSelectOptions().add(option);
				}
			}
			if(domain.getType().equals("0")||domain.getType().equals("5")){
				//防止原来已经有多个答案变成单个答案出现都问题
				domain.getSelectOptions().clear();
				SelectOption option = new SelectOption("",0);
				domain.getSelectOptions().add(option);
			}else if(domain.getType().equals("3")||domain.getType().equals("4")){
				Long id = oldSize >= options.length ? ids.get(options.length-1) : null;
				SelectOption option = new SelectOption(id,"",options.length);
				domain.getSelectOptions().add(option);
			}
			int newSize = domain.getSelectOptions().size();
			if(oldSize>newSize){
				for (int i = newSize; i < oldSize; i++) {
					selectOptionService.removeById(ids.get(i));
				}
			}
		}
		return super.saveOrUpdate();
	}
	
	public String addQuestion() throws Exception{
		saveOrUpdate();
		this.addActionMessage("操作成功!");
		return "input";
	}
	//复制问题
	public String copy() throws Exception{
		edit();
		domain.setId(null);
		domain.setContent("复制问题－" + domain.getContent());
		return EDIT_RESULT;
	}

	
	public String listby() throws Exception{
		if(this.questionnaireId == null){
//			addActionError("");
			logger.info("问卷ID为null。");
		}else{
			domain=getDomainClass().newInstance();
			domain.setQuestionnaireId(questionnaireId);
		}
		CriteriaSetup criteria = new CriteriaSetup();
		List<Criterion> cList = new ArrayList<Criterion>();
		cList.add(Restrictions.isNull("questionnaireId"));
		criteria.setAddCriterions(cList);
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
	    
		return "listby";
	}
	
	public String delete() throws Exception{
		super.delete();
		return list();
	}
	
	
	
	
	public void editmore() throws Exception{
		List list=new ArrayList();
		if(!StringUtil.isEmpty(keys)){
			list=StringUtil.splitToList(keys,",");
		}
		for(int i=0;i<list.size();i++){
			domain = getService().getQuestionWithOpts(Long.parseLong(list.get(i).toString()));
			domain.setQuestionnaireId(questionnaireId);
		}	
		super.saveOrUpdate();
		//this.addActionError("操作成功");
	}
	

	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	public Long getQuestionnaireId() {
		return questionnaireId;
	}
	public void setQuestionnaireId(Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}


	public String getSelectOptions() {
		return selectOptions;
	}


	public void setSelectOptions(String selectOptions) {
		this.selectOptions = selectOptions;
	}



}
