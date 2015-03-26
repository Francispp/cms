package com.cyberway.cms.component.oscache.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.ecside.core.ECSideConstants;

import com.cyberway.cms.component.oscache.domain.CacheURL;
import com.cyberway.cms.component.oscache.service.CMSCacheURLService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;
import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.base.persistence.CachePersistenceException;
import com.opensymphony.oscache.web.ServletCacheAdministrator;

public class CMSCacheURLController extends BaseBizController<CacheURL> {

	
	//WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued" list="#{true:'是',false:'否'}" />
  	//改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
	private Map<Boolean, String> trueOfFalseMap1 = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4262141059437497530L;
	private CMSCacheURLService service;

	@Override
	protected EntityDao<CacheURL> getService() {
		if (this.service == null)
			this.service = (CMSCacheURLService) super.getServiceById("CMSCacheURLService");
		return service;
	}


	/**
	 * 清理缓存
	 * @return
	 * @throws CachePersistenceException 
	 * @throws IOException 
	 */
	public String cleanup() throws CachePersistenceException, IOException {
		ServletContext sc = getHttpServletRequest().getSession().getServletContext();
		ServletCacheAdministrator admin = ServletCacheAdministrator.getInstance(sc);
		Cache cache = admin.getAppScopeCache(sc);
		//CMSCacheURLFilter中设置 oid 为 groupName
		//cache.removeEntry(groupName);
		if(!StringUtil.isEmpty(keys)){
			for(String groupName:keys.split(","))
				cache.getPersistenceListener().removeGroup(groupName);
		}
		getHttpServletRequest().setAttribute(ECSideConstants.C_UPDATE_RESULT_CODE,"清除成功！");
		return AJAX;
	}


	public Map<Boolean, String> getTrueOfFalseMap1() {
		if(trueOfFalseMap1 != null){
			return trueOfFalseMap1;
		}else{
			trueOfFalseMap1 = new HashMap<Boolean, String>();
			trueOfFalseMap1.put(new Boolean(true), "立即生效");
			trueOfFalseMap1.put(new Boolean(false), "暂不使用");
			return trueOfFalseMap1;
		}
	}
	
	

}
