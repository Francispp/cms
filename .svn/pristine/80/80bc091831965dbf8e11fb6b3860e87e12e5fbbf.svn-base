package com.cyberway.common.commoninfo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.common.domain.CoreCommonType;
import com.cyberway.common.xtree.DHtmlXTree;
import com.cyberway.common.xtree.DHtmlXTree.Node;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.exception.BizException;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

/**
 * @author caiw
 * 
 *         公共信息管理service
 * 
 */
public class CommonTypeService extends HibernateEntityDao<CoreCommonType> {
	private SiteCache siteCache;
	public synchronized void init(){
		List<CoreCommonType> coreCommonTypeList=getAll();
		if(!coreCommonTypeList.isEmpty()){			
			for(CoreCommonType coreCommonType:coreCommonTypeList){
				siteCache.putCoreCommonTypeInCache(coreCommonType);
			}
		}
	}
	
	private SiteManagerService siteManagerService;

	public CoreCommonType saveOrUpdate(CoreCommonType obj) {
		Boolean unique = this.isNotUnique(obj, "keyword");
		if (unique)
			throw new BizException("类别名不能重名！");
		return super.saveOrUpdate(obj);
	}

	public String getCommonTypeXmls(Long siteid) {
		List<CoreCommonType> types = new ArrayList();
		if (siteid != null)
			types = this.find("from CoreCommonType where siteid=? and parentid is null", new Object[] { siteid });
		DHtmlXTree dx = new DHtmlXTree();
		dx.setTreeId("P_" + siteid);
		
		siteManagerService = (SiteManagerService) ServiceLocator.getBean("siteManagerService");
		CmsSite site = siteManagerService.getSiteFromCache(siteid);
		Node nd = dx.initNode();
		nd.setId("R_" + siteid);
		nd.setOpen(true);
		nd.setText(site.getSitename());
	//	nd.setIm0("tombs.gif");
	//	nd.setIm1("tombs.gif");
	//	nd.setIm2("tombs.gif");
		List subnd = new ArrayList();
		for(CoreCommonType cm : types) {
			subnd.add(getNode(cm, dx));
		}
		nd.setSubNodeList(subnd);
		dx.addNode(nd);
		return dx.getDHtmlXmlTree();
	}

	private Node getNode(CoreCommonType type, DHtmlXTree dx) {
		Node nd = dx.initNode();
		nd.setId(type.getId().toString());
		nd.setText(type.getKeyword());
		//nd.setIm0("tombs.gif");
		//nd.setIm1("tombs.gif");
		//nd.setIm2("tombs.gif");

		Set subTypes = type.getSubTypes();
		if (subTypes != null && subTypes.size() > 0) {
			Iterator it = subTypes.iterator();
			List ls = new ArrayList();
			while (it.hasNext()) {
				ls.add(getNode((CoreCommonType) it.next(), dx));
			}
			nd.setSubNodeList(ls);
		}
		return nd;
	}

	public boolean setParentType(String mid, String pmid, String siteid) {
		if (StringUtil.isEmpty(mid)) {
			return false;
		}
		CoreCommonType type = this.get(new Long(mid));
		if (!StringUtil.isEmpty(pmid)) {
			CoreCommonType pType = this.get(new Long(pmid));
			
			type.setParentid(pType.getId());
		} else {// 父菜单id为空，设置siteid
			try {
				type.setParentid(null);
				type.setSiteid(new Long(siteid));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				return false;
			}
		}
		this.save(type);
		return true;
	}
	
	/**
	 * dwr 验证 : Keyword 是否唯一
	 * @remark dwrUnique add by liaozhiyong 2012-03-13
	 * @param coreCommonTypeId
	 * @return String (0 已经存在有，1是唯一)
	 */
	@SuppressWarnings("unchecked")
	public String dwrCoreCommonTypeValidateKeywordUnique(String coreCommonTypeId, String keyword){
		String flag = "0";
		if (keyword != null && keyword.length() > 0) {
			String hql = " from CoreCommonType where keyword='"+keyword+"' ";
			if(coreCommonTypeId!=null && coreCommonTypeId.length()>0){
				hql += " and id<>"+coreCommonTypeId+" ";
			}  
			List<CoreCommonType> list =this.find( hql );
			if(list==null || list.size()<1){
				flag = "1";
			}
		} 
		return flag;
	}
	

	public SiteManagerService getSiteManagerService() {
		return siteManagerService;
	}
	public void setSiteManagerService(SiteManagerService siteManagerService) {
		this.siteManagerService = siteManagerService;
	}
	public SiteCache getSiteCache() {
		return siteCache;
	}
	public void setSiteCache(SiteCache siteCache) {
		this.siteCache = siteCache;
	}
}
