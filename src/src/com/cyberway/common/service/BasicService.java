package com.cyberway.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cyberway.cms.Constants;
import com.cyberway.cms.login.service.OtherJdbcManager;
import com.cyberway.common.domain.CoreDept;
import com.cyberway.common.domain.CoreResource;
import com.cyberway.common.domain.CoreRole;
import com.cyberway.common.domain.CoreUser;
import com.cyberway.common.domain.VcorePermission;
import com.cyberway.common.permission.service.PermissionService;
import com.cyberway.common.role.service.RoleManagerService;
import com.cyberway.common.user.service.UserManagerService;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.objects.Portal;
import com.cyberway.core.utils.AesEncrypt;
import com.cyberway.core.utils.PortalUtil;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.ldap.persistence.dto.UserTO;
import com.cyberway.ldap.proxy.LDAPHelper;

public class BasicService extends HibernateEntityDao {

	/**
	 * 用户登录验证
	 * @param loginid
	 * @param password
	 * @return
	 */
	public Loginer login(String name,String password,String portalcode)throws Exception{
		Loginer loginer=new Loginer();
		CoreUser _user;
		
		String _pwd = "";
		String _name = name.trim().toLowerCase();
		_pwd = UserManagerService.encodePassword(password);//getEncString(name,password);
		
		logger.info("user: " + _name);
		logger.info("pwd: " + password);
		logger.info(_pwd);
		List<CoreUser> list=this.find("from CoreUser where loginid=?", new Object[]{name});
		if(list==null||list.size()==0){
			_user = null;
		}else{
			_user = (CoreUser) list.get(0);
		}
		
		if(list.size()>1){
			logger.info("存在同名用户");
			loginer.setLoginerStatus(Loginer.THE_SAME_NAME_EXIST);
		}else if (_user == null) {// 用户不存在
			logger.info("用户不存在");
			loginer.setLoginerStatus(Loginer.NO_SUCH_ID);
		} else if (_user.getPassword() == null
				|| (!_user.getPassword().equals(_pwd))) {// 密码错误
			logger.info("密码错误");
			loginer.setLoginerStatus(Loginer.WRONG_PASSWORD);
		} else {
			//正常登录
			loginer.setUserid(_user.getUserid());
			loginer.setLoginid(_user.getLoginid());
			loginer.setUsername(_user.getUsername());
			loginer.setPassword(_user.getPassword());
			loginer.setIndexStyle(_user.getIndexstyle());
			loginer.setLoginTime(new Date());
			CoreDept dept=_user.getCoreDept();
			if (dept != null) {
				loginer.setDeptcode(dept.getDeptid());
				loginer.setDeptname(dept.getDeptname());
			}
			//增加门户信息
			if(portalcode!=null){
			Portal portal=getPortalByCode(portalcode);
			loginer.setPortal(portal);
			}
			//增加当前用户的权限信息
			PermissionService permService=(PermissionService)ServiceLocator.getBean("permissionService");
			List<VcorePermission> ps=permService.getAllResourcesAccredited(loginer.getUserid(), PermissionService.types[2]);
			List permkeys=new ArrayList();
			for(VcorePermission vp:ps){
				permkeys.add(vp.getResourcekey());
			}
			loginer.setPermissions(permkeys);
		}
		
		return loginer;
	}
	/**
	 * 前台登录，登录方式可配置
	 * @param loginid
	 * @param password
	 * @param portalcode
	 * @return
	 * @throws Exception
	 */
	public Loginer logon(String loginid,String password,String portalcode)throws Exception{
		Loginer loginer=new Loginer();
		loginer.setLoginerStatus(1);//前台用户
		CoreUser _user = null;
		
		String _pwd = "";
		String _name = loginid.trim().toLowerCase();
		_pwd = UserManagerService.encodePassword(password);//getEncString(name,password);
		UserManagerService userService=(UserManagerService)ServiceLocator.getBean("userManagerService");
		//本地用户登陆
		if(Constants.LOGON_USER.equals(Loginer.USER_LOCAL))
		{
			List<CoreUser> list=this.find("from CoreUser where loginid=?", new String[]{loginid});
			if(list!=null && list.size()>0){
				_user = (CoreUser) list.get(0);	
			}
			loginer.setUserType(Loginer.USER_LOCAL);
		}
        //AD用户登陆
		else if(Constants.LOGON_USER.equals(Loginer.USER_AD))
		{
			loginer.setUserType(Loginer.USER_AD);
			if(LDAPHelper.authenticateNaming(loginid, password))
			{
				loginer.setIsADUser(true);
				_user = (CoreUser)userService.getADUser(loginid);
				_user.setPassword(_pwd);
			/*List ldapList = LDAPHelper.getUserByLoginno(loginid);
			if (ldapList.size()==1){
				Iterator it = ldapList.iterator();
				if (it.hasNext()){  				
					UserTO ut = (UserTO) it.next();
					List<CoreUser> userList =this.find("from CoreUser where userid=?",  new Long[]{Constants.PUBLIC_USERID});
					if(userList!=null && userList.size()>0)
					{
					_user = (CoreUser) userList.get(0);	
					_user.setUsername(ut.getUserName());
					_user.setEmail(ut.getMail());
					_user.setPassword(_pwd);
					}
				}
			}*/
				
			}
		
		}//其他用户验证登陆
		else if(Constants.LOGON_USER.equals(Loginer.USER_OTHER))
		{ 
			OtherJdbcManager otherJdbc=(OtherJdbcManager)ServiceLocator.getBean("otherJdbcManager");
			_user=otherJdbc.getLoginerByLoginid(loginid);			
			loginer.setUserType(Loginer.USER_OTHER);
		}
		else//未设置验证方式
		{
		List<CoreUser> list=this.find("from CoreUser where loginid=?", new String[]{loginid});
		if(list!=null && list.size()>0){
			_user = (CoreUser) list.get(0);	
		}else{
			if(LDAPHelper.authenticateNaming(loginid, password))
			{
				_user = (CoreUser)userService.getADUser(loginid);
				_user.setPassword(_pwd);
				loginer.setIsADUser(true);
			/*List ldapList = LDAPHelper.getUserByLoginno(loginid);
			if (ldapList.size()==1){
				Iterator it = ldapList.iterator();
				if (it.hasNext()){  				
					UserTO ut = (UserTO) it.next();
					List<CoreUser> userList =this.find("from CoreUser where userid=?",  new Long[]{Constants.PUBLIC_USERID});
					if(userList!=null && userList.size()>0)
					{
					_user = (CoreUser) userList.get(0);	
					_user.setUsername(ut.getUserName());
					_user.setEmail(ut.getMail());
					_user.setPassword(_pwd);
					}
				}
			}*/
				
			}
		}
		}
		
		if (_user == null) {// 用户不存在
			logger.info("用户不存在或用户名与密码不匹配！");
			loginer.setLoginerStatus(Loginer.NO_SUCH_ID);
		} else if (_user.getPassword() == null
				|| (!_user.getPassword().equals(_pwd))) {// 密码错误
			logger.info("密码错误！");
			loginer.setLoginerStatus(Loginer.WRONG_PASSWORD);
		} else {
			//正常登录
			loginer.setUserid(_user.getUserid());
			loginer.setLoginid(_user.getLoginid());
			loginer.setUsername(_user.getUsername());
			loginer.setPassword(_user.getPassword());
			loginer.setIndexStyle(_user.getIndexstyle());
			loginer.setLoginTime(new Date());
			CoreDept dept=_user.getCoreDept();
			if (dept != null) {
				loginer.setDeptcode(dept.getDeptid());
				loginer.setDeptname(dept.getDeptname());
			}
			//增加门户信息
			if(portalcode!=null){
			Portal portal=getPortalByCode(portalcode);
			loginer.setPortal(portal);
			}
			//获得当前用户的角色
			RoleManagerService roleManagerService=(RoleManagerService)ServiceLocator.getBean("roleManagerService");
			
			//本地登录
			if(Constants.LOGON_USER.equals(Loginer.USER_LOCAL))
			  loginer.setRoles(roleManagerService.getRolesByUserId(loginer.getUserid()));
			 //AD用户登陆
			else if(Constants.LOGON_USER.equals(Loginer.USER_AD))
				loginer.setRoles(roleManagerService.getLDAPRolesByUser(loginer));
			else//未设置验证方式
			{
				if(loginer.getIsADUser()){ //AD用户登陆
					loginer.setRoles(roleManagerService.getLDAPRolesByUser(loginer));
				}else //本地登录
					loginer.setRoles(roleManagerService.getRolesByUserId(loginer.getUserid()));
			}
			//增加当前用户的权限信息
			/*PermissionService permService=(PermissionService)ServiceLocator.getBean("permissionService");
			List<VcorePermission> ps=permService.getAllResourcesAccredited(loginer.getUserid(), PermissionService.types[2]);
			List permkeys=new ArrayList();
			for(VcorePermission vp:ps){
				permkeys.add(vp.getResourcekey());
			}
			loginer.setPermissions(permkeys);*/
		}
		
		return loginer;
	}
	/**
	 * 易联网登录,采用第三方数据源
	 * @param loginid
	 * @param password
	 * @param portalcode
	 * @return
	 * @throws Exception
	 */
	public Loginer ebizlogon(String loginid,String password,String portalcode)throws Exception{
		Loginer loginer=new Loginer();
		CoreUser _user = null;		
		String _pwd = "";
       //其他用户验证登陆
		if(!StringUtil.isEmpty(loginid))
		{ 
			OtherJdbcManager otherJdbc=(OtherJdbcManager)ServiceLocator.getBean("otherJdbcManager");
			_user=otherJdbc.getLoginerByLoginid(loginid);			
      
	 			
		}
		
		if (_user == null) {// 用户不存在
			logger.info("用户不存在！");
			loginer.setLoginerStatus(Loginer.NO_SUCH_ID);
		} else {
			//正常登录
			loginer.setUserid(_user.getUserid());
			loginer.setLoginid(_user.getLoginid());
			loginer.setUsername(_user.getUsername());
			
			loginer.setPassword(_user.getPassword());
			loginer.setIndexStyle(_user.getIndexstyle());
			loginer.setLoginTime(new Date());
			/*CoreDept dept=_user.getCoreDept();
			if (dept != null) {
				loginer.setDeptcode(dept.getDeptid());
				loginer.setDeptname(dept.getDeptname());
			}*/
			//增加门户信息
			if(portalcode!=null){
			Portal portal=getPortalByCode(portalcode);
			loginer.setPortal(portal);
			}
	         RoleManagerService roleService=(RoleManagerService)ServiceLocator.getBean("roleManagerService");
	         List<CoreRole> roles=roleService.getRolesByUserId(_user.getUserid());
	        //增加用户对应的角色(从易联网中带的角色)
	         if(!StringUtil.isEmpty(_user.getLevel())){
	           List<CoreRole> crs=roleService.getRolesByRoleName(_user.getLevel());
	           if(crs!=null)
	        	   roles.addAll(crs);
	        	 }
	         loginer.setRoles(roles);
			//增加当前用户的权限信息
			/*PermissionService permService=(PermissionService)ServiceLocator.getBean("permissionService");
			List<VcorePermission> ps=permService.getAllResourcesAccredited(loginer.getUserid(), PermissionService.types[2]);
			List permkeys=new ArrayList();
			for(VcorePermission vp:ps){
				permkeys.add(vp.getResourcekey());
			}
			loginer.setPermissions(permkeys);*/
		}
		
		return loginer;
	}	
	
	/**
	 * 新易联网登录
	 * @param loginid
	 * @param password
	 * @param portalcode
	 * @return
	 * @throws Exception
	 */
	public Loginer newEbizlogon(String loginid,String standing, String level, Integer isLogined)throws Exception{
		Loginer loginer=new Loginer();
		CoreUser _user = null;		
		String _pwd = "";
       //其他用户验证登陆
		if(!StringUtil.isEmpty(loginid))
		{ 
			_user = new CoreUser();
		}
		if (_user == null) {// 用户不存在
			logger.info("用户不存在！");
			loginer.setLoginerStatus(Loginer.NO_SUCH_ID);
		} else {
			//正常登录
			loginer.setUserid(Constants.PUBLIC_USERID);
			loginer.setLoginid(loginid);
			loginer.setUsername(loginid);
			loginer.setLoginTime(new Date());
			if(isLogined != 0){
		         List<CoreRole> roles=new ArrayList<CoreRole>();
		        //增加用户对应的角色(从易联网中带的角色)
		         if(!StringUtil.isEmpty(standing) && false)
		         {
		         RoleManagerService roleService=(RoleManagerService)ServiceLocator.getBean("roleManagerService");
		         if(!StringUtil.isEmpty(standing)){
		           List<CoreRole> crs=roleService.getRolesByRoleName(standing);
		           if(crs!=null)
		        	   roles.addAll(crs);
		        	 }
		         }
		         loginer.setRoles(roles);
			}
		}
		
		return loginer;
	}	
	
	
	
	
	/**
	 * 检测用户是否为管理员
	 * @param loginer
	 * @return
	 */
	private int checkIsAdministratorUser(Loginer loginer){
		int ifadmin = 0;//一般用户
		if(loginer.getUserid().intValue()==0){//判断管理员的条件，待后修改
			ifadmin=1;
		}
		if(loginer.getLoginid().endsWith("admin"))
			ifadmin=1;
		return ifadmin;
	}
	/**
	 * 取得指定用户的系统管理菜单
	 * @param userid
	 * @return
	 */
	public List getSystemConfigMenus(Loginer loginer){
		//增加权限判断
		List<CoreResource> list=find("from CoreResource where resouretype=? "+loginer.getPortal().getPortalWhereByCode()+"order by orderno",new Object[]{"0"});
		logger.info("SystemConfigMenus size:"+list.size());
		if(loginer.getLoginerStatus()==loginer.ADMIN_LOGININ)			
		 return list;
		List<CoreResource> result=new ArrayList();
		for(CoreResource cr:list){
			if(loginer.haveThePermission(cr.getResourcekey()))
				result.add(cr);
		}
		return result;
	}
	
	public static String getEncString(String name,String pwd){
		String _name = name.trim().toLowerCase();
		AesEncrypt aes = new AesEncrypt(_name);
		String _pwd = aes.getEncString(pwd.trim().toLowerCase());
		return _pwd;
	}
	/**
	 * 按门户编号来取得配置的门户信息
	 * @param portalcode
	 * @return
	 */
	public Portal getPortalByCode(String portalcode) {
		Map allPortal = PortalUtil.getInstance().getAllPortal();
		//logger.info("portalcode:" + portalcode);
		Object obj = allPortal.get(portalcode);
		return obj == null ? null : (Portal) obj;
	}	
}
