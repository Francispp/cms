package com.cyberway.common.grade.service;

import java.util.List;

import com.cyberway.common.domain.CoreGrade;
import com.cyberway.core.dao.HibernateEntityDao;

public class GradeManagerService extends HibernateEntityDao<CoreGrade> {
	
	/**
	 * 判断级别代码是否唯一
	 * @author Dicky
	 * @time 2012-9-13 10:58:30
	 * @version 1.0
	 * @param id
	 * @param gradeCode
	 * @param siteId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean gradeCodeIsUnique(Long id, String gradeCode, Long siteId) {
		if(id==null || id==0){
			List list = this.find("from CoreGrade where gcode=? and siteId=?", gradeCode, siteId);
			if(list.size()>0){
				return false;
			}
		}else{
			List list = this.find("from CoreGrade where gcode=? and siteId=? and gid<>?", gradeCode, siteId,id);
			if(list.size()>0){
				return false;
			}
		}
		return true;
	}
}
