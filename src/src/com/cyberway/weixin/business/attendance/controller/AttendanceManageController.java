package com.cyberway.weixin.business.attendance.controller;

import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.weixin.business.attendance.domain.AttendanceManage;
import com.cyberway.weixin.business.attendance.service.AttendanceManageService;
/**
 * 
 * @com.cyberway.weixin.business.attendance.controller.AttendanceManageController
 * TODO :微信考勤地点控制器
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年3月4日 上午11:27:04
 */
public class AttendanceManageController extends BaseBizController<AttendanceManage>{
	/**  
	* serialVersionUID:TODO（用一句话描述这个变量表示什么）
	*/  
	private static final long serialVersionUID = -6397560669810052076L;
	AttendanceManageService service = (AttendanceManageService) this.getServiceById("attendanceManageService");
	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub 未完成 by yanruqian 2015年3月4日
		return service;
	}
	public String doIndex(){
		service.getAttendance();
		return "success";
	}
}
