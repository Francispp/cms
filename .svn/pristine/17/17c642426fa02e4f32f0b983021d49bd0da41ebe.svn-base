package com.cyberway.common.commoninfo.view;

import org.hibernate.criterion.Order;

import com.cyberway.cms.webservice.service.CacheSynchroismService;
import com.cyberway.common.commoninfo.service.CommonInfoService;
import com.cyberway.common.commoninfo.service.CommonTypeService;
import com.cyberway.common.domain.CoreCommonInfo;
import com.cyberway.common.domain.CoreCommonType;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.web.BaseBizController;

/**
 * @author caiw
 * @update  liaozhiyong 2012-03-12
 * 信息管理控制器
 */
public class CommonInfoController extends BaseBizController<CoreCommonInfo>{
	private static final long serialVersionUID = 12232L;
	CommonInfoService service=(CommonInfoService)this.getServiceById("commonInfoService");
	CommonTypeService commonTypeService = (CommonTypeService) this.getServiceById("commonTypeService");
	CacheSynchroismService cacheSynchroismService = (CacheSynchroismService) this.getServiceById("cacheSynchroismService");
	
	/**
	 * 说明：必需是String 类型，才可以在分页时把参数值传到这里 
	 */
	//private Long coreCommonTypeId;
	private String coreCommonTypeId;
	
	public String list() throws Exception{
		if (coreCommonTypeId != null ) {
			CriteriaSetup criteria = new CriteriaSetup();
			criteria.addFilter("coreCommonType.id", Long.valueOf(coreCommonTypeId));
			criteria.setInOrder(Order.asc("sortOrder"));
			doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		}
		return LIST_RESULT;
	}
	
	public String edit()throws Exception{
		if(id!=null){
			get();
		} else {
			domain=getDomainClass().newInstance();
			if (coreCommonTypeId != null) {
				CoreCommonType coreCommonType = commonTypeService.get(Long.valueOf(coreCommonTypeId));
				this.domain.setCoreCommonType(coreCommonType);
			}
		}
		return EDIT_RESULT;
	}
	
    public String saveOrUpdate() throws Exception {
		if(coreCommonTypeId!=null){
			CoreCommonType coreCommonType = commonTypeService.get(Long.valueOf(coreCommonTypeId));
			if(coreCommonType!=null){
				domain.setCoreCommonType(coreCommonType);
				getService().saveOrUpdate(domain);
			    addActionMessage("保存成功!");
			}
			/* 缓存同步 */
			try {
				cacheSynchroismService.updateCommonInfo(getId(), "");
			} catch (Exception ex) {
				logger.error("", ex);
				this.addActionMessage("保存失败!");
			}
		}
	    return EDIT_RESULT;
    }
    
    public String delete() throws Exception { 
		super.delete();
		/* 缓存同步 */
		try {
			cacheSynchroismService.updateCommonInfo(getId(), "del");
		} catch (Exception ex) {
			logger.error("", ex);
			this.addActionMessage("删除失败!");
		}
		return list();
    }

	public String getCoreCommonTypeId() {
		return coreCommonTypeId;
	}

	public void setCoreCommonTypeId(String coreCommonTypeId) {
		this.coreCommonTypeId = coreCommonTypeId;
	}

	protected EntityDao getService() {
		return service;
	}
}
