package com.cyberway.weixin.business.attendance.controller;

import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.weixin.business.attendance.domain.FieldRecord;
import com.cyberway.weixin.business.attendance.service.FieldRecordService;
import com.opensymphony.xwork2.Preparable;

public class FieldRecordController extends BaseBizController<FieldRecord>implements Preparable{
	
	FieldRecordService service = (FieldRecordService)this.getServiceById("fieldrecordservice");

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected EntityDao<FieldRecord> getService() {
		// TODO Auto-generated method stub
		return service;
	}

}
