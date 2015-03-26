package com.cyberway.common.identity.service;

import java.util.List;

import com.cyberway.common.domain.CoreIdentity;
import com.cyberway.core.dao.HibernateEntityDao;

public class IdentityManagerService extends HibernateEntityDao<CoreIdentity> {
	/**
	 * 判断身份代码是否唯一
	 * @author Dicky
	 * @time 2012-9-13上午11:05:34
	 * @version 1.0
	 * @param id
	 * @param identityCode
	 * @param siteId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean identityCodeIsUnique(Long id, String identityCode, Long siteId) {
		if(id==null || id==0){
			List list = this.find("from CoreIdentity where icode=? and siteId=?", identityCode, siteId);
			if(list.size()>0){
				return false;
			}
		}else{
			List list = this.find("from CoreIdentity where icode=? and siteId=? and iid<>?", identityCode, siteId,id);
			if(list.size()>0){
				return false;
			}
		}
		return true;
	}
}
