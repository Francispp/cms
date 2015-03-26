package com.cyberway.cms.component.wsr.view;

import java.util.HashMap;
import java.util.Map;

import com.cyberway.cms.component.wsr.domain.Channelfee;
import com.cyberway.cms.component.wsr.service.ChannelfeeService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;

public class ChannelfeeController extends BaseBizController<Channelfee> { 
	
	
	private Map<Long,String> yesno = new HashMap<Long,String>();
	

	ChannelfeeService service = (ChannelfeeService)this.getServiceById("ChannelfeeService");
	
	@Override
	protected EntityDao<Channelfee> getService() {
		return  service;
	}
	 

	public Map<Long, String> getYesno() {
		yesno.put(0l,"否");
		yesno.put(1l,"是");
		return yesno;
	} 
}
