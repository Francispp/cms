package com.cyberway.cms.component.webuser.service;

import java.util.ArrayList;
import java.util.List;

import ognl.Ognl;

import com.cyberway.cms.component.webuser.domain.WebUser;
import com.cyberway.cms.component.webuser.domain.WebUserRole;
import com.cyberway.common.domain.CoreADUserRole;
import com.cyberway.common.domain.CoreRole;
import com.cyberway.common.domain.CoreUser;
import com.cyberway.common.domain.CoreUserRole;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class WebRoleService extends HibernateEntityDao<CoreRole> {
	
	private WebUserService webUserService;
	/**
	 * 获得用户所属的基本类型角色
	 * @param userid
	 * @return
	 */
	public List<CoreRole> getRolesByUserId(Long userid){
		List<CoreRole> list=new ArrayList();
		List<WebUserRole> curs=find("from WebUserRole where webUser.oid=?", new Object[]{userid});
		for(WebUserRole cur:curs){
			CoreRole cr=cur.getCoreRole();			
			if(cr.getState()!=null && cr.getState()==0)
			  list.add(cr);
		}
		/*Long allRoleid=com.cyberway.cms.Constants.ALL_USER_ROLE_ID;
		if(allRoleid!=null ){//增加所有用户角色
			CoreRole acr=this.get(allRoleid);
			if(acr!=null && !list.contains(acr))
				list.add(acr);
		}*/
		return list;
	}
	/**
	 * 获得基本类型角色所包含的用户
	 * @param roleid
	 * @return
	 */
	public List<WebUser> getWebUsersByRoleId(Long roleid){
		List<WebUser> list=new ArrayList();
		List<WebUserRole> curs=find("from WebUserRole where webUser.oid=?", new Object[]{roleid});
		for(WebUserRole cur:curs){
			WebUser cu=cur.getWebUser();
			//cu.getUsername();
			list.add(cu);
			
		}
		return list;
	}
    /**
     * 
     * @param roleid
     * @param userids
     * @return
     */
    public boolean insertUsersToRole(String roleid,String userids){
    	if(StringUtil.isEmpty(roleid)||StringUtil.isEmpty(userids)){
    		return false;
    	}
    	WebUserRole ur;
    	//CoreADUserRole ad;
    	webUserService = (WebUserService)ServiceLocator.getBean("WebUserService");
    	try{
    		String[] ids=userids.split(";");
    		for(int i=0;i<ids.length;i++){
    		 if(StringUtil.isEmpty(ids[i]))
    			continue;
    		 WebUser coreUser = webUserService.get(new Long(ids[i]));
    		 if(coreUser != null)
    		 {
    		 ur=new WebUserRole();
    		 Ognl.setValue("coreRole.oid", ur, new Long(roleid));
    		 Ognl.setValue("webUser.oid", ur, new Long(ids[i]));
    		 this.save(ur);
    		 //同时修改权限操作
    		 //modifyAuthorization(new Long(ids[i]),new Long(roleid),true);
    		 }
    		 else
    		 {
    			 
    			 throw new Exception("用户不存在！");
    			 /*ad=new CoreADUserRole();
        		 Ognl.setValue("coreRole.oid", ad, new Long(roleid));
        		 ad.setUserid(new Long(ids[i]));
        		 this.save(ad);*/
    		 }
    		
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error(e.getMessage());
    	}
    	return true;
    }
    
    /**
     * 删除指定角色下某些用户
     * @param rolid
     * @param userids
     * @return
     */
    public boolean removeUsersFromRole(String roleid,String userids){
    	if(StringUtil.isEmpty(roleid)||StringUtil.isEmpty(userids)){
    		return false;
    	}
    	List<Long> ids=StringUtil.splitToList(userids,",");
    	
    	List<Long> roleids=StringUtil.splitToList(roleid,",");
    	
    	for(Long _roleid:roleids){
    	for(Long uid:ids){
    	List list=this.find("from WebUserRole where coreRole.oid=? and webUser.oid = ?", new Object[]{new Long(_roleid),uid});
    	//删除权限操作
    	//modifyAuthorization(uid,new Long(roleid),false);
    	for(int i=0;i<list.size();i++)
    		remove(list.get(i));    		
    	
    	}
    	}
    	return true;
    }
}
