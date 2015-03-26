package com.cyberway.weixin.business.field.service;

import java.util.Date;

import org.apache.tools.ant.taskdefs.Get;

import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.weixin.business.field.domain.Field;

public class FieldService extends HibernateEntityDao<Field> {
	/**
	 * 签到
	 */
	public void signIn(Long id,String signInPlace,Date signInTime,String SignInTxt,String signInPic) {
		String sql = "update wx_field set signInPlace=" + signInPlace + ",signInTime= '" 
				+ signInTime+"',SignInTxt=" + SignInTxt + ",signInPic=" + signInPic + "where id= "+id;
		this.getSession().createQuery(sql).executeUpdate();
	}
	
	/**
	 * 签退
	 */
	public void signOut(Long id,String signOutPlace,Date signOutTime,String SignOutTxt,String signOutPic) {
		String sql = "update wx_field set signOutPlace=" + signOutPlace + ",signOutTime= '" 
				+ signOutTime+"',SignOutTxt=" + SignOutTxt + ",signInPic=" + signOutPic + "where id= "+id;
		this.getSession().createQuery(sql).executeUpdate();
	}

}
