package com.cyberway.cms.siteDistribution.view;

import java.util.ArrayList;
import java.util.List;

import com.cyberway.cms.CmsIpDistribution.service.CmsIpDistributionService;
import com.cyberway.cms.component.selectlist.domain.ListOption;
import com.cyberway.cms.component.selectlist.service.ListOptionService;
import com.cyberway.cms.domain.CmsDistribution;
import com.cyberway.cms.domain.CmsIpDistribution;
import com.cyberway.cms.siteDistribution.service.SiteDistributionService;
import com.cyberway.common.domain.CoreSiteDistribution;
import com.cyberway.common.ftpservice.service.FtpServiceService;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

/**
 * 站点分发管理
 * 
 * @author taoz
 * 
 */
public class SiteDistributionController extends BaseBizController<CmsDistribution> {
	SiteDistributionService	   service	 = (SiteDistributionService) ServiceLocator.getBean("siteDistributionService");

	FtpServiceService	       ftpServiceService	     = (FtpServiceService) this.getServiceById("ftpServiceService");

	ListOptionService	       listOptionService	     = (ListOptionService) ServiceLocator.getBean("listOptionService");

	CmsIpDistributionService	cmsIpDistributionService	= (CmsIpDistributionService) ServiceLocator.getBean("cmsIpDistributionService");

	List<CoreSiteDistribution>	ftpList	                 = new ArrayList<CoreSiteDistribution>();

	/**
	 * 资源类型:静态html
	 */
	public final static String	HTML_RESOURCE	         = "html_resource";
	/**
	 * 资源类型:其他资源
	 */
	public final static String	OTHER_RESOURCE	         = "other_resource";
	/**
	 * 资源类型:流媒体
	 */
	public final static String	MEDIA_RESOURCE	         = "media_resource";
	/**
	 * 资源类型:静态资源
	 */
	public final static String	STATIC_RESOURCE	         = "static_resource";
	/**
	 * 资源类型:动态jsp资源
	 */
	public final static String	JSP_RESOURCE	         = "jsp_resource";
	/**
	 * 资源类型:word文档资源
	 */
	public final static String	WORD_RESOURCE	         = "word_resource";
	/**
	 * 资源类型:Lucene资源
	 */
	public final static String	LUCENE_RESOURCE	         = "lucene_resource";

	Long	                   siteId	                 = null;

	@Override
	protected SiteDistributionService getService() {
		return service;
	}

	@Override
	public String execute() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		Long siteId = getLoginerSiteId();
		if (siteId != null && siteId > 0) {
			criteria.addFilter("siteId", siteId);
			doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
			if (items != null && items.size() > 0) {
				for (int i = 0; i < items.size(); i++) {
					CmsDistribution cdb = (CmsDistribution) items.get(i);
					String dtype = cdb.getDtype();
					List<ListOption> list = listOptionService.getAll();
					for (ListOption listOption : list) {
						if (dtype.equals(listOption.getKey())) {
							cdb.setDtypeName(listOption.getValue());
						}
					}
				}

			}
		}
		return LIST_RESULT;

	}

	@Override
	public String edit() throws Exception {
		ftpList = ftpServiceService.getFtpBySiteId(getLoginerSiteId());
		if (id != null) {
			get();

			// 分发服务器的回显
			List<CmsIpDistribution> iplist = cmsIpDistributionService.findBy("did", id);
			for (int i = 0; i < ftpList.size(); i++) {
				CoreSiteDistribution csd = ftpList.get(i);
				for (int j = 0; j < iplist.size(); j++) {
					if (csd.getId().equals(iplist.get(j).getIpid())) {
						csd.setChecked("1");
					}
				}
			}
		} else {
			domain = getDomainClass().newInstance();
		}
		return EDIT_RESULT;
	}

	@Override
	public String saveOrUpdate() throws Exception {
		Long siteId = getLoginerSiteId();
		if (siteId != null) {
			domain.setSiteId(siteId);
			domain = getService().saveOrUpdate(domain);

			List<Long> list = new ArrayList<Long>();
			if (!StringUtil.isEmpty(keys)) {
				list = StringUtil.splitToList(keys, ",");
			}
			cmsIpDistributionService.saveSelectIp(list, domain.getDtype(), domain.getId(), siteId);

			// 分发服务器的回显
			ftpList = ftpServiceService.getFtpBySiteId(getLoginerSiteId());
			List<CmsIpDistribution> iplist = cmsIpDistributionService.findBy("did", domain.getId());
			for (int i = 0; i < ftpList.size(); i++) {
				CoreSiteDistribution csd = ftpList.get(i);
				for (int j = 0; j < iplist.size(); j++) {
					if (csd.getId().equals(iplist.get(j).getIpid())) {
						csd.setChecked("1");
					}
				}
			}

			addActionMessage("保存成功！");
		}
		return EDIT_RESULT;
	}

	/**
	 * 查看分发记录
	 */
	public String selectLog() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		Long siteId = getLoginerSiteId();
		if (siteId != null && siteId > 0) {
			criteria.addFilter("siteId", siteId);
			doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);

		}

		return "selectlog";
	}

	@Override
	public String delete() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> list = StringUtil.splitToList(keys, ",");
			for(Long id : list){
				CmsDistribution d = service.get(id);
				
				//删除缓存
				cmsIpDistributionService.removeIp(getSiteId(), d.getId(), d.getDtype());
				
				//删除分发资源类型
				service.remove(d);
				
				//删除分发资源类型跟ftp服务器之间的更新表
				List<CmsIpDistribution> ipDistributionList = cmsIpDistributionService.findByType(getLoginerSiteId(), id);
				for(CmsIpDistribution ipDistribution : ipDistributionList){
					cmsIpDistributionService.remove(ipDistribution);
				}
			}
			this.addActionMessage(this.getText("RESOURCE.HINTINFO.DELETESUCCESS"));

		} else
			this.addActionError(this.getText("RESOURCE.HINTINFO.LETSELECTDELETERECORDWORD"));
		return execute();
	}

	public List<CoreSiteDistribution> getFtpList() {
		return ftpList;
	}

	public void setFtpList(List<CoreSiteDistribution> ftpList) {
		this.ftpList = ftpList;
	}

	public Long getSiteId() {
		return getLoginerSiteId();
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

}
