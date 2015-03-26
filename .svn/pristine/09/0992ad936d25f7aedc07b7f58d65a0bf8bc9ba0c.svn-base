package com.cyberway.weixin.sign.controller;

import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.weixin.sign.domain.Sign;
import com.cyberway.weixin.sign.service.SignService;

public class SignController extends BaseBizController<Sign>{

	SignService service = (SignService) this.getServiceById("signService");
	private Long longitude;
	private Long latitude;
	@Override
	protected EntityDao<Sign> getService() {
		// TODO Auto-generated method stub
		return null;
	}


	public String setAddress(){
		return "addressedit";
		
	}
	
	public String openMap(){
		return "baidumap";
	}
	
	public String saveOrUpdate(){
		
		String result = "addressedit";
		getDomain().setLongitude(longitude);
		getDomain().setLatitude(latitude);
		return result;
	}
}
