package com.cyberway.cms.form.view;

import java.util.List;

import com.cyberway.cms.form.domain.CoreForm;
import com.cyberway.cms.form.domain.CoreFormField;
import com.cyberway.cms.form.service.CoreFormFieldService;
import com.cyberway.cms.form.service.CoreFormService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class FieldSelectController extends BaseBizController<CoreFormField> {
	CoreFormFieldService service = (CoreFormFieldService) getServiceById("coreFormFieldService");
	CoreFormService formService=(CoreFormService)this.getServiceById("coreFormService");



	private String multiSelect;
	private String selectChild;
	private List fields;
	private String  selectedids;
	private Long formid;
	private String formName;
	private CoreForm coreForm;
	private String ReadWriteFields;
	private String NotNullFields;
	private String OnlyReadFields;
	private String HiddenFields;
	private String ThrowBackEmptyFields;
	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		return service;
	}
	
	public String execute() throws Exception {
		//若formId不为空，则选择指定Form的字段
		if(formid!=null)
			coreForm=formService.get(formid);
		else {
		 if(!StringUtil.isEmpty(formName)){//若表单名称不为空，指定表单模板名称
		  coreForm=formService.getCoreFormByTemplateName(formName);
		 }

		}
		if(coreForm!=null)
			 fields=coreForm.getFormFields();
		
		return "select";
	}
	/**
	 * 选择字段
	 * @return
	 * @throws Exception
	 */
	public String selectList()throws Exception{
		CriteriaSetup criteria = new CriteriaSetup();
        if(formid!=null){
        	criteria.addFilter("coreForm.oid",formid);
        	doListEntity(criteria, getTableId(), 10000);
        }
		
			
		return "select_list";
	}
	/**
	 * 设置流程权限字段
	 * @return
	 * @throws Exception
	 */
	public String setFlowFields()throws Exception{
		CriteriaSetup criteria = new CriteriaSetup();
        if(formid!=null){
        	criteria.addFilter("coreForm.oid",formid);
        	doListEntity(criteria, getTableId(), 10000);
        }
		
		return "set_flow_select_list";
	}	

	public String getMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(String multiSelect) {
		this.multiSelect = multiSelect;
	}

	public String getSelectChild() {
		return selectChild;
	}

	public void setSelectChild(String selectChild) {
		this.selectChild = selectChild;
	}


	public String getSelectedids() {
		return selectedids;
	}

	public void setSelectedids(String selectedids) {
		this.selectedids = selectedids;
	}

	public List getFields() {
		return fields;
	}

	public void setFields(List fields) {
		this.fields = fields;
	}

	public Long getFormid() {
		return formid;
	}

	public void setFormid(Long formId) {
		this.formid = formId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public CoreForm getCoreForm() {
		return coreForm;
	}

	public void setCoreForm(CoreForm coreForm) {
		this.coreForm = coreForm;
	}

	public String getHiddenFields() {
		return HiddenFields;
	}

	public void setHiddenFields(String hiddenFields) {
		HiddenFields = hiddenFields;
	}

	public String getNotNullFields() {
		return NotNullFields;
	}

	public void setNotNullFields(String notNullFields) {
		NotNullFields = notNullFields;
	}

	public String getOnlyReadFields() {
		return OnlyReadFields;
	}

	public void setOnlyReadFields(String onlyReadFields) {
		OnlyReadFields = onlyReadFields;
	}

	public String getReadWriteFields() {
		return ReadWriteFields;
	}

	public void setReadWriteFields(String readWriteFields) {
		ReadWriteFields = readWriteFields;
	}

	public String getThrowBackEmptyFields() {
		return ThrowBackEmptyFields;
	}

	public void setThrowBackEmptyFields(String throwBackEmptyFields) {
		ThrowBackEmptyFields = throwBackEmptyFields;
	}

}
