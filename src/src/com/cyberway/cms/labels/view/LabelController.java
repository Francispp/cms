package com.cyberway.cms.labels.view;

import java.util.List;

import org.hibernate.criterion.Order;

import com.cyberway.cms.labels.domain.Label;
import com.cyberway.cms.labels.service.LabelService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;
import com.opensymphony.xwork2.ActionContext;

/**
 * 标签管理
 * 
 */

public class LabelController extends BaseBizController<Label> {

	LabelService service = (LabelService) this.getServiceById("labelService");

	public String execute() throws Exception {
		return list();
	}
 
	public String list() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		criteria.setInOrder(Order.asc("id"));
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}

	public String edit() throws Exception {
		return super.edit();
	}

	public String saveOrUpdate() throws Exception {
		super.saveOrUpdate();
		addActionMessage("保存成功!");
		return EDIT_RESULT;
	}

	/**
	 * 删除
	 */
	public String delete() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> list  = StringUtil.splitToList(keys, ",");
			getService().removeByIds(list);
			this.addActionMessage(this.getText("RESOURCE.HINTINFO.DELETESUCCESS"));
		} else
			this.addActionError(this.getText("RESOURCE.HINTINFO.LETSELECTDELETERECORDWORD"));
		return list();
	}

	@Override
	protected EntityDao getService() {
		return service;
	}
	
	public String titleList() throws Exception {
		List<Label> result=service.getAll();
		ActionContext.getContext().put("result", result);
		return "titleList";
	}
	
	public String getContent() throws Exception {
		if(id!=null){
			get();
		}else{
			domain=getDomainClass().newInstance();
		}	
		return "getContent";
	}
	
}
