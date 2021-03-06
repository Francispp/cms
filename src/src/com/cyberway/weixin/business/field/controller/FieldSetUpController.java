package com.cyberway.weixin.business.field.controller;

import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.weixin.business.field.domain.FieldSetUp;
import com.cyberway.weixin.business.field.service.FieldSetUpService;

public class FieldSetUpController extends BaseBizController<FieldSetUp>{
	FieldSetUpService service = (FieldSetUpService) this.getServiceById("fieldSetUpService");
	@Override
	protected EntityDao<FieldSetUp> getService() {
		return service;
	}
	
	
	/**
	 * 跳转至设置页面
	 */
	public String fieldSet() {
		return "setUp";
	}
	
	/**
	 * 设置签到，签退是否拍照
	 */
	public void signInSetup() {
		FieldSetUp fs = new FieldSetUp();
		fs.setId(1L);
		fs.setSignInSet(domain.getSignInSet());
		fs.setSignOutSet(domain.getSignOutSet());
		getService().saveOrUpdate(fs);
	}

}
