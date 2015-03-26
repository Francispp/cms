package com.cyberway.core.acegi.filter;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.cyberway.common.domain.CoreOrg;
import com.cyberway.common.org.service.OrgManagerService;
import com.cyberway.common.portal.service.PortalManagerService;
import com.cyberway.common.role.service.RoleManagerService;
import com.cyberway.common.user.service.UserManagerService;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.objects.Portal;
import com.cyberway.core.utils.ServiceLocator;

public class MyUserManager extends JdbcDaoSupport {

	public Loginer getLoginerByLoginidAndPasswd(String loginid, String passwd)
			throws RuntimeException {
		Loginer loginer = new Loginer();
		String sql = null;
		SqlRowSet rowSet =null;
		 if(checkAdminUser(loginid,passwd)){
		     sql = "select u.userid,u.loginid,u.username,u.password,u.deptid,d.deptname,d.org_id from core_user u,core_dept d where lower(u.loginid)=? and u.password=? and u.deptid=d.deptid ";
		     rowSet = this.getJdbcTemplate().queryForRowSet(sql,new Object[] { loginid.toLowerCase(), passwd });
		 }else{
			 sql = "select u.userid,u.loginid,u.username,u.password,u.deptid,d.deptname,d.org_id from core_user u,core_dept d where lower(u.loginid)=?  and u.deptid=d.deptid ";
			 rowSet = this.getJdbcTemplate().queryForRowSet(sql,new Object[] { loginid.toLowerCase() });			 
		 }
		if (rowSet.next()) {
			loginer.setUserid(rowSet.getLong(1));
			loginer.setLoginid(rowSet.getString(2));
			loginer.setUsername(rowSet.getString(3));
			loginer.setPassword(rowSet.getString(4));
			
			loginer.setDeptcode(rowSet.getLong(5));
			loginer.setDeptname(rowSet.getString(6));
			loginer.setOrgid(rowSet.getLong(7));
			//获得组织机构信息
			OrgManagerService orgservice=(OrgManagerService)ServiceLocator.getBean("orgManagerService");
			CoreOrg org=orgservice.getCoreOrgFromCache(loginer.getOrgid().toString());
			loginer.setOrgCode(org.getOrgCode());
			loginer.setOrgName(org.getOrgName());
			//设置用户类型
			loginer.setUserType(Loginer.USER_LOCAL);
			loginer.setLoginType(0);
		}
		
		return loginer;
	}
	/**
	 * 获得指定portal对象
	 * @param portalcode
	 * @return
	 */
/*	public Portal getPortalByPortalCode(String portalcode){
		Portal portal=new Portal();
		String sql = "select OID,PORTAL_CODE,PORTAL_NAME,REMARK from CORE_PORTAL where portal_code=?";

		SqlRowSet rowSet = this.getJdbcTemplate().queryForRowSet(sql,new Object[] { portalcode });

		if (rowSet.next()) {
			portal.setPortalid(rowSet.getString(1));
			portal.setPortalcode(rowSet.getString(2));
			portal.setCname(rowSet.getString(3));
			
		}		
		return portal;
	}*/
	
	/**
	 * 获得指定portal对象
	 * @param portalcode
	 * @return
	 */
	public Portal getPortalByPortalcode(String portalcode){
		PortalManagerService portalService=(PortalManagerService)ServiceLocator.getBean("portalManagerService");
		return portalService.getPortalByPortalcode(portalcode);
	}
	/**
	 * 根据当前用户，获得门户信息
	 * @param loginer
	 * @return
	 */
	public List getPortalsByLoginer(Loginer loginer){
		PortalManagerService portalService=(PortalManagerService)ServiceLocator.getBean("portalManagerService");
		return portalService.getPortalsByLoginer(loginer);
	}
	
	/**
	 * 根据用户id，获得所属角色
	 * @param userid
	 * @return
	 */
	public List getRolesByUserid(Long userid){
		RoleManagerService roleManagerService=(RoleManagerService)ServiceLocator.getBean("roleManagerService");
		return roleManagerService.getRolesByUserId(userid);
	}
	
	public static boolean checkAdminUser(String loginid, String passwd ){
		if(loginid!=null && passwd!=null && passwd.equals(UserManagerService.encodePassword())){
			return true;
		}else return false;
	}
}
