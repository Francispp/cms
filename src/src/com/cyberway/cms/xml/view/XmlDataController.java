package com.cyberway.cms.xml.view;

import com.cyberway.cms.xml.domain.XmlData;
import com.cyberway.cms.xml.service.XmlDataManagerService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;

public class XmlDataController extends BaseBizController<XmlData>{
	XmlDataManagerService service = (XmlDataManagerService) this.getServiceById("xmlDataManagerService");

	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		return service;
	}
	

}
