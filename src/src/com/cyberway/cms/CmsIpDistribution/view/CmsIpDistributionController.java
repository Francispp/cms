package com.cyberway.cms.CmsIpDistribution.view;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.CmsIpDistribution.service.CmsIpDistributionService;
import com.cyberway.cms.domain.CmsIpDistribution;
import com.cyberway.cms.siteDistribution.service.SiteDistributionService;
import com.cyberway.common.domain.CoreSiteDistribution;
import com.cyberway.common.ftpservice.service.FtpServiceService;
import com.cyberway.common.service.CommonCache;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.web.BaseBizController;

public class CmsIpDistributionController extends BaseBizController<CmsIpDistribution> {
	CmsIpDistributionService service=(CmsIpDistributionService)this.getServiceById("cmsIpDistributionService");
	
	FtpServiceService ftpServiceService=(FtpServiceService)ServiceLocator.getBean("ftpServiceService");
	SiteDistributionService siteDistributionService=(SiteDistributionService)ServiceLocator.getBean("siteDistributionService");
	
	private String siteId;
	@Override
	protected CmsIpDistributionService getService() {
		return service;
	}
	
	/* 列表
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	public String execute()throws Exception{
		CriteriaSetup criteria = new CriteriaSetup();
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}
    /**
     * 保存 选中的服务器
     * @throws Exception
     */
	public void saveSelectIp() throws Exception{
		String ipids=getHttpServletRequest().getParameter("ids");
		String dtype=getHttpServletRequest().getParameter("dtype");
		String did=getHttpServletRequest().getParameter("did");
		if(ipids!=null&& StringUtils.isNotBlank(ipids)){
		   String[] ipid=ipids.split(",");
			List<CmsIpDistribution> cidList=getService().findByType(Long.valueOf(siteId),Long.valueOf(did));
			if(cidList.size()>0){
				for(int i=0;i<cidList.size();i++){
					CmsIpDistribution cidb=cidList.get(i); 	
					// 获得siteDistribution在缓冲中的key,格式是：S_+ siteId + R_ + resourceType + "_" + ftpId
					ftpServiceService.removeSiteDistributionFromCache(Long.valueOf(siteId),dtype,cidb.getIpid());
					getService().remove(cidList.get(i));
				}
			}
			for(int i=0;i<ipid.length;i++){
				
				CmsIpDistribution ipDistribution=new CmsIpDistribution();
				ipDistribution.setDid(Long.valueOf(did));
				ipDistribution.setIpid(Long.valueOf(ipid[i]));
				ipDistribution.setSiteId(Long.valueOf(siteId));
				getService().save(ipDistribution);
				
				CoreSiteDistribution csdb=ftpServiceService.get(Long.valueOf(ipid[i]));
				System.out.println(csdb);
				ftpServiceService.putSiteDistributionInCache(Long.valueOf(siteId), dtype, csdb);
				
			}
			
		}
		else{
			List<CmsIpDistribution> cidList=getService().findByType(Long.valueOf(siteId),Long.valueOf(did));
			for(int i=0;i<cidList.size();i++){
				CmsIpDistribution cidb=cidList.get(i);
				getService().remove(cidList.get(i));
				ftpServiceService.removeSiteDistributionFromCache(Long.valueOf(siteId),dtype,cidb.getIpid());
			}
			addActionMessage("保存成功!");
		}
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}


		
	
}
