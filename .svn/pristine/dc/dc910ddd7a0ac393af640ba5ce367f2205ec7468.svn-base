package com.cyberway.common.resource.view;

import java.util.HashMap;
import java.util.Map;

import com.cyberway.common.domain.CoreResource;
import com.cyberway.common.resource.service.ResourceManagerService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.web.BaseBizController;

public class ResourceController extends BaseBizController<CoreResource> {
	ResourceManagerService service =(ResourceManagerService)this.getServiceById("resourceManagerService");
	//WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued" list="#{true:'是',false:'否'}" />
  	//改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
	private Map<String,String> trueOfFalseMap1=null;
	private Long moid;
	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		return service;
	}
	/*
	 * 列表
	 * 
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	public String execute() throws Exception{

		CriteriaSetup criteria = new CriteriaSetup();
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}
	/* (non-Javadoc)
	 * @see com.cyberway.core.web.BaseBizController#edit()
	 */
	public String edit()throws Exception{
		String rt=super.edit();
		return rt;
	}
	
	
	public String saveOrUpdate()throws Exception{
		super.saveOrUpdate();
		this.addActionMessage("保存成功！");
		return EDIT_RESULT;
	}
	public Long getMoid() {
		return moid;
	}
	public void setMoid(Long moid) {
		this.moid = moid;
	}

public Map<String, String> getTrueOfFalseMap1() {
		if(trueOfFalseMap1 != null){
			return trueOfFalseMap1;
		}else{
			trueOfFalseMap1 = new HashMap<String, String>();
			trueOfFalseMap1.put("URL", "URL");
			trueOfFalseMap1.put("METHOD", "METHOD");
			return trueOfFalseMap1;
		}
	}
	
}
