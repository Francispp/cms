package com.cyberway.common.ftpservice.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.Constants;
import com.cyberway.cms.CmsIpDistribution.service.CmsIpDistributionService;
import com.cyberway.cms.domain.CmsIpDistribution;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.common.domain.CoreSiteDistribution;
import com.cyberway.common.ftpservice.service.FtpServiceService;
import com.cyberway.common.ftpservice.service.ThreeDES;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

/**
 * ftp服务器配置管理
 * 
 * @author taoz
 * 
 */
public class FtpServiceController extends BaseBizController<CoreSiteDistribution> {

	FtpServiceService	         service	              = (FtpServiceService) this.getServiceById("ftpServiceService");

	SiteManagerService	         siteManagerService	      = (SiteManagerService) ServiceLocator.getBean("siteManagerService");

	CmsIpDistributionService	 cmsIpDistributionService	= (CmsIpDistributionService) ServiceLocator.getBean("cmsIpDistributionService");

	private String	             siteId;

	// WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued"
	// list="#{true:'是',false:'否'}" />
	// 改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
	private Map<String, String>	trueOfFalseMap1	      = null;

	@Override
	protected FtpServiceService getService() {
		return service;
	}

	@Override
	public String execute() throws Exception {
		return list();
	}

	@Override
	public String list() throws Exception {
		CriteriaSetup c = new CriteriaSetup();
		c.addFilter("siteId", getLoginerSiteId());
		doListEntity(c, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}

	@Override
	public String edit() throws Exception {
		if(id!=null){
			get();
			ThreeDES des = new ThreeDES();
			des.getKey(Constants.CMS_FTP_ACTISECRETKEY);
			domain.setPassWord(des.getDesString(domain.getPassWord()));
		}else{
			domain=getDomainClass().newInstance();
		}			
		return EDIT_RESULT;
	}

	@Override
	public String saveOrUpdate() throws Exception {
		domain.setSiteId(getLoginerSiteId());

		if(StringUtils.isNotBlank(domain.getPassWord())){//加密
			ThreeDES des = new ThreeDES();
			des.getKey(Constants.CMS_FTP_ACTISECRETKEY);
			domain.setPassWord(des.getEncString(domain.getPassWord()));
		}
		
		super.saveOrUpdate();
		addActionMessage("保存成功!");
		
		if(domain.getId() != null){//更新缓存
			cmsIpDistributionService.saveByFtpService(getLoginerSiteId(), (CoreSiteDistribution)BeanUtils.cloneBean(domain));
		}
		//解密
		ThreeDES des = new ThreeDES();
		des.getKey(Constants.CMS_FTP_ACTISECRETKEY);
		domain.setPassWord(des.getDesString(domain.getPassWord()));
		
		return EDIT_RESULT;
	}

	

	@Override
    public String delete() throws Exception {
		if(!StringUtil.isEmpty(keys)){
			List<Long> list=StringUtil.splitToList(keys,",");
			for(Long id : list){
				List<CmsIpDistribution> ipDistributionList = cmsIpDistributionService.findByFtp(getLoginerSiteId(), id);
				if(ipDistributionList.size() > 0){
					CoreSiteDistribution c = service.get(id);
					addActionMessage("ip为 " + c.getFtpIp() + " 的ftp服务器被某个分发资源类型引用着,不可以删除!");
				}else{
					service.removeById(id);
				}
			}
		}else
			this.addActionError(this.getText("RESOURCE.HINTINFO.LETSELECTDELETERECORDWORD"));
		return execute();
    }

	/**
	 * 选择分发服务器
	 */
	public String selectIp() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		List<CmsIpDistribution> iplist = cmsIpDistributionService.findBy("did", id);
		String dtype = getHttpServletRequest().getParameter("dtype");
		getHttpServletRequest().setAttribute("siteId", siteId);
		getHttpServletRequest().setAttribute("dtype", dtype);
		if (items != null && items.size() > 0) {
			for (int i = 0; i < items.size(); i++) {
				CoreSiteDistribution csd = (CoreSiteDistribution) items.get(i);
				for (int j = 0; j < iplist.size(); j++) {
					if (csd.getId().equals(iplist.get(j).getIpid())) {
						csd.setChecked("1");
					}
				}
			}
		}

		return "selectlist";
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Map<String, String> getTrueOfFalseMap1() {
		if (trueOfFalseMap1 != null) {
			return trueOfFalseMap1;
		} else {
			trueOfFalseMap1 = new HashMap<String, String>();
			trueOfFalseMap1.put("1", "FTP");
			trueOfFalseMap1.put("2", "SFTP");
			return trueOfFalseMap1;
		}
	}

}
