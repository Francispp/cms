package com.cyberway.cms.form.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.cms.form.domain.CoreForm;
import com.cyberway.cms.form.service.CoreFormService;

public class CoreFormController extends BaseBizController<CoreForm> {
	CoreFormService service=(CoreFormService)this.getServiceById("coreFormService");
	
	private Map<Integer, String> formTypes = null;
	
	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		return service;
	}
	
	private List pojos;
	/* 列表
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	public String execute()throws Exception{
		CriteriaSetup criteria = new CriteriaSetup();

		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		
		return LIST_RESULT;
	}
	
	
	public String saveOrUpdate() throws Exception{
		super.saveOrUpdate();
		this.addActionMessage("保存成功！");
		return EDIT_RESULT;
	}

	
	public String view()throws Exception{
		if(id!=null){
			get();
		}else{
			domain=getDomainClass().newInstance();
		}			
		return "view";
	}
	
	public List getPojos() {
		if(pojos==null)
			pojos=service.getAllClassMetadata();
		return pojos;
	}
	public void setPojos(List pojos) {
		this.pojos = pojos;
	}	
	
	public Map<Integer, String> getFormTypes() {
		if (formTypes != null) {
			return formTypes;
		} else {
			formTypes = new HashMap<Integer, String>();
			formTypes.put(new Integer(0), "基本表单");
			formTypes.put(new Integer(1), "动态表单");
			return formTypes;
		}
	}
}
