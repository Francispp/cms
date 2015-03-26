package com.cyberway.cms.permission.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;

import com.cyberway.cms.permission.domain.CmsResource;
import com.cyberway.cms.permission.service.CmsResourceService;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.web.BaseBizController;

/**
 * cms资源管理控制器
 * 
 * @author caiw
 * 
 */
public class CmsResourceController extends BaseBizController<CmsResource> {
	CmsResourceService	         service	        = (CmsResourceService) this.getServiceById("cmsResourceService");

	// WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued" list="#{true:'是',false:'否'}" />
	// 改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
	private Map<Integer, String>	trueOfFalseMap1	= null;
	private Map<Integer, String>	trueOfFalseMap2	= null;

	@Override
	protected CmsResourceService getService() {
		return service;
	}

	@Override
	public String execute() throws Exception {

		CriteriaSetup criteria = new CriteriaSetup();
		//List<Order> orders = new ArrayList<Order>();
		//orders.add(Order.asc("orderNo"));
		//criteria.setInOrders(orders);
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);

		return LIST_RESULT;
	}

	@Override
	public String saveOrUpdate() throws Exception {
		super.saveOrUpdate();
		addActionMessage("保存成功!");
		return INPUT;
	}

	public Map<Integer, String> getTrueOfFalseMap1() {
		if (trueOfFalseMap1 != null) {
			return trueOfFalseMap1;
		} else {
			trueOfFalseMap1 = new HashMap<Integer, String>();
			trueOfFalseMap1.put(new Integer(1), "站点");
			trueOfFalseMap1.put(new Integer(2), "频道");
			trueOfFalseMap1.put(new Integer(3), "文档");
			trueOfFalseMap1.put(new Integer(4), "模板");
			return trueOfFalseMap1;
		}
	}

	public Map<Integer, String> getTrueOfFalseMap2() {
		if (trueOfFalseMap2 != null) {
			return trueOfFalseMap2;
		} else {
			trueOfFalseMap2 = new HashMap<Integer, String>();
			trueOfFalseMap2.put(new Integer(0), "否");
			trueOfFalseMap2.put(new Integer(1), "是");
			return trueOfFalseMap2;
		}
	}

}
