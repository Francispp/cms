package com.cyberway.weixin.business.attendance.service;

import java.util.List;

import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.weixin.business.attendance.domain.AttendanceManage;
/**
 * 
 * @com.cyberway.weixin.business.attendance.service.AttendanceManageService
 * TODO :
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年3月4日 上午11:29:45
 */
public class AttendanceManageService  extends HibernateEntityDao<AttendanceManage>{

	public AttendanceManage getAttendance(){
		String sql = "select * from wx_attendance_manage";
		List<AttendanceManage> suite = getSession().createSQLQuery(sql).addEntity(AttendanceManage.class).list();
		return suite.size()>0?suite.get(0):null;
	}
}
