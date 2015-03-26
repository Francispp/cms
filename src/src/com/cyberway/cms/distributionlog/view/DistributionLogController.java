package com.cyberway.cms.distributionlog.view;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.component.selectlist.domain.ListOption;
import com.cyberway.cms.component.selectlist.service.ListOptionService;
import com.cyberway.cms.distributionlog.service.DistributionLogService;
import com.cyberway.cms.domain.CmsDistributionLog;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

/**
 * 站点分发记录
 * 
 * @author taoz
 * 
 */
public class DistributionLogController extends BaseBizController<CmsDistributionLog> {
	DistributionLogService	distributionLogService	= (DistributionLogService) ServiceLocator.getBean("distributionLogService");
	ListOptionService	   listOptionService	   = (ListOptionService) ServiceLocator.getBean("listOptionService");

	@Override
	protected DistributionLogService getService() {
		return distributionLogService;
	}

	/**
	 * 列表
	 */
	public String execute() throws Exception {
		String dtype = getHttpServletRequest().getParameter("dtype");
		getHttpServletRequest().setAttribute("dtype", dtype);
		CriteriaSetup criteria = new CriteriaSetup();
		Long siteId = getLoginerSiteId();
		if (!siteId.equals(0L)) {
			criteria.addFilter("siteId", siteId);
		}
		if (StringUtils.isNotEmpty(dtype)) {
			criteria.addFilter("type", dtype);
		}

		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		if (items != null && items.size() > 0) {
			for (int i = 0; i < items.size(); i++) {
				CmsDistributionLog cdbl = (CmsDistributionLog) items.get(i);
				String type = cdbl.getType();
				List<ListOption> list = listOptionService.getAll();
				for (ListOption listOption : list) {
					if (type.equals(listOption.getKey())) {
						cdbl.setTypeName(listOption.getValue());
					}
				}
			}

		}
		getHttpServletRequest().setAttribute("siteId", siteId);
		return LIST_RESULT;

	}

	/**
	 * 清空
	 */
	public String deleteAll() throws Exception {
		Long siteId = getLoginerSiteId();
		String dtype = getHttpServletRequest().getParameter("dtype");
		if (StringUtils.isNotEmpty(dtype)) {
			List<CmsDistributionLog> cdlList = getService().findByType(siteId, dtype);
			if (cdlList != null) {
				for (int i = 0; i < cdlList.size(); i++) {
					getService().remove(cdlList.get(i));
				}
			}
		}
		return execute();
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		Long siteId = getLoginerSiteId();
		getHttpServletRequest().setAttribute("siteId", siteId);
		String dtype = getHttpServletRequest().getParameter("dtype");
		getHttpServletRequest().setAttribute("dtype", dtype);

		if (!StringUtil.isEmpty(keys)) {
			List list = StringUtil.splitToList(keys, ",");
			getService().removeByIds(list);
			this.addActionMessage(this.getText("RESOURCE.HINTINFO.DELETESUCCESS"));

		} else
			this.addActionError(this.getText("RESOURCE.HINTINFO.LETSELECTDELETERECORDWORD"));
		return execute();
	}
}
