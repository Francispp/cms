package com.cyberway.common.portal.service;

import java.util.ArrayList;
import java.util.List;

import com.cyberway.common.domain.CorePortal;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.exception.BizException;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.objects.Portal;

/**
 * 门户管理service
 * @author caiw
 * 
 */
public class PortalManagerService extends HibernateEntityDao<CorePortal> {

    /**
     * 获得当前用户所有门户信息
     * @param loginer
     * @return
     */
    public List<Portal> getPortalsByLoginer(Loginer loginer){
    	List<Portal> cos=new ArrayList();
    	CorePortal cp=null;
    	if(loginer.checkIsAdministratorUser()){//超级管理员
    		List<CorePortal> cps=getAll();
    		if(cps!=null){
    			for(CorePortal tcp:cps)
    				cos.add(getPortalByCorePortal(tcp));
    		}
    	}else{//一般用户只能管理当前登录的门户 		
    		cp=get(new Long(loginer.getPortal().getPortalid()));
    		cos.add(getPortalByCorePortal(cp));
    	}
    		return cos;
    }
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.core.dao.HibernateEntityDao#saveOrUpdate(java.lang.Object)
	 */
	public CorePortal saveOrUpdate(CorePortal obj) {
		Boolean unique = this.isNotUnique(obj, "code");
		// 如果已经存在相同的portalCode
		if (unique) {
			throw new BizException("code已经存在！");
		}
		return super.saveOrUpdate(obj);
	}

	/**
	 * 按门户编码获得门户对象
	 * 
	 * @param portalcode
	 * @return
	 */
	public Portal getPortalByPortalcode(String portalcode) {
		// Portal portal=new Portal();
		// List<CorePortal> list=this.findUniqueBy("portalCode", portalcode);
		// if(list!=null && list.size()>0){
		CorePortal cp = findUniqueBy("code", portalcode);
		
		return getPortalByCorePortal(cp);
	}
	
	/**
	 * 从CorePortal转换成Portal对象
	 * 
	 * @param cp
	 * @return
	 */
	public static Portal getPortalByCorePortal(CorePortal cp) {
		Portal portal = new Portal();
		portal.setPortalid(cp.getOid().toString());
		portal.setPortalcode(cp.getCode());
		portal.setCname(cp.getName());
		return portal;
	}
}
