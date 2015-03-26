package com.cyberway.common.email.view;

import java.util.HashMap;
import java.util.Map;

import com.cyberway.common.email.domain.CoreEmailLog;
import com.cyberway.common.email.service.EmailLogManagerService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;

public class EmailLogController extends BaseBizController<CoreEmailLog>{
	EmailLogManagerService service = (EmailLogManagerService)this.getServiceById("EmailLogManagerService");
	
	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		return service;
	}
	
	private Map<Long,String> yesno = new HashMap<Long,String>();
	public Map<Long, String> getYesno() {
		yesno.put(0l,"否");
		yesno.put(1l,"是");
		return yesno;
	} 
}
