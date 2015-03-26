package com.cyberway.common.grade.view;

import org.hibernate.criterion.Order;

import com.cyberway.common.domain.CoreGrade;
import com.cyberway.common.grade.service.GradeManagerService;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.web.BaseBizController;

/**
 * 角色级别管理
 * 
 * @author taoz
 * 
 *         2011-10-26下午05:27:02
 */
public class GradeController extends BaseBizController<CoreGrade> {
	GradeManagerService	service	= (GradeManagerService) this.getServiceById("gradeManagerService");

	@Override
	protected GradeManagerService getService() {
		return service;
	}
	
	@Override
	public String execute() throws Exception {
		return list();
	}

	@Override
    public String list() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		criteria.addFilter("siteId", getLoginerSiteId());
		criteria.setInOrder(Order.asc("gnumber"));
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
    }

	@Override
    public String saveOrUpdate() throws Exception {
		if (getLoginerSiteId() != 0 && domain.getSiteId()==null) {
			domain.setSiteId(getLoginerSiteId());
		}
		if(getService().gradeCodeIsUnique(domain.getGid(), domain.getGcode(), domain.getSiteId())){
			domain=getService().saveOrUpdate(domain);
		    addActionMessage("保存成功!");
		}else{
			addActionError("级别代码已存在，请重新输入!");
		}
	    return EDIT_RESULT;
    }

}
