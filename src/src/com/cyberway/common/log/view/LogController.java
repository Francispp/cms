package com.cyberway.common.log.view;

import java.util.Map;

import org.ecside.util.RequestUtil;
import org.hibernate.criterion.Order;
import org.springframework.util.CollectionUtils;

import com.cyberway.common.service.SystemLogService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.ectable.ExtremeTablePage;
import com.cyberway.core.web.BaseBizController;

public class LogController extends BaseBizController {
	SystemLogService service=(SystemLogService)this.getServiceById("systemLogService");
	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		return service;
	}
	public String execute() throws Exception{
		//HttpServletRequest request=getHttpServletRequest();
		
/*		Map filterMap=new HashMap();	
		filterMap.put("accessid", getLoginer().getUserid());		
		Page page=service.findECPage(filterMap, ExtremeTablePage.getLimit(request),null);
		request.setAttribute("items", page.getResult());
		this.setTotalRows(page.getTotalCount());//设置数据条数
*/
/*	if(!limit.getSort().isSorted()){
	if(criteriaSetup==null)
		criteriaSetup=new CriteriaSetup();
	Order order=Order.desc("accesstime");
	criteriaSetup.setInOrder(order);
	logger.info("按默认的方式排序! accesstime desc");
	}else
		logger.info("order by :"+limit.getSort().toString());*/
		CriteriaSetup criteria = new CriteriaSetup();
		Map orderMap = ExtremeTablePage.getSort(RequestUtil.getLimit(getHttpServletRequest(), getTableId()));
		if (CollectionUtils.isEmpty(orderMap)){
		 Order order=Order.desc("accesstime");
		 criteria.setInOrder(order);		
		logger.info("按默认的方式排序! accesstime desc");
		}
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);		
				
		return LIST_RESULT;
	}
	
	/**
	 * 清空所有
	 * @return
	 * @throws Exception
	 */
	public String deleteAll() throws Exception{
		service.deleteAll();
		return execute();
	}
}
