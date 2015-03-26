package com.cyberway.crawl.regular.view;

import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.crawl.regular.domain.Regular;
import com.cyberway.crawl.regular.service.RegularManagerService;

public class RegularController extends BaseBizController<Regular> {
	
	RegularManagerService service = (RegularManagerService) this.getServiceById("regularManagerService");
	@Override
	protected EntityDao getService() {
		return service;
	}

}
