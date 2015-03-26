package com.cyberway.cms.form.view;

import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.cms.form.domain.CoreFormField;
import com.cyberway.cms.form.service.CoreFormFieldService;

public class CoreFormFieldController extends BaseBizController<CoreFormField> {
	CoreFormFieldService service=(CoreFormFieldService)this.getServiceById("coreFormFieldService");
	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		if(service==null)
			 service=(CoreFormFieldService)this.getServiceById("coreFormFieldService");
		return service;
	}
	private Long formid;
	
	/* 列表
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	public String execute()throws Exception{
		CriteriaSetup criteria = new CriteriaSetup();
        if(formid!=null){
        	criteria.addFilter("coreForm.oid",formid);
        	doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
        }
		
		
		return LIST_RESULT;
	}
	
	public String saveOrUpdate() throws Exception{
		super.saveOrUpdate();
		this.addActionMessage("保存成功！");
		return EDIT_RESULT;
	}
	

	public Long getFormid() {
		return formid;
	}

	public void setFormid(Long formid) {
		this.formid = formid;
	}	
}
