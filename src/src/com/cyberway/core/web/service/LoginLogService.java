package com.cyberway.core.web.service;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.web.domain.LoginLog;

public class LoginLogService extends HibernateEntityDao<LoginLog>{

	@Override
	public void save(Object dto) {
		LoginLog log = new LoginLog();
		log.setLoginSuccess(true);
		BeanUtils.copyProperties(dto, log);
		if(log.getLoginTime()!=null){
			log.setLogoutTime(new Date()); //退出时间
			super.save(log);
		}
	}

	
	
}
